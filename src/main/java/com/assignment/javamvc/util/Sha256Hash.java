package com.assignment.javamvc.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;
@Component
public class Sha256Hash {
	 public String hashString(String input) {
	        try {
	            // Get an instance of the SHA-256 algorithm
	            MessageDigest digest = MessageDigest.getInstance("SHA-256");

	            // Convert the input string to bytes
	            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

	            // Convert the byte array to a hexadecimal string representation
	            StringBuilder hexString = new StringBuilder();
	            for (byte b : hashBytes) {
	                String hex = Integer.toHexString(0xff & b);
	                if (hex.length() == 1) hexString.append('0');
	                hexString.append(hex);
	            }

	            return hexString.toString();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

}
