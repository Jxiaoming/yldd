package com.hlkj.util;

import java.security.MessageDigest;

public class Md5Util {
	/***
	 *@
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
		return buf.toString().toLowerCase();//
	}
	public static void main(String[] args) {
		String md5 = md5Encode("17705697364");
		System.out.println(md5);    
		String md6 = md5Encode("80ca9f7d56478ae5eb37712a0f5a524c");
		System.out.println(md6);
 
	}
}

