package com.hotel.app.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hotel.app.config.AbstractITextPdfView;
import com.hotel.app.domain.Bill_service;
import com.hotel.app.domain.Book;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * @author www.codejava.net
 *
 */
public class PDFBuilder extends AbstractITextPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// get data model which is passed by the Spring container
		List<Bill_service> listBooks = (List<Bill_service>) model.get("listBill_service");
		
		doc.add(new Paragraph("Recommended books for Spring framework"));
		
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {3.0f, 2.0f, 2.0f, 2.0f, 1.0f});
		table.setSpacingBefore(10);
		
		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);
		
		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(5);
		
		// write table header 
		cell.setPhrase(new Phrase("Book Title", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Author", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("ISBN", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Published Date", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Price", font));
		table.addCell(cell);
		
		// write table row data
		for (int i=0;i<100;i++) {
			table.addCell("title");
			table.addCell("author");
			table.addCell("isbn");
			table.addCell("Date push");
			table.addCell("price");
		}
		
		doc.add(table);
		
	}
}