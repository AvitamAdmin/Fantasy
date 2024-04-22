package com.avitam.fantasy11.qa.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExcelUtils {

	private static FileInputStream inputWorkbook;
	private static FileOutputStream outputWorkbook;

	/**
	 * Method to Get workbook
	 *
	 * @param fileName Excel sheet file name
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	private static Workbook getWorkBook(String fileName) {
		Workbook w = null;
		try {
			inputWorkbook = new FileInputStream(fileName);
			w = WorkbookFactory.create(inputWorkbook);
		} catch (Exception e) {
		}
		return w;
	}

	/**
	 * Method to Get Excel sheet exists or not
	 *
	 * @param fileName Excel sheet file name
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static boolean isExcelSheetExits(String fileName) {
		boolean isFileExists = false;
		try {
			inputWorkbook = new FileInputStream(fileName);
			isFileExists = true;
		} catch (Exception e) {
		}
		return isFileExists;
	}

	/**
	 * Method to create workbook
	 *
	 * @param fileName Excel sheet file name
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	private static Workbook createWorkBook(String fileName) throws InvalidFormatException, IOException {
		return getWorkBook(fileName);
	}

	/**
	 * Method to create workbook
	 *
	 * @throws InvalidFormatException
	 */
	private static Sheet createSheet(Workbook w, int sheetNo) throws IOException, InvalidFormatException {
		try {
			return w.getSheetAt(sheetNo);
		} catch (Exception e) {
			return w.createSheet();
		}
	}

	/**
	 * Method to create workbook
	 *
	 * @throws InvalidFormatException
	 */
	private static Row createRow(Sheet sheet, int rowNo) throws IOException, InvalidFormatException {
		try {
			Row row = sheet.getRow(rowNo);
			if (row == null) {
				return sheet.createRow(rowNo);
			} else {
				return row;
			}
		} catch (Exception e) {
			return sheet.createRow(0);
		}
	}

	/**
	 * Method to create workbook
	 *
	 * @throws InvalidFormatException
	 */
	private static Cell createCell(Row row, int columnNo, String value) {
		try {
			Cell cell = row.getCell(columnNo);
			if (cell == null) {
				return row.createCell(columnNo);
			} else {
				return cell;
			}
		} catch (Exception e) {
			return row.getCell(columnNo);
		}
	}

	/**
	 * Method to Write workbook
	 *
	 * @param fileName Excel sheet file name
	 * @throws InvalidFormatException
	 */
	private static void writeWorkBook(Workbook w, String fileName) throws IOException, InvalidFormatException {
		outputWorkbook = new FileOutputStream(new File(fileName));
		w.write(outputWorkbook);
		outputWorkbook.close();
	}

	/**
	 * Method to close workbook
	 *
	 * @param w workbook to be closed
	 */
	private static void closeWorkBook(Workbook w) throws IOException {
		inputWorkbook.close();
	}

	/**
	 * Method to obtain the row no of the test name
	 *
	 * @param sheetNo  starting at index 0
	 * @param testName - the test name to fetch the data
	 * @param columnNo - index of the key column name
	 * @return the row no of the test name
	 * @throws IOException
	 */
	public static ArrayList<Integer> obtainRowNos(String fileName, int sheetNo, int columnNo, String testName) throws IOException {
		Workbook w = getWorkBook(fileName);
		ArrayList<Integer> rows = new ArrayList<Integer>();
		Sheet sheet = w.getSheetAt(sheetNo);
		for (int j = 0; j <= sheet.getLastRowNum(); j++) {
			Row row = sheet.getRow(j);
			if (row != null) {
				String cellContent = getCellValue(sheet.getRow(j), columnNo);
				if (cellContent.equalsIgnoreCase(testName)) {
					rows.add(j);
				}
			} else {
				break;
			}
		}
		return rows;
	}

	/**
	 * Method to obtain the row no of the test name
	 *
	 * @param w        the workbook to read data from
	 * @param sheetNo  starting at index 0
	 * @param testName - the test name to fetch the data
	 * @param columnNo - index of the key column name
	 * @return the row no of the test name
	 * @throws IOException
	 */
	private static int obtainRowNo(Workbook w, int sheetNo, int columnNo, String testName) throws IOException {
		int rowNo = -1;
		Sheet sheet = w.getSheetAt(sheetNo);
		for (int j = 0; j <= sheet.getLastRowNum(); j++) {
			Row row = sheet.getRow(j);
			if (row != null) {
				String cellContent = getCellValue(sheet.getRow(j), columnNo);
				if (cellContent.equalsIgnoreCase(testName)) {
					rowNo = j;
					break;
				}
			} else {
				break;
			}
		}
		return rowNo;
	}

	/**
	 * Get Cell value
	 *
	 * @param row
	 */
	private static String getCellValue(Row row, int columnNo) {
		if (row != null) {
			Cell cell = row.getCell(columnNo, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cell.setCellType(CellType.STRING);
			return cell.getStringCellValue().trim();
		} else {
			return "";
		}
	}

	/**
	 * Set Cell value
	 *
	 * @param row the row to read data from
	 * @return
	 */
	private static void setCellValue(Row row, int columnNo, String value) {
		Cell cell = createCell(row, columnNo, value);
		cell.setCellType(CellType.STRING);
		cell.setCellValue(value);
	}

	/**
	 * Method obtaining test data of the corresponding test name
	 *
	 * @param fileName - name of the excel file
	 * @param sheetNo  starting at index 0
	 * @param columnNo - index of the column name (starting at index 0) to search the text
	 * @param testName - the test name to fetch the data (this is key name searched
	 *                 in column)
	 * @return hash map containing all the test data
	 * @throws Exception
	 */
	public static HashMap<String, String> obtainTestData(String fileName, int sheetNo, int columnNo, String testName)
			throws Exception {
		HashMap<String, String> testData = new HashMap<String, String>();
		Workbook w = getWorkBook(fileName);

		// To obtain the row no of the test
		int rowNo = obtainRowNo(w, sheetNo, columnNo, testName);
		testData = obtainTestData(w, sheetNo, rowNo);
		return testData;
	}

	/**
	 * Method obtaining test data of the corresponding test name
	 *
	 * @param fileName - name of the excel file
	 * @param sheetNo  starting at index 0
	 * @param rowNo    - index of the row (starting at index 0) to search the text
	 *                 in column)
	 * @return hash map containing all the test data
	 * @throws Exception
	 */
	public static HashMap<String, String> obtainTestDataRow(String fileName, int sheetNo, int rowNo) throws Exception {
		HashMap<String, String> testData = new HashMap<String, String>();
		Workbook w = getWorkBook(fileName);
		testData = obtainTestData(w, sheetNo, rowNo);
		return testData;
	}

	/**
	 * Method obtaining test data of the corresponding test name
	 *
	 * @param sheetNo starting at index 0
	 * @param rowNo   - index of the row (starting at index 0) to search the text
	 *                in column)
	 * @return hash map containing all the test data
	 * @throws Exception
	 */
	public static HashMap<String, String> obtainTestData(Workbook w, int sheetNo, int rowNo) throws Exception {
		List<Integer> rowList = new ArrayList<Integer>();
		HashMap<String, String> testData = new HashMap<String, String>();

		// Add the row nos to the list
		rowList.add(0);
		rowList.add(rowNo);

		// Return if row no is < 0
		if (rowNo < 0) {
			// System.out.println("Test Name not found in the excel: " + testName);
		} else {
			Sheet sheet = w.getSheetAt(sheetNo);
			Row rowHeader = sheet.getRow(rowList.get(0));
			Row rowValue = sheet.getRow(rowList.get(1));
			for (int i = 0; i < rowHeader.getLastCellNum(); i++) {
				String header = getCellValue(rowHeader, i);
				String value = getCellValue(rowValue, i);
				if (header != "") {
					testData.put(header, value);
				} else {
					break;
				}
			}
		}
		closeWorkBook(w);
		return testData;
	}

	/**
	 * Method obtaining test data as list with condition (based on keyName)
	 *
	 * @param fileName    - name of the excel file
	 * @param sheetNo     - sheet no to fetch data (starting at index 0)
	 * @param columnNo    - index of the test data column (starting at index 0)
	 * @param keyColumnNo - - index of the Key column (starting at index 0)
	 * @param keyName     - key name to be checked
	 * @return Arraylist containing test data
	 * @throws Exception
	 */
	public static ArrayList<String> obtainTestData(String fileName, int sheetNo, int columnNo, int keyColumnNo, String keyName) throws Exception {
		ArrayList<String> testNames = new ArrayList<String>();
		Workbook w = getWorkBook(fileName);
		Sheet sheet = w.getSheetAt(sheetNo);
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				String execute = getCellValue(row, keyColumnNo);
				if (execute.equalsIgnoreCase(keyName)) {
					String testName = getCellValue(row, columnNo);
					testNames.add(testName);
				}
			} else {
				break;
			}
		}
		closeWorkBook(w);
		return testNames;
	}

	/**
	 * Method obtaining test data as list (Row wise)
	 *
	 * @param fileName - name of the excel file
	 * @param sheetNo  - sheet no to fetch data (starting at index 0)
	 * @param columnNo - index of the test data column (starting at index 0)
	 * @return Arraylist containing test data
	 * @throws Exception
	 */
	public static ArrayList<String> obtainTestData(String fileName, int sheetNo, int columnNo) throws Exception {
		ArrayList<String> inputData = new ArrayList<String>();
		Workbook w = getWorkBook(fileName);
		Sheet sheet = w.getSheetAt(sheetNo);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			String Data = getCellValue(sheet.getRow(i), columnNo);
			if (StringUtils.isNotBlank(Data))
				inputData.add(Data);
		}
		closeWorkBook(w);
		return inputData;
	}

	/**
	 * Method obtaining test data as list
	 *
	 * @param fileName  - name of the excel file
	 * @param sheetNo   - sheet no to fetch data (starting at index 0)
	 * @param columnNo  - index of the test data column (starting at index 0)
	 * @param withEmpty - true to include empty string as well
	 * @return Arraylist containing test data
	 * @throws Exception
	 */
	public static ArrayList<String> obtainTestData(String fileName, int sheetNo, int columnNo, boolean withEmpty)
			throws Exception {
		ArrayList<String> inputData = new ArrayList<String>();
		Workbook w = getWorkBook(fileName);
		Sheet sheet = w.getSheetAt(sheetNo);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			String Data = getCellValue(sheet.getRow(i), columnNo);
			if (StringUtils.isNotBlank(Data) || withEmpty)
				inputData.add(Data);
		}
		closeWorkBook(w);
		return inputData;
	}

	/**
	 * Method obtaining test data as key value pair
	 *
	 * @param fileName      - name of the excel file
	 * @param sheetNo       - starting at index 0
	 * @param keyColumnNo   - the column text stored as key
	 * @param valueColumnNo - the column text stored as value
	 * @return hash map containing key value pair test data
	 * @throws Exception
	 */
	public static HashMap<String, String> obtainTestData(String fileName, int sheetNo, int keyColumnNo, int valueColumnNo) throws Exception {
		HashMap<String, String> testData = new LinkedHashMap<String, String>();
		Workbook w = getWorkBook(fileName);
		Sheet sheet = w.getSheetAt(sheetNo);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				String header = getCellValue(row, keyColumnNo);
				String value = getCellValue(row, valueColumnNo);
				if (header != "") {
					testData.put(header, value);
				} else {
					break;
				}
			} else {
				break;
			}
		}
		closeWorkBook(w);
		return testData;
	}

	/**
	 * Method Writing test data as list (Column wise)
	 *
	 * @param fileName - name of the excel file
	 * @param sheetNo  - sheet no to fetch data (starting at index 0)
	 * @throws Exception
	 */
	public static int getLastRowNumber(String fileName, int sheetNo) throws Exception {
		Workbook w = createWorkBook(fileName);
		Sheet sheet = createSheet(w, sheetNo);
		closeWorkBook(w);
		return sheet.getLastRowNum();
	}

	/**
	 * Method Writing test data as list (Column wise)
	 *
	 * @param fileName - name of the excel file
	 * @param sheetNo  - sheet no to fetch data (starting at index 0)
	 * @throws Exception
	 */
	public static ArrayList<Integer> obtainRowNos(String fileName, int sheetNo) throws Exception {
		ArrayList<Integer> rowList = new ArrayList<>();
		Workbook w = createWorkBook(fileName);
		Sheet sheet = createSheet(w, sheetNo);
		closeWorkBook(w);
		int lastRow = sheet.getLastRowNum();
		for (int i = 1; i <= lastRow; i++) {
			rowList.add(i);
		}
		return rowList;
	}

	/**
	 * Method Writing test data as list (Row wise)
	 *
	 * @param fileName  - name of the excel file
	 * @param sheetNo   - sheet no to fetch data (starting at index 0)
	 * @param columnNo  - index of the test data column (starting at index 0)
	 * @param inputData - date to be written in excel sheet
	 * @throws Exception
	 */
	public static void writeTestDataRow(String fileName, int sheetNo, int columnNo, ArrayList<String> inputData) throws Exception {
		Workbook w = createWorkBook(fileName);
		Sheet sheet = createSheet(w, sheetNo);
		for (int i = 0; i < inputData.size(); i++) {
			Row row = createRow(sheet, i);
			setCellValue(row, columnNo, inputData.get(i));
		}
		writeWorkBook(w, fileName);
	}

	/**
	 * Method Writing test data as list (Row wise)
	 *
	 * @param fileName  - name of the excel file
	 * @param sheetNo   - sheet no to fetch data (starting at index 0)
	 * @param columnNo  - index of the test data column (starting at index 0)
	 * @param inputData - date to be written in excel sheet
	 * @throws Exception
	 */
	public static void writeTestDataRow(String fileName, int sheetNo, int rowNo, int columnNo, ArrayList<String> inputData) throws Exception {
		Workbook w = createWorkBook(fileName);
		Sheet sheet = createSheet(w, sheetNo);
		for (int i = 0; i < inputData.size(); i++) {
			Row row = createRow(sheet, i + rowNo);
			setCellValue(row, columnNo, inputData.get(i));
		}
		writeWorkBook(w, fileName);
	}

	/**
	 * Method Writing test data as list (Row-Column wise)
	 *
	 * @param fileName  - name of the excel file
	 * @param sheetNo   - sheet no to fetch data (starting at index 0)
	 * @param columnNo  - index of the test data column (starting at index 0)
	 * @param inputData - date to be written in excel sheet
	 * @throws Exception
	 */
	public static void writeTestDataRow(String fileName, int sheetNo, int rowNo, int columnNo, HashMap<String, String> inputData) throws Exception {
		Workbook w = createWorkBook(fileName);
		Sheet sheet = createSheet(w, sheetNo);
		int head = 0;
		for (String key : inputData.keySet()) {
			Row row = createRow(sheet, rowNo);
			setCellValue(row, (head + columnNo), key);
			setCellValue(row, (head + columnNo + 1), inputData.get(key));
			rowNo++;
		}
		writeWorkBook(w, fileName);
	}

	/**
	 * Method Writing test data as list (Column wise)
	 *
	 * @param fileName  - name of the excel file
	 * @param sheetNo   - sheet no to fetch data (starting at index 0)
	 * @param columnNo  - index of the test data column (starting at index 0)
	 * @param inputData - date to be written in excel sheet
	 * @throws Exception
	 */
	public static void writeTestDataColumn(String fileName, int sheetNo, int columnNo, ArrayList<String> inputData) throws Exception {
		Workbook w = createWorkBook(fileName);
		Sheet sheet = createSheet(w, sheetNo);
		Row row = createRow(sheet, 0);
		for (int i = 0; i < inputData.size(); i++) {
			setCellValue(row, (i + columnNo), inputData.get(i));
		}
		writeWorkBook(w, fileName);
	}

	/**
	 * Method Writing test data as list (Column wise)
	 *
	 * @param fileName  - name of the excel file
	 * @param sheetNo   - sheet no to fetch data (starting at index 0)
	 * @param rowNo     - index of the test data row (starting at index 0)
	 * @param columnNo  - index of the test data column (starting at index 0)
	 * @param inputData - date to be written in excel sheet
	 * @throws Exception
	 */
	public static void writeTestDataColumn(String fileName, int sheetNo, int rowNo, int columnNo, ArrayList<String> inputData) throws Exception {
		Workbook w = createWorkBook(fileName);
		Sheet sheet = createSheet(w, sheetNo);
		Row row = createRow(sheet, rowNo);
		for (int i = 0; i < inputData.size(); i++) {
			setCellValue(row, (i + columnNo), inputData.get(i));
		}
		writeWorkBook(w, fileName);
	}

	/**
	 * Method Writing test data as list (Row-Column wise)
	 *
	 * @param fileName  - name of the excel file
	 * @param sheetNo   - sheet no to fetch data (starting at index 0)
	 * @param rowNo     - index of the test data row (starting at index 0)
	 * @param columnNo  - index of the test data column (starting at index 0)
	 * @param inputData - date to be written in excel sheet
	 * @throws Exception
	 */
	public static void writeTestDataColumn(String fileName, int sheetNo, int rowNo, int columnNo, HashMap<String, String> inputData) throws Exception {
		Workbook w = createWorkBook(fileName);
		Sheet sheet = createSheet(w, sheetNo);
		int head = 0;
		Row rowHeading = sheet.createRow(0);
		Row rowValue = createRow(sheet, rowNo);
		for (String key : inputData.keySet()) {
			setCellValue(rowHeading, (head + columnNo), key);
			setCellValue(rowValue, (head + columnNo), inputData.get(key));
			columnNo++;
		}
		writeWorkBook(w, fileName);
	}
}
