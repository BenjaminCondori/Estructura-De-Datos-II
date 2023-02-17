package com.uagrm.grafos.Grafos.Excepciones;

public class NroVerticesInvalidoExcepcion extends Exception {

    /**
     * Creates a new instance of <code>NroVerticesInvalidoExcepcion</code> without detail message.
     */
    public NroVerticesInvalidoExcepcion() {
        super("Nro de vertices inválido");
    }

    /**
     * Constructs an instance of <code>NroVerticesInvalidoExcepcion</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NroVerticesInvalidoExcepcion(String msg) {
        super(msg);
    }
}
