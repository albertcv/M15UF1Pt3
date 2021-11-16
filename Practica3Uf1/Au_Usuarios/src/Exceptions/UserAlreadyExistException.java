/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package Exceptions;

/**
 *
 * @author roger
 */
public class UserAlreadyExistException extends Exception{

    /**
     * Creates a new instance of <code>UserAlreadyExistException</code> without
     * detail message.
     */
    public UserAlreadyExistException() {
    }

    /**
     * Constructs an instance of <code>UserAlreadyExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UserAlreadyExistException(String msg) {
        super(msg);
    }
}
