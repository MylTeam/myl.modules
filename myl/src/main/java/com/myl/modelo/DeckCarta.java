package com.myl.modelo;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
@Table(name = "DeckCarta")
public class DeckCarta {

	private Integer deckId;
	private Integer cartaId;
	private Integer cartaQt;

	private Carta carta;
	private Deck deck;
	private DeckCartaId deckCartaId;

	public DeckCarta() {

	}

	public DeckCarta(Integer deckId, Integer cartaId) {
		this.deckCartaId = new DeckCartaId();
		this.deckCartaId.setDeckId(deckId);
		this.deckCartaId.setCartaId(cartaId);
	}

	public DeckCarta(DeckCartaId deckCartaId) {
		this.deckCartaId = deckCartaId;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "deck", "carta");
	}

	@Column(name = "DeckId", insertable = false, updatable = false)
	public Integer getDeckId() {
		return deckId;
	}

	public void setDeckId(Integer deckId) {
		this.deckId = deckId;
	}

	@Column(name = "CartaId", insertable = false, updatable = false)
	public Integer getCartaId() {
		return cartaId;
	}

	public void setCartaId(Integer cartaId) {
		this.cartaId = cartaId;
	}

	@Column(name = "CartaQt")
	public Integer getCartaQt() {
		return cartaQt;
	}

	public void setCartaQt(Integer cartaQt) {
		this.cartaQt = cartaQt;
	}

	@EmbeddedId
	public DeckCartaId getDeckCartaId() {
		return deckCartaId;
	}

	public void setDeckCartaId(DeckCartaId deckCartaId) {
		this.deckCartaId = deckCartaId;
	}

	@ManyToOne
	@JoinColumn(name = "DeckId", referencedColumnName = "DeckId", insertable = false, updatable = false)
	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	@ManyToOne
	@JoinColumn(name = "CartaId", referencedColumnName = "CartaId", insertable = false, updatable = false)
	public Carta getCarta() {
		return carta;
	}

	public void setCarta(Carta carta) {
		this.carta = carta;
	}

}