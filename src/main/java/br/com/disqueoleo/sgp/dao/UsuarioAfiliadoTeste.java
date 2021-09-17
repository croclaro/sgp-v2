package br.com.disqueoleo.sgp.dao;

import org.junit.Test;

import br.com.disqueoleo.sgp.domain.Afiliado;

public class UsuarioAfiliadoTeste {

	/*@Test
	public void salvar(){
		AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
		Afiliado afiliado = afiliadoDAO.buscar(14L);
		
		System.out.println("Pessoa encontrada");
		System.out.println("Nome: " +afiliado.getNomeCompleto());
		System.out.println("Cpf: " +afiliado.getCpf());
		System.out.println("Código: " +afiliado.getCodigo());
			
		Usuario usuario = new Usuario();
		usuario.setStatus(true);
		usuario.setAfiliado(afiliado);
		usuario.setSenha("12345678");
		usuario.setTipo('A');
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.salvar(usuario);
		
		System.out.println("Usuário salvo com sucesso");
	}*/
	
	@Test
	public void salvar(){
				
		Afiliado afiliado = new Afiliado();		
		afiliado.setNomeCompleto("celso teste");
		afiliado.setCpf("02106490704");
		afiliado.setEmail("croclaro@yahoo.com.br");
		afiliado.setTelFixo("2122953917");
		afiliado.setCep("22290160");
		afiliado.setNumero(new Long(66));
		afiliado.setCidade("rio de janeiro");
		afiliado.setDataAfiliado("23/08/2021 14:54:00");
		afiliado.setEstado("rj");
		afiliado.setLogradouro("rua lauro muller");
		afiliado.setValor(null);
		afiliado.setStatus(true);
		
		AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
		afiliadoDAO.salvar(afiliado);
		
		System.out.println("AFILIADO SALVO COM SUCESSO!!!");		
	}
}
