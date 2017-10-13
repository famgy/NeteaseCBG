package com.famgy.android.neteasecbg;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {
    Button bt_start;
    Button bt_load_url;
    boolean bIsStart = false;
    Handler mHandler;
    ImageView imageView;
    ProgressBar progressBar;
    int progress = 0;
    public int imageSwitch = 0;
    private final int MSG_STOP_SUB_THREAD = 1;
    MainWorkProcess MainWorkThread;
    private TextView equipInfoUI;
    String equipVerboseUrl;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public void ask_permission(String permission)
    {
        String[] permissions = {permission};

        if (Build.VERSION.SDK_INT >= 23)
        {
            int i = ContextCompat.checkSelfPermission(this, permission);
            if (i != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ask_permission(Manifest.permission.INTERNET);
        ask_permission(Manifest.permission.ACCESS_NETWORK_STATE);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        equipInfoUI = (TextView) findViewById(R.id.equipInfo);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        imageView = (ImageView) findViewById(R.id.image_view);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        if (0 == imageSwitch)
                        {
                            imageView.setImageResource(R.drawable.jelly_bean);
                            progressBar.setVisibility(View.VISIBLE);
                            imageSwitch = 1;
                        }
                        else if (1 == imageSwitch)
                        {
                            //progressBar.setVisibility(View.INVISIBLE);
                            imageView.setImageResource(R.drawable.ic_launcher);
                            imageSwitch = 0;
                        }

                        progress = progressBar.getProgress();
                        progress += 20;
                        if (progress > 100)
                        {
                            progress = 0;

                        }
                        progressBar.setProgress(progress);

                        break;
                    case 1:
                        parseJsonWithJSONObject((String) msg.obj);
                        break;
                    default:
                        break;
                }
            }
        };

        bt_start = (Button)findViewById(R.id.bt_start);
        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bIsStart == false)
                {
                    bt_start.setText("停止");
                    bIsStart = true;

                    if (null == MainWorkThread)
                    {
                        MainWorkThread = new MainWorkProcess(MainActivity.this, mHandler);
                    }
                    new Thread(MainWorkThread).start();
                }
                else
                {
                    bt_start.setText("启动");
                    bIsStart = false;

                    MainWorkThread.setBIsContinue(false);
                    MainWorkThread = null;
                }
            }
        });

        bt_load_url = (Button)findViewById(R.id.bt_load_url);
        bt_load_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(equipVerboseUrl));
                startActivity(intent);
            }
        });

    }

    private void parseJsonWithJSONObject(String jsonData) {
        Log.w("cbg", jsonData);

        try {
            JSONObject jsonObjectCBG = new JSONObject(jsonData);
            int status = jsonObjectCBG.getInt("status");
            JSONObject equip = jsonObjectCBG.getJSONObject("equip");
            parseEquipWithJSONObject(equip);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseEquipWithJSONObject(JSONObject equipJsonObject) {

        EquipInfoVerbose equipInfoVerbose = new EquipInfoVerbose();
        try {
            equipInfoVerbose.set_equip_id(equipJsonObject.getInt("equipid"));
            equipInfoVerbose.set_equip_name(equipJsonObject.getString("equip_name"));
            equipInfoVerbose.set_price_desc(equipJsonObject.getString("price_desc"));
            equipInfoVerbose.set_last_price_desc(equipJsonObject.getString("last_price_desc"));
            equipInfoVerbose.set_equip_desc(equipJsonObject.getString("equip_desc"));
            equipInfoVerbose.set_create_time_desc(equipJsonObject.getString("create_time_desc"));
            equipInfoVerbose.set_collect_num(equipJsonObject.getInt("collect_num"));
            equipInfoVerbose.set_fair_show_end_time(equipJsonObject.getString("fair_show_end_time"));
            equipInfoVerbose.set_level_desc(equipJsonObject.getString("level_desc"));
            //equipInfoVerbose.set_allow_bargain(equipJsonObject.getBoolean("allow_bargain"));
            equipInfoVerbose.set_equip_detail_url(equipJsonObject.getString("equip_detail_url"));
            equipInfoVerbose.set_status_desc(equipJsonObject.getString("status_desc"));
            equipInfoVerbose.set_fair_show_poundage_desc(equipJsonObject.getString("fair_show_poundage_desc"));
            equipInfoVerbose.set_equip_type(equipJsonObject.getString("equip_type"));
            equipInfoVerbose.set_area_name(equipJsonObject.getString("area_name"));
            equipInfoVerbose.set_server_name(equipJsonObject.getString("server_name"));
            equipInfoVerbose.set_fair_show_end_time_desc(equipJsonObject.getString("fair_show_end_time_desc"));
            equipInfoVerbose.set_expire_time(equipJsonObject.getString("expire_time"));
            equipInfoVerbose.set_pass_fair_show(equipJsonObject.getString("pass_fair_show"));
            //equipInfoVerbose.set_allow_cross_buy(equipJsonObject.getString("allow_cross_buy"));
            equipInfoVerbose.set_selling_time(equipJsonObject.getString("selling_time"));

            refreshEquipInfoUI(equipInfoVerbose);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void refreshEquipInfoUI(EquipInfoVerbose equipInfoVerbose)
    {
        StringBuffer sb = new StringBuffer(256);

        sb.append("selling_time : ");
        sb.append(equipInfoVerbose.get_selling_time());

        sb.append("\nequip_id : ");
        sb.append(equipInfoVerbose.get_equip_id());

        sb.append("\nequip_name : ");
        sb.append(equipInfoVerbose.get_equip_name());

        sb.append("\nprice_desc : ");
        sb.append(equipInfoVerbose.get_price_desc());

        sb.append("\nexpire_time : ");
        sb.append(equipInfoVerbose.get_expire_time());

        equipVerboseUrl = equipInfoVerbose.get_equip_detail_url();

        logMsg(sb.toString());
    }

    public void logMsg(String str) {
        final String s = str;
        try {
            if (equipInfoUI != null){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        equipInfoUI.post(new Runnable() {
                            @Override
                            public void run() {
                                equipInfoUI.setText(s);
                            }
                        });

                    }
                }).start();
            }
            //LocationResult.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
