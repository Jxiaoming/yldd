package com.hlkj.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hlkj.service.Ddservice;
import com.hlkj.util.AES;
import com.hlkj.util.ConnectionDB;
import com.hlkj.util.ConnectionUrlUtil;
import com.hlkj.util.HttpUtil;
import com.hlkj.util.IPUtils;
import com.hlkj.util.Md5Util;
import com.hlkj.util.Order;
import com.hlkj.util.PhoneOfHefeiUtil;
import com.hlkj.util.SendMessageUtil;
import com.hlkj.util.UserUtil;


@Controller
public class IndexContorller {
	
	private Logger log=Logger.getRootLogger();
	private Ddservice zc = new Ddservice();
	@RequestMapping(value="/index.htm")
	public String index(HttpServletRequest request,Model model) throws UnsupportedEncodingException{  
		//String isp=IPUtils.getIpAddr(request);
		//model.addAttribute("isp", isp);
		return "index";
	}
	@RequestMapping(value="/list.htm")
	public String login(){  
		return "list";
	}
	@RequestMapping(value="/clickCounts")
	public String clickCounts(){  
		return "counts";
	} 
	@RequestMapping(value="/counts.htm")
	public String counts2(){  
		return "counts2";
	} 
	@RequestMapping(value="/send")
	public @ResponseBody String sendMsg(String phone,HttpServletRequest request,HttpServletResponse response) throws IOException{
		 response.setContentType("text/plain;charset=utf-8");
		 response.setCharacterEncoding("UTF-8");
		 PrintWriter out = response.getWriter(); 
		 
		 if(isMobile(phone)){
			 
		 }else{
			out.print("请输入正确的手机号码");
	        out.flush();
	        out.close();
	        return null;
		 }
		 
		//判断合肥电信号码
			String result = PhoneOfHefeiUtil.getLanId(phone);
	   if(!"551".equals(result)){
			out.print("您的手机号码非合肥电信用户！请直接输入验证码8888验证");
	        out.flush();
	        out.close();
	        return null;
		} 

		String msg = "";//6位随机验证码
		for (int i = 0; i < 4; i++) {
			 msg += (int)Math.floor(Math.random()*10);
		}
        if(zc.checkCode(phone, msg)){
    	  log.info("手机:"+phone+",验证码："+msg);
			String str = "本次验证码为："+msg+",如非本人操作请忽略（有效时间5分钟)";
			String num="";
	 	    num =SendMessageUtil.sendSms(phone, str);
//			ServletContext application = request.getSession().getServletContext(); 
//			application.setAttribute("msg"+phone, msg);
//			application.setAttribute("time"+phone, new Date());
			System.out.println("验证码发送结果："+num);
			if("0".equals(num.trim())){
				out.print("验证码发送成功！");
		        out.flush();
		        out.close();
		        zc.insertCode(phone, msg);
			}else if("1".equals(num.trim())){
				out.print("请勿重复发送，1分钟后再来！");
		        out.flush();
		        out.close();
			}else{
				out.print("验证码发送失败！");
		        out.flush();
		        out.close();
			}
	        return null;
		}else{
	        out.print("今日发送次数已上限！");
	        out.flush();
	        out.close();
	        return null;
		}
			
	 
		
	} 
	private static  HashMap<String,String> orderFlowCache	=  new HashMap<String,String>(10);
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/sumbit")
	public  @ResponseBody  String smbmit(HttpServletRequest request,String ph,String code,String sph,HttpServletResponse response) throws IOException{
		response.setContentType("text/plain;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter(); 
		if(orderFlowCache.size()>9){
			orderFlowCache.clear();
		}
		if(orderFlowCache.get(ph)!=null){
			System.out.println(ph+"=====点赞重复请求....");
			out.print("您的手速过快，休息一下再来把！");
	        out.flush();
	        out.close();
	        return null;
		}else{
			orderFlowCache.put(ph, "1");
		}
		
		ServletContext application = request.getSession().getServletContext(); 
		
		HttpSession session = request.getSession();
//		Date pre = (Date)application.getAttribute("time"+ph);
//		String appApplication =(String) application.getAttribute("msg"+ph);
		System.out.println("登录手机："+ph+",code="+code+"司机号码=="+sph);
		if(isMobile(ph)){
			 
		 }else{
			out.print("请输入正确的手机号码");
	        out.flush();
	        out.close();
	        orderFlowCache.remove(ph);
	        return null;
		 }
		String str = zc.getAllCounts();
		if(Integer.parseInt(str)>100){
			log.info(ph+",活动已结束！");
			out.print("活动已结束，更多活动敬请期待！");
	        out.flush();
	        out.close();
	        orderFlowCache.remove(ph);
	        return null;
		}
		String result2 = PhoneOfHefeiUtil.getLanId(ph);
		if(!"551".equals(result2)){//非电信用户
			log.info("非合肥电信用户访问"+ph);
			//out.print("您的手机号码非合肥电信用户！");
	        if(!"8888".equals(code)){
	        	out.print("验证码错误！");
		        out.flush();
		        out.close();
		        orderFlowCache.remove(ph);
		        return null;
	        }else{
	        	if(!zc.getExsitPhone(sph)){
					out.print("您输入的司机号码尚未在合肥滴滴服务站登记，请您关注“合小滴”微信公众号参与抽奖领取其他礼品！");
			        out.flush();
			        out.close();
			        orderFlowCache.remove(ph);
			        return null;
				}
				//三个判断。。点赞3次和流量包3次  24小时内不能点赞
				String san = zc.getLimitTime(ph);
				if(san.equals("time")){
					out.print("24小时内不要重复点赞！");
			        out.flush();
			        out.close();
			        orderFlowCache.remove(ph);
			        return null;
				}else if(san.equals("false")){
					out.print("您的点赞次数已上限！");
			        out.flush();
			        out.close();
			        orderFlowCache.remove(ph);
			        return null;
				}else if(san.equals("error")){
					log.info(ph+",数据库连接异常！");
					out.print("服务器异常，请稍后再试！");
			        out.flush();
			        out.close();
			        orderFlowCache.remove(ph);
			        return null;
				}else{
	                   //非电信用户直接提示领奖
						zc.insert(ph, sph, "success2", "");
						log.info("恭喜"+ph+"点赞成功！");
				        session.setAttribute("phone", AES.Encrypt(ph));
						out.print("success2");
				        out.flush();
				        out.close();
				        orderFlowCache.remove(ph);
				        return null;
				}	 
	        }
		}else{//电信用户
			log.info("合肥电信用户访问"+ph);
			String appApplication="";
			Date pre =null;
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Object> list = zc.getCodeInfo(ph);
			if(list==null||list.size()<1){
				out.print("验证码错误！");
		        out.flush();
		        out.close();
		        orderFlowCache.remove(ph);
		        return null;
			}else{
				JSONObject json =JSONObject.fromObject(list.get(0));
				appApplication = json.optString("scode");
			    try {
					pre = sdf.parse(json.optString("stime"));
				} catch (ParseException e) {
					 e.printStackTrace();
				}
			}
			log.info("=====数据库取验证码："+appApplication+",时间"+sdf.format(pre));
			
			if(code!=null&&!code.equals(appApplication)){
				out.print("验证码错误！");
		        out.flush();
		        out.close();
		        orderFlowCache.remove(ph);
		        return null;
			} 
			else if(pre!=null&&new Date().getTime()-pre.getTime()>300000){   //短信验证码5分钟过期
				out.print("验证码已过期！");
		        out.flush();
		        out.close();
		        orderFlowCache.remove(ph);
		        return null;
			}
			else{
				String result = PhoneOfHefeiUtil.getLanId(ph);
				if(!"551".equals(result)){
					out.print("您的手机号码非合肥电信用户！");
			        out.flush();
			        out.close();
			        orderFlowCache.remove(ph);
			        return null;
				}
				if(!zc.getExsitPhone(sph)){
					out.print("您输入的司机号码尚未在合肥滴滴服务站登记，请您关注“合小滴”微信公众号参与抽奖领取其他礼品！");
			        out.flush();
			        out.close();
			        orderFlowCache.remove(ph);
			        return null;
				}
				//三个判断。。点赞3次和流量包3次  24小时内不能点赞
				String san = zc.getLimitTime(ph);
				if(san.equals("time")){
					out.print("24小时内不要重复点赞！");
			        out.flush();
			        out.close();
			        orderFlowCache.remove(ph);
			        return null;
				}else if(san.equals("false")){
					out.print("您的点赞次数已上限！");
			        out.flush();
			        out.close();
			        orderFlowCache.remove(ph);
			        return null;
				}else if(san.equals("error")){
					log.info(ph+",数据库连接异常！");
					out.print("服务器异常，请稍后再试！");
			        out.flush();
			        out.close();
			        orderFlowCache.remove(ph);
			        return null;
				}else{
					//开流量包 发短信 保存入库
					String order = Order.order100(ph);
					if(order.equals("")){
						log.info(ph+"流量包开通异常！");
						out.print("服务器异常，请稍后再试！");
				        out.flush();
				        out.close();
				        orderFlowCache.remove(ph);
				        return null;
					}else if(!order.equals("success")){//流量包开通失败
						//取流量券 等待。。
						
						
						String ss = zc.getPackages();
						if(!ss.equals("")){
							zc.insert(ph, sph,ss,"等待补券");
							log.info("恭喜"+ph+"点赞成功！,但100M流量包开通失败，系统自动发送流量券:"+ss);
							session.setAttribute("phone", AES.Encrypt(ph));
							out.print("success2"+","+ss);
					        out.flush();
					        out.close();
					        orderFlowCache.remove(ph);
					        return null;
						}else{
							log.info(ph+",数据库连接异常！getPackages");
							out.print("服务器异常，请稍后再试！");
					        out.flush();
					        out.close();
					        orderFlowCache.remove(ph);
					        return null;
						}
						
					}else{//流量包开通 成功
						zc.insert(ph, sph, "success", "");
						log.info("恭喜"+ph+"点赞成功！");
				        session.setAttribute("phone", AES.Encrypt(ph));
						out.print("success");
				        out.flush();
				        out.close();
				        orderFlowCache.remove(ph);
				        return null;
					}
				}	 
			}
		}
		
		
		
		
		
		
		
		
		
		
	
	}
	@RequestMapping(value="/gengxin")
	public  @ResponseBody String ys(String ph,String ss){
    	try {
			Thread.sleep(60*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	zc.update(ss,SendMessageUtil.sendSms(ph, "感谢您参与“合肥滴滴三周年，感恩送好礼”活动，为您奉上100M省内流量劵1张，流量兑换码："+ss+"，兑换地址:v.ah-3g.cn/paihb/allDh.jsp。【安徽电信】"),ph);
    	return "1";
    }
	@RequestMapping(value="/queryList")
	public  @ResponseBody  String queryList(int pageSize,int pageNo){
		 log.info("pageNo="+pageNo);
		 Map<String, Object> map =zc.getList(pageSize, pageNo);
		 
		 JSONObject js = new JSONObject();
		 js.put("map", map);
		 return js.toString();	
	}
	@RequestMapping(value="/clicks")
	public  @ResponseBody  String click(String ph,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		 log.info("点击统计----->"+ph);
		 zc.clicks(ph);
		 return null;	
	}
	
	@RequestMapping(value="/getAllCounts")
	public  @ResponseBody  String getAll(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/plain;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter(); 
		String str = zc.getAllCounts2();
		out.print(str);
        out.flush();
        out.close();
        return null;	
	}
	public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }  
	@RequestMapping(value="/queryClicks")
	public  @ResponseBody  String queryClick(String ph,HttpServletRequest request,HttpServletResponse response) throws IOException{
		 log.info("点击统计----->"+ph);
		List<Object> list =  zc.getClicksList();
		PrintWriter out = response.getWriter();
		JSONObject js = new JSONObject();
		 js.put("list", list);
		 out.print(js);
		 out.flush();
		 out.close(); 
		 return null;	
	}
	@RequestMapping(value="/queryDataList")
	public  @ResponseBody  String queryData(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<Object> list =  zc.getAllData();
		PrintWriter out = response.getWriter();
		JSONObject js = new JSONObject();
		 js.put("list", list);
		 out.print(js);
		 out.flush();
		 out.close(); 
		 return null;	
	}
	@RequestMapping(value="/export")
	public  void export(String ph,HttpServletRequest request,HttpServletResponse response) throws IOException{
		 ConnectionDB db = new ConnectionDB();
		List<Object> list = db.excuteQuery("select * from ah_yldd    order by dtime",null);
		  
//		 List<Object> list = db.excuteQuery("select * from orderRed",null);
		 ServletOutputStream out = null;
	     try{
	         //设置输出xls的头信息
	         response.setContentType("text/xls");
	         String disposition = "attachment; fileName=userinfo.xls";
	         response.setHeader("Content-Disposition", disposition);
	         //获得输出对象
	         out = response.getOutputStream();
	         HSSFWorkbook wb = compareExcelinfo(list);
	         wb.write(out);  
	         out.flush();  
	         out.close();  
	     }catch(Exception e){
	         e.printStackTrace();
	     }
	}
	private HSSFWorkbook compareExcelinfo(List<Object> list){
	    String[] excelHeader = {"手机", "司机号码", "点赞时间","结果","短信"};
		HSSFWorkbook wb = new HSSFWorkbook();  
		 HSSFSheet sheet = wb.createSheet("userinfo");   
	        HSSFRow row = sheet.createRow((int) 0);  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        for (int i = 0; i < excelHeader.length; i++) {  
	            HSSFCell cell = row.createCell((short) i);  
	            cell.setCellValue(excelHeader[i]);  
	            cell.setCellStyle(style);             
	        }
	        for (int i = 0; i < list.size(); i++) {  
	        	JSONObject js = JSONObject.fromObject(list.get(i));
	            row = sheet.createRow(i + 1);  
	            String ss = js.optString("result");
	            if(ss.equals("success")){
			        ss="电信号码";
			      }else if(ss.equals("success2")){
			         ss="移动/联通";
			      }else{
			         ss="电信号码(补券)";
			      }
	            row.createCell((short) 0).setCellValue(js.optString("phone"));  
	            row.createCell((short) 1).setCellValue(js.optString("sphone"));  
	            row.createCell((short) 2).setCellValue(js.optString("dtime"));  
	            row.createCell((short) 3).setCellValue(ss);  
	            row.createCell((short) 4).setCellValue(js.optString("msg"));
	            
	            sheet.autoSizeColumn((short) i); 
	        }   
		return wb;
	}
}
