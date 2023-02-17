package com.uagrm.grafos.Grafos.NoPesados;


import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;

import java.util.List;

public class Warshall {
    
    private Grafo grafo;
    private int matriz[][];
    
    public Warshall(Grafo unGrafo) {
        this.grafo = unGrafo;
        int n = grafo.cantidadDeVertices();
        this.matriz = new int[n][n];
    }

    public int[][] getMatriz() {
        return matriz;
    }
    
    // Matriz de camino de longitud 1
    public void matrizDeCaminos() {
        int n = matriz.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matriz[i][j] = 0;
            }
        }
        
        for (int i = 0; i < n; i++) {
            List<Integer> adyacentes = grafo.listaDeAdyacencias.get(i);
            for (int j = 0; j < adyacentes.size(); j++) {
                int elemento = adyacentes.get(j);
                matriz[i][elemento] = 1;
            }
        }
    }
    
    public void warshall() {
        matrizDeCaminos();
        int n = matriz.length;
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (matriz[i][j] != 1) {
                        matriz[i][j] = matriz[i][j] | (matriz[i][k] & matriz[k][j]);
                    }
                }
            }
        }
    }
    
    public String mostrarMatriz() {
        String m = "[ ";
        int n = matriz.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m = m + matriz[i][j] + " ";
            }
            m = m + "]\n[ ";
        }
        m = m.substring(0, m.length() - 2);
        return m;
    }
    
    public static void main(String[] args) throws ExcepcionAristaYaExiste {
        
        Digrafo grafo = new Digrafo();
        
        grafo.insertarVertice();
        grafo.insertarVertice();
        grafo.insertarVertice();
        grafo.insertarVertice();
        grafo.insertarVertice();
        grafo.insertarVertice();
        
        grafo.insertarArista(1, 0);
        grafo.insertarArista(1, 3);
        grafo.insertarArista(2, 0);
        grafo.insertarArista(2, 3);
        grafo.insertarArista(3, 1);
        grafo.insertarArista(3, 3);
//        grafo.insertarArista(0, 1);
//        grafo.insertarArista(3, 2);
        grafo.insertarArista(4, 5);
        grafo.insertarArista(5, 4);
//        grafo.insertarArista(5, 2);

//        grafo.insertarArista(0, 1);
//        grafo.insertarArista(1, 3);
//        grafo.insertarArista(1, 4);
//        grafo.insertarArista(2, 2);
//        grafo.insertarArista(2, 4);
//        grafo.insertarArista(3, 1);
//        grafo.insertarArista(4, 2);
        
//        System.out.println(grafo.toString());
        
        Warshall m = new Warshall(grafo);
        
        m.matrizDeCaminos();
        System.out.println(m.mostrarMatriz());
        
        m.warshall();
        System.out.println(m.mostrarMatriz());
        
    }
    
}
