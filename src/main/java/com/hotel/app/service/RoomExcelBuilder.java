
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

import com.hotel.app.domain.Room;


/**
 * This class builds an Excel spreadsheet document using Apache POI library.
 * 
 * @author www.codejava.net
 *
 */
@SuppressWarnings("deprecation")
public class RoomExcelBuilder extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		List<Room> rooms = (List<Room>) model.get("lists");

		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("DANH SÁCH PHÒNG");
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

		header.createCell(1).setCellValue("Mã phòng");
		header.getCell(1).setCellStyle(style);

		header.createCell(2).setCellValue("Mã khóa");
		header.getCell(2).setCellStyle(style);

		header.createCell(3).setCellValue("Thú nuôi");
		header.getCell(3).setCellStyle(style);

		header.createCell(4).setCellValue("Giường phụ trẻ em");
		header.getCell(4).setCellStyle(style);

		header.createCell(5).setCellValue("SL P.khách");
		header.getCell(5).setCellStyle(style);

		header.createCell(6).setCellValue("SL P.ngủ");
		header.getCell(6).setCellStyle(style);

		header.createCell(7).setCellValue("SL P.Vệ sinh");
		header.getCell(7).setCellStyle(style);

		header.createCell(8).setCellValue("SL P.bếp");
		header.getCell(8).setCellStyle(style);

		header.createCell(9).setCellValue("SL P.tắm");
		header.getCell(9).setCellStyle(style);
		
		header.createCell(10).setCellValue("Tầng");
		header.getCell(10).setCellStyle(style);

		header.createCell(11).setCellValue("Hướng phòng");
		header.getCell(11).setCellStyle(style);

		header.createCell(12).setCellValue("Diện tích");
		header.getCell(12).setCellStyle(style);

		header.createCell(13).setCellValue("Người lớn tối đa");
		header.getCell(13).setCellStyle(style);
		
		header.createCell(14).setCellValue("Trẻ em tối đa");
		header.getCell(14).setCellStyle(style);
		
		
		header.createCell(15).setCellValue("Giá giờ");
		header.getCell(15).setCellStyle(style);

		header.createCell(16).setCellValue("Giá ngày");
		header.getCell(16).setCellStyle(style);

		header.createCell(17).setCellValue("Giá tháng");
		header.getCell(17).setCellStyle(style);

		header.createCell(18).setCellValue("ĐVT");
		header.getCell(18).setCellStyle(style);

		header.createCell(19).setCellValue("Loại phòng");
		header.getCell(19).setCellStyle(style);

		header.createCell(20).setCellValue("Trạng thái");
		header.getCell(20).setCellStyle(style);
		
		header.createCell(21).setCellValue("Ngày tạo");
		header.getCell(21).setCellStyle(style);
		
		// create data rows
		int rowCount = 0;

		for (Room room : rooms) {
			rowCount++;
			HSSFRow aRow = sheet.createRow(rowCount);
			aRow.createCell(0).setCellValue(rowCount);
			aRow.createCell(1).setCellValue(room.getCode());
			aRow.createCell(2).setCellValue(room.getKey_code());
			aRow.createCell(3).setCellValue(room.getIs_pet());
			aRow.createCell(4).setCellValue(room.getIs_bed_kid());
			aRow.createCell(5).setCellValue(room.getNumber_of_livingroom());
			aRow.createCell(6).setCellValue(room.getNumber_of_bedroom());
			aRow.createCell(7).setCellValue(room.getNumber_of_toilet());
			aRow.createCell(8).setCellValue(room.getNumber_of_kitchen());
			aRow.createCell(9).setCellValue(room.getNumber_of_bathroom());
			aRow.createCell(10).setCellValue(room.getFloor());
			aRow.createCell(11).setCellValue(room.getOrientation());
			aRow.createCell(12).setCellValue(room.getSurface_size());
			aRow.createCell(13).setCellValue(room.getMax_adults());
			aRow.createCell(14).setCellValue(room.getMax_kids());
			aRow.createCell(15).setCellValue(room.getHourly_price().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(16).setCellValue(room.getDaily_price().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(17).setCellValue(room.getMonthly_price().setScale(0, BigDecimal.ROUND_HALF_DOWN).toString());
			aRow.createCell(18).setCellValue(room.getCurrency().getCode());
			aRow.createCell(19).setCellValue(room.getType_room().getName());
			aRow.createCell(20).setCellValue(room.getStatus_room().getName());
			aRow.createCell(21).setCellValue(room.getCreate_date().format(DateTimeFormatter.ISO_DATE_TIME));
		}
	}
}