package com.hlkj.util;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class ConnectionUrlUtil {
	public static String sendPost(String url, String requestXml) {
		String result = "";
		try {
			 

			URL u0 = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u0.openConnection();
			conn.setRequestMethod("POST");
			byte contentbyte[] = requestXml.toString().getBytes();
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", (new StringBuilder()).append(contentbyte.length).toString());
			conn.setRequestProperty("Content-Language", "en-US");
			
			conn.setConnectTimeout(60000);
			conn.setReadTimeout(60000);
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bWriter.write(requestXml.toString());
			bWriter.flush();
			bWriter.close();
			InputStream in = conn.getInputStream();
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i != -1;) {
				i = in.read();
				if (i != -1)
					buffer.append((char) i);
			}
			in.close();
			result =new String(buffer.toString().getBytes("iso-8859-1"), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}
		System.out.println("result="+result);
		return result;
	}
	 
	public static String sends() {
		String msg = sendPost("http://61.191.47.181/ah_send_msg/core2",
		"phone=17705697364&content=短信测试2q");
		System.out.println(msg);
		return msg;
	}
	 public static void main(String[] args) {
		 sends();
	}
	
}
