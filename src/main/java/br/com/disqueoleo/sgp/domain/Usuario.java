package br.com.disqueoleo.sgp.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import br.com.disqueoleo.sgp.enums.TipoUsuario;

@SuppressWarnings("serial")
@Entity
public class Usuario extends Generico {
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = true)
	private Funcionario funcionario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = true)
	private Fornecedor fornecedor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = true)
	private Afiliado afiliado;

	@Transient
	private String senhaSemCriptografia;

	@Column(length = 32, nullable = false)
	private String senha;

	@Transient
	private String confSenha;

	@Column(length = 20, nullable = false)
	private String dataUsuario;

	@Column(nullable = false)
	private Boolean status;

	@Column(nullable = false)
	private Character tipo;
	
	@Transient
	private String login;
	
	@Column(nullable = true)
	private String token;
	
	@Transient
	private TipoUsuario tipoUsuario;

	// MÉTODO GETTER AND SETTERS ...
	// MÉTODO GET LEITURA
	// MÉTODO SET ESCRITA ...
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public String getSenhaSemCriptografia() {
		return senhaSemCriptografia;
	}

	public void setSenhaSemCriptografia(String senhaSemCriptografia) {
		this.senhaSemCriptografia = senhaSemCriptografia;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfSenha() {
		return confSenha;
	}

	public void setConfSenha(String confSenha) {
		this.confSenha = confSenha;
	}

	public Boolean getStatus() {
		return status;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Transient
	public String getStatusFormatado() {
		String statusFormatado = "Não";

		if (status) {
			statusFormatado = "Sim";
		}

		return statusFormatado;
	}

	public Character getTipo() {
		return tipo;
	}

	@Transient
	public String getTipoFormatado() {
		String tipoFormatado = null;

		if (tipo == null) {
			tipoFormatado = "";
		} else if (tipo == 'A') {
			tipoFormatado = "Administrador";
		} else if (tipo == 'U') {
			tipoFormatado = "Usuário";
		} else if (tipo == 'D') {
			tipoFormatado = "Desenvolvedor";
		}

		return tipoFormatado;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public String getDataUsuario() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void setDataUsuario(String dataUsuario) {
		this.dataUsuario = dataUsuario;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Afiliado getAfiliado() {
		return afiliado;
	}

	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}

	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}
	
	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
}