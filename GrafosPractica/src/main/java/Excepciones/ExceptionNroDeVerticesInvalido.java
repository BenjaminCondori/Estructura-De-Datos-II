package Excepciones;

public class ExceptionNroDeVerticesInvalido extends Exception {

    public ExceptionNroDeVerticesInvalido() {
        super("Nro de vertices no es válido");
    }
    
    public ExceptionNroDeVerticesInvalido(String msg) {
        super(msg);
    }
    
}
