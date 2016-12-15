package com.hlkj.util;
import java.security.MessageDigest;
import java.util.regex.Pattern;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
public class UserUtil {
	public static String getUseridByCookies(HttpServletRequest request){
		String userid =null;
		try{
				  Cookie cookies[]=request.getCookies();
					if(cookies!=null){
						for(Cookie cookie : cookies){
							if("ah_flow_user".equals(cookie.getName())){
								userid=AES.Decrypt(cookie.getValue());
								break;
							}
						}
					}
			
		  
		}catch(Exception ex){
		}
		if(userid==null)return null;
		return userid;
	}
	
	
	/**
	 *判断是否是电信号码
	 * 
	 */
	public static int existsUserid(String userid)
	{
		try{
			if(userid==null){
				return 1;
			}
			Pattern pattern = Pattern.compile("[0-9]*"); 
			boolean b=pattern.matcher(userid).matches();
			if(b==false)
				return 1;
			if(userid.length()!=11){
				return 1;
			}
			userid=userid.substring(0,3);
			if(userid.equals("189")|| userid.equals("180") ||userid.equals("181") || userid.equals("133")|| userid.equals("153")|| userid.equals("177")){
				return 0;
			}
			else
				return 2;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0;
	}
	/*
	 * 进行md5加密
	 * */
	public static String md5Encode(String str)
	{
		StringBuffer buf = new StringBuffer();
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes());
			byte bytes[] = md5.digest();
			for(int i = 0; i < bytes.length; i++)
			{
			String s = Integer.toHexString(bytes[i] & 0xff);
			if(s.length()==1){
			buf.append("0");
			}
			buf.append(s);
		}
		}
		catch(Exception ex){	
		}
		return buf.toString().toLowerCase();//转换成小写字母
	}
	/*
	 * 获取当前系统时间
	 * */
	public java.sql.Timestamp getTimestamp(){
		java.util.Date Dates = new java.util.Date();
		return  new java.sql.Timestamp(Dates.getTime());
		
	}
}
