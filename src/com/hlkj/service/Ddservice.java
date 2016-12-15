package com.hlkj.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.hlkj.util.ConnectionDB;
import com.hlkj.util.SendMessageUtil;

public class Ddservice {
	
	private ConnectionDB db =null;
	private Logger log=Logger.getRootLogger();
	 
	public String insert(String phone,String sphone,String result,String msg){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		db = new ConnectionDB();
		List<Object> list=db.excuteQuery("SELECT ah_yldd_ID.NEXTVAL FROM DUAL", null);
		JSONObject jss = JSONObject.fromObject(list.get(0));
		String id = jss.optString("nextval");
		System.out.println("id-----------------------》 "+id);
		int rows = db.executeUpdate("insert into ah_yldd values (?,?,?,?,?,?)",new Object[]{id,phone,sphone,sdf.format(new Date()),result,msg});
		return rows+"";
	}
	public String update(String result,String msg,String ph){
		db = new ConnectionDB();
		db.executeUpdate("update ah_yldd set msg = ? where result = ? and phone=?", new Object[]{msg,result,ph});
		return "1";
	}
	public String queryPhone(String phone){
		db = new ConnectionDB();
		List<Object> list=db.excuteQuery("select count(*) ct from ah_wj where phone = ? ", new Object[]{phone});
		JSONObject jss = JSONObject.fromObject(list.get(0));
		String counts = jss.optString("ct");
		return counts;
	}
	public String getPackages(){
		ConnectionDB db = new ConnectionDB();
		//List<Object> list=db.excuteQuery("select * from (select * from ah_100 order by id) where status!='1'", null);//oracle
		List<Object> list=db.excuteQuery("select * from ah_yldd_packageno where status !='1' order by id", null);
		JSONObject js = new JSONObject();
		 
		js =JSONObject.fromObject(list.get(0));
		String id = js.optString("id");
		String packno=js.optString("package_no");
		db.executeUpdate("update ah_yldd_packageno set  status ='1' where id =?", new Object[]{id});
		return packno;
	} 
	public boolean  checkCode(String phone,String msg){
		 db = new ConnectionDB();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 String date = sdf.format(new Date());
		 System.out.println("date="+date);
		 String sql="select count(*) ct from ah_yldd_code where phone=? and stime like '"+date+"%'";
		 System.out.println(sql);
		 List<Object> list=db.excuteQuery(sql, new Object[]{phone});
		 JSONObject json = JSONObject.fromObject(list.get(0));
		 int j = Integer.valueOf(json.optString("ct"));
		 System.out.println("一路滴滴：手机号码"+phone+",今天已发验证码"+j+"次");
		 if(j<10){//每天只能发10次验证码
			 return true;
		 }else{
			 return false;
		 } 
	}
	public String insertCode(String phone ,String msg){
		 List<Object> list1=db.excuteQuery("select ah_yldd_code_id.Nextval from dual", null);
		 JSONObject jss = JSONObject.fromObject(list1.get(0));
		 int i = Integer.valueOf(jss.optString("nextval"));
		 SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 db.executeUpdate("insert into ah_yldd_code values(?,?,?,?)", new Object[]{i,phone,msg,sdf2.format(new Date())});  
		 return null;
	}
	public List<Object> getCodeInfo(String phone){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 String date = sdf.format(new Date());
		 String sql="select * from ah_yldd_code where phone=? and stime like '"+date+"%' order by stime desc";
		 List<Object> list=db.excuteQuery(sql, new Object[]{phone});
		 return list;
		
	}
	public boolean getExsitPhone(String sph){
		db = new ConnectionDB();
		 sph=sph.substring(0,3)+"****"+sph.substring(7,11);
		 System.out.println(sph);
		 String sql="select * from ah_yldd_sj where sphone = ?";
		 List<Object> list=db.excuteQuery(sql, new Object[]{sph});
		 if(list.size()>0){
			 return true;
		 }
		 return false;
	}
	public String getLimitTime(String phone) {
		try {
			db = new ConnectionDB();
			 String sql="select * from ah_yldd where phone = ? order by dtime desc";
			 List<Object> list=db.excuteQuery(sql, new Object[]{phone});
			 if(list!=null&&list.size()<3&&list.size()>0){//非第一次点赞 24小时内不可重复
				 log.info(phone+","+"已经点赞"+list.size()+"次");
				 JSONObject json = JSONObject.fromObject(list.get(0));
				 String dtime =  json.optString("dtime");
				 log.info(phone+"最近一次点赞时间："+dtime);
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		         Date date =  (Date) (sdf.parseObject(dtime)) ;
		         if(new Date().getTime()-date.getTime()<24*60*60*1000){
		        	 log.info(phone+",24小时内不可重复点赞");
		        	 return "time";
		         }else{
		        	 return "true";
		         }
				 
			 }else if(list==null||list.size()==0){//第一次点赞
				 log.info(phone+",第一次点赞");
				 return "true";
				 
			 }else {//>=3不能继续点赞最大次数
				 log.info(phone+",点赞次数已达3次");
				 return "false";
			 }
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	/**
	 * 取流量劵
	 * @return
	 */
	public String getPackages(String no){
		ConnectionDB db = new ConnectionDB();
		List<Object> list=db.excuteQuery("select * from ah_yldd_packageno  where  status!='1'order by id", null);
		JSONObject js = new JSONObject();
		if(list.size()<50){
			SendMessageUtil.sendSms("17755103782", "一路滴滴流量包剩余"+list.size()+",已不足50个。请及时补充！");
			SendMessageUtil.sendSms("17755105916", "一路滴滴流量包剩余"+list.size()+",已不足50个。请及时补充！");	
		}
		js =JSONObject.fromObject(list.get(0));
		String id = js.optString("id");
		String packno="";
		packno=	js.optString("package_no");
		db.executeUpdate("update ah_yldd_packageno set  status ='1' where id =?", new Object[]{id});
		return packno;
	} 
	/**
	 * 司机排行榜select sphone,count(*) ct  from ah_yldd group by sphone order by ct desc
	 */
	public Map<String, Object> getList(int pageSize,int pageNo){
		log.info("进入排行榜底层方法");
		if(pageNo==1){
			pageSize=9;
		}else{
			
		}
		ConnectionDB db = new ConnectionDB();
//		SELECT *
//		  FROM (SELECT a.*, ROWNUM rn
//		          FROM ( select sphone,count(*) ct  from ah_yldd group by sphone order by ct desc) a
//		         WHERE ROWNUM <= 10*1)
//		 WHERE rn >= 10*0
		List<Object> list1 = db.excuteQuery(" select count(*) counts from (select sphone,count(*) ct  from ah_yldd group by sphone )", null);
		JSONObject json = JSONObject.fromObject(list1.get(0));
		System.out.println(json.toString());
		String counts =  json.optString("counts");
		log.info("=======>counts="+counts);
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ");
		sb.append("(SELECT a.*, ROWNUM rn  FROM ");
		sb.append("( select sphone,count(*) ct  from ah_yldd group by sphone order by ct desc) a ");
		if(pageNo>1){
			sb.append("WHERE ROWNUM <="+(pageNo*pageSize-1)+")");
			sb.append("WHERE rn > "+((pageNo-1)*pageSize-1));
		}else{
			sb.append("WHERE ROWNUM <="+(pageNo*pageSize)+")");
			sb.append("WHERE rn > "+((pageNo-1)*pageSize));
		}
		
		String sql = sb.toString();
		log.info("司机排行榜分页SQL="+sql);
		List<Object> list2=db.excuteQuery(sql, null);
		JSONObject js = new JSONObject();
		JSONObject jsok = new JSONObject();
		JSONArray ja =new JSONArray();
		if(list2!=null){
			for (int i = 0; i < list2.size(); i++) {
				  js=JSONObject.fromObject(list2.get(i));
				  int rn = js.optInt("rn");
				  if(rn==1){
					  jsok.put("rn", rn);
					  jsok.put("sphone", "138****2639");
					  jsok.put("ct",550);
					  ja.add(jsok);
					  //----------------------
					  String sphone = js.optString("sphone");
					  int ct = js.optInt("ct");
					  sphone=sphone.substring(0,3)+"****"+sphone.substring(7,11);
					  jsok.put("rn", rn+1);
					  jsok.put("sphone", sphone);
					  jsok.put("ct", ct);
					  ja.add(jsok);  
				  }else if(rn>1){
					  String sphone = js.optString("sphone");
					  int ct = js.optInt("ct");
					  sphone=sphone.substring(0,3)+"****"+sphone.substring(7,11);
					  jsok.put("rn", rn+1);
					  jsok.put("sphone", sphone);
					  jsok.put("ct", ct);
					  ja.add(jsok);  
					  
				  }else{
					  String sphone = js.optString("sphone");
					  int ct = js.optInt("ct");
					  sphone=sphone.substring(0,3)+"****"+sphone.substring(7,11);
					  jsok.put("rn", rn);
					  jsok.put("sphone", sphone);
					  jsok.put("ct", ct);
					  ja.add(jsok);
				  }
				 
			}
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("counts", counts);
		map.put("list", ja);
		return map;
	}
	public void clicks(String phone){
		ConnectionDB db = new ConnectionDB();
		List<Object> list=db.excuteQuery("SELECT ah_yldd_clicks_ID.NEXTVAL FROM DUAL", null);
		JSONObject jss = JSONObject.fromObject(list.get(0));
		String id = jss.optString("nextval");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		db.executeUpdate("insert into ah_yldd_clicks values(?,?,?)", new Object[]{id,phone,sdf.format(new Date())});
	}
	/**
	 * 流量和流量券10000份 活动结束
	 * @return
	 */
	public String getAllCounts(){
		//select count(*) from ah_yldd 
		 db = new ConnectionDB();
		 String sql="select count(*) ct from ah_yldd where result!='success2' ";
		 System.out.println(sql);
		 List<Object> list=db.excuteQuery(sql, null);
		 if(list!=null&&list.size()>0){
			 JSONObject json = JSONObject.fromObject(list.get(0));
			 log.info("滴滴三周年截止目前流量已发送"+json.optString("ct")+"份");
			 return json.optString("ct");
		 }
		return "";
	}
	/**
	 * 所有点赞数
	 * @return
	 */
	public String getAllCounts2(){
		//select count(*) from ah_yldd 
		 db = new ConnectionDB();
		 String sql="select count(*) ct from ah_yldd";
		 System.out.println(sql);
		 List<Object> list=db.excuteQuery(sql, null);
		 if(list!=null&&list.size()>0){
			 JSONObject json = JSONObject.fromObject(list.get(0));
		     log.info("滴滴三周年截止目前点赞数"+json.optString("ct")+"。");
			 return (Integer.parseInt(json.optString("ct"))+550)+"";
		 }
		return "";
	}
	//点击统计
	public List<Object> getClicksList(){
		db = new ConnectionDB();
		List<Object> list = db.excuteQuery("select substr(ctime,1,10) time,count(*) ct  from ah_yldd_clicks group by substr(ctime,1,10) order by substr(ctime,1,10)", null);
		return list;
	}
	//后台点赞数据
	public List<Object> getAllData(){
		db = new ConnectionDB();
		List<Object> list = db.excuteQuery("select * from ah_yldd    order by dtime", null);
		return list;
	} 
}
