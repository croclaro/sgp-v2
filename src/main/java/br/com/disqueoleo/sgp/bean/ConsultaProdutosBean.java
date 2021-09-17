package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.ProdutoDAO;
import br.com.disqueoleo.sgp.domain.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ConsultaProdutosBean implements Serializable {
	private Produto produto;
	private List<Produto> produtos;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	@PostConstruct
	public void listar() {
		try {

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os produtos");
			erro.printStackTrace();
		}
	}

	public void cadastrar() {
		try{
			produto = new Produto();
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar();
		}catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao cadastrar o produto!!!");
			erro.printStackTrace();
		}
		
	}

	public void salvar() {
		try {

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.merge(produto);

			cadastrar();

			Messages.addGlobalInfo("Produto editado com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao editar o produto!!!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.excluir(produto);

			produtos = produtoDAO.listar();

			Messages.addGlobalInfo("Produto excluido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Não foi possível excluir o produto!!!");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar();

			Messages.addGlobalInfo("Produto editado com sucesso");
		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}
	}	
}
