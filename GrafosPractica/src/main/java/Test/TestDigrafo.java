package Test;

import NoPesados.Digrafo;

public class TestDigrafo {
 
    public static void main(String[] args) {
        
        Digrafo digrafo = new Digrafo(8);
        
        digrafo.insertarArista(0, 0);
        digrafo.insertarArista(0, 4);
        digrafo.insertarArista(1, 0);
        digrafo.insertarArista(1, 2);
        digrafo.insertarArista(1, 6);
        digrafo.insertarArista(2, 5);
        digrafo.insertarArista(3, 6);
        digrafo.insertarArista(4, 7);   
        digrafo.insertarArista(5, 2);   
        digrafo.insertarArista(5, 4);   
        digrafo.insertarArista(6, 1);   
        digrafo.insertarArista(6, 7);   
        
        System.out.println(digrafo.toString());
        System.out.println("Nro de Islas: " + digrafo.nroDeIslas());
        
    }
    
}
