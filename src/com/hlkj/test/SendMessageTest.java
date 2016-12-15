package com.hlkj.test;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
public class SendMessageTest {
	/***
	 * ����webservices���ж��ŷ���
	 * @param usreid �����ֻ����
	 * @param msg ����Ҫ���͵�����
	 * @return ���ؽ��
	 */
	public static String sendSms(String userid,String msg){
		String result="2";
		try {
            //ֱ������Զ�̵�wsdl�ļ�
           //���¶�����· 
			Service service = new Service();
            Call call = (Call)service.createCall();
            //127.0.0.1 ���
            call.setTargetEndpointAddress("http://61.191.44.252/sms/services/sms");//http://localhost/sms/services/sms
            call.setOperationName("sendSMGP");//WSDL���������Ľӿ����
            call.addParameter("phone", org.apache.axis.encoding.XMLType.XSD_STRING,
                          javax.xml.rpc.ParameterMode.IN);//�ӿڵĲ���
            call.addParameter("msg", org.apache.axis.encoding.XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);//�ӿڵĲ���
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//���÷�������  
            result = (String)call.invoke(new Object[]{userid,msg});
            //�����ݲ����ҵ��÷���
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
   public static void main(String[] args) {
	   System.out.println(SendMessageTest.sendSms("15385636922", "您已成功参与“流量开夜车，0元来续航”活动。参与人数达到1200人后我们将在5月23日为您开通10元1G省内闲时包，并通过短信告知您，10元翼支付返利将于5月30日前到账，请注意开通翼支付，否则无法返利。"));
}
}
