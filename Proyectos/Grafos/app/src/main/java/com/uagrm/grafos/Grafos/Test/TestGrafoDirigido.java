package com.uagrm.grafos.Grafos.Test;


import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;
import com.uagrm.grafos.Grafos.NoPesados.Digrafo;

public class TestGrafoDirigido {
    
    public static void main(String[] args) throws ExcepcionAristaYaExiste, NroVerticesInvalidoExcepcion {
        
        Digrafo digrafo = new Digrafo(12);
        
//        digrafo.insertarArista(1, 0);
//        digrafo.insertarArista(1, 3);
//        digrafo.insertarArista(2, 0);
//        digrafo.insertarArista(2, 3);
//        digrafo.insertarArista(3, 1);
//        digrafo.insertarArista(3, 3);
////        digrafo.insertarArista(0, 1);
////        digrafo.insertarArista(3, 2);
//        digrafo.insertarArista(4, 5);
//        digrafo.insertarArista(5, 4);
//        digrafo.insertarArista(5, 2);

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
//        digrafo.insertarArista(7, 3);
        digrafo.insertarArista(8, 9);
        digrafo.insertarArista(8, 10);
        digrafo.insertarArista(10, 9);
        
//        grafo.insertarArista(0, 1);
//        grafo.insertarArista(1, 3);
//        grafo.insertarArista(1, 4);
//        grafo.insertarArista(2, 2);
//        grafo.insertarArista(2, 4);
//        grafo.insertarArista(3, 1);
//        grafo.insertarArista(4, 2);
        
//        digrafo.insertarArista(0, 2);
//        digrafo.insertarArista(0, 3);
//        digrafo.insertarArista(1, 2);
//        digrafo.insertarArista(2, 1);
//        digrafo.insertarArista(2, 3);
        
        System.out.println(digrafo.toString());
//        Digrafo unDigrafo = digrafo.invertirAristas();
//        System.out.println(unDigrafo.toString());
        
        System.out.println("Cantidad de Vertices: " + digrafo.cantidadDeVertices());
        System.out.println("Cantidad de Aristas: " + digrafo.cantidadDeAristas());
        System.out.println("Grado de entrada: " + digrafo.gradoDeEntrada(3));
        System.out.println("Grado de salida: " + digrafo.gradoDeSalida(3));
        System.out.println("Es fuertemente conexo: " + digrafo.esFuertementeConexo());
        System.out.println("Es debilmente conexo: " + digrafo.esDebilmenteConexo());
        System.out.println("Es debilmente conexo: " + digrafo.esDebilmenteConexo1());
        System.out.println("Es debilmente conexo: " + digrafo.esDebilmenteConexo2());
        System.out.println("Llega desde estos vertices: " + digrafo.llegaDesdeVertices(1));
        System.out.println("Llega desde estos vertices: " + digrafo.llegaDesdeVertices1(1));
        System.out.println("Cantidad de islas: " + digrafo.nroDeIslas());
        System.out.println("Cantidad de islas: " + digrafo.nroDeIslas1());
        System.out.println("Cantidad de islas: " + digrafo.nroDeIslas2());
        System.out.println("Existe ciclo: " + digrafo.existeCiclo());
        System.out.println("Existe ciclo: " + digrafo.existeCiclo1());
        System.out.println("");
                
//        digrafo.eliminarArista(3, 3);
//        digrafo.eliminarVertice(3);
//        
//        System.out.println(digrafo.toString());
//        System.out.println("Cantidad de Vertices: " + digrafo.cantidadDeVertices());
//        System.out.println("Cantidad de Aristas: " + digrafo.cantidadDeAristas());
//        System.out.println("");

    }
    
}
