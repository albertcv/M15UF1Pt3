/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Usuario con nombre, apellidos, email, password, fecha de creación y el rol
 * @author roger
 */
public class Usuario implements Serializable{
    
    
    private String nombre; //nombre del usuario
    private String apellidos; //apellido del usuario
    private String email; //e-mail de ingreso
    private String password; //contraseña de ingreso
    private LocalDateTime ldt; //fecha de creación del usuario
    private int rol; // 1-user, 2-admin
    
    public static final int USER = 1; //usuario con rol normal user
    public static final int ADMIN = 2; //usuario especial con rol admin

    /**
     * Metodo para recuperar la fecha de creación de un usuario.
     * @return LocalDateTime atributo ldt de la classe usuario
     */
    public LocalDateTime getLdt() {
        return ldt;
    }
    
    /**
     * Metodo para insertar la fecha y hora actual.
     */
    public void setLdt() {
        this.ldt = LocalDateTime.now();
    }
    
    public Usuario(){
       
    }
    
    /**
     * Crear un usuario con los valores nombres, apellidos, email, password y rol.
     * @param nombre nombre del usuario
     * @param apellidos los dos apellidos del usuario
     * @param email email unico que identifica al usuario
     * @param password password de inicio de session
     * @param rol si es administrador o usuario
     */
    public Usuario(String nombre,String apellidos, String email, String password, int rol){
        this.nombre = upperCaseFirst(nombre);
        this.apellidos = upperCaseFirst(apellidos);
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.setLdt();
    }
    
    public static String upperCaseFirst(String val) {
        String[] strs = val.split(" ");
        String newString = "";
        for (String str : strs){
            char[] arr = str.toCharArray();
            arr[0] = Character.toUpperCase(arr[0]);
            newString += new String(arr);
        }
        
        return newString;
   }
    
    /**
     * Metodo para devolver el nombre del usuario.
     * @return String con el nombre
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Metodo para añadir el nombre del usuario.
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Metodo para devolver los apellidos del usuario.
     * @return String con los apellidos
     */
    public String getApellidos() {
        return apellidos;
    }
    
    /**
     * Metodo para añadir los apellidos del usuario.
     * @param apellidos 
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Metodo para devolver el e-mail del usuario.
     * @return String con el e-mail del usuario.
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Metodo para añadir el e-mail del usuario.
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Metodo para devolver la contraseña del usuario.
     * @return String con la contraseña
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Metodo para añadir la contraseña del usuario.
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Metodo para devolver el rol del usuario.
     * @return String con "admin" o "user"
     */
    public String getRol() {
        return this.isAdmin() ? "admin" : "user";
    }
    
    /**
     * Metodo para añadir el rol del usuario
     * @param rol 1-user, 2-admin
     */
    public void setRol(int rol) {
        this.rol = rol;
    }
    
    /**
     * Metodo Override de hash Code segun el email
     * @return int
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.email);
        return hash;
    }
    
    /**
     * Metodo Override de equals segun el email
     * @param obj
     * @return boolean true si es igual
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
    
    /**
     * Metodo comprovar si el usuario es un usuario normal (user)
     * @return boolean si es un user
     */
    public boolean isUser(){
        return this.rol == Usuario.USER;
    }
    
    /**
     * Metodo comprovar si el usuario es un usuario especial (admin)
     * @return boolean si es un admin
     */
    public boolean isAdmin(){
        return this.rol == Usuario.ADMIN;
    }
    
    /**
     * Metodo que te permite buscar un atributo de usuario con una cadena de texto
     * que especifique el nombre del atributo
     * @param s
     * @return String con el valor del atributo
     */
    public String get(String s){
        switch(s){
            case "nombre":
                return this.getNombre();
            case "apellidos":
                return this.getApellidos();
            case "email":
                return this.getEmail();
            case "rol":
                return this.getRol();
            default:
                return "";
        }
    }
   
    
    /**
     * Metodo que devuelve todos los atributos de un usuario
     * @return String con todos los atributos de forma legible
     */
    public String getAll(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(this.getNombre()).append(" ").append(this.getApellidos());
        sb.append("\n");
        sb.append("E-mail: ").append(this.email);
        sb.append("\n");
        sb.append("Rol: ").append(this.getRol());
        sb.append("\n");
        sb.append("Date: ").append(this.getLdt());
        return sb.toString();
    }
    
    /**
     * Metodo override de toString
     * @return String
     */
    @Override
    public String toString() {
        return "{" + "nombre : " + nombre + ", apellidos : " + apellidos + ", email : " + email + ", password : " + password + ", ldt : " + ldt + ", rol : " + rol + '}';
    }
    
}
