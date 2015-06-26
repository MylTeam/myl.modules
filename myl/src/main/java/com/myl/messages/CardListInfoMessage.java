package com.myl.messages;

import java.util.List;

import com.myl.modelo.Carta;

public class CardListInfoMessage {

    private final CardListInfo cardListInfo;

    public CardListInfoMessage(String from, String to, String message,List<Carta> cartas,String origen) {
        this.cardListInfo = new CardListInfo(from, to, message,cartas,origen);
    }

    public CardListInfo getCardListInfo() {
        return cardListInfo;
    }

    public class CardListInfo {

        private final String from;
        private final String to;
        private final String message;
        private final List<Carta> cartas;
        private final String origen;

        public CardListInfo(String from, String to, String message,List<Carta> cartas,String origen) {
            this.from = from;
            this.to = to;
            this.message = message;
            this.cartas = cartas;
            this.origen=origen;
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
        
        public List<Carta> getCartas(){
        	return cartas;
        }
        
        public String getOrigen(){
        	return origen;
        }
                
    }

}
