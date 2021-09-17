package br.com.disqueoleo.sgp.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

import br.com.disqueoleo.sgp.dao.FuncionarioDAO;
import br.com.disqueoleo.sgp.dao.UsuarioDAO;
import br.com.disqueoleo.sgp.domain.Funcionario;
import br.com.disqueoleo.sgp.domain.Usuario;

public class teste {
	@Test
	public void salvar(){
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		Funcionario funcionario = funcionarioDAO.buscar(1l);
		
		Usuario usuario = new Usuario();
		usuario.setStatus(true);
		usuario.setFuncionario(funcionario);
		usuario.setDataUsuario("12/-12/2017 06:00:00");
		usuario.setSenha("123");
		
		SimpleHash hash = new SimpleHash("md5", usuario.getSenha());
		usuario.setSenha(hash.toHex());
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.salvar(usuario);
		
		System.out.println("SALVO COM SUCESSO");
	}
	
	
}
