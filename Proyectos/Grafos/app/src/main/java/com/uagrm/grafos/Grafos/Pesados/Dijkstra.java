package com.uagrm.grafos.Grafos.Pesados;


import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaNoExiste;
import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Dijkstra {

    private GrafoPesado grafo;
    private int[] predecesores;
    private double[] costos;
    private boolean[] marcados;

    public Dijkstra(GrafoPesado unGrafo) {
        this.grafo = unGrafo;
        int n = grafo.cantidadDeVertices();
        this.predecesores = new int[n];
        this.costos = new double[n];
        this.marcados = new boolean[n];
        for (int i = 0; i < this.grafo.cantidadDeVertices(); i++) {
            costos[i] = Double.POSITIVE_INFINITY;
            predecesores[i] = -1;
            marcados[i] = false;
        }
    }

    public void caminoMasCorto(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        costos[posVerticeOrigen] = 0;
        int posVerticeEnTurno = posVerticeOrigen;
        while (!marcados[posVerticeDestino] && costos[posVerticeEnTurno] != Double.POSITIVE_INFINITY) {
            Iterable<Integer> adyacentesDelVertice = grafo.adyacentesDeVertice(posVerticeEnTurno);
            for (Integer posDeAdyacente : adyacentesDelVertice) {
                if (!this.marcados[posDeAdyacente]) {
                    double peso = grafo.peso(posVerticeEnTurno, posDeAdyacente);
                    if (costos[posDeAdyacente] > (costos[posVerticeEnTurno] + peso)) {
                        costos[posDeAdyacente] = costos[posVerticeEnTurno] + peso;
                        predecesores[posDeAdyacente] = posVerticeEnTurno;
                    }
                }
            }
            marcados[posVerticeEnTurno] = true;
            posVerticeEnTurno = posDeVerticeNoMarcadoDeMenorCosto();
        }
    }

    private int posDeVerticeNoMarcadoDeMenorCosto() {
        int posDeVertice = 0;
        double maximo = Double.POSITIVE_INFINITY;
        for (int i = 0; i < marcados.length; i++) {
            if (!marcados[i] && costos[i] <= maximo) {
                maximo = costos[i];
                posDeVertice = i;
            }
        }
        return posDeVertice;
    }

    public List<Integer> caminoDeCostoMinimoAVertice(int posVerticeDestino) {
        List<Integer> camino = new ArrayList<>();
        Stack<Integer> pila = new Stack<>();
        if (marcados[posVerticeDestino]) {
            pila.push(posVerticeDestino);
        }
        int predecesor = predecesores[posVerticeDestino];
        while (predecesor != -1) {
            pila.push(predecesor);
            predecesor = predecesores[predecesor];
        }
        int n = pila.size();
        for (int i = 0; i < n; i++) {
            camino.add(pila.pop());
        }
        return camino;
    }

    public int costoMinimoAVertice(int posVerticeDestino) {
        return (int)costos[posVerticeDestino];
    }

    public String toString(int posDeVerticeDestino) {
        String camino = "El camino de costo minimo al vertice [" + posDeVerticeDestino + "] es: ";
        List<Integer> listaDelCamino = caminoDeCostoMinimoAVertice(posDeVerticeDestino);
        for (int i = 0; i < listaDelCamino.size(); i++) {
            if (i == listaDelCamino.size() - 1) {
                camino = camino + "[" + listaDelCamino.get(i) + "]\n";
            } else {
                camino = camino + "[" + listaDelCamino.get(i) + "]" + " -> ";
            }
        }
        camino = camino + "El costo minimo al vertice [" + posDeVerticeDestino + "] es: " + costoMinimoAVertice(posDeVerticeDestino) + "\n";
        return camino;
    }

    public static void main(String[] args) throws ExcepcionAristaYaExiste, ExcepcionAristaNoExiste, NroVerticesInvalidoExcepcion {

        DigrafoPesado digrafo = new DigrafoPesado(6);

//        digrafo.insertarArista(0, 2, 20);
//        digrafo.insertarArista(0, 3, 30);
//        digrafo.insertarArista(1, 2, 40);
//        digrafo.insertarArista(2, 1, 5);
//        digrafo.insertarArista(2, 3, 100);
        digrafo.insertarArista(0, 1, 50);
        digrafo.insertarArista(0, 2, 10);
        digrafo.insertarArista(0, 4, 60);
        digrafo.insertarArista(0, 5, 100);
        digrafo.insertarArista(1, 3, 50);
        digrafo.insertarArista(1, 4, 15);
        digrafo.insertarArista(2, 1, 5);
        digrafo.insertarArista(3, 0, 80);
        digrafo.insertarArista(3, 5, 20);
        digrafo.insertarArista(4, 5, 20);
        digrafo.insertarArista(5, 1, 40);
        digrafo.insertarArista(5, 2, 70);

        Dijkstra m = new Dijkstra(digrafo);

        m.caminoMasCorto(0, 4);
//        System.out.println("Camino a vertice: " + m.caminoDeCostoMinimoAVertice(1));
//        System.out.println("Costo minimo a vertice: " + m.costoMinimoAVertice(1));
        System.out.println(m.toString(4));

    }

}
