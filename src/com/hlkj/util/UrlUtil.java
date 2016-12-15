package com.hlkj.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;
 


public class UrlUtil
{
	
	private static Logger logger = Logger.getLogger ( UrlUtil.class.getName () );

  public static String sendPost(String url, String requestXml)
  {
    String result = "";
    try {
      URL u0 = new URL(url);
      HttpURLConnection conn = (HttpURLConnection)u0.openConnection();
      conn.setRequestMethod("POST");
      byte[] contentbyte = requestXml.toString().getBytes();
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      conn.setRequestProperty("Content-Length", ""+contentbyte.length);
      conn.setRequestProperty("Content-Language", "en-US");
      conn.setConnectTimeout(30000);
      conn.setReadTimeout(30000);
      conn.setUseCaches(false);
      conn.setDoInput(true);
      conn.setDoOutput(true);
      BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
      bWriter.write(requestXml.toString());
      bWriter.flush();
      bWriter.close();
      InputStream in = conn.getInputStream();
      StringBuffer buffer = new StringBuffer();
      for (int i = 0; i != -1; ) {
        i = in.read();
        if (i != -1)
          buffer.append((char)i);
      }
      in.close();
      result = new String(buffer.toString().getBytes("iso-8859-1"), "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
  
  
  public static String doGet(String url, String queryString, String charset, boolean pretty) { 
      StringBuffer response = new StringBuffer(); 
      HttpClient client = new HttpClient(); 
      HttpMethod method = new GetMethod(url); 
      try { 
              if (!"".equals(queryString)) 
                      //��get�����������http����Ĭ�ϱ��룬����û���κ����⣬���ֱ���󣬾ͳ�Ϊ%ʽ����ַ� 
                      method.setQueryString(URIUtil.encodeQuery(queryString)); 
              client.executeMethod(method); 
              if (method.getStatusCode() == HttpStatus.SC_OK) { 
                      BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset)); 
                      String line; 
                      while ((line = reader.readLine()) != null) { 
                              if (pretty) 
                                      response.append(line).append(System.getProperty("line.separator"));
                              else 
                                      response.append(line); 
                      } 
                      reader.close(); 
              } 
      } catch (URIException e) { 
//              log.error("ִ��HTTP Get����ʱ�������ѯ�ַ�" + queryString + "�������쳣��", e); 
      } catch (IOException e) { 
//              log.error("ִ��HTTP Get����" + url + "ʱ�������쳣��", e); 
      } finally { 
              method.releaseConnection(); 
      } 
      return response.toString(); 
} 
  
  public static String BatchOrder(String phone){
	  String result = UrlUtil.doGet("http://61.191.47.186/ToolBar/order" ,"phone="+phone+"&pid=d00d&from=batch", "UTF-8", true);
	  logger.error("phone--->" + phone + "result--->"+result); 
	 return  result;
  }
  
  public static String Preference(String phone){
		 return  UrlUtil.sendPost("http://134.64.116.90:9140/httpjsonp/custPreference.action", "{\"LATN_ID\":\"551\",\"ACC_NBR\":\""+phone+"\"}");
	  }
   
}


