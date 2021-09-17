package br.com.disqueoleo.sgp.domain;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CPF;

@SuppressWarnings("serial")
@Entity // QUER DIZER QUE É UMA ENTIDADE. O HIBERNATE VAI CRIAR UMA TABELA
public class Afiliado extends Generico {

	@Column(length = 100, nullable = false)
	private String nomeCompleto;

	@CPF(message = "CPF inválido!!!")
	@Column(length = 11, nullable = false, unique = true)
	private String cpf;

	@Email(message = "EMAIL INVÁLIDO!!!")
	@Column(length = 100, nullable = false, unique = true)
	private String email;

	@Column(length = 100, nullable = true)
	private String telFixo;

	@Column(length = 100, nullable = true)
	private String celular1;

	@Column(length = 100, nullable = true)
	private String celular2;

	@Column(length = 100, nullable = true)
	private String celular3;

	@Column(length = 10, nullable = true)
	private String cep;

	@Column(length = 100, nullable = true)
	private String logradouro;

	@Column(length = 100, nullable = true)
	private Long numero;

	@Column(length = 100, nullable = true)
	private String complemento;

	@Column(length = 100, nullable = true)
	private String pontoReferencia;

	@Column(length = 100, nullable = true)
	private String bairro;

	@Column(length = 100, nullable = true)
	private String cidade;

	@Column(length = 100, nullable = true)
	private String estado;

	@Column(length = 20, nullable = true)
	private String dataAfiliado;

	@Column(nullable = true, precision = 6, scale = 2)
	private BigDecimal valor;

	@Column(length = 15, nullable = true)
	private String conta;

	@Column(length = 10, nullable = true)
	private String agencia;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Banco banco;

	@Column(nullable = true)
	private Boolean status;	
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Fornecedor fornecedor;

	// MÉTODO GETTER AND SETTERS ...
	// MÉTODO GET LEITURA
	// MÉTODO SET ESCRITA ...

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf.replaceAll("\\D", "").replace("-", "");
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

	public String getPontoReferencia() {
		return pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
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

	public String getDataAfiliado() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void setDataAfiliado(String dataAfiliado) {
		this.dataAfiliado = dataAfiliado;
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

	public Boolean getStatus() {
		return status;
	}
	
	@Transient
	public String getStatusFormatado() {
		String statusFormatado = "Não";

		if (status) {
			statusFormatado = "Sim";
		}

		return statusFormatado;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
}