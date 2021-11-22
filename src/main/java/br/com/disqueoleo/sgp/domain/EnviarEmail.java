package br.com.disqueoleo.sgp.domain;

import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarEmail {		
	private List<Fornecedor> fornecedores;
	private List<Afiliado> afiliados;
	private Afiliado afiliado;
	private Fornecedor fornecedor;	
	

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public List<Afiliado> getAfiliados() {
		return afiliados;
	}

	public void setAfiliados(List<Afiliado> afiliados) {
		this.afiliados = afiliados;
	}

	public Afiliado getAfiliado() {
		return afiliado;
	}

	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}	
	
	public void enviarEmailFornecedor(Fornecedor fornecedor) {

		Properties props = new Properties();
		/** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", "mail.sgp-disqueoleo.com.br");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("admin@sgp-disqueoleo.com.br", "C3lso1359@#");
			}
		});

		/** Ativa Debug para sessão */
		session.setDebug(true);
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("admin@sgp-disqueoleo.com.br"));
			// Remetente

			Address[] toUser = InternetAddress // Destinatário(s)
					.parse(fornecedor.getEmail());

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("SGP - Sistema Gerenciador de Produtos");// Assunto
			
			StringBuilder texto = new StringBuilder();
			texto.append("Seja bem-vindo,  " +fornecedor.getRazaoSocial());
			texto.append("\n");
			texto.append("Clique no link abaixo para terminar o seu cadastro.");
			texto.append("\n");
			texto.append("http://localhost:8080/SGP/cad-fornecedorIndicadoUpgrade.xhtml?codigo=" + fornecedor.getCodigo());
			message.setText(texto.toString());

			/** Método para enviar a mensagem criada */
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void enviarEmailAfiliado(Afiliado afiliado) {

		Properties props = new Properties();
		/** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", "mail.sgp-disqueoleo.com.br");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("admin@sgp-disqueoleo.com.br", "C3lso1359@#");
			}
		});

		/** Ativa Debug para sessão */
		session.setDebug(true);
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("admin@sgp-disqueoleo.com.br"));
			// Remetente

			Address[] toUser = InternetAddress // Destinatário(s)
					.parse(afiliado.getEmail());

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("SGP - Sistema Gerenciador de Produtos");// Assunto
			
			StringBuilder texto = new StringBuilder();
			texto.append("Seja bem-vindo,  " +afiliado.getNomeCompleto());
			texto.append("\n");
			texto.append("Clique no link abaixo para terminar o seu cadastro.");
			texto.append("\n");
			texto.append("http://localhost:8080/SGP/cad-afiliadoIndicadoUpgrade.xhtml?codigo=" + afiliado.getCodigo());
			message.setText(texto.toString());

			/** Método para enviar a mensagem criada */
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void enviarEmailUsuario(Usuario usuario) {

		Properties props = new Properties();
		/** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", "mail.sgp-disqueoleo.com.br");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("admin@sgp-disqueoleo.com.br", "C3lso1359@#");
			}
		});

		/** Ativa Debug para sessão */
		session.setDebug(true);
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("admin@sgp-disqueoleo.com.br"));
			// Remetente

			Address[] toUser = InternetAddress // Destinatário(s)
					.parse(usuario.getFornecedor().getEmail());

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("SGP - Sistema Gerenciador de Produtos");// Assunto
			
			StringBuilder texto = new StringBuilder();
			texto.append("Token: " + usuario.getToken());
			texto.append("\n");
			texto.append(" CNPJ: " + usuario.getFornecedor().getCnpj());
			texto.append("\n");
			texto.append(" Senha Provisória: " + usuario.getSenhaSemCriptografia());
			texto.append("\n");
			texto.append("http://localhost:8080/SGP/bt-loginCodigo.xhtml?codigo=" + usuario.getFornecedor().getCodigo());
			message.setText(texto.toString());

			/** Método para enviar a mensagem criada */
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void enviarEmailUsuarioAfiliado(Usuario usuario) {

		Properties props = new Properties();
		/** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", "mail.sgp-disqueoleo.com.br");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("admin@sgp-disqueoleo.com.br", "C3lso1359@#");
			}
		});

		/** Ativa Debug para sessão */
		session.setDebug(true);
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("admin@sgp-disqueoleo.com.br"));
			// Remetente

			Address[] toUser = InternetAddress // Destinatário(s)
					.parse(usuario.getAfiliado().getEmail());

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("SGP - Sistema Gerenciador de Produtos");// Assunto
			
			StringBuilder texto = new StringBuilder();
			texto.append("Token: " + usuario.getToken());
			texto.append("\n");
			texto.append(" CNPJ: " + usuario.getAfiliado().getCpf());
			texto.append("\n");
			texto.append(" Senha Provisória: " + usuario.getSenhaSemCriptografia());
			texto.append("\n");
			texto.append("http://localhost:8080/SGP/bt-loginCodigo.xhtml?codigo=" + usuario.getAfiliado().getCodigo());
			message.setText(texto.toString());

			/** Método para enviar a mensagem criada */
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
