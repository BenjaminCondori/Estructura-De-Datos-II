package Utileria;

import Excepciones.ExcepcionAristaYaExiste;
import Excepciones.NroVerticesInvalidoExcepcion;
import NoPesados.Grafo;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DFS {

    private List<Boolean> marcados;
    private List<Integer> recorrido;
    private Grafo grafo;

    public DFS(Grafo unGrafo, int posVerticePartida) {
        this.grafo = unGrafo;
        this.grafo.validarVertice(posVerticePartida);
        desmarcarTodo();
        ejecutarDFS(posVerticePartida);
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

    private void ejecutarDFS(int posVertice) {
        marcarVertice(posVertice);
        recorrido.add(posVertice);
        Iterable<Integer> adyacentesEnTurno = this.grafo.adyacentesDeVertice(posVertice);
        for (Integer posVerticeAdyacente : adyacentesEnTurno) {
            if (!estaMarcado(posVerticeAdyacente)) {
                ejecutarDFS(posVerticeAdyacente);
            }
        }
    }

//    private void ejecutarDFS1(int posVertice) {
//        Stack<Integer> pila = new Stack<>();
//        pila.push(posVertice);
//        marcarVertice(posVertice);
//        while (!pila.isEmpty()) {
//            int posVerticeEnTurno = pila.pop();
//            recorrido.add(posVerticeEnTurno);
//            Iterable<Integer> posDeAdyacentes = grafo.adyacentesDeVertice(posVerticeEnTurno);
//            for (Integer adyacente : posDeAdyacentes) {
//                if (!estaMarcado(adyacente)) {
//                    pila.push(adyacente);
//                    marcarVertice(adyacente);
//                }
//            }
//        }
//    }

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

    public static void main(String[] args) throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste {
        
        Grafo grafo = new Grafo(9);

        grafo.insertarArista(0, 1);
        grafo.insertarArista(1, 4);
        grafo.insertarArista(1, 2);
        grafo.insertarArista(1, 3);
        grafo.insertarArista(2, 4);
        grafo.insertarArista(2, 5);
        grafo.insertarArista(6, 7);
        grafo.insertarArista(6, 8);
        grafo.insertarArista(7, 8);
        
        DFS recorrido = new DFS(grafo, 0);
        
        System.out.println(recorrido.getRecorrido());
        
    }
    
}
