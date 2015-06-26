package com.myl.messages;

import com.myl.modelo.Carta;

public class CardInfoMessage {

	private final CardInfo cardInfo;

	public CardInfoMessage(String from, String to, String message, Carta carta,
			String origen, String destino) {
		this.cardInfo = new CardInfo(from, to, message, carta, origen, destino);
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public class CardInfo {

		private final String from;
		private final String to;
		private final String message;
		private final Carta carta;
		private final String origen;
		private final String destino;

		public CardInfo(String from, String to, String message, Carta carta,
				String origen, String destino) {
			this.from = from;
			this.to = to;
			this.message = message;
			this.carta = carta;
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

		public Carta getCarta() {
			return carta;
		}

		public String getOrigen() {
			return origen;
		}

		public String getDestino() {
			return destino;
		}
	}
}