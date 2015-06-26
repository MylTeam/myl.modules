package com.myl.controller;


import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myl.modelo.Usuario;
import com.myl.negocio.UsuarioNegocio;
import com.myl.util.NombreObjetosSesion;
import com.myl.util.Spoiler;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


@Named
@Results({
		@Result(name = "error", location = "pages/login.jsp"),
		@Result(name = "acceso", type = "redirectAction", params = {
				"actionName", "usuario" })
		 }

)
public class LoginController extends ActionSupport implements
		ServletRequestAware {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	private static final long serialVersionUID = -7475211274962357078L;
	private String userId;
	private String password;
	private Usuario usuarioSel;
	private UsuarioNegocio service;	
	private HttpServletRequest request;	

	public LoginController(UsuarioNegocio loginService) {
		service = loginService;
	}
	
	@Override
	public String execute() throws Exception {
		
		Usuario usuarioAux = new Usuario();
		List<Usuario> usuarios = null;
		usuarioAux.setLogin(userId);
		usuarioAux.setPassword(password);		
				
		try {
			usuarios = service.findByExample(usuarioAux);
						
			if (usuarios.isEmpty()) {
				addActionError("Usuario y/o contraseña incorrectos");
			} else {
				clearActionErrors();
				usuarioSel=usuarios.get(0);
				usuarioSel.setFhLastSession(new Date());
				usuarioSel=service.save(usuarioSel);
				ActionContext.getContext().getSession().put(NombreObjetosSesion.USUARIO, usuarioSel);				
				return "next";
			}

		} catch (Exception e) {
			LOGGER.error("Error",e);
			return "error";
		}
		return "error";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String create(){
			try {
				return execute();
			} catch (Exception e) {				
				LOGGER.error("Error", e);
				return "error";
			}			
	}

	public String index() {
		usuarioSel = (Usuario) ActionContext.getContext().getSession().get(NombreObjetosSesion.USUARIO);
		return "index";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setLoginService(UsuarioNegocio s) {
		service = s;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public Usuario getUsuarioSel() {
		return usuarioSel;
	}

	public void setUsuarioSel(Usuario usuarioSel) {
		this.usuarioSel = usuarioSel;
	}

	public UsuarioNegocio getService() {
		return service;
	}

	public void setService(UsuarioNegocio service) {
		this.service = service;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

}