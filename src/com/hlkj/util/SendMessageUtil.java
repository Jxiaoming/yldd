package com.hlkj.util;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
public class SendMessageUtil {
	/***
	 * 调用webservices进行短信发送
	 * @param usreid 传入手机号码
	 * @param msg 传入要发送的内容
	 * @return 返回结果
	 */
	public static String sendSms(String userid,String msg){
		String result="2";
		try {
            //直接引用远程的wsdl文件
           //以下都是套路 
			Service service = new Service();
            Call call = (Call)service.createCall();
            //127.0.0.1 生产//公网61.191.44.252  内网172.16.7.242
            call.setTargetEndpointAddress("http://172.16.7.242/sms/services/sms");//http://localhost/sms/services/sms
            call.setOperationName("sendSMGP");//WSDL里面描述的接口名称
            call.addParameter("phone", org.apache.axis.encoding.XMLType.XSD_STRING,
                          javax.xml.rpc.ParameterMode.IN);//接口的参数
            call.addParameter("msg", org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);//接口的参数
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型  
            result = (String)call.invoke(new Object[]{userid,msg});
            //给方法传递参数，并且调用方法
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
   public static void main(String[] args) {
	   System.out.println(SendMessageUtil.sendSms("17705697364", "本次验证码为"));
}
}
