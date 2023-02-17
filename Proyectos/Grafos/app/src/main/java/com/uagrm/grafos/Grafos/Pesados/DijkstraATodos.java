package com.uagrm.grafos.Grafos.Pesados;


import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaNoExiste;
import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DijkstraATodos {

    private GrafoPesado grafo;
    private int[] predecesores;
    private double[] costos;
    private boolean[] marcados;

    public DijkstraATodos(GrafoPesado unGrafo) {
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

    public void caminosMasCortos(int posVerticeOrigen) throws ExcepcionAristaNoExiste {
        costos[posVerticeOrigen] = 0;
        int posVerticeEnTurno = posVerticeOrigen;
        while (!estanTodosMarcados() && costos[posVerticeEnTurno] != Double.POSITIVE_INFINITY) {
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

    private boolean estanTodosMarcados() {
        for (int i = 0; i < marcados.length; i++) {
            if (!marcados[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean estaMarcado(int posVertice) {
        for (int i = 0; i < marcados.length; i++) {
            if (marcados[posVertice]) {
                return true;
            }
        }
        return false;
    }

    public List<List<Integer>> caminoDeCostoMinimoAVertice() {
        List<List<Integer>> camino = new ArrayList<>();
        for (int i = 0; i < marcados.length; i++) {
            List<Integer> lista = new ArrayList<>();
            if (marcados[i]) {
                lista.add(i);
            }
            int predecesor = predecesores[i];
            while (predecesor != -1) {
                lista.add(predecesor);
                predecesor = predecesores[predecesor];
            }
            camino.add(i, lista);
        }

        int n = camino.size();
        for (int i = 0; i < n; i++) {
            List<Integer> elementos = camino.get(i);
            Queue<Integer> cola = new LinkedList<>();
            int size = elementos.size();
            for (int j = size - 1; j >= 0; j--) {
                int elemento = elementos.get(j);
                cola.offer(elemento);
            }
            size = cola.size();
            elementos = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                elementos.add(cola.poll());
            }
            camino.set(i, elementos);
        }
        return camino;
    }

    public List<Integer> costoMinimoAVertice() {
        List<Integer> listaDeCostos = new ArrayList<>();
        for (int i = 0; i < this.costos.length; i++) {
            listaDeCostos.add((int)this.costos[i]);
        }
        return listaDeCostos;
    }

    public String toString(int origen) {
        String camino = "";
        List<List<Integer>> caminos = caminoDeCostoMinimoAVertice();
        List<Integer> costos = costoMinimoAVertice();
        for (int i = 0; i < caminos.size(); i++) {
            if (estaMarcado(i)) {
                List<Integer> ruta = caminos.get(i);
                int destino = ruta.get(ruta.size() - 1);
                if (origen != destino) {
                    camino = camino + "Origen: " + origen + "      Destino: " + destino + "      Costo Minimo: " + costos.get(i) + "      Recorrido: ";
                    for (int j = 0; j < ruta.size(); j++) {
                        if (j == ruta.size() - 1) {
                            camino = camino + "[" + ruta.get(j) + "]\n";
                        } else {
                            camino = camino + "[" + ruta.get(j) + "] -> ";
                        }
                    }
                }
            } else {
                camino = camino + "Origen: " + origen + "      Destino: " + i + "      No existe camino.\n";
            }
        }
        return camino;
    }

    public static void main(String[] args) throws ExcepcionAristaYaExiste, ExcepcionAristaNoExiste, NroVerticesInvalidoExcepcion {

        DigrafoPesado digrafo = new DigrafoPesado(4);

        digrafo.insertarArista(0, 2, 20);
        digrafo.insertarArista(0, 3, 30);
        digrafo.insertarArista(1, 2, 40);
        digrafo.insertarArista(2, 1, 5);
        digrafo.insertarArista(2, 3, 100);

//        digrafo.insertarArista(0, 1, 50);
//        digrafo.insertarArista(0, 2, 10);
//        digrafo.insertarArista(0, 4, 60);
//        digrafo.insertarArista(0, 5, 100);
//        digrafo.insertarArista(1, 3, 50);
//        digrafo.insertarArista(1, 4, 15);
//        digrafo.insertarArista(2, 1, 5);
//        digrafo.insertarArista(3, 0, 80);
//        digrafo.insertarArista(3, 5, 20);
//        digrafo.insertarArista(4, 5, 20);
//        digrafo.insertarArista(5, 1, 40);
//        digrafo.insertarArista(5, 2, 70);
        DijkstraATodos m = new DijkstraATodos(digrafo);

        int origen = 1;

        m.caminosMasCortos(origen);
//        System.out.println(Arrays.toString(m.costos));
//        System.out.println(Arrays.toString(m.marcados));
//        System.out.println(Arrays.toString(m.predecesores));
//        System.out.println("Camino a vertice: " + m.caminoDeCostoMinimoAVertice());
//        System.out.println("Costo minimo a vertice: " + m.costoMinimoAVertice());
        System.out.println(m.toString(origen));

    }

}
