package com.hlkj.test;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hlkj.util.ConnectionUrlUtil;
import com.hlkj.util.Md5Util;

public class Test4 {

	public static void main(String[] args) throws UnsupportedEncodingException {
//		String str="验证码为:"+1211+",本次验证码有效期为30分钟";//61.191.47.181
//		String result=ConnectionUrlUtil.sendPost("http://61.191.47.181/ah_send_msg/","depart=创新部流量团队&phone="+"17705697364"+"&content="+str+"&userName=张光辉");
	    String lineTxt="17705697364";
		String staffCode="-10003";
		String key="@Ah#$%^TYI141";
		String url="http://61.191.44.252:8123/crm/oipBusiness.do";
		String sign=Md5Util.md5Encode(lineTxt+staffCode+key);
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("sign", sign);
		jsonObject.put("staffCode",staffCode);
		jsonObject.put("userid", lineTxt);
		String result=ConnectionUrlUtil.sendPost(url, "para="+jsonObject.toString()+"&action=queryLantId&type=md5");
	 	//System.out.println(result);
	 	JSONObject json = JSONObject.fromObject(result);
	 	result=json.optString("lantId");
	    System.out.println(result);
	   
		
		
	}
}
