package br.com.disqueoleo.sgp.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass //Anotação para dizer que essa classe não corresponde a uma tabela, mas que será usada por outros ...
public class Generico implements Serializable {
	@Id //Ele diz o hibernate que será uma chave primária ... 
	@GeneratedValue(strategy = GenerationType.AUTO) // Opção que o banco vai gerenciar o código. Vai gerar automaticamente.
	private Long codigo; //Long é o maior inteiro que o Java suporta ...
	
	// MÉTODO GETTERS AND SETTERS

	// MÉTODO GET LEITURA
	public Long getCodigo() {
		return codigo;
	}

	// MÉTODO SET ESCRITA
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return String.format("%s[codigo=%d]", getClass().getSimpleName(), getCodigo());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Generico other = (Generico) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
