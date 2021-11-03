package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.BeneficiarioDAO;
import br.com.disqueoleo.sgp.dao.FornecedorDAO;
import br.com.disqueoleo.sgp.dao.FuncionarioDAO;
import br.com.disqueoleo.sgp.dao.ProdutoDAO;
import br.com.disqueoleo.sgp.dao.RetiradaDao;
import br.com.disqueoleo.sgp.domain.Beneficiario;
import br.com.disqueoleo.sgp.domain.Fornecedor;
import br.com.disqueoleo.sgp.domain.Funcionario;
import br.com.disqueoleo.sgp.domain.Produto;
import br.com.disqueoleo.sgp.domain.Retirada;
import br.com.disqueoleo.sgp.enums.TipoUsuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ConsultaRetiradasBean implements Serializable {
	private Retirada retirada;
	private List<Fornecedor> fornecedores;
	private List<Beneficiario> beneficiarios;
	private List<Produto> produtos;
	private List<Funcionario> funcionarios;
	private List<Retirada> retiradas;

	@ManagedProperty("#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public Retirada getRetirada() {
		return retirada;
	}

	public void setRetirada(Retirada retirada) {
		this.retirada = retirada;
	}

	public List<Retirada> getRetiradas() {
		return retiradas;
	}

	public void setRetiradas(List<Retirada> retiradas) {
		this.retiradas = retiradas;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<Beneficiario> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<Beneficiario> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
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
			RetiradaDao retiradaDao = new RetiradaDao();
			if (autenticacaoBean.getUsuarioLogado().getTipoUsuario() == TipoUsuario.FORNECEDOR) {
				retiradas = retiradaDao
						.buscarPorRetirada(autenticacaoBean.getUsuarioLogado().getFornecedor().getCodigo());
			} else {
				retiradas = retiradaDao.listar();
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as retiradas.");
			erro.printStackTrace();
		}
	}

	public void cadastrar() {
		retirada = new Retirada();
	}

	public void salvar() {
		try {

			RetiradaDao retiradaDao = new RetiradaDao();
			retiradaDao.merge(retirada);

			cadastrar();

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();

			BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
			beneficiarios = beneficiarioDAO.listar();

			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar();

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar();

			retiradas = retiradaDao.listar();

			Messages.addGlobalInfo("Fornecedor editado com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao editar o fornecedor!!!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			retirada = (Retirada) evento.getComponent().getAttributes().get("retiradaSelecionada");

			RetiradaDao retiradaDao = new RetiradaDao();
			retiradaDao.excluir(retirada);

			retiradas = retiradaDao.listar();

			Messages.addGlobalInfo("Retirada excluida com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao excluir a retirada.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			retirada = (Retirada) evento.getComponent().getAttributes().get("retiradaSelecionada");

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();

			BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAO();
			beneficiarios = beneficiarioDAO.listar();

			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar();

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar();

		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}
	}
}
