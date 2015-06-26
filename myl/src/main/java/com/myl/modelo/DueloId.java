package com.myl.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Embeddable
public class DueloId implements java.io.Serializable {
			
	private static final long serialVersionUID = -6939513911005249280L;
	
	private Integer winnerId=null;
	private Integer loserId=null;
				
	public DueloId() {	
	}
	public DueloId(Integer winnerId, Integer loserId) {
		this.winnerId=winnerId;
		this.loserId=loserId;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "");
	}
	
	@Override
	public int hashCode() {

		return super.hashCode();
	}
	
	@Column(name = "id_winner", nullable = false)			
	public Integer getWinnerId() {
		return winnerId;
	}
	public void setWinnerId(Integer winnerId) {
		this.winnerId = winnerId;
	}
	@Column(name = "id_loser", nullable = false)
	public Integer getLoserId() {
		return loserId;
	}
	public void setLoserId(Integer loserId) {
		this.loserId = loserId;
	}

}