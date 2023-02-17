package com.uagrm.grafos.Grafos.NoPesados;


import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;

import java.util.Stack;

public class ExisteCicloEnGrafo {

    private Grafo grafo;
    private boolean[] marcados;

    public ExisteCicloEnGrafo(Grafo unGrafo) {
        this.grafo = unGrafo;
        this.marcados = new boolean[grafo.cantidadDeVertices()];
        for (int i = 0; i < marcados.length; i++) {
            marcados[i] = false;
        }
    }

    public boolean existeCiclo() throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste {
        Grafo grafoAux = new Grafo(this.grafo.cantidadDeVertices());
        Stack<Integer> pila = new Stack<>();
        int noMarcado = 0;
        pila.push(noMarcado);
        marcados[noMarcado] = true;
        while (!estanTodosMarcados()) {
            while (!pila.isEmpty()) {
                int posVerticeEnTurno = pila.pop();
                Iterable<Integer> adyacentesDelVertice = grafo.adyacentesDeVertice(posVerticeEnTurno);
                for (Integer dato : adyacentesDelVertice) {
                    if (!marcados[dato]) {
                        pila.push(dato);
                        marcados[dato] = true;
                        grafoAux.insertarArista(posVerticeEnTurno, dato);
                    } else {
                        if (!grafoAux.existeAdyacencia(posVerticeEnTurno, dato)) {
                            return true;
                        }
                    }
                }
            }
            noMarcado = posicionDeVerticeNoMarcado();
            if (noMarcado != -1) {
                pila.push(noMarcado);
                marcados[noMarcado] = true;
            }
        }
        return false;
    }
    
    public int posicionDeVerticeNoMarcado() {
        for (int i = 0; i < marcados.length; i++) {
            if (!marcados[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean estanTodosMarcados() {
        for (int i = 0; i < marcados.length; i++) {
            if (!marcados[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste {

        Grafo grafo = new Grafo(13);

        grafo.insertarArista(0, 1);
//        grafo.insertarArista(0, 2);
        grafo.insertarArista(1, 2);
        grafo.insertarArista(1, 3);
        grafo.insertarArista(2, 4);
        grafo.insertarArista(2, 5);
        grafo.insertarArista(6, 7);
//        grafo.insertarArista(6, 8);
        grafo.insertarArista(7, 8);
        
        grafo.insertarArista(9, 10);
        grafo.insertarArista(9, 12);
        grafo.insertarArista(10, 11);
//        grafo.insertarArista(11, 12);
        

        ExisteCicloEnGrafo g = new ExisteCicloEnGrafo(grafo);

        System.out.println(grafo.toString());
        System.out.println("Existe ciclo: " + g.existeCiclo());

    }

}
