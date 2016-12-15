package com.hlkj.util;

import net.sf.json.JSONObject;

public class PhoneOfHefeiUtil {

	 public static String getLanId(String lineTxt){
		 
		 
			String staffCode="-10003";
			String key="@Ah#$%^TYI141";//61.191.44.252 		//内网172.16.7.242  外网61.191.44.252
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
		    return result;
		 
	 }
	 public static void main(String[] args) {
		System.out.println( getLanId("17755557364"));
	}
}
