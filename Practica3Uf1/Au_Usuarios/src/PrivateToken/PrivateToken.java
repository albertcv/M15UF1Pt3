/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrivateToken;

import Encryption.EncryptAndDecrypt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roger
 */
public class PrivateToken {

    private String FILE_NAME = "key.txt";
    
    /**
     * Method to return a string key
     * @return 
     */
    public String getToken() {
        
        // try to open a file
        File file = new File(FILE_NAME);

        try {
            // if file exist read a key
            FileInputStream fis = new FileInputStream(file);
            int valor = fis.read();
            String key = "";
            while (valor != -1){
                key += (char)valor;
                valor = fis.read();
            }
            return key;
            
            
        } catch (FileNotFoundException ex) { // if file not exist
            try {
                // crete new file and write the new key
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                EncryptAndDecrypt ead = new EncryptAndDecrypt();
                String key = ead.getKey();
                fos.write(key.getBytes("UTF-8"));
                file.setReadOnly();
                return key;
            } catch (IOException ex1) {
                return null;
            }
        } catch (IOException ex) {
            return null;
        }

    }
}
