package br.com.disqueoleo.sgp.main;

import java.util.Scanner;

import br.com.disqueoleo.sgp.domain.EnviarEmail;

public class TesteEmail {
	
	public static void main(String[] args) {
		EnviarEmail enviarEmail = new EnviarEmail();
		enviarEmail.getEmail();
		Scanner texto = new Scanner(System.in);
		String str;
		System.out.println("Digite seu email");
		str = texto.nextLine();
		System.out.println("Seu email foi enviado com sucesso" +str);
		enviarEmail.enviarEmailFornecedor();
		texto.close();
	}
}
