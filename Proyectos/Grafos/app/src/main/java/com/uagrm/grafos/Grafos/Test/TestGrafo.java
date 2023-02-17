package com.uagrm.grafos.Grafos.Test;


import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaNoExiste;
import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;
import com.uagrm.grafos.Grafos.NoPesados.Grafo;

public class TestGrafo {
    
    public static void main(String[] args) throws ExcepcionAristaYaExiste, ExcepcionAristaNoExiste, NroVerticesInvalidoExcepcion {

        Grafo grafo = new Grafo(3);
        
        grafo.insertarArista(0, 1);
        grafo.insertarArista(0, 2);
        grafo.insertarArista(1, 2);
        grafo.insertarArista(2, 2);

//        grafo.insertarArista(0, 1);
//        grafo.insertarArista(1, 2);
//        grafo.insertarArista(1, 3);
//        grafo.insertarArista(2, 4);
//        grafo.insertarArista(2, 5);
//        grafo.insertarArista(6, 7);
//        grafo.insertarArista(6, 8);
//        grafo.insertarArista(7, 8);

        System.out.println(grafo.toString());
//        System.out.println("Cantidad de Vertices: " + grafo.cantidadDeVertices());
//        System.out.println("Cantidad de Aristas: " + grafo.cantidadDeAristas());
//        System.out.println("Es conexo: " + grafo.esConexo());
//        System.out.println("Existe ciclo: " + grafo.existeCiclo());
//        System.out.println("Cantidad de Islas: " + grafo.nroDeIslas());
        System.out.println("");
        
        grafo.eliminarArista(1, 0);
//        grafo.eliminarVertice(2);
        
        System.out.println(grafo.toString());
//        System.out.println("Cantidad de Vertices: " + grafo.cantidadDeVertices());
//        System.out.println("Cantidad de Aristas: " + grafo.cantidadDeAristas());
//        System.out.println("");
                
    }
    
}
