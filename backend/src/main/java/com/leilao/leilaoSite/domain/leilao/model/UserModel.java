package com.leilao.leilaoSite.domain.leilao.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.leilao.leilaoSite.presentation.user.UserDTO;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TB_USER", uniqueConstraints = {@UniqueConstraint(columnNames = "username", name = "UK_USERNAME"), 
@UniqueConstraint(columnNames = "email", name = "UK_EMAIL"),
@UniqueConstraint(columnNames = "cpf", name = "UK_CPF"),})
public class UserModel implements Serializable {

	private static final long serialVersionUID = -6518853480190451215L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "USERNAME", nullable = false)
	private String username;
	
	@Column(name = "EMAIL", nullable = false)
	private String email;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@Column(name = "CPF", nullable = false)
	private String CPF;
	
	@Column(name = "IS_ADMIN")
	private boolean isAdmin;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_NASCIMENTO", nullable = false)
	private Date dataNascimento;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "ENDERECO_FK")
	private EnderecoModel endereco;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name =  "PRODUTOS_ADQUIRIDOS_FK")
	private List<ProdutoModel> produtosArrematado;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name =  "PRODUTOS_LEILOAR_FK")
	private List<ProdutoModel> produtosLeiloar;
	
	public UserModel() {
	}

	public UserModel (UserDTO userDTO) {
		this.dataNascimento = userDTO.getDataNascimento();
		this.email = userDTO.getEmail();
		this.CPF = userDTO.getCpf();
		this.endereco = userDTO.getEndereco();
		this.password = userDTO.getSenha();
		this.username = userDTO.getUsername();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((CPF == null) ? 0 : CPF.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserModel other = (UserModel) obj;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!removeTime(dataNascimento).equals(removeTime(other.dataNascimento)))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (CPF == null) {
			if (other.CPF != null)
				return false;
		} else if (!CPF.equals(other.CPF))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	private Date removeTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + username + ", email=" + email + ", CPF=" + CPF
				+ ", birthday=" + dataNascimento + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public EnderecoModel getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoModel endereco) {
		this.endereco = endereco;
	}

	public List<ProdutoModel> getProdutosArrematado() {
		return produtosArrematado;
	}

	public void setProdutosArrematado(List<ProdutoModel> produtosArrematado) {
		this.produtosArrematado = produtosArrematado;
	}

	public List<ProdutoModel> getProdutosLeiloar() {
		return produtosLeiloar;
	}

	public void setProdutosLeiloar(List<ProdutoModel> produtosLeiloar) {
		this.produtosLeiloar = produtosLeiloar;
	}
	
	public void addProdutoLeiloar (ProdutoModel produtoModel) {
		produtosLeiloar.add(produtoModel);
	}

	public void addProdutoArrematado (ProdutoModel produtoModel) {
		produtosArrematado.add(produtoModel);
	}
}