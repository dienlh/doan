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
import com.hotel.app.domain.Bill_service;
import com.hotel.app.domain.Register_info;
import com.hotel.app.domain.Room;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * 
 * @author www.codejava.net
 *
 */
@SuppressWarnings("deprecation")
public class Bill_serviceExcelBuilder extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		List<Bill_service> bill_services = (List<Bill_service>) model.get("lists");

		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("DANH SÁCH PHIẾU DỊCH VỤ");
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

		header.createCell(1).setCellValue("Mã Đk");
		header.getCell(1).setCellStyle(style);

		header.createCell(2).setCellValue("Tên dịch vụ");
		header.getCell(2).setCellStyle(style);

		header.createCell(3).setCellValue("Số lượng");
		header.getCell(3).setCellStyle(style);

		header.createCell(4).setCellValue("Đơn giá");
		header.getCell(4).setCellStyle(style);
		
		header.createCell(5).setCellValue("Tổng tiền");
		header.getCell(5).setCellStyle(style);

		header.createCell(6).setCellValue("ĐVT");
		header.getCell(6).setCellStyle(style);

		header.createCell(7).setCellValue("Mã nhận phòng");
		header.getCell(7).setCellStyle(style);

		header.createCell(8).setCellValue("Mã phòng");
		header.getCell(8).setCellStyle(style);

		header.createCell(9).setCellValue("Trạng thái");
		header.getCell(9).setCellStyle(style);

		header.createCell(10).setCellValue("Ngày đăng ký");
		header.getCell(10).setCellStyle(style);
		
		
		// create data rows
		int rowCount = 0;

		for (Bill_service bill_service : bill_services) {
			rowCount++;
			HSSFRow aRow = sheet.createRow(rowCount);
			aRow.createCell(0).setCellValue(rowCount);
			aRow.createCell(1).setCellValue(bill_service.getId());
			aRow.createCell(2).setCellValue(bill_service.getServices().getName());
			aRow.createCell(3).setCellValue(bill_service.getQuantity());
			aRow.createCell(4).setCellValue(String
					.valueOf(BigDecimal.valueOf(bill_service.getTotal().doubleValue() / bill_service.getQuantity())
							.setScale(0, BigDecimal.ROUND_HALF_DOWN)));
			aRow.createCell(5).setCellValue(bill_service.getTotal().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(6).setCellValue(bill_service.getCurrency().getCode());
			aRow.createCell(7).setCellValue(bill_service.getReservation().getId());
			aRow.createCell(8).setCellValue(bill_service.getReservation().getRegister_info().getRoom().getCode());
			aRow.createCell(9).setCellValue(bill_service.getStatus_bill_service().getName());
			aRow.createCell(10).setCellValue(bill_service.getCreate_date().format(DateTimeFormatter.ISO_DATE_TIME));
		}
	}
}