package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.BancoDAO;
import br.com.disqueoleo.sgp.dao.FornecedorDAO;
import br.com.disqueoleo.sgp.domain.Banco;
import br.com.disqueoleo.sgp.domain.Fornecedor;
import br.com.disqueoleo.sgp.enums.TipoUsuario;
import br.com.disqueoleo.sgp.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ConsultaFornecedoresBean implements Serializable {
	private Fornecedor fornecedor;
	private List<Fornecedor> fornecedores;
	private List<Banco> bancos;
	
	@ManagedProperty("#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;
	
	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<Banco> getBancos() {
		return bancos;
	}

	public void setBancos(List<Banco> bancos) {
		this.bancos = bancos;
	}

	@PostConstruct
	public void listar() {
		try {
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			
			if (autenticacaoBean.getUsuarioLogado().getTipoUsuario() == TipoUsuario.AFILIADO) {
				fornecedores = fornecedorDAO.buscarPorAfiliado(autenticacaoBean.getUsuarioLogado().getAfiliado().getCodigo());
			} else {
				fornecedores = fornecedorDAO.listar();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os fornecedores");
			erro.printStackTrace();
		}
	}

	public void cadastrar() {
		fornecedor = new Fornecedor();
	}

	public void salvar() {
		try {
			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedorDAO.merge(fornecedor);

			cadastrar();

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();

			fornecedores = fornecedorDAO.listar();

			Messages.addGlobalInfo("Fornecedor editado com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao editar o fornecedor!!!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			fornecedor = (Fornecedor) evento.getComponent().getAttributes().get("fornecedorSelecionado");

			FornecedorDAO fornecedorDAO = new FornecedorDAO();
			fornecedorDAO.excluir(fornecedor);

			fornecedores = fornecedorDAO.listar();

			Messages.addGlobalInfo("Fornecedor excluido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao gerar o Cartão Fornecedor.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			fornecedor = (Fornecedor) evento.getComponent().getAttributes().get("fornecedorSelecionado");

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();

		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}
	}

	public void imprimir() {
		try {
			String caminho = Faces.getRealPath("/relatorios/CartaoFornecedor.jasper");
			String caminhoBanner = Faces.getRealPath("/resources/images/capaTraseira.jpg");

			Map<String, Object> parametros = new HashMap<>();

			parametros.put("CAMINHO_BANNER", caminhoBanner);

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);

			JasperPrintManager.printReport(relatorio, true);
		} catch (JRException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar o relatório");
			erro.printStackTrace();
		}
	}
}
