package com.myl.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

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
import com.myl.negocio.CartaNegocio;
import com.myl.negocio.DeckNegocio;
import com.myl.negocio.GenericBs;
import com.myl.negocio.PaisNegocio;
import com.myl.negocio.UsuarioNegocio;
import com.myl.util.IssueMail;
import com.myl.util.NombreObjetosSesion;
import com.myl.util.Spoiler;
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
public class RegistroController extends ActionSupport implements
		ModelDriven<Usuario> {

	private static final long serialVersionUID = 1L;

	private Integer idSel;

	private Usuario model = null;
	private Usuario usuario;
	private UsuarioNegocio usuarioNegocio;
	private DeckNegocio deckNegocio;
	private List<Deck> lista;
	private Deck deck;

	private String confirmPass;
	private List<Pais> listPaises;

	private PaisNegocio paisNegocio;

	private IssueMail mailSender;
	private Long cd;
	

	@SkipValidation
	public String editNew() {

		listPaises = paisNegocio.findAll();

		return "editNew";
	}

	public void validateCreate() {

		if (!model.getPassword().equals(confirmPass)) {
			addActionError("Las contraseñas no son iguales");
		}

		Usuario aux = new Usuario();
		aux.setLogin(model.getLogin());
		if (!usuarioNegocio.findByExample(aux).isEmpty()) {
			addActionError("Nombre de usuario no disponible");
		}

		aux = new Usuario();
		aux.setEmail(model.getEmail());
		if (!usuarioNegocio.findByExample(aux).isEmpty()) {
			addActionError("El correo electrónico ingresado ya está registrado");
		}

		if (hasFieldErrors() || hasActionErrors()) {
			listPaises = paisNegocio.findAll();
		}
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.login", type = ValidatorType.FIELD, key = "Introduce un nombre de usuario"),
			@RequiredStringValidator(fieldName = "model.password", type = ValidatorType.FIELD, key = "Introduce la contraseña"),
			@RequiredStringValidator(fieldName = "model.email", type = ValidatorType.FIELD, key = "Introduce tu correo electrónico"),
			@RequiredStringValidator(fieldName = "confirmPass", type = ValidatorType.FIELD, key = "Confirma la contraseña") }, 
			regexFields = { @RegexFieldValidator(fieldName = "model.login", type = ValidatorType.FIELD, key = "Nombre de usuario no válido", regexExpression = "[A-Z[a-z][0-9]]+") }, 
			intRangeFields = { @IntRangeFieldValidator(fieldName = "model.idPais", type = ValidatorType.FIELD, message = "Selecciona tu pais", min = "1") }, 
			emails = { @EmailValidator(fieldName = "model.email", type = ValidatorType.FIELD, message = "Correo electrónico no válido") })
	public String create() {
		model.setLogin(model.getLogin().trim());
		model.setDeckPred(0);
		model.setWons(0);
		model.setLost(0);
		model.setFhRegistro(new Date());

		Random random = new Random();
		model.setCodigo(random.nextLong() * 99999 + 1);
		model.setVerificado(false);
		
		model.setEstatus(true);
		model.setDiasRestantes(14);
		model = usuarioNegocio.save(model);

		String msg="Hola "+model.getLogin()+"<p>Por favor confirma tu e-mail ingresando a la siguiente liga:</p><p><a href='http://50.62.23.86:8080/myl/registro/"+model.getIdUsuario()+"?cd="+model.getCodigo()+"'>Confirmar</a></p><p>MyL Team</p>";				
//		mailSender.sendMailConfirm(model.getEmail(), "MyL: Confirmar E-mail", msg);
		
		addActionMessage("El registro se ha realizado exitósamente.");
		if(mailSender.sendMailConfirmTest(model.getEmail(), "MyL: Confirmar E-mail", msg)){
			addActionMessage("Un enlace ha sido enviado a tu correo electrónico para verificar tu identidad. Si no lo ves revisa tu bandeja de SPAM.");
		}else{
			addActionError("Por el momento no se te puede enviar el correo de verificación por favor inténtalo mas tarde desde tu perfil.");
		}
		
		
		
		return "login";
	}

	public String show() {		
		if (cd.equals(model.getCodigo())) {			
			if (!model.getVerificado()) {
				model.setVerificado(true);
				model.setEstatus(true);
				usuarioNegocio.save(model);
				addActionMessage("Gracias por verificar tu correo "+ model.getLogin()+".");
				ActionContext.getContext().getSession().put(NombreObjetosSesion.USUARIO, model);
			} else {
				addActionMessage("Tu correo ya había sido verificado con anterioridad "+ model.getLogin()+".");				
			}
		} else {			
			addActionError("El código ingresado no es válido.");
		}

		return "show";
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

	public List<Deck> getLista() {
		return lista;
	}

	public void setLista(List<Deck> lista) {
		this.lista = lista;
	}

	public DeckNegocio getDeckNegocio() {
		return deckNegocio;
	}

	public void setDeckNegocio(DeckNegocio deckNegocio) {
		this.deckNegocio = deckNegocio;
	}

	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public List<Pais> getListPaises() {
		return listPaises;
	}

	public void setListPaises(List<Pais> listPaises) {
		this.listPaises = listPaises;
	}

	public PaisNegocio getPaisNegocio() {
		return paisNegocio;
	}

	public void setPaisNegocio(PaisNegocio paisNegocio) {
		this.paisNegocio = paisNegocio;
	}

	public IssueMail getMailSender() {
		return mailSender;
	}

	public void setMailSender(IssueMail mailSender) {
		this.mailSender = mailSender;
	}

	public Long getCd() {
		return cd;
	}

	public void setCd(Long cd) {
		this.cd = cd;
	}


}
