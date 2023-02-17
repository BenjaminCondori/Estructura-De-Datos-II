package Test;

import Excepciones.ExceptionNroDeVerticesInvalido;
import NoPesados.Conexo;
import NoPesados.Grafo;

public class TestGrafo {
    
    public static void main(String[] args) throws ExceptionNroDeVerticesInvalido {
        
        Grafo grafo = new Grafo(13);
        
//        grafo.insertarArista(0, 1);
//        grafo.insertarArista(0, 2);
//        grafo.insertarArista(1, 2);
//        grafo.insertarArista(2, 2);
        
        grafo.insertarArista(0, 1);
        grafo.insertarArista(1, 2);
        grafo.insertarArista(1, 3);
        grafo.insertarArista(2, 4);
        grafo.insertarArista(2, 5);
        grafo.insertarArista(6, 7);
        grafo.insertarArista(6, 8);
        grafo.insertarArista(7, 8);
        
        grafo.insertarArista(9, 10);
        grafo.insertarArista(10, 11);
        
        Conexo conexo = new Conexo(grafo);
        
        System.out.println(grafo.toString());
        System.out.println("Es conexo: " + conexo.esConexo());
        System.out.println("Es conexo: " + grafo.esConexo());
        System.out.println("Nro de Islas: " + grafo.nroDeIslas());
        
    }
    
}
