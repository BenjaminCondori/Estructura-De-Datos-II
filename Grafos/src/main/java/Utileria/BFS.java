package Utileria;

import NoPesados.Grafo;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {
    
    private List<Boolean> marcados;
    private List<Integer> recorrido;
    private Grafo grafo;
    
    public BFS(Grafo unGrafo, int posVerticePartida )    {
        this.grafo = unGrafo;
        this.grafo.validarVertice(posVerticePartida);
        desmarcarTodo();
        ejecutarBFS(posVerticePartida);
    }
    
    private void desmarcarTodo() {
        this.marcados = new ArrayList<>();
        this.recorrido = new ArrayList<>();
        for (int i = 0; i < grafo.cantidadDeVertices(); i++) {
            this.marcados.add(Boolean.FALSE);
        }
    }
    
    private void marcarVertice(int posVertice) {
        this.marcados.set(posVertice, Boolean.TRUE);
    }
    
    private void ejecutarBFS(int posVertice) {
        Queue<Integer> cola = new LinkedList<>();
        cola.offer(posVertice);
        marcarVertice(posVertice);
        while (!cola.isEmpty()) {
            int posVerticeEnTurno = cola.poll();
            this.recorrido.add(posVerticeEnTurno);
            Iterable<Integer> adyacentesEnTurno = this.grafo.adyacentesDeVertice(posVerticeEnTurno);
            for (Integer posVerticeAdyacente : adyacentesEnTurno) {
                if (!estaMarcado(posVerticeAdyacente)) {
                    cola.offer(posVerticeAdyacente);
                    marcarVertice(posVerticeAdyacente);
                }
            }
        }
    }
    
    private boolean estanTodosMarcados() {
        for (Boolean marcado : this.marcados) {
            if (!marcado) {
                return false;
            }
        }
        return true;
    }
    
    private boolean estaMarcado(int posVertice) {
        return this.marcados.get(posVertice);
    }
    
    public boolean hayCaminoAVertice(int posVertice) {
        this.grafo.validarVertice(posVertice);
        return estaMarcado(posVertice);
    }
    
    public boolean hayCaminoATodosLosVertices() {
        return estanTodosMarcados();
    }
    
    public Iterable<Integer> getRecorrido() {
        return this.recorrido;
    }
    
}
