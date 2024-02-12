package com.spotifyinfo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class PasswordEncryptorUtil {
    static Logger logger = LoggerFactory.getLogger(PasswordEncryptorUtil.class);

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "x7JVqx4COBT84KmD0RauptrD9U5RFYbv"; // You should change this to your secret key
    private static final String CHARSET_NAME = "UTF-8";

    public static String encrypt(String password) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(CHARSET_NAME), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes(CHARSET_NAME));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Error encrypting password");
        }
    }

    public static String decrypt(String encryptedPassword) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(CHARSET_NAME), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decryptedBytes, CHARSET_NAME);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting password");
        }
    }
}
