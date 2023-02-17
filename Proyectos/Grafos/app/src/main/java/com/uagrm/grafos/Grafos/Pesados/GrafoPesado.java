package com.uagrm.grafos.Grafos.Pesados;


import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaNoExiste;
import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrafoPesado {
    
    protected List<List<AdyacenteConPeso>> listaDeAdyacencias;
    
    public GrafoPesado() {
        this.listaDeAdyacencias = new ArrayList<>();
    }
    
    public GrafoPesado(int cantidadDeVertices) throws NroVerticesInvalidoExcepcion {
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
        List<AdyacenteConPeso> listaDeAdyacentesDelOrigen = this.listaDeAdyacencias.get(posDeVerticeOrigen);
        AdyacenteConPeso adyacenteConPesoDestino = new AdyacenteConPeso(posDeVerticeDestino);
        return listaDeAdyacentesDelOrigen.contains(adyacenteConPesoDestino);
    }
    
    public int cantidadDeVertices() {
        return listaDeAdyacencias.size();
    }
    
    public int cantidadDeAristas() { //
        int cantAristas = 0;
        int cantLazos = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            //List<AdyacenteConPeso> adyacentesDeUnVertice = listaDeAdyacencias.get(i);
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
        List<AdyacenteConPeso> adyacentesDelNuevoVertice = new ArrayList<>();
        this.listaDeAdyacencias.add(adyacentesDelNuevoVertice);
    }

    public void insertarArista(int posDeVerticeOrigen, int posDeVerticeDestino, double peso) {
        if (!this.existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino)) {
            List<AdyacenteConPeso> listaDeAdyacentesDelOrigen = this.listaDeAdyacencias.get(posDeVerticeOrigen);
            AdyacenteConPeso adyacenteConPesoDestino = new AdyacenteConPeso(posDeVerticeDestino, peso);
            listaDeAdyacentesDelOrigen.add(adyacenteConPesoDestino);
            Collections.sort(listaDeAdyacentesDelOrigen);
            if (posDeVerticeOrigen != posDeVerticeDestino) {
                List<AdyacenteConPeso> listaDeAdyacentesDelDestino = this.listaDeAdyacencias.get(posDeVerticeDestino);
                AdyacenteConPeso adyacenteConPesoOrigen = new AdyacenteConPeso(posDeVerticeOrigen, peso);
                listaDeAdyacentesDelDestino.add(adyacenteConPesoOrigen);
                Collections.sort(listaDeAdyacentesDelDestino);
            }
        }
    }
    
    public int gradoDeVertice(int posDelVertice) {
        validarVertice(posDelVertice);
        List<AdyacenteConPeso> listaDeAdyacentesDelVertice = this.listaDeAdyacencias.get(posDelVertice);
        return listaDeAdyacentesDelVertice.size();
    }
    
    public void eliminarVertice(int posDeVerticeAEliminar) {
        validarVertice(posDeVerticeAEliminar);
        this.listaDeAdyacencias.remove(posDeVerticeAEliminar);
        for (List<AdyacenteConPeso> adyacentesDeUnVertice : this.listaDeAdyacencias) {
            AdyacenteConPeso adyacenteConPesoAEliminar = new AdyacenteConPeso(posDeVerticeAEliminar);
            int posDeVerticeAEliminarEnAdy = adyacentesDeUnVertice.indexOf(adyacenteConPesoAEliminar);
            if (posDeVerticeAEliminarEnAdy >= 0) {
                adyacentesDeUnVertice.remove(posDeVerticeAEliminarEnAdy);
            }
            for (int i = 0; i < adyacentesDeUnVertice.size(); i++) {
                AdyacenteConPeso adyacenteConPesoEnTurno = adyacentesDeUnVertice.get(i);
                if (adyacenteConPesoEnTurno.getIndiceDeVertice() > posDeVerticeAEliminar) {
                    adyacenteConPesoEnTurno.setIndiceDeVertice(adyacenteConPesoEnTurno.getIndiceDeVertice() - 1);
                    //listaDeAdyDeUnVertice.set(i, datoDePosDeAdy);
                }
            }
        }
    }
    
    public void eliminarArista(int posDeVerticeOrigen, int posDeVerticeDestino) throws ExcepcionAristaNoExiste { //
        if (!this.existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posDeVerticeOrigen);
        AdyacenteConPeso adyacenteConPesoDestino = new AdyacenteConPeso(posDeVerticeDestino);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(adyacenteConPesoDestino);
        adyacentesDelOrigen.remove(posicionDelDestino);
        if (posDeVerticeOrigen != posDeVerticeDestino) {
            List<AdyacenteConPeso> adyacentesDelDestino = this.listaDeAdyacencias.get(posDeVerticeDestino);
            AdyacenteConPeso adyacenteConPesoOrigen = new AdyacenteConPeso(posDeVerticeOrigen);
            int posicionDelOrigen = adyacentesDelDestino.indexOf(adyacenteConPesoOrigen);
            adyacentesDelDestino.remove(posicionDelOrigen);
        }
    }
    
    public Iterable<Integer> adyacentesDeVertice(int posDeVertice) { 
        validarVertice(posDeVertice);
        List<AdyacenteConPeso> adyacenciasDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        List<Integer> listaDeSoloPosVertice = new ArrayList<>();
        for (AdyacenteConPeso unAdyacente : adyacenciasDelVertice) {
            listaDeSoloPosVertice.add(unAdyacente.getIndiceDeVertice());
        }
        Iterable<Integer> iterableDeAdyacentesDeVertice = listaDeSoloPosVertice;
        return iterableDeAdyacentesDeVertice;
    }

    public Iterable<AdyacenteConPeso> adyacentesDelVerticeConPeso(int posicionDelVertice) {
        this.validarVertice(posicionDelVertice);
        List<AdyacenteConPeso> lista = this.listaDeAdyacencias.get(posicionDelVertice);
        return lista;
    }
    
    public double peso(int posDeVerticeOrigen, int posDeVerticeDestino) throws ExcepcionAristaNoExiste {
        validarVertice(posDeVerticeOrigen);
        validarVertice(posDeVerticeDestino);
        if (!this.existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<AdyacenteConPeso> adyacentesDelVerticeOrigen = this.listaDeAdyacencias.get(posDeVerticeOrigen);
        AdyacenteConPeso adyacenteDelOrigen = new AdyacenteConPeso(posDeVerticeDestino);
        int posicionDeLaAdyacencia = adyacentesDelVerticeOrigen.indexOf(adyacenteDelOrigen);
        AdyacenteConPeso adyacenteDelOrienAlmacenado = adyacentesDelVerticeOrigen.get(posicionDeLaAdyacencia);
        return adyacenteDelOrienAlmacenado.getPeso();
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

    public void insertarArista(int posVerticeEnTurno, Integer dato) {
    }
}
