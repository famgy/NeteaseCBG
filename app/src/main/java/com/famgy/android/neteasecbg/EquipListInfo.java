package com.famgy.android.neteasecbg;

/**
 * Created by uniking on 17-9-11.
 */

public class EquipListInfo {
    public  static String getUrl(int page)
    {
        String a = "http://xy2-android2.cbg.163.com/cbg-center/query.py?page=";
        String b = "&channel=ANDNETEASE&pass_fair_show=1&platform=android&app_version=2.0.8&need_check_license=1&sdk_version=23&device_name=AOSP+on+HammerHead&app_version_code=2080&os_version=6.0&package_name=com.netease.xy2cbg&os_name=aosp_hammerhead";

        return a + String.valueOf(page) + b;
    }


    //http://xy2-android2.cbg.163.com/cbg-center/query.py?kindid=54&serverid=125&page=1&channel=ANDNETEASE&pass_fair_show=0&platform=android&app_version=2.0.8&need_check_license=1&sdk_version=23&device_name=AOSP+on+HammerHead&app_version_code=2080&os_version=6.0&package_name=com.netease.xy2cbg&os_name=aosp_hammerhead%200x4c643f6c%20NORMAL%20null
    public static String getUrl(int serverId, int page)
    {
        String a = "http://xy2-android2.cbg.163.com/cbg-center/query.py?serverid=";
        String b = "&page=";
        String c = "&channel=ANDNETEASE&orderby=selling_time+DESC&pass_fair_show=0&platform=android&app_version=2.0.8&need_check_license=1&sdk_version=23&device_name=AOSP+on+HammerHead&app_version_code=2080&os_version=6.0&package_name=com.netease.xy2cbg&os_name=aosp_hammerhead";

        return a + String.valueOf(serverId) + b + String.valueOf(page) + c;
    }
}
