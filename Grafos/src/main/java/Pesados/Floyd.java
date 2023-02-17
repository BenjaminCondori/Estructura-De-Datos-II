package Pesados;

import Excepciones.ExcepcionAristaNoExiste;
import Excepciones.ExcepcionAristaYaExiste;
import Excepciones.NroVerticesInvalidoExcepcion;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Floyd {

    private GrafoPesado grafo;
    private double[][] matriz;    // Matriz de adyacencia de pesos
    private int[][] predecesores;

    public Floyd(GrafoPesado unGrafo) {
        this.grafo = unGrafo;
        int n = grafo.cantidadDeVertices();
        this.matriz = new double[n][n];
        this.predecesores = new int[n][n];
    }

    private void llenarMatrizDePesos() {
        int n = matriz.length;
        double max = Double.POSITIVE_INFINITY;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matriz[i][j] = 0;
                } else {
                    matriz[i][j] = max;
                }
                predecesores[i][j] = -1;
            }
        }

        for (int i = 0; i < n; i++) {
            List<AdyacenteConPeso> adyacentes = grafo.listaDeAdyacencias.get(i);
            for (int j = 0; j < adyacentes.size(); j++) {
                int elemento = adyacentes.get(j).getIndiceDeVertice();
                double peso = adyacentes.get(j).getPeso();
                matriz[i][elemento] = peso;
            }
        }
    }

    public void algoritmoFloyd() {
        this.llenarMatrizDePesos();
        int n = matriz.length;
        double minimo;
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    minimo = Math.min(matriz[i][j], matriz[i][k] + matriz[k][j]);
                    if (matriz[i][j] != matriz[i][k] + matriz[k][j]) {
                        if (minimo == matriz[i][k] + matriz[k][j]) {
                            predecesores[i][j] = k;
                        }
                    }
                    matriz[i][j] = minimo;
                }
            }
        }
    }

    public String mostrarCamino(int posDeOrigen, int posDeDestino) {
        List<Integer> lista = new ArrayList<>();
        Stack<Integer> pila = new Stack<>();
        pila.push(posDeDestino);
        pila.push(posDeOrigen);
        while (!pila.isEmpty()) {
            int origen = pila.pop();
            int destino = pila.pop();
            int dato = predecesores[origen][destino];
            if (dato != -1) {
                pila.push(destino);
                pila.push(dato);
                pila.push(dato);
                pila.push(origen);
            } else {
                if (matriz[origen][destino] != Double.POSITIVE_INFINITY) {
                    if (!lista.contains(origen)) {
                        lista.add(origen);
                    }
                    if (!lista.contains(destino)) {
                        lista.add(destino);
                    }
                }
            }
        }
        String camino = "";
        camino = camino + "El camino de costo minimo del vertice [" + posDeOrigen + "] al vertice [" + posDeDestino + "] es: ";
        if (lista.isEmpty()) {
            camino = camino + "No existe camino";
        } else {
            camino = camino + lista.toString() + "\n";
            camino = camino + "El costo minimo es: " + matriz[posDeOrigen][posDeDestino];
        }
//        for (int i = 0; i < lista.size(); i++) {
//            if (i == lista.size() - 1) {
//                camino = camino + "[" + lista.get(i) + "]\n";
//            } else {
//                camino = camino + "[" + lista.get(i) + "] -> ";
//            }
//        }
        return camino;
    }

    public String mostrarMatriz() {
        String m = "[ ";
        int n = matriz.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double max = Double.POSITIVE_INFINITY;
                if (matriz[i][j] == max) {
                    m = m + "***" + " ";
                } else {
                    m = m + matriz[i][j] + " ";
                }
            }
            m = m + "]\n[ ";
        }
        m = m.substring(0, m.length() - 2);
        return m;
    }

    public String mostrarPredecesores() {
        String m = "[ ";
        int n = predecesores.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m = m + predecesores[i][j] + " ";
            }
            m = m + "]\n[ ";
        }
        m = m.substring(0, m.length() - 2);
        return m;
    }

    public static void main(String[] args) throws ExcepcionAristaYaExiste, ExcepcionAristaNoExiste, NroVerticesInvalidoExcepcion {

//        GrafoPesado grafo = new GrafoPesado(10);
//        DigrafoPesado digrafo = new DigrafoPesado(5);
//        grafo.insertarArista(4, 7, 3);
//        grafo.insertarArista(4, 6, 4);
//        grafo.insertarArista(6, 8, 4);
//        grafo.insertarArista(4, 3, 5);
//        grafo.insertarArista(5, 1, 5);
//        grafo.insertarArista(0, 1, 5);
//        grafo.insertarArista(1, 3, 6);
//        grafo.insertarArista(6, 9, 6);
//        grafo.insertarArista(2, 3, 7);
//        grafo.insertarArista(8, 9, 7);
//        grafo.insertarArista(5, 8, 7);
//        grafo.insertarArista(2, 4, 8);
//        grafo.insertarArista(0, 3, 8);
//        grafo.insertarArista(5, 6, 9);
//        grafo.insertarArista(0, 2, 10);
//        grafo.insertarArista(3, 5, 11);
//        grafo.insertarArista(6, 7, 12);
//        grafo.insertarArista(7, 9, 12);
//        grafo.insertarArista(2, 7, 15);
//        digrafo.insertarArista(0, 1, 1);
//        digrafo.insertarArista(1, 3, 4);
//        digrafo.insertarArista(1, 4, 7);
//        digrafo.insertarArista(2, 0, 3);
//        digrafo.insertarArista(2, 1, 2);
//        digrafo.insertarArista(2, 4, 4);
//        digrafo.insertarArista(3, 0, 6);
//        digrafo.insertarArista(3, 4, 2);
//        digrafo.insertarArista(4, 3, 3);
        DigrafoPesado grafo = new DigrafoPesado(11);

        grafo.insertarArista(0, 3, 13);
        grafo.insertarArista(0, 4, 3);
        grafo.insertarArista(1, 0, 1);
        grafo.insertarArista(2, 0, 25);
        grafo.insertarArista(2, 1, 2);
        grafo.insertarArista(2, 5, 30);
        grafo.insertarArista(4, 3, 12);
        grafo.insertarArista(4, 6, 17);
        grafo.insertarArista(4, 9, 8);
        grafo.insertarArista(5, 1, 5);
        grafo.insertarArista(5, 6, 4);
        grafo.insertarArista(5, 8, 14);
        grafo.insertarArista(6, 2, 11);
        grafo.insertarArista(6, 7, 9);
        grafo.insertarArista(7, 5, 15);
        grafo.insertarArista(8, 7, 50);
        grafo.insertarArista(8, 10, 6);
        grafo.insertarArista(9, 7, 23);
        grafo.insertarArista(10, 9, 33);

        Floyd f = new Floyd(grafo);

        f.algoritmoFloyd();
//        System.out.println(f.caminosMasCortos1());

        System.out.println(f.mostrarMatriz());
        System.out.println(f.mostrarPredecesores());

        System.out.println(f.mostrarCamino(2, 8));

    }

}
