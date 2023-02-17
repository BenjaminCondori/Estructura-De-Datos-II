package com.uagrm.grafos.Grafos.Excepciones;

public class ExcepcionAristaYaExiste extends Exception {

    /**
     * Creates a new instance of <code>AristaYaExisteException</code> without detail message.
     */
    public ExcepcionAristaYaExiste() {
        super("La arista ya existe");
    }

    /**
     * Constructs an instance of <code>AristaYaExisteException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcepcionAristaYaExiste(String msg) {
        super(msg);
    }
}
