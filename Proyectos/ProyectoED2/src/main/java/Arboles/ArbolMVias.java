package Arboles;

import Excepciones.ClaveNoExisteException;
import Excepciones.ExcepcionOrdenInvalido;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArbolMVias<K extends Comparable<K>, V> implements IArbolBusqueda<K, V> {

    protected NodoMVias<K, V> raiz;
    protected int orden;
    protected int POSICION_INVALIDA = -1;
    private static final int ORDEN_MINIMO = 3;

    public ArbolMVias() {
        this.orden = ORDEN_MINIMO;
    }

    public ArbolMVias(int orden) throws ExcepcionOrdenInvalido {
        if (orden < ORDEN_MINIMO) {
            throw new ExcepcionOrdenInvalido();
        }
        this.orden = orden;
    }

    @Override
    // Metodo para vaciar un Árbol
    public void vaciar() {
        this.raiz = NodoMVias.nodoVacio();
    }

    @Override
    // Metodo para verificar si el Árbol está vacio
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    // Metodo para buscar la clave de un nodo en el Árbol
    public V buscar(K claveABuscar) {
        if (claveABuscar == NodoMVias.datoVacio()) {
            throw new IllegalArgumentException("La clave a buscar no puede ser nula.");
        }
        NodoMVias<K, V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            boolean huboCambioDeNodoActual = false;
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias() && !huboCambioDeNodoActual; i++) {

                K claveActual = nodoActual.getClave(i);
                if (claveABuscar.compareTo(claveActual) == 0) {
                    return nodoActual.getValor(i);
                } else if (claveABuscar.compareTo(claveActual) < 0) {
                    if (!nodoActual.esHijoVacio(i)) {
                        nodoActual = nodoActual.getHijo(i);
                        huboCambioDeNodoActual = true;
                    }
                }
            }
            if (!huboCambioDeNodoActual) {
                nodoActual = nodoActual.getHijo(nodoActual.nroDeClavesNoVacias());
            }
        }
        return (V)NodoMVias.datoVacio();
    }

    @Override
    // Metodo para verificar si existe la clave de un nodo en el Árbol
    public boolean existe(K clave) {
        return this.buscar(clave) != NodoMVias.datoVacio();
    }

    @Override
    // Metodo para insertar un nodo en el Árbol
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (this.esArbolVacio()) {
            this.raiz = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
            return;
        }
        NodoMVias<K, V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            int posicionDeClaveAInsertar = this.obtenerPosicionDeClave(nodoActual, claveAInsertar);
            if (posicionDeClaveAInsertar != POSICION_INVALIDA) {
                nodoActual.setValor(posicionDeClaveAInsertar, valorAInsertar);
                nodoActual = NodoMVias.nodoVacio();
            } else if (nodoActual.esHoja()) {
                if (nodoActual.estanClavesLlenas()) {
                    int posicionPorDondeBajar = this.obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                    NodoMVias<K, V> nuevoHijo = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
                    nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                } else {
                    this.insertarDatosOrdenadoEnNodo(nodoActual, claveAInsertar, valorAInsertar);
                }
                nodoActual = NodoMVias.nodoVacio();
            } else {
                // En caso de que el nodo actual no sea hoja
                int posicionPorDondeBajar = this.obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                if (nodoActual.esHijoVacio(posicionPorDondeBajar)) {
                    NodoMVias<K, V> nuevoHijo = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
                    nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                    nodoActual = NodoMVias.nodoVacio();
                } else {
                    nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
                }
            }
        }
    }

    // Metodo para obtener la posición de una clave en un nodo
    protected int obtenerPosicionDeClave(NodoMVias<K, V> nodoActual, K claveABuscar) {
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if (claveABuscar.compareTo(claveActual) == 0) {
                return i;
            }
        }
        return POSICION_INVALIDA;
    }

    // Metodo para obtener la posición para bajar a un hijo
    protected int obtenerPosicionPorDondeBajar(NodoMVias<K, V> nodoActual, K claveABuscar) {
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if (claveABuscar.compareTo(claveActual) < 0) {
                return i;
            }
        }
        return nodoActual.nroDeClavesNoVacias();
    }

    // Metodo para insertar los datos de manera ordenada en un nodo
    protected void insertarDatosOrdenadoEnNodo(NodoMVias<K, V> nodoActual, K claveAInsertar, V valorAInsertar) {
        int posicion = obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
        K clave = claveAInsertar;
        V valor = valorAInsertar;
        for (int i = posicion; i < orden - 1; i++) {
            K auxClave = nodoActual.getClave(i);
            V auxValor = nodoActual.getValor(i);
            nodoActual.setClave(i, clave);
            nodoActual.setValor(i, valor);
            clave = auxClave;
            valor = auxValor;
        }
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

    private NodoMVias<K, V> eliminar(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if (claveAEliminar.compareTo(claveActual) == 0) {
                if (nodoActual.esHoja()) { // Caso 1
                    this.eliminarDatosDeNodo(nodoActual, i);
                    if (nodoActual.nroDeClavesNoVacias() == 0) {
                        return NodoMVias.nodoVacio();
                    }
                    return nodoActual;
                }
                // Si llego acá la clave esta en un nodo no hoja
                // Caso 2 o 3
                K claveDeReemplazo;
                if (this.hayHijosMasAdelante(nodoActual, i)) {
                    // Caso 2
                    claveDeReemplazo = this.buscarClaveSucesoraInOrden(nodoActual, claveAEliminar);
                } else {
                    // Caso 3
                    claveDeReemplazo = this.buscarClavePredecesoraInOrden(nodoActual, claveAEliminar);
                }
                V valorDeReemplazo = this.buscar(claveDeReemplazo);
                nodoActual = eliminar(nodoActual, claveDeReemplazo);
                nodoActual.setClave(i, claveDeReemplazo);
                nodoActual.setValor(i, valorDeReemplazo);
                return nodoActual;
            }
            if (claveAEliminar.compareTo(claveActual) < 0) {
                NodoMVias<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijo(i), claveAEliminar);
                nodoActual.setHijo(i, supuestoNuevoHijo);
                return nodoActual;
            }
        }
        NodoMVias<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), claveAEliminar);
        nodoActual.setHijo(nodoActual.nroDeClavesNoVacias(), supuestoNuevoHijo);
        return nodoActual;
    }

    // Metodo para eliminar los datos de un nodo
    protected void eliminarDatosDeNodo(NodoMVias<K, V> nodoActual, int posicion) {
        for (int i = posicion; i < orden - 2; i++) {
            K clave = nodoActual.getClave(i + 1);
            V valor = nodoActual.getValor(i + 1);
            nodoActual.setClave(i, clave);
            nodoActual.setValor(i, valor);
        }
        nodoActual.setClave(orden - 2, (K) NodoMVias.datoVacio());
        nodoActual.setValor(orden - 2, (V) NodoMVias.datoVacio());
    }

    // Metodo para verificar si hay hijos mas adelante de una posición indicada
    protected boolean hayHijosMasAdelante(NodoMVias<K, V> nodoActual, int i) {
        for (int j = i; j < orden - 1; j++) {
            if (nodoActual.getHijo(j + 1) != NodoMVias.nodoVacio()) {
                return true;
            }
        }
        return false;
    }

    // Metodo para buscar una clave sucesora
    public K buscarClaveSucesoraInOrden(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        int posicion = obtenerPosicionDeClave(nodoActual, claveAEliminar);
        if (!nodoActual.esHijoVacio(posicion + 1)) {
            NodoMVias<K, V> nodoHijo = nodoActual.getHijo(posicion + 1);
            return nodoHijo.getClave(0);
        } 
        return nodoActual.getClave(posicion + 1);
    }

    // Metodo para buscar una clave predecesora
    public K buscarClavePredecesoraInOrden(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        int posicion = obtenerPosicionDeClave(nodoActual, claveAEliminar);
        if (!nodoActual.esHijoVacio(posicion)) {
            NodoMVias<K, V> nodoHijo = nodoActual.getHijo(posicion);
            for (int i = 0; i < nodoHijo.nroDeClavesNoVacias(); i++) {
                if (nodoHijo.getClave(i) != NodoMVias.datoVacio()) {
                    posicion = i;
                }
            }
            return nodoHijo.getClave(posicion);
        }
        return nodoActual.getClave(posicion - 1);
    }

    @Override
    // Metodo para obtener el tamaño de un árbol (número de nodos de un Árbol)
    public int size() {
        int cantidad = 0;
        if (!esArbolVacio()) {
            Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while (!colaDeNodos.isEmpty()) {
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();
                cantidad++;

                for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                    if (!nodoActual.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }

                if (!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())) {
                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
                }
            }
        }
        return cantidad;
    }

    @Override
    // Metodo para obtener la altura de un Árbol en RECURSIVO
    public int altura() {
        return altura(this.raiz);
    }

    protected int altura(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaMayor = 0;
        for (int i = 0; i < orden; i++) {
            int alturaDeHijo = altura(nodoActual.getHijo(i));
            if (alturaDeHijo > alturaMayor) {
                alturaMayor = alturaDeHijo;
            }
        }
        return alturaMayor + 1;
    }

    @Override
    // Metodo para obtener el nivel de un árbol
    public int nivel() {
        return this.altura() - 1;
    }

    @Override
    // Metodo para recorrer un Árbol en InOrden en RECURSIVO --> IRD
    public List<K> recorridoInOrden() {
        List<K> recorrido = new ArrayList();
        recorridoInOrden(recorrido, this.raiz);
        return recorrido;
    }

    private void recorridoInOrden(List<K> recorrido, NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            recorridoInOrden(recorrido, nodoActual.getHijo(i));
            recorrido.add(nodoActual.getClave(i));
        }
        recorridoInOrden(recorrido, nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
    }

    @Override
    // Metodo para recorrer un Árbol en PreOrden en RECURSIVO --> RID
    public List<K> recorridoPreOrden() {
        List<K> recorrido = new ArrayList();
        recorridoPreOrden(recorrido, this.raiz);
        return recorrido;
    }

    private void recorridoPreOrden(List<K> recorrido, NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            recorrido.add(nodoActual.getClave(i));
            recorridoPreOrden(recorrido, nodoActual.getHijo(i));
        }
        recorridoPreOrden(recorrido, nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
    }

    @Override
    // Metodo para recorrer un Árbol en PostOrden en RECURSIVO --> IDR
    public List<K> recorridoPostOrden() {
        List<K> recorrido = new ArrayList();
        recorridoPostOrden(recorrido, this.raiz);
        return recorrido;
    }

    private void recorridoPostOrden(List<K> recorrido, NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        recorridoPostOrden(recorrido, nodoActual.getHijo(0));
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            recorridoPostOrden(recorrido, nodoActual.getHijo(i + 1));
            recorrido.add(nodoActual.getClave(i));
        }
    }

    // Metodo para recorrer un Árbol por niveles
    public List<K> recorridoPorNiveles1() {
        List<K> recorrido = new ArrayList<>();
        if (!esArbolVacio()) {
            Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while (!colaDeNodos.isEmpty()) {
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();

                for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                    recorrido.add(nodoActual.getClave(i));
                    if (!nodoActual.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }

                if (!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())) {
                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
                }
            }
        }
        return recorrido;
    }
    
    @Override
    public List<String> recorridoPorNiveles() {
        List<String> recorrido = new ArrayList<>();
        if (!esArbolVacio()) {
            Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while (!colaDeNodos.isEmpty()) {
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();

                for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                    recorrido.add("" + nodoActual.getValor(i) + "-" + nodoActual.getClave(i));
                    if (!nodoActual.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }

                if (!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())) {
                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
                }
            }
        }
        return recorrido;
    }

    // Metodo que devuelve la clave mas menor del Árbol
    public K minimo() {
        if (this.esArbolVacio()) {
            return null;
        }
        NodoMVias<K, V> nodoActual = this.raiz;
        NodoMVias<K, V> nodoAnterior = NodoMVias.nodoVacio();
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijo(0);
        }
        return nodoAnterior.getClave(0);
    }

}
