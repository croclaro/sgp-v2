package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.FuncaoDAO;
import br.com.disqueoleo.sgp.domain.Funcao;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncaoBean implements Serializable {
	private Funcao funcao;
	private List<Funcao> funcoes;

	public Funcao getFuncao() {
		return funcao;
	}

	public List<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<Funcao> funcoes) {
		this.funcoes = funcoes;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	@PostConstruct
	public void cadastrar() {
		try {
			funcao = new Funcao();

			FuncaoDAO funcaoDAO = new FuncaoDAO();
			funcoes = funcaoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao cadastrar a função");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			FuncaoDAO funcaoDAO = new FuncaoDAO();
			funcaoDAO.merge(funcao);

			cadastrar();

			Messages.addGlobalInfo("Função salva com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("A função " +funcao.getNomeFuncao() + " já existe.");			
			erro.printStackTrace();
		}
	}
}