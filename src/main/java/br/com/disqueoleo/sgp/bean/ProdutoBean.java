package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.model.StreamedContent;

import br.com.disqueoleo.sgp.dao.ProdutoDAO;
import br.com.disqueoleo.sgp.domain.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean implements Serializable {
	private Produto produto;
	private List<Produto> produtos;
	private StreamedContent manual;

	public Produto getProduto() {
		return produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public StreamedContent getManual() {
		return manual;
	}

	public void setManual(StreamedContent manual) {
		this.manual = manual;
	}

	@PostConstruct
	public void cadastrar() {
		try {
			produto = new Produto();

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro cadastrar o produto");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.merge(produto);

			cadastrar();

			Messages.addGlobalInfo("produto salvo com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("O produto " + produto.getDescricao() + " já existe.");
			erro.printStackTrace();
		}
	}

}