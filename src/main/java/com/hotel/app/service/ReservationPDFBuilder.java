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
import com.hotel.app.domain.Reservation;
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
public class ReservationPDFBuilder extends AbstractITextPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		Reservation reservation = (Reservation) model.get("reservation");
		BaseFont courier = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.EMBEDDED);
		Font font1 = new Font(courier, 12, Font.NORMAL);
		doc.add(new Paragraph(2f,"PHIẾU NHẬN PHÒNG KHACH SAN DMCHOTEL", font1));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");
		doc.add(new Paragraph("Ngay xuat phieu    : " + simpleDateFormat.format(new Date())));
		doc.add(new Paragraph("No:" + reservation.getId(), font1));
		

		doc.add(new Paragraph("		Nhan vien lễ tân                          " + "Khach hang", font1));

	}
}