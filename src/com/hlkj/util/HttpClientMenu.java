package com.hlkj.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.log4j.Logger;

public class HttpClientMenu
{
	static Logger log= Logger.getLogger("com.js.bss.manager.HttpClientMenu");
	String url = "";
	
	public HttpClientMenu(String url){
		this.url = url;
		
	}
	
	private static String encoding="UTF-8";
	//private static String encoding="gbk";
	private static int timeOut=1000;
	
	public  String excute(String requestData){

		HttpClientParams httpClientParams = new HttpClientParams(); 
		String contentType = "text/xml";
		
		httpClientParams.setContentCharset(encoding);
		httpClientParams.setHttpElementCharset(encoding);
		
		HttpClient httpClient = new HttpClient(httpClientParams);
		httpClient.getHostConfiguration().setHost(url);	
		HttpConnectionManagerParams httpConnectionManagerParams = new HttpConnectionManagerParams();
		httpConnectionManagerParams.setConnectionTimeout(timeOut * 60);
		httpConnectionManagerParams.setDefaultMaxConnectionsPerHost(1);
		httpConnectionManagerParams.setMaxTotalConnections(20);
		httpConnectionManagerParams.setSoTimeout(timeOut * 1);
		
		//httpClient.getHttpConnectionManager().setParams(httpConnectionManagerParams);
		String responseBody=null;
		PostMethod postMethod = null;
		try {
			postMethod = new PostMethod(url);
			RequestEntity requestEntity = new StringRequestEntity(requestData, contentType, encoding);
			postMethod.setRequestEntity(requestEntity);
			@SuppressWarnings("unused")
			int httpReturnCold = httpClient.executeMethod(postMethod);
			responseBody = postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();
		} catch (UnsupportedEncodingException e){
			log.error("UnsupportedEncodingException:"+e.getMessage());
		} catch (HttpException e) {
			log.error("HttpException:"+e.getMessage());
		} catch (IOException e) {
			log.error("IOException"+e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
		return responseBody;

	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEncoding() {
		return encoding;
	}
	@SuppressWarnings("static-access")
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public int getTimeOut() {
		return timeOut;
	}
	@SuppressWarnings("static-access")
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
	

}
