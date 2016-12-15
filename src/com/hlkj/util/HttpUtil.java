package com.hlkj.util;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 陈俊 E-mail: chj252817295@126.com
 * @version 创建时间：2014-11-17
 */

public class HttpUtil {
	/**
	 * HTTP POST请求 content-Type:application/ x-json;charset=UTF-8
	 * 
	 * @param url
	 * @param parameter
	 * @return
	 */
	public static String sendPost(String url, String parameter) {
		String result = "连接超时";
		try {
			URL u0 = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u0.openConnection();
			conn.setRequestMethod("POST");
			byte contentbyte[] = parameter.toString().getBytes();
			// conn.setRequestProperty("HOST","http://pay.ah.vnet.cn/");
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 设置请求类型
			conn.setRequestProperty("Content-Type", "text/plain;charset=UTF-8"); // application/x-www-form-urlencoded
			// 设置表单长度
			conn.setRequestProperty("Content-Length", (new StringBuilder())
					.append(contentbyte.length).toString());
			// 设置默认语言
			conn.setRequestProperty("Content-Language", "zh-CN");// zh-CN代表中国
			// en-US美式英语
			// 默认为美式英语
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("contentType", "UTF-8");
			// 连接主机的超时时间（单位：毫秒）
			conn.setConnectTimeout(30000);
			// 从主机读取数据的超时时间（单位：毫秒)
			conn.setReadTimeout(30000);
			// Post 请求不能使用缓存
			conn.setUseCaches(false);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setDoInput(true);
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在 2
			// http正文内，因此需要设为true, 默认情况下是false;
			conn.setDoOutput(true);
			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(
					conn.getOutputStream()));
			bWriter.write(parameter.toString());
			bWriter.flush();
			bWriter.close();
			// 调用HttpURLConnection连接对象的getInputStream()函数,
			// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
			InputStream in = conn.getInputStream();
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i != -1;) {
				i = in.read();
				if (i != -1)
					buffer.append((char) i);
			}
			in.close();

			// 此方法是用Reader读取BufferedReader reader = new BufferedReader(new
			// InputStreamReader( connection.getInputStream()));
			result = new String(buffer.toString().getBytes("iso-8859-1"),
					"UTF-8");
			conn.disconnect();
		} catch (Exception ex) {
			System.out.println(ex);
			result = "";
		}
		return result;
	}

	/**
	 * HTTP POST请求 content-Type:application/x-www-form-urlencoded
	 * 
	 * @param url
	 * @param parameter
	 * @return
	 */
	public static String sendPost2(String url, String parameter) {
		String result = "连接超时";
		try {
			URL u0 = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u0.openConnection();
			conn.setRequestMethod("POST");
			byte contentbyte[] = parameter.toString().getBytes();
			// conn.setRequestProperty("HOST","http://pay.ah.vnet.cn/");
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 设置请求类型
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 设置表单长度
			conn.setRequestProperty("Content-Length", (new StringBuilder())
					.append(contentbyte.length).toString());
			// 设置默认语言
			conn.setRequestProperty("Content-Language", "zh-CN");// zh-CN代表中国
			// en-US美式英语
			// 默认为美式英语
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("contentType", "UTF-8");
			// 连接主机的超时时间（单位：毫秒）
			conn.setConnectTimeout(30000);
			// 从主机读取数据的超时时间（单位：毫秒)
			conn.setReadTimeout(30000);
			// Post 请求不能使用缓存
			conn.setUseCaches(false);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setDoInput(true);
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在 2
			// http正文内，因此需要设为true, 默认情况下是false;
			conn.setDoOutput(true);
			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(
					conn.getOutputStream()));
			bWriter.write(parameter.toString());
			bWriter.flush();
			bWriter.close();
			// 调用HttpURLConnection连接对象的getInputStream()函数,
			// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
			InputStream in = conn.getInputStream();
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i != -1;) {
				i = in.read();
				if (i != -1)
					buffer.append((char) i);
			}
			in.close();

			// 此方法是用Reader读取BufferedReader reader = new BufferedReader(new
			// InputStreamReader( connection.getInputStream()));
			result = new String(buffer.toString().getBytes("iso-8859-1"),
					"UTF-8");
			conn.disconnect();
		} catch (Exception ex) {
			System.out.println(ex);
			result = "";
		}
		return result;
	}

	public static String sendGet(String url, String parameter) {
		String result = "连接超时";
		try {
			URL u0 = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u0.openConnection();
			conn.setRequestMethod("GET");
			byte contentbyte[] = parameter.toString().getBytes();
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 设置请求类型
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 设置表单长度
			conn.setRequestProperty("Content-Length", (new StringBuilder())
					.append(contentbyte.length).toString());
			// 设置默认语言
			conn.setRequestProperty("Content-Language", "en-US");// zh-CN代表中国
			// 默认为美式英语
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("contentType", "utf-8");
			// 连接主机的超时时间（单位：毫秒）
			conn.setConnectTimeout(30000);
			// 从主机读取数据的超时时间（单位：毫秒)
			conn.setReadTimeout(30000);
			// Post 请求不能使用缓存
			conn.setUseCaches(false);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setDoInput(true);
			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在 2
			// http正文内，因此需要设为true, 默认情况下是false;
			conn.setDoOutput(true);
			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(
					conn.getOutputStream()));
			bWriter.write(parameter.toString());
			bWriter.flush();
			bWriter.close();
			// 调用HttpURLConnection连接对象的getInputStream()函数,
			// 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
			InputStream in = conn.getInputStream();
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i != -1;) {
				i = in.read();
				if (i != -1)
					buffer.append((char) i);
			}
			in.close();

			// 此方法是用Reader读取BufferedReader reader = new BufferedReader(new
			// InputStreamReader( connection.getInputStream()));
			result = new String(buffer.toString().getBytes("iso-8859-1"),
					"UTF-8");
			conn.disconnect();
		} catch (Exception ex) {
			System.out.println(ex);
			result = "";
		}
		return result;
	}

	public static String sendGet(String surl) {
		String htmlContent = "";
		try {
			java.io.InputStream inputStream;
			java.net.URL url = new java.net.URL(surl);
			java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url
					.openConnection();
			connection.connect();
			inputStream = connection.getInputStream();
			byte bytes[] = new byte[1024 * 2000];
			int index = 0;
			int count = inputStream.read(bytes, index, 1024 * 2000);
			while (count != -1) {
				index += count;
				count = inputStream.read(bytes, index, 1);
			}
			htmlContent = new String(bytes, "UTF-8");
			connection.disconnect();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return htmlContent.trim();
	}

}
