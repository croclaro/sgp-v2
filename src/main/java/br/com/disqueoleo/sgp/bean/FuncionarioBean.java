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

import br.com.disqueoleo.sgp.dao.FuncaoDAO;
import br.com.disqueoleo.sgp.dao.FuncionarioDAO;
import br.com.disqueoleo.sgp.dao.TipoSanguineoDAO;
import br.com.disqueoleo.sgp.domain.CEP;
import br.com.disqueoleo.sgp.domain.Funcao;
import br.com.disqueoleo.sgp.domain.Funcionario;
import br.com.disqueoleo.sgp.domain.TipoSanguineo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable {
	private Funcionario funcionario;

	private List<Funcionario> funcionarios;
	private List<Funcao> funcoes;
	private List<TipoSanguineo> tiposSanguineos;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<Funcao> funcoes) {
		this.funcoes = funcoes;
	}

	public List<TipoSanguineo> getTiposSanguineos() {
		return tiposSanguineos;
	}

	public void setTiposSanguineos(List<TipoSanguineo> tiposSanguineos) {
		this.tiposSanguineos = tiposSanguineos;
	}

	@PostConstruct

	public void cadastrar() {
		try {
			funcionario = new Funcionario();

			FuncaoDAO funcaoDAO = new FuncaoDAO();
			funcoes = funcaoDAO.listar();

			TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
			tiposSanguineos = tipoSanguineoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar um novo funcionário");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (funcionario.getCpf() == "") {
				Messages.addGlobalError("Você precisa digitar um CPF para continuar.");
			} else if (funcionario.getTelFixo() == "" && (funcionario.getCelular1() == "")) {
				Messages.addGlobalError("Você precisa digitar pelo menos um telefone para prosseguir.");
			} else if (funcionario.getTelFixo() != ""
					&& (funcionario.getCelular1() == "" && (funcionario.getCpf() != ""))) {
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				funcionarioDAO.merge(funcionario);

				cadastrar();

				FuncaoDAO funcaoDAO = new FuncaoDAO();
				funcoes = funcaoDAO.listar();

				TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
				tiposSanguineos = tipoSanguineoDAO.listar();

				Messages.addGlobalInfo("Funcionario salvo com sucesso!!!");
			} else if (funcionario.getTelFixo() == "" && (funcionario.getCelular1() != "")) {
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				funcionarioDAO.merge(funcionario);

				cadastrar();

				FuncaoDAO funcaoDAO = new FuncaoDAO();
				funcoes = funcaoDAO.listar();

				TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
				tiposSanguineos = tipoSanguineoDAO.listar();

				Messages.addGlobalInfo("Funcionario salvo com sucesso!!!");
			} else if (funcionario.getTelFixo() != "" && (funcionario.getCelular1() != "")) {
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				funcionarioDAO.merge(funcionario);

				cadastrar();

				FuncaoDAO funcaoDAO = new FuncaoDAO();
				funcoes = funcaoDAO.listar();

				TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
				tiposSanguineos = tipoSanguineoDAO.listar();

				Messages.addGlobalInfo("Funcionario salvo com sucesso!!!");
			} else if (funcionario.getCaminho() == null) {
				Messages.addGlobalError("Você precisa buscar uma foto antes de continuar.");
			} else if (funcionario.getCaminho() != null) {
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				funcionarioDAO.merge(funcionario);

				cadastrar();

				FuncaoDAO funcaoDAO = new FuncaoDAO();
				funcoes = funcaoDAO.listar();

				TipoSanguineoDAO tipoSanguineoDAO = new TipoSanguineoDAO();
				tiposSanguineos = tipoSanguineoDAO.listar();

				Messages.addGlobalInfo("Funcionario salvo com sucesso!!!");
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao cadastrar o Funcionário.");

			erro.printStackTrace();
		}
	}

	public void consultarCEP() {
		String cepSemMascara = funcionario.getCep().replace(".", "").replace("-", "").replace("_", "");
		String url = "http://cep.republicavirtual.com.br/web_cep.php?cep=" + cepSemMascara + "&formato=json";

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);

		Response response = target.request().get();
		String json = response.readEntity(String.class);

		Gson gson = new Gson();
		CEP cep = gson.fromJson(json, CEP.class);

		if (cep.getResultado().equals("0")) {
			funcionario.setBairro(null);
			funcionario.setCidade(null);
			funcionario.setLogradouro(null);
			funcionario.setEstado(null);

			Messages.addGlobalWarn("O campo CEP é inválido");
		} else {
			funcionario.setBairro(cep.getBairro());
			funcionario.setCidade(cep.getCidade());
			funcionario.setLogradouro(cep.getTipo_logradouro() + " " + cep.getLogradouro());
			funcionario.setEstado(cep.getUf());
		}
	}

	/*
	 * public void upload(FileUploadEvent evento) { try { UploadedFile arquivoUpload
	 * = evento.getFile(); Path arquivoTemp = Files.createTempFile(null, null);
	 * Files.copy(arquivoUpload.getInputstream(), arquivoTemp,
	 * StandardCopyOption.REPLACE_EXISTING);
	 * funcionario.setCaminho(arquivoTemp.toString()); } catch (IOException erro) {
	 * Messages.
	 * addGlobalInfo("Ocorreu um erro ao tentar realizar o upload do arquivo");
	 * erro.printStackTrace(); } }
	 */
}
