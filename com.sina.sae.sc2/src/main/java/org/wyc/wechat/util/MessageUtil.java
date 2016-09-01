package org.wyc.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.wyc.wechat.message.Article;
import org.wyc.wechat.message.ImageMessage;
import org.wyc.wechat.message.MusicMessage;
import org.wyc.wechat.message.NewsMessage;
import org.wyc.wechat.message.TextMessage;
import org.wyc.wechat.message.VideoMessage;
import org.wyc.wechat.message.VoiceMessage;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * ------消息类型定义------
 * 
 * @author NJU
 *
 */
public class MessageUtil {
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	public static final String REQ_MESSAGE_TYPE_LINK = "link";
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 解析HttpRequest请求的XML文件
	 * 
	 * @param request--HttpRequest请求
	 * @return 解析过后的信息列表
	 * @throws IOException
	 * @throws DocumentException
	 * @throws Exception
	 *             输入流异常
	 */
	public static HashMap<String, String> parseXML(HttpServletRequest request) throws DocumentException, IOException {
		HashMap<String, String> map = new HashMap<>();
		// 从HttpRequest请求获取输入流
		SAXReader reader = new SAXReader();
		Document doc = reader.read(request.getInputStream());
		// 得到XML文件的根节点
		Element root = doc.getRootElement();
		// 解析XML文件
		recursiveParseXML(root, map);

		return map;
	}

	/**
	 * 解析HttpRequest请求的XML文件
	 * 
	 * @param request--HttpRequest请求
	 * @return 解析过后的信息列表
	 * @throws IOException
	 * @throws DocumentException
	 * @throws Exception
	 *             输入流异常
	 */
	public static HashMap<String, String> parseXMLEncrypt(HttpServletRequest request)
			throws DocumentException, IOException {
		HashMap<String, String> map = new HashMap<>();
		String xml = decryptXML(request);
		// 解析xml
		Document doc = DocumentHelper.parseText(xml);
		// 得到XML文件的根节点
		Element root = doc.getRootElement();
		// 解析XML文件
		recursiveParseXML(root, map);
		return map;
	}

	/**
	 * 递归遍历XML文件获取所有内容
	 * 
	 * @param root--需要递归的根节点(判断下面是否有子节点)
	 * @param map--XML文件的信息列表
	 */
	private static void recursiveParseXML(Element root, HashMap<String, String> map) {
		// 得到根节点的子节点列表
		List<Element> elements = root.elements();

		// 判断子节点下面是否存在次子节点
		if (elements.isEmpty()) {
			map.put(root.getName(), root.getTextTrim());
		} else {
			// 遍历子节点列表
			for (Element element : elements)
				recursiveParseXML(element, map);
		}
	}

	/**
	 * 解析经过微信加密的XML文档
	 * 
	 * @param request Http请求,包含所需要的XML文档
	 * @return 解密后的XML文档
	 * @throws IOException
	 */
	private static String decryptXML(HttpServletRequest request) throws IOException {
		// 获取XML文本
		InputStream is = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer buffer = new StringBuffer();
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}
		br.close();
		is.close();
		String xml = buffer.toString();
		System.out.println(xml);
		WXBizMsgCrypt pc = null;
		String str = null;
		// 解密
		try {
			pc = new WXBizMsgCrypt("MineOrNothing", "cIrzSnQMO0ET46V1JnhDk560ccKSuKDzA4i5aKEkNWy",
					"wx6e068c4b65f00267");
		} catch (AesException e) {
			e.printStackTrace();
		}
		String signature = request.getParameter("msg_signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		try {
			str = pc.decryptMsg(signature, timestamp, nonce, xml);
		} catch (AesException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String encryptXML(String xml,String timestamp,String nonce){
		WXBizMsgCrypt pc = null;
		String str = null;
		// 解密
		try {
			pc = new WXBizMsgCrypt("MineOrNothing", "cIrzSnQMO0ET46V1JnhDk560ccKSuKDzA4i5aKEkNWy",
					"wx6e068c4b65f00267");
		} catch (AesException e) {
			e.printStackTrace();
		}
		try {
			str = pc.encryptMsg(xml, timestamp, nonce);
		} catch (AesException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 修改xStream以支持CDATA标记
	 */
	private static XStream xStream = new XStream(new XppDriver()) {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点都增加CDATA标记
				boolean cdata = true;

				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	};

	public static String messageToXML(TextMessage textMessage) {
		xStream.alias("xml", TextMessage.class);
		return xStream.toXML(textMessage);
	}

	public static String messageToXML(ImageMessage imageMessage) {
		xStream.alias("xml", ImageMessage.class);
		return xStream.toXML(imageMessage);
	}

	public static String messageToXML(MusicMessage musicMessage) {
		xStream.alias("xml", MusicMessage.class);
		return xStream.toXML(musicMessage);
	}

	public static String messageToXML(NewsMessage newsMessage) {
		xStream.alias("xml", NewsMessage.class);
		xStream.alias("item", Article.class);
		return xStream.toXML(newsMessage);
	}

	public static String messageToXML(VideoMessage videoMessage) {
		xStream.alias("xml", VideoMessage.class);
		return xStream.toXML(videoMessage);
	}

	public static String messageToXML(VoiceMessage voiceMessage) {
		xStream.alias("xml", VoiceMessage.class);
		return xStream.toXML(voiceMessage);
	}
}
