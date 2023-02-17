package Arboles;

import Excepciones.ClaveNoExisteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class ArbolBinario<K extends Comparable<K>, V> implements IArbolBusqueda<K, V> {
    
    protected NodoBinario<K, V> raiz;

    public NodoBinario<K, V> getRaiz() {
        return raiz;
    }

    public ArbolBinario() {
    }
    
    @Override
    // Metodo para verificar si el Árbol está vacio
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }
    
    @Override
    // Metodo para vaciar un Árbol
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }
    
    @Override
    // Metodo para insertar un nodo en el Árbol
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("La clave a insertar no puede ser nula");
        }
        if (valorAInsertar == null) {
            throw new IllegalArgumentException("El valor a insertar no puede ser nulo");
        }
        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return;
        }
        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<K, V> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            if (claveAInsertar.compareTo(claveActual) < 0) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveAInsertar.compareTo(claveActual) > 0) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                nodoActual.setValor(valorAInsertar);
                return;
            }
        }
        NodoBinario<K, V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAInsertar);
        K claveDelNodoAnterior = nodoAnterior.getClave();
        if (claveAInsertar.compareTo(claveDelNodoAnterior) < 0) {
            nodoAnterior.setHijoIzquierdo(nuevoNodo);
        } else if (claveAInsertar.compareTo(claveDelNodoAnterior) > 0) { 
            nodoAnterior.setHijoDerecho(nuevoNodo);
        }
    }
    
    // Metodo para insertar un nodo en el Árbol en RECURSIVO
    public void insertarR(K claveAInsertar, V valorAInsertar) {
        this. raiz = insertarR(this.raiz, claveAInsertar, valorAInsertar);
    }
    
    private NodoBinario<K, V> insertarR(NodoBinario<K, V> nodoActual, K claveAInsertar, V valorAInsertar) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            nodoActual = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return nodoActual;
        } 
        K claveActual = nodoActual.getClave();
        if (claveAInsertar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> nodoIzquierdo = insertarR(nodoActual.getHijoIzquierdo(), claveAInsertar, valorAInsertar);
            nodoActual.setHijoIzquierdo(nodoIzquierdo);
            return nodoActual;
        } else if (claveAInsertar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> nodoDerecho = insertarR(nodoActual.getHijoDerecho(), claveAInsertar, valorAInsertar);
            nodoActual.setHijoDerecho(nodoDerecho);
            return nodoActual;
        }
        nodoActual.setValor(valorAInsertar);
        return nodoActual;
    }
    
    @Override
    // Metodo para eliminar un nodo de un Árbol en RECURSIVO
    public V eliminar(K claveAEliminar) throws ClaveNoExisteException {
        if (claveAEliminar == null) {
            throw new IllegalArgumentException("Clave a eliminar no puede ser nula");
        }
        V valorAEliminar = buscar(claveAEliminar);
        if (valorAEliminar == null) {
            throw new ClaveNoExisteException();
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorAEliminar;
    }
    
    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        K claveDelNodoActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveDelNodoActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return nodoActual;
        }
        if (claveAEliminar.compareTo(claveDelNodoActual) > 0) {
            NodoBinario<K, V> supuestoNuevoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return nodoActual;
        }

        // caso 1
        if (nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }

        // caso 2, opción a
        if (!nodoActual.esHijoIzquierdoVacio() && nodoActual.esHijoDerechoVacio()) {
            return nodoActual.getHijoIzquierdo();
        }

        // caso 2, opción b
        if (nodoActual.esHijoIzquierdoVacio() && !nodoActual.esHijoDerechoVacio()) {
            return nodoActual.getHijoDerecho();
        }

        // caso 3
        NodoBinario<K, V> nodoDelSucesor = obtenerSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K, V> supuestoNuevoHijoDerecho = this.eliminar(nodoActual.getHijoDerecho(), nodoDelSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return nodoActual;
    }
    
    protected NodoBinario<K, V> obtenerSucesor(NodoBinario<K, V> nodoEnTurno) {
        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoEnTurno)) {
            nodoAnterior = nodoEnTurno;
            nodoEnTurno = nodoEnTurno.getHijoIzquierdo();
        }
        return nodoAnterior;
    }
    
    @Override
    // Metodo para buscar la clave de un nodo en el Árbol
    public V buscar(K claveABuscar) {
        if (claveABuscar == null) {
            throw new IllegalArgumentException("La clave a buscar no puede ser nula");
        }
        NodoBinario<K, V> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            if (claveABuscar.compareTo(claveActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (claveABuscar.compareTo(claveActual) > 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                return nodoActual.getValor();
            }
        }
        return null;
    }
    
    @Override
    // Metodo para verificar si existe la clave de un nodo en el Árbol
    public boolean existe(K claveABuscar) {
        return buscar(claveABuscar) != null;
    }
    
    @Override
    // Metodo para recorrer un Árbol por niveles
    public List<String> recorridoPorNiveles() {
        List<String> recorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while (!colaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esHijoIzquierdoVacio()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esHijoDerechoVacio()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                recorrido.add("" + nodoActual.getValor() + "-" + nodoActual.getClave());
            }
        }
        return recorrido;
    }
    
    @Override
    // Metodo para recorrer un Árbol en PreOrden --> RID
    public List<K> recorridoPreOrden() {
        List<K> recorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);
            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                if (!nodoActual.esHijoDerechoVacio()) {
                    pilaDeNodos.push(nodoActual.getHijoDerecho());
                }
                if (!nodoActual.esHijoIzquierdoVacio()) {
                    pilaDeNodos.push(nodoActual.getHijoIzquierdo());
                }
                recorrido.add(nodoActual.getClave());
            }
        }
        return recorrido;
    }
    
    // Metodo para recorrer un Árbol en PreOrden en RECURSIVO --> RID
    public List<K> recorridoPreOrdenR() {
        List<K> recorrido = new ArrayList<>();
        recorridoPreOrdenR(recorrido, this.raiz);
        return recorrido;
    }
    
    private void recorridoPreOrdenR(List<K> recorrido, NodoBinario<K, V> nodoActual) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            recorrido.add(nodoActual.getClave());
            recorridoPreOrdenR(recorrido, nodoActual.getHijoIzquierdo());
            recorridoPreOrdenR(recorrido, nodoActual.getHijoDerecho());
        }
    }
    
    @Override
    // Metodo para recorrer un Árbol en InOrden --> IRD
    public List<K> recorridoInOrden() {
        List<K> recorrido = new ArrayList<>();
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        this.apilarParaInOrden(pilaDeNodos, this.raiz);
        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
            this.apilarParaInOrden(pilaDeNodos, nodoActual.getHijoDerecho());
            recorrido.add(nodoActual.getClave());
        }
        return recorrido;
    }
    
    private void apilarParaInOrden(Stack<NodoBinario<K, V>> pilaDeNodos, NodoBinario<K, V> nodoActual) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        }
    }
    
    // Metodo para recorrer un Árbol en InOrden en RECURSIVO --> IRD
    public List<K> recorridoInOrdenR() {
        List<K> recorrido = new ArrayList<>();
        recorridoInOrdenR(recorrido, this.raiz);
        return recorrido;
    }
    
    private void recorridoInOrdenR(List<K> recorrido, NodoBinario<K, V> nodoActual) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            recorridoInOrdenR(recorrido, nodoActual.getHijoIzquierdo());
            recorrido.add(nodoActual.getClave());
            recorridoInOrdenR(recorrido, nodoActual.getHijoDerecho());
        }
    }
    
    @Override
    // Metodo para recorrer un Árbol en PostOrden --> IDR
    public List<K> recorridoPostOrden() {
        List<K> recorrido = new ArrayList<>();
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        this.apilarParaPostOrden(pilaDeNodos, this.raiz);
        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
            if (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoDelTope = pilaDeNodos.peek();
                if (!nodoDelTope.esHijoDerechoVacio() && nodoDelTope.getHijoDerecho() != nodoActual) {
                    this.apilarParaPostOrden(pilaDeNodos, nodoDelTope.getHijoDerecho());
                }
            }
            recorrido.add(nodoActual.getClave());
        }
        return recorrido;
    }
    
    private void apilarParaPostOrden(Stack<NodoBinario<K, V>> pilaDeNodos, NodoBinario<K, V> nodoActual) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            if (!nodoActual.esHijoIzquierdoVacio()) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
    }
    
    // Metodo para recorrer un Árbol en PostOrden en RECURSIVO --> IDR
    public List<K> recorridoPostOrdenR() {
        List<K> recorrido = new ArrayList<>();
        recorridoPostOrdenR(recorrido, this.raiz);
        return recorrido;
    }
    
    private void recorridoPostOrdenR(List<K> recorrido, NodoBinario<K, V> nodoActual) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            recorridoPostOrdenR(recorrido, nodoActual.getHijoIzquierdo());
            recorridoPostOrdenR(recorrido, nodoActual.getHijoDerecho());
            recorrido.add(nodoActual.getClave());
        }
    }
    
    @Override
    // Metodo para obtener la altura de un Árbol
    public int altura() {
        int altura = 0;
        if (!esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while (!colaDeNodos.isEmpty()) {
                int cantidadNodosEnLaCola = colaDeNodos.size();
                int i = 0;
                while (i < cantidadNodosEnLaCola) {
                    NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                    if (!nodoActual.esHijoIzquierdoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if (!nodoActual.esHijoDerechoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoDerecho());
                    }
                    i++;
                }
                altura++;
            }
        }
        return altura;
    }
    
    // Metodo para obtener la altura de un Árbol en RECURSIVO
    public int alturaR() {
        return alturaR(this.raiz);
    }
    
    protected int alturaR(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaPorIzq = alturaR(nodoActual.getHijoIzquierdo());
        int alturaPorDer = alturaR(nodoActual.getHijoDerecho());
        return (alturaPorIzq > alturaPorDer) ? alturaPorIzq + 1: alturaPorDer + 1;
    }
    
    @Override
    // Metodo para obtener el nivel de un árbol
    public int nivel() {
        return this.altura() - 1;
    }
    
    @Override
    // Metodo para obtener el tamaño de un árbol (número de nodos de un Árbol)
    public int size() {
        int nroDeNodos = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while (!colaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                nroDeNodos++;
                if (!nodoActual.esHijoIzquierdoVacio()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esHijoDerechoVacio()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
            }
        }
        return nroDeNodos;
    }
    
    // Metodo para obtener el tamaño de un árbol RECURSIVO
    public int sizeR() {
        return sizeR(this.raiz);
    }
    
    private int sizeR(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int nroNodosPorIzq = sizeR(nodoActual.getHijoIzquierdo());
        int nroNodosPorDer = sizeR(nodoActual.getHijoDerecho());
        return nroNodosPorIzq + nroNodosPorDer + 1;
    }
    
}
