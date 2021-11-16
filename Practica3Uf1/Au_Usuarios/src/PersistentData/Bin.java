/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistentData;

import Encryption.EncryptAndDecrypt;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import Exceptions.UserAlreadyExistException;
import Users.Usuario;
import Users.Usuarios;


/**
 * Class to control the file binary.
 * @author roger
 */
public class Bin {
    
    /**
     * Default constructor.
     */
    public Bin(){
    }
    
    /**
     * Method to add new user in file binary
     * @param u
     * @param token
     * @return true if no problems
     */
    public boolean add(Usuario u,String token) {
        try{
            ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream("archivo.bin"));
            EncryptAndDecrypt ead = new EncryptAndDecrypt();
            
            // encrypt password
            String password = u.getPassword();
            u.setPassword(ead.encrypt(password, token));
            oss.writeObject(u); 
            u.setPassword(password);
            return true;
        }catch (IOException e){
            return false;
        }catch (NullPointerException ex){
            System.out.println("Token invalido");
            return false;
        }catch (Exception ex){
            System.out.println("Error inesperado relacionado con la encriptacion del archivo.bin");
            return false;
        }
    }
    
    /**
     * Method to add in "Usuarios" class.
     * @param usuarios array users
     * @param token key for encrypt and decrypt
     */
    public void addList(Usuarios usuarios,String token) {

        try{

            ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream("archivo.bin"));
            Iterator<Usuario> iterator = usuarios.iterator();
            
            // while users have user
            while(iterator.hasNext()){
                // decrypt de password and write de user in "Usuarios"
                EncryptAndDecrypt ead = new EncryptAndDecrypt();
                Usuario u = iterator.next();
                String password = u.getPassword();
                u.setPassword(ead.encrypt(password, token));
                oss.writeObject(u);
                u.setPassword(password);
            }
            // no more users, close de stream
            oss.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }catch (NullPointerException ex){
            System.out.println("Token invalido");
        }catch (Exception ex){
            System.out.println("Error inesperado relacionado con archivo.bin");
        }
        
    }
    
    
    /**
     * Method to read a file binary and put in "Usuarios" class.
     * @param usuarios array users
     * @param token key for encrypt and decrypt
     */
    public void read(Usuarios usuarios,String token) {
        try {
            
            // try open the binary file
            ObjectInputStream ois;
            ois = new ObjectInputStream(new FileInputStream("archivo.bin"));
            
            //first user
            Usuario u = (Usuario) ois.readObject();
            //while u is not null continue
            while (u != null) {
                
                try{
                    // try encrypt and add new user
                    EncryptAndDecrypt ead = new EncryptAndDecrypt();

                    String password = u.getPassword();
                    u.setPassword(ead.decrypt(password, token));
                    
                    usuarios.add(u);

                    
                } catch (UserAlreadyExistException ex) {
                    // if user already exist print the message error
                    System.out.println(ex.getMessage());
                } catch (NullPointerException ex) {
                    // if the user is null, token fail
                    System.out.println("Token invalido");
                } catch (Exception ex) {
                    // else the no controller error
                    System.out.println("Error inesperado relacionado con archivo.bin");
                }
                
                // next user
                u = (Usuario) ois.readObject();
            }
            //no more users, close the stream
            ois.close();
        } catch (FileNotFoundException ex) {
            // file not found
            System.out.println("archivo binario no creado");
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
            
        }
    }
}
