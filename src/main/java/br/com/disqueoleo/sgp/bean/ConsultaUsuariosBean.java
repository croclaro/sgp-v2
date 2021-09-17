
package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.UsuarioDAO;
import br.com.disqueoleo.sgp.domain.Funcionario;
import br.com.disqueoleo.sgp.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ConsultaUsuariosBean implements Serializable {
	private Usuario usuario;
	private List<Funcionario> funcionarios;
	private List<Usuario> usuarios;

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

	@PostConstruct
	public void listar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarios = usuarioDAO.listar("tipo");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os usuários");
			erro.printStackTrace();
		}
	}

	public void cadastrar() {
		usuario = new Usuario();
	}

	public void salvar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();

			usuarioDAO.merge(usuario);

			cadastrar();

			usuarios = usuarioDAO.listar("tipo");

			Messages.addGlobalInfo("O Status foi alterado com sucesso!!!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Úsuário já existe");
			erro.printStackTrace();
		}
	}

	public void salvarSenha() {
		try {
			if(usuario.getSenha().equals(usuario.getConfSenha())){
				UsuarioDAO usuarioDAO = new UsuarioDAO();

				SimpleHash hash = new SimpleHash("md5", usuario.getSenha());
				usuario.setSenha(hash.toHex());

				usuarioDAO.merge(usuario);

				cadastrar();

				usuarios = usuarioDAO.listar("tipo");

				Messages.addGlobalInfo("A senha foi alterada com sucesso!!!");
			}else{
				Messages.addGlobalError("Senha não conferem!!!");
			}
			

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao editar a senha!!!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");

			Messages.addGlobalInfo("Usuário excluido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao excluir o usuário.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");

			UsuarioDAO usuarioDAO = new UsuarioDAO();

			usuarios = usuarioDAO.listar();

			Messages.addGlobalInfo("Usuário editado com sucesso");
		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}
	}
}
