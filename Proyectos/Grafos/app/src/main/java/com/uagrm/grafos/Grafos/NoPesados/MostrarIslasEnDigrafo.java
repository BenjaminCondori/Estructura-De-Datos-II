package com.uagrm.grafos.Grafos.NoPesados;
import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MostrarIslasEnDigrafo {

    private boolean[] marcados;
    private Digrafo digrafo;

    public MostrarIslasEnDigrafo(Digrafo unDigrafo) {
        this.digrafo = unDigrafo;
        int n = digrafo.cantidadDeVertices();
        this.marcados = new boolean[n];
        for (int i = 0; i < marcados.length; i++) {
            this.marcados[i] = false;
        }
    }

    public String obtenerIslas() {
        List<List<Integer>> islas = new ArrayList<>();
        List<Integer> isla = new ArrayList<>();
        Queue<Integer> cola = new LinkedList<>();
        cola.offer(0);
        marcados[0] = true;
        while (!this.estanTodosMarcados()) {
            while (!cola.isEmpty()) {
                int posVerticeEnTurno = cola.poll();
                marcados[posVerticeEnTurno] = true;
                isla.add(posVerticeEnTurno);
                Iterable<Integer> posDeAdyacentes = digrafo.adyacentesDeVertice(posVerticeEnTurno);
                for (Integer posDeAdyacente : posDeAdyacentes) {
                    if (!marcados[posDeAdyacente]) {
                        cola.offer(posDeAdyacente);
                        marcados[posDeAdyacente] = true;
                    }
                }
            }
            int posNoMarcado = this.posicionDeNoMarcadoConAdyacenteMarcado();
            if (posNoMarcado != -1) {
                cola.offer(posNoMarcado);
            } else {
//                islas.add(isla);
//                if (!this.estanTodosMarcados()) {
                    posNoMarcado = this.posicionDeNoMarcado();
//                    if (posNoMarcado != -1) {
                        cola.offer(posNoMarcado);
                        islas.add(isla);
                        isla = new ArrayList<>();
//                    }
//                }
            }
        }
        String cadena = "";
        for (int i = 0; i < islas.size(); i++) {
            cadena = cadena + "Isla " + (i + 1) + " con Vertices: " + islas.get(i) + "\n";
        }
        return cadena;
    }
    
    public boolean estanTodosMarcados() {
        for (int i = 0; i < marcados.length; i++) {
            if (!marcados[i]) {
                return false;
            }
        }
        return true;
    }

    public int posicionDeNoMarcadoConAdyacenteMarcado() {
        for (int i = 0; i < marcados.length; i++) {
            if (!marcados[i]) {
                Iterable<Integer> posAdyacentes = digrafo.adyacentesDeVertice(i);
                for (int adyacente : posAdyacentes) {
                    if (marcados[adyacente]) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public int posicionDeNoMarcado() {
        for (int i = 0; i < marcados.length; i++) {
            if (!marcados[i]) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste {

        Digrafo digrafo = new Digrafo(12);

        digrafo.insertarArista(0, 0);
        digrafo.insertarArista(0, 4);
        digrafo.insertarArista(1, 0);
        digrafo.insertarArista(1, 2);
        digrafo.insertarArista(1, 6);
        digrafo.insertarArista(2, 5);
        digrafo.insertarArista(3, 6);
        digrafo.insertarArista(4, 7);
        digrafo.insertarArista(5, 2);
        digrafo.insertarArista(5, 4);
        digrafo.insertarArista(6, 1);
        digrafo.insertarArista(6, 7);
        digrafo.insertarArista(7, 3);
        digrafo.insertarArista(8, 9);
        digrafo.insertarArista(8, 10);
        digrafo.insertarArista(10, 9);

        MostrarIslasEnDigrafo islas = new MostrarIslasEnDigrafo(digrafo);

//        System.out.println(digrafo.toString());
        System.out.println(islas.obtenerIslas());

    }

}
