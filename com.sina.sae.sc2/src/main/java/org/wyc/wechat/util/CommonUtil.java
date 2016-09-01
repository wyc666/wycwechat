package org.wyc.wechat.util;

import java.io.UnsupportedEncodingException;

public class CommonUtil {
	/**
	 * 返回URL的UTF-8编码格式
	 * @param URL链接
	 * @return URL的UTF-8编码格式
	 */
	public static String urlEncodeUTF8(String url) {
		String encoded = url;
		try {
			encoded = java.net.URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return encoded;
	}
}
