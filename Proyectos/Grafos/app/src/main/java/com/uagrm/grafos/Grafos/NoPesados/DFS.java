package com.uagrm.grafos.Grafos.NoPesados;


import com.uagrm.grafos.Grafos.Utileria.RecorridoUtils;

import java.util.ArrayList;
import java.util.List;

public class DFS {
    
    private RecorridoUtils controlMarcados;
    private Grafo grafo;
    private List<Integer> recorrido;
    
    public DFS(Grafo unGrafo, int posDeVerticeDePartida) {
        this.grafo = unGrafo;
        this.grafo.validarVertice(posDeVerticeDePartida);
        this.recorrido = new ArrayList<>();
        this.controlMarcados = new RecorridoUtils(this.grafo.cantidadDeVertices());
        ejecutarDFS(posDeVerticeDePartida);
    }
    
    public void ejecutarDFS(int posDeVerticeDePartida) {
        controlMarcados.marcarVertice(posDeVerticeDePartida);
        recorrido.add(posDeVerticeDePartida);
        Iterable<Integer> adyacentesEnTurno = this.grafo.adyacentesDeVertice(posDeVerticeDePartida);
        for (Integer posVerticeAdyacente : adyacentesEnTurno) {
            if (!controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                ejecutarDFS(posVerticeAdyacente);
            }
        }
    }
    
//    public boolean hayCiclo(int posDeVerticeDePartida, Grafo unGrafo) {
//        
//        Iterable<Integer> adyacentesEnTurno = this.grafo.adyacentesDeVertice(posDeVerticeDePartida);
//        for (Integer posVerticeAdyacente : adyacentesEnTurno) {
//            if (!controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
//                unGrafo.insertarArista1(posDeVerticeDePartida, posVerticeAdyacente);
//                ejecutarDFS(posVerticeAdyacente);
//            } else {
//                if (!unGrafo.existeAdyacencia(posDeVerticeDePartida, posVerticeAdyacente)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
    
    public Iterable<Integer> getRecorrido() {
        return this.recorrido;
    }
    
    public boolean hayCaminoAVertice(int posDeVerticeDestino) {
        this.grafo.validarVertice(posDeVerticeDestino);
        return controlMarcados.estaVerticeMarcado(posDeVerticeDestino);
    }
    
    public boolean hayCaminoATodosLosVertices() {
        return controlMarcados.estanTodosMarcados();
    }
    
}
