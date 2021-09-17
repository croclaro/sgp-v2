package br.com.disqueoleo.sgp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Retirada extends Generico {
	@ManyToOne
	@JoinColumn(nullable = false)
	private Fornecedor fornecedor;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Produto produto;

	@ManyToOne
	private Beneficiario beneficiario;

	@Column(length = 20, nullable = false)
	private String dataRetirada;

	@Column(length = 10)
	private Long residuo;

	@Column(length = 10)
	private Long oleo;

	@Column(length = 10)
	private Long Lampada;

	@Column(length = 20)
	private String formaPagamento;

	// MÉTODO GETTER AND SETTERS ...
	// MÉTODO GET LEITURA
	// MÉTODO SET ESCRITA ...
	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(String dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public Long getResiduo() {
		return residuo;
	}

	public void setResiduo(Long residuo) {
		this.residuo = residuo;
	}

	public Long getOleo() {
		return oleo;
	}

	public void setOleo(Long oleo) {
		this.oleo = oleo;
	}

	public Long getLampada() {
		return Lampada;
	}

	public void setLampada(Long lampada) {
		Lampada = lampada;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}
}
