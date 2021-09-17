package br.com.disqueoleo.sgp.domain;

import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class FormaPagamento extends Generico {
	
	String troca;
	String dinheiro;

	public String getTroca() {
		return troca;
	}

	public void setTroca(String troca) {
		this.troca = troca;
	}

	public String getDinheiro() {
		return dinheiro;
	}

	public void setDinheiro(String dinheiro) {
		this.dinheiro = dinheiro;
	}

}
