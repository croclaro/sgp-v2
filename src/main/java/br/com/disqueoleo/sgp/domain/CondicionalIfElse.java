package br.com.disqueoleo.sgp.domain;

public class CondicionalIfElse {

	public static void main(String[] args) {

		/*
		 * @SuppressWarnings("resource") Scanner entrada = new
		 * Scanner(System.in);
		 * 
		 * int idade;
		 * 
		 * System.out.println("Digite a sua idade"); idade = entrada.nextInt();
		 * 
		 * if(idade >= 18){ System.out.println("Você é maior de idade"); }else{
		 * System.out.println("Você é menor de idade"); }
		 */

		/*
		 * int i = 1;
		 * 
		 * while(i<101){ System.out.println("FORÇA JOVEM VASCO"); ++i; }
		 */

		/*
		 * int i = 1; int j = 1; do{ System.out.println("FORÇA JOVEM VASCO");
		 * ++i; }while(i < 1);
		 * 
		 * while(j < 1){ System.out.println("VASCO DA GAMA"); ++j; }
		 */

		/*
		 * for(int i = 0; i < 100; ++i){
		 * System.out.println("FORÇA JOVEM VASCO"); }
		 */

		/*
		 * Scanner input = new Scanner(System.in); Random random = new Random();
		 * 
		 * boolean acertou = false; int tentativas = 10; int numeroSecreto =
		 * random.nextInt(); long chute = 0;
		 * 
		 * while(tentativas >0 && acertou == false){
		 * System.out.println("Qual o seu chute"); chute = input.nextLong();
		 * 
		 * if(numeroSecreto == chute){ System.out.println("Você acertou");
		 * acertou = true; }else if(chute < numeroSecreto){ --tentativas;
		 * System.out.println("Número muito pequeno " + tentativas +
		 * " tentativas restastes"); }else{ --tentativas;
		 * System.out.println("Número muito grande " + tentativas +
		 * " tentativas restastes"); } }
		 

		Scanner input = new Scanner(System.in);

		System.out.println("Selecione um número de 1 a 5: ");

		int num = input.nextInt();

		switch (num) {
		case 1:
			System.out.println("Você escolheu 1");
			break;
		case 2:
			System.out.println("Você escolheu 2");
			break;
		case 3:
			System.out.println("Você escolheu 3");
			break;
		case 4:
			System.out.println("Você escolheu 4");
			break;
		case 5:
			System.out.println("Você escolheu 5");
			break;
			default:
				System.out.println("Você precisa digitar um número de 1 a 5.");
				
		}*/
	}
}
