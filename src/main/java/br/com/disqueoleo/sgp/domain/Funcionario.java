package br.com.disqueoleo.sgp.domain;

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
@Entity
public class Funcionario extends Generico {
	@Column(length = 100, nullable = false)
	private String nome;

	@Column(length = 100, nullable = false)
	private String apelido;

	@CPF(message = "CPF inválido!!!")
	@Column(length = 11, nullable = false, unique = true)
	private String cpf;

	// CARTEIRA DE MOTORISTA
	@Column(length = 25)
	private String cnh;

	// MOPP – MOVIMENTAÇÃO OPERACIONAL DE PRODUTOS PERIGOSOS
	@Column(length = 25)
	private String mopp;

	@Column(length = 14, nullable = false, unique = true)
	private Long codBarras = (long) (10000000000000L + Math.random() * 89999999999999L);

	@Email(message = "Email inválido!!!")
	@Column(length = 100, nullable = false, unique = true)
	private String email;

	@Column(length = 100)
	private String telFixo;

	@Column(length = 100)
	private String celular1;

	@Column(length = 100)
	private String celular2;

	@Column(length = 100)
	private String celular3;

	@Column(length = 100, nullable = false)
	private String cep;

	@Column(length = 100, nullable = false)
	private String logradouro;

	@Column(length = 100, nullable = false)
	private Long numero;

	@Column(length = 100)
	private String complemento;

	@Column(length = 100, nullable = false)
	private String bairro;

	@Column(length = 100, nullable = false)
	private String cidade;

	@Column(length = 100, nullable = false)
	private String estado;

	@ManyToOne
	@JoinColumn(nullable = true)
	private TipoSanguineo tipoSanguineo;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Funcao funcao;

	@Column(length = 20, nullable = false)
	private String dataFuncionario;

	@Transient
	private String caminho;

	// MÉTODO GETTER AND SETTERS ...
	// MÉTODO GET LEITURA..
	// MÉTODO SET ESCRITA ...
	public String getNome() {
		return nome;
	}

	public TipoSanguineo getTipoSanguineo() {
		return tipoSanguineo;
	}

	public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf.replaceAll("\\D", "").replace("-", "");
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

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
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

	public String getDataFuncionario() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void setDataFuncionario(String dataFuncionario) {
		this.dataFuncionario = dataFuncionario;
	}

	public String getCnh() {
		return cnh;
	}

	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	public String getMopp() {
		return mopp;
	}

	public void setMopp(String mopp) {
		this.mopp = mopp;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

}
