package com.myl.util;

import java.nio.ByteBuffer;
import java.io.IOException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.myl.messages.CardInfoMessage;
import com.myl.messages.ChatInfoMessage;
import com.myl.messages.ConnectionInfoMessage;
import com.myl.messages.MessageInfoMessage;
import com.myl.messages.StatusInfoMessage;

//@WebServlet(urlPatterns = "/chat")
public class WebSocketLobbyServlet extends WebSocketServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketLobbyServlet.class);

    private static final Map<String, ChatConnection> CONNECTIONS = new HashMap<String, ChatConnection>();       
    
    @Override
    protected boolean verifyOrigin(String origin) {
        return true;
    }

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
        final String connectionId = request.getSession().getId();
        final String userName = request.getParameter("userName");
        final String format = request.getParameter("format");
        return new ChatConnection(connectionId,format, userName);
    }

    private static class ChatConnection extends MessageInbound {

        private final String connectionId;
        private final String userName;
        private final String format;
        private final Gson jsonProcessor;

        private ChatConnection(String connectionId, String format, String userName) {
            this.connectionId = connectionId;
            this.userName = userName;
            this.format = format;
            this.jsonProcessor = new Gson();
        }

        @Override
        protected void onOpen(WsOutbound outbound) {
            LOGGER.info("El usuario "+userName+" ha entrado al lobby");
            sendConnectionInfo(outbound);
            sendStatusInfoToOtherUsers(new StatusInfoMessage(userName, format, StatusInfoMessage.STATUS.CONNECTED));
            CONNECTIONS.put(connectionId, this);
        }

        @Override
        protected void onClose(int status) {
            sendStatusInfoToOtherUsers(new StatusInfoMessage(userName, format, StatusInfoMessage.STATUS.DISCONNECTED));
            LOGGER.info("El usuario "+userName+" ha salido del lobby");
            CONNECTIONS.remove(connectionId);
        }

        @Override
        protected void onBinaryMessage(ByteBuffer byteBuffer) throws IOException {
            throw new UnsupportedOperationException("Binary messages not supported");
        }

        @Override
        protected void onTextMessage(CharBuffer charBuffer) throws IOException{
        	LOGGER.info("MsgLobby= "+charBuffer.toString());
        	if(charBuffer.toString().contains("messageInfo")){
        		final MessageInfoMessage message = jsonProcessor.fromJson(charBuffer.toString(), MessageInfoMessage.class);
        		sendMessage(message.getMessageInfo().getTo(), message);
        	}else if(charBuffer.toString().contains("chatInfo")){
        		final ChatInfoMessage message = jsonProcessor.fromJson(charBuffer.toString(), ChatInfoMessage.class);        		
        		sendMessageToAll(message);	
        	}
        }

        public String getUserName() {
            return userName;
        }
        public String getFormat() {
            return format;
        }

        private void sendConnectionInfo(WsOutbound outbound) {
            final List<String> activeUsers = getActiveUsers();
            final List<String> formats = getFormats();
            final ConnectionInfoMessage connectionInfoMessage = new ConnectionInfoMessage(userName,activeUsers,formats);
            try {
                outbound.writeTextMessage(CharBuffer.wrap(jsonProcessor.toJson(connectionInfoMessage)));
            } catch (IOException e) {
            	LOGGER.error("No se pudo enviar el mensaje", e);
            }
        }

        private List<String> getActiveUsers() {
        	LOGGER.info("Obteniendo lista de usuarios, Total: "+CONNECTIONS.size());
            final List<String> activeUsers = new ArrayList<String>();
            for (ChatConnection connection : CONNECTIONS.values()) {            	
                activeUsers.add(connection.getUserName());
            }
            if(activeUsers.isEmpty()){
            	LOGGER.error("No hay usuarios activos: "+this.getUserName());
            }
            return activeUsers;
        }
        
        private List<String> getFormats() {
            final List<String> formats = new ArrayList<String>();
            for (ChatConnection connection : CONNECTIONS.values()) {
            	formats.add(connection.getFormat());
            }
            if(formats.isEmpty()){
            	LOGGER.error("No hay usuarios activos");
            }
            return formats;
        }

        private void sendStatusInfoToOtherUsers(StatusInfoMessage message) {
        	LOGGER.info("Enviado estado de: "+this.userName+" a los demás usuarios.");
        	final Collection<ChatConnection> otherUsersConnections = getAllChatConnectionsExceptThis();            
            for (ChatConnection connection : otherUsersConnections) {
                try {                	
                    connection.getWsOutbound().writeTextMessage(CharBuffer.wrap(jsonProcessor.toJson(message)));
                } catch (IOException e) {
                	LOGGER.error("No se pudo enviar el mensaje de estado de la conexión", e);
                }
            }            
        }
        
        private void sendMessageToAll(Object message) {
        	final Collection<ChatConnection> otherUsersConnections = getAllChatConnectionsAvailable();            
            for (ChatConnection connection : otherUsersConnections) {
                try {
                	if(!connection.getUserName().equals(this.getUserName())){
                		connection.getWsOutbound().writeTextMessage(CharBuffer.wrap(jsonProcessor.toJson(message)));
                	}
                } catch (IOException e) {
                	LOGGER.error("No se pudo enviar el mensaje", e);
                }
            }
        }

        private Collection<ChatConnection> getAllChatConnectionsExceptThis() {
            final Collection<ChatConnection> allConnections = CONNECTIONS.values();
            allConnections.remove(this);
            return allConnections;
        }
        
        private Collection<ChatConnection> getAllChatConnectionsAvailable() {
            final Collection<ChatConnection> allConnections = CONNECTIONS.values();
            
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
}
