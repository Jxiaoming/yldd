package com.hlkj.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hlkj.util.AES;
import com.hlkj.util.ConnectionUrlUtil;
import com.hlkj.util.Md5Util;

import net.sf.json.JSONObject;

public class Test2 {
	public static void main(String[] args) throws ParseException {
//		String url=" http://61.191.47.139/flowdd";
//		String staffCode="-100051";
//		String key="hflanD@dx2016";
//		String userid="17705697364";
//		StringBuffer sb=new StringBuffer();
//		sb.append("userid="+userid);
//		sb.append("&qdid="+staffCode);
//		sb.append("&sqnid=51");
//		sb.append("&sign="+Md5Util.md5Encode((userid+staffCode+key).toUpperCase()));
//		String result=ConnectionUrlUtil.sendPost(url, sb.toString());
//        System.out.println(result);
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date date =  (Date) (sdf.parseObject("2016-08-10 15:05:22")) ;
         System.out.println(date.getTime());
         System.out.println(new Date().getTime());
		if(new Date().getTime()-date.getTime()>24*60*60*1000){
			
			System.out.println("24小时外");
		}else{
			System.out.println("24小时内");
		}
	}
}
