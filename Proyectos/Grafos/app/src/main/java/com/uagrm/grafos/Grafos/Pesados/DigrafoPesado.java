package com.uagrm.grafos.Grafos.Pesados;


import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaNoExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;

import java.util.Collections;
import java.util.List;

public class DigrafoPesado extends GrafoPesado {

    public DigrafoPesado() {
        super();
    }

    public DigrafoPesado(int cantidadDeVertices) throws NroVerticesInvalidoExcepcion {
        super(cantidadDeVertices);
    }

    @Override
    public void insertarArista(int posDeVerticeOrigen, int posDeVerticeDestino, double peso) {
        if (!this.existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino)) {
            List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posDeVerticeOrigen);
            AdyacenteConPeso adyacenteConPesoDestino = new AdyacenteConPeso(posDeVerticeDestino, peso);
            adyacentesDelOrigen.add(adyacenteConPesoDestino);
            Collections.sort(adyacentesDelOrigen);
        }
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
        for (int i = 0; i < listaDeAdyacencias.size(); i++) {
            Iterable<Integer> adyacentesDeUnVertice = adyacentesDeVertice(i);
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
    public void eliminarArista(int posDeVerticeOrigen, int posDeVerticeDestino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(posDeVerticeOrigen, posDeVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listaDeAdyacencias.get(posDeVerticeOrigen);
        AdyacenteConPeso adyacenteConPesoDestino = new AdyacenteConPeso(posDeVerticeDestino);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(adyacenteConPesoDestino);
        adyacentesDelOrigen.remove(posicionDelDestino);
    }

}
