package org.wyc.wechat.message;

/**
 * ------文本消息-------
 * @author NJU
 *
 */
public class TextMessage extends BaseMessage{
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
}
