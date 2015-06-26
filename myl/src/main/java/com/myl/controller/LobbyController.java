package com.myl.controller;

import javax.inject.Named;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.myl.modelo.Usuario;
import com.myl.negocio.CartaNegocio;
import com.myl.negocio.DeckCartaNegocio;
import com.myl.negocio.DeckNegocio;
import com.myl.util.NombreObjetosSesion;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {"actionName", "lobby" })
,@Result(name = "nodeck", type = "redirectAction", params = {"actionName", "usuario" })
})
public class LobbyController extends ActionSupport {

	private static final long serialVersionUID = 8585016072024421730L;
	private Integer idSel;
	private Usuario usuario;
	private String username;
	private String format;
	
	private DeckNegocio	deckNegocio;
	private DeckCartaNegocio deckCartaNegocio;
	
	@SkipValidation
	public HttpHeaders index() {
		usuario=(Usuario) ActionContext.getContext().getSession().get(NombreObjetosSesion.USUARIO);
		setUsername(usuario.getLogin());
		
		if(!usuario.getEstatus() && !usuario.getVerificado()){
			addActionError("Tu usuario ha sido desactivado por lo que no podrás jugar. Esto se debe a que no has verificado tu e-mail o has sido sancionado, si tienes dudas contacta al administrador.");
			return new DefaultHttpHeaders("nodeck").disableCaching();
		}
		
		if(usuario.getDeckPred()==0){
			addActionError("Necesitas armar un mazo para poder jugar.");
			return new DefaultHttpHeaders("nodeck").disableCaching();
		
		}else{
			if(deckCartaNegocio.getDeckSize(usuario.getDeckPred()).intValue()!=50){
				addActionError("Tu mazo debe contener 50 cartas exactamente para poder jugar.");
				return new DefaultHttpHeaders("nodeck").disableCaching();
			}else{
				setFormat(deckNegocio.findById(usuario.getDeckPred()).getFormato().getNombre()+" / V: "+usuario.getWons()+" D: "+usuario.getLost());
				return new DefaultHttpHeaders("index").disableCaching();
			}
		}
	}
	
	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	public String getFormat() {
		return format;
	}


	public void setFormat(String format) {
		this.format = format;
	}


	public DeckNegocio getDeckNegocio() {
		return deckNegocio;
	}


	public void setDeckNegocio(DeckNegocio deckNegocio) {
		this.deckNegocio = deckNegocio;
	}


	public DeckCartaNegocio getDeckCartaNegocio() {
		return deckCartaNegocio;
	}


	public void setDeckCartaNegocio(DeckCartaNegocio deckCartaNegocio) {
		this.deckCartaNegocio = deckCartaNegocio;
	}

}
