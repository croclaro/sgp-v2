package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.AfiliadoDAO;
import br.com.disqueoleo.sgp.dao.FornecedorDAO;
import br.com.disqueoleo.sgp.dao.FuncionarioDAO;
import br.com.disqueoleo.sgp.dao.UsuarioDAO;
import br.com.disqueoleo.sgp.domain.Afiliado;
import br.com.disqueoleo.sgp.domain.Fornecedor;
import br.com.disqueoleo.sgp.domain.Funcionario;
import br.com.disqueoleo.sgp.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {
	private Usuario usuario;
	private Fornecedor fornecedor;
	private Afiliado afiliado;

	private List<Funcionario> funcionarios;
	private List<Usuario> usuarios;
	private List<Fornecedor> fornecedores;
	private List<Afiliado> afiliados;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
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

	@PostConstruct
	public void cadastrar() {
		try {
			usuario = new Usuario();			

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar("nome");
			
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedores = fornecedorDAO.listar("nomeFantasia");
			
			AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
			afiliados = afiliadoDAO.listar("cpf");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar criar um novo usuário");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (usuario.getSenha().equals(usuario.getConfSenha())) {
				UsuarioDAO usuarioDAO = new UsuarioDAO();

				SimpleHash hash = new SimpleHash("md5", usuario.getSenha());
				usuario.setSenha(hash.toHex());

				usuarioDAO.merge(usuario);

				usuario = new Usuario();

				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				funcionarios = funcionarioDAO.listar("nome");
				
				FornecedorDAO fornecedorDAO = new FornecedorDAO();
				fornecedores = fornecedorDAO.listar("nomeFantasia");
				
				AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
				afiliados = afiliadoDAO.listar("cpf");

				Messages.addGlobalInfo("Usuário salvo com sucesso");
			} else {
				Messages.addGlobalError("Senha não confere!!!");
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Usuário já existe");
			erro.printStackTrace();
		}
	}
}