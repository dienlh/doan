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

import com.hotel.app.domain.Register_info;
import com.hotel.app.domain.Room;


/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * 
 * @author www.codejava.net
 *
 */
@SuppressWarnings("deprecation")
public class Register_infoExcelBuilder extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		List<Register_info> register_infos = (List<Register_info>) model.get("lists");

		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("DANH SÁCH ĐĂNG KÝ PHÒNG");
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

		header.createCell(1).setCellValue("Mã ĐK");
		header.getCell(1).setCellStyle(style);

		header.createCell(2).setCellValue("Ngày nhận phòng");
		header.getCell(2).setCellStyle(style);

		header.createCell(3).setCellValue("Ngày trả phòng");
		header.getCell(3).setCellStyle(style);

		header.createCell(4).setCellValue("SL người lớn");
		header.getCell(4).setCellStyle(style);

		header.createCell(5).setCellValue("SL trẻ em");
		header.getCell(5).setCellStyle(style);

		header.createCell(6).setCellValue("Đặt cọc");
		header.getCell(6).setCellStyle(style);

		header.createCell(7).setCellValue("ĐVT");
		header.getCell(7).setCellStyle(style);

		header.createCell(8).setCellValue("Mã phòng");
		header.getCell(8).setCellStyle(style);

		header.createCell(9).setCellValue("Tên khách hàng");
		header.getCell(9).setCellStyle(style);
		
		header.createCell(10).setCellValue("CMND");
		header.getCell(10).setCellStyle(style);
		
		
		header.createCell(11).setCellValue("Phương thức thanh toán");
		header.getCell(11).setCellStyle(style);

		header.createCell(12).setCellValue("Trạng thái thanh toán");
		header.getCell(12).setCellStyle(style);

		header.createCell(13).setCellValue("Phương thức đăng ký");
		header.getCell(13).setCellStyle(style);

		header.createCell(14).setCellValue("Trạng thái đăng ký");
		header.getCell(14).setCellStyle(style);
		
		header.createCell(15).setCellValue("Ngày tạo");
		header.getCell(15).setCellStyle(style);
		
		// create data rows
		int rowCount = 0;

		for (Register_info register_info : register_infos) {
			rowCount++;
			HSSFRow aRow = sheet.createRow(rowCount);
			aRow.createCell(0).setCellValue(rowCount);
			aRow.createCell(1).setCellValue(register_info.getId());
			aRow.createCell(2).setCellValue(register_info.getDate_checkin().format(DateTimeFormatter.ISO_DATE));
			aRow.createCell(3).setCellValue(register_info.getDate_checkout().format(DateTimeFormatter.ISO_DATE));
			aRow.createCell(4).setCellValue(register_info.getNumber_of_adult());
			aRow.createCell(5).setCellValue(register_info.getNumber_of_kid());
			aRow.createCell(6).setCellValue(register_info.getDeposit_value().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(7).setCellValue(register_info.getCurrency().getCode());
			aRow.createCell(8).setCellValue(register_info.getRoom().getCode());
			aRow.createCell(9).setCellValue(register_info.getCustomer().getFull_name());
			aRow.createCell(10).setCellValue(register_info.getCustomer().getIc_passport_number());
			aRow.createCell(11).setCellValue(register_info.getMethod_payment().getName());
			aRow.createCell(12).setCellValue(register_info.getStatus_payment().getName());
			aRow.createCell(13).setCellValue(register_info.getMethod_register().getName());
			aRow.createCell(14).setCellValue(register_info.getStatus_register().getName());
			aRow.createCell(15).setCellValue(register_info.getCreate_date().format(DateTimeFormatter.ISO_DATE_TIME));
		}
	}

}