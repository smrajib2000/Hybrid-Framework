package com.qtpselenium.hybrid.driver;



import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;

import com.aventstack.extentreports.ExtentTest;
import com.qtpselenium.hybrid.keywords.AppKeywords;
import com.qtpselenium.hybrid.util.Constants;
import com.qtpselenium.hybrid.util.Xls_Reader;

public class DriverScript {
	public Properties envProp; //represents prod properties 
	public Properties prop;  //env.propeties
	public ExtentTest test;
	AppKeywords app;
	
	public void executeKeywords(String testName,Xls_Reader xls,Hashtable<String, String> testData) throws Exception{
		int rows = xls.getRowCount(Constants.KEYWORDS_SHEET);
		System.out.println("Rows "+rows);
		app = new AppKeywords();
		//send prop to keywords class
		app.setEnvProp(envProp);
		app.setProp(prop);
		//send the data
		app.setData(testData);
		app.setExtentTest(test);
		
		for(int rNum=2;rNum<=rows;rNum++){
			String tcid = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.TCID_COL, rNum);
			if(tcid.equals(testName)){
				String keyword = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.KEYWORD_COL, rNum);
				String objectKey=xls.getCellData(Constants.KEYWORDS_SHEET, Constants.OBJECT_COL, rNum);
				String dataKey = xls.getCellData(Constants.KEYWORDS_SHEET, Constants.DATA_COL, rNum);
				String proceedOnFail=xls.getCellData(Constants.KEYWORDS_SHEET, Constants.PROCEED_COL, rNum);
				String data = testData.get(dataKey);
				//System.out.println(tcid+"...."+keyword+"...."+prop.getProperty(objectKey)+"....."+data);
				app.setDataKey(dataKey);
				app.setObjectKey(objectKey);
				app.setProceedOnFail(proceedOnFail);
				
			/*	if(keyword.equals("openBrowser"))
					app.openBrowser();
				else if(keyword.equals("navigate"))
					app.navigate();
				else if(keyword.equals("click"))
					app.click();
				else if(keyword.equals("type"))
					app.type();
				else if(keyword.equals("validateLogin"))
					app.validateLogin();
					*/
				//reflection api starts
				Method method;
				method = app.getClass().getMethod(keyword);
				method.invoke(app);
				
							
			}
		}
		app.assertAll();
	}


	public Properties getEnvProp() {
		return envProp;
	}


	public void setEnvProp(Properties envProp) {
		this.envProp = envProp;
	}


	public Properties getProp() {
		return prop;
	}


	public void setProp(Properties prop) {
		this.prop = prop;
	}


	public void setExtentTest(ExtentTest test) {
		this.test = test;
	}
	public void quit(){
		if(app!=null)
		app.quit();
	}

}
