package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.BancoDAO;
import br.com.disqueoleo.sgp.domain.Banco;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class BancoBean implements Serializable {
	private Banco banco;

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	@PostConstruct
	public void cadastrar() {
		banco = new Banco();
	}
	
	public void salvar() {
		try {

			BancoDAO bancoDAO = new BancoDAO();
			bancoDAO.merge(banco);

			cadastrar();

			Messages.addGlobalInfo("Banco salvo com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("O banco " +banco.getNome() + " já existe" );
			erro.printStackTrace();
		}
	}
	
}