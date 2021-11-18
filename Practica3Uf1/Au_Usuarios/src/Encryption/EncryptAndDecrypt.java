/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author roger
 */
public class EncryptAndDecrypt {

    public String getKey() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 100;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    /**
     * generate the key for encrypt en decrypt
     *
     * @param key
     * @return sha-1 key
     */
    private SecretKeySpec createKey(String key) {
        try {
            byte[] listByte = key.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            listByte = md.digest(listByte);
            listByte = Arrays.copyOf(listByte, 16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(listByte, "AES");
            return secretKeySpec;
        } catch (UnsupportedEncodingException ex) {
            return null;
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    /**
     * Method to encrypt using the AES algorithm.
     *
     * @param content // content that will encrypting
     * @param key // key for encrypt and decrypt
     * @return String encryption
     * @throws java.security.NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws java.io.UnsupportedEncodingException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     */
    public String encrypt(String content, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {

        SecretKeySpec sks = createKey(key);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sks);

        byte[] bytes_content = content.getBytes("UTF-8");
        byte[] bytes_encryption = cipher.doFinal(bytes_content);

        String encryption = Base64.getEncoder().encodeToString(bytes_encryption);
        return encryption;
    }
    
    /**
     * Method to decrypt using the AES algorithm
     * @param encrypt
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     */
    public String decrypt(String encrypt, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        SecretKeySpec sks = createKey(key);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sks);
        byte[] bytes_encrypt = Base64.getDecoder().decode(encrypt);
        byte[] bytes_decryption = cipher.doFinal(bytes_encrypt);
        String decryption = new String(bytes_decryption);
        return decryption;

    }
}
