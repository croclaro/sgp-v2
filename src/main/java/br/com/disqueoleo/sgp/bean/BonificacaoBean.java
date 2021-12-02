package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.AfiliadoDAO;
import br.com.disqueoleo.sgp.dao.BonificacaoDAO;
import br.com.disqueoleo.sgp.dao.FornecedorDAO;
import br.com.disqueoleo.sgp.dao.FuncionarioDAO;
import br.com.disqueoleo.sgp.domain.Afiliado;
import br.com.disqueoleo.sgp.domain.Bonificacao;
import br.com.disqueoleo.sgp.domain.Fornecedor;
import br.com.disqueoleo.sgp.domain.Funcionario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class BonificacaoBean implements Serializable {
	private Funcionario funcionario;
	private Afiliado afiliado;
	private Fornecedor fornecedor;
	private Bonificacao bonificacao;
	private List<Fornecedor> fornecedores;
	private List<Bonificacao> bonificacoes;
	private List<Funcionario> funcionarios;
	private List<Afiliado> afiliados;

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Afiliado getAfiliado() {
		return afiliado;
	}

	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Afiliado> getAfiliados() {
		return afiliados;
	}

	public void setAfiliados(List<Afiliado> afiliados) {
		this.afiliados = afiliados;
	}

	public List<Bonificacao> getBonificacoes() {
		return bonificacoes;
	}

	public void setBonificacoes(List<Bonificacao> bonificacoes) {
		this.bonificacoes = bonificacoes;
	}

	public Bonificacao getBonificacao() {
		return bonificacao;
	}

	public void setBonificacao(Bonificacao bonificacao) {
		this.bonificacao = bonificacao;
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

	@PostConstruct
	public void cadastrar() {
		try {
			bonificacao = new Bonificacao();

			AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
			afiliados = afiliadoDAO.listar();

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();

			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao cadastrar a Bonificação do fornecedor.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			BonificacaoDAO bonificacaoDAO = new BonificacaoDAO();
			bonificacaoDAO.merge(bonificacao);

			AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
			afiliados = afiliadoDAO.listar();

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();
			
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar();

			cadastrar();

			Messages.addGlobalInfo("Bonificação salva com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro");
			erro.printStackTrace();
		}

	}
}
