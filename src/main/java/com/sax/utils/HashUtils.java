package com.sax.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

        public static String hashPassword(String password) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] bytes = md.digest(password.getBytes());
                StringBuilder sb = new StringBuilder();
                for (byte aByte : bytes) {
                    sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
                }

                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("MD5 algorithm not found", e);
            }
        }

        public static boolean checkPassword(String enteredPassword, String storedHash) {
            String hashedEnteredPassword = hashPassword(enteredPassword);
            return hashedEnteredPassword.equals(storedHash);
        }
    public static String decryptMD5(String md5Hash) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = new BigInteger(1, md.digest(md5Hash.getBytes())).toByteArray();
        StringBuilder decryptedString = new StringBuilder();

        for (byte b : hashBytes) {
            decryptedString.append(String.format("%02x", b));
        }

        return decryptedString.toString();
    }
}
