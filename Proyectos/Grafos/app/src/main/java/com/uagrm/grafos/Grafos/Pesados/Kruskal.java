package com.uagrm.grafos.Grafos.Pesados;

import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaNoExiste;
import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;

import java.util.ArrayList;
import java.util.List;

public class Kruskal {

    private GrafoPesado grafo;
    private  List<Integer> origenes;
    private List<Integer> destinos;
    private List<Double> pesos;
    private double[][] matriz;    // Es la matriz de pesos

    public Kruskal(GrafoPesado unGrafo) {
        this.grafo = unGrafo;
        this.origenes = new ArrayList<>();
        this.destinos = new ArrayList<>();
        this.pesos = new ArrayList<>();
        int n = grafo.cantidadDeVertices();
        this.matriz = new double[n][n];
        this.matrizDePesos();
    }

    public GrafoPesado kruskal() throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {
        this.ordenar();
        GrafoPesado grafoAux = new GrafoPesado(this.grafo.cantidadDeVertices());
        for (int i = 0; i < origenes.size(); i++) {
            int origen = origenes.get(i);
            int destino = destinos.get(i);
            double peso = pesos.get(i);
            grafoAux.insertarArista(origen, destino, peso);
            ExisteCicloGP g = new ExisteCicloGP(grafoAux);
            if (g.existeCiclo()) {
                grafoAux.eliminarArista(origen, destino);
            }
        }
        return grafoAux;
    }
    
    
    // Carga la matriz con los pesos 
    public void matrizDePesos() {
        int n = grafo.cantidadDeVertices();
        for (int i = 0; i < n; i++) {
            List<AdyacenteConPeso> lista = grafo.listaDeAdyacencias.get(i);
            for (int j = 0; j < lista.size(); j++) {
                int elemento = lista.get(j).getIndiceDeVertice();
                double peso = lista.get(j).getPeso();
                matriz[i][elemento] = peso;
            }
        }
    }
    
    // Ordena los vertices por el menor peso
    public void ordenar() {
        while (!estaVacia()) {
            List<Double> lista = pesoMenor();
            double origen = lista.get(0);
            double destino = lista.get(1);
            this.origenes.add((int)origen);
            this.destinos.add((int)destino);
            this.pesos.add(lista.get(2));
        }
    }

    // Devuelve la arista de menor peso
    private List<Double> pesoMenor() {
        double pesoMenor = Double.POSITIVE_INFINITY;
        int posOrigen = 0;
        int posDestino = 0;
        boolean hayCambio;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                if (matriz[i][j] != 0 && matriz[i][j] < pesoMenor) {
                    pesoMenor = matriz[i][j];
                    posOrigen = i;
                    posDestino = j;
                }
            }
        }
        matriz[posOrigen][posDestino] = 0;
        matriz[posDestino][posOrigen] = 0;
        List<Double> lista = new ArrayList<>();
        lista.add((double)posOrigen);
        lista.add((double)posDestino);
        lista.add(pesoMenor);
        return lista;
    }

    // Verifica que la matriz de pesos este vacia (esten con puros ceros)
    private boolean estaVacia() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                if (matriz[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public String mostrarMatriz() {
        String m = "[ ";
        int n = matriz.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m = m + matriz[i][j] + "   ";
            }
            m = m + "]\n[ ";
        }
        m = m.substring(0, m.length() - 2);
        return m;
    }

    public String mostrarLista() {
        String listas = "Origenes    Destinos    pesos\n";
        for (int i = 0; i < this.origenes.size(); i++) {
            listas = listas + "    " + this.origenes.get(i) + "\t\t" + this.destinos.get(i) + "\t " + this.pesos.get(i) + "\n";
        }
        return listas;
    }

    public static void main(String[] args) throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste, ExcepcionAristaNoExiste {

        GrafoPesado grafo = new GrafoPesado(10);

        grafo.insertarArista(4, 7, 3);
        grafo.insertarArista(4, 6, 4);
        grafo.insertarArista(6, 8, 4);
        grafo.insertarArista(4, 3, 5);
        grafo.insertarArista(5, 1, 5);
        grafo.insertarArista(0, 1, 5);
        grafo.insertarArista(1, 3, 6);
        grafo.insertarArista(6, 9, 6);
        grafo.insertarArista(2, 3, 7);
        grafo.insertarArista(8, 9, 7);
        grafo.insertarArista(5, 8, 7);
        grafo.insertarArista(2, 4, 8);
        grafo.insertarArista(0, 3, 8);
        grafo.insertarArista(5, 6, 9);
        grafo.insertarArista(0, 2, 10);
        grafo.insertarArista(3, 5, 11);
        grafo.insertarArista(6, 7, 12);
        grafo.insertarArista(7, 9, 12);
        grafo.insertarArista(2, 7, 15);

        Kruskal kruskal = new Kruskal(grafo);

//        kruskal.matrizDePesos();
//
//        System.out.println(kruskal.mostrarMatriz());
//        kruskal.ordenar();
//        System.out.println(kruskal.mostrarMatriz());
//        System.out.println(kruskal.mostrarLista());
        

        GrafoPesado aux = kruskal.kruskal();
        System.out.println(aux.toString());

        
        
    }

}
