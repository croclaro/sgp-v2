package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.BancoDAO;
import br.com.disqueoleo.sgp.dao.BeneficiarioDAO;
import br.com.disqueoleo.sgp.domain.Banco;
import br.com.disqueoleo.sgp.domain.Beneficiario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ConsultaBeneficiariosBean implements Serializable {
	private Beneficiario beneficiario;
	private List<Beneficiario> beneficiarios;
	private List<Banco> bancos;

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public List<Beneficiario> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<Beneficiario> beneficiarios) {
		this.beneficiarios = beneficiarios;
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

			BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
			beneficiarios = beneficiarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os beneficiarios");
			erro.printStackTrace();
		}
	}

	public void cadastrar() {
		try {
			beneficiario = new Beneficiario();
			BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
			beneficiarios = beneficiarioDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao cadastrar o beneficiário!!!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
			beneficiarioDAO.merge(beneficiario);

			cadastrar();

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();

			beneficiarios = beneficiarioDAO.listar();

			Messages.addGlobalInfo("O Beneficiário foi alterado com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao alterar o beneficiário!!!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			beneficiario = (Beneficiario) evento.getComponent().getAttributes().get("beneficiarioSelecionado");

			BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
			beneficiarioDAO.excluir(beneficiario);

			Messages.addGlobalInfo("Beneficiario excluido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao excluir o beneficiario.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			beneficiario = (Beneficiario) evento.getComponent().getAttributes().get("beneficiarioSelecionado");

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao editar o beneficiario.");
			erro.printStackTrace();
		}
	}
}
