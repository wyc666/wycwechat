package org.wyc.wechat.message;

/**
 * ------视频消息------
 * @author NJU
 *
 */
public class VideoMessage extends BaseMessage{
	private Video vedio;

	public Video getVedio() {
		return vedio;
	}

	public void setVedio(Video vedio) {
		this.vedio = vedio;
	}
}
