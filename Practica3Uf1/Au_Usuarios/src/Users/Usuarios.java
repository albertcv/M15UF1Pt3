/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

import java.util.ArrayList;
import java.util.Iterator;
import Exceptions.UserAlreadyExistException;


/**
 * Classe que gestiona a los usuarios.
 * @author roger
 */

public class Usuarios implements Iterable<Usuario>{
    private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

//    public ArrayList<Usuario> getUsuarios() {
//        return usuarios;
//    }
    
    /**
     * Constructor por defecto sin parametros.
     */
    public Usuarios(){
    }
    
    /**
     * Añadir usuario al ArrayList
     * @param u objeto usuario
     * @return boolean
     */
    public void add(Usuario u) throws UserAlreadyExistException{
        if (usuarios.contains(u)){
            throw new UserAlreadyExistException("Usuario ya existe");    
        }
        usuarios.add(u);
    }
    
    /**
     * Metodo quitar usuario del array
     * @param u objeto usuario
     * @return boolean
     */
    public boolean remove(Usuario u){
        if (usuarios.contains(u)){
            usuarios.remove(u);
            return true; 
        }
        return false;
    }
    
    /**
     * Metodo cargar unos datos por defecto
     * @return boolean  
     */
    public boolean loadData(){
        Usuario[] usuarios = {
            new Usuario("Roger","Puga Ruiz", "roger@gmail.com", "1234",Usuario.ADMIN),
            new Usuario("David", "Garcia", "david@gmail.com","1234",Usuario.USER),
            new Usuario("Alberd", "Casany", "alberd@gmail.com", "1234", Usuario.USER),
            new Usuario("Roger", "Ruiz Puga", "rogerruiz@gmail.com", "1234", Usuario.USER),
            new Usuario("David", "Martinez", "davidm@gmail.com", "1234", Usuario.USER),
            new Usuario("Alberd", "Casas", "alberdcasas@gmail.com", "1234", Usuario.USER),
        };
        
        // comprovar que el usuario no es encuentre ya incluido
        for (Usuario usuario : usuarios) {
            if (this.usuarios.contains(usuario)){
                return false;
            }
            this.usuarios.add(usuario);
        }
        return true;

    }
    
    /**
     * Metodo que te devuelve el ultimo elemento introducido en el ArrayList
     * @return Un Objeto usuario
     */
    public Usuario lastUser(){
        return this.usuarios.get(this.usuarios.size()-1);
    }
    
    /**
     * Metodo para buscar usuario por su e-mail
     * @param email e-mail del usuario
     * @return Un usuario que tiene el e-mail especifico
     */
    public Usuario search(String email){
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)){
                System.out.println("Usuario " + usuario.getEmail() + " encontrado");
                return usuario;
            }
        }
        return new Usuario();
    }
    
    /**
     * Metodo para buscar una collección de usuarios que contengan el parametro nombre
     * @param nombre
     * @return Un Objecto usuarios donde se guardan usuarios.
     */
    public Usuarios searchAll(String nombre) throws UserAlreadyExistException{
        Usuarios u = new Usuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().toLowerCase().contains(nombre.toLowerCase())){
                u.add(usuario);
            }
        }
        return u;
    }
    /**
     * Metodo para buscar una collección de usuarios que contengan el parametro nombre y apellidos
     * @param nombre
     * @param apellidos
     * @return Un Objeto usuarios donde se guardan usuarios.
     */
    public Usuarios searchAll(String nombre, String apellidos) throws UserAlreadyExistException{
        Usuarios u = new Usuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().toLowerCase().contains(nombre.toLowerCase()) || usuario.getApellidos().toLowerCase().contains(apellidos.toLowerCase())){
                u.add(usuario);
            }
        }
        
        return u;
    }
    
    /**
     * Metodo para visualizar por consola toda la información de los usuarios
     * @return Una cadena de texto con el nombre, apellido, e-mail, password, el rol y la fecha de creación.
     */
    public String getAll(){
        StringBuilder sb = new StringBuilder();
        for (Usuario usuario : usuarios) {
            sb.append(usuario.getAll());
            sb.append("\n");
            sb.append("-------------------------------------------");
            sb.append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Comprovar que un e-mail y una contraseña coinciden con los usuarios actuales
     * @param email
     * @param password
     * @return El usuario que coincide con el e-mail y el password.
     */
    public Usuario login(String email, String password){
        for (Usuario usuario : usuarios) {
            if (password_validator(usuario, email, password)){
//                usuario.setLdt();
                return usuario;
            }
        }
        return null;
    }
    
    /**
     * Comprovar si el usuario tiene el rol de user
     * @param u
     * @return Boolean con true en caso de que el rol sea user.
     */
    public boolean isUser(Usuario u){
        return u.isUser();
    }
    
    /**
     * Comprovar si el usuario tiene el rol de admin
     * @param u
     * @return Boolean con true en caso de que el rol sea user.
     */
    public boolean isAdmin(Usuario u){
        return u.isAdmin();
    }
    
    /**
     * añadir multiples usuarios a un array de usuarios
     * @param u 
     */
    public void addAll(Usuarios u){     
        
        Iterator<Usuario> iterator = u.iterator();
        while(iterator.hasNext()){
            final Usuario usuario = iterator.next();
            
            try{
                this.add(usuario);
                System.out.println(usuario.getAll());
                System.out.println("======================");
            }catch(UserAlreadyExistException ex){
                System.out.println(usuario.getEmail() + ": " +ex.getMessage());
                System.out.println("======================");
            }
//            System.out.println(usuario.getAll());
        }
    }
    
    public int count(){
        return usuarios.size();
    }
    /**
     * Metodo para iterar los elementos de usuarios.
     * @Override
     * @return 
     */
    @Override
    public Iterator<Usuario> iterator(){
        return usuarios.iterator();
    }
    
    private static boolean password_validator(Usuario usuario, String email, String password) {
        return usuario.getEmail().equals(email) && usuario.getPassword().equals(password);
    }
    
    
    
}
