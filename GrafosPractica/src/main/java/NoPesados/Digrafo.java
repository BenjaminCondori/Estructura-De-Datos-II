package NoPesados;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Digrafo extends Grafo {

    List<List<Integer>> listaDeAdyacencias;

    public Digrafo() {
        this.listaDeAdyacencias = new ArrayList<>();
    }

    public Digrafo(int nroDeVertices) {
        if (nroDeVertices <= 0) {
            throw new IllegalArgumentException("Nro de vertices no válido");
        }
        this.listaDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < nroDeVertices; i++) {
            insertarVertice();
        }
    }

    @Override
    public void validarVertice(int posDeVertice) {
        if (posDeVertice < 0 || posDeVertice >= this.cantidadDeVertices()) {
            throw new IllegalArgumentException("Vertice inválido");
        }
    }

    @Override
    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino) {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        return adyacentesDelOrigen.contains(posVerticeDestino);
    }

    @Override
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) {
        if (!existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
            adyacentesDelOrigen.add(posVerticeDestino);
            Collections.sort(adyacentesDelOrigen);
        }
    }

    @Override
    public void eliminarVertice(int posDeVerticeAEliminar) {
        validarVertice(posDeVerticeAEliminar);
        this.listaDeAdyacencias.remove(posDeVerticeAEliminar);
        for (List<Integer> adyacentesDeUnVertice : this.listaDeAdyacencias) {
            int posVerticeEliminar = adyacentesDeUnVertice.indexOf(posDeVerticeAEliminar);
            if (posVerticeEliminar >= 0) {
                adyacentesDeUnVertice.remove(posVerticeEliminar);
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

    @Override
    public void eliminarArista(int posVerticeOrigen, int posVeticeDestino) {
        if (existeAdyacencia(posVerticeOrigen, posVeticeDestino)) {
            List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
            int posAEliminar = adyacentesDelOrigen.indexOf(posVeticeDestino);
            adyacentesDelOrigen.remove(posAEliminar);
        }
    }

    @Override
    public int cantidadDeVertices() {
        return this.listaDeAdyacencias.size();
    }

    @Override
    public int cantidadDeAristas() {
        int cantAristas = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            cantAristas = cantAristas + this.listaDeAdyacencias.get(i).size();
        }
        return cantAristas;
    }

    public int gradoDeSalida(int posVertice) {
        validarVertice(posVertice);
        List<Integer> adyacentesDelVertice = this.listaDeAdyacencias.get(posVertice);
        return adyacentesDelVertice.size();
    }

    public int gradoDeEntrada(int posVertice) {
        validarVertice(posVertice);
        int entradasDeVertice = 0;
        for (List<Integer> adyacentesDeUnVertice : this.listaDeAdyacencias) {
            for (Integer adyacente : adyacentesDeUnVertice) {
                if (adyacente == posVertice) {
                    entradasDeVertice++;
                }
            }
        }
        return entradasDeVertice;
    }

    @Override
    public Iterable<Integer> adyacentesDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        Iterable<Integer> adyacentes = adyacentesDelVertice;
        return adyacentes;
    }

    // ======================= EJERCICIOS =======================
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

}
