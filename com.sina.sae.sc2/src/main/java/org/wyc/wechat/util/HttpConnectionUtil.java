package org.wyc.wechat.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionUtil {
	/**
	 * 处理HTTP GET/POST请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式(GET/POST)
	 * @param output
	 *            请求体
	 * @return 请求结果
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
		String line = null;
		StringBuffer buffer = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(requestMethod);
			// 设置当前实例使用的SSLSocketFactory
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();

			// 往服务器端写内容
			if (outputStr != null) {
				OutputStream os = conn.getOutputStream();
				os.write(outputStr.getBytes("utf-8"));
				os.close();
			}

			// 读取当前服务器返回的内容
			InputStream is = conn.getInputStream();
			InputStreamReader isReader = new InputStreamReader(is, "utf-8");
			BufferedReader brReader = new BufferedReader(isReader);
			buffer = new StringBuffer();
			while ((line = brReader.readLine()) != null) {
				buffer.append(line);
			}
			// 关闭输出流
			brReader.close();
			isReader.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
}
