package com.myl.messages;

public class PhaseInfoMessage {

	private final PhaseInfo phaseInfo;

	public PhaseInfoMessage(String from, String to, String message, String phase) {
		this.phaseInfo = new PhaseInfo(from, to, message, phase);
	}

	public PhaseInfo getPhaseInfo() {
		return phaseInfo;
	}

	public class PhaseInfo {

		private final String from;
		private final String to;
		private final String message;
		private final String phase;

		public PhaseInfo(String from, String to, String message, String phase) {
			this.from = from;
			this.to = to;
			this.message = message;
			this.phase = phase;
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

		public String getPhase() {
			return phase;
		}
	}

}
