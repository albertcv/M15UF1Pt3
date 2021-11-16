/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author albertcasany
 */
public class Menus {
    
    
    // ======================= MAIN =======================

    /**
     * String array for main options.
     */
    public static final String[] MENU = {
        "EXIT",
        "LOGIN"};
        
    // ======================= USERS =======================
    
    /**
     * Strings array for user options.
     */
    public static final String[] MENUUSER = {
        "EXIT",
        "INFO. USER",
        "LOGOUT",
        "MANAGE ARRAYS ADN/ARN"};
    
    /**
     *  Strings array for DNA tools options.
     */
    public static final String[] MENUADN = {
        "Exit",
        "Donar la volta a una cadena de ADN ",
        "Trobar la base més repetida",
        "Trobar la base menys repetida",
        "Fer recompte de bases i mostrarles.",
        "Convertir ADN a ARN.",
        "Convertir ARN a ADN"};
    
    
    // ======================= ADMINS =======================
    
    /**
     * Strings array for admin options.
     */
    public static final String[] MENUADMIN = {
        "EXIT",
        "INFO. USER",
        "C.R.U.D (Create, Read, Update and Delete)",
        "JSON EXPORT",
        "JSON IMPORT",
        "LOGOUT"};
    
    /**
     * Strings array for admin CRUD options.
     */
    public static final String[] CRUD = {
        "EXIT",
        "CREATE",
        "READ",
        "UPDATE",
        "DELETE"};
    
    /**
     * Strings array for admin read options.
     */
    public static final String[] READ = {
        "EXIT",
        "FULL",
        "SEARCH BY E-MAIL",
        "SEARCH BY NAME",
        "SEARCH BY NAME AND LASTNAME"};
    
    /**
     * Strings array for attribute options.
     */
    public static final String[] ATTRIBUTES = {
        "EXIT",
        "NAME",
        "LASTNAME",
        "PASSWORD",
        "ROL"};
}
