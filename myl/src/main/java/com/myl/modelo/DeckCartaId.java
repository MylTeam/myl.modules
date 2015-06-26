package com.myl.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Embeddable
public class DeckCartaId implements java.io.Serializable {
			
	private static final long serialVersionUID = -6939513911005249280L;
	
	private Integer deckId=null;
	private Integer cartaId=null;
				
	public DeckCartaId() {	
	}
	public DeckCartaId(Integer deckId, Integer cartaId) {
		this.deckId=deckId;
		this.cartaId=cartaId;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "");
	}
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}
	
	@Column(name = "DeckId", nullable = false)			
	public Integer getDeckId() {
		return deckId;
	}
	public void setDeckId(Integer deckId) {
		this.deckId = deckId;
	}
	@Column(name = "CartaId", nullable = false)
	public Integer getCartaId() {
		return cartaId;
	}
	public void setCartaId(Integer cartaId) {
		this.cartaId = cartaId;
	}

}