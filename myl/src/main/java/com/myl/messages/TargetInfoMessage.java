package com.myl.messages;

public class TargetInfoMessage {

	private final TargetInfo targetInfo;

	public TargetInfoMessage(String from, String to, String message,
			String origen, String destino) {
		this.targetInfo = new TargetInfo(from, to, message, origen, destino);
	}

	public TargetInfo getTargetInfo() {
		return targetInfo;
	}

	public class TargetInfo {

		private final String from;
		private final String to;
		private final String message;
		private final String origen;
		private final String destino;

		public TargetInfo(String from, String to, String message,
				String origen, String destino) {
			this.from = from;
			this.to = to;
			this.message = message;
			this.origen = origen;
			this.destino = destino;
		}

		public String getFrom() {
			return from;
		}

		public String getTo() {
			return to;
		}

		public String getMessage() {
			return message;
		}

		public String getOrigen() {
			return origen;
		}

		public String getDestino() {
			return destino;
		}
	}

}
