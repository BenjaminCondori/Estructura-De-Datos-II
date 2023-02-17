package com.uagrm.grafos.Grafos.Test;


import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Pesados.DigrafoPesado;

public class TestGrafoDirigidoPesado {
    
    public static void main(String[] args) throws ExcepcionAristaYaExiste {
        
        DigrafoPesado digrafo = new DigrafoPesado();
        
        digrafo.insertarVertice();
        digrafo.insertarVertice();
        digrafo.insertarVertice();
        digrafo.insertarVertice();
        
        digrafo.insertarArista(0, 2, 20);
        digrafo.insertarArista(0, 3, 30);
        digrafo.insertarArista(1, 2, 40);
        digrafo.insertarArista(2, 1, 5);
        digrafo.insertarArista(2, 3, 100);
        
        System.out.println(digrafo.toString());
        
    }
    
}
