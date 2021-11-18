/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package Exceptions;

/**
 *
 * @author roger
 */
public class StringIsCryptedException extends Exception{

    /**
     * Creates a new instance of <code>StringIsCryptedException</code> without
     * detail message.
     */
    public StringIsCryptedException() {
    }

    /**
     * Constructs an instance of <code>StringIsCryptedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public StringIsCryptedException(String msg) {
        super(msg);
    }
}
