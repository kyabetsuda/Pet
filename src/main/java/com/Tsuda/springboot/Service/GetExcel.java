package com.Tsuda.springboot.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Tsuda.springboot.model.ReserveTodayListEntity;

@Service
public class GetExcel {
	
	@Autowired
	ReserveTodayListWithJdbc withJdbc;
	
	public void getExcel(
			HttpServletRequest request,
			HttpServletResponse response
			) throws FileNotFoundException, IOException {
		HttpSession session = request.getSession(false);
		List<ReserveTodayListEntity> rooms = new ArrayList<ReserveTodayListEntity>();
		
		String itemnm = null;
		String reserved = null;
		String stayed = null;
		if( session == null) {
			session = request.getSession(true);
			itemnm = (String)session.getAttribute("itemnm");
			reserved = (String)session.getAttribute("reserved");
			stayed = (String)session.getAttribute("stayed");
		}else {
			itemnm = (String)session.getAttribute("itemnm");
			reserved = (String)session.getAttribute("reserved");
			stayed = (String)session.getAttribute("stayed");
		}
		
		if( reserved == null ) {
			rooms = withJdbc.makeList();
		}else {
			rooms = withJdbc.getSearchResult(itemnm, reserved, stayed);
		}

        HSSFWorkbook book = null;
        OutputStream outputStream = null;
        // リクエストの文字コードをUTF-8に変換する。
        request.setCharacterEncoding("UTF-8");
        
        SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyy-MM-dd");
        String filename = "ReserveToday" +simpleDataFormat.format(new Date()) + ".xlsx";

        // レスポンスヘッダを設定する。
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("content-disposition", String.format("attachment; filename=\"%s\"", filename));
        response.setCharacterEncoding("UTF-8");

        try {
        		book = new HSSFWorkbook();
        		outputStream = response.getOutputStream();

            Font font = book.createFont();
            font.setFontName("ＭＳ ゴシック");
            font.setFontHeightInPoints((short) 9);

            DataFormat format = book.createDataFormat();

            //ヘッダ文字列用のスタイル
            CellStyle style_header = book.createCellStyle();
            style_header.setBorderBottom(BorderStyle.THIN);
            GetExcel.setBorder(style_header, BorderStyle.THIN);
            style_header.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_CORNFLOWER_BLUE.getIndex());
            style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style_header.setVerticalAlignment(VerticalAlignment.TOP);
            style_header.setFont(font);

            //文字列用のスタイル
            CellStyle style_string = book.createCellStyle();
            GetExcel.setBorder(style_string, BorderStyle.THIN);
            style_string.setVerticalAlignment(VerticalAlignment.TOP);
            style_string.setFont(font);

            //改行が入った文字列用のスタイル
            CellStyle style_string_wrap = book.createCellStyle();
            GetExcel.setBorder(style_string_wrap, BorderStyle.THIN);
            style_string_wrap.setVerticalAlignment(VerticalAlignment.TOP);
            style_string_wrap.setWrapText(true);
            style_string_wrap.setFont(font);

            //整数用のスタイル
            CellStyle style_int = book.createCellStyle();
            GetExcel.setBorder(style_int, BorderStyle.THIN);
            style_int.setDataFormat(format.getFormat("#,##0;-#,##0"));
            style_int.setVerticalAlignment(VerticalAlignment.TOP);
            style_int.setFont(font);

            //小数用のスタイル
            CellStyle style_double = book.createCellStyle();
            GetExcel.setBorder(style_double, BorderStyle.THIN);
            style_double.setDataFormat(format.getFormat("#,##0.0;-#,##0.0"));
            style_double.setVerticalAlignment(VerticalAlignment.TOP);
            style_double.setFont(font);

            //円表示用のスタイル
            CellStyle style_yen = book.createCellStyle();
            GetExcel.setBorder(style_yen, BorderStyle.THIN);
            style_yen.setDataFormat(format.getFormat("\"\\\"#,##0;\"\\\"-#,##0"));
            style_yen.setVerticalAlignment(VerticalAlignment.TOP);
            style_yen.setFont(font);

            //パーセント表示用のスタイル
            CellStyle style_percent = book.createCellStyle();
            GetExcel.setBorder(style_percent, BorderStyle.THIN);
            style_percent.setDataFormat(format.getFormat("0.0%"));
            style_percent.setVerticalAlignment(VerticalAlignment.TOP);
            style_percent.setFont(font);

            //日時表示用のスタイル
            CellStyle style_datetime = book.createCellStyle();
            GetExcel.setBorder(style_datetime, BorderStyle.THIN);
            style_datetime.setDataFormat(format.getFormat("yyyy/mm/dd hh:mm:ss"));
            style_datetime.setVerticalAlignment(VerticalAlignment.TOP);
            style_datetime.setFont(font);

            Row row;
            int rowNumber;
            Cell cell;
            int colNumber;

            //シートの作成(3シート作ってみる)
            Sheet sheet;
            sheet = book.createSheet();

            //シート名称の設定
            book.setSheetName(0,"本日の予約");

            //ヘッダ行の作成
            rowNumber = 0;
            colNumber = 0;
            row = sheet.createRow(rowNumber);
            cell = row.createCell(colNumber++);
            cell.setCellStyle(style_header);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("部屋番号");

            cell = row.createCell(colNumber++);
            cell.setCellStyle(style_header);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("予約");

            cell = row.createCell(colNumber++);
            cell.setCellStyle(style_header);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("宿泊中");

            cell = row.createCell(colNumber++);
            cell.setCellStyle(style_header);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("顧客ID");

            cell = row.createCell(colNumber++);
            cell.setCellStyle(style_header);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("顧客名");

            //ウィンドウ枠の固定
            sheet.createFreezePane(1, 1);

            //ヘッダ行にオートフィルタの設定
            sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, colNumber));

            //列幅の自動調整
            for (int i = 0; i <= colNumber; i++) {
                sheet.autoSizeColumn(i, true);
            }

            //データ行の生成(10行作ってみる)
            for (ReserveTodayListEntity todayList : rooms) {
                rowNumber++;
                colNumber = 0;
                row = sheet.createRow(rowNumber);
                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_string);
                cell.setCellType(CellType.STRING);
                cell.setCellValue(todayList.getItemnm());

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_string);
                cell.setCellType(CellType.STRING);
                cell.setCellValue(todayList.getReserved());

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_string);
                cell.setCellType(CellType.STRING);
                cell.setCellValue(todayList.getStayed());

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_int);
                cell.setCellType(CellType.STRING);
                cell.setCellValue(todayList.getCustomerid());

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_string);
                cell.setCellType(CellType.STRING);
                cell.setCellValue(todayList.getCustomernm());

                //列幅の自動調整
                for (int k = 0; k <= colNumber; k++) {
                    sheet.autoSizeColumn(k, true);
                }
            }

            //ファイル出力
            book.write(outputStream);
        
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
	        	if (outputStream != null) {
	                try {
	                    outputStream.close();
	                }
	                catch (IOException e) {
	                }
	        }
        }
        	
        
    }

    private static void setBorder(CellStyle style, BorderStyle border) {
        style.setBorderBottom(border);
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
    }

}
