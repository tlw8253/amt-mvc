package com.amt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amt.app.Constants;

public final class Validate {
	private static Logger objLogger = LoggerFactory.getLogger(Validate.class);
	private static final String[] csStateCodes = {"AL","AK","AS","AZ","AR","CA","CO","CT","DE","DC","FL","GA","GU","HI","ID","IL","IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","MP","OH","OK","OR","PA","PR","RI","SC","SD","TN","TX","UT","VT","VA","VI","WA","WV","WI","WY"}; 

	private Validate() {
		super();
	}

	//
	// ### Utility method to check if string is an primitive int
	public static boolean isInt(String sValue) {
		String sMethod = "isInt";
		boolean bIsValid = false;
		try {
			objLogger.debug(sMethod + "Checking if string value of: [" + sValue + "] is an integer.");
			Integer.parseInt(sValue);
			bIsValid = true;

		} catch (NumberFormatException objE) {
			objLogger.debug(sMethod + "String value of: [" + sValue + "] is NOT an integer.");
		}
		return bIsValid;
	}

	//
	// ### Utility method to check if string is an primitive int
	public static boolean isDouble(String sValue) {
		String sMethod = "isDouble";
		boolean bIsValid = false;
		try {
			objLogger.debug(sMethod + "Checking if string value of: [" + sValue + "] is a double.");
			Double.parseDouble(sValue);
			bIsValid = true;

		} catch (NumberFormatException objE) {
			objLogger.debug(sMethod + "String value of: [" + sValue + "] is NOT a double.");
		}
		return bIsValid;
	}

	public static boolean isAlphaNumeric(String sValue) {
		String sMethod = "isAlphaNumeric(): ";
		boolean bIsValid = false;

		bIsValid = sValue.matches("[A-Za-z0-9]+");
		objLogger.debug(sMethod + "String value of: [" + sValue + "] is AlphaNumeric: [" + bIsValid + "]");

		return bIsValid;
	}

	public static boolean isNumeric(String sValue) {
		String sMethod = "isNumeric(): ";
		boolean bIsValid = false;

		bIsValid = sValue.matches("[0-9]+");
		objLogger.debug(sMethod + "String value of: [" + sValue + "] is Numeric: [" + bIsValid + "]");

		return bIsValid;
	}

	
	public static boolean isAlpha(String sValue) {
		String sMethod = "isAlpha(): ";
		boolean bIsValid = false;

		bIsValid = sValue.matches("[A-Za-z]+");
		objLogger.debug(sMethod + "String value of: [" + sValue + "] is isAlpha: [" + bIsValid + "]");

		return bIsValid;
	}

	public static boolean isAlphaPlusLastname(String sValue) {
		String sMethod = "isAlpha(): ";
		boolean bIsValid = false;

		bIsValid = sValue.matches("[A-Za-z -]+");
		objLogger.debug(sMethod + "String value of: [" + sValue + "] is Alpha Plus: [" + bIsValid + "]");

		return bIsValid;
	}

	//
	// ### inspiration for validation from:
	// https://www.geeksforgeeks.org/java-program-to-check-the-validity-of-a-password-using-user-defined-exception/
	public static boolean isPasswordFormat(String sValue, int iMinPwdLen, int iMaxPwdLen) {
		String sMethod = "isPasswordFormat(): ";
		boolean bIsValid = false;

		
		// for checking if password length
		// is between 8 and 15
		if (!((sValue.length() >= iMinPwdLen) && (sValue.length() <= iMaxPwdLen))) {
			objLogger.debug(sMethod + "password length error: [" + sValue.length() + "] min len: [" + iMinPwdLen + "] max len: [" + iMaxPwdLen + "]");
			return bIsValid;
		}

		//check for any invalid characters next before continuing with validation
		if (!hasAllowedPasswordChars(sValue)) {
			objLogger.debug(sMethod + "password contains invalid characters: [" + sValue + "]");
			return bIsValid;			
		}

		
		String sStringAt0 = sValue.substring(0, 1);
		if (!isAlpha(sStringAt0)) {
			objLogger.debug(sMethod + "password error does not start with an alpha.");
			return bIsValid;
		}

		// to check space
		if (sValue.contains(" ")) {
			objLogger.debug(sMethod + "password eror contains a space.");
			return bIsValid;
		}

		// check for at least one numeric value
		int iCount = 0;
		for (int iCtr = 0; iCtr < sValue.length(); iCtr++) {
			if ((sValue.charAt(iCtr) >= 48) && (sValue.charAt(iCtr) >= 57)) {
				iCount++;
			}
		}

		if (iCount == 0) {
			objLogger.debug(sMethod + "password eror contains no numeric.");
			return bIsValid;
		}

		// check for at least one uppercase alpha
		iCount = 0;
		for (int iCtr = 0; iCtr < sValue.length(); iCtr++) {
			if ((sValue.charAt(iCtr) >= 65) && (sValue.charAt(iCtr) <= 90)) {
				iCount++;
			}
		}

		if (iCount == 0) {
			objLogger.debug(sMethod + "password eror contains no capital letter.");
			return bIsValid;
		}

		// check for at least one lowercase alpha
		iCount = 0;
		for (int iCtr = 0; iCtr < sValue.length(); iCtr++) {
			if ((sValue.charAt(iCtr) >= 97) && (sValue.charAt(iCtr) <= 122)) {
				iCount++;
			}
		}

		if (iCount == 0) {
			objLogger.debug(sMethod + "password eror contains no lower case letter.");
			return bIsValid;
		}

		// check for program defined password special character
		if (!hasPasswordSpecialChar(sValue)) {
			objLogger.debug(sMethod + "password eror contains no passowrd special characters.");
			return bIsValid;
		}

		bIsValid = true;
		return bIsValid;
	}

	
	public static boolean hasAllowedPasswordChars(String sValue) {
		String sMethod = "hasAllowedPasswordChars";
		boolean bIsValid = true;
		
		char[] carPwdChars = sValue.toCharArray();
		String sSpecialChar = Constants.csPasswordAllowedSpecialChars; 
		objLogger.debug(sMethod + "sSpecialChar: [" + sSpecialChar + "]");
		
		String sThisChar = "";
		for (int iCtr=0; iCtr<carPwdChars.length; iCtr++) {
			sThisChar = Character.toString(carPwdChars[iCtr]);
			bIsValid = sSpecialChar.contains(sThisChar);
			if (!bIsValid) {
				bIsValid = Constants.csAlphabet.contains(sThisChar);
			}
			if (!bIsValid) {
				bIsValid = Constants.csNumeric.contains(sThisChar);
			}
			if (!bIsValid) {
				objLogger.debug(sMethod + "password has character not allowed at [" + iCtr + "] value: [" + sThisChar + "]");
				break;
			}
		}
		
		
		return bIsValid;
	}
	
	
	//
	//###
	public static boolean hasPasswordSpecialChar(String sValue) {
		String sMethod = "hasPasswordSpecialChar(): ";
		boolean bIsValid = false;

		char[] carPwdChars = sValue.toCharArray();
		String sSpecialChar = Constants.csPasswordAllowedSpecialChars; 

		objLogger.debug(sMethod + "sSpecialChar: [" + sSpecialChar + "]");
		
		String sThisChar = "";
		for (int iCtr=0; iCtr<carPwdChars.length; iCtr++) {
			sThisChar = Character.toString(carPwdChars[iCtr]);
			bIsValid = sSpecialChar.contains(sThisChar);
			if(bIsValid) {
				objLogger.debug(sMethod + "found special char at: [" + iCtr + "] char: [" + sThisChar + "]");
				break;
			}
		}
		

		return bIsValid;
	}

	public boolean hasSpecialChar(String sValue) {
		String sMethod = "hasSpecialChar(): ";
		boolean bIsValid = true;

		// for special characters
		if (!(sValue.contains("`") || sValue.contains("~") || sValue.contains("@") || sValue.contains("#")
				|| sValue.contains("$") || sValue.contains("%") || sValue.contains("^") || sValue.contains("&")
				|| sValue.contains("*") || sValue.contains("(") || sValue.contains(")") || sValue.contains("-")
				|| sValue.contains("_") || sValue.contains("=") || sValue.contains("+") || sValue.contains("[")
				|| sValue.contains("{") || sValue.contains("]") || sValue.contains("}") || sValue.contains("\\")
				|| sValue.contains("|") || sValue.contains(";") || sValue.contains(":") || sValue.contains("'")
				|| sValue.contains("\"") || sValue.contains(";") || sValue.contains(",") || sValue.contains("<")
				|| sValue.contains(".") || sValue.contains(">") || sValue.contains("/") || sValue.contains("?"))) {
			objLogger.debug(sMethod + "String contains no special characters.");
			bIsValid = false;
		}

		return bIsValid;
	}

	//
	//### A very basic email validation
	public static boolean isValidEmailAddress(String sValue) {
		String sMethod = "isValidEmailAddress(): ";
		boolean bIsValid = true;

		bIsValid = sValue.matches("^(.+)@(.+)$");
		objLogger.debug(sMethod + "String value of: [" + sValue + "] is email format: [" + bIsValid + "]");

		return bIsValid;
	}
	
	public static boolean isValidValueInArray(String sValue, String[] arrValues) {
		boolean bValid = false;
		
		for (int iCtr=0; iCtr<arrValues.length; iCtr++) {
			if(sValue.equalsIgnoreCase(arrValues[iCtr])) {
				bValid = true;
				break;
			}
		}

		return bValid;
	}
	
	public static boolean isValidStateCode(String sStateCode) {
		boolean bValid = false;
		
		if (sStateCode.length() == 2) {
			bValid = isValidValueInArray(sStateCode,csStateCodes);
		}
		return bValid;
	}

}
