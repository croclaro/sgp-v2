package br.com.disqueoleo.sgp.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.disqueoleo.sgp.dao.AfiliadoDAO;
import br.com.disqueoleo.sgp.dao.BancoDAO;
import br.com.disqueoleo.sgp.domain.Afiliado;
import br.com.disqueoleo.sgp.domain.Banco;
import br.com.disqueoleo.sgp.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ConsultaAfiliadoBean implements Serializable {
	private Afiliado afiliado;
	private List<Afiliado> afiliados;
	private List<Banco> bancos;

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

	public List<Banco> getBancos() {
		return bancos;
	}

	public void setBancos(List<Banco> bancos) {
		this.bancos = bancos;
	}

	@PostConstruct
	public void listar() {
		try {
			AfiliadoDAO afiliadoDAO  = new AfiliadoDAO();
			afiliados = afiliadoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os afiliados");
			erro.printStackTrace();
		}
	}

	public void cadastrar() {
		afiliado = new Afiliado();
	}

	public void salvar() {
		try {
			AfiliadoDAO afiliadoDAO  = new AfiliadoDAO();
			afiliadoDAO.merge(afiliado);

			cadastrar();

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();

			afiliados = afiliadoDAO.listar();

			Messages.addGlobalInfo("Afiliado editado com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao editar o afiliado!!!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			afiliado = (Afiliado) evento.getComponent().getAttributes().get("afiliadoSelecionado");

			AfiliadoDAO afiliadoDAO = new AfiliadoDAO();
			afiliadoDAO.excluir(afiliado);

			afiliados = afiliadoDAO.listar();

			Messages.addGlobalInfo("Afiliado excluido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao gerar o Cartão Fornecedor.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			afiliado = (Afiliado) evento.getComponent().getAttributes().get("afiliadoSelecionado");

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
