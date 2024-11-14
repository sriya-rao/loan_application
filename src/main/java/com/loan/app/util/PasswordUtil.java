package com.loan.app.util;

public class PasswordUtil {
	
	
	
	private PasswordUtil() {
}

	public static String generatePwd() {
		String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		         + "123456789"
		         + "abcdefghijklmnopqrstuvxyz"; 
		
		  StringBuilder sb = new StringBuilder(6);
		  
		  for (int i = 0; i < 6; i++) { 
             int index = (int)(alphaNumericString.length() 
			      * Math.random()); 
			 
			   // add Character one by one in end of sb 
			   sb.append(alphaNumericString 
			      .charAt(index));
			  } 
			 
			  return sb.toString(); 
			 } 

	
}
