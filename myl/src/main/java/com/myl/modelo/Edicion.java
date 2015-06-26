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
@Table(name = "Edicion")
public class Edicion {
	private Integer id;
	private String nombre;
	private String siglas;
	
	private List<Carta> cartas;
	
	private List<Formato> formatos;
	private List<FormatoEdicion> formatoEdiciones;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "EdicionId")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "EdicionNb")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "EdicionSg")
	public String getSiglas() {
		return siglas;
	}
	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}
	
	@OneToMany(mappedBy = "edicion")
	public List<Carta> getCartas() {
		return cartas;
	}
	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}
	
	@ManyToMany
	@JoinTable(name = "formato_edicion_tbl", joinColumns = { @JoinColumn(name = "EdicionId") }, inverseJoinColumns = { @JoinColumn(name = "FormatoId") })
	public List<Formato> getFormatos() {
		return formatos;
	}
	public void setFormatos(List<Formato> formatos) {
		this.formatos = formatos;
	}
	
	@OneToMany(mappedBy = "edicion")
	public List<FormatoEdicion> getFormatoEdiciones() {
		return formatoEdiciones;
	}
	public void setFormatoEdiciones(List<FormatoEdicion> formatoEdiciones) {
		this.formatoEdiciones = formatoEdiciones;
	}			
}