package org.wyc.wechat.message;

/**
 * ------图片消息------
 * @author NJU
 *
 */
public class ImageMessage extends BaseMessage{
	private Image image;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
