package com.hlkj.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
public class SendMessage {
	public static String send(HttpServletRequest request,String phone,String content,String msg){
		 
		String geturl = "http://ln212.10006.info/ah_mstywap/SMS_toSend?flag=1&pho="+phone+"&content="+content;
        try {
            URL url = new URL(geturl);    //ַ
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();//
            connection.connect();// 
            //
            BufferedReader brr = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String s=null;
            StringBuilder sbb = new StringBuilder();
            while ((s = brr.readLine()) != null) {// 
                sbb.append(s); 
            }
            brr.close();// 
            connection.disconnect();//
            
            if("0".equals(sbb.toString())){
            	 return "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
		 
		return "";
	}
}
