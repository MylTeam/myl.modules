package com.myl.modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.myl.util.IntegerAdapter;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;


@XmlRootElement
@Entity
@Table(name = "Usuario")
public class Usuario {

	private Integer idUsuario;
	private String login;
	private String password;
	private Integer deckPred;
	
	private Integer wons;
	private Integer lost;
	
	private String email;
	private Date fhRegistro;
	private Date fhLastSession;
	private Integer idPais;
	
	private Boolean verificado;
	private Long codigo;
	
	private Boolean estatus;
	private Integer diasRestantes;
	
	private Boolean tieneDeck;	
	private List<Deck> decks;
		
	
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "UsuarioId")
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	@Column(name = "UsuarioNb")
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	@Column(name = "UsuarioPs")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "UsuarioDk")
	public Integer getDeckPred() {
		return deckPred;
	}
	public void setDeckPred(Integer deckPred) {
		this.deckPred = deckPred;
	}
	
	@XmlTransient
	@OneToMany(mappedBy = "usuario")
	public List<Deck> getDecks() {
		return decks;
	}
	public void setDecks(List<Deck> decks) {
		this.decks = decks;
	}
	
	@Transient
	public Boolean getTieneDeck() {
		return tieneDeck;
	}
	public void setTieneDeck(Boolean tieneDeck) {
		this.tieneDeck = tieneDeck;
	}
	
	@RequiredStringValidator(fieldName = "model.email", type = ValidatorType.FIELD, key = "Introduce tu correo electrónico")
	@EmailValidator(fieldName = "model.email", type = ValidatorType.FIELD, message = "Correo electrónico no válido")
	@Column(name = "UsuarioEm")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "Fh_registro")
	public Date getFhRegistro() {
		return fhRegistro;
	}
	public void setFhRegistro(Date fhRegistro) {
		this.fhRegistro = fhRegistro;
	}
	
	@Column(name = "Fh_last")
	public Date getFhLastSession() {
		return fhLastSession;
	}
	public void setFhLastSession(Date fhLastSession) {
		this.fhLastSession = fhLastSession;
	}
	
	@Column(name = "id_pais")
	public Integer getIdPais() {
		return idPais;
	}
	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}
	
	@Column(name = "dl_won")
	public Integer getWons() {
		return wons;
	}
	public void setWons(Integer wons) {
		this.wons = wons;
	}
	
	@Column(name = "dl_lost")
	public Integer getLost() {
		return lost;
	}
	public void setLost(Integer lost) {
		this.lost = lost;
	}
	
	@Column(name = "em_verificado")
	public Boolean getVerificado() {
		return verificado;
	}
	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}
	
	@Column(name = "cd_verificacion")
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	@Column(name = "estatus")
	public Boolean getEstatus() {
		return estatus;
	}
	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
	
	@Column(name = "vf_diasrestantes")
	public Integer getDiasRestantes() {
		return diasRestantes;
	}
	public void setDiasRestantes(Integer diasRestantes) {
		this.diasRestantes = diasRestantes;
	}
	
}
