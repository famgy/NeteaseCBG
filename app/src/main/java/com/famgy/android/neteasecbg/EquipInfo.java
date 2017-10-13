package com.famgy.android.neteasecbg;

/**
 * Created by uniking on 17-9-11.
 */

public class EquipInfo {
    public static String getUrl(String serverId, String game_ordersn)
    {
        String a = "http://xy2-android2.cbg.163.com/cbg-center/query.py?act=get_equip_detail&game_ordersn=";
        String b = "&serverid=";
        String c = "&platform=android&app_version=2.0.8&need_check_license=1&sdk_version=23&device_name=AOSP+on+HammerHead&app_version_code=2080&os_version=6.0&package_name=com.netease.xy2cbg&os_name=aosp_hammerhead";

        return a + game_ordersn + b + serverId + c;
    }
}
