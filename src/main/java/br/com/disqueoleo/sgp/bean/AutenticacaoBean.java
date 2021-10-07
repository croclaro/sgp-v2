package br.com.disqueoleo.sgp.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.AfiliadoDAO;
import br.com.disqueoleo.sgp.dao.FornecedorDAO;
import br.com.disqueoleo.sgp.dao.FuncionarioDAO;
import br.com.disqueoleo.sgp.dao.RetiradaDao;
import br.com.disqueoleo.sgp.dao.UsuarioDAO;
import br.com.disqueoleo.sgp.domain.Afiliado;
import br.com.disqueoleo.sgp.domain.Fornecedor;
import br.com.disqueoleo.sgp.domain.Funcionario;
import br.com.disqueoleo.sgp.domain.Usuario;
import br.com.disqueoleo.sgp.enums.TipoUsuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class AutenticacaoBean implements Serializable {
	private Usuario usuario;
	private Usuario usuarioLogado;
	private Usuario status;

	@SuppressWarnings("unused")
	private String data;
	private String usuarioNome;
	private String funcaoNome;
	private Long residuos;
	private Long oleos;
	private Long fornecedores;
	private String nomeIndicacao;
	private String dadosPessoais;

	public Usuario getUsuario() {
		return usuario;
	}

	public String getNomeIndicacao() {
		if (usuarioLogado.getTipoUsuario() == TipoUsuario.AFILIADO) {
			nomeIndicacao = "Indicação";
		} else if (usuarioLogado.getTipoUsuario() == TipoUsuario.FORNECEDOR) {
			nomeIndicacao = "Indicação";
		} else if (usuarioLogado.getTipoUsuario() == TipoUsuario.FUNCIONARIO) {
			nomeIndicacao = "Fornecedor";
		} else {
			nomeIndicacao = "Erro";
		}
		return nomeIndicacao;
	}

	public void setNomeIndicacao(String nomeIndicacao) {
		this.nomeIndicacao = nomeIndicacao;
	}

	public Long getFornecedores() {
		RetiradaDao retiradaDao = new RetiradaDao();
		fornecedores = retiradaDao.buscarFornecedores(usuarioLogado);
		return fornecedores;
	}

	public void setFornecedores(Long fornecedores) {
		this.fornecedores = fornecedores;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Usuario getStatus() {
		return status;
	}

	public void setStatus(Usuario status) {
		this.status = status;
	}

	public String getData() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getUsuarioNome() {
		if (usuarioLogado.getTipoUsuario() == TipoUsuario.AFILIADO) {
			usuarioNome = usuarioLogado.getAfiliado().getNomeCompleto();
		} else if (usuarioLogado.getTipoUsuario() == TipoUsuario.FORNECEDOR) {
			usuarioNome = usuarioLogado.getFornecedor().getNomeFantasia();
		} else if (usuarioLogado.getTipoUsuario() == TipoUsuario.FUNCIONARIO) {
			usuarioNome = usuarioLogado.getFuncionario().getNome();
		} else {
			usuarioNome = "ERRO";
		}
		return usuarioNome;
	}

	public String getDadosPessoais() {
		if (usuarioLogado.getTipoUsuario() == TipoUsuario.AFILIADO) {
			dadosPessoais = usuarioLogado.getAfiliado().getNomeCompleto();
			dadosPessoais = usuarioLogado.getAfiliado().getCpf();
			dadosPessoais = usuarioLogado.getAfiliado().getEmail();
			dadosPessoais = usuarioLogado.getAfiliado().getTelFixo();
			dadosPessoais = usuarioLogado.getAfiliado().getCelular1();
			dadosPessoais = usuarioLogado.getAfiliado().getCelular2();
			dadosPessoais = usuarioLogado.getAfiliado().getCelular3();
			dadosPessoais = usuarioLogado.getAfiliado().getCep();
			dadosPessoais = usuarioLogado.getAfiliado().getLogradouro();
			dadosPessoais = usuarioLogado.getAfiliado().getNumero().toString();
			dadosPessoais = usuarioLogado.getAfiliado().getPontoReferencia();
			dadosPessoais = usuarioLogado.getAfiliado().getBairro();
			dadosPessoais = usuarioLogado.getAfiliado().getCidade();
			dadosPessoais = usuarioLogado.getAfiliado().getEstado();

		} else if (usuarioLogado.getTipoUsuario() == TipoUsuario.FORNECEDOR) {
			dadosPessoais = usuarioLogado.getFornecedor().getRazaoSocial();
			dadosPessoais = usuarioLogado.getFornecedor().getCnpj();
			dadosPessoais = usuarioLogado.getFornecedor().getRazaoSocial();
			dadosPessoais = usuarioLogado.getFornecedor().getRazaoSocial();

		} else {
			dadosPessoais = "Erro";
		}
		return dadosPessoais;
	}

	public void setDadosPessoais(String dadosPessoais) {
		this.dadosPessoais = dadosPessoais;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}

	public String getFuncaoNome() {
		if (usuarioLogado.getTipoUsuario() == TipoUsuario.AFILIADO) {
			funcaoNome = usuarioLogado.getAfiliado().getNomeCompleto();
		} else if (usuarioLogado.getTipoUsuario() == TipoUsuario.FORNECEDOR) {
			funcaoNome = usuarioLogado.getFornecedor().getRazaoSocial();
		} else if (usuarioLogado.getTipoUsuario() == TipoUsuario.FUNCIONARIO) {
			funcaoNome = usuarioLogado.getFuncionario().getFuncao().getNomeFuncao();
		} else {
			funcaoNome = "ERRO";
		}

		return funcaoNome;
	}

	public void setFuncaoNome(String funcaoNome) {
		this.funcaoNome = funcaoNome;
	}

	public Long getResiduos() {
		RetiradaDao retiradaDao = new RetiradaDao();
		residuos = retiradaDao.buscarResiduos(usuarioLogado);
		return residuos;
	}

	public void setResiduos(Long residuos) {
		this.residuos = residuos;
	}

	public Long getOleos() {
		RetiradaDao retiradaDao = new RetiradaDao();
		oleos = retiradaDao.buscarOleos(usuarioLogado);
		return oleos;
	}

	public void setOleos(Long oleos) {
		this.oleos = oleos;
	}

	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		usuario.setFuncionario(new Funcionario());
		usuario.setFornecedor(new Fornecedor());
		usuario.setAfiliado(new Afiliado());
	}

	public void autenticar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

			TipoUsuario tipoUsuario = null;

			// Descobre o tipo do usuário que esta tentando efetuar a autenticação
			Afiliado afiliado = afiliadoDAO.buscarPorCPFOuEmail(usuario.getLogin());
			if (afiliado != null) {
				tipoUsuario = TipoUsuario.AFILIADO;
			} else {
				Fornecedor fornecedor = fornecedorDAO.buscarPorCPFOuCNPJ(usuario.getLogin());
				if (fornecedor != null) {
					tipoUsuario = TipoUsuario.FORNECEDOR;
				} else {
					Funcionario funcionario = funcionarioDAO.buscarPorCPFOuEmail(usuario.getLogin());
					if (funcionario != null) {
						tipoUsuario = TipoUsuario.FUNCIONARIO;
					} else {
						tipoUsuario = TipoUsuario.INVALIDO;
					}
				}
			}

			// Efetua a autenticação
			if (tipoUsuario == TipoUsuario.AFILIADO) {
				usuarioLogado = usuarioDAO.autenticarAFiliado(usuario.getLogin(), usuario.getSenha(),
						usuario.getStatus());
			} else if (tipoUsuario == TipoUsuario.FORNECEDOR) {
				usuarioLogado = usuarioDAO.autenticarFornecedor(usuario.getLogin(), usuario.getSenha(),
						usuario.getStatus());
			} else if (tipoUsuario == TipoUsuario.FUNCIONARIO) {
				usuarioLogado = usuarioDAO.autenticarFuncionario(usuario.getLogin(), usuario.getSenha(),
						usuario.getStatus());
			} else {
				usuarioLogado = null;
			}

			// Verifica se o usuário foi autenticado
			if (usuarioLogado == null) {
				Messages.addGlobalError("CPF e/ou senha incorretos");
				return;
			} else if (usuarioLogado.getStatus() == false) {
				Messages.addGlobalError("Usuário está desativado.");
			} else {
				usuarioLogado.setTipoUsuario(tipoUsuario);
				Faces.redirect("bt-principal.xhtml");
			}
		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError(erro.getMessage());
		}
	}

	public void deslogar() {
		HttpSession autenticar = Faces.getSession();
		autenticar.invalidate();
		Faces.navigate("bt-login.xhtml");
	}

	public boolean temPermissoes(List<String> permissoes) {
		for (String permissao : permissoes) {
			if (usuarioLogado.getTipo() == permissao.charAt(0)) {
				return true;
			}
		}
		return false;
	}

	public void construcao() {
		Messages.addGlobalInfo("Em construção. Em breve você poderá trocar a sua senha.");
	}

	public String sair() {
		usuarioLogado = null;
		return "bt-login.xhtml?faces-redirect=true";
	}

	public boolean temPerfis(List<String> permissoes) {
		for (String permissao : permissoes) {
			if (usuarioLogado.getTipoUsuario().toString().equals(permissao)) {
				return true;
			}
		}
		return false;
	}
}
