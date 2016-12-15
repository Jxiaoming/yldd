package com.hlkj.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



public class AES {
	/*
	 * �����õ�Key ������26����ĸ��������ɣ���ò�Ҫ�ñ����ַ�����Ȼ�����������ô�þ������˿��������
	 * �˴�ʹ��AES-128-CBC����ģʽ��key��ҪΪ16λ��
	 */
	private static final String SKEY = "Hlkj@~_^&&123_78";

	// ʹ��CBCģʽ����Ҫһ������iv�������Ӽ����㷨��ǿ��
	private static final String IVSTR = "0201080306050704";

	// ����
	public static String Encrypt( String sSrc ){
		try{
			if(sSrc==null || sSrc.length()<2){
				return null;
			}
			if( SKEY == null ) {
				System.out.print( "KeyΪ��null" );
				return null;
			}
			// �ж�Key�Ƿ�Ϊ16λ
			if( SKEY.length() != 16 ) {
				System.out.print( "Key���Ȳ���16λ" );
				return null;
			}
			byte[] raw = SKEY.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec( raw, "AES" );
			Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5Padding" );// "�㷨/ģʽ/���뷽ʽ"
			IvParameterSpec iv = new IvParameterSpec( IVSTR.getBytes() );// ʹ��CBCģʽ����Ҫһ������iv�������Ӽ����㷨��ǿ��
			cipher.init( Cipher.ENCRYPT_MODE, skeySpec, iv );
			byte[] encrypted = cipher.doFinal( sSrc.getBytes() );

			return ObjectSerializer.encodeBytes( encrypted );
		}catch(Exception ex){
			return null;
		}
		
	}

	// ����
	public static String Decrypt( String sSrc){
		try {
			// �ж�Key�Ƿ���ȷ
			if(sSrc==null || sSrc.length()<16){
				return null;
			}
			if( SKEY == null ) {
				System.out.print( "KeyΪ��null" );
				return null;
			}
			// �ж�Key�Ƿ�Ϊ16λ
			if( SKEY.length() != 16 ) {
				System.out.print( "Key���Ȳ���16λ" );
				return null;
			}
			byte[] raw = SKEY.getBytes( "ASCII" );
			SecretKeySpec skeySpec = new SecretKeySpec( raw, "AES" );
			Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5Padding" );
			IvParameterSpec iv = new IvParameterSpec( IVSTR.getBytes() );
			cipher.init( Cipher.DECRYPT_MODE, skeySpec, iv );

			byte[] encrypted1 = ObjectSerializer.decodeBytes( sSrc );
			try {
				byte[] original = cipher.doFinal( encrypted1 );
				String originalString = new String( original );
				return originalString;
			}
			catch( Exception e ) {
				System.out.println( e.toString() );
				return null;
			}
		}
		catch(Exception ex ) {
			return null;
		}
	}
	// ����
			public static String EncryptActivity( String sSrc ){
				try{
					if(sSrc==null || sSrc.length()<2){
						return null;
					}
					
					byte[] raw = "AhTelecomChina@2".getBytes();
					SecretKeySpec skeySpec = new SecretKeySpec( raw, "AES" );
					Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5Padding" );// "�㷨/ģʽ/���뷽ʽ"
					IvParameterSpec iv = new IvParameterSpec( "7201084316056726".getBytes() );// ʹ��CBCģʽ����Ҫһ������iv�������Ӽ����㷨��ǿ��
					cipher.init( Cipher.ENCRYPT_MODE, skeySpec, iv );
					byte[] encrypted = cipher.doFinal( sSrc.getBytes() );
					return ObjectSerializer.encodeBytes( encrypted );
				}catch(Exception ex){
					return null;
				}
				
			}

}
