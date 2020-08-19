package de.pfeiffy.excel;

import java.util.ArrayList;

public class TestExcel {

	 public static void main(String[] args) {
		// TODO Auto-generated method stub		
		ArrayList<String> excelZeilen = new ArrayList<String>();
		excelZeilen.add("a1;B1;c1;d1");
		excelZeilen.add("a2;B2;c2;d2");		
		
		ExcelFileReader.makeExcFile("testExcel.xls", "sheet2",excelZeilen);
	}

}
