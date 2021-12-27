package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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

import br.com.disqueoleo.sgp.dao.AfiliadoDAO;
import br.com.disqueoleo.sgp.dao.BancoDAO;
import br.com.disqueoleo.sgp.dao.FornecedorDAO;
import br.com.disqueoleo.sgp.dao.UsuarioDAO;
import br.com.disqueoleo.sgp.domain.Afiliado;
import br.com.disqueoleo.sgp.domain.Banco;
import br.com.disqueoleo.sgp.domain.CEP;
import br.com.disqueoleo.sgp.domain.EnviarEmail;
import br.com.disqueoleo.sgp.domain.Fornecedor;
import br.com.disqueoleo.sgp.domain.Usuario;

//ATUALIZADO MASTER ....

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AfiliadoBean implements Serializable {
	private Afiliado afiliado;
	private List<Afiliado> afiliados;
	private List<Banco> bancos;
	private EnviarEmail enviarEmail;

	@ManagedProperty("#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;
	
	private String acao;

	// MÉTODO GETTER AND SETTERS ...
	// MÉTODO GET LEITURA..
	// MÉTODO SET ESCRITA ...
	public Afiliado getAfiliado() {
		return afiliado;
	}

	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public EnviarEmail getEnviarEmail() {
		return enviarEmail;
	}

	public void setEnviarEmail(EnviarEmail enviarEmail) {
		this.enviarEmail = enviarEmail;
	}

	public List<Afiliado> getAfiliados() {
		return afiliados;
	}

	public void setAfiliados(List<Afiliado> afiliados) {
		this.afiliados = afiliados;
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
			afiliado = new Afiliado();
			enviarEmail = new EnviarEmail();

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();
			
			String codigo = Faces.getRequestParameter("codigo");

			if (codigo == null) {
				acao = "N";
			} else {
				acao = "U";
			}

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar o afiliado");
			erro.printStackTrace();
		}
	}

	public void upgrade() {
		String codigo = Faces.getRequestParameter("codigo");

		if (codigo == null) {
			acao = "N";
			Faces.navigate("confEmail.xhtml?faces-redirect=true");
		}

		AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
		afiliado = afiliadoDAO.buscar(Long.valueOf(codigo));

		if (afiliado == null) {
			acao = "U";
			Faces.navigate("confEmail.xhtml?faces-redirect=true");
		}
	}

	public void salvar() {
		try {
			if (afiliado.getCpf() == "") {
				Messages.addGlobalError("O campo CPF não pode ficar vazio");
			} else if (afiliado.getCpf() != "") {

				AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				FornecedorDAO fornecedorDAO = new FornecedorDAO();
				
				Usuario usuarioLogado = autenticacaoBean.getUsuario();
				
				// Admin
				if (usuarioLogado.getFuncionario().getCodigo() != null) {
					Usuario usuario = new Usuario();
					usuario.setAfiliado(afiliado);

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

					enviarEmail.enviarEmailUsuarioAfiliado(usuarioSalvo);
				} 
				
				// Não Admin - Novo
				else if (acao.equals("N")) {						
					Fornecedor fornecedorUsuarioLogado = fornecedorDAO.buscarPorCPFOuCNPJ(usuarioLogado.getLogin());

					if (usuarioLogado.getFuncionario().getCodigo() == null) {
						afiliado.setFornecedor(fornecedorUsuarioLogado);
					}

					Afiliado afiliadoSalvo = afiliadoDAO.merge(afiliado);
					
					enviarEmail.enviarEmailAfiliado(afiliadoSalvo);
				} 
				
				// Não Admim - Upgrade
				else if (acao.equals("U")) {
					Afiliado afiliadoSalvo = afiliadoDAO.merge(afiliado);
					
					Usuario usuario = new Usuario();
					usuario.setAfiliado(afiliado);

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

					enviarEmail.enviarEmailUsuarioAfiliado(usuarioSalvo);

					Faces.navigate("confEmail.xhtml?faces-redirect=true");
				} 

				cadastrar();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addFlashGlobalInfo("Afiliado salvo com sucesso!!!");
				Messages.addFlashGlobalInfo("Você receberá um email para cadastrar o seu login");
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao cadastrar o afiliado");
			erro.printStackTrace();
		}
	}

	public void consultarCEP() {
		String cepSemMascara = afiliado.getCep().replace(".", "").replace("-", "").replace("_", "");
		String cepSemMascara1 = afiliado.getCep().replace(".", "").replace("-", "").replace("_", "");

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
			afiliado.setBairro(null);
			afiliado.setCidade(null);
			afiliado.setLogradouro(null);
			afiliado.setEstado(null);
			Messages.addGlobalWarn("O campo CEP é inválido");
		} else if (cep1.getResultado().equals("0")) {
			afiliado.setBairro(null);
			afiliado.setCidade(null);
			afiliado.setLogradouro(null);
			afiliado.setEstado(null);

			Messages.addGlobalWarn("O campo CEP é inválido");
		} else {
			afiliado.setBairro(cep.getBairro());
			afiliado.setCidade(cep.getCidade());
			afiliado.setLogradouro(cep.getTipo_logradouro() + " " + cep.getLogradouro());
			afiliado.setEstado(cep.getUf());
		}
	}
}