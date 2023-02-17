/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package Excepciones;

/**
 *
 * @author USER
 */
public class ClaveNoExisteException extends Exception {

    /**
     * Creates a new instance of <code>ClaveNoExisteException</code> without detail message.
     */
    public ClaveNoExisteException() {
        super("La clave no existe");
    }

    /**
     * Constructs an instance of <code>ClaveNoExisteException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ClaveNoExisteException(String msg) {
        super(msg);
    }
}
