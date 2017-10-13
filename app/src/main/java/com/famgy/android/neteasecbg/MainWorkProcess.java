package com.famgy.android.neteasecbg;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by famgy on 10/12/17.
 */

public class MainWorkProcess implements Runnable {
    Activity mActivity;
    Handler mHandler;
    int count = 0;
    int page = 1;
    private final int MSG_TIMER_NOTICE = 0;
    private final int MSG_EQUIP_VERBOSE = 1;
    boolean bIsContinue = true;

    MainWorkProcess(Activity activity, Handler handler)
    {
        mActivity = activity;
        mHandler = handler;
    }

    void setBIsContinue(boolean bIsContinue)
    {
        this.bIsContinue = bIsContinue;
    }

    @Override
    public void run() {

        while(bIsContinue)
        {
            try {
                Thread.sleep(1000);
                mHandler.sendEmptyMessage(MSG_TIMER_NOTICE);

                count++;
                if (count >= 5) {
                    count = 0;
                    HttpUtil.sendHttpRequest(EquipListInfo.getUrl(125, page), new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) throws JSONException {
                            JSONObject equipList = new JSONObject(response);
                            JSONArray ja = equipList.getJSONArray("equip_list");
                            //for(int i=0; i<ja.length(); i++)
                            for(int i=0; i<1; i++)
                            {
                                JSONObject one = ja.getJSONObject(i);
                                HttpUtil.sendHttpRequest(EquipInfo.getUrl(String.valueOf((int)one.get("equip_serverid")), (String) one.get("game_ordersn")), new HttpCallbackListener() {
                                    @Override
                                    public void onFinish(String response) {
                                        //Log.w("cbg", response);
                                        mHandler.obtainMessage(MSG_EQUIP_VERBOSE, response).sendToTarget();//发送消息到CustomThread实例
                                    }

                                    @Override
                                    public void onError(Exception e) {

                                    }
                                });

                                //Thread.sleep(2000);
                            }
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            Log.w("cbg", "pass 1s");
        }
    }
}
