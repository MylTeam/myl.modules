package com.myl.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import ch.qos.logback.classic.Logger;

import com.myl.modelo.Usuario;
import com.myl.negocio.UsuarioNegocio;
import com.myl.util.IssueMail;
import com.myl.util.AppError;
import com.myl.util.NombreObjetosSesion;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Named
@Results({ @Result(name = "appError", type = "json", params = {
		"includeProperties", "appError.*" }) })
public class ReporteController extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IssueMail mailSender;
	private AppError appError;
	private String url;
	private String stackTrace;
	private String exceptionName;
	private Usuario usuario;
	private Logger LOGGER;
	
	
	public String generaReporte() {
		Date fecha = new Date();
		appError = new AppError(getUrl(), getStackTrace(), fecha, getExceptionName());
		enviarCorreoReporte(appError);
		return "appError";
	}

	public void enviarCorreoReporte(AppError appError) {
		Date fecha = new Date();
		usuario=(Usuario) ActionContext.getContext().getSession().get(NombreObjetosSesion.USUARIO);
		String msg="";
		if(usuario!=null){
			msg="Usuario: "+usuario.getLogin()+"\n Fecha: "+fecha+"\n URL: "+appError.getUrl()+"\n Error: \n "+appError.getStackTrace();
		}else{
			msg="Fecha: "+fecha+"\n URL: "+appError.getUrl()+"\n Error: \n "+appError.getStackTrace();
		}
		mailSender.sendMailError(appError.getExceptionName(), msg);
		LOGGER.info(msg);
	}

	public IssueMail getMailSender() {
		return mailSender;
	}

	public void setMailSender(IssueMail mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * @return the appError
	 */
	public AppError getAppError() {
		return appError;
	}

	/**
	 * @param appError
	 *            the appError to set
	 */
	public void setAppError(AppError appError) {
		this.appError = appError;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the stackTrace
	 */
	public String getStackTrace() {
		return stackTrace;
	}

	/**
	 * @param stackTrace
	 *            the stackTrace to set
	 */
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Logger getLOGGER() {
		return LOGGER;
	}

	public void setLOGGER(Logger lOGGER) {
		LOGGER = lOGGER;
	}

}
