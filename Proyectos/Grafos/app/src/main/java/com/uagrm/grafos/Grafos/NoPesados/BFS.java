package com.uagrm.grafos.Grafos.NoPesados;


import com.uagrm.grafos.Grafos.Utileria.RecorridoUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {
    
    private RecorridoUtils controlMarcados;
    private Grafo grafo;
    private List<Integer> recorrido;
    
    public BFS(Grafo unGrafo, int posDeVerticeDePartida) {
        this.grafo = unGrafo;
        this.grafo.validarVertice(posDeVerticeDePartida);
        this.recorrido = new ArrayList<>();
        this.controlMarcados = new RecorridoUtils(this.grafo.cantidadDeVertices());
        ejecutarBFS(posDeVerticeDePartida);
    }
    
    public void ejecutarBFS(int posDeVerticeDePartida) {
        Queue<Integer> cola = new LinkedList<>();
        cola.offer(posDeVerticeDePartida);
        controlMarcados.marcarVertice(posDeVerticeDePartida);
        while (!cola.isEmpty()) {
            int posVerticeEnTurno = cola.poll();
            this.recorrido.add(posVerticeEnTurno);
            Iterable<Integer> adyacentesEnTurno = this.grafo.adyacentesDeVertice(posVerticeEnTurno);
            for (Integer posVerticeAdyacente : adyacentesEnTurno) {
                if (!controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                    cola.offer(posVerticeAdyacente);
                    controlMarcados.marcarVertice(posVerticeAdyacente);
                }
            }
        }
    }
    
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
