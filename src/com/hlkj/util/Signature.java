package com.hlkj.util;

import org.apache.commons.codec.digest.DigestUtils;

import net.sf.json.JSONObject;

public class Signature {
	private static   String APPID = "wx140002bd54e78ef9";
	private static  String APPSECRET = "7b6bcb5d38d964e66fd4a01ace72026d";
	private static  String JSAPI_TICKET_URL = "http://wt.10006.info/cgi-bin/ticket/getticket";
	private static   String TOKEN_URL = "http://wt.10006.info/wx/cgi-bin/token";
	//获取signature  值
	public  String getSignature(String noncestr,String timestamp,String url) {
		String access_ticket=null;
		try {
			System.out.println("------------url:"+url);
			String access_token_json = ConnectionUrlUtil.sendPost(JSAPI_TICKET_URL,"grant_type=client_credential&appid=" + APPID+ "&secret=" + APPSECRET);
			System.out.println("------------access_token_json:"+access_token_json);
			if(access_token_json==null ||"".equals(access_token_json))return "";
			JSONObject tokenJson  =JSONObject.fromObject(access_token_json.trim());
			access_ticket = tokenJson.getString("ticket");
			String para = "jsapi_ticket=" + access_ticket + "&noncestr="+ noncestr + "&timestamp=" + timestamp + "&url="+ url;
			
			String signature = DigestUtils.shaHex(para);
			return signature;
		} catch (Exception e) {
			System.out.println("getSignature:"+e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	
	//获取signature  值
		public  String getAccessToken() {
			String access_token=null;
			try {
				String access_token_json = ConnectionUrlUtil.sendPost(TOKEN_URL,"grant_type=client_credential&appid=" + APPID+ "&secret=" + APPSECRET);
				JSONObject tokenJson  =JSONObject.fromObject(access_token_json.trim());
				access_token = tokenJson.getString("access_token");
			} catch (Exception e) {
				System.out.println("getAccessToken:"+e.getMessage());
				e.printStackTrace();

			}
			return access_token;
		}
	
	public static void main(String[] args) {
		
	}
}
