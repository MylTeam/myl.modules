package com.myl.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.myl.messages.CardInfoMessage;
import com.myl.messages.CardListInfoMessage;
import com.myl.messages.ConnectionInfoMessage;
import com.myl.messages.MessageInfoMessage;
import com.myl.messages.PhaseInfoMessage;
import com.myl.messages.StatusInfoMessage;
import com.myl.messages.TargetInfoMessage;
import com.opensymphony.xwork2.ActionContext;

//@WebServlet(urlPatterns = "/chat")
public class WebSocketCharServlet extends WebSocketServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketCharServlet.class);

    private static final Map<String, ChatConnection> CONNECTIONS = new HashMap<String, ChatConnection>();
        
    
    @Override
    protected boolean verifyOrigin(String origin) {
        return true;
    }

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
        final String connectionId = request.getSession().getId();
        final String userName = request.getParameter("userName");
        final String userNameTwo = request.getParameter("userNameTwo");
        final String format = request.getParameter("format");        
        return new ChatConnection(connectionId,format, userName, userNameTwo);
    }

    private static class ChatConnection extends MessageInbound {

        private final String connectionId;
        private final String userName;
        private final String format;
        private final String userNameTwo;
        private final Gson jsonProcessor;
        
        private String key;

        private ChatConnection(String connectionId, String format, String userName, String userNameTwo) {
            this.connectionId = connectionId;
            this.userName = userName;
            this.userNameTwo = userNameTwo;
            this.format= format;
            this.jsonProcessor = new Gson();

        }

        @Override
        protected void onOpen(WsOutbound outbound) {
        	LOGGER.info("Abriendo la conexión: "+userName);
            sendConnectionInfo(outbound);
            sendStatusInfoToOponent(new StatusInfoMessage(userName,format, StatusInfoMessage.STATUS.CONNECTED));
            CONNECTIONS.put(connectionId, this);
        }

        @Override
        protected void onClose(int status) {
        	LOGGER.info("Cerrando la conexión, status: "+status+" connectionId: "+connectionId);
        	if(status==1000){
        		sendStatusInfoToOponent(new StatusInfoMessage(userName,format, StatusInfoMessage.STATUS.DISCONNECTED));
        	}
        		CONNECTIONS.remove(connectionId);
        }

        @Override
        protected void onBinaryMessage(ByteBuffer byteBuffer) throws IOException {
            throw new UnsupportedOperationException("Binary messages not supported");
        }

        @Override
        protected void onTextMessage(CharBuffer charBuffer) throws IOException {
        	String to = "";
        	
        	if(charBuffer.toString().contains("messageInfo")){
        		LOGGER.info(charBuffer.toString());
        		final MessageInfoMessage message = jsonProcessor.fromJson(charBuffer.toString(), MessageInfoMessage.class);
        		to=message.getMessageInfo().getTo();
        		sendMessage(to, message);
        		
        	}else if(charBuffer.toString().contains("cardInfo")){
        		final CardInfoMessage message = jsonProcessor.fromJson(charBuffer.toString(), CardInfoMessage.class);
        		sendMessage(message.getCardInfo().getTo(), message);
        		
        	}else if(charBuffer.toString().contains("cardListInfo")){
        		final CardListInfoMessage message = jsonProcessor.fromJson(charBuffer.toString(), CardListInfoMessage.class);
        		to=message.getCardListInfo().getTo();
        		sendMessage(to, message);
        		
        	}else if(charBuffer.toString().contains("targetInfo")){
        		final TargetInfoMessage message = jsonProcessor.fromJson(charBuffer.toString(), TargetInfoMessage.class);
        		to=message.getTargetInfo().getTo();
        		sendMessage(to, message);
        		
        	}else if(charBuffer.toString().contains("phaseInfo")){
        		final PhaseInfoMessage message = jsonProcessor.fromJson(charBuffer.toString(), PhaseInfoMessage.class);
        		to=message.getPhaseInfo().getTo();
        		sendMessage(to, message);
        		
        	}

        }


        public String getUserName() {
            return userName;
        }
        
        public String getUserNameTwo() {
            return userNameTwo;
        }
        
        public String getFormat() {
            return format;
        }

        private void sendConnectionInfo(WsOutbound outbound) {
            final List<String> activeUsers = getActiveUsers();
            final List<String> formats = getFormats();
            final ConnectionInfoMessage connectionInfoMessage = new ConnectionInfoMessage(userName, activeUsers,formats);
            try {
                outbound.writeTextMessage(CharBuffer.wrap(jsonProcessor.toJson(connectionInfoMessage)));
            } catch (IOException e) {            	
            	LOGGER.error("No se pudo enviar el mensaje", e);
            }
        }

        private List<String> getActiveUsers() {
            final List<String> activeUsers = new ArrayList<String>();
            for (ChatConnection connection : CONNECTIONS.values()) {
                activeUsers.add(connection.getUserName());
            }
            return activeUsers;
        }
        
        private List<String> getFormats() {
            final List<String> formats = new ArrayList<String>();
            for (ChatConnection connection : CONNECTIONS.values()) {
                formats.add(connection.getFormat());
            }
            return formats;
        }

        
        private void sendStatusInfoToOponent(StatusInfoMessage message) {
        	
        	try {
        		
        		LOGGER.info("Notificando estado a: "+this.getUserNameTwo()+" de: "+this.getUserName()+" Mensaje: "+message.getStatusInfo().getStatus());
				sendMessage(this.getUserNameTwo(), message);
			} catch (IOException e1) {
				LOGGER.error("No se pudo enviar el mensaje", e1);
			}
        	
        }

        private Collection<ChatConnection> getAllChatConnectionsExceptThis() {
            final Collection<ChatConnection> allConnections = CONNECTIONS.values();
            allConnections.remove(this);
            return allConnections;
        }

        private ChatConnection getDestinationUserConnection(String destinationUser) {
            for (ChatConnection connection : CONNECTIONS.values()) {
                if (destinationUser.equals(connection.getUserName())) {
                    return connection;
                }
            }
            return null;
        }
        
        private void sendMessage(String string,Object object) throws IOException{
        	final ChatConnection destinationConnection = getDestinationUserConnection(string);
            if (destinationConnection != null) {
                final CharBuffer jsonMessage = CharBuffer.wrap(jsonProcessor.toJson(object));
                destinationConnection.getWsOutbound().writeTextMessage(jsonMessage);
            } else {
            	LOGGER.warn("Se está intentando enviar un mensaje a un usuario no conectado");
            }
        }

    }

	public static Map<String, ChatConnection> getConnections() {
		return CONNECTIONS;
	}
}
