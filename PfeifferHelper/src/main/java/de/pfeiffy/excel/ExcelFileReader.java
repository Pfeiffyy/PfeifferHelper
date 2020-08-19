package de.pfeiffy.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * ExcelFileProxy liest die Excel-Datei ein und erzeugt ein tabellarisches
 * Abbild der Arbeitsmappe in Form von verschachtelten Listen.
 *
 * @author Gernot Segieth
 */
public class ExcelFileReader {

	private File excelFile;
	private Workbook workbook;
	private FormulaEvaluator evaluator;
	private boolean isEmptyRow;

	public ExcelFileReader(File excelFile) throws ClassNotFoundException, IOException, InvalidFormatException {
		this.excelFile = excelFile;
		createWorkbook(excelFile);
	}

	/**
	 * Gibt den Inhalt des übergebenen Tabellenblattes in einer geschachtelten Liste
	 * zurück.
	 *
	 * @param sheetIndex der Index des Tabellenblattes
	 * @return eine Liste aus Listen (Zeilen mit Spalten)
	 */
	public List<List<?>> getSheetContent(int sheetIndex) {
		evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		return getSheetContent(sheetIndex, 0, 0, 0, 0);
	}

	/**
	 * Diese Methode sucht sich der erste und letzte zeile/Spalte selbst, aber
	 * achtung, das Objekt muss angepasst werden
	 * 
	 * @param sheetName
	 * @param linsObenCell
	 * @param rechtsUnterCell
	 * @return
	 */

	public List<Mapping> getSheetContentArray(String sheetName, int cols) {
		evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		Sheet sheet = workbook.getSheet(sheetName);
		List<Mapping> mappingList = new ArrayList<Mapping>();

		int rowLiO = sheet.getFirstRowNum() + 1;
		int rowReU = sheet.getLastRowNum();

		System.out.println();
		String blatt = "";
		// über die Zeilen gehen
		for (int z = rowLiO; z < rowReU; z++) {

			Mapping mapping = new Mapping();
			int s = 0;

			String xx = gibZelleInhalt(sheet, z, s);
			mapping.setBlatt(gibZelleInhalt(sheet, z, s));
			// da das Blatt nur ausgefüllt ist, wenn sich etwas ändert
			if (mapping.getBlatt() == null || mapping.getBlatt().equalsIgnoreCase("")) {
				mapping.setBlatt(blatt);
			}
			blatt = mapping.getBlatt();
			s++;
			mapping.setBezeichnung(gibZelleInhalt(sheet, z, s));
			s++;
			mapping.setZellenkoordinate(gibZelleInhalt(sheet, z, s));
			s++;
			mapping.setFormat(gibZelleInhalt(sheet, z, s));
			s++;
			mapping.setZiel(gibZelleInhalt(sheet, z, s));
			s++;
			mappingList.add(mapping);

		}

		return mappingList;

	}

	public List<Mapping> getSheetContentArray(String sheetName, String linsObenCell, String rechtsUnterCell) {
		evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		Sheet sheet = workbook.getSheet(sheetName);
		List<Mapping> mappingList = new ArrayList<Mapping>();

		CellAddress caLiO = new CellAddress(linsObenCell);
		int rowLiO = caLiO.getRow();
		int coLiO = caLiO.getColumn();

		System.out.println();

		CellAddress caReU = new CellAddress(rechtsUnterCell);
		int rowReU = caReU.getRow();
		int colReU = caReU.getColumn();

		System.out.println();
		String blatt = "";
		// über die Zeilen gehen
		for (int z = rowLiO; z < rowReU; z++) {

			Mapping mapping = new Mapping();
			int s = 0;

			String xx = gibZelleInhalt(sheet, z, s);
			mapping.setBlatt(gibZelleInhalt(sheet, z, s));
			// da das Blatt nur ausgefüllt ist, wenn sich etwas ändert
			if (mapping.getBlatt() == null || mapping.getBlatt().equalsIgnoreCase("")) {
				mapping.setBlatt(blatt);
			}
			blatt = mapping.getBlatt();
			s++;
			mapping.setBezeichnung(gibZelleInhalt(sheet, z, s));
			s++;
			mapping.setZellenkoordinate(gibZelleInhalt(sheet, z, s));
			s++;
			mapping.setFormat(gibZelleInhalt(sheet, z, s));
			s++;
			mapping.setZiel(gibZelleInhalt(sheet, z, s));
			s++;
			mappingList.add(mapping);

		}

		return mappingList;

	}

	private String gibZelleInhalt(Sheet sheet, int z, int s) {
		Row row = sheet.getRow(z);

		Cell cell = row.getCell(s);
		String returnString = (String) getData(cell);
		return returnString;
	}

	/**
	 * Gibt den Inhalt des übergebenen Tabellenblattes in einer geschachtelten Liste
	 * zurück.
	 *
	 * @param sheetIndex der Index des Tabellenblattes
	 * @param firstRow   in dieser Zeile mit dem Auslesen beginnen
	 * @param firstCol   in dieser Spalte mit dem Auslesen beginnen
	 * @param lastRow    letzte auszulesene Zeile
	 * @param lastCol    letzte auszulesene Spalte
	 * @return eine Liste aus Listen (Zeilen mit Spalten)
	 */
	public List<List<?>> getSheetContent(int sheetIndex, int firstRow, int firstCol, int lastRow, int lastCol) {
		evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		List<List<?>> sheetData = new ArrayList<List<?>>();

		// Range festlegen (bei Übergabe 0 - ermitteln, sonst Range verarbeiten
		int lastRowNum;
		if (lastRow < 1) {
			lastRowNum = sheet.getLastRowNum();
		} else {
			lastRowNum = lastRow;
		}

		for (int actuallyRow = firstRow; actuallyRow <= lastRowNum; actuallyRow++) {

			Row row = sheet.getRow(actuallyRow); // leere Zeile kann NPE auslösen!

			if (row != null) { // leere Zeile abfangen
				List<Object> rowData = new ArrayList<Object>();
				isEmptyRow = true; // Zeile erstmal als leer ansehen

				int lastColNum;
				if (lastCol < 1) {
					lastColNum = row.getLastCellNum();
				} else {
					lastColNum = lastCol;
				}

				for (int actuallyCol = firstCol; actuallyCol < lastColNum; actuallyCol++) {
					Cell cell = row.getCell(actuallyCol);
					rowData.add(getData(cell));
				}
				if (!isEmptyRow) { // leere Zeilen nicht aufnehmen
					sheetData.add(rowData);
				}
			}
		}
		return sheetData;
	}

	/**
	 * 
	 * @param sheetIndex
	 * @param rowIn
	 * @param colIn
	 * @return gibt den aufbereiteten Wert einer Zelle zurück
	 */

	public String getCellInhalt(String sheetName, int rowIn, int colIn) {
		String returnString = "";
		evaluator = workbook.getCreationHelper().createFormulaEvaluator();

		Sheet sheet = workbook.getSheet(sheetName);

		Row row = sheet.getRow(rowIn); // leere Zeile kann NPE auslösen!

		if (row != null) {
			Cell cell = row.getCell(colIn);
			returnString = (String) getData(cell);
		}
		return returnString;
	}

	/**
	 * 
	 * @param sheetName
	 * @param adress
	 * @return gibt den aufbereiteten Wert einer Zelle zurück
	 */

	public String getCellInhalt(String sheetName, String adress) {
		String returnString = "";
		evaluator = workbook.getCreationHelper().createFormulaEvaluator();

		Sheet sheet = workbook.getSheet(sheetName);

		CellAddress ca = new CellAddress(adress);
		try {
			Row row = sheet.getRow(ca.getRow()); // leere Zeile kann NPE auslösen!
			Cell cell = row.getCell(ca.getColumn());

			System.out.println(cell.getCellTypeEnum());

			System.out.println("==> " + getData(cell) + " / " + sheetName + " / " + adress);
			returnString = (String) getData(cell);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return returnString;
	}

	/**
	 * Schreibt Daten einer tabellenartig aufgebauten Liste in das Arbeitsblatt.
	 *
	 * @param sheetData die Daten aus der Liste, die in das Tabellenblatt
	 *                  geschrieben werden sollen
	 * @param sheetName Name des Tabellenblattes
	 */
	public void setSheetContent(List<List<?>> sheetData, String sheetName) {
		String safeName = WorkbookUtil.createSafeSheetName(sheetName);
		CreationHelper createHelper = workbook.getCreationHelper();
		Sheet sheet = workbook.createSheet(safeName);

		for (int i = 0, j = sheetData.size(); i < j; i++) {
			Row row = sheet.createRow(i); // Zeile erzeugen
			List<?> rowList = sheetData.get(i); // Zeile holen
			for (int x = 0, y = rowList.size(); x < y; x++) {
				Cell cell = row.createCell(x); // Zelle erzeugen

				Object cellValue = rowList.get(x);
				if (cellValue instanceof Number) {
					cell.setCellValue((Double) cellValue);
				} else if (cellValue instanceof Date) {
					cell.setCellValue((Date) cellValue);
				} else if (cellValue instanceof Boolean) {
					cell.setCellValue((Boolean) cellValue);
				} else if (cellValue instanceof String) {
					cell.setCellValue(createHelper.createRichTextString(cellValue.toString()));
				}
			}
		}

	}

	/**
	 * Gibt die Namen aller Tabellenblätter zurück.
	 *
	 * @return die Namen der Tabellenblätter
	 */
	public String[] getSheetNames() {
		String[] sheetNames = new String[getNumberOfSheets()];
		for (int i = 0; i < sheetNames.length; i++) {
			sheetNames[i] = getSheetName(i);
		}
		return sheetNames;
	}

	/**
	 * Gibt die Anzahl der Tabellenblätter in der Excel-Datei zurück.
	 *
	 * @return die Anzahl
	 */
	public int getNumberOfSheets() {
		return workbook.getNumberOfSheets();
	}

	/**
	 * Gibt den Namen des Tabellenblattes am übergebenen Index zurück.
	 *
	 * @param sheetIndex der Index
	 * @return das Tabelleenblatt
	 */
	public String getSheetName(int sheetIndex) {
		return workbook.getSheetName(sheetIndex);
	}

	/**
	 * Setzt den Namen des Tabellenblattes am übergebenen Index.
	 *
	 * @param index     der Index des Tabellenblattes in der Arbeitsmappe
	 * @param sheetName der Name des Tabellenblattes
	 */
	public void setSheetName(int index, String sheetName) {
		workbook.setSheetName(index, sheetName);
	}

	/**
	 * Gibt den Index des aktiven Tabellenblattes zurück.
	 *
	 * @return der Index des aktiven Tabellenblattes
	 */
	public int getActiveSheetIndex() {
		return workbook.getActiveSheetIndex();
	}

	/**
	 * Setzt das aktive Tabellenblatt.
	 *
	 * @param index der Index des aktiven Tabellenblattes in der Arbeitsmappe
	 */
	public void setActiveSheet(int index) {
		workbook.setActiveSheet(index);
	}

	/**
	 * Gibt das Datum der letzten Änderung der Excel-Datei zurück.
	 *
	 * @return
	 */
	public long lastModified() {
		return excelFile.lastModified();
	}

	/**
	 * Gibt den genauen (einzigen) Pfad zur geladenen Excel-Datei zurück.
	 *
	 * @return Der Pfad zur geladenen Excel-Datei. //@throws java.io.IOException
	 */
	public String getPath() /* throws IOException */ {
		return excelFile.getAbsolutePath();
	}

	/**
	 * Setzt den Pfad zur Excel-Datei und erzeugt ein Abbild des Workbooks (alle
	 * Tabellenblätter).
	 *
	 * @param excelFile
	 * @throws java.lang.ClassNotFoundException
	 * @throws java.io.IOException
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
	 */
	public void setPath(File excelFile) throws ClassNotFoundException, IOException, InvalidFormatException {
		this.excelFile = excelFile;
		update();
	}

	/**
	 * Liest das Workbook der Excel-Datei neu ein.
	 *
	 * @throws java.lang.ClassNotFoundException
	 * @throws java.io.IOException
	 * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
	 */
	public synchronized void update() throws ClassNotFoundException, IOException, InvalidFormatException {
		createWorkbook(excelFile);
	}

//	public void createWorkbook(Map<String, List<List<?>>> sheetData) throws IOException {
//		Set<String> keys = sheetData.keySet();
//		Iterator<String> it = keys.iterator();
//		while (it.hasNext()) {
//			String key = it.next();
//			this.setSheetContent(sheetData.get(key), key);
//		}
//		createExcelFile(excelFile);
//	}

	// erzeugt ein Workbook aus der übergebenen Excel-Datei
	private void createWorkbook(File file) throws ClassNotFoundException, IOException, InvalidFormatException {
		String filename = file.getName().toLowerCase();
		try {
			if (filename.endsWith(".xls")) {
				readHSSFWorkbook(file);
				// workbook = WorkbookFactory.create(file);
			} else if (filename.endsWith(".xlsx")) {
				readXSSFWorkbook(file);
			} else {
				throw new IllegalArgumentException("Ausnahmefehler: Die übergebene Datei " + file.getName()
						+ " ist keine valide Microsoft Excel-Datei (*.xls bzw. *.xlsx)!");
			}
		} catch (IOException ioe) {
			throw new IOException("Ausnahmefehler: Die Datei " + file.getName() + " konnte nicht eingelesen werden!");
		} catch (EncryptedDocumentException ede) {
			throw new EncryptedDocumentException("Ausnahmefehler: Die Datei " + file.getName()
					+ " ist mit einem Passwort geschützt und kann daher nicht geöffnet werden!");
		} catch (NoClassDefFoundError err) {
			System.err.println(err.toString());
			throw new ClassNotFoundException(
					"Schwerer Ausnahmefehler: Klassendefinition nicht gefunden!\nPrüfen Sie die Programmdateien und externen Bibliotheken auf Vollständigkeit!");
		}

	}

	private void readHSSFWorkbook(File file) throws IOException {
		POIFSFileSystem fs = new POIFSFileSystem(file);
		workbook = new HSSFWorkbook(fs.getRoot(), true);
		fs.close();
	}

	private void readXSSFWorkbook(File file) throws InvalidFormatException, IOException {
		OPCPackage pkg = OPCPackage.open(file);
		workbook = new XSSFWorkbook(pkg);
		pkg.close();
	}

//	// Schreibt eine Arbeitsmappe in eine Exceldatei
//	private void createExcelFile(File file) throws FileNotFoundException, IOException {
//		try (FileOutputStream fileOut = new FileOutputStream(file)) {
//			workbook.write(fileOut);
//		} catch (FileNotFoundException fnfe) {
//			throw new IOException("Ausnahmefehler: Die Datei " + file.getName() + " konnte nicht erzeugt werden!");
//		} catch (IOException ioe) {
//			throw new IOException("Ausnahmefehler: Die Datei " + file.getName() + " konnte nicht erzeugt werden!");
//		}
//	}

	// Liest die Daten aus einer Zelle und gibt sie zurück.
	@SuppressWarnings("deprecation")
	private String getData(Cell cell) {

		if (cell != null) {
			System.out.println("--------------------------------------");
			System.out.println("Cell: " + cell.toString());
			System.out.println("CellAdress: " + cell.getAddress());
			String type = cell.getCellTypeEnum().toString();
			System.out.println("Type: " + type);

			switch (cell.getCellTypeEnum()) {
			case STRING:
				String string = cell.getRichStringCellValue().getString();
				if (string.isEmpty()) {
					return null;
				} else {
					isEmptyRow = false;
					return string.trim();
				}
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date.toString().isEmpty()) {
						return null;
					} else {
						isEmptyRow = false;
						return date.toString();
					}
				} else {
					Number number = cell.getNumericCellValue();
					if (number.toString().isEmpty()) {
						return null;
					} else {
						isEmptyRow = false;
						return number.toString();
					}
				}

			case FORMULA:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date.toString().isEmpty()) {
						return null;
					} else {
						isEmptyRow = false;
						return date.toString();
					}
				} else {
					Number number = cell.getNumericCellValue();
					if (number.toString().isEmpty()) {
						return null;
					} else {
						isEmptyRow = false;
						return number.toString();
					}
				}

			case BOOLEAN:
				Boolean bool = (cell.getBooleanCellValue());
				if (bool.toString().isEmpty()) {
					return null;
				} else {
					isEmptyRow = false;
					return bool.toString();
				}

			case _NONE:
			case BLANK:
				return "";
			case ERROR:
			default:
				return null;
			}
		} else {
			return "";
		}
	}

	/**
	 * Anwendung ArrayList<String> excelZeilen = new ArrayList<String>();
	 * excelZeilen.add("Überschrift1;Überschrift2;Überschrift3;Überschrift4");
	 * excelZeilen.add("a1;B1;c1;d1"); excelZeilen.add("a2;B2;c2;d2");
	 * ExcelFileReader.makeExcFile("testExcel.xls", "sheet2",excelZeilen);
	 * 
	 * @param fileName
	 * @param SheetName
	 * @param zeilen
	 */

	static void makeExcFile(String fileName, String SheetName, ArrayList<String> zeilen) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(SheetName);

		int rownum = 0;
		Cell cell;
		Row row;

		for (String zeile : zeilen) {
			row = sheet.createRow(rownum);
			rownum++;
			String[] daten = zeile.split(";");
			int spalte = 0;
			for (String dat : daten) {
				cell = row.createCell(spalte, CellType.STRING);
				cell.setCellValue(dat);
				spalte++;
			}

		}

		File file = new File(fileName);

		FileOutputStream outFile;
		try {
			outFile = new FileOutputStream(file);
			workbook.write(outFile);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}