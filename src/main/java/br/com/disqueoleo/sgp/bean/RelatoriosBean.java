package br.com.disqueoleo.sgp.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfPTable;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class RelatoriosBean implements Serializable {

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
		// Document pdf = (Document) document;
		// pdf.open();
		// Font catFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
		// Paragraph p = new Paragraph("SGP - SISTEMA GERENCIADOR DE PRODUTOS",
		// catFont);
		// p.setAlignment(Element.ALIGN_CENTER);
		// p.setSpacingAfter(20);
		// pdf.add(p);
		// HeaderFooter footer = new HeaderFooter(new Phrase("página "), new
		// Phrase("."));
		// pdf.setFooter(footer);

		// pdf.setPageSize(PageSize.A4.rotate());
		// pdf.setMargins(0f, 0f, 0f, 0f);
		// pdf.open();

		Document pdf = (Document) document;
		pdf.setPageSize(PageSize.A4.rotate());
		pdf.open();

		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "images"
				+ File.separator + File.separator + "logo.png";
		Image image = Image.getInstance(logo);

		pdf.add(image);
		// add a couple of blank lines		
		pdf.add(Chunk.NEWLINE);
		
		
	}

}
