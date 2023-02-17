package com.uagrm.grafos.Grafos.Excepciones;

public class ExcepcionAristaNoExiste extends Exception {

    /**
     * Creates a new instance of <code>AristaYaExisteException</code> without detail message.
     */
    public ExcepcionAristaNoExiste() {
        super("La arista no existe");
    }

    /**
     * Constructs an instance of <code>AristaYaExisteException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionAristaNoExiste(String msg) {
        super(msg);
    }
}
