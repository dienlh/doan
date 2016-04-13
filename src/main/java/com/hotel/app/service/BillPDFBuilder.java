package com.hotel.app.service;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.format.datetime.joda.DateTimeFormatterFactory;

import com.hotel.app.config.AbstractITextPdfView;
import com.hotel.app.domain.Bill;
import com.hotel.app.domain.Bill_service;
import com.hotel.app.domain.Book;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 * 
 * @author www.codejava.net
 *
 */
public class BillPDFBuilder extends AbstractITextPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		Bill bill = (Bill) model.get("bill");
		BaseFont courier = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.EMBEDDED);
		Font font1 = new Font(courier, 12, Font.NORMAL);
		doc.add(new Paragraph(2f,"Hoa Don Thanh Toan KHACH SAN DMCHOTEL", font1));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");
		doc.add(new Paragraph("Ngay xuat phieu    : " + simpleDateFormat.format(new Date())));
		doc.add(new Paragraph("Ma phieu thanh toan:" + bill.getId(), font1));
		doc.add(new Paragraph("Ten khach hang     :" + bill.getCustomer().getFull_name(), font1));
		doc.add(new Paragraph("Ma nhan phong      : " + bill.getReservation().getId() + "                    Mã phòng : "
				+ bill.getReservation().getRegister_info().getRoom().getCode(), font1));
		doc.add(new Paragraph("Da dat coc         :" + bill.getReservation().getRegister_info().getDeposit_value() + " "
				+ bill.getCurrency().getCode(), font1));
		doc.add(new Paragraph("Thời gian nhan phong : "
						+ bill.getReservation().getTime_checkin().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
				font1));
		doc.add(new Paragraph("Thời gian tra phong  : "
						+ bill.getReservation().getTime_checkout().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
				font1));
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 1.0f, 2.0f, 1.0f, 2.0f });
		table.setSpacingBefore(10);

		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);

		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(new BaseColor(58, 15, 25, 1));
		cell.setPadding(5);

		// write table header
		cell.setPhrase(new Phrase("STT", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Tên chi phí", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("DVT", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Tổng tiền", font));
		table.addCell(cell);

		// write table row data
		table.addCell("1");
		table.addCell("Thue phong");
		table.addCell(bill.getCurrency().getCode());
		table.addCell(bill.getFees_room().setScale(0,BigDecimal.ROUND_HALF_DOWN).toString());
		
		table.addCell("2");
		table.addCell("Dich vu");
		table.addCell(bill.getCurrency().getCode());
		table.addCell(bill.getFees_service().setScale(0,BigDecimal.ROUND_HALF_DOWN).toString());
		
		table.addCell("3");
		table.addCell("Tien thuong");
		table.addCell(bill.getCurrency().getCode());
		table.addCell(bill.getFees_bonus().setScale(0,BigDecimal.ROUND_HALF_DOWN).toString());
		
		table.addCell("4");
		table.addCell("Chi phi khac");
		table.addCell(bill.getCurrency().getCode());
		table.addCell(bill.getFees_other().setScale(0,BigDecimal.ROUND_HALF_DOWN).toString());
		
		cell.setPhrase(new Phrase("Tong tien               :" + bill.getTotal().setScale(0,BigDecimal.ROUND_HALF_DOWN) + " " + bill.getCurrency().getCode(), font));
		cell.setColspan(3);
		table.addCell(cell);

		cell.setPhrase(new Phrase("Thue VAT             : 10%                 " + "Tien VAT : "
				+ bill.getFees_vat().setScale(0,BigDecimal.ROUND_HALF_DOWN) + " " + bill.getCurrency().getCode(), font));
		cell.setColspan(3);
		table.addCell(cell);

		cell.setPhrase(new Phrase("Tong tien (Da tinh VAT) : " + bill.getTotal_vat().setScale(0,BigDecimal.ROUND_HALF_DOWN) + " " + bill.getCurrency().getCode(), font));
		cell.setColspan(3);
		table.addCell(cell);
		doc.add(table);

		doc.add(new Paragraph("		Nhan vien thu ngan                          " + "Khach hang", font1));

	}
}