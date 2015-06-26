package com.myl.modelo;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
@Table(name = "duelo_tbl")
public class Duelo {

	private Integer winnerId;
	private Integer loserId;
	private Integer dueloQt;

	private Usuario winner;
	private Usuario loser;
	private DueloId dueloId;

	public Duelo() {

	}

	public Duelo(Integer winnerId, Integer loserId) {
		this.dueloId = new DueloId();
		this.dueloId.setWinnerId(winnerId);
		this.dueloId.setLoserId(loserId);
	}

	public Duelo(DueloId dueloId) {
		this.dueloId = dueloId;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "usuario", "usuario");
	}

	@Column(name = "id_winner", insertable = false, updatable = false)
	public Integer getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(Integer winnerId) {
		this.winnerId = winnerId;
	}

	@Column(name = "id_loser", insertable = false, updatable = false)
	public Integer getLoserId() {
		return loserId;
	}

	public void setLoserId(Integer loserId) {
		this.loserId = loserId;
	}

	@Column(name = "ct_duels")
	public Integer getDueloQt() {
		return dueloQt;
	}

	public void setDueloQt(Integer dueloQt) {
		this.dueloQt = dueloQt;
	}

	@EmbeddedId
	public DueloId getDueloId() {
		return dueloId;
	}

	public void setDueloId(DueloId dueloId) {
		this.dueloId = dueloId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_loser", referencedColumnName = "UsuarioId", insertable = false, updatable = false)
	public Usuario getLoser() {
		return loser;
	}

	public void setLoser(Usuario loser) {
		this.loser = loser;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_winner", referencedColumnName = "UsuarioId", insertable = false, updatable = false)
	public Usuario getWinner() {
		return winner;
	}

	public void setWinner(Usuario winner) {
		this.winner = winner;
	}

}