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
import br.com.disqueoleo.sgp.dao.ClienteDAO;
import br.com.disqueoleo.sgp.domain.Banco;
import br.com.disqueoleo.sgp.domain.CEP;
import br.com.disqueoleo.sgp.domain.Cliente;
import br.com.disqueoleo.sgp.domain.ValidaCNPJ;
import br.com.disqueoleo.sgp.domain.ValidaCPF;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {
	private Cliente cliente;

	private List<Cliente> clientes;
	private List<Banco> bancos;

	// MÉTODO GETTER AND SETTERS ...
	// MÉTODO GET LEITURA..
	// MÉTODO SET ESCRITA ...
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
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
			cliente = new Cliente();

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar o Cliente");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (cliente.getCnpj() == "" && (cliente.getCpf() == "")) {
				Messages.addGlobalError("CPF ou CNPJ não podem ficar em branco");
			} else if (cliente.getCpf() != "" && (cliente.getCnpj() != "")) {
				Messages.addGlobalError("Digite um CPF ou CNPJ");
			} else if (cliente.getTelFixo() == "" && (cliente.getCelular1() == "")) {
				Messages.addGlobalError("Você precisa digitar pelo menos um telefone para prosseguir.");
			} else if (ValidaCNPJ.isCNPJ(cliente.getCnpj()) == false
					&& (cliente.getCnpj() != "" && (cliente.getCpf() == ""))) {
				Messages.addGlobalError("O Cnpj " + cliente.getCnpj() + " está incorreto.");
			} else if (cliente.getCnpj() != "" && (cliente.getCpf() == "")) {
				
				ClienteDAO clienteDAO = new ClienteDAO();
				clienteDAO.merge(cliente);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Cliente salvo com sucesso!!!");
				
			} else if (ValidaCPF.isCPF(cliente.getCpf()) == false
					&& (cliente.getCpf() != "" && (cliente.getCnpj() == ""))) {
				Messages.addGlobalError("O CPF " + cliente.getCpf() + " está incorreto.");
			} else if (cliente.getCpf() != "" && (cliente.getCnpj() == "")) {
				
				ClienteDAO clienteDAO = new ClienteDAO();
				clienteDAO.merge(cliente);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Cliente salvo com sucesso!!!");
			} else if (cliente.getTelFixo() != "" && (cliente.getCelular1() == "")) {
				ClienteDAO clienteDAO = new ClienteDAO();
				clienteDAO.merge(cliente);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Cliente salvo com sucesso!!!");
			} else if (cliente.getTelFixo() == "" && (cliente.getCelular1() != "")) {
				ClienteDAO clienteDAO = new ClienteDAO();
				clienteDAO.merge(cliente);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Cliente salvo com sucesso!!!");
			} else if (cliente.getTelFixo() != "" && (cliente.getCelular1() != "")) {
				ClienteDAO clienteDAO = new ClienteDAO();
				clienteDAO.merge(cliente);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Cliente salvo com sucesso!!!");
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao cadastrar o Cliente.");
			erro.printStackTrace();
		}
	}

	public void consultarCEP() {
		String cepSemMascara = cliente.getCep().replace(".", "").replace("-", "").replace("_", "");
		String url = "http://cep.republicavirtual.com.br/web_cep.php?cep=" + cepSemMascara + "&formato=json";

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);

		Response response = target.request().get();
		String json = response.readEntity(String.class);

		Gson gson = new Gson();
		CEP cep = gson.fromJson(json, CEP.class);

		if (cep.getResultado().equals("0")) {
			cliente.setBairro(null);
			cliente.setCidade(null);
			cliente.setLogradouro(null);
			cliente.setEstado(null);

			Messages.addGlobalWarn("O campo CEP é inválido");
		} else {
			cliente.setBairro(cep.getBairro());
			cliente.setCidade(cep.getCidade());
			cliente.setLogradouro(cep.getTipo_logradouro() + " " + cep.getLogradouro());
			cliente.setEstado(cep.getUf());
		}
	}
}
