package br.com.disqueoleo.sgp.domain;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;

@SuppressWarnings("serial")
@Entity
public class Cliente extends Generico {
	@Column(length = 100, nullable = false)
	private String razaoSocial;

	@Column(length = 100, nullable = false)
	private String nomeFantasia;

	@Column(length = 14)
	private String cnpj;

	@Column(length = 11)
	private String cpf;

	@Column(length = 50)
	private String licencaOperacao;

	@Column(length = 50)
	private String numeroIbama;

	@Column(length = 30)
	private String inscricaoEstadual;

	@Column(length = 14, nullable = false, unique = true)
	private Long codBarras = (long) (10000000000000L + Math.random() * 89999999999999L);

	@Email(message = "EMAIL INVÁLIDO")
	@Column(length = 100, nullable = false)
	private String email;

	@Column(length = 100)
	private String telFixo;

	@Column(length = 100)
	private String celular1;

	@Column(length = 100)
	private String celular2;

	@Column(length = 100)
	private String celular3;

	@Column(length = 10, nullable = false)
	private String cep;

	@Column(length = 100, nullable = false)
	private String logradouro;

	@Column(length = 100, nullable = false)
	private Long numero;

	@Column(length = 100)
	private String complemento;

	@Column(length = 100)
	private String pontoReferencia;

	@Column(length = 100, nullable = false)
	private String bairro;

	@Column(length = 100, nullable = false)
	private String cidade;

	@Column(length = 100, nullable = false)
	private String estado;

	@Column(nullable = false)
	private String dataCliente;

	@Column(nullable = false, precision = 6, scale = 2)
	private BigDecimal valor;

	@Column(length = 15)
	private String conta;

	@Column(length = 10)
	private String agencia;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Banco banco;

	// MÉTODO GETTER AND SETTERS ...
	// MÉTODO GET LEITURA..
	// MÉTODO SET ESCRITA ...
	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getNumeroIbama() {
		return numeroIbama;
	}

	public void setNumeroIbama(String numeroIbama) {
		this.numeroIbama = numeroIbama;
	}

	public String getPontoReferencia() {
		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	public Long getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(Long codBarras) {
		this.codBarras = codBarras;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelFixo() {
		return telFixo;
	}

	public void setTelFixo(String telFixo) {
		this.telFixo = telFixo.replaceAll("\\D", "").replace("-", "");
	}

	public String getCelular1() {
		return celular1;
	}

	public void setCelular1(String celular1) {
		this.celular1 = celular1.replaceAll("\\D", "").replace("-", "");
	}

	public String getCelular2() {
		return celular2;
	}

	public void setCelular2(String celular2) {
		this.celular2 = celular2.replaceAll("\\D", "").replace("-", "");
	}

	public String getCelular3() {
		return celular3;
	}

	public void setCelular3(String celular3) {
		this.celular3 = celular3.replaceAll("\\D", "").replace("-", "");
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep.replaceAll("\\D", "").replace("-", "");
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDataCliente() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);

	}

	public void setDataCliente(String dataCliente) {
		this.dataCliente = dataCliente;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj.replaceAll("\\D", "").replace("-", "");
	}

	public void setCpf(String cpf) {
		this.cpf = cpf.replaceAll("\\D", "").replace("-", "");
	}

	public String getLicencaOperacao() {
		return licencaOperacao;
	}

	public void setLicencaOperacao(String licencaOperacao) {
		this.licencaOperacao = licencaOperacao;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}
}
