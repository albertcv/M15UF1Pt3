/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistentData;

import Encryption.EncryptAndDecrypt;
import Exceptions.UserAlreadyExistException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.Scanner;
import Users.Usuario;
import Users.Usuarios;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Class to control de json file.
 * @author roger
 */
public class Json {
    
    // ckeck if is json encrypt
    private static boolean isJsonEncrypt = false;

    /**
     * Method to import json.
     * @param jsonFile
     * @return 
     */
    public Usuarios jsonImport(String jsonFile) {

        JSONParser parser = new JSONParser();
        
        // try to read a json file 
        try ( Reader reader = new FileReader(jsonFile)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            
            // open a array object with key usuarios

                JSONArray listaUsuarios = (JSONArray) jsonObject.get("usuarios");
                Iterator<JSONObject> iterator = listaUsuarios.iterator();
                Usuarios usuarios = new Usuarios();
 
            // is json encrypt
            Scanner sc = new Scanner(System.in);
            System.out.println("¿Las contraseñas estan encriptada?(Si/No)");
            char s = sc.next().charAt(0);
            String key = "";
            if (s == 'S' || s == 's') {
                // json is encrypt
                System.out.println("Escribe el token: ");
                key = sc.next();
                Json.isJsonEncrypt = true;
            } else {
                // json is not encrypt
                Json.isJsonEncrypt = false;
            }

            // while json has a user
            int index = 0;
            while (iterator.hasNext()) {
                index++;
                final JSONObject nextUser = iterator.next();
                Usuario usuario = null;
                
                // json is encrypt
                if (Json.isJsonEncrypt) {
                    // create user with password encrypt
                    EncryptAndDecrypt ead = new EncryptAndDecrypt();
                    String password = ead.decrypt(nextUser.get("password").toString(), key);
                    usuario = new Usuario(
                            nextUser.get("nombre").toString(),
                            nextUser.get("apellidos").toString(),
                            nextUser.get("email").toString(),
                            password,
                            rol(nextUser.get("rol").toString()));
                } else {
                    // create user with normal password
                    usuario = new Usuario(
                            nextUser.get("nombre").toString(),
                            nextUser.get("apellidos").toString(),
                            nextUser.get("email").toString(),
                            nextUser.get("password").toString(),
                            rol(nextUser.get("rol").toString()));
                }
                try {
                    // add user in "usuarios"
                    usuarios.add(usuario);
                } catch (UserAlreadyExistException ex) {
                    System.out.println("Usuario ya existente");
                }

            }
            
            return usuarios;
        } catch (FileNotFoundException ex) {
            System.out.println("Json no encontrado");
        } catch (IOException ex) {
            System.out.println("Error de entrada y salida de datos");
        } catch (ParseException ex) {
            System.out.println("Error al crear el objeto json");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println("Error de encriptación");
        } catch (NullPointerException ex){
            System.out.println("Formato de json esperado:");
            System.out.println("{");
            System.out.println("    Usuarios : [{");
            System.out.println("        \"nombre\":xxxxx,");
            System.out.println("        \"apellidos\":xxxxx,");
            System.out.println("        \"password\":xxxxx,");
            System.out.println("        \"rol\":xxxxx");
            System.out.println("        },{");
            System.out.println("        \"nombre\":xxxxx,");
            System.out.println("        \"apellidos\":xxxxx,");
            System.out.println("        \"password\":xxxxx,");
            System.out.println("        \"rol\":xxxxx}]");
            System.out.println("}");
        }
        
        return null;

    }

    /**
     * Method to export json file.
     * @param jsonFile
     * @param usuarios 
     */
    public void jsonExport(String jsonFile, Usuarios usuarios) {
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        Iterator<Usuario> i = usuarios.iterator();
        
        // create a token for encrypt and decrypt file
        EncryptAndDecrypt ead = new EncryptAndDecrypt();
        String token = ead.getKey();
        System.out.println("token-> " + token);
        
        // while exist a user
        while (i.hasNext()) {
            try {
                Usuario u = i.next();
                
                // encrypt password
                String encryptPassword = ead.encrypt(u.getPassword(), token);
                JSONObject objUsuario = new JSONObject();
                
                // add info in new json object
                objUsuario.put("nombre", u.getNombre());
                objUsuario.put("apellidos", u.getApellidos());
                objUsuario.put("email", u.getEmail());
                objUsuario.put("password", encryptPassword);
                objUsuario.put("rol", u.getRol());
                
                // add new json object in json array.
                list.add(objUsuario);
            } catch (NullPointerException ex) {
                System.out.println("Token invalido");
            } catch (Exception ex) {
                System.out.println("Error inesperado relacionado con" + jsonFile + ".json");
            }
        }
        // add json array in json object "usuarios"
        obj.put("usuarios", list);

        // write the json object in json file
        try ( FileWriter file = new FileWriter(jsonFile)) {
            file.write(obj.toJSONString());
        } catch (IOException ex) {
        }

    }

    // Method to convert a string role to int role
    private int rol(String s) {
        return s.equalsIgnoreCase("admin") ? 2 : 1;
    }
}
