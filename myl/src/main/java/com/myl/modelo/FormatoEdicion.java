package com.myl.modelo;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
@Table(name = "formato_edicion_tbl")
public class FormatoEdicion {

	private Integer edicionId;
	private Integer formatoId;

	private Formato formato;
	private Edicion edicion;
	private FormatoEdicionId formatoEdicionId;

	public FormatoEdicion() {

	}

	public FormatoEdicion(Integer edicionId, Integer formatoId) {
		this.formatoEdicionId = new FormatoEdicionId();
		this.formatoEdicionId.setEdicionId(edicionId);
		this.formatoEdicionId.setFormatoId(formatoId);
	}

	public FormatoEdicion(FormatoEdicionId formatoEdicionId) {
		this.formatoEdicionId = formatoEdicionId;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "edicion", "formato");
	}
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Column(name = "EdicionId", insertable = false, updatable = false)
	public Integer getEdicionId() {
		return edicionId;
	}

	public void setEdicionId(Integer edicionId) {
		this.edicionId = edicionId;
	}

	@Column(name = "FormatoId", insertable = false, updatable = false)
	public Integer getFormatoId() {
		return formatoId;
	}

	public void setFormatoId(Integer formatoId) {
		this.formatoId = formatoId;
	}

	@EmbeddedId
	public FormatoEdicionId getFormatoEdicionId() {
		return formatoEdicionId;
	}

	public void setFormatoEdicionId(FormatoEdicionId formatoEdicionId) {
		this.formatoEdicionId = formatoEdicionId;
	}

	@ManyToOne
	@JoinColumn(name = "EdicionId", referencedColumnName = "EdicionId", insertable = false, updatable = false)
	public Edicion getEdicion() {
		return edicion;
	}

	public void setEdicion(Edicion edicion) {
		this.edicion = edicion;
	}

	@ManyToOne
	@JoinColumn(name = "FormatoId", referencedColumnName = "FormatoId", insertable = false, updatable = false)
	public Formato getFormato() {
		return formato;
	}

	public void setFormato(Formato formato) {
		this.formato = formato;
	}

}