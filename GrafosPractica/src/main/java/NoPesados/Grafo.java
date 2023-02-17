package NoPesados;

import Excepciones.ExceptionNroDeVerticesInvalido;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grafo {
    
    List<List<Integer>> listaDeAdyacencias;
    
    public Grafo() {
        this.listaDeAdyacencias = new ArrayList<>();
    }
    
    public Grafo(int nroDeVertices) throws ExceptionNroDeVerticesInvalido {
        if (nroDeVertices <= 0) {
            throw new ExceptionNroDeVerticesInvalido();
        }
        this.listaDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < nroDeVertices; i++) {
            insertarVertice();
        }
    }
    
    public void validarVertice(int posDeVertice) {
        if (posDeVertice < 0 || posDeVertice >= this.cantidadDeVertices()) {
            throw new IllegalArgumentException("Vertice no válido");
        }
    }
    
    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino) {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<Integer> adyacentesDeVerticeOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        return adyacentesDeVerticeOrigen.contains(posVerticeDestino);
    }
    
    public int gradoDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacentes = this.listaDeAdyacencias.get(posDeVertice);
        return adyacentes.size();
    }
    
    public final void insertarVertice() {
        List<Integer> listaDeAdyacentesDelNuevoVertice = new ArrayList<>();
        this.listaDeAdyacencias.add(listaDeAdyacentesDelNuevoVertice);
    }
    
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) {
        if (!existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
            adyacentesDelOrigen.add(posVerticeDestino);
            Collections.sort(adyacentesDelOrigen);
            if (posVerticeOrigen != posVerticeDestino) {
                List<Integer> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
                adyacentesDelDestino.add(posVerticeOrigen);
                Collections.sort(adyacentesDelDestino);
            }
        }
    }
    
    public void eliminarVertice(int posDeVerticeAEliminar) {
        validarVertice(posDeVerticeAEliminar);
        this.listaDeAdyacencias.remove(posDeVerticeAEliminar);
        for (List<Integer> adyacentesDeUnVertice : this.listaDeAdyacencias) {
            int posVerticeAEliminar = adyacentesDeUnVertice.indexOf(posDeVerticeAEliminar);
            if (posVerticeAEliminar >= 0) {
                adyacentesDeUnVertice.remove(posVerticeAEliminar);
            }
            for (int i = 0; i < adyacentesDeUnVertice.size(); i++) {
                int dato = adyacentesDeUnVertice.get(i);
                if (dato > posDeVerticeAEliminar) {
                    dato--;
                    adyacentesDeUnVertice.set(i, dato);
                }
            }
        }
    }
    
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) {
        if (existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
            int posDelDestino = adyacentesDelOrigen.indexOf(posVerticeDestino);
            adyacentesDelOrigen.remove(posDelDestino);
            if (posVerticeOrigen != posDelDestino) {
                List<Integer> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
                int posDelOrigen = adyacentesDelDestino.indexOf(posVerticeOrigen);
                adyacentesDelDestino.remove(posDelOrigen);
            }
        }
    }
    
    public int cantidadDeVertices() {
        return this.listaDeAdyacencias.size();
    }
    
    public int cantidadDeAristas() {
        int cantLazos = 0;
        int cantAristas = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            List<Integer> adyacentesDelVertice = this.listaDeAdyacencias.get(i);
            for (Integer adyacente : adyacentesDelVertice) {
                if (i == adyacente) {
                    cantLazos++;
                } else {
                    cantAristas++;
                }
            }
        }
        cantAristas = (cantAristas / 2 ) + cantLazos;
        return cantAristas;
    }
    
    public Iterable<Integer> adyacentesDeVertice(int posDeVertice) {
        List<Integer> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        Iterable<Integer> adyacentes = adyacentesDelVertice;
        return adyacentes;
    }
    
    @Override
    public String toString() {
        String grafo = "";
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            grafo = grafo + "[" + i + "] --> " + this.listaDeAdyacencias.get(i).toString() + "\n";
        }
        return grafo;
    }
    
    
    // ======================= EJERCICIOS =======================
    
    // Método para determinar si un grafo es conexo
    public boolean esConexo() {
        DFS recorrido = new DFS(this, 0);
        return recorrido.hayCaminoATodosLosVertices();
    }
    
    // Método para determinar si un grafo tiene ciclos
    
    
    // Método para determinar el nro de islas en un grafo
    public int nroDeIslas() {
        DFS recorrido = new DFS(this, 0);
        int nroIslas = 1;
        while (!recorrido.hayCaminoATodosLosVertices()) {
            int noMarcado = posicionDeVerticeNoMarcado(recorrido);
            recorrido.ejecutarDFS(noMarcado);
            nroIslas++;
        }
        return nroIslas;
    }
    
    private int posicionDeVerticeNoMarcado(DFS recorrido) {
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            if (!recorrido.hayCaminoAVertice(i)) {
                return i;
            }
        }
        return -1;
    }
    
    
}