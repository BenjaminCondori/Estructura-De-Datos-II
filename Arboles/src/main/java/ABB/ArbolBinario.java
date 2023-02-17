package ABB;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class ArbolBinario<K extends Comparable<K>, V> {
    
    protected NodoBinario<K, V> raiz;

    public ArbolBinario() {
    }
    
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }
    
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }
    
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
    
    // Metodo para eliminar un nodo del Árbol
    public V eliminar(K claveAEliminar) {
        return null;
    }
    
    // Metodo para recorrer un Árbol por niveles
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList();
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
                recorrido.add(nodoActual.getClave());
            }
        }
        return recorrido;
    }
    
    // Metodo para recorrer un Árbol en PreOrden --> RID
    public List<K> recorridoPreOrden() {
        List<K> recorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            pilaDeNodos.add(this.raiz);
            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                if (!nodoActual.esHijoDerechoVacio()) {
                    pilaDeNodos.add(nodoActual.getHijoDerecho());
                }
                if (!nodoActual.esHijoIzquierdoVacio()) {
                    pilaDeNodos.add(nodoActual.getHijoIzquierdo());
                }
                recorrido.add(nodoActual.getClave());
            }
        }
        return recorrido;
    }
    
    // Metodo para recorrer un Árbol en InOrden --> IRD
    public List<K> recorridoInOrden() {
        List<K> recorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            apilarIzq(pilaDeNodos, this.raiz);
            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                apilarIzq(pilaDeNodos, nodoActual.getHijoDerecho());
                recorrido.add(nodoActual.getClave());
            }
        }
        return recorrido;
    }
    
    private void apilarIzq(Stack<NodoBinario<K, V>> pilaDeNodos, NodoBinario<K, V> nodoActual) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.add(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        }
    }
    
    // Metodo para recorrer un Árbol en PostOrden --> IDR
    public List<K> recorridoPostOrden() {
        List<K> recorrido = new ArrayList<>();
        
        return recorrido;
    }
    
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
    
    // Metodo para verificar si existe la clave de un nodo en el Árbol
    public boolean existe(K claveABuscar) {
        return buscar(claveABuscar) != null;
    }
    
    
    
}
