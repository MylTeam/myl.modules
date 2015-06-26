package com.myl.messages;

public class StatusInfoMessage {

	public enum STATUS {
		CONNECTED, DISCONNECTED
	}

	private final StatusInfo statusInfo;

	public StatusInfoMessage(String user, String format, STATUS status) {
		this.statusInfo = new StatusInfo(user,format,status);
	}

	public StatusInfo getStatusInfo() {
		return statusInfo;
	}

	public class StatusInfo {

		private final String user;
		private final String format;
		private final STATUS status;

		public StatusInfo(String user, String format,STATUS status) {
			this.user = user;
			this.format = format;
			this.status = status;
		}

		public String getUser() {
			return user;
		}
		
		public String getFormat() {
			return format;
		}

		public STATUS getStatus() {
			return status;
		}
	}

}
