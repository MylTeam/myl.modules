package com.myl.springsockets;

import java.io.Serializable;

import org.springframework.web.socket.WebSocketSession;

import com.myl.messages.SessionInfoMessage.TYPE;

public class UserConnection implements Serializable{

	private final String userName;
	private final String formatOrUser;
	private final TYPE tipo;

	public UserConnection(String userName, String formatOrUser,TYPE tipo) {
		this.userName = userName;
		this.formatOrUser = formatOrUser;
		this.tipo=tipo;
	}

	public String getUserName() {
		return userName;
	}

	public String getFormatOrUser() {
		return formatOrUser;
	}
	
	public TYPE getTipo(){
		return tipo;
	}


}