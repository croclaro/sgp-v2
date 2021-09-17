package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.BancoDAO;
import br.com.disqueoleo.sgp.domain.Banco;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ConsultaBancosBean implements Serializable {
	private Banco banco;
	private List<Banco> bancos;

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public List<Banco> getBancos() {
		return bancos;
	}

	public void setBancos(List<Banco> bancos) {
		this.bancos = bancos;
	}

	@PostConstruct
	public void listar() {
		try {

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os banco");
			erro.printStackTrace();
		}
	}

	public void cadastrar() {
		try {
			banco = new Banco();
			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao cadastrar o banco!!!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			BancoDAO bancoDAO = new BancoDAO();
			bancoDAO.merge(banco);

			cadastrar();

			Messages.addGlobalInfo("O Banco foi editado com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao editar o banco!!!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			banco = (Banco) evento.getComponent().getAttributes().get("bancoSelecionado");

			BancoDAO bancoDAO = new BancoDAO();
			bancoDAO.excluir(banco);

			bancos = bancoDAO.listar();

			Messages.addGlobalInfo("Banco excluido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao excluir o Banco.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			banco = (Banco) evento.getComponent().getAttributes().get("bancoSelecionado");

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();

			Messages.addGlobalInfo("Banco editado com sucesso");
		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}
	}
}
