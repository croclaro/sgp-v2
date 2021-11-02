package br.com.disqueoleo.sgp.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.omnifaces.util.Messages;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

import br.com.disqueoleo.sgp.dao.BancoDAO;
import br.com.disqueoleo.sgp.domain.Banco;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ConsultaBancosBean implements Serializable {
	private Banco banco;
	private List<Banco> bancos;
	private ExcelOptions excelOpt;
	private PDFOptions pdfOpt;

	public Banco getBanco() {
		return banco;
	}

	public ExcelOptions getExcelOpt() {
		return excelOpt;
	}

	public void setExcelOpt(ExcelOptions excelOpt) {
		this.excelOpt = excelOpt;
	}

	public PDFOptions getPdfOpt() {
		return pdfOpt;
	}

	public void setPdfOpt(PDFOptions pdfOpt) {
		this.pdfOpt = pdfOpt;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
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

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();
			customizationOptions();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os banco");
			erro.printStackTrace();
		}
	}

	public void cadastrar() {
		try {
			banco = new Banco();
			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao cadastrar o banco!!!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			BancoDAO bancoDAO = new BancoDAO();
			bancoDAO.merge(banco);

			cadastrar();

			Messages.addGlobalInfo("O Banco foi editado com sucesso!!!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao editar o banco!!!");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			banco = (Banco) evento.getComponent().getAttributes().get("bancoSelecionado");

			BancoDAO bancoDAO = new BancoDAO();
			bancoDAO.excluir(banco);

			bancos = bancoDAO.listar();

			Messages.addGlobalInfo("Banco excluido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao excluir o Banco.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			banco = (Banco) evento.getComponent().getAttributes().get("bancoSelecionado");

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();

			Messages.addGlobalInfo("Banco editado com sucesso");
		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}
	}

	public void customizationOptions() {
		excelOpt = new ExcelOptions();
		excelOpt.setFacetBgColor("#F88017");
		excelOpt.setFacetFontSize("10");
		excelOpt.setFacetFontColor("#0000ff");
		excelOpt.setFacetFontStyle("BOLD");
		excelOpt.setCellFontColor("#00ff00");
		excelOpt.setCellFontSize("50");
		excelOpt.setFacetFontStyle("Verdana");

		pdfOpt = new PDFOptions();
		pdfOpt.setFacetBgColor("#F88017"); //COR DO MENU
		pdfOpt.setFacetFontColor("#0000ff"); //COR FONTE DO MENU
		pdfOpt.setFacetFontStyle("BOLD"); // TIPO FONTE
		pdfOpt.setCellFontSize("12"); // TAMANHO DA FONTE
		pdfOpt.setFacetFontStyle("Courier");
		
	}

	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ORANGE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = header.getCell(i);

			cell.setCellStyle(cellStyle);			
		}
	}

	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
		Document pdf = (Document) document;
		pdf.open();
		pdf.setPageSize(PageSize.A4);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		String separator = File.separator;
		String logo = externalContext.getRealPath("") + separator + "resources" + separator + "images" + separator
				+ "LogoDisqueOleo1.png";

		pdf.add(Image.getInstance(logo));		
	}

}
