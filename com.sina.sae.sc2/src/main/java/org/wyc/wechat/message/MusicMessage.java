package org.wyc.wechat.message;

/**
 * ------音乐消息------
 * @author NJU
 *
 */
public class MusicMessage extends BaseMessage{
	private Music music;

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}
}
