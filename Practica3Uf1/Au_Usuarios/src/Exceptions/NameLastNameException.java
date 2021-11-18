/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package Exceptions;

/**
 *
 * @author roger
 */
public class NameLastNameException extends Exception{

    /**
     * Creates a new instance of <code>NameLastNameException</code> without
     * detail message.
     */
    public NameLastNameException() {
    }

    /**
     * Constructs an instance of <code>NameLastNameException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NameLastNameException(String msg) {
        super(msg);
    }
}
