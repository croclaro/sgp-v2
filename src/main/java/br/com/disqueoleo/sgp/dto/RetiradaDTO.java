package br.com.disqueoleo.sgp.dto;

public class RetiradaDTO {
	private Integer ano;
	private Integer mes;
	private Integer oleo;
	private Integer residuo;

	public RetiradaDTO(Integer ano, Integer mes, Integer oleo, Integer residuo) {
		super();
		this.ano = ano;
		this.mes = mes;
		this.oleo = oleo;
		this.residuo = residuo;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getOleo() {
		return oleo;
	}

	public void setOleo(Integer oleo) {
		this.oleo = oleo;
	}

	public Integer getResiduo() {
		return residuo;
	}

	public void setResiduo(Integer residuo) {
		this.residuo = residuo;
	}

	@Override
	public String toString() {
		return "RetiradaDTO [ano=" + ano + ", mes=" + mes + ", oleo=" + oleo + ", residuo=" + residuo + "]";
	}
}
