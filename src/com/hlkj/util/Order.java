package com.hlkj.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Order {

	/**
	 * 流量订购提示信息发送(直接订购)
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public static String dw(String phone,String pid){
		String userid=phone;
		String staffCode="";
		String key="";
		
		staffCode="-10002";//公用的 100018
		key="Ah@Telnet10018";
		
		//内网172.16.7.242  外网61.191.44.252
		String url="http://172.16.7.242:8123/crm/oipBusiness.do";
		String sign=Md5Util.md5Encode(userid+staffCode+key);
		JSONObject jsonObject =new JSONObject();
		jsonObject.put("sign", sign);
		jsonObject.put("staffCode",staffCode);
		jsonObject.put("userid", userid);
        jsonObject.put("proid", pid);
//		jsonObject.put("inactiveDate", "");
//		jsonObject.put("effectDate", "");
		System.out.println(jsonObject.toString());//"para="+jsonObject.toString()+"&action=orderFlow&type=md5"
		String result=ConnectionUrlUtil.sendPost(url,"para="+jsonObject.toString()+"&action=orderFlow&type=md5");			
        System.out.println(result);
		return result;
	}
	/**
	 * 
	 * @param userid
	 */
	public static String queryLantId(String userid){
		//内网172.16.7.242  外网61.191.44.252
		String url="http://61.191.44.252:8123/crm/oipBusiness.do";
		try{
			//String userid="18956001032";  //18156990455
			String staffCode="-10003";
			String key="@Ah#$%^TYI141";
			
			String sign=Md5Util.md5Encode(userid+staffCode+key);
			JSONObject jsonObject =new JSONObject();
			jsonObject.put("sign", sign);
			jsonObject.put("staffCode",staffCode);	
			jsonObject.put("userid", userid);
			//System.out.println(jsonObject.toString());
			String result=ConnectionUrlUtil.sendPost(url,"para="+jsonObject.toString()+"&action=queryLantId&type=md5");
			jsonObject =JSONObject.fromObject(result);
			return jsonObject.optString("lantId");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	
	public static String order100(String phone){
		//172.16.25.132  61.191.47.139
		String url=" http://172.16.25.132/flowdd";
		String staffCode="-100051";
		String key="hflanD@dx2016";
		String userid=phone;
		StringBuffer sb=new StringBuffer();
		sb.append("userid="+userid);
		sb.append("&qdid="+staffCode);
		sb.append("&sqnid=51");
		sb.append("&sign="+Md5Util.md5Encode((userid+staffCode+key).toUpperCase()));
		String result=ConnectionUrlUtil.sendPost(url, sb.toString());
		JSONObject json = JSONObject.fromObject(result);
		result = json.optString("result");
		result=result.equals("0")?"success":result;
		return result;
	}
   public static void main(String[] args) {
	    //372
	   System.out.println(queryLantId("17705697364"));
	   
	   
}
 

}
