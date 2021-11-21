package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.google.gson.Gson;

import br.com.disqueoleo.sgp.dao.BancoDAO;
import br.com.disqueoleo.sgp.dao.FornecedorDAO;
import br.com.disqueoleo.sgp.dao.UsuarioDAO;
import br.com.disqueoleo.sgp.domain.Banco;
import br.com.disqueoleo.sgp.domain.CEP;
import br.com.disqueoleo.sgp.domain.EnviarEmail;
import br.com.disqueoleo.sgp.domain.Fornecedor;
import br.com.disqueoleo.sgp.domain.Usuario;
import br.com.disqueoleo.sgp.domain.ValidaCNPJ;
import br.com.disqueoleo.sgp.domain.ValidaCPF;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class FornecedorBean implements Serializable {
	private Fornecedor fornecedor;
	private List<Fornecedor> fornecedores;
	private List<Banco> bancos;
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

	public List<Banco> getBancos() {
		return bancos;
	}

	public void setBancos(List<Banco> bancos) {
		this.bancos = bancos;
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

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar o fornecedor");
			erro.printStackTrace();
		}
	}

	public void upgrade() {
		String codigo = Faces.getRequestParameter("codigo");

		if (codigo == null) {
			Faces.navigate("bt-login.xhtml?faces-redirect=true");
		}

		FornecedorDAO fornecedorDAO = new FornecedorDAO();
		fornecedor = fornecedorDAO.buscar(Long.valueOf(codigo));

		if (fornecedor == null) {
			Faces.navigate("bt-login.xhtml?faces-redirect=true");
		}
	}

	public void salvar() {
		try {
			if (fornecedor.getCnpj().isEmpty() && (fornecedor.getCpf().isEmpty())) {
				Messages.addGlobalError("CPF ou CNPJ não podem ficar em branco");
			} else if (fornecedor.getTelFixo().isEmpty() && (fornecedor.getCelular1().isEmpty())) {
				Messages.addGlobalError("Você precisa digitar pelo menos um telefone para prosseguir.");
			} else if (ValidaCNPJ.isCNPJ(fornecedor.getCnpj()) == false
					&& (!fornecedor.getCnpj().isEmpty() && (fornecedor.getCpf().isEmpty()))) {
				Messages.addGlobalError("O Cnpj " + fornecedor.getCnpj() + " está incorreto.");
			} else if (!fornecedor.getCnpj().isEmpty() && (fornecedor.getCpf().isEmpty())) {

				FornecedorDAO fornecedorDAO = new FornecedorDAO();
				Fornecedor fornecedorSalvo = fornecedorDAO.merge(fornecedor);

				if (fornecedor.getCodigo() == null) {
					enviarEmail.enviarEmailFornecedor(fornecedorSalvo);
				} else {
					UsuarioDAO usuarioDAO = new UsuarioDAO();

					Usuario usuario = new Usuario();
					usuario.setFornecedor(fornecedorSalvo);

					usuario.setDataUsuario(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date()));

					String senha = RandomStringUtils.randomAlphanumeric(6);
					SimpleHash hash = new SimpleHash("md5", senha);
					usuario.setSenha(hash.toHex());
					usuario.setSenhaSemCriptografia(senha);

					usuario.setStatus(true);

					String token = RandomStringUtils.randomNumeric(6);
					usuario.setToken(token);

					Usuario usuarioSalvo = usuarioDAO.merge(usuario);
					usuarioSalvo.setSenhaSemCriptografia(usuario.getSenhaSemCriptografia());

					enviarEmail.enviarEmailUsuario(usuarioSalvo);

					Faces.navigate("bt-login.xhtml?faces-redirect=true");
				}

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addFlashGlobalInfo("Fornecedor salvo com sucesso!!!");
				Messages.addFlashGlobalInfo("Você receberá um email para cadastrar o seu login");

			} else if (ValidaCPF.isCPF(fornecedor.getCpf()) == false
					&& (fornecedor.getCpf() != "" && (fornecedor.getCnpj() == ""))) {
				Messages.addGlobalError("O CPF " + fornecedor.getCpf() + " está incorreto.");
			} else if (fornecedor.getCpf() != "" && (fornecedor.getCnpj() == "")) {

				FornecedorDAO fornecedorDAO = new FornecedorDAO();
				fornecedorDAO.merge(fornecedor);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Fornecedor salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para cadastrar o seu login");

			} else if (fornecedor.getCpf().equals(fornecedor.getCpf())
					&& (fornecedor.getCnpj() != fornecedor.getCnpj())) {
				Messages.addGlobalError("O CPF " + fornecedor.getCpf() + " Já existe");
			} else if (fornecedor.getCnpj().equals(fornecedor.getCnpj())
					&& (fornecedor.getCpf() != fornecedor.getCpf())) {

				FornecedorDAO fornecedorDAO = new FornecedorDAO();
				fornecedorDAO.merge(fornecedor);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Fornecedor salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para cadastrar o seu login");

			} else if (fornecedor.getTelFixo() != "" && (fornecedor.getCelular1() == "")) {
				FornecedorDAO fornecedorDAO = new FornecedorDAO();
				fornecedorDAO.merge(fornecedor);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Fornecedor salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para cadastrar o seu login");
			} else if (fornecedor.getTelFixo() == "" && (fornecedor.getCelular1() != "")) {
				FornecedorDAO fornecedorDAO = new FornecedorDAO();
				fornecedorDAO.merge(fornecedor);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Fornecedor salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para cadastrar o seu login");
			} else if (fornecedor.getTelFixo() != "" && (fornecedor.getCelular1() != "")) {
				FornecedorDAO fornecedorDAO = new FornecedorDAO();
				fornecedorDAO.merge(fornecedor);

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Fornecedor salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para cadastrar o seu login");

			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao cadastrar o Fornecedor");
			erro.printStackTrace();
		}
	}

	public void consultarCEP() {
		String cepSemMascara = fornecedor.getCep().replace(".", "").replace("-", "").replace("_", "");
		String cepSemMascara1 = fornecedor.getCep().replace(".", "").replace("-", "").replace("_", "");

		String url = "http://cep.republicavirtual.com.br/web_cep.php?cep=" + cepSemMascara + "&formato=json";
		String url1 = "http://cep.republicavirtual.com.br/web_cep.php?cep=" + cepSemMascara1 + "&formato=json";

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		WebTarget target1 = client.target(url1);

		Response response = target.request().get();
		String json = response.readEntity(String.class);

		Response response1 = target1.request().get();
		String json1 = response1.readEntity(String.class);

		Gson gson = new Gson();
		CEP cep = gson.fromJson(json, CEP.class);

		Gson gson1 = new Gson();
		CEP cep1 = gson1.fromJson(json1, CEP.class);

		if (cep.getResultado().equals("0")) {
			fornecedor.setBairro(null);
			fornecedor.setCidade(null);
			fornecedor.setLogradouro(null);
			fornecedor.setEstado(null);
			Messages.addGlobalWarn("O campo CEP é inválido");
		} else if (cep1.getResultado().equals("0")) {
			fornecedor.setBairro(null);
			fornecedor.setCidade(null);
			fornecedor.setLogradouro(null);
			fornecedor.setEstado(null);

			Messages.addGlobalWarn("O campo CEP é inválido");
		} else {
			fornecedor.setBairro(cep.getBairro());
			fornecedor.setCidade(cep.getCidade());
			fornecedor.setLogradouro(cep.getTipo_logradouro() + " " + cep.getLogradouro());
			fornecedor.setEstado(cep.getUf());

			fornecedor.setBairro(cep1.getBairro());
			fornecedor.setCidade(cep1.getCidade());
			fornecedor.setLogradouro(cep1.getTipo_logradouro() + " " + cep1.getLogradouro());
			fornecedor.setEstado(cep1.getUf());
		}
	}
}
