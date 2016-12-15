package com.hlkj.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import com.hlkj.util.AES;
import com.hlkj.util.ConnectionDB;
import com.hlkj.util.ConnectionUrlUtil;

public class Test {

	public static void main(String[] args) throws ParseException {
		 ConnectionDB db = new ConnectionDB();
//		 List<Object> list=db.excuteQuery("select sysdate dd from dual", new Object[]{});
//		 System.out.println(list.get(0));
//		 List<Object> list=db.excuteQuery("select  phone  from user_share where phone ='17705697365' and status ='1'", null);
//		 if(list.size()>0)
//		 System.out.println(list.get(0));
//			 JSONObject js = JSONObject.fromObject(list.get(0));
//			 int a = Integer.valueOf(js.optString("nextval"));
//			 System.out.println(js.optString("nextval"));
//		   String ss = AES.Encrypt("17755105916");
//		   System.out.println(ss);
//		   System.out.println(AES.Decrypt(ss));
		 
//		  String sql = "update user_share  set status = ? where phone = ? ";
//		  db.executeUpdate(sql, new Object[]{"0",null});
	 
//				String phone = "17705697364";
//				String packgeid = "2301225804013292";// ��Ʒid
//				String action = "huilong";// ������
//				String price = "20";// �۸�
//				String ordertime = "2016-04-12 12:00:00";// ����ʱ��
//				String skey = AES.Encrypt("GNLAHFLOW"+phone+action);// �ܳ�
//				String dfurl = ConnectionUrlUtil.sendPost(
//						"http://61.191.47.181/ahflowred/orderRed", "phone=" + phone
//								+ "&packgeid=" + packgeid + "&action=" + action
//								+ "&price=" + price + "&ordertime=" + ordertime
//								+ "&skey=" + skey);
//				System.out.println(dfurl);
		 String sph="17705697364";
		 sph=sph.substring(0,3)+"****"+sph.substring(7,11);
		 System.out.println(sph);
		}



		/*����ID��������ƣ�
		BIGBANG��������ը�ͻ���
		PUBLIC��������ը���ں�
		100018MG��100018����
		NETHALL��WAPר��ҳ��
		HAND10000������10000
		AHHUIP�����յ��Ź��ں�
		DPI��toolbar*/
	
		 

 
}
