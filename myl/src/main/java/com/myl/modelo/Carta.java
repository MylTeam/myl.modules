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
import javax.persistence.Transient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "Carta")
public class Carta implements Cloneable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Carta.class);
	private Integer id;
	private String nombre;
	private String efecto;
	private String leyenda;
	private String numero;
	private String tipo;
	private String raza;
	private String frecuencia;
	private Integer coste;
	private Integer fuerza;
	private Integer idEdicion;

	private String idTemp;
	private String siglas;
	private Integer cantidad;

	private Edicion edicion;

	private List<Deck> decks;
	private List<DeckCarta> deckCartas;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "CartaId")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CartaNb")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "CartaEf")
	public String getEfecto() {
		return efecto;
	}

	public void setEfecto(String efecto) {
		this.efecto = efecto;
	}

	@Column(name = "CartaLy")
	public String getLeyenda() {
		return leyenda;
	}

	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
	}

	@Column(name = "CartaNo")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Column(name = "CartaTp")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(name = "CartaRz")
	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	@Column(name = "CartaFr")
	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	@Column(name = "CartaCt")
	public Integer getCoste() {
		return coste;
	}

	public void setCoste(Integer coste) {
		this.coste = coste;
	}

	@Column(name = "CartaFz")
	public Integer getFuerza() {
		return fuerza;
	}

	public void setFuerza(Integer fuerza) {
		this.fuerza = fuerza;
	}

	@Column(name = "EdicionId")
	public Integer getIdEdicion() {
		return idEdicion;
	}

	public void setIdEdicion(Integer idEdicion) {
		this.idEdicion = idEdicion;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "EdicionId", referencedColumnName = "EdicionId", insertable = false, updatable = false) })
	public Edicion getEdicion() {
		return edicion;
	}

	public void setEdicion(Edicion edicion) {
		this.edicion = edicion;
	}

	@ManyToMany
	@JoinTable(name = "DeckCarta", joinColumns = { @JoinColumn(name = "CartaId") }, inverseJoinColumns = { @JoinColumn(name = "DeckId") })
	public List<Deck> getDecks() {
		return decks;
	}

	public void setDecks(List<Deck> decks) {
		this.decks = decks;
	}

	@OneToMany(mappedBy = "carta")
	public List<DeckCarta> getDeckCartas() {
		return deckCartas;
	}

	public void setDeckCartas(List<DeckCarta> deckCartas) {
		this.deckCartas = deckCartas;
	}

	@Transient
	public String getIdTemp() {
		return idTemp;
	}

	public void setIdTemp(String idTemp) {
		this.idTemp = idTemp;
	}

	@Transient
	public String getSiglas() {
		return siglas;
	}

	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}

	@Transient
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			LOGGER.debug("No se puede clonar", e);
		}
		return clone;
	}
}