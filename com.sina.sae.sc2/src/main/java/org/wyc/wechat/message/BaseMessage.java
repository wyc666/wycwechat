package org.wyc.wechat.message;

public class BaseMessage {
	//-------变量名大写与微信服务器端保持一致-------
	//接收方账号(用户的OpenID)
	private String ToUserName;
	//发送方账号(公众号的原始ID)
	private String FromUserName;
	//消息创建时间
	private long CreateTime;
	//消息的类型
	private String MsgType;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
}
