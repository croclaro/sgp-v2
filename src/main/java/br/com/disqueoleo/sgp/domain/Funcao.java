package br.com.disqueoleo.sgp.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Funcao extends Generico {

	@Column(length = 30, nullable = false, unique = true)
	private String nomeFuncao;

	@Column(length = 14, nullable = false, unique = true)
	private Long codBarras = (long) (10000000000000L + Math.random() * 89999999999999L);

	@Column(length = 20, nullable = false)
	private String dataCadastro;

	public String getNomeFuncao() {
		return nomeFuncao;
	}

	public void setNomeFuncao(String nomeFuncao) {
		this.nomeFuncao = nomeFuncao;
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
