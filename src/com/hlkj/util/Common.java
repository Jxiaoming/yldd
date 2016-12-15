package com.hlkj.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

import net.sf.json.JSONObject;

public class Common {
	private static   String APPID = "wx140002bd54e78ef9";;// 公众号appid ----我自己的
	private static final String APPSECRET = "7b6bcb5d38d964e66fd4a01ace72026d";// 公众号APPSECRET --我自己的
	
	private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	
	private static final String JSAPI_TICKET_URL ="http://wt.189.cn/cgi-bin/ticket/getticket";// "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    //wt.10006.info
	public String randomStr() {
		String numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz"
				+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String numStr = "";
		Random random = new Random();
		for (int i = 0; i < 11; i++) {
			numStr += numbersAndLetters.charAt(random.nextInt(71));
		}
		return numStr;
	}
	/**
	 * 未集约
	 * @param noncestr
	 * @param timestamp
	 * @param url
	 * @return
	 */
/*	public String getSignature(String noncestr, String timestamp, String url) {
		try {
			String access_token=AccessToken.tokenDao.getToken(DateUtil.getFormatDateTime(new Date()), DateUtil.getFormatDateTime(new Date()));
			System.out.println(access_token);
			// 当token缓存为空时，新建新的缓存
			if (CharUtil.isEmpty(access_token)) {
				String access_token_json = ConnectionUrlUtil.sendPost(TOKEN_URL, "grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET);
				JSONObject tokenJson = JSONObject.fromObject(access_token_json);
				System.out.println("--返回token"+tokenJson.toString());
				access_token = tokenJson.getString("access_token");
				//缓存token
				String starTime=DateUtil.getFormatDateTime(new Date());
				String endTime=DateUtil.getFormatDateTime(getafter2hours(new Date()));	
				AccessToken.tokenDao.addToken(access_token, starTime, endTime);
			}
			String jsapi_ticket="";
			String jsapi_ticket_json = ConnectionUrlUtil.sendPost(JSAPI_TICKET_URL, "access_token=" + access_token + "&type=jsapi");
			JSONObject jsapiJson = JSONObject.fromObject(jsapi_ticket_json);
			System.out.println("--jsapiJson:"+jsapiJson);
			jsapi_ticket = jsapiJson.getString("ticket");
			// 当前网页的URL，不包含#及其后面部分
			String para = "jsapi_ticket=" + jsapi_ticket + "&noncestr="+ noncestr + "&timestamp=" + timestamp + "&url=" + url;
			System.out.println("para==="+para);
			String signature = DigestUtils.shaHex(para);
			return signature;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	} 
	/**
	 * 集约
	 * @param args
	 */
	public String getSignature(String noncestr, String timestamp, String url) {
		System.out.println("noncestr:"+noncestr+",timestamp:"+timestamp+",当前地址路径:"+url);
		try {
			String jsapi_ticket="";
			
			String jsapi_ticket_json = ConnectionUrlUtil.sendPost(
					JSAPI_TICKET_URL, "grant_type=client_credential&appid=" + APPID + "&secret="
					+ APPSECRET);
			System.out.println("jsapi_ticket_json==="+jsapi_ticket_json);
			JSONObject jsapiJson = JSONObject.fromObject(jsapi_ticket_json);
			jsapi_ticket = jsapiJson.getString("ticket");
			// 当前网页的URL，不包含#及其后面部分
			String para = "jsapi_ticket=" + jsapi_ticket + "&noncestr="+ noncestr + "&timestamp=" + timestamp + "&url=" + url;
			System.out.println("para==="+para);
			String signature = DigestUtils.shaHex(para);
			System.out.println("签名信息:"+signature);
			return signature;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	public Date getafter2hours(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 2);
		Date date2 = calendar.getTime();
		return date2;
	}
	public static void main(String[] args) {
		//new Common().getSignature("1442585791", "ld99LLdN56", "http://ah180.huilongkj.com/fashion");
		long times = new Date().getTime();
		String timeStr = times + "";
		timeStr = timeStr.substring(0, timeStr.length() - 3);
		String nonceStr = new Common().randomStr();
		String url = "";
	    url = "http://ah231.huilongkj.com/traffic/fx" ;
		String signature = new Common().getSignature(nonceStr, timeStr, url);
	}
}
