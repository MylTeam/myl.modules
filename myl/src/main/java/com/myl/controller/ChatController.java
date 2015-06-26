package com.myl.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Named;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myl.modelo.Carta;
import com.myl.modelo.Deck;
import com.myl.modelo.Duelo;
import com.myl.modelo.Usuario;
import com.myl.negocio.DeckNegocio;
import com.myl.negocio.DueloNegocio;
import com.myl.negocio.UsuarioNegocio;
import com.myl.util.NombreObjetosSesion;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Named
@Results({
		@Result(name = "success", type = "redirectAction", params = {
				"actionName", "chat" }),		
		@Result(name = "cual", type = "json", params = { "root","action", "includeProperties",
						"deck1\\[\\d+\\]\\.nombre,"
						+ "deck1\\[\\d+\\]\\.efecto,"
						+ "deck1\\[\\d+\\]\\.frecuencia,"
						+ "deck1\\[\\d+\\]\\.coste,"
						+ "deck1\\[\\d+\\]\\.fuerza,"
						+ "deck1\\[\\d+\\]\\.idTemp,"
						+ "deck1\\[\\d+\\]\\.numero,"
						+ "deck1\\[\\d+\\]\\.raza,"
						+ "deck1\\[\\d+\\]\\.tipo,"				
						+ "deck1\\[\\d+\\]\\.siglas" })	})
public class ChatController extends ActionSupport {

	private static final long serialVersionUID = 8585016072024421730L;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChatController.class);

	private Integer idSel;

	private Deck deck;
	private DeckNegocio deckNegocio;
	private UsuarioNegocio usuarioNegocio;
	private DueloNegocio dueloNegocio;
	
	private List<Carta> deck1;

	private String user1;
	private String user2;

	private Integer deckId;
	

	private String key;
	private String keyctrl;

	private Duelo duelo;
	
	private Usuario us1;
	private Usuario us2;
	
	
	@SkipValidation
	public HttpHeaders index() {

		Random randomGenerator = new Random();
		key = Integer.valueOf(randomGenerator.nextInt(100000000)).toString();

		return new DefaultHttpHeaders("index").disableCaching();
	}

	public void test() {
		key = (String) ActionContext.getContext().getSession().get("OponentId");
		if (keyctrl.equals(key)) {		
			
			Duelo example = new Duelo();
	
			List<Usuario> listUsers=usuarioNegocio.findByName(user1);			
			if(!listUsers.isEmpty()){
				us1=listUsers.get(0);				
			}			
			listUsers=usuarioNegocio.findByName(user2);
			if(!listUsers.isEmpty()){
				us2=listUsers.get(0);
			}
			
			example.setWinnerId(us1.getIdUsuario());
			example.setLoserId(us2.getIdUsuario());
			
			List<Duelo> listDuel=dueloNegocio.findByExample(example);
			if(listDuel.isEmpty()){				
				dueloNegocio.insertDuel(example.getWinnerId(), example.getLoserId(), 1);				
			}else{
				duelo=listDuel.get(0);
				duelo.setDueloQt(duelo.getDueloQt()+1);
				dueloNegocio.save(duelo);
			}
			
			us1.setWons(us1.getWons()+1);
			us2.setLost(us2.getLost()+1);
			usuarioNegocio.save(us1);
			usuarioNegocio.save(us2);

		} else {
			LOGGER.info("Error " + user1 + " : " + keyctrl + " - " + key);
		}
		ActionContext.getContext().getSession().remove("OponentId");
	}

	public void settingUp() {
		ActionContext.getContext().getSession().put("OponentId", key);
	}

	public String prueba() {
		String aux2 = "cual";
		Usuario usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);

		deck = deckNegocio.findById(usuario.getDeckPred());
		deck1 = new ArrayList<Carta>();

		int count = 0;

		for (Carta carta : deck.getCartas()) {

			for (int i = 0; i < deck.getDeckCartas().get(count).getCartaQt(); i++) {
				Carta aux = new Carta();
				aux.setIdTemp(i + carta.getNumero()
						+ carta.getEdicion().getSiglas());
				aux.setNombre(carta.getNombre());
				aux.setNumero(carta.getNumero());
				aux.setEfecto(carta.getEfecto());
				aux.setRaza(carta.getRaza());
				aux.setTipo(carta.getTipo());
				aux.setFrecuencia(carta.getFrecuencia());
				aux.setCoste(carta.getCoste());
				aux.setFuerza(carta.getFuerza());
				aux.setSiglas(carta.getEdicion().getSiglas());
				deck1.add(aux);
			}
			count++;
		}

		return aux2;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public DeckNegocio getDeckNegocio() {
		return deckNegocio;
	}

	public void setDeckNegocio(DeckNegocio deckNegocio) {
		this.deckNegocio = deckNegocio;
	}

	public List<Carta> getDeck1() {
		return deck1;
	}

	public void setDeck1(List<Carta> deck1) {
		this.deck1 = deck1;
	}

	public String getUser1() {
		return user1;
	}

	public void setUser1(String user1) {
		this.user1 = user1;
	}

	public String getUser2() {
		return user2;
	}

	public void setUser2(String user2) {
		this.user2 = user2;
	}

	public Integer getDeckId() {
		return deckId;
	}

	public void setDeckId(Integer deckId) {
		this.deckId = deckId;
	}

	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKeyctrl() {
		return keyctrl;
	}

	public void setKeyctrl(String keyctrl) {
		this.keyctrl = keyctrl;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public Duelo getDuelo() {
		return duelo;
	}

	public void setDuelo(Duelo duelo) {
		this.duelo = duelo;
	}

	public DueloNegocio getDueloNegocio() {
		return dueloNegocio;
	}

	public void setDueloNegocio(DueloNegocio dueloNegocio) {
		this.dueloNegocio = dueloNegocio;
	}

	public Usuario getUs1() {
		return us1;
	}

	public void setUs1(Usuario us1) {
		this.us1 = us1;
	}

	public Usuario getUs2() {
		return us2;
	}

	public void setUs2(Usuario us2) {
		this.us2 = us2;
	}

}
