package com.myl.modelo;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "formato_tbl")
public class Formato {
	private Integer id;
	private String nombre;
	
	private List<Deck> decks;
	
	private List<Edicion> ediciones;
	private List<FormatoEdicion> formatoediciones;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "FormatoId")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "FormatoNb")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	@OneToMany(mappedBy = "formato")
	public List<Deck> getDecks() {
		return decks;
	}
	public void setDecks(List<Deck> decks) {
		this.decks = decks;
	}
	
	@ManyToMany
	@JoinTable(name = "formato_edicion_tbl", joinColumns = { @JoinColumn(name = "FormatoId") }, inverseJoinColumns = { @JoinColumn(name = "EdicionId") })
	public List<Edicion> getEdiciones() {
		return ediciones;
	}
	public void setEdiciones(List<Edicion> ediciones) {
		this.ediciones = ediciones;
	}
	
	@OneToMany(mappedBy = "formato")
	public List<FormatoEdicion> getFormatoediciones() {
		return formatoediciones;
	}
	public void setFormatoediciones(List<FormatoEdicion> formatoediciones) {
		this.formatoediciones = formatoediciones;
	}			
}