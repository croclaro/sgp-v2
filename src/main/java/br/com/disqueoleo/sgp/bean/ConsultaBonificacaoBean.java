package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.BonificacaoDAO;
import br.com.disqueoleo.sgp.dao.FornecedorDAO;
import br.com.disqueoleo.sgp.dao.FuncionarioDAO;
import br.com.disqueoleo.sgp.domain.Bonificacao;
import br.com.disqueoleo.sgp.domain.Fornecedor;
import br.com.disqueoleo.sgp.domain.Funcionario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ConsultaBonificacaoBean implements Serializable {
	private Bonificacao bonificacao;
	private Funcionario funcionario;
	private Fornecedor fornecedor;
	private List<Bonificacao> bonificacoes;
	private List<Funcionario> funcionarios;
	private List<Fornecedor> fornecedores;

	public Bonificacao getBonificacao() {
		return bonificacao;
	}

	public void setBonificacao(Bonificacao bonificacao) {
		this.bonificacao = bonificacao;
	}

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

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<Bonificacao> getBonificacoes() {
		return bonificacoes;
	}

	public void setBonificacoes(List<Bonificacao> bonificacoes) {
		this.bonificacoes = bonificacoes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	@PostConstruct
	public void listar() {
		try {

			BonificacaoDAO bonificacaoDAO = new BonificacaoDAO();
			bonificacaoDAO.merge(bonificacao);
			
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar();

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os beneficiarios");
			erro.printStackTrace();
		}
	}

	public void cadastrar() {
		try {
			bonificacao = new Bonificacao();			

			BonificacaoDAO bonificacaoDAO = new BonificacaoDAO();
			bonificacaoDAO.merge(bonificacao);
			
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar();

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao cadastrar a Bonificação do fornecedor.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			BonificacaoDAO bonificacaoDAO = new BonificacaoDAO();
			bonificacaoDAO.merge(bonificacao);
			
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar();

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();

			cadastrar();

			Messages.addGlobalInfo("Bonificação salva com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro");
			erro.printStackTrace();
		}

	}
	
	public void excluir(ActionEvent evento) {
		try {
			fornecedor = (Fornecedor) evento.getComponent().getAttributes().get("fornecedorSelecionado");

			

			Messages.addGlobalInfo("Bonificação excluida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao excluir a bonificação.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			fornecedor = (Fornecedor) evento.getComponent().getAttributes().get("fornecedorSelecionado");

			BonificacaoDAO bonificacaoDAO = new BonificacaoDAO();
			bonificacaoDAO.merge(bonificacao);
			
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar();

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();
		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}
	}

}
