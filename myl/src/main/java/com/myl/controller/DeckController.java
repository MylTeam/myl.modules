package com.myl.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myl.modelo.Carta;
import com.myl.modelo.Deck;
import com.myl.modelo.DeckCarta;
import com.myl.modelo.Edicion;
import com.myl.modelo.Formato;
import com.myl.modelo.Usuario;
import com.myl.negocio.CartaNegocio;
import com.myl.negocio.DeckCartaNegocio;
import com.myl.negocio.DeckNegocio;
import com.myl.negocio.EdicionNegocio;
import com.myl.negocio.FormatoNegocio;
import com.myl.negocio.GenericBs;
import com.myl.negocio.UsuarioNegocio;
import com.myl.util.NombreObjetosSesion;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({
		@Result(name = "success", type = "redirectAction", params = {"actionName", "usuario" }),
		@Result(name = "input", type = "redirectAction", params = {"actionName", "usuario" }),
		@Result(name = "res", type = "json", params = { "root","action", "includeProperties",
				"resultado\\[\\d+\\]\\.id,"
				+ "resultado\\[\\d+\\]\\.nombre,"
				+ "resultado\\[\\d+\\]\\.efecto,"
				+ "resultado\\[\\d+\\]\\.frecuencia,"
				+ "resultado\\[\\d+\\]\\.coste,"
				+ "resultado\\[\\d+\\]\\.fuerza,"
				+ "resultado\\[\\d+\\]\\.idTemp,"
				+ "resultado\\[\\d+\\]\\.numero,"
				+ "resultado\\[\\d+\\]\\.raza,"
				+ "resultado\\[\\d+\\]\\.tipo,"				
				+ "resultado\\[\\d+\\]\\.siglas" }),
		@Result(name = "decks", type = "json", params = { "root","action", "includeProperties",
				"deckCompleto\\[\\d+\\]\\.id,"
				+ "deckCompleto\\[\\d+\\]\\.cantidad,"
				+ "deckCompleto\\[\\d+\\]\\.nombre,"
				+ "deckCompleto\\[\\d+\\]\\.efecto,"
				+ "deckCompleto\\[\\d+\\]\\.frecuencia,"
				+ "deckCompleto\\[\\d+\\]\\.coste,"
				+ "deckCompleto\\[\\d+\\]\\.fuerza,"
				+ "deckCompleto\\[\\d+\\]\\.numero,"
				+ "deckCompleto\\[\\d+\\]\\.raza,"
				+ "deckCompleto\\[\\d+\\]\\.tipo,"				
				+ "deckCompleto\\[\\d+\\]\\.siglas"		
		}) })
public class DeckController extends ActionSupport implements ModelDriven<Deck>,
		Preparable {

	private static final long serialVersionUID = -8068256015486413672L;
	private static final Logger LOGGER = LoggerFactory.getLogger(DeckController.class);
	private Integer idSel;
	private Deck model;
	private Usuario usuario;

	private List<Carta> resultado;
	private List<Edicion> ediciones;

	private EdicionNegocio edicionNegocio;
	private CartaNegocio cartaNegocio;
	private DeckNegocio deckNegocio;
	private DeckCartaNegocio deckCartaNegocio;
	private UsuarioNegocio usuarioNegocio;
	private GenericBs genericBs;

	private List<String> razas;
	private List<String> tipos;
	private List<DeckCarta> deckCartas;
	private String lista;
	private String criterioJson;

	private Gson jsonProcessor;
	private List<Deck> decks;

	private Deck deckAux;
	private List<Formato> formatos;
	private List<DeckCarta> deck;
	private List<Carta> deckCompleto;

	@SkipValidation
	public HttpHeaders index() {

		return new DefaultHttpHeaders("index").disableCaching();
	}

	@SkipValidation
	public String editNew() {
		
		ediciones = edicionNegocio.findAll();
		razas = cartaNegocio.findByCriteria();
		tipos = cartaNegocio.findByCriteriaTipo();
		formatos=genericBs.findAll(Formato.class);

		return "editNew";
	}

	public void validateCreate() {

		jsonProcessor = new Gson();
		Type listType = new TypeToken<List<DeckCarta>>() {
		}.getType();
		deckCartas = jsonProcessor.fromJson(lista, listType);
		
		/**
		 * Verifica que el mazo no esté vacío
		 */
		if (deckCartas.isEmpty()) {
			addActionError("El mazo está vacío");
		}
		
		/**
		 * Verifica que el formato sea correcto 
		 */
		String res=deckNegocio.isCorrectFormat(deckCartas, model.getFormatoId());
		if(!res.isEmpty()){
			addActionError(res);
		}
				
		
		if (hasFieldErrors() || hasActionErrors()) {
			ActionContext.getContext().getSession().put("deckTmp", deckCartas);
			formatos=genericBs.findAll(Formato.class);
		}
	}
	

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.deckNombre", type = ValidatorType.FIELD, key = "Introduce un nombre para el mazo")},
			intRangeFields = {
			@IntRangeFieldValidator(fieldName = "model.formatoId", type = ValidatorType.FIELD, message = "Selecciona el formato del mazo", min = "1")
			})	
	public HttpHeaders create() {
		jsonProcessor = new Gson();
		Type listType = new TypeToken<List<DeckCarta>>() {
		}.getType();
		deckCartas = jsonProcessor.fromJson(lista, listType);

		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);

		model.setUsuarioId(usuario.getIdUsuario());
		model = deckNegocio.save(model);

		for (DeckCarta dc : deckCartas) {
			deckCartaNegocio.insertCard(model.getDeckId(), dc.getCartaId(),
					dc.getCartaQt());
		}

		return new DefaultHttpHeaders("success").setLocationId(model
				.getDeckId());
	}

	@SkipValidation
	public String edit() {
		String result="edit";
		Usuario aux=(Usuario) ActionContext.getContext().getSession().get(NombreObjetosSesion.USUARIO);		
		if(!model.getUsuarioId().equals(aux.getIdUsuario())){
			result="denied";
		}else{
			ediciones = edicionNegocio.findAll();
			razas = cartaNegocio.findByCriteria();
			tipos = cartaNegocio.findByCriteriaTipo();
			formatos=genericBs.findAll(Formato.class);			
		}

		return result;
	}

	public void validateUpdate() {

		jsonProcessor = new Gson();
		Type listType = new TypeToken<List<DeckCarta>>() {
		}.getType();
		deckCartas = jsonProcessor.fromJson(lista, listType);
		
		if (deckCartas.isEmpty()) {
			addActionError("El mazo está vacío");
		}
		
		
		String res=deckNegocio.isCorrectFormat(deckCartas, model.getFormatoId());
		if(!res.isEmpty()){
			addActionError(res);
		}
		
		if (hasFieldErrors() || hasActionErrors()) {
			ActionContext.getContext().getSession().put("deckTmp", deckCartas);
			formatos=genericBs.findAll(Formato.class);
		}
		
		
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.deckNombre", type = ValidatorType.FIELD, key = "Introduce un nombre para el mazo")},
			intRangeFields = {
			@IntRangeFieldValidator(fieldName = "model.formatoId", type = ValidatorType.FIELD, message = "Selecciona el formato", min = "1")
			})
	public String update() {
		jsonProcessor = new Gson();
		Type listType = new TypeToken<List<DeckCarta>>() {
		}.getType();
		deckCartas = jsonProcessor.fromJson(lista, listType);
		model = deckNegocio.save(model);
		deckCartaNegocio.deleteCardsFromDeck(model.getDeckId());

		for (DeckCarta dc : deckCartas) {
			deckCartaNegocio.insertCard(model.getDeckId(), dc.getCartaId(),
					dc.getCartaQt());
		}

		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		String result="deleteConfirm";
		Usuario aux=(Usuario) ActionContext.getContext().getSession().get(NombreObjetosSesion.USUARIO);		
		if(!model.getUsuarioId().equals(aux.getIdUsuario())){
			result="denied";
		}
		return result;
	}

	public void validateDestroy() {
		LOGGER.debug("onValidateDestroy");
	}

	public String destroy() {
		usuario = (Usuario) ActionContext.getContext().getSession().get(NombreObjetosSesion.USUARIO);
		usuario.setDeckPred(0);

		usuarioNegocio.save(usuario);
		deckNegocio.delete(model);
		return "success";
	}

	@SkipValidation
	public String search() {		
		jsonProcessor = new Gson();
		Carta cartaAux = jsonProcessor.fromJson(criterioJson, Carta.class);
		resultado = new ArrayList<Carta>();

		for (Carta carta : cartaNegocio.findByCriterioBusqueda(cartaAux)) {

			Carta aux = new Carta();
			aux.setId(carta.getId());
			aux.setIdTemp(carta.getNumero() + carta.getEdicion().getSiglas());
			aux.setNombre(carta.getNombre());
			aux.setNumero(carta.getNumero());
			aux.setEfecto(carta.getEfecto());
			aux.setRaza(carta.getRaza());
			aux.setTipo(carta.getTipo());
			aux.setFrecuencia(carta.getFrecuencia());
			aux.setCoste(carta.getCoste());
			aux.setFuerza(carta.getFuerza());
			aux.setSiglas(carta.getEdicion().getSiglas());

			resultado.add(aux);
		}		
		return "res";
	}

	@SkipValidation
	public String buscarDecks() {
		
		deckCompleto = new ArrayList<Carta>();

		
		deckCartas=(List<DeckCarta>) ActionContext.getContext().getSession().get("deckTmp");
		ActionContext.getContext().getSession().remove("deckTmp");
		
		if (deckCartas==null && idSel!=null ) {
			deckAux = deckNegocio.findById(idSel);
			int c = 0;
			for (Carta carta : deckAux.getCartas()) {
				Carta aux = new Carta();
				aux.setId(carta.getId());
				aux.setCantidad(deckAux.getDeckCartas().get(c).getCartaQt());
				aux.setNombre(carta.getNombre());
				aux.setNumero(carta.getNumero());
				aux.setEfecto(carta.getEfecto());
				aux.setTipo(carta.getTipo());
				aux.setRaza(carta.getRaza());
				aux.setFrecuencia(carta.getFrecuencia());
				aux.setCoste(carta.getCoste());
				aux.setFuerza(carta.getFuerza());
				aux.setSiglas(carta.getEdicion().getSiglas());
				deckCompleto.add(aux);
				c++;
			}
		}else{
			int c=0;
			for(DeckCarta deckCarta : deckCartas){
				Carta carta=cartaNegocio.findById(deckCarta.getCartaId());
				Carta aux = new Carta();
				aux.setId(carta.getId());
				aux.setCantidad(deckCarta.getCartaQt());
				aux.setNombre(carta.getNombre());
				aux.setNumero(carta.getNumero());
				aux.setEfecto(carta.getEfecto());
				aux.setTipo(carta.getTipo());
				aux.setRaza(carta.getRaza());
				aux.setFrecuencia(carta.getFrecuencia());
				aux.setCoste(carta.getCoste());
				aux.setFuerza(carta.getFuerza());
				aux.setSiglas(carta.getEdicion().getSiglas());
								
				deckCompleto.add(aux);
				c++;
			}
		}
		
		return "decks";
	}
		

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = deckNegocio.findById(idSel);
		}
	}

	@Override
	public Deck getModel() {
		if (model == null) {
			model = new Deck();
		}
		return model;
	}

	public void setModel(Deck model) {
		this.model = model;
	}

	public Deck getDeckAux() {
		return deckAux;
	}

	public void setDeckAux(Deck deckAux) {
		this.deckAux = deckAux;
	}

	public List<Carta> getResultado() {
		return resultado;
	}

	public void setResultado(List<Carta> resultado) {
		this.resultado = resultado;
	}

	public List<Edicion> getEdiciones() {
		ediciones = edicionNegocio.findAll();
		return ediciones;
	}

	public void setEdiciones(List<Edicion> ediciones) {
		this.ediciones = ediciones;
	}

	public EdicionNegocio getEdicionNegocio() {
		return edicionNegocio;
	}

	public void setEdicionNegocio(EdicionNegocio edicionNegocio) {
		this.edicionNegocio = edicionNegocio;
	}

	public CartaNegocio getCartaNegocio() {
		return cartaNegocio;
	}

	public void setCartaNegocio(CartaNegocio cartaNegocio) {
		this.cartaNegocio = cartaNegocio;
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

	public List<String> getRazas() {
		razas = cartaNegocio.findByCriteria();
		return razas;
	}

	public void setRazas(List<String> razas) {
		this.razas = razas;
	}

	public List<DeckCarta> getDeckCartas() {
		return deckCartas;
	}

	public void setDeckCartas(List<DeckCarta> deckCartas) {
		this.deckCartas = deckCartas;
	}

	public List<DeckCarta> getDeck() {
		return deck;
	}

	public void setDeck(List<DeckCarta> deck) {
		this.deck = deck;
	}

	public String getLista() {
		return lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}

	public String getCriterioJson() {
		return criterioJson;
	}

	public void setCriterioJson(String criterioJson) {
		this.criterioJson = criterioJson;
	}

	public Gson getJsonProcessor() {
		return jsonProcessor;
	}

	public void setJsonProcessor(Gson jsonProcessor) {
		this.jsonProcessor = jsonProcessor;
	}

	public List<Deck> getDecks() {
		return decks;
	}

	public void setDecks(List<Deck> decks) {
		this.decks = decks;
	}

	public List<Carta> getDeckCompleto() {
		return deckCompleto;
	}

	public void setDeckCompleto(List<Carta> deckCompleto) {
		this.deckCompleto = deckCompleto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void prepare() {
		clearErrorsAndMessages();
	}

	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}

	public List<String> getTipos() {
		tipos = cartaNegocio.findByCriteriaTipo();
		return tipos;
	}

	public void setTipos(List<String> tipos) {
		this.tipos = tipos;
	}

	public List<Formato> getFormatos() {
		return formatos;
	}

	public void setFormatos(List<Formato> formatos) {
		this.formatos = formatos;
	}	

	public GenericBs getGenericBs() {
		return genericBs;
	}

	public void setGenericBs(GenericBs genericBs) {
		this.genericBs = genericBs;
	}

}
