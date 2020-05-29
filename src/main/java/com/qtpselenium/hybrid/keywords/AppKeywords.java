package com.qtpselenium.hybrid.keywords;

import com.aventstack.extentreports.Status;

public class AppKeywords extends GenericKeywords{
	
	public void validateLogin(){
		test.log(Status.INFO, "Validating Login");
		String expectedResult = data.get(dataKey);
		String actualResult="";
		
		boolean result = isElementPresent("crmLink_xpath");
		if(result)
			actualResult = "LoginSuccess";
		else
			actualResult= "LoginFailure";
		
		
		if(!expectedResult.equals(actualResult))
			reportFailure("Got result as "+ actualResult);
	}
	
	public void defaultLogin(){
		String username=envProp.getProperty("adminusername");
		String password=envProp.getProperty("adminpassword");
		System.out.println("Default username "+username);
		System.out.println("Default username "+password);
	}

}
