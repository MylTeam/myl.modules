package com.myl.messages;

public class ChatInfoMessage {

    private final ChatInfo chatInfo;

    public ChatInfoMessage(String from, String message) {
        this.chatInfo = new ChatInfo(from, message);
    }

    public ChatInfo getChatInfo() {
        return chatInfo;
    }

    public class ChatInfo {

        private final String from;
        private final String message;

        public ChatInfo(String from, String message) {
            this.from = from;
            this.message = message;
        }

        public String getFrom() {
            return from;
        }

        public String getMessage() {
            return message;
        }
    }

}
