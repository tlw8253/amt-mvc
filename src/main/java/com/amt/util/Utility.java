package com.amt.util;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Utility {
	private static Logger objLogger = LoggerFactory.getLogger(Utility.class);

	private Utility() {
		super();
	}
	
	public static int getRandomIntBetween(int iMin, int iMax) {
		return (int) ((Math.random() * (iMax - iMin)) + iMin);
	}

	public static String padIntLeadingZero(int iInt, int iStringLen) {
		String sRetString = Integer.toString(iInt);
		int iPadLen = iStringLen - sRetString.length();
		
		
		for(int iCtr=0; iCtr<iPadLen; iCtr++) {
			sRetString = "0" + sRetString;
		}		
		return sRetString;
	}
	
	//Should move to utility class
	//###
	public static TreeSet<String> getHashMapSortedKeys(HashMap<String, String> hmStringHashMap){
		String sMethod = "\n\t getHashMapSortedKeys(): ";		
		objLogger.trace(sMethod + "Entered");

		Set<String> setKeys = hmStringHashMap.keySet();
		TreeSet<String> treeSetKeys = new TreeSet<String>(setKeys);
		
		objLogger.debug(sMethod + "treeSetKeys: [" + treeSetKeys.toString() + "]");
		
		return treeSetKeys;
	}
	
	public static String hashMapToStringBySortedKeys(TreeSet<String> tsSortedNames, HashMap<String, String> hmStringDataElements) {
		String sMethod = "\n\t hashMapToStringBySortedKeys(TreeSet<String>, HashMap<String, String>): ";		
		String sToString = "";
		objLogger.trace(sMethod + "Entered");

		for(String sName : tsSortedNames) {
			objLogger.debug(sMethod + "getting element sName: [" + sName + "] from hashmap.");
			sToString += " [" + sName + "]: [" + hmStringDataElements.get(sName) + "]";
		}
		
		objLogger.debug(sMethod + "[" + sToString + "]");
		return sToString;		
	}

	public static String hashMapToStringBySortedKeys(HashMap<String, String> hmStringDataElements) {
		String sMethod = "\n\t hashMapToStringBySortedKeys(HashMap<String, String>): ";		
		String sToString = "";

		TreeSet<String> tsSortedNames = Utility.getHashMapSortedKeys(hmStringDataElements);
		for(String sName : tsSortedNames) {
			objLogger.debug(sMethod + "getting element sName: [" + sName + "] from hashmap.");
			sToString += " [" + sName + "]: [" + hmStringDataElements.get(sName) + "]";
		}
		
		objLogger.debug(sMethod + "[" + sToString + "]");
		return sToString;		
	}
	
	public static String hashMapToStringByByKeyOrder(HashMap<String, String> hmStringDataElements, String... sKey) {
		String sMethod = "\n\t hashMapToStringByByKeyOrder(): ";
		String sToString = "";
		objLogger.trace(sMethod + "Entered");
		
		for (int iCtr=0; iCtr<sKey.length; iCtr++) {
			String sThisKey = sKey[iCtr];
			objLogger.debug(sMethod + "sKey[" + sKey + "]: [" + sKey[iCtr] + "]" );
			
			sToString += "[" + sThisKey + "]: [" + hmStringDataElements.get(sThisKey) + "] ";			
		}
		objLogger.debug(sMethod + "[" + sToString + "]");		
		
		return sToString;
		
	}

}






