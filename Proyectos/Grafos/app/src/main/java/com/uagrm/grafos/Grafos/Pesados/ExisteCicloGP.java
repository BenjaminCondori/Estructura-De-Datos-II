package com.uagrm.grafos.Grafos.Pesados;

import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;

import java.util.Stack;

public class ExisteCicloGP {

    private GrafoPesado grafo;
    private boolean[] marcados;

    public ExisteCicloGP(GrafoPesado unGrafo) {
        this.grafo = unGrafo;
        this.marcados = new boolean[grafo.cantidadDeVertices()];
        for (int i = 0; i < marcados.length; i++) {
            marcados[i] = false;
        }
    }

    public boolean existeCiclo() throws NroVerticesInvalidoExcepcion {
        GrafoPesado grafoAux = new GrafoPesado(this.grafo.cantidadDeVertices());
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

        GrafoPesado grafo = new GrafoPesado(10);

        grafo.insertarArista(4, 7, 3);
        grafo.insertarArista(4, 6, 4);
        grafo.insertarArista(6, 8, 4);
        grafo.insertarArista(4, 3, 5);
        grafo.insertarArista(5, 1, 5);
        grafo.insertarArista(0, 1, 5);
        grafo.insertarArista(1, 3, 6);
        grafo.insertarArista(6, 9, 6);
        grafo.insertarArista(2, 3, 7);
        grafo.insertarArista(8, 9, 7);
        grafo.insertarArista(5, 8, 7);
        grafo.insertarArista(2, 4, 8);
        grafo.insertarArista(0, 3, 8);
        grafo.insertarArista(5, 6, 9);
        grafo.insertarArista(0, 2, 10);
        grafo.insertarArista(3, 5, 11);
        grafo.insertarArista(6, 7, 12);
        grafo.insertarArista(7, 9, 12);
        grafo.insertarArista(2, 7, 15);
        

        ExisteCicloGP g = new ExisteCicloGP(grafo);

        System.out.println(grafo.toString());
        System.out.println("Existe ciclo: " + g.existeCiclo());

    }

}
