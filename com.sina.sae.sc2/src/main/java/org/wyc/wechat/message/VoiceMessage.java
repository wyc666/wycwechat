package org.wyc.wechat.message;

/**
 * ------语音消息------
 * @author NJU
 *
 */
public class VoiceMessage extends BaseMessage{
	private Voice voice;

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}
	
}
