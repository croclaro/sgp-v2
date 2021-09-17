package br.com.disqueoleo.sgp.bean;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.disqueoleo.sgp.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@ManagedBean
@ViewScoped
public class ImprimirBean {

	private StreamedContent manual;

	public StreamedContent getManual() {
		return manual;
	}

	public void setManual(StreamedContent manual) {
		this.manual = manual;
	}

	public void retiradaOleo() {
		try {
			// FILTRO DE DADOS ...
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			// CAMINHO DO RELATÓRIO ...
			String caminho = Faces.getRealPath("/relatorios/Retirada.jasper");

			// FILTROS ...
			String NomeFantasia = (String) filtros.get("fornecedor.nomeFantasia");
			String NomeFuncionario = (String) filtros.get("funcionario.nome");
			String NomeProduto = (String) filtros.get("produto.descricao");
			String NomeBairro = (String) filtros.get("fornecedor.bairro");

			// CAMINHO DA IMAGEM
			String caminhoBanner = Faces.getRealPath("/resources/images/LogoDisqueOleo.png");

			Map<String, Object> parametros = new HashMap<>();

			if (NomeFantasia == null) {
				parametros.put("NOME_FANTASIA", "%%");
			} else {
				parametros.put("NOME_FANTASIA", "%" + NomeFantasia + "%");
			}
			if (NomeFuncionario == null) {
				parametros.put("NOME_FUNCIONARIO", "%%");
			} else {
				parametros.put("NOME_FUNCIONARIO", "%" + NomeFuncionario + "%");
			}
			if (NomeProduto == null) {
				parametros.put("PRODUTO_DESCRICAO", "%%");
			} else {
				parametros.put("PRODUTO_DESCRICAO", "%" + NomeProduto + "%");
			}
			if (NomeBairro == null) {
				parametros.put("NOME_BAIRRO", "%%");
			} else {
				parametros.put("NOME_BAIRRO", "%" + NomeBairro + "%");
			}

			parametros.put("CAMINHO_BANNER", caminhoBanner);

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			byte[] buffer = JasperExportManager.exportReportToPdf(relatorio);

			Faces.sendFile(buffer, "Retirada.pdf", true);

			conexao.close();
		} catch (IOException erro) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
			erro.printStackTrace();
		} catch (SQLException e) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
		} catch (JRException e) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
		}
	}

	public void retiradaPorBairros() {
		try {

			String caminho = Faces.getRealPath("/relatorios/RetiradaPorBairros.jasper");

			String caminhoBanner = Faces.getRealPath("/resources/images/LogoDisqueOleo.png");

			Map<String, Object> parametros = new HashMap<>();

			parametros.put("CAMINHO_BANNER", caminhoBanner);

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			byte[] buffer = JasperExportManager.exportReportToPdf(relatorio);

			Faces.sendFile(buffer, "RetiradaPorBairros.pdf", true);

			conexao.close();
		} catch (JRException erro) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
			erro.printStackTrace();
		} catch (SQLException e) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
		} catch (IOException e) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
		}
	}

	public void retiradaPorMes() {
		try {
			String caminho = Faces.getRealPath("/relatorios/RetiradaPorMes.jasper");

			String caminhoBanner = Faces.getRealPath("/resources/images/LogoDisqueOleo.png");

			Map<String, Object> parametros = new HashMap<>();

			parametros.put("CAMINHO_BANNER", caminhoBanner);

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			byte[] buffer = JasperExportManager.exportReportToPdf(relatorio);

			Faces.sendFile(buffer, "RetiradaPorMes.pdf", true);

			conexao.close();
		} catch (JRException erro) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
			erro.printStackTrace();
		} catch (SQLException e) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
		} catch (IOException e) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
		}
	}

	public void cartaoFornecedor() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String NomeFantasia = (String) filtros.get("nomeFantasia");
			String Cnpj = (String) filtros.get("cnpj");
			String Cpf = (String) filtros.get("cpf");
			String CodBarras = (String) filtros.get("codBarras");

			String caminho = Faces.getRealPath("/relatorios/CartaoFornecedor.jasper");

			String caminhoBanner = Faces.getRealPath("/resources/images/capaTraseira.jpg");

			Map<String, Object> parametros = new HashMap<>();

			if (NomeFantasia == null) {
				parametros.put("NOME_FANTASIA", "%%");
			} else {
				parametros.put("NOME_FANTASIA", "%" + NomeFantasia + "%");
			}
			if (Cnpj == null) {
				parametros.put("CNPJ", "%%");
			} else {
				parametros.put("CNPJ", "%" + Cnpj + "%");
			}
			if (Cpf == null) {
				parametros.put("CPF", "%%");
			} else {
				parametros.put("CPF", "%" + Cpf + "%");
			}
			if (CodBarras == null) {
				parametros.put("COD_BARRAS", "%%");
			} else {
				parametros.put("COD_BARRAS", "%" + CodBarras + "%");
			}

			parametros.put("CAMINHO_BANNER", caminhoBanner);

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			byte[] buffer = JasperExportManager.exportReportToPdf(relatorio);

			Faces.sendFile(buffer, "CartaoForncedor.pdf", true);

			conexao.close();
		} catch (JRException erro) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
			erro.printStackTrace();
		} catch (SQLException e) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
		} catch (IOException e) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
		}
	}

	public void crachaVerso() {
		try {
			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters();

			String nomeCracha = (String) filtros.get("nome");
			String Cpf = (String) filtros.get("cpf");

			String caminho = Faces.getRealPath("/relatorios/CrachaVerso.jasper");

			String caminhoBanner = Faces.getRealPath("/resources/images/CrachaVerso.jpg");

			Map<String, Object> parametros = new HashMap<>();

			if (nomeCracha == null) {
				parametros.put("CRACHA_NOME", "%%");
			} else {
				parametros.put("CRACHA_NOME", "%" + nomeCracha + "%");
			}
			if (Cpf == null) {
				parametros.put("CPF", "%%");
			} else {
				parametros.put("CPF", "%" + Cpf + "%");
			}

			parametros.put("BANNER_CRACHA", caminhoBanner);

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			byte[] buffer = JasperExportManager.exportReportToPdf(relatorio);

			Faces.sendFile(buffer, "CrachaVerso.pdf", true);

			conexao.close();
		} catch (JRException erro) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
			erro.printStackTrace();
		} catch (SQLException e) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
		} catch (IOException e) {
			Messages.addGlobalError("Relatório em Construção. Obrigado.");
		}
	}

	public void manualWeb() {
		try {
			InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
					.getResourceAsStream("/relatorios/Manual/ManualDisqueOleoWeb.pdf");
			manual = new DefaultStreamedContent(stream, "PDF", "Manual_Web.pdf");
		} catch (Exception erro) {
			Messages.addGlobalError("Manual do usuário em construção. Obrigado. ");
		}
	}

	public void manualMobile() {
		try {
			InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
					.getResourceAsStream("/relatorios/Manual/ManualDisqueOleoMobile.pdf");
			manual = new DefaultStreamedContent(stream, "PDF", "Manual_Mobile.pdf");
		} catch (Exception erro) {
			Messages.addGlobalError("Manual do usuário em construção. Obrigado. ");
		}
	}

	public void manualPidion() {
		try {
			InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
					.getResourceAsStream("/relatorios/Manual/BIP-1300.pdf");
			manual = new DefaultStreamedContent(stream, "PDF", "Manual_BIP1300.pdf");
		} catch (Exception erro) {
			Messages.addGlobalError("Manual do usuário em construção. Obrigado. ");
		}
	}

	public void sgpMobile() {
		try {
			InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
					.getResourceAsStream("/SGP-MOBILE/sgp_mobile.rar");
			manual = new DefaultStreamedContent(stream, "RAR", "sgp_mobile.rar");
		} catch (Exception erro) {
			Messages.addGlobalError("Manual do usuário em construção. Obrigado. ");
		}
	}

	public void netcfv35() {
		try {
			InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
					.getResourceAsStream("/SGP-MOBILE/NETCFv35.wm.armv4i.cab");
			manual = new DefaultStreamedContent(stream, "CAB", "NETCFv35.wm.armv4i.cab");
		} catch (Exception erro) {
			Messages.addGlobalError("Manual do usuário em construção. Obrigado. ");
		}
	}

	public void instalador() {
		try {
			InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
					.getResourceAsStream("/SGP-MOBILE/Instalador_SGP.CAB");
			manual = new DefaultStreamedContent(stream, "CAB", "Instalador_SGP.cab");
		} catch (Exception erro) {
			Messages.addGlobalError("Manual do usuário em construção. Obrigado. ");
		}
	}
}
