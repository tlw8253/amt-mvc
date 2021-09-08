package com.amt.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amt.app.Constants;

//
//### https://www.appsdeveloperblog.com/encrypt-user-password-example-java/
public final class PasswordUtil {
	private static Logger objLogger = LoggerFactory.getLogger(PasswordUtil.class);

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = Constants.csAllPasswordCharacters;  //"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

 	
    public static String getSalt(int length) {
    	String sMethod = "getSalt(): ";
    	objLogger.trace(sMethod + "Entered: length: [" + length + "]");
    	
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        objLogger.trace(sMethod + "Exit: returnValue: [" + returnValue + "]");
        return new String(returnValue);
    }

    public static byte[] hash(char[] password, byte[] salt) {
    	String sMethod = "hash(): ";
    	objLogger.trace(sMethod + "Entered: password: [" + String.copyValueOf(password) + "] salt: [" + salt + "]");

    	PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            
            byte[] returnValue = skf.generateSecret(spec).getEncoded();
            objLogger.trace(sMethod + "Exit: returnValue: [" + returnValue + "]");
            
            return returnValue; //skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        	objLogger.trace(sMethod + "Exit: NoSuchAlgorithmException | InvalidKeySpecException: [" + e.getMessage() + "]");
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    
    public static String generateSecurePassword(String password, String salt) {
    	String sMethod = "generateSecurePassword(): ";
    	objLogger.trace(sMethod + "Entered: password: [" + password + "] salt: [" + salt + "]");

    	String returnValue = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
 
        returnValue = Base64.getEncoder().encodeToString(securePassword);
 
        objLogger.trace(sMethod + "Exit: returnValue: [" + returnValue + "]");
        return returnValue;
    }

    public static boolean verifyUserPassword(String providedPassword,
            String securedPassword, String salt)
    {
       	String sMethod = "verifyUserPassword(): ";
    	objLogger.trace(sMethod + "Entered: securedPassword: [" + securedPassword + "] salt: [" + salt + "]");

    	boolean returnValue = false;
        
        // Generate New secure password with the same salt
        String newSecurePassword = generateSecurePassword(providedPassword, salt);
        
        // Check if two passwords are equal
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);
        
        objLogger.trace(sMethod + "Exit: returnValue: [" + returnValue + "]");
        return returnValue;
    }
	
}














