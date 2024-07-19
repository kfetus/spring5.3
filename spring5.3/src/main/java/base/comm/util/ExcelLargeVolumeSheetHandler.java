package base.comm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.binary.XSSFBSheetHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

public class ExcelLargeVolumeSheetHandler implements SheetContentsHandler {

//	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelLargeVolumeSheetHandler.class);


	int currentRow = 0;
	private List<String> header = null;// 1번째 라인. 헤더
	private List<String> row = new ArrayList<String>();
	private List<List<String>> rows = new ArrayList<List<String>>();

	List<HashMap<String, String>> excelDataList = new ArrayList<HashMap<String, String>>();
	
	public List<List<String>> getRows() {
		return rows;
	}

	public List<String> getHeader() {
		return header;
	}

	public List<HashMap<String, String>> getExcelDataList() {
		return excelDataList;
	}

	@Override
	public void startRow(int rowNum) {
//		LOGGER.debug("startRow ====================================" + rowNum);
		this.currentRow = rowNum;
	}

	@Override
	public void endRow(int rowNum) {
//		LOGGER.debug("endRow ====================================" + rowNum);
		if (rowNum == 0) {
			header = new ArrayList<String>(row);
		} else {
			HashMap<String, String> excelDataMap = new HashMap<String, String>();
			for ( int i = 0 ; i < header.size(); i++) {
				excelDataMap.put(header.get(i), row.get(i));
			}
			excelDataList.add(excelDataMap);
			rows.add(new ArrayList<String>(row));
		}
		row.clear();
	}

	@Override
	public void cell(String cellReference, String formattedValue, XSSFComment comment) {
//		LOGGER.debug("cell ====================================" + cellReference + "|" + formattedValue);
		row.add(formattedValue);
	}

	@Override
	public void hyperlinkCell(String cellReference, String formattedValue, String url, String toolTip,
			XSSFComment comment) {
	}

}
