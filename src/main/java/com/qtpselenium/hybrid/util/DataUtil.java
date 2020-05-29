package com.qtpselenium.hybrid.util;

import java.util.Hashtable;

public class DataUtil {
	
	public static Object[][] getTestData(String testName,Xls_Reader xls){
		//find the row number test cases
				int testStartRowNum = 1;
				while(!xls.getCellData(Constants.Data_SHEET, 0, testStartRowNum).equals(testName)){
					testStartRowNum++;
				}
				//find total cols in test cases
				int colStartRowNum = testStartRowNum+1;
				int totalCols=0;
				while(!xls.getCellData(Constants.Data_SHEET, totalCols, colStartRowNum).equals("")){
					totalCols++;
				}
				//find total rows in test cases
				int dataStartRowNumber = testStartRowNum+2;
				int totalRows=0;
				while(!xls.getCellData(Constants.Data_SHEET, 0, dataStartRowNumber).equals("")){
					totalRows++;
					dataStartRowNumber++;
				}
				//reset the data again
				dataStartRowNumber = testStartRowNum+2;
				//Read the data
				Hashtable<String,String> table = null;
				int finalRows=dataStartRowNumber+totalRows;
				Object[][] myData = new Object[totalRows][1];
				int i=0;
				for(int rNum=dataStartRowNumber;rNum<finalRows;rNum++){
					table = new Hashtable<String,String>();  //put data in hashtable
					for(int cNum=0;cNum<totalCols;cNum++){
						String data=xls.getCellData(Constants.Data_SHEET, cNum, rNum);
						String key = xls.getCellData(Constants.Data_SHEET, cNum, colStartRowNum);
						//System.out.println(key+"........."+data);
						table.put(key, data);	
					}
					System.out.println(table);
					myData[i][0] =table;
					i++;
					System.out.println(".............");
				}
				return myData;
	}

	// function to check the runmode of test
		// true - N
		// false - Y
		public static boolean isSkip(String testName, Xls_Reader xls){
			int rows = xls.getRowCount(Constants.TESTCASES_SHEET);
			for(int rNum=2;rNum<=rows;rNum++){
				String tcid = xls.getCellData(Constants.TESTCASES_SHEET, Constants.TCID_COL, rNum);
				if(tcid.equals(testName)){// test is found
					String runmode = xls.getCellData(Constants.TESTCASES_SHEET, Constants.RUNMODE_COL, rNum);
					if(runmode.equals(Constants.RUNMODE_NO))
						return true;
					else
						return false;
				}
			}
			
			return true;
			
		}
}
