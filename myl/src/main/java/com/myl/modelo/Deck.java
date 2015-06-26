package com.myl.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Deck")
public class Deck {

	private Integer deckId;
	private String deckNombre;
	private Integer usuarioId;
	private Integer formatoId;

	private Usuario usuario;
	private Formato formato;
	private List<Carta> cartas;
	private List<DeckCarta> deckCartas;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "DeckId")
	public Integer getDeckId() {
		return deckId;
	}

	public void setDeckId(Integer deckId) {
		this.deckId = deckId;
	}

	@Column(name = "DeckNombre")
	public String getDeckNombre() {
		return deckNombre;
	}

	public void setDeckNombre(String deckNombre) {
		this.deckNombre = deckNombre;
	}

	@Column(name = "UsuarioId")
	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	@Column(name = "FormatoId")
	public Integer getFormatoId() {
		return formatoId;
	}

	public void setFormatoId(Integer formatoId) {
		this.formatoId = formatoId;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "UsuarioId", referencedColumnName = "UsuarioId", insertable = false, updatable = false) })
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "FormatoId", referencedColumnName = "FormatoId", insertable = false, updatable = false) })
	public Formato getFormato() {
		return formato;
	}

	public void setFormato(Formato formato) {
		this.formato = formato;
	}

	@ManyToMany
	@JoinTable(name = "DeckCarta", joinColumns = { @JoinColumn(name = "DeckId") }, inverseJoinColumns = { @JoinColumn(name = "CartaId") })
	public List<Carta> getCartas() {
		return cartas;
	}

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}

	@OneToMany(mappedBy = "deck")
	public List<DeckCarta> getDeckCartas() {
		return deckCartas;
	}

	public void setDeckCartas(List<DeckCarta> deckCartas) {
		this.deckCartas = deckCartas;
	}

}