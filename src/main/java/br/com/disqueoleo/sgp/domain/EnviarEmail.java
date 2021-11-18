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

import br.com.disqueoleo.sgp.main.TesteEmail;

public class EnviarEmail {
	TesteEmail emailFornecedor1 = new TesteEmail();
	private String email;
	private List<Fornecedor> fornecedores;
	private Fornecedor fornecedor;

	public TesteEmail getEmailFornecedor1() {
		return emailFornecedor1;
	}

	public void setEmailFornecedor1(TesteEmail emailFornecedor1) {
		this.emailFornecedor1 = emailFornecedor1;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
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

	public void enviarEmailFornecedor() {

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
					.parse("croclaro@yahoo.com.br");

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("SGP - Sistema Gerenciador de Produtos");// Assunto
			message.setText("Seu email foi enviado por: ");

			/** Método para enviar a mensagem criada */
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
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
			texto.append("Seu email foi enviado por: ");
			texto.append("http://localhost:8080/SGP/cad-fornecedorIndicadoUpgrade.xhtml?codigo=" + fornecedor.getCodigo());
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
			texto.append(" CNPJ: " + usuario.getFornecedor().getCnpj());
			texto.append(" Senha Provisória: " + usuario.getSenhaSemCriptografia());
			message.setText(texto.toString());

			/** Método para enviar a mensagem criada */
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
