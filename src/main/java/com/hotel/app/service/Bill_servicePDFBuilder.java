
package com.hotel.app.service;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.hotel.app.config.AbstractITextPdfView;
import com.hotel.app.domain.Bill_service;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
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
public class Bill_servicePDFBuilder extends AbstractITextPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		@SuppressWarnings("unchecked")
		List<Bill_service> bill_services = (List<Bill_service>) model.get("listBill_service");

		BaseFont courier = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.EMBEDDED);
		Font font1 = new Font(courier, 12, Font.NORMAL);
		doc.add(new Paragraph("PHIEU THANH TOAN DICH VU", font1));
		doc.add(new Paragraph("KHACH SAN DMCHOTEL",
				new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLDITALIC, new BaseColor(Color.RED))));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");
		doc.add(new Paragraph("Ngay xuat phieu : " + simpleDateFormat.format(new Date())));
		doc.add(new Paragraph(
				"Ten khach hang :" + bill_services.get(0).getReservation().getPerson_checkin().getFull_name(), font1));
		doc.add(new Paragraph(
				"Ma nhan phong : " + bill_services.get(0).getReservation().getId() + "                    Mã phòng : "
						+ bill_services.get(0).getReservation().getRegister_info().getRoom().getCode(),
				font1));
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 1.0f, 2.0f, 1.0f, 1.0f, 2.0f, 2.0f });
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

		cell.setPhrase(new Phrase("Ten dich vu", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("So luong", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("DVT", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Don gia", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Thanh tien", font));
		table.addCell(cell);

		// write table row data
		int count = 0;
		double total = 0;
		for (Bill_service bill_service : bill_services) {
			count++;
			total += bill_service.getTotal().doubleValue();
			table.addCell(String.valueOf(count));
			table.addCell(bill_service.getServices().getName());
			table.addCell(bill_service.getQuantity().toString());
			table.addCell(bill_service.getCurrency().getCode());
			table.addCell(String
					.valueOf(BigDecimal.valueOf(bill_service.getTotal().doubleValue() / bill_service.getQuantity())
							.setScale(0, BigDecimal.ROUND_HALF_DOWN)));
			table.addCell(bill_service.getTotal().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
		}
		cell.setPhrase(new Phrase("Tong tien :" + BigDecimal.valueOf(total).setScale(0,BigDecimal.ROUND_HALF_DOWN) + " " + bill_services.get(0).getCurrency().getCode(), font));
		cell.setColspan(6);
		table.addCell(cell);

		cell.setPhrase(new Phrase("Thue VAT  : 10%                                        " + "Tien VAT : "
				+ BigDecimal.valueOf(total * 0.1).setScale(0, BigDecimal.ROUND_HALF_DOWN) + " " + bill_services.get(0).getCurrency().getCode(), font));
		cell.setColspan(6);
		table.addCell(cell);

		cell.setPhrase(new Phrase(
				"Tong tien (Da tinh VAT) : " + BigDecimal.valueOf(total * 1.1).setScale(0, BigDecimal.ROUND_HALF_DOWN) + " " + bill_services.get(0).getCurrency().getCode(),
				font));
		cell.setColspan(6);
		table.addCell(cell);
		doc.add(table);

		doc.add(new Paragraph("		Nhân viên xác nhận                                    " + "Khách hàng", font1));

	}

}