package com.myl.controller;

import java.util.List;

import javax.inject.Named;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myl.modelo.Usuario;
import com.myl.negocio.UsuarioNegocio;
import com.opensymphony.xwork2.ActionSupport;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "usuario" }) })
public class RankingController extends ActionSupport {

	private static final long serialVersionUID = 8585016072024421730L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RankingController.class);

	private List<Usuario> userList;
	private UsuarioNegocio usuarioNegocio;
	
	public String index() {
			userList=usuarioNegocio.findFirstX(100);
		return "index";
	}


	public List<Usuario> getUserList() {
		return userList;
	}


	public void setUserList(List<Usuario> userList) {
		this.userList = userList;
	}


	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}


	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}

	
	
}
