package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.omnifaces.util.Messages;

import com.google.gson.Gson;

import br.com.disqueoleo.sgp.dao.AfiliadoDAO;
import br.com.disqueoleo.sgp.dao.BancoDAO;
import br.com.disqueoleo.sgp.domain.Afiliado;
import br.com.disqueoleo.sgp.domain.Banco;
import br.com.disqueoleo.sgp.domain.CEP;


//ATUALIZADO MASTER ....

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class AfiliadoBean implements Serializable {
	private Afiliado afiliado;
	private List<Afiliado> afiliados;
	private List<Banco> bancos;

	// MÉTODO GETTER AND SETTERS ...
	// MÉTODO GET LEITURA..
	// MÉTODO SET ESCRITA ...
	public Afiliado getAfiliado() {
		return afiliado;
	}

	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
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
			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao gerar o afiliado");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (afiliado.getCpf() == "") {
				Messages.addGlobalError("CPF não pode ficar em branco");
			} else if (afiliado.getTelFixo() == "" && (afiliado.getCelular1() == "")) {
				Messages.addGlobalError("Você precisa digitar pelo menos um telefone para prosseguir.");

				AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
				afiliadoDAO.merge(afiliado);

				cadastrar();
				enviarEmailAfiliado();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Afiliado salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para cadastrar o seu login");
			} else if (afiliado.getCpf() != "") {
				AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
				afiliadoDAO.merge(afiliado);

				cadastrar();
				enviarEmailAfiliado();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Afiliado salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para cadastrar o seu login");
			} else if (afiliado.getTelFixo() != "" && (afiliado.getCelular1() == "")) {
				AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
				afiliadoDAO.merge(afiliado);

				cadastrar();
				enviarEmailAfiliado();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Afiliado salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para cadastrar o seu login");
			} else if (afiliado.getTelFixo() == "" && (afiliado.getCelular1() != "")) {
				AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
				afiliadoDAO.merge(afiliado);

				cadastrar();
				enviarEmailAfiliado();

				Messages.addGlobalInfo("Afiliado salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para cadastrar o seu login");
			} else if (afiliado.getTelFixo() != "" && (afiliado.getCelular1() != "")) {
				AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
				afiliadoDAO.merge(afiliado);

				cadastrar();
				enviarEmailAfiliado();

				BancoDAO bancoDAO = new BancoDAO();
				bancos = bancoDAO.listar();

				Messages.addGlobalInfo("Afiliado salvo com sucesso!!!");
				Messages.addGlobalInfo("Você receberá um email para cadastrar o seu login");
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Afiliado Já existe!!!");
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

	public void enviarEmailAfiliado() {		
		
		Properties props = new Properties();
		/** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", "mail.sgp-disqueoleo.com.br");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("admin@sgp-disqueoleo.com.br", "C3lso1359");
			}
		});

		/** Ativa Debug para sessão */
		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("admin@sgp-disqueoleo.com.br"));
			// Remetente
			
			Address[] toUser = InternetAddress.parse(getAfiliado().getEmail());
			
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("SGP - Sistema Gerenciador de Produtos");// Assunto
			message.setText("Seu email foi enviado por: " +getAfiliado().getNomeCompleto());

			/** Método para enviar a mensagem criada */
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
