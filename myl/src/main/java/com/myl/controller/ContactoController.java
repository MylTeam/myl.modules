package com.myl.controller;

import java.util.Date;

import javax.inject.Named;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myl.modelo.Usuario;
import com.myl.util.IssueMail;
import com.myl.util.NombreObjetosSesion;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "usuario" }) })
public class ContactoController extends ActionSupport {

	private static final long serialVersionUID = 8585016072024421730L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactoController.class);
	private String asunto;
	private String mensaje;
	private IssueMail mailSender;

	@SkipValidation
	public String editNew() {

		return "editNew";
	}

	
	public void validateCreate() {
		LOGGER.debug("en ValidateCreate");
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "asunto", type = ValidatorType.FIELD, key = "Introduce el asunto"),
			@RequiredStringValidator(fieldName = "mensaje", type = ValidatorType.FIELD, key = "Introduce el mensaje")},
			regexFields = {
			@RegexFieldValidator(fieldName = "asunto", type = ValidatorType.FIELD, key = "Asunto no válido", regexExpression = "[A-Z[a-z][0-9]\\s]+"),
			@RegexFieldValidator(fieldName = "mensaje", type = ValidatorType.FIELD, key = "Mensaje no válido", regexExpression = "[A-Z[a-z][0-9]\\s]+")
			})
	public HttpHeaders create() {
			LOGGER.info(asunto+" : "+mensaje);
			
			
			Date fecha = new Date();
			Usuario usuario=(Usuario) ActionContext.getContext().getSession().get(NombreObjetosSesion.USUARIO);
			String msg=" Fecha: "+fecha+"\n Enviado por: "+usuario.getLogin()+"\n Correo: "+usuario.getEmail()+" \n Asunto: "+asunto+"\n Mensaje: "+mensaje;
			String asuntoUsuario=usuario.getLogin()+" : "+asunto;
//			mailSender.sendMail("mylzupport@gmail.com", asuntoUsuario, msg);
			mailSender.sendMailComment(asuntoUsuario, msg);
			
		return new DefaultHttpHeaders("success");
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public IssueMail getMailSender() {
		return mailSender;
	}

	public void setMailSender(IssueMail mailSender) {
		this.mailSender = mailSender;
	}

}
