package com.mmzb.house.web.model;

import com.mmzb.house.web.constant.WeChatConstants;

public class ClientTextMessage extends ClientBaseMessage {
	
	public class Text {
		/**
		 * 消息内容
		 */
		public String content;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
		
	}
	
	private Text text = new Text();

	public ClientTextMessage(){
		setMsgtype(WeChatConstants.MSG_TYPE_TEXT);
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClientTextMessage [content=").append(getText().getContent()).append("]");
		return builder.toString();
	}
}
