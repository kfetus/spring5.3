package base.comm.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * Excel Upload시에 List<HashMap<String, String>> 로 반환
	 * 
	 * @param excelFile java.io.File
	 * @param sheetName 읽어들일 Excel Sheet 명
	 * @return
	 * @throws Exception
	 */
	public static List<HashMap<String, String>> readExcel(File excelFile, String sheetName) throws Exception {
		LOGGER.debug("readExcel ====================================");

		List<HashMap<String, String>> excelDataList = new ArrayList<HashMap<String, String>>();
		XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
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
						LOGGER.debug(null);
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
					LOGGER.debug(value);
				}
				if (nRow != 0) {
					excelDataList.add(excelDataMap);
				}
				LOGGER.debug("====================================");
			}
			LOGGER.debug("==================================== LOOP END" + excelDataList);
		} finally {
			workbook.close();
		}

		return excelDataList;

	}

	public static void main(String[] args) {
		String excelFullPath = "C:/Users/PMG/Desktop/testData.xlsx";
		File file = new File(excelFullPath);
		try {
			String[] keyArrau = { "SEQ", "CODE", "MENU_DEPTH_1", "MENU_DEPTH_2", "MENU_DEPTH_3", "MENU_DEPTH_4",
					"PROG_FILE_NM", "MENU_NAME", "STATE", "MASTER_NAME", "START_DT", "CNG_DT" };

			List<HashMap<String, String>> retList = readExcel(file, "sheet1");
			for (int i = 0; i < retList.size(); i++) {
				for (String key : keyArrau) {
					System.out.print(key + "=" + retList.get(i).get(key) + " ");
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
