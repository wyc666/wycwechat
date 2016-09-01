package org.wyc.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.wyc.wechat.service.coreService;
import org.wyc.wechat.util.MessageUtil;
import org.wyc.wechat.util.SignatureUtil;

/**
 * Servlet implementation class testMysql
 */
@WebServlet(name = "coreServlet", urlPatterns = "/coreServlet")
public class coreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 微信服务器的token验证的本地部署
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 编码设置
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		// 将echostr回复给微信服务器
		PrintWriter writer = response.getWriter();
		if (SignatureUtil.isValidSignature(signature, timestamp, nonce)) {
			writer.write(echostr);
		}
		// writer.append("Served at: ").append(request.getContextPath());
		writer.close();
	}

	/**
	 * 对微信服务器的请求的XML文件进行解析并返回相应XML文件
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 编码设置
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		// 请求校验
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		// 加密类型
		String encrptType = request.getParameter("encrypt_type");

		PrintWriter writer = response.getWriter();
		// 当校验成功
		try {
			if (SignatureUtil.isValidSignature(signature, timestamp, nonce)) {
				HashMap<String, String> map = null;
				System.out.println(encrptType);
				// 启用了加密模式
				if (encrptType!=null && encrptType.equals("aes")) {
					map = MessageUtil.parseXMLEncrypt(request);
					for(String s:map.values())
						System.out.println(s);
					String respXML = coreService.processRequest(map);
					String respXMl = MessageUtil.encryptXML(respXML, timestamp, nonce);
					writer.write(respXMl);
				}
				// 明文模式
				else {
					map = MessageUtil.parseXML(request);
					String respXML = coreService.processRequest(map);
					writer.write(respXML);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

}
