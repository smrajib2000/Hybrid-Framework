package com.qtpselenium.hybrid.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.qtpselenium.hybrid.driver.DriverScript;
import com.qtpselenium.hybrid.reports.ExtentManager;
import com.qtpselenium.hybrid.util.DataUtil;
import com.qtpselenium.hybrid.util.Xls_Reader;

public class BaseTest {
	public Properties envProp; //represents prod properties 
	public Properties prop;  //env.propeties
	public Xls_Reader xls;
	public String testName;
	public DriverScript ds;
	public ExtentReports rep;
	public ExtentTest test; 

	@BeforeTest
	public void init(){
		//initialize testName 
		System.out.println("**TestCase Name**"+this.getClass().getSimpleName());
		testName=this.getClass().getSimpleName();
		//get the xlsx file name dynamic way
		String arr[]=this.getClass().getPackage().getName().split("\\.");
		String suiteName=arr[arr.length-1]; //test suiteName 
		//properties file
		prop = new Properties();
		envProp = new Properties();
		
		//initialize prop file
		try {
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//env.properties");
			prop.load(fs);
			String env = (prop.getProperty("env"));
			fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//"+env+".properties");
			envProp.load(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//init the xls file
		// how do i come to know the suite ?
		xls = new Xls_Reader(envProp.getProperty(suiteName+"_xlsx"));
		
		//init DS
		ds = new DriverScript();
		ds.setEnvProp(envProp);
		ds.setProp(prop);
		
	}
	
	
	@BeforeMethod
	public void initTest(){
		rep = ExtentManager.getInstance(prop.getProperty("reportPath"));
		test = rep.createTest(testName);
		ds.setExtentTest(test);
	}
	@AfterMethod
	public void quit(){
		//quit the driver
		/*if(ds!=null)
			ds.quit(); */
		if(rep!=null)
			rep.flush();
	}
	
	@DataProvider
	public Object[][] getData(){
		//Object[][] data = new Object[][];
		System.out.println("Inside data Provider");
		
		
		
		return DataUtil.getTestData(testName, xls);
	}

}
