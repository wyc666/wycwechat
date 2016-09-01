package org.wyc.wechat.service;

import java.util.HashMap;

import org.wyc.wechat.message.TextMessage;
import org.wyc.wechat.util.FacePlusPlusUtil;
import org.wyc.wechat.util.MessageUtil;
import org.wyc.wechat.util.MySQLUtil;
import org.wyc.wechat.util.TimestampUtil;

/**
 * 逻辑处理HttpRequest请求,做出响应,返回相应的XML的字符串
 *
 * @author NJU
 *
 */
public class coreService {

	public static String processRequest(HashMap<String, String> map) {
		TextMessage textMessage = null;
		// 解析微信服务器发送的请求
		// 用户的openID
		String toUserName = map.get("ToUserName");
		System.out.println(toUserName);
		// 公众号的原始ID
		String fromUserName = map.get("FromUserName");
		// 请求的消息类型
		String msgType = map.get("MsgType");
		// 消息的创建时间
		String createTime = map.get("CreateTime");
	//	long createTime = Long.parseLong(map.get("CreateTime"));

		// 对不同的消息类型进行不同的响应
		// 回复文本消息
		textMessage = new TextMessage();
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
	//	textMessage.setCreateTime(createTime);
		textMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
		// 文本消息
		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			String content = map.get("Content");
			if (content.equals("签到")) {
				if (!MySQLUtil.isSignedToday(fromUserName)) {
					if (!MySQLUtil.isAllSignedWeekly(fromUserName, TimestampUtil.getMondayOfThisWeek())) {
						MySQLUtil.saveWechatSign(fromUserName, 2);
						MySQLUtil.updateUserPoints(fromUserName, 2);
						textMessage.setContent("签到成功,获得2个积分!");
					} else {
						MySQLUtil.saveWechatSign(fromUserName, 12);
						MySQLUtil.updateUserPoints(fromUserName, 12);
						textMessage.setContent("完成一星期签到!额外获得10个积分!");
					}
				} else {
					textMessage.setContent("今天已经签到过了!");
				}
			}
		}
		// 图片消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
			String imageUrl = map.get("PicUrl");
			String respMessage = FacePlusPlusUtil.detectFace(imageUrl);
			textMessage.setContent(respMessage);
		}
		// 语音消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
			textMessage.setContent("这是一个语音消息");
		}
		// 视频消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
			textMessage.setContent("这是一个视频消息");
		}
		// 地理位置消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
			textMessage.setContent("这是一个地理位置消息");
		}
		// 链接消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
			textMessage.setContent("这是一个链接消息");
		}
		// 事件消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			// 获取事件的类型
			String eventType = map.get("Event");
			// 关注事件
			if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
				MySQLUtil.saveWechatUser(fromUserName);
				textMessage.setContent("谢谢您的关注!!!");
			}
			// 取关事件
			else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {

			}
			else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
				//判断点击了哪一个菜单
				String eventKey = map.get("EventKey");
				//判断是否点击了(签到)菜单
				if(eventKey.equals("qiandao")){
					if (!MySQLUtil.isSignedToday(fromUserName)) {
						if (!MySQLUtil.isAllSignedWeekly(fromUserName, TimestampUtil.getMondayOfThisWeek())) {
							MySQLUtil.saveWechatSign(fromUserName, 2);
							MySQLUtil.updateUserPoints(fromUserName, 2);
							textMessage.setContent("签到成功,获得2个积分!");
						} else {
							MySQLUtil.saveWechatSign(fromUserName, 12);
							MySQLUtil.updateUserPoints(fromUserName, 12);
							textMessage.setContent("完成一星期签到!额外获得10个积分!");
						}
					} else {
						textMessage.setContent("今天已经签到过了!");
					}
				}
				else{
					textMessage.setContent("别瞎几把点!");
				}
			}
		}
		return MessageUtil.messageToXML(textMessage);
	}
}
