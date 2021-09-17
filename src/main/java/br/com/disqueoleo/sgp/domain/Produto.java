package br.com.disqueoleo.sgp.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Produto extends Generico {
	@Column(length = 50, nullable = false, unique = true)
	private String descricao;

	@Column(length = 14, nullable = false, unique = true)
	private Long codBarras = (long) (10000000000000L + Math.random() * 89999999999999L);

	@Column(length = 20, nullable = false)
	private String dataCadastro;

	// MÉTODO GETTER AND SETTERS ...
	// MÉTODO GET LEITURA
	// MÉTODO SET ESCRITA ...
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getCodBarras() {
		
		return codBarras;
	}

	public void setCodBarras(Long codBarras) {
		this.codBarras = codBarras;
	}

	public String getDataCadastro() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
}
