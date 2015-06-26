package com.myl.controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.inject.Named;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;

import ch.qos.logback.core.Context;

import com.myl.modelo.Deck;
import com.myl.modelo.Duelo;
import com.myl.modelo.Pais;
import com.myl.modelo.Usuario;
import com.myl.negocio.CartaNegocio;
import com.myl.negocio.DeckNegocio;
import com.myl.negocio.DueloNegocio;
import com.myl.negocio.EdicionNegocio;
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
import com.opensymphony.xwork2.validator.annotations.ExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "usuario" }) })
public class UsuarioController extends ActionSupport implements
		ModelDriven<Usuario> {

	private static final long serialVersionUID = 1L;
	private Integer idSel;
	private Integer deckId;

	private Usuario model = null;
	private Usuario usuario;

	private Deck deck;
	private String confirmPass;

	private UsuarioNegocio usuarioNegocio;
	private DeckNegocio deckNegocio;
	private CartaNegocio cartaNegocio;	
	private DueloNegocio dueloNegocio;

	private GenericBs genericBs;
	
	private List<Deck> lista;
	private List<Pais> listPaises;
	private List<Duelo> duelosGanados;
	private List<Duelo> duelosPerdidos;
	
	private Integer confirm;

	private IssueMail mailSender;
	
	@SkipValidation
	public HttpHeaders index() {

		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);
		idSel = usuario.getIdUsuario();

		Deck deckAux = new Deck();
		deckAux.setUsuarioId(usuario.getIdUsuario());
		lista = deckNegocio.findByExample(deckAux);

		if (usuario.getEmail() == null || usuario.getIdPais() == null) {
			addActionMessage("Favor de actualizar tus datos");
		}
		if(!usuario.getVerificado() && usuario.getDiasRestantes()>=8){
			addActionMessage("Por favor verifica tu correo electrónico ingresando a la opción 'Modificar Perfil', si no lo verificas en "+ (usuario.getDiasRestantes()-7) +" días, tu cuenta será bloqueda y no podrás jugar");
		}
		if(!usuario.getVerificado() && usuario.getDiasRestantes()<8){
			addActionMessage("Por favor verifica tu correo electrónico ingresando a la opción 'Modificar Perfil, si no lo verificas en "+ usuario.getDiasRestantes() +" días, tu cuenta será eliminada");
		}

		return new DefaultHttpHeaders("index").disableCaching();
	}

	@SkipValidation
	public String edit() {
		String result = "edit";
		
		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);
		if (!usuario.getIdUsuario().equals(idSel)) {
			result = "denied";
		}else{			
			listPaises=genericBs.findAll(Pais.class);
		}

		return result;
	}

	public void validateUpdate() {		
		
		if(!model.getEmail().equals("")){
			Usuario aux = new Usuario();
			aux.setEmail(model.getEmail());
			List<Usuario> usuariosAux = usuarioNegocio.findByExample(aux);

		
			if (!usuariosAux.isEmpty()) {
				if (!usuariosAux.get(0).getIdUsuario().equals(model.getIdUsuario())) {
					addActionError("El correo electrónico ingresado ya está registrado");
				}
			}
		}

		if (hasFieldErrors() || hasActionErrors()) {
			listPaises=genericBs.findAll(Pais.class);
			System.out.println("despues de obtener lista de paises");
		}
	}

	@Validations(
			intRangeFields = { @IntRangeFieldValidator(fieldName = "model.idPais", type = ValidatorType.SIMPLE, message = "Selecciona tu pais", min = "1") },
			requiredStrings = { @RequiredStringValidator(fieldName = "model.email", type = ValidatorType.FIELD, key = "Introduce tu correo electrónico") }, 			 
			emails = { @EmailValidator(fieldName = "model.email", type = ValidatorType.FIELD, message = "Correo electrónico no válido")
			})
	public String update() {
		if(confirm!=null){
			if(model.getCodigo()==0){
				Random random = new Random();
				model.setCodigo(random.nextLong() * 99999 + 1);				
			}			
				String msg="Hola "+model.getLogin()+"<p>Por favor confirma tu e-mail ingresando a la siguiente liga:</p><p><a href='http://50.62.23.86:8080/myl/registro/"+model.getIdUsuario()+"?cd="+model.getCodigo()+"'>Confirmar</a></p><p>MyL Team</p>";				
				
//				mailSender.sendMailConfirm(model.getEmail(), "MyL: Confirmar E-mail", msg);
				if(mailSender.sendMailConfirmTest(model.getEmail(), "MyL: Confirmar E-mail", msg)){
					addActionMessage("Se ha enviado un e-mail a "+model.getEmail()+" para realizar la verificación de identidad. Si no lo ves revisa tu bandeja de SPAM.");
				}else{
					addActionError("Por el momento no se te puede enviar el correo de verificación por favor inténtalo mas tarde desde tu perfil.");
				}
							
		}
				
		model = usuarioNegocio.save(model);
		ActionContext.getContext().getSession().put(NombreObjetosSesion.USUARIO, model);
		addActionMessage("Se han actualizado correctamente tus datos");
		return "success";
	}
	

	@SkipValidation
	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public void validateDestroy() {
		throw new UnsupportedOperationException();
	}

	@SkipValidation
	public String destroy() {

		return "success";
	}

	public String show() {
		String result="show";
		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);
		if (!usuario.getIdUsuario().equals(idSel)) {
			result = "denied";
		} else {
			Duelo aux = new Duelo();
			aux.setWinnerId(model.getIdUsuario());
			duelosGanados = dueloNegocio.findByExample(aux);
			aux = new Duelo();
			aux.setLoserId(model.getIdUsuario());
			duelosPerdidos = dueloNegocio.findByExample(aux);
		}

		return result;
	}

	public void setDeckPredeterminado() {
		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);
		usuario.setDeckPred(deckId);
		usuarioNegocio.save(usuario);
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public DeckNegocio getDeckNegocio() {
		return deckNegocio;
	}

	public void setDeckNegocio(DeckNegocio deckNegocio) {
		this.deckNegocio = deckNegocio;
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

	public List<Deck> getLista() {
		return lista;
	}

	public void setLista(List<Deck> lista) {
		this.lista = lista;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public Integer getDeckId() {
		return deckId;
	}

	public void setDeckId(Integer deckId) {
		this.deckId = deckId;
	}

	public CartaNegocio getCartaNegocio() {
		return cartaNegocio;
	}

	public void setCartaNegocio(CartaNegocio cartaNegocio) {
		this.cartaNegocio = cartaNegocio;
	}

	public List<Pais> getListPaises() {
		return listPaises;
	}

	public void setListPaises(List<Pais> listPaises) {
		this.listPaises = listPaises;
	}

	public List<Duelo> getDuelosGanados() {
		return duelosGanados;
	}

	public void setDuelosGanados(List<Duelo> duelosGanados) {
		this.duelosGanados = duelosGanados;
	}

	public List<Duelo> getDuelosPerdidos() {
		return duelosPerdidos;
	}

	public void setDuelosPerdidos(List<Duelo> duelosPerdidos) {
		this.duelosPerdidos = duelosPerdidos;
	}

	public DueloNegocio getDueloNegocio() {
		return dueloNegocio;
	}

	public void setDueloNegocio(DueloNegocio dueloNegocio) {
		this.dueloNegocio = dueloNegocio;
	}

	public Integer getConfirm() {
		return confirm;
	}

	public void setConfirm(Integer confirm) {
		this.confirm = confirm;
	}

	public IssueMail getMailSender() {
		return mailSender;
	}

	public void setMailSender(IssueMail mailSender) {
		this.mailSender = mailSender;
	}

	public GenericBs getGenericBs() {
		return genericBs;
	}

	public void setGenericBs(GenericBs genericBs) {
		this.genericBs = genericBs;
	}

}
