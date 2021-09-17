package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.omnifaces.util.Messages;

import com.google.gson.Gson;

import br.com.disqueoleo.sgp.dao.BancoDAO;
import br.com.disqueoleo.sgp.dao.BeneficiarioDAO;
import br.com.disqueoleo.sgp.domain.Banco;
import br.com.disqueoleo.sgp.domain.Beneficiario;
import br.com.disqueoleo.sgp.domain.CEP;
import br.com.disqueoleo.sgp.domain.ValidaCNPJ;
import br.com.disqueoleo.sgp.domain.ValidaCPF;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class BeneficiarioBean implements Serializable {
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

	public void cadastrar() {
		try {
			beneficiario = new Beneficiario();

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar um novo beneficiário");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (beneficiario.getCnpj() == "" && (beneficiario.getCpf() == "")) {
				Messages.addGlobalError("CPF ou CNPJ não podem ficar em branco");
			} else if (beneficiario.getCpf() != "" && (beneficiario.getCnpj() != "")) {
				Messages.addGlobalError("Digite um CPF ou CNPJ");
			} else if (beneficiario.getTelFixo() == "" && (beneficiario.getCelular1() == "")) {
				Messages.addGlobalError("Você precisa digitar pelo menos um telefone para prosseguir.");
			} else if (ValidaCNPJ.isCNPJ(beneficiario.getCnpj()) == false
					&& (beneficiario.getCnpj() != "" && (beneficiario.getCpf() == ""))) {
				Messages.addGlobalError("O Cnpj " + beneficiario.getCnpj() + " está incorreto.");
			} else if (beneficiario.getCnpj() != "" && (beneficiario.getCpf() == "")) {

				BeneficiarioDAO baneficiarioDAO = new BeneficiarioDAO();
				baneficiarioDAO.merge(beneficiario);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Beneficiário salvo com sucesso!!!");

			} else if (ValidaCPF.isCPF(beneficiario.getCpf()) == false
					&& (beneficiario.getCpf() != "" && (beneficiario.getCnpj() == ""))) {
				Messages.addGlobalError("O CPF " + beneficiario.getCpf() + " está incorreto.");
			} else if (beneficiario.getCpf() != "" && (beneficiario.getCnpj() == "")) {

				BeneficiarioDAO baneficiarioDAO = new BeneficiarioDAO();
				baneficiarioDAO.merge(beneficiario);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Beneficiário salvo com sucesso!!!");

			} else if (beneficiario.getCnpj() != "" && (beneficiario.getCpf() != "")) {
				Messages.addGlobalError("Digite um CPF ou CNPJ");

				BeneficiarioDAO baneficiarioDAO = new BeneficiarioDAO();
				baneficiarioDAO.merge(beneficiario);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Beneficiário salvo com sucesso!!!");
			} else if (beneficiario.getTelFixo() == "" && (beneficiario.getCelular1() != "")) {

				BeneficiarioDAO baneficiarioDAO = new BeneficiarioDAO();
				baneficiarioDAO.merge(beneficiario);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Beneficiário salvo com sucesso!!!");

			} else if (beneficiario.getTelFixo() != "" && (beneficiario.getCelular1() != "")) {

				BeneficiarioDAO baneficiarioDAO = new BeneficiarioDAO();
				baneficiarioDAO.merge(beneficiario);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Beneficiário salvo com sucesso!!!");
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao cadastrar o Beneficiário");
			erro.printStackTrace();

		}
	}

	public void consultarCEP() {
		String cepSemMascara = beneficiario.getCep().replace(".", "").replace("-", "").replace("_", "");
		String url = "http://cep.republicavirtual.com.br/web_cep.php?cep=" + cepSemMascara + "&formato=json";

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);

		Response response = target.request().get();
		String json = response.readEntity(String.class);

		Gson gson = new Gson();
		CEP cep = gson.fromJson(json, CEP.class);

		if (cep.getResultado().equals("0")) {
			beneficiario.setBairro(null);
			beneficiario.setCidade(null);
			beneficiario.setLogradouro(null);
			beneficiario.setEstado(null);

			Messages.addGlobalWarn("O campo CEP é inválido");
		} else {
			beneficiario.setBairro(cep.getBairro());
			beneficiario.setCidade(cep.getCidade());
			beneficiario.setLogradouro(cep.getTipo_logradouro() + " " + cep.getLogradouro());
			beneficiario.setEstado(cep.getUf());
		}
	}

}
