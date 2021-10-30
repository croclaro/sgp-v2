package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.FornecedorDAO;
import br.com.disqueoleo.sgp.domain.EnviarEmail;
import br.com.disqueoleo.sgp.domain.Fornecedor;
import br.com.disqueoleo.sgp.domain.ValidaCNPJ;
import br.com.disqueoleo.sgp.domain.ValidaCPF;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FornecedorIndicacaoBean implements Serializable {
	private Fornecedor fornecedor;
	private List<Fornecedor> fornecedores;	
	private EnviarEmail enviarEmail;

	// MÉTODO GETTER AND SETTERS ...
	// MÉTODO GET LEITURA..
	// MÉTODO SET ESCRITA ...
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

	public EnviarEmail getEnviarEmail() {
		return enviarEmail;
	}

	public void setEnviarEmail(EnviarEmail enviarEmail) {
		this.enviarEmail = enviarEmail;
	}

	@PostConstruct
	public void cadastrar() {
		try {
			fornecedor = new Fornecedor();
			enviarEmail = new EnviarEmail();			

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar o fornecedor indicado");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (fornecedor.getCnpj() == "" && (fornecedor.getCpf() == "")) {
				Messages.addGlobalError("CPF ou CNPJ não podem ficar em branco");
			} else if (fornecedor.getCpf() != "" && (fornecedor.getCnpj() != "")) {
				Messages.addGlobalError("Digite um CPF ou CNPJ");
			} else if (fornecedor.getTelFixo() == "" && (fornecedor.getCelular1() == "")) {
				Messages.addGlobalError("Você precisa digitar pelo menos um telefone para prosseguir.");
			} else if (ValidaCNPJ.isCNPJ(fornecedor.getCnpj()) == false
					&& (fornecedor.getCnpj() != "" && (fornecedor.getCpf() == ""))) {
				Messages.addGlobalError("O Cnpj " + fornecedor.getCnpj() + " está incorreto.");
			} else if (fornecedor.getCnpj() != "" && (fornecedor.getCpf() == "")) {

				FornecedorDAO fornecedorDAO = new FornecedorDAO();
				fornecedorDAO.merge(fornecedor);

				cadastrar();
				enviarEmail.enviarEmailFornecedor();				

				Messages.addGlobalInfo("Fornecedor indicado salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para terminar o cadastro.");

			} else if (ValidaCPF.isCPF(fornecedor.getCpf()) == false
					&& (fornecedor.getCpf() != "" && (fornecedor.getCnpj() == ""))) {
				Messages.addGlobalError("O CPF " + fornecedor.getCpf() + " está incorreto.");
			} else if (fornecedor.getCpf() != "" && (fornecedor.getCnpj() == "")) {

				FornecedorDAO fornecedorDAO = new FornecedorDAO();
				fornecedorDAO.merge(fornecedor);

				cadastrar();
				enviarEmail.enviarEmailFornecedor();				

				Messages.addGlobalInfo("Fornecedor salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para cadastrar o seu login");

			} else if (fornecedor.getTelFixo() != "" && (fornecedor.getCelular1() == "")) {
				FornecedorDAO fornecedorDAO = new FornecedorDAO();
				fornecedorDAO.merge(fornecedor);

				cadastrar();
				enviarEmail.enviarEmailFornecedor();				

				Messages.addGlobalInfo("Fornecedor salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para cadastrar o seu login");
			} else if (fornecedor.getTelFixo() == "" && (fornecedor.getCelular1() != "")) {
				FornecedorDAO fornecedorDAO = new FornecedorDAO();
				fornecedorDAO.merge(fornecedor);

				cadastrar();
				enviarEmail.enviarEmailFornecedor();
				

				Messages.addGlobalInfo("Fornecedor salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para cadastrar o seu login");
			} else if (fornecedor.getTelFixo() != "" && (fornecedor.getCelular1() != "")) {
				FornecedorDAO fornecedorDAO = new FornecedorDAO();
				fornecedorDAO.merge(fornecedor);

				cadastrar();
				enviarEmail.enviarEmailFornecedor();				

				Messages.addGlobalInfo("Fornecedor salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para cadastrar o seu login");

			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao cadastrar o Fornecedor");
			erro.printStackTrace();
		}
	}	
}
