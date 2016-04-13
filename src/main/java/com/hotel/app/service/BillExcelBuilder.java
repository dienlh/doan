package com.hotel.app.service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.hotel.app.domain.Bill;
import com.hotel.app.domain.Register_info;
import com.hotel.app.domain.Room;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * 
 * @author www.codejava.net
 *
 */
@SuppressWarnings("deprecation")
public class BillExcelBuilder extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		List<Bill> bills = (List<Bill>) model.get("lists");

		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("DANH SÁCH PHIẾU THANH TOÁN");
		sheet.setDefaultColumnWidth(15);

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		
		

		// create header row
		HSSFRow header = sheet.createRow(0);

		header.createCell(0).setCellValue("STT");
		header.getCell(0).setCellStyle(style);

		header.createCell(1).setCellValue("Mã TT");
		header.getCell(1).setCellStyle(style);

		header.createCell(2).setCellValue("Phí phòng");
		header.getCell(2).setCellStyle(style);

		header.createCell(3).setCellValue("Phí dịch vụ");
		header.getCell(3).setCellStyle(style);

		header.createCell(4).setCellValue("Phí thưởng");
		header.getCell(4).setCellStyle(style);

		header.createCell(5).setCellValue("Phí khác");
		header.getCell(5).setCellStyle(style);

		header.createCell(6).setCellValue("Đã đặt cọc");
		header.getCell(6).setCellStyle(style);

		header.createCell(7).setCellValue("Tổng tiền");
		header.getCell(7).setCellStyle(style);

		header.createCell(8).setCellValue("Phí VAT");
		header.getCell(8).setCellStyle(style);

		header.createCell(9).setCellValue("Tổng tiền (VAT)");
		header.getCell(9).setCellStyle(style);
		
		header.createCell(10).setCellValue("ĐVT");
		header.getCell(10).setCellStyle(style);
		
		header.createCell(11).setCellValue("Phương thức thanh toán");
		header.getCell(11).setCellStyle(style);
		
		
		header.createCell(12).setCellValue("Trạng thái thanh toán");
		header.getCell(12).setCellStyle(style);

		header.createCell(13).setCellValue("Phương thức đăng ký");
		header.getCell(13).setCellStyle(style);

		header.createCell(14).setCellValue("Mã nhận phòng");
		header.getCell(14).setCellStyle(style);

		header.createCell(15).setCellValue("Mã phòng");
		header.getCell(15).setCellStyle(style);
		
		header.createCell(16).setCellValue("Trạng thái phiếu thanh toán");
		header.getCell(16).setCellStyle(style);
		
		header.createCell(17).setCellValue("Ngày tạo");
		header.getCell(17).setCellStyle(style);
		
		// create data rows
		int rowCount = 0;

		for (Bill bill : bills) {
			rowCount++;
			HSSFRow aRow = sheet.createRow(rowCount);
			aRow.createCell(0).setCellValue(rowCount);
			aRow.createCell(1).setCellValue(bill.getId());
			aRow.createCell(2).setCellValue(bill.getFees_room().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(3).setCellValue(bill.getFees_service().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(4).setCellValue(bill.getFees_bonus().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(5).setCellValue(bill.getFees_other().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(6).setCellValue(bill.getReservation().getRegister_info().getDeposit_value().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(7).setCellValue(bill.getTotal().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(8).setCellValue(bill.getFees_vat().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(9).setCellValue(bill.getTotal_vat().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(10).setCellValue(bill.getCurrency().getCode());
			aRow.createCell(11).setCellValue(bill.getMethod_payment().getName());
			aRow.createCell(12).setCellValue(bill.getStatus_payment().getName());
			aRow.createCell(13).setCellValue(bill.getReservation().getRegister_info().getMethod_register().getName());
			aRow.createCell(14).setCellValue(bill.getReservation().getId());
			aRow.createCell(15).setCellValue(bill.getReservation().getRegister_info().getRoom().getCode());
			aRow.createCell(16).setCellValue(bill.getStatus_bill().getName());
			aRow.createCell(17).setCellValue(bill.getCreate_date().format(DateTimeFormatter.ISO_DATE_TIME));
		}
	}
}