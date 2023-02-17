package com.uagrm.grafos.Grafos.NoPesados;


import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaNoExiste;
import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Digrafo extends Grafo {

    public Digrafo() {
        super();
    }

    public Digrafo(int cantidadDeVertices) throws NroVerticesInvalidoExcepcion {
        super(cantidadDeVertices);
    }

    @Override
    public void insertarArista(int posDeVerticeOrigen, int posDeVerticeDestino) throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> listaDeAdyacentesDelOrigen = this.listaDeAdyacencias.get(posDeVerticeOrigen);
        listaDeAdyacentesDelOrigen.add(posDeVerticeDestino);
        Collections.sort(listaDeAdyacentesDelOrigen);
    }

    @Override
    public int gradoDeVertice(int posDelVertice) {
        throw new UnsupportedOperationException("Método no soportado en grafos dirigidos");
    }

    public int gradoDeSalida(int posDelVertice) {
        return super.gradoDeVertice(posDelVertice);
    }

    public int gradoDeEntrada(int posDelVertice) {
        validarVertice(posDelVertice);
        int entradasDeVertice = 0;
        for (List<Integer> adyacentesDeUnVertice : listaDeAdyacencias) {
            for (Integer posAdyacente : adyacentesDeUnVertice) {
                if (posAdyacente == posDelVertice) {
                    entradasDeVertice++;
                }
            }
        }
        return entradasDeVertice;
    }

    @Override
    public int cantidadDeAristas() {
        int cantidadAristas = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            cantidadAristas = cantidadAristas + this.listaDeAdyacencias.get(i).size();
        }
        return cantidadAristas;
    }

    @Override
    public void eliminarArista(int posDeVerticeOrigen, int posDeVerticeDestino) {
        if (this.existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino)) {
            List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posDeVerticeOrigen);
            int posicionDelDestino = adyacentesDelOrigen.indexOf(posDeVerticeDestino);
            adyacentesDelOrigen.remove(posicionDelDestino);
        }
    }

    // ======================= EJERCICIOS =======================
    // Método para determinar si un grafo dirigido es fuertemente conexo
    public boolean esFuertementeConexo() {
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            BFS recorridoBFS = new BFS(this, i);
            if (!recorridoBFS.hayCaminoATodosLosVertices()) {
                return false;
            }
        }
        return true;
    }

    // Método para determinar si un grafo dirigido es debilmente conexo
    public boolean esDebilmenteConexo() throws NroVerticesInvalidoExcepcion { // Opción 1
        if (!esFuertementeConexo()) {
            Grafo grafo = this.convertirAGrafo();
            if (!grafo.esConexo()) {
                return false;
            }
        }
        return true;
    }

    private Grafo convertirAGrafo() throws NroVerticesInvalidoExcepcion {
        Grafo grafo = new Grafo(this.cantidadDeVertices());
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            Iterable<Integer> adyacentesDeVertice = this.adyacentesDeVertice(i);
            for (Integer posDeAdyacente : adyacentesDeVertice) {
                if (this.existeAdyacencia(i, posDeAdyacente)) {
                    grafo.insertarArista1(i, posDeAdyacente);
                }
            }
        }
        return grafo;
    }

    public boolean esDebilmenteConexo1() { // Opción 2
        DFS recorridoDFS = new DFS(this, 0);
        while (!recorridoDFS.hayCaminoATodosLosVertices()) {
            int posVerticeX = posicionDeVerticeNoMarcado(recorridoDFS);
            if (!listaDeAdyacencias.get(posVerticeX).isEmpty()) {
                Iterable<Integer> posDeAdyacentes = adyacentesDeVertice(posVerticeX);
                boolean hayAdyacencia = false;
                for (Integer posDeAdyacente : posDeAdyacentes) {
                    if (recorridoDFS.hayCaminoAVertice(posDeAdyacente)) {
                        recorridoDFS.ejecutarDFS(posVerticeX);
                        hayAdyacencia = true;
                    }
                }
                if (!hayAdyacencia) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private int posicionDeVerticeNoMarcado(DFS recorridoDFS) {
        int posicion = 0;
        boolean hayAdaycencia = false;
        while (posicion < this.cantidadDeVertices() && !hayAdaycencia) {
            if (!recorridoDFS.hayCaminoAVertice(posicion)) {
                Iterable<Integer> posDeAdyacentes = adyacentesDeVertice(posicion);
                for (Integer posDeAdyacente : posDeAdyacentes) {
                    if (recorridoDFS.hayCaminoAVertice(posDeAdyacente)) {
                        hayAdaycencia = true;
                    }
                }
            }
            posicion++;
        }
        return posicion - 1;
    }

    public boolean esDebilmenteConexo2() {
        DFS recorrido = new DFS(this, 0);
        while (!recorrido.hayCaminoATodosLosVertices()) {
            int noMarcado = noMarcadoConAdyacente(recorrido);
            if (noMarcado != -1) {
                recorrido.ejecutarDFS(noMarcado);
            } else {
                return false;
            }
        }
        return true;
    }

    private int noMarcadoConAdyacente(DFS recorrido) {
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            if (!recorrido.hayCaminoAVertice(i)) {
                Iterable<Integer> adyacentes = this.adyacentesDeVertice(i);
                for (Integer dato : adyacentes) {
                    if (recorrido.hayCaminoAVertice(dato)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    private int noMarcado(DFS recorrido) {
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            if (!recorrido.hayCaminoAVertice(i)) {
                return i;
            }
        }
        return -1;
    }

    // Método para determinar desde que vertices puedo llegar a un vertice en un grafo dirigido
    public List<Integer> llegaDesdeVertices(int posDeVertice) { // Opcion 1
        List<Integer> verticesDeDondeLlega = new ArrayList<>();
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            BFS recorridoDFS = new BFS(this, i);
            if (recorridoDFS.hayCaminoAVertice(posDeVertice)) {
                verticesDeDondeLlega.add(i);
            }
        }
        return verticesDeDondeLlega;
    }

    public List<Integer> llegaDesdeVertices1(int posDeVertice) throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste { // Opcion 2
        Digrafo digrafo = new Digrafo(this.cantidadDeVertices());
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            Iterable<Integer> posAdyacentes = adyacentesDeVertice(i);
            for (Integer posDeAdyacente : posAdyacentes) {
                digrafo.insertarArista(posDeAdyacente, i);
            }
        }
        DFS recorridoDFS = new DFS(digrafo, posDeVertice);
        List<Integer> verticesDeDondeLlega = (List<Integer>) recorridoDFS.getRecorrido();
        Collections.sort(verticesDeDondeLlega);
        return verticesDeDondeLlega;
    }

    // Método para determinar si un grafo dirigo tiene ciclos
    @Override
    public boolean existeCiclo() {
        int n = this.cantidadDeVertices();
        int[][] matriz = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matriz[i][j] = 0;
            }
        }

        for (int i = 0; i < n; i++) {
            List<Integer> adyacentes = this.listaDeAdyacencias.get(i);
            for (int j = 0; j < adyacentes.size(); j++) {
                int elemento = adyacentes.get(j);
                matriz[i][elemento] = 1;
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (matriz[i][j] != 1) {
                        matriz[i][j] = matriz[i][j] | (matriz[i][k] & matriz[k][j]);
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (matriz[i][i] == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean existeCiclo1() {
        Warshall m = new Warshall(this);
        m.warshall();
        int matriz[][] = m.getMatriz();
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i][i] == 1) {
                return true;
            }
        }
        return false;
    }

    // Método para determinar el nro de islas en un grafo dirigido
    @Override
    public int nroDeIslas() {
        DFS recorrido = new DFS(this, 0);
        int nroIslas = 1;
        while (!recorrido.hayCaminoATodosLosVertices()) {
            int noMarcado = noMarcadoConAdyacente(recorrido);
            if (noMarcado != -1) {
                recorrido.ejecutarDFS(noMarcado);
            } else {
                noMarcado = noMarcado(recorrido);
                recorrido.ejecutarDFS(noMarcado);
                nroIslas++;
            }
        }
        return nroIslas;
    }

    public int nroDeIslas2() throws NroVerticesInvalidoExcepcion {
        if (this.cantidadDeVertices() == 0) {
            return 0;
        }
        Grafo grafo = this.convertirAGrafo();
        return grafo.nroDeIslas();
    }

    /* Hacer un algoritmo para grafos dirigidos que devuelva un grafo dirigido con los mismos 
       vertices pero invertidos sus aristas. (si el grafo original tiene una arista que es 
       bidireccional, entonces no se invierte nada, se deja tal como esta). */
    public Digrafo invertirAristas() throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste {
        Digrafo digrafo = new Digrafo(this.cantidadDeVertices());
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            Iterable<Integer> posDeAdyacentes = this.adyacentesDeVertice(i);
            for (Integer posAdyacente : posDeAdyacentes) {
                digrafo.insertarArista(posAdyacente, i);
            }
        }
        return digrafo;
    }

}
