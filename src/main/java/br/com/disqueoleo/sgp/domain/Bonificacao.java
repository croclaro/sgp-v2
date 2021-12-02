package br.com.disqueoleo.sgp.domain;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Bonificacao extends Generico {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = true)
	private Funcionario funcionario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = true)
	private Fornecedor fornecedor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = true)
	private Afiliado afiliado;

	@Column(length = 20, nullable = false)
	private String dataBonificacao;
	
	@Column(nullable = true, precision = 6, scale = 2)
	private BigDecimal valor;

	// MÉTODO GETTER AND SETTERS ...
	// MÉTODO GET LEITURA
	// MÉTODO SET ESCRITA ...
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}	

	public String getDataBonificacao() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void setDataBonificacao(String dataBonificacao) {
		this.dataBonificacao = dataBonificacao;
	}

	public Afiliado getAfiliado() {
		return afiliado;
	}

	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}
	
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
