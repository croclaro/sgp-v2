package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.BancoDAO;
import br.com.disqueoleo.sgp.dao.ClienteDAO;
import br.com.disqueoleo.sgp.domain.Banco;
import br.com.disqueoleo.sgp.domain.Cliente;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ConsultaClientesBean implements Serializable {
	private Cliente cliente;
	private List<Cliente> clientes;
	private List<Banco> bancos;
	
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
	public void listar() {
		try {

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os clientes");
			erro.printStackTrace();
		}
	}

	public void cadastrar() {
		cliente = new Cliente();
	}
	
	public void salvar() {
		try {

			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.merge(cliente);

			cadastrar();

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();

			clientes = clienteDAO.listar();

			Messages.addGlobalInfo("O Cliente foi editado com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao editar o cliente!!!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");

			ClienteDAO ClienteDAO = new ClienteDAO();
			ClienteDAO.excluir(cliente);

			Messages.addGlobalInfo("Cliente excluido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao excluir o cliente.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();
		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}
	}
}
