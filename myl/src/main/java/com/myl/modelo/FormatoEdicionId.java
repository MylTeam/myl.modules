package com.myl.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Embeddable
public class FormatoEdicionId implements java.io.Serializable {
			
	private static final long serialVersionUID = -6939513911005249280L;
	
	private Integer edicionId=null;
	private Integer formatoId=null;
				
	public FormatoEdicionId() {	
	}
	public FormatoEdicionId(Integer edicionId, Integer formatoId) {
		this.edicionId=edicionId;
		this.formatoId=formatoId;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "");
	}
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}
	
	@Column(name = "EdicionId", nullable = false)			
	public Integer getEdicionId() {
		return edicionId;
	}
	public void setEdicionId(Integer edicionId) {
		this.edicionId = edicionId;
	}
	@Column(name = "FormatoId", nullable = false)
	public Integer getFormatoId() {
		return formatoId;
	}
	public void setFormatoId(Integer formatoId) {
		this.formatoId = formatoId;
	}

}