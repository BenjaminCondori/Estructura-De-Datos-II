package com.uagrm.grafos.Grafos.Pesados;

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

    private void matrizDeCaminos() {
        int n = matriz.length;
        double max = Double.POSITIVE_INFINITY;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    matriz[i][j] = 0;
                    predecesores[i][j] = -1;
                } else {
                    matriz[i][j] = max;
                    predecesores[i][j] = -1;
                }
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

    public void caminosMasCortos() {
        this.matrizDeCaminos();
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
        caminosMasCortos();
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
        }
        for (int i = 0; i < lista.size(); i++) {
            if (i == lista.size() - 1) {
                camino = camino + "[" + lista.get(i) + "]\n";
            } else {
                camino = camino + "[" + lista.get(i) + "] -> ";
            }
        }
        if (matriz[posDeOrigen][posDeDestino] != Double.POSITIVE_INFINITY) {
            camino = camino + "El costo minimo es: " + matriz[posDeOrigen][posDeDestino];
        }
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


}
