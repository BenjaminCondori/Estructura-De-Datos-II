package Pesados;

import Excepciones.ExcepcionAristaYaExiste;
import Excepciones.NroVerticesInvalidoExcepcion;
import java.util.ArrayList;
import java.util.List;

public class Prim {

    private GrafoPesado miGrafo;
    private boolean[] marcados;

    public Prim(GrafoPesado unGrafo) {
        this.miGrafo = unGrafo;
        marcados = new boolean[miGrafo.cantidadDeVertices()];
        for (int i = 0; i < marcados.length; i++) {
            marcados[i] = false;
        }
    }

    public GrafoPesado arbol() throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste  {
        int n = this.miGrafo.cantidadDeVertices();
        GrafoPesado arbol = new GrafoPesado(n);
        marcados[0] = true;
        while (!estanTodosMarcados()) {
            double menorPeso = Double.POSITIVE_INFINITY;
            int origen = 0;
            int destino = 0;
            List<Integer> listaDeMarcados = listaDeElementosMarcados();
            for (Integer elemento : listaDeMarcados) {
                Iterable<AdyacenteConPeso> adyacentes = miGrafo.adyacentesDelVerticeConPeso(elemento);
                for (AdyacenteConPeso adya : adyacentes) {
                    if (!marcados[adya.getIndiceDeVertice()] && adya.getPeso() < menorPeso) {
                        origen = elemento;
                        destino = adya.getIndiceDeVertice();
                        menorPeso = adya.getPeso();
                    }
                }
            }
            arbol.insertarArista(origen, destino, menorPeso);
            marcados[destino] = true;
        }
        return arbol;
    }

    private List<Integer> listaDeElementosMarcados() {
        List<Integer> lista = new ArrayList<>();
        for (int i = 0; i < marcados.length; i++) {
            if (marcados[i]) {
                lista.add(i);
            }
        }
        return lista;
    }

    private boolean estanTodosMarcados() {
        for (int i = 0; i < this.marcados.length; i++) {
            if (!marcados[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste  {
        
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
        
//        GrafoPesado grafo = new DigrafoPesado(11);
//
//            grafo.insertarArista(0,1,5);
//            grafo.insertarArista(0,2,10);
//            grafo.insertarArista(0,3,3);
//            grafo.insertarArista(1,5,25);
//            grafo.insertarArista(2,1,2);
//            grafo.insertarArista(2,3,15);
//            grafo.insertarArista(3,5,30);
//            grafo.insertarArista(4,1,15);
//            grafo.insertarArista(4,5,4);
//            grafo.insertarArista(4,7,3);
//            grafo.insertarArista(4,8,15);
//            grafo.insertarArista(5,6,17);
//            grafo.insertarArista(6,3,12);
//            grafo.insertarArista(6,8,9);
//            grafo.insertarArista(6,9,8);
//            grafo.insertarArista(7,8,50);
//            grafo.insertarArista(7,10,6);
//            grafo.insertarArista(8,9,23);
//            grafo.insertarArista(8,10,8);
//            grafo.insertarArista(9,10,2);
        
        Prim p = new Prim(grafo);
        
        GrafoPesado aux = p.arbol();
        System.out.println(aux.toString());
        
    }
    
}
