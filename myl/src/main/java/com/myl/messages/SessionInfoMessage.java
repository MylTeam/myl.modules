package com.myl.messages;

public class SessionInfoMessage {

	public enum TYPE {
		FORMAT, OPONENT
	}

	private final SessionInfo sessionInfo;

	public SessionInfoMessage(String user, String formatOrUserTwo, TYPE type) {
		this.sessionInfo = new SessionInfo(user,formatOrUserTwo,type);
	}

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}

	public class SessionInfo {

		private final String user;
		private final String formatOrUserTwo;
		private final TYPE type;

		public SessionInfo(String user, String formatOrUserTwo,TYPE type) {
			this.user = user;
			this.formatOrUserTwo = formatOrUserTwo;
			this.type = type;
		}

		public String getUser() {
			return user;
		}
		
		public String getFormatOrUserTwo() {
			return formatOrUserTwo;
		}

		public TYPE getType() {
			return type;
		}
	}

}
