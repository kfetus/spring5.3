package base.comm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

	public static List<HashMap<String, String>> readExcelMultiPartFile(MultipartFile excelFile, String sheetName) throws Exception {
		LOGGER.debug("readExcelMultiPartFile ====================================");

		XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());

		return readExcel(workbook,sheetName);
	}

	
	public static List<HashMap<String, String>> readExcelFile(File excelFile, String sheetName) throws Exception {
		LOGGER.debug("readExcelFile ====================================");
		XSSFWorkbook workbook = new XSSFWorkbook(excelFile);

		return readExcel(workbook,sheetName);
	}

	/**
	 * Excel Upload시에 List<HashMap<String, String>> 로 반환.
	 * 엑셀파일 첫째 줄은 헤더부, 둘째줄부터 데이터 부임.
	 * 
	 * @param workbook XSSFWorkbook
	 * @param sheetName String 읽어들일 Excel Sheet 명
	 * @return
	 * @throws Exception
	 */
	public static List<HashMap<String, String>> readExcel(XSSFWorkbook workbook, String sheetName) throws Exception {
		LOGGER.debug("readExcel ====================================");

		List<HashMap<String, String>> excelDataList = new ArrayList<HashMap<String, String>>();
		try {
			XSSFSheet sheet = workbook.getSheet(sheetName);
			ArrayList<String> keyList = new ArrayList<String>();
			int nMaxRowCnt = sheet.getPhysicalNumberOfRows();
			for (int nRow = 0; nRow < nMaxRowCnt; nRow++) {

				XSSFRow row = sheet.getRow(nRow);
				int nMaxColCnt = row.getLastCellNum();
				HashMap<String, String> excelDataMap = new HashMap<String, String>();
				;
				for (int nCol = 0; nCol < nMaxColCnt; nCol++) {

					XSSFCell cell = row.getCell(nCol);
					String value = "";

					if (cell == null) {
//						LOGGER.debug(null);
					} else {
						switch (cell.getCellType()) {
						case FORMULA:
							value = cell.getCellFormula();
							break;
						case NUMERIC:
							if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								value = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
							} else {
								double numericCellValue = cell.getNumericCellValue();
								if (numericCellValue == Math.rint(numericCellValue)) {
									value = String.valueOf((int) numericCellValue);
								} else {
									value = String.valueOf(numericCellValue);
								}
							}
							break;
						case STRING:
							value = cell.getStringCellValue() + "";
							break;
						case BLANK:
							value = cell.getBooleanCellValue() + "";
							break;
						case ERROR:
							value = cell.getErrorCellValue() + "";
							break;
						default:
							value = cell.getStringCellValue();
							break;
						}
					}

					if (nRow == 0) {
						keyList.add(value);
					} else {
						excelDataMap.put(keyList.get(nCol), value);
					}
//					LOGGER.debug(value);
				}
				if (nRow != 0) {
					excelDataList.add(excelDataMap);
				}
			}
//			LOGGER.debug("==================================== keyList" + keyList);
//			LOGGER.debug("==================================== LOOP END" + excelDataList);
		} finally {
			workbook.close();
		}

		return excelDataList;
	}

	/**
	 * 엑셀 파일 생성
	 * 
	 * @param keySet 첫째줄 헤더 부
	 * @param data 둘째줄 부터 시작되는 데이타 부
	 * @param filePath 생성할 파일 경로
	 * @throws Exception
	 */
	public static void makeExcel(List<String> keySet, List<HashMap<String,String>> data, String filePath) throws Exception {
		XSSFWorkbook workBook = new XSSFWorkbook(); 
		
		CellStyle defaultStyle = workBook.createCellStyle();
		defaultStyle.setBorderTop(BorderStyle.THIN);
		defaultStyle.setBorderLeft(BorderStyle.THIN);
		defaultStyle.setBorderRight(BorderStyle.THIN);
		defaultStyle.setBorderBottom(BorderStyle.THIN);
		defaultStyle.setAlignment(HorizontalAlignment.CENTER);
		defaultStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		Sheet sheet = workBook.createSheet();
		sheet.setDefaultRowHeightInPoints(30);
		
		Row headRow = sheet.createRow(0);
		for (int i = 0 ; i < keySet.size() ; i++) {
			Cell cell = headRow.createCell(i);
			cell.setCellStyle(defaultStyle);
			cell.setCellValue(keySet.get(i));
			sheet.setColumnWidth(i, 3000);
		}
		
		
		for (int i = 0 ; i < data.size() ; i++) {
			Row row = sheet.createRow(i+1);
			HashMap<String,String> rowData = data.get(i);
			for (int j = 0 ; j < keySet.size() ; j++) {
				
				Cell cell = row.createCell(j);
				cell.setCellStyle(defaultStyle);
				cell.setCellValue(rowData.get(keySet.get(j)));
				sheet.setColumnWidth(j, 3000);
			}
		}
		
		FileOutputStream fileOut = null;
		try {
			File xlsFile = new File(filePath);
			fileOut = new FileOutputStream(xlsFile);
			workBook.write(fileOut);
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    workBook.close();
			fileOut.close();
		}
	}

	public static void main(String[] args) {
		String excelFullPath = "C:/Users/PMG/Desktop/sampleData.xlsx";
		File file = new File(excelFullPath);
		
		try {
			List<HashMap<String, String>> retList = readExcelFile(file, "sample");
			System.out.println(retList);

			List<String> keyList = Arrays.asList("goodsno", "goodsnm", "sellYn", "goodscd", "maker", "keyword", "strprice", "shortdesc", "coupon", "coupon_ea", "coupon_usecnt", "coupon_date", "regdt");
			
			for(HashMap<String, String> rowMap : retList) {
				if("".equals( rowMap.get("maker").trim()) ) {
					rowMap.put("maker", "NIDAS");
				}
				if("".equals( rowMap.get("goodscd").trim()) ) {
					rowMap.put("goodscd", rowMap.get("goodsnm"));
				}
				SecureRandom random = SecureRandom.getInstanceStrong();
				rowMap.put("coupon_usecnt", random.nextInt(5)+"");
			}
			
			makeExcel(keyList,retList,"C:/Users/PMG/Desktop/sampleData22.xlsx");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		String excelFullPath = "C:/Users/PMG/Desktop/testData.xlsx";
//		try {
//			String[] keyArrau = { "SEQ", "CODE", "MENU_DEPTH_1", "MENU_DEPTH_2", "MENU_DEPTH_3", "MENU_DEPTH_4",
//					"PROG_FILE_NM", "MENU_NAME", "STATE", "MASTER_NAME", "START_DT", "CNG_DT" };
//
//			List<HashMap<String, String>> retList = readExcelFile(file, "sheet1");
//			for (int i = 0; i < retList.size(); i++) {
//				for (String key : keyArrau) {
//					System.out.print(key + "=" + retList.get(i).get(key) + " ");
//				}
//				System.out.println();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
