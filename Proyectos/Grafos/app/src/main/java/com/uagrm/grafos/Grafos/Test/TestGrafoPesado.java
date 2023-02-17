package com.uagrm.grafos.Grafos.Test;


import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Pesados.GrafoPesado;

public class TestGrafoPesado {
    
    public static void main(String[] args) throws ExcepcionAristaYaExiste {
        
        GrafoPesado grafo = new GrafoPesado();
        
        grafo.insertarVertice();
        grafo.insertarVertice();
        grafo.insertarVertice();
        grafo.insertarVertice();
        
        grafo.insertarArista(0, 2, 20);
        grafo.insertarArista(0, 3, 30);
        grafo.insertarArista(1, 2, 40);
        grafo.insertarArista(3, 1, 5);
        grafo.insertarArista(2, 3, 100);
        
        System.out.println(grafo.toString());
        
    }
    
}
