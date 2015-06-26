package com.myl.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.myl.modelo.Deck;
import com.myl.modelo.Pais;
import com.myl.modelo.Usuario;
import com.myl.negocio.DeckNegocio;
import com.myl.negocio.PaisNegocio;
import com.myl.negocio.UsuarioNegocio;
import com.myl.util.AppError;
import com.myl.util.IssueMail;
import com.myl.util.NombreObjetosSesion;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({ @Result(name = "restored", type = "redirectAction", params = {
		"actionName", "login" }) })
public class RecuperarPassController extends ActionSupport implements
		ModelDriven<Usuario> {

	private static final long serialVersionUID = 1L;

	private Integer idSel;

	private Usuario model = null;
	private Usuario usuario;
	private UsuarioNegocio usuarioNegocio;
	private IssueMail mailSender;
	private String email;
	private List<Usuario> listUsuarios; 
	
	@SkipValidation
	public String editNew() {
 
		return "editNew";
	}	

	public void validateCreate(){
		Usuario aux=new Usuario();
		aux.setEmail(model.getEmail());		
		listUsuarios=usuarioNegocio.findByExample(aux);
		
		if(listUsuarios.isEmpty()){
			addActionError("No se ha encontrado un usuario con el email ingresado");
		}		
	}
	
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "email", type = ValidatorType.FIELD, key = "Introduce tu correo electrónico"),			},
			emails={
			@EmailValidator(fieldName="email", type=ValidatorType.FIELD, message="Correo electrónico no válido")})
	public HttpHeaders create() {		
		usuario=listUsuarios.get(0);
		enviarCorreoPass(usuario.getLogin(),usuario.getPassword(),usuario.getEmail());
		addActionMessage("Tu contraseña ha sido enviada a tu correo electrónico");
		return new DefaultHttpHeaders("restored").setLocationId(model.getIdUsuario());
	}
	
	public void enviarCorreoPass(String usuario,String pass,String email) {
		String msg="De acuerdo a tu solicitud se ha recuperado tus datos de acceso \n Usuario: "+usuario+"\n Password: "+pass;
		mailSender.sendMailTo(email, "Myl - contraseña recuperada", msg);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = usuarioNegocio.findById(idSel);
		}
	}

	@Override
	public Usuario getModel() {
		if (model == null) {
			model = new Usuario();
		}
		return model;
	}

	public void setModel(Usuario model) {
		this.model = model;
	}



	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public IssueMail getMailSender() {
		return mailSender;
	}

	public void setMailSender(IssueMail mailSender) {
		this.mailSender = mailSender;
	}

	public List<Usuario> getListUsuarios() {
		return listUsuarios;
	}

	public void setListUsuarios(List<Usuario> listUsuarios) {
		this.listUsuarios = listUsuarios;
	}

	
}
