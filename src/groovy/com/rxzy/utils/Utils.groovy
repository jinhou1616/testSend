package com.rxzy.utils

import org.springframework.web.context.request.WebRequest
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Administrator on 2014/8/1.
 */
class Utils {


    //使用流方式发送数据
    public static int sendPostRequestByForm(String path, String smsContent,String mobiles ) throws Exception{

        String params = getParams(smsContent,mobiles);//获取，设置发送参数
        if (params==null)return -1;//获取参数失败


        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");// 提交模式
        // conn.setConnectTimeout(10000);//连接超时 单位毫秒
        // conn.setReadTimeout(2000);//读取超时 单位毫秒
        conn.setDoOutput(true);// 是否输入参数
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
//        InputStream inStream=conn.getInputStream();
        //    return StreamTool.readInputStream(inStream);


        InputStream inStream=conn.getInputStream();
        String str  = convertStreamToString(inStream);


        if (str.contains("false"))return 0;//发送失败
        if (str.contains("true"))return 1;//发送成功
        return  1;

    }

//从输入流中读取返回信息
    public static  String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder sb = new StringBuilder();



        String line = null;

        try {

            while ((line = reader.readLine()) != null) {

                sb.append(line + "/n");

            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                is.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }



        return sb.toString();

    }











    public static String   getParams(String smsContent,String mobiles){
        //    def String params ;

        def props = new Properties();
        new File(System.getProperty("user.dir") +"/grails-app/conf/messages.properties").withInputStream {stream -> props.load(stream) };

        def timeTemp = new Date().getTime();
        def appIdTemp = props["appId"];
        def appSignTemp = props["appSign"];
        String tAppId  = appIdTemp.toString();
        String appId = tAppId.substring(1,tAppId.length()-1);

        String tAppSign = appSignTemp.toString();
        String appSign = tAppSign.substring(1,tAppSign.length()-1);

        def sign = (appId+appSign+timeTemp).encodeAsMD5();



        StringBuffer params = new StringBuffer();
        params.append("appId").append("=").append(appId).append("&").append("appSign").append("=").append(sign).append("&").append("time").append("=").append(timeTemp).append("&").append("mobiles").append("=").append(mobiles).append("&").append("content").append("=").append(smsContent);


        return params.toString();


    }




}
