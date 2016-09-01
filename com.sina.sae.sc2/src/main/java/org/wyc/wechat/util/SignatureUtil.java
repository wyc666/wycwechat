package org.wyc.wechat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignatureUtil {
	private static final String TOKEN = "MineOrNothing";
	
	public static boolean isValidSignature(String signature, String timestamp, String nonce){
		boolean isValid = false;
		String sha1 = "";
		
		//对token,timestamper,nonce进行字典序排序
		String[] array = new String[]{TOKEN, timestamp, nonce};
		Arrays.sort(array);
		
		//将三个参数按字典序拼接成一个字符串
		String str = array[0].concat(array[1]).concat(array[2]);
		
		//对拼接后的字符串进行sha1加密
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(str.getBytes());
			sha1 = bytesToString(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		//判断字符串与签名是否相等
		if((!signature.equals(""))&&sha1.equals(signature))
			return true;
		else
			return false;
	}
	
	private static String bytesToString(byte[] array){
		StringBuffer buffer = new StringBuffer();
		for(byte bits: array){
			//转为16进制字符串
			String hexStr = Integer.toHexString(bits & 0xFF);
			if(hexStr.length()<2)
				buffer.append(0);
			buffer.append(hexStr);
		}
		return buffer.toString();
	}
}
