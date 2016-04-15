package com.hotel.app.service;

import java.math.BigDecimal;
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

import com.hotel.app.domain.Profile;

/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * 
 * @author www.codejava.net
 *
 */
@SuppressWarnings("deprecation")
public class ProfileExcelBuilder extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		@SuppressWarnings("unchecked")
		List<Profile> profiles = (List<Profile>) model.get("lists");

		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("DANH SÁCH NHÂN VIÊN");
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

		header.createCell(1).setCellValue("Mã hồ sơ");
		header.getCell(1).setCellStyle(style);

		header.createCell(2).setCellValue("Ngày vào làm");
		header.getCell(2).setCellStyle(style);

		header.createCell(3).setCellValue("Lương phụ cấp");
		header.getCell(3).setCellStyle(style);

		header.createCell(4).setCellValue("Lương cơ bản");
		header.getCell(4).setCellStyle(style);

		header.createCell(5).setCellValue("Lương chính");
		header.getCell(5).setCellStyle(style);

		header.createCell(6).setCellValue("Đơn vị tính");
		header.getCell(6).setCellStyle(style);

		header.createCell(7).setCellValue("Vị trí");
		header.getCell(7).setCellStyle(style);

		header.createCell(8).setCellValue("Phòng ban");
		header.getCell(8).setCellStyle(style);

		header.createCell(9).setCellValue("Tên nhân viên");
		header.getCell(9).setCellStyle(style);
		
		header.createCell(10).setCellValue("CMND");
		header.getCell(10).setCellStyle(style);
		
		header.createCell(11).setCellValue("Trạng thái");
		header.getCell(11).setCellStyle(style);
		
		
		header.createCell(12).setCellValue("Ngày tạo");
		header.getCell(12).setCellStyle(style);

		// create data rows
		int rowCount = 0;

		for (Profile profile : profiles) {
			rowCount++;
			HSSFRow aRow = sheet.createRow(rowCount);
			aRow.createCell(0).setCellValue(rowCount);
			aRow.createCell(1).setCellValue(profile.getId());
			aRow.createCell(2).setCellValue(profile.getJoin_date().format(DateTimeFormatter.ISO_DATE));
			aRow.createCell(3).setCellValue(profile.getSalary_subsidies().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(4).setCellValue(profile.getSalary_basic().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(5).setCellValue(profile.getSalary().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(6).setCellValue(profile.getCurrency().getCode());
			aRow.createCell(7).setCellValue(profile.getPosition().getPosition());
			aRow.createCell(8).setCellValue(profile.getDepartment().getName());
			aRow.createCell(9).setCellValue(profile.getEmployee().getFull_name());
			aRow.createCell(10).setCellValue(profile.getEmployee().getIc_number());
			aRow.createCell(11).setCellValue(profile.getStatus_profile().getName());
			aRow.createCell(12).setCellValue(profile.getCreate_date().format(DateTimeFormatter.ISO_DATE_TIME));
		}
	}
}