package NoPesados;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Conexo {
    
    Grafo grafo;
    boolean[] marcados;
    
    public Conexo(Grafo unGrafo) {
        this.grafo = unGrafo;
        this.marcados = new boolean[grafo.cantidadDeVertices()];
        for (int i = 0; i < marcados.length; i++) {
            this.marcados[i] = false;
        }
    }
    
    public boolean esConexo() {
//        Queue<Integer> cola = new LinkedList<>();
        Stack<Integer> pila = new Stack<>();
//        cola.offer(0);
        pila.push(0);
        marcados[0] = true;
//        while (!cola.isEmpty()) {
        while (!pila.isEmpty()) {
//            int posVerticeEnTurno = cola.poll();
            int posVerticeEnTurno = pila.pop();
            Iterable<Integer> adyacentesDelVertice = grafo.adyacentesDeVertice(posVerticeEnTurno);
            for (Integer adyacente : adyacentesDelVertice) {
                if (!marcados[adyacente]) {
//                    cola.offer(adyacente);
                    pila.push(adyacente);
                    marcados[adyacente] = true;
                }
            }
        }
        return estanTodosMarcados();
    }
    
    public boolean estanTodosMarcados() {
        for (int i = 0; i < marcados.length; i++) {
            if (!marcados[i]) {
                return false;
            }
        }
        return true;
    }
    
}
