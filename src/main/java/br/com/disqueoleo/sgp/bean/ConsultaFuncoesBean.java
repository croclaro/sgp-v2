package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.FuncaoDAO;
import br.com.disqueoleo.sgp.domain.Funcao;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ConsultaFuncoesBean implements Serializable {
	private Funcao funcao;
	private List<Funcao> funcoes;

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public List<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<Funcao> funcoes) {
		this.funcoes = funcoes;
	}

	@PostConstruct
	public void listar() {
		try {

			FuncaoDAO funcaoDAO = new FuncaoDAO();
			funcoes = funcaoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as funções");
			erro.printStackTrace();
		}
	}

	public void cadastrar() {
		try {
			funcao = new Funcao();
			FuncaoDAO funcaoDAO = new FuncaoDAO();
			funcoes = funcaoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao cadastrar a função!!!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			FuncaoDAO funcaoDAO = new FuncaoDAO();
			funcaoDAO.merge(funcao);

			cadastrar();

			Messages.addGlobalInfo("A Função foi editada com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao editar a função!!!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			funcao = (Funcao) evento.getComponent().getAttributes().get("funcaoSelecionada");

			FuncaoDAO funcaoDAO = new FuncaoDAO();
			funcaoDAO.excluir(funcao);

			funcoes = funcaoDAO.listar();

			Messages.addGlobalInfo("Função excluida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao excluir a função.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			funcao = (Funcao) evento.getComponent().getAttributes().get("funcaoSelecionada");

			FuncaoDAO funcaoDAO = new FuncaoDAO();
			funcoes = funcaoDAO.listar();

			Messages.addGlobalInfo("Função editada com sucesso");
		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}
	}
}
