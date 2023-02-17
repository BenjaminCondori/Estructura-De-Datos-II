package com.uagrm.grafos.Grafos.NoPesados;

import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaNoExiste;
import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;
import com.uagrm.grafos.Grafos.Utileria.RecorridoUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Grafo {

    protected List<List<Integer>> listaDeAdyacencias;

    public Grafo() {
        this.listaDeAdyacencias = new ArrayList<>();
    }

    public Grafo(int cantidadDeVertices) throws NroVerticesInvalidoExcepcion {
        if (cantidadDeVertices <= 0) {
            throw new NroVerticesInvalidoExcepcion();
        }
        this.listaDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < cantidadDeVertices; i++) {
            insertarVertice();
        }
    }

    public void validarVertice(int posDeVertice) {
        if (posDeVertice < 0 || posDeVertice >= this.cantidadDeVertices()) {
            throw new IllegalArgumentException("Vertice Inválido");
        }
    }

    public boolean existeAdyacencia(int posDeVerticeOrigen, int posDeVerticeDestino) {
        validarVertice(posDeVerticeOrigen);
        validarVertice(posDeVerticeDestino);
        List<Integer> listaDeAdyacentesDelOrigen = this.listaDeAdyacencias.get(posDeVerticeOrigen);
        return listaDeAdyacentesDelOrigen.contains(posDeVerticeDestino);
    }

    public int cantidadDeVertices() {
        return listaDeAdyacencias.size();
    }

    public int cantidadDeAristas() {
        int cantAristas = 0;
        int cantLazos = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            //List<Integer> adyacentesDeUnVertice = listaDeAdyacencias.get(i);
            Iterable<Integer> adyacentesDeUnVertice = this.adyacentesDeVertice(i);
            for (Integer posAdyacente : adyacentesDeUnVertice) {
                if (i == posAdyacente) {
                    cantLazos++;
                } else {
                    cantAristas++;
                }
            }
        }
        cantAristas = (cantAristas / 2) + cantLazos;
        return cantAristas;
    }

    public final void insertarVertice() {
        List<Integer> listaDeAdyacentesDelNuevoVertice = new ArrayList<>();
        this.listaDeAdyacencias.add(listaDeAdyacentesDelNuevoVertice);
    }

    public void insertarArista(int posDeVerticeOrigen, int posDeVerticeDestino) throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> listaDeAdyacentesDelOrigen = this.listaDeAdyacencias.get(posDeVerticeOrigen);
        listaDeAdyacentesDelOrigen.add(posDeVerticeDestino);
        Collections.sort(listaDeAdyacentesDelOrigen);
        if (posDeVerticeOrigen != posDeVerticeDestino) {
            List<Integer> listaDeAdyacentesDelDestino = this.listaDeAdyacencias.get(posDeVerticeDestino);
            listaDeAdyacentesDelDestino.add(posDeVerticeOrigen);
            Collections.sort(listaDeAdyacentesDelDestino);
        }
    }

    public void insertarArista1(int posDeVerticeOrigen, int posDeVerticeDestino) {
        if (!this.existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino)) {
            List<Integer> listaDeAdyacentesDelOrigen = this.listaDeAdyacencias.get(posDeVerticeOrigen);
            listaDeAdyacentesDelOrigen.add(posDeVerticeDestino);
            Collections.sort(listaDeAdyacentesDelOrigen);
            if (posDeVerticeOrigen != posDeVerticeDestino) {
                List<Integer> listaDeAdyacentesDelDestino = this.listaDeAdyacencias.get(posDeVerticeDestino);
                listaDeAdyacentesDelDestino.add(posDeVerticeOrigen);
                Collections.sort(listaDeAdyacentesDelDestino);
            }
        }
    }

    public int gradoDeVertice(int posDelVertice) {
        validarVertice(posDelVertice);
        List<Integer> listaDeAdyacentesDelVertice = this.listaDeAdyacencias.get(posDelVertice);
        return listaDeAdyacentesDelVertice.size();
    }

    public void eliminarVertice(int posDeVerticeAEliminar) {
        validarVertice(posDeVerticeAEliminar);
        this.listaDeAdyacencias.remove(posDeVerticeAEliminar);
        for (List<Integer> listaDeAdyDeUnVertice : this.listaDeAdyacencias) {
            int posDeVerticeAEliminarEnAdy = listaDeAdyDeUnVertice.indexOf(posDeVerticeAEliminar);
            if (posDeVerticeAEliminarEnAdy >= 0) {
                listaDeAdyDeUnVertice.remove(posDeVerticeAEliminarEnAdy);
            }
            for (int i = 0; i < listaDeAdyDeUnVertice.size(); i++) {
                int datoDePosDeAdy = listaDeAdyDeUnVertice.get(i);
                if (datoDePosDeAdy > posDeVerticeAEliminar) {
                    datoDePosDeAdy--;
                    listaDeAdyDeUnVertice.set(i, datoDePosDeAdy);
                }
            }
        }
    }

    public void eliminarArista(int posDeVerticeOrigen, int posDeVerticeDestino) {
        if (this.existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino)) {
            List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posDeVerticeOrigen);
            int posicionDelDestino = adyacentesDelOrigen.indexOf(posDeVerticeDestino);
            adyacentesDelOrigen.remove(posicionDelDestino);
            if (posDeVerticeOrigen != posDeVerticeDestino) {
                List<Integer> adyacentesDelDestino = this.listaDeAdyacencias.get(posDeVerticeDestino);
                int posicionDelOrigen = adyacentesDelDestino.indexOf(posDeVerticeOrigen);
                adyacentesDelDestino.remove(posicionDelOrigen);
            }
        }
    }

    public Iterable<Integer> adyacentesDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacenciasDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        Iterable<Integer> integerIterable = adyacenciasDelVertice;
        return integerIterable;
    }

    @Override
    public String toString() {
        String grafo = "";
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            grafo = grafo + "[" + i + "] --> " + this.listaDeAdyacencias.get(i).toString() + "\n";
        }
        grafo = grafo.substring(0, grafo.length() - 1);
        return grafo;
    }

    // ======================= EJERCICIOS =======================
    
    // Método para determinar si un grafo es conexo
    public boolean esConexo() {
        BFS recorridoBFS = new BFS(this, 0);
        return recorridoBFS.hayCaminoATodosLosVertices();
    }

    // Método para determinar si un grafo tiene ciclos
    public boolean existeCiclo() throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste {
        RecorridoUtils controlMarcados = new RecorridoUtils(this.cantidadDeVertices());
        List<Integer> recorrido = new ArrayList<>();
        boolean luzRoja = false;
        List<Integer> anteriores = new ArrayList<>();
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            anteriores.add(-1);
        }

        Grafo grafoAuxiliar = new Grafo(this.cantidadDeVertices());
        int verticeNoMarcado = 0;
        while (!controlMarcados.estanTodosMarcados() && !luzRoja) {
            luzRoja = ejecutarTieneCiclos(verticeNoMarcado,controlMarcados, recorrido, anteriores, grafoAuxiliar);
            if (!luzRoja) {
                verticeNoMarcado = buscarVertNoMarcado(controlMarcados);
            }
        }
        return luzRoja;
    }

    private int buscarVertNoMarcado(RecorridoUtils controlMarcados) {
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            boolean estaMarcado = controlMarcados.estaVerticeMarcado(i);
            if (!estaMarcado) {
                return i;
            }
        }
        return -1;
    }

    private boolean ejecutarTieneCiclos(int posVerticeDePartida,RecorridoUtils controlMarcados, 
        List<Integer> recorrido, List<Integer> anteriores, Grafo grafoAuxiliar) throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste {

        Stack<Integer> pila = new Stack<>();

        pila.push(posVerticeDePartida);
        int anteriorTurno;

        while (!pila.isEmpty()) {
            int posVerticeEnTurno = pila.pop();
            if (!controlMarcados.estaVerticeMarcado(posVerticeEnTurno)) {
                recorrido.add(posVerticeEnTurno);
            }
            anteriorTurno = anteriores.get(posVerticeEnTurno);
            if (anteriorTurno != -1) {
                if (!controlMarcados.estaVerticeMarcado(posVerticeEnTurno)) {
                    if (existeAdyacencia(anteriorTurno, posVerticeEnTurno)) {
                        grafoAuxiliar.insertarArista(anteriorTurno, posVerticeEnTurno);
                    }
                }
            }

            controlMarcados.marcarVertice(posVerticeEnTurno);

            List<Integer> listaDeAdyacentesEnTurno = this.listaDeAdyacencias.get(posVerticeEnTurno);
            int aux = listaDeAdyacentesEnTurno.size() - 1;
            for (int i = aux; i >= 0; i--) {
                int posVerticeAdy = listaDeAdyacentesEnTurno.get(i);
                if (!controlMarcados.estaVerticeMarcado(posVerticeAdy)) {
                    pila.push(posVerticeAdy);
                    anteriores.set(posVerticeAdy, posVerticeEnTurno);
                } else {
                    if (!grafoAuxiliar.existeAdyacencia(posVerticeEnTurno, posVerticeAdy)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

//    private int posicionDeVerticeNoMarcado(DFS recorridoDFS) {
//        int posicion = 0;
//        while (recorridoDFS.hayCaminoAVertice(posicion)) {
//            posicion++;
//        }
//        return posicion;
//    }

    // Método para determinar el nro de islas en un grafo
    public int nroDeIslas() {
        if (this.cantidadDeVertices() == 0) {
            return 0;
        }
        DFS recorridoDFS = new DFS(this, 0);
        int cantidadIslas = 1;
        while (!recorridoDFS.hayCaminoATodosLosVertices()) {
            int posicionDeVerticeNoMarcado = 0;
            while (recorridoDFS.hayCaminoAVertice(posicionDeVerticeNoMarcado)) {
                posicionDeVerticeNoMarcado++;
            }
            recorridoDFS.ejecutarDFS(posicionDeVerticeNoMarcado);
            cantidadIslas++;
        }
        return cantidadIslas;
    }
    
    public int nroDeIslas1() {
        DFS recorrido = new DFS(this, 0);
        int cantidadDeIslas = 1;
        while (!recorrido.hayCaminoATodosLosVertices()) {
            int noMarcado = posNoMarcado(recorrido);
            recorrido.ejecutarDFS(noMarcado);
            cantidadDeIslas++;
        }
        return cantidadDeIslas;
    }
    
    private int posNoMarcado(DFS recorrido) {
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            if (!recorrido.hayCaminoAVertice(i)) {
                return i;
            }
        }
        return -1;
    }

}
