package com.example.tianqiyubao.juhe;

public class URLUtils {
    public static final String KEY="864bdd5bfbaa5474078aa98ec94ed947";
    public static final String KEY1="108f91d439908e5d2da86cc8881dd3db" ;
    public static String temp_url="http://apis.juhe.cn/simpleWeather/query";
    public static String index_url="http://apis.juhe.cn/simpleWeather/life";//指数信息的部分

    /**
     * 获取天气url
     * @return
     */
    public static String getTemp_url(String city){
        String url=temp_url+"?city="+city+"&key="+KEY;
        return url;
    }
    public static String getIndex_url(String city){
        String url=index_url+"?city="+city+"&key="+KEY;
        return url;
    }
}
