package com.hlkj.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PhoneUtils {

	/***
	 * 查询用户的本地网标识
	 * @param userid 传入手机号码
	 * @return
	 */
	public static String queryLantid(String userid){
		String lantId=null;
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("{");
			sb.append("  \"TcpCont\": {");
			sb.append("		\"ServiceCode\": \"/ServiceBus/custView/prodInst/prodInst010\",");
			sb.append(" \"ServiceContractVer\": \"000002\",");
			sb.append(" \"ActionCode\": \"abeadedf-dcd8-4f22-a035-569168006ec1\",");
			sb.append("  \"TransactionID\": \"abeadedf-dcd8-4f22-a035-569168006ec1\",");
			sb.append("  \"ServiceLevel\": \"1\",");
			sb.append("  \"SrcSysID\": \"100000\",");
			sb.append("  \"ReqTime\": \"0\"");
			sb.append("},");
			sb.append("  \"SvcCont\": {");
			sb.append(" 	\"SOO\": [{");
			sb.append("	\"PUB_REQ\": {");
			sb.append(" \"TYPE\": \"QRY_PHONE_LATN_TYPE\",");
			sb.append(" \"SVC_CONT_VER\": \"1.0\",");
			sb.append(" \"PAGE_INDEX\": 1,");
			sb.append(" \"PAGE_SIZE\": \"100\"},");
			
			
			
			sb.append(" \"PHONE_LATN_TYPE_REQ\": {");
			sb.append("\"ACC_NBR\": \""+userid+"\"");
			sb.append("},");
			sb.append(" \"RETURN_OBJECTS\": {");
			sb.append(" \"PHONE_LATN_TYPE_RES\": {");
			sb.append(" \"FIELDS\": \"\"}}");
			sb.append("}]}}");
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			String transactionId=sdf.format(new Date());
			for(int i=0;i<3;i++)transactionId+=i;
//			String url="http://134.64.110.182:9999/service/mboss/route?isRest=true&sender=LLDBZ&servCode=1001.ServiceBus_custView_prodInst_prodInst010.SynReq&msgId=LLDBZ_20140828140122002_251&transactionId="+transactionId;
			
			String url="http://134.64.116.90:9102/service/mboss/route?isRest=true&sender=LLDBZ&servCode=1001.ServiceBus_custView_prodInst_prodInst010.SynReq&msgId=LLDBZ_20140828140122002_251&transactionId="+transactionId;
			HttpClientMenu http=new HttpClientMenu(url);
			String resp=http.excute(sb.toString());
			System.out.println("queryLantid=============>"+resp);
			JSONObject jsonResp=JSONObject.fromObject(resp);
			JSONObject contractRoot=JSONObject.fromObject(jsonResp.get("ContractRoot"));
			JSONObject tcpCont=JSONObject.fromObject(contractRoot.get("TcpCont"));
			if("0".equals(tcpCont.get("RspCode"))){
				JSONObject svcCont=JSONObject.fromObject(contractRoot.get("SvcCont"));
				JSONArray sOO=JSONArray.fromObject(svcCont.get("SOO"));
				for(int i=0;i<sOO.size();i++){
					JSONObject jsonSOO=JSONObject.fromObject(sOO.get(i));
					JSONArray phone_latn_type_res=JSONArray.fromObject(jsonSOO.get("PHONE_LATN_TYPE_RES"));  
					for(int k=0;k<phone_latn_type_res.size();k++){
						 JSONObject json_lantid=JSONObject.fromObject(phone_latn_type_res.get(k));
						 lantId=json_lantid.getString("LATN_ID");
						 continue;
					}  
					
				}
			}
		} catch (Exception e) {
			System.out.println("queryLantid: 错误信息：\r\n"+e.getMessage());
		}
		return lantId;
	}
	/***
	 * 查询用户是否欠费   使用查询接口为（内部）类
	 * @param userid 手机号码
	 * @param lantid 本地网标识
	 * @return 返回true为欠费
	 */
	public static boolean queryCharge_inter(String userid,String lantid){
		boolean flag=false;
		try{
			String TransactionID=(Long)new Date().getTime()+"";
			Date date=new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String sysdate=formatter.format(date);
			JSONObject TcpCont=new JSONObject();
			TcpCont.put("ActionCode", "0");
			TcpCont.put("TransactionID",TransactionID);
			TcpCont.put("MsgId", TransactionID);
			TcpCont.put("ServiceLevel", "0");
			TcpCont.put("BusCode", "SC_IAS_STD_INTER_BIL_QRY");
			TcpCont.put("ServiceCode", "BILQRY");
			TcpCont.put("ServiceContractVer", "BILQRY");
			TcpCont.put("SrcOrgID", "100000");
			TcpCont.put("SrcSysID", "102");
			TcpCont.put("SrcSysSign", "1000000002");
			TcpCont.put("DstOrgID", "600305");
			TcpCont.put("DstSysID", "6003050001");
			TcpCont.put("ReqTime", "sysdate("+sysdate+")");
			
			
			JSONObject array=new JSONObject();
			array.put("STAFF_ID", "100002");
			array.put("ACCT_LATN_ID", lantid);
			array.put("PARATYPE", "1");
			array.put("PARAVALUE", userid);
			array.put("QRY_TYPE", "0");
			
			JSONObject SvcCont=new JSONObject();
			SvcCont.put("SOO", array);
			
			JSONObject json=new JSONObject();
			json.put("TcpCont",TcpCont);
			json.put("SvcCont", SvcCont);
			String url = "http://134.64.116.90:9102/service/mboss/route?isRest=true&sender=LLDBZ&servCode=1164.ServiceBus_qryFeeInfo001.SynReq&msgId="+TransactionID+"&transactionId="+TransactionID;
			System.out.println("============ ServiceBus_qryFeeInfo001："+json.toString());
			String result=UrlUtil.sendPost(url, json.toString());
            System.out.println("查询用户是否欠费 ======>"+result);
			System.out.println("============ ServiceBus_qryFeeInfo001："+result);

			if(result!=null && result.length()>10){
				JSONObject jsonResult=JSONObject.fromObject(result);
				JSONObject jsonContractRoot=JSONObject.fromObject(jsonResult.getString("ContractRoot"));
				JSONObject jsonTcpCont=JSONObject.fromObject(jsonContractRoot.getString("TcpCont"));
				if("0".equals(jsonTcpCont.getString("ResCode"))){
					JSONObject jsonSvcCont=JSONObject.fromObject(jsonContractRoot.getString("SvcCont"));
					JSONArray jsonSOO=JSONArray.fromObject(jsonSvcCont.getString("SOO"));
					System.out.println("返回JSON数据为："+jsonSOO.toString()+"============");

					for(int i=0;i<jsonSOO.size();i++){
						JSONObject jsonArr=JSONObject.fromObject(jsonSOO.get(i));
						//  无论预付费还是后付费，按TOTAL_HISTORY_CHARGE+TOTAL_REAL_CHARGE-TOTAL_BALANCE是否大于0，大于0认为是欠费，否则不欠费
//						String charge=jsonArr.getString("CHARGE");
						String hisCharge=jsonArr.getString("TOTAL_HISTORY_CHARGE");
						String realCharge=jsonArr.getString("TOTAL_REAL_CHARGE");
						String balance=jsonArr.getString("TOTAL_BALANCE");
						if(Integer.parseInt(hisCharge)+Integer.parseInt(realCharge)-Integer.parseInt(balance)>0){
							flag=true;
							break;
						}
						
					/*	if(Integer.parseInt(charge)>0){
							flag=true;
							break;
						}
					*/	
					}
				}
				
			}
			
		}catch(Exception ex){
			System.out.println("queryCharge_inter:"+ex.getMessage());
		}
		return flag;
	}
	/***
	 * 查询套餐能否受理
	 * @param userid 传入手机号码
	 * @return
	 */
	public static boolean checkFlowVaild(String userid,String lant,String offer_id){
		String lantId=null;
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("{");
			sb.append("  \"TcpCont\": {");
			sb.append("		\"ServiceCode\":\"/ServiceBus/custView/otherInst/otherInst022\",");
			sb.append(" \"ServiceContractVer\": \"000002\",");
			sb.append(" \"ActionCode\": \"abeadedf-dcd8-4f22-a035-569168006ec1\",");
			sb.append("  \"TransactionID\": \"abeadedf-dcd8-4f22-a035-569168006ec1\",");
			sb.append("  \"ServiceLevel\": \"1\",");
			sb.append("  \"SrcSysID\": \"100000\",");
			sb.append("  \"ReqTime\": \"0\"");
			sb.append("},");
			sb.append("  \"SvcCont\": {");
			sb.append(" 	\"SOO\": [{");
			sb.append("	\"PUB_REQ\": {");
			sb.append(" \"TYPE\": \"QRY_OPT_OFFER_ORDER_TYPE\",");
			sb.append(" \"SVC_CONT_VER\": \"1.0\",");
			sb.append(" \"PAGE_INDEX\": 1,");
			sb.append(" \"PAGE_SIZE\": \"100\"},");
			
			sb.append(" \"OPT_OFFER_TYPE_REQ\": {");
			sb.append("\"PROD_INST_ID\": \":getProdInstId('"+userid+"','1','','"+lant+"')\",");
			sb.append("\"PRE_ORDER_OFFER_IDS\":\"\",");
			sb.append("\"TARGET_OFFER_ID\":\""+offer_id+"\",");
//			sb.append("\"TARGET_OFFER_ID\":\"800469293\",");

			sb.append("\"LATN_ID\":\""+lant+"\"");

			sb.append("},");
			sb.append(" \"RETURN_OBJECTS\": {");
			sb.append(" \"OPT_OFFER_TYPE_RES\": {");
			sb.append(" \"FIELDS\": \"\"}}");
			sb.append("}]}}");
			//'18155029996'
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			String transactionId=sdf.format(new Date());
			for(int i=0;i<3;i++)transactionId+=i;
			
//			String url="http://134.64.116.90:9102/service/mboss/route?isRest=true&sender=DPI&servCode=1001.ServiceBus_custView_otherInst_otherInst022.SynReq&msgId=DPI_20151208140122002_251&transactionId="+transactionId;
			String url="http://134.64.116.90:9991/service/mboss/route?isRest=true&sender=DPI&servCode=1001.ServiceBus_custView_otherInst_otherInst022.SynReq&msgId=DPI_20151208140122002_251&transactionId="+transactionId;

			System.out.println("=====================" + sb.toString());

			HttpClientMenu http=new HttpClientMenu(url);
			String resp=http.excute(sb.toString());
			 
			System.out.println("查询套餐能否受理=====================>" + resp);

			JSONObject jsonResp=JSONObject.fromObject(resp);
			
			JSONObject contractRoot=JSONObject.fromObject(jsonResp.get("ContractRoot"));
			JSONObject tcpCont=JSONObject.fromObject(contractRoot.get("TcpCont"));
			if("0".equals(tcpCont.get("RspCode"))){
				JSONObject svcCont=JSONObject.fromObject(contractRoot.get("SvcCont"));
				JSONArray sOO=JSONArray.fromObject(svcCont.get("SOO"));
				for(int i=0;i<sOO.size();i++){
					JSONObject jsonSOO=JSONObject.fromObject(sOO.get(i));
					JSONArray phone_latn_type_res=JSONArray.fromObject(jsonSOO.get("OPT_OFFER_TYPE_RES"));  
					for(int k=0;k<phone_latn_type_res.size();k++){
						 JSONObject json_lantid=JSONObject.fromObject(phone_latn_type_res.get(k));
						 lantId=json_lantid.getString("RETURN_CODE");
						 continue;
					}  
				}
			}
		} catch (Exception e) {
			System.out.println("queryLantid: 错误信息：\r\n"+e.getMessage());
		}
		return "0".equals(lantId);
	}
	public static void main(String[] args) {
		
		System.out.println(PhoneUtils.queryLantid("17755105916"));
	}
}
