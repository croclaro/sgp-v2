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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.omnifaces.util.Messages;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

import br.com.disqueoleo.sgp.dao.BancoDAO;
import br.com.disqueoleo.sgp.domain.Banco;
import br.com.disqueoleo.sgp.domain.Fornecedor;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RelatoriosBean implements Serializable{
	public Banco banco;	
	public Fornecedor fornecedor;
	public List<Fornecedor> fornecedores;
	public List<Banco> bancos;
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
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

			BancoDAO bancoDAO = new BancoDAO();
			bancos = bancoDAO.listar();	
			

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os dados");
			erro.printStackTrace();
		}
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
		
		pdf.setPageSize(PageSize.LEGAL.rotate());
		pdf.setMargins(2, 2, 2, 0);
		pdf.open();
				
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		String separator = File.separator;
		String logo = externalContext.getRealPath("") + separator + "resources" + separator + "images" + separator
				+ "logo.png";

		pdf.add(Image.getInstance(logo));		
	}
	
	
}
