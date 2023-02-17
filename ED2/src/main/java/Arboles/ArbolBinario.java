package Arboles;

import Excepciones.ClaveNoExisteException;
import Presentacion.ArbolExpresionGrafico;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JPanel;

public class ArbolBinario<K extends Comparable<K>, V> implements IArbolBinario<K, V> {

    private NodoBinario<K, V> raiz;

    public ArbolBinario() {
    }

    public NodoBinario<K, V> getRaiz() {
        return raiz;
    }

    @Override
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
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
    public boolean existe(K claveABuscar) {
        return buscar(claveABuscar) != null;
    }

    // ================================== INSERTAR =================================
    @Override
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

    // ================================== INSERTAR =================================
    public void insertarR(K claveAInsertar, V valorAInsertar) {
        this.raiz = insertarR(this.raiz, claveAInsertar, valorAInsertar);
    }

    private NodoBinario<K, V> insertarR(NodoBinario<K, V> nodoActual, K claveAInsertar, V valorAInsertar) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            nodoActual = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return nodoActual;
        }
        K claveActual = nodoActual.getClave();
        if (claveAInsertar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> nodoActualIzquierdo = insertarR(nodoActual.getHijoIzquierdo(), claveAInsertar, valorAInsertar);
            nodoActual.setHijoIzquierdo(nodoActualIzquierdo);
            return nodoActual;
        } else if (claveAInsertar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> nodoActualDerecho = insertarR(nodoActual.getHijoDerecho(), claveAInsertar, valorAInsertar);
            nodoActual.setHijoDerecho(nodoActualDerecho);
            return nodoActual;
        }
        nodoActual.setValor(valorAInsertar);
        return nodoActual;
    }

    // ================================== ELIMINAR =================================
    @Override
    public V eliminar(K claveAEliminar) throws ClaveNoExisteException {
        if (claveAEliminar == null) {
            throw new IllegalArgumentException("La clave a eliminar no puede ser nula");
        }
        V valorAEliminar = buscar(claveAEliminar);
        if (valorAEliminar == null) {
            throw new ClaveNoExisteException();
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorAEliminar;
    }

    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        K claveActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return nodoActual;
        }
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> supuestoNuevoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return nodoActual;
        }

        // Caso 1
        if (nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }

        // Caso 2, opción a
        if (!nodoActual.esHijoIzquierdoVacio() && nodoActual.esHijoDerechoVacio()) {
            return nodoActual.getHijoIzquierdo();
        }

        // Caso 2, opción b
        if (nodoActual.esHijoIzquierdoVacio() && !nodoActual.esHijoDerechoVacio()) {
            return nodoActual.getHijoDerecho();
        }

        // Caso 3
        NodoBinario<K, V> nodoDelSucesor = obtenerSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K, V> supuestoNuevoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), nodoDelSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return nodoActual;
    }

    protected NodoBinario<K, V> obtenerSucesor(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoAnterior;
    }

    // ================================== SIZE =================================
    @Override
    public int size() {
        int size = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while (!colaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                size++;
                if (!nodoActual.esHijoIzquierdoVacio()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esHijoDerechoVacio()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
            }
        }
        return size;
    }

    // ================================== SIZE =================================
    public int sizeR() {
        return sizeR(this.raiz);
    }

    private int sizeR(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int nroDeNodosPorIzq = sizeR(nodoActual.getHijoIzquierdo());
        int nroDeNodosPorDer = sizeR(nodoActual.getHijoDerecho());
        return nroDeNodosPorIzq + nroDeNodosPorDer + 1;
    }

    // ================================== ALTURA =================================
    @Override
    public int altura() {
        int altura = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while (!colaDeNodos.isEmpty()) {
                int cantidadDeNodos = colaDeNodos.size();
                for (int i = 0; i < cantidadDeNodos; i++) {
                    NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                    if (!nodoActual.esHijoIzquierdoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if (!nodoActual.esHijoDerechoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoDerecho());
                    }
                }
                altura++;
            }
        }
        return altura;
    }

    // ================================== ALTURA =================================
    public int alturaR() {
        return alturaR(this.raiz);
    }

    private int alturaR(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaPorIzq = alturaR(nodoActual.getHijoIzquierdo());
        int alturaPorDer = alturaR(nodoActual.getHijoDerecho());
        return (alturaPorIzq > alturaPorDer) ? alturaPorIzq + 1 : alturaPorDer + 1;
    }

    // ================================== NIVEL =================================
    @Override
    public int nivel() {
        int nivel = -1;
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while (!colaDeNodos.isEmpty()) {
                int cantidadDeNodos = colaDeNodos.size();
                for (int i = 0; i < cantidadDeNodos; i++) {
                    NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                    if (!nodoActual.esHijoIzquierdoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if (!nodoActual.esHijoDerechoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoDerecho());
                    }
                }
                nivel++;
            }
        }
        return nivel;
    }

    // ================================ RECORRIDO IN_ORDEN ===============================
    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        apilarParaInOrden(pilaDeNodos, this.raiz);
        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            apilarParaInOrden(pilaDeNodos, nodoActual.getHijoDerecho());
        }
        return recorrido;
    }

    private void apilarParaInOrden(Stack<NodoBinario<K, V>> pilaDeNodos, NodoBinario<K, V> nodoActual) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        }
    }

    // ================================ RECORRIDO IN_ORDEN ===============================
    public List<K> recorridoEnInOrdenR() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnInOrdenR(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnInOrdenR(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            recorridoEnInOrdenR(nodoActual.getHijoIzquierdo(), recorrido);
            recorrido.add(nodoActual.getClave());
            recorridoEnInOrdenR(nodoActual.getHijoDerecho(), recorrido);
        }
    }

    // ================================ RECORRIDO PRE_ORDEN ===============================
    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);
            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                recorrido.add(nodoActual.getClave());
                if (!nodoActual.esHijoDerechoVacio()) {
                    pilaDeNodos.push(nodoActual.getHijoDerecho());
                }
                if (!nodoActual.esHijoIzquierdoVacio()) {
                    pilaDeNodos.push(nodoActual.getHijoIzquierdo());
                }
            }
        }
        return recorrido;
    }

    // ================================ RECORRIDO PRE_ORDEN ===============================
    public List<K> recorridoEnPreOrdenR() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnPreOrdenR(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrdenR(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            recorrido.add(nodoActual.getClave());
            recorridoEnPreOrdenR(nodoActual.getHijoIzquierdo(), recorrido);
            recorridoEnPreOrdenR(nodoActual.getHijoDerecho(), recorrido);
        }
    }

    // ================================ RECORRIDO POST_ORDEN =============================
    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new ArrayList<>();
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        apilarParaPostOrden(pilaDeNodos, this.raiz);
        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoDelTope = pilaDeNodos.peek();
                if (!nodoDelTope.esHijoDerechoVacio() && nodoDelTope.getHijoDerecho() != nodoActual) {
                    apilarParaPostOrden(pilaDeNodos, nodoDelTope.getHijoDerecho());
                }
            }
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

    // ================================ RECORRIDO POST_ORDEN =============================
    public List<K> recorridoEnPostOrdenR() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnPostOrdenR(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrdenR(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            recorridoEnPostOrdenR(nodoActual.getHijoIzquierdo(), recorrido);
            recorridoEnPostOrdenR(nodoActual.getHijoDerecho(), recorrido);
            recorrido.add(nodoActual.getClave());
        }
    }

    // ================================ RECORRIDO POR NIVELES =============================
    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while (!colaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                recorrido.add(nodoActual.getClave());
                if (!nodoActual.esHijoIzquierdoVacio()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esHijoDerechoVacio()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
            }
        }
        return recorrido;
    }

    // ==================================== EJERCICIOS ====================================
    // Mostrar el mínimo del árbol
    public K minimo() {
        if (!this.esArbolVacio()) {
            NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
            NodoBinario<K, V> nodoActual = this.raiz;
            while (!NodoBinario.esNodoVacio(nodoActual)) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoIzquierdo();
            }
            return nodoAnterior.getClave();
        }
        return null;
    }

    // Mostrar el máximo del árbol
    public K maximo() {
        if (!this.esArbolVacio()) {
            NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
            NodoBinario<K, V> nodoActual = this.raiz;
            while (!NodoBinario.esNodoVacio(nodoActual)) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijoDerecho();
            }
            return nodoAnterior.getClave();
        }
        return null;
    }

    // Mostrar los nodos que están en un determinado nivel
    public List<K> mostrarNodosDeLNivel(int nivel) {
        List<K> lista = new ArrayList<>();
        mostrarNodosDelNivel(this.raiz, lista, nivel, 0);
        return lista;
    }

    private void mostrarNodosDelNivel(NodoBinario<K, V> nodoActual, List<K> lista, int nivelABuscar, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }
        mostrarNodosDelNivel(nodoActual.getHijoIzquierdo(), lista, nivelABuscar, nivelActual + 1);
        mostrarNodosDelNivel(nodoActual.getHijoDerecho(), lista, nivelABuscar, nivelActual + 1);
        if (nivelABuscar == nivelActual) {
            lista.add(nodoActual.getClave());
        }
    }
    
    public List<K> mostrarNodosDeNivel(int nivelObjetivo) {
        List<K> lista = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            int nivelActual = 0;
            while (!colaDeNodos.isEmpty()) {
                int cantNodos = colaDeNodos.size();
                for (int i = 0; i < cantNodos; i++) {
                    NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                    if (nivelActual == nivelObjetivo) {
                        lista.add(nodoActual.getClave());
                    }
                    if (!nodoActual.esHijoIzquierdoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if (!nodoActual.esHijoDerechoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoDerecho());
                    }
                }
                nivelActual++;
            }
        }
        return lista;
    }

    // Cantidad de Hijos Derechos que tiene un árbol
    public int cantidadDeHijosDerechos() {
        return cantidadDeHijosDerechos(this.raiz);
    }

    private int cantidadDeHijosDerechos(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int HDPorRamaIzquierda = cantidadDeHijosDerechos(nodoActual.getHijoIzquierdo());
        int HDPorRamaDerecha = cantidadDeHijosDerechos(nodoActual.getHijoDerecho());
        if (!nodoActual.esHijoDerechoVacio()) {
            return HDPorRamaIzquierda + HDPorRamaDerecha + 1;
        }
        return HDPorRamaIzquierda + HDPorRamaDerecha;
    }

    public int cantDeHijosDerechos() {
        int cantidad = 0;
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
                    cantidad++;
                }
            }
        }
        return cantidad;
    }

    public int cantDeHijosDerechosEnNivel(int nivelObjetivo) {
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            int nivelActual = 0;
            while (!colaDeNodos.isEmpty()) {
                int cantDeNodos = colaDeNodos.size();
                for (int i = 0; i < cantDeNodos; i++) {
                    NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                    if (!nodoActual.esHijoIzquierdoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if (!nodoActual.esHijoDerechoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoDerecho());
                        if (nivelActual == nivelObjetivo) {
                            cantidad++; 
                        }
                    }
                }
                nivelActual++;
            }
        }
        return cantidad;
    }

    // Cantidad de Hijos Derechos en un nivel
    public int nroDeHijosDerechosEnNivel(int nivelObjetivo) {
        return nroDeHijosDerechosEnNivel(this.raiz, nivelObjetivo, 0);
    }

    private int nroDeHijosDerechosEnNivel(NodoBinario<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int HDPorRamaIzquierda = nroDeHijosDerechosEnNivel(nodoActual.getHijoIzquierdo(), nivelObjetivo, nivelActual + 1);
        int HDPorRamaDerecha = nroDeHijosDerechosEnNivel(nodoActual.getHijoDerecho(), nivelObjetivo, nivelActual + 1);
        if (nivelActual == nivelObjetivo) {
            if (!nodoActual.esHijoDerechoVacio()) {
                return HDPorRamaIzquierda + HDPorRamaDerecha + 1;
            }
        }
        return HDPorRamaIzquierda + HDPorRamaDerecha;
    }

    // Cantidad de Hijos Izquierdos que tiene un árbol
    public int cantidadDeHijosIzquierdos() {
        return cantidadDeHijosIzquierdos(this.raiz);
    }

    private int cantidadDeHijosIzquierdos(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int HIPorRamaIzquierda = cantidadDeHijosIzquierdos(nodoActual.getHijoIzquierdo());
        int HIPorRamaDerecha = cantidadDeHijosIzquierdos(nodoActual.getHijoDerecho());
        if (!nodoActual.esHijoIzquierdoVacio()) {
            return HIPorRamaIzquierda + HIPorRamaDerecha + 1;
        }
        return HIPorRamaIzquierda + HIPorRamaDerecha;
    }

    // Contar los nodos hojas de un árbol
    public int cantidadDeHojasPorNivel() {
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while (!colaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                if (nodoActual.esHoja()) {
                    cantidad++;
                }
                if (!nodoActual.esHijoIzquierdoVacio()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esHijoDerechoVacio()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
            }
        }
        return cantidad;
    }

    public int cantidadDeHojasPreOrden() {
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);
            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                if (nodoActual.esHoja()) {
                    cantidad++;
                }
                if (!nodoActual.esHijoDerechoVacio()) {
                    pilaDeNodos.push(nodoActual.getHijoDerecho());
                }
                if (!nodoActual.esHijoIzquierdoVacio()) {
                    pilaDeNodos.push(nodoActual.getHijoIzquierdo());
                }
            }
        }
        return cantidad;
    }

    public int cantidadDeHojasInOrden() {
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            apilarParaInOrden(pilaDeNodos, this.raiz);
            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                if (nodoActual.esHoja()) {
                    cantidad++;
                }
                if (!nodoActual.esHijoDerechoVacio()) {
                    apilarParaInOrden(pilaDeNodos, nodoActual.getHijoDerecho());
                }
            }
        }
        return cantidad;
    }

    public int cantidadDeHojasPostOrden() {
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            apilarParaPostOrden(pilaDeNodos, this.raiz);
            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                if (nodoActual.esHoja()) {
                    cantidad++;
                }
                if (!pilaDeNodos.isEmpty()) {
                    NodoBinario<K, V> nodoDelTope = pilaDeNodos.peek();
                    if (!nodoDelTope.esHijoDerechoVacio() && nodoDelTope.getHijoDerecho() != nodoActual) {
                        apilarParaPostOrden(pilaDeNodos, nodoDelTope.getHijoDerecho());
                    }
                }
            }
        }
        return cantidad;
    }

    public int cantidadDeHojas() {
        return cantidadDeHojas(this.raiz);
    }

    private int cantidadDeHojas(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantHojasPorIzq = cantidadDeHojas(nodoActual.getHijoIzquierdo());
        int cantHojasPorDer = cantidadDeHojas(nodoActual.getHijoDerecho());
        if (nodoActual.esHoja()) {
            return cantHojasPorIzq + cantHojasPorDer + 1;
        }
        return cantHojasPorIzq + cantHojasPorDer;
    }

    public int cantidadHojasEnUnNivel(int nivel) {
        return cantidadHojasEnUnNivel(this.raiz, nivel, 0);
    }

    private int cantidadHojasEnUnNivel(NodoBinario<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            if (nivelActual == nivelObjetivo) {
                if (nodoActual.esHoja()) {
                    return 1;
                }
            } else {
                if (nodoActual.esHoja()) {
                    return 0;
                }
            }
            int cantidad = 0;
            cantidad += cantidadHojasEnUnNivel(nodoActual.getHijoIzquierdo(), nivelObjetivo, nivelActual + 1);
            cantidad += cantidadHojasEnUnNivel(nodoActual.getHijoDerecho(), nivelObjetivo, nivelActual + 1);
            return cantidad;
//            int cantidadPorIzq = cantidadHojasEnUnNivel(nodoActual.getHijoIzquierdo(), nivelObjetivo, nivelActual + 1);
//            int cantidadPorDer = cantidadHojasEnUnNivel(nodoActual.getHijoDerecho(), nivelObjetivo, nivelActual + 1);
//            if (nivelActual == nivelObjetivo) {
//                if (nodoActual.esHoja()) {
//                    return cantidadPorIzq + cantidadPorDer + 1;
//                }
//            }
//            return cantidadPorIzq + cantidadPorDer;
        }
        return 0;
    }

    // Cantidad de nodos que tienen ambos hijos 
    public int cantidadDeNodosConAmbosHijos() { //IN_ORDEN
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            apilarParaInOrden(pilaDeNodos, this.raiz);
            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                if (!nodoActual.esHijoIzquierdoVacio() && !nodoActual.esHijoDerechoVacio()) {
                    cantidad++;
                }
                if (!nodoActual.esHijoDerechoVacio()) {
                    apilarParaInOrden(pilaDeNodos, nodoActual.getHijoDerecho());
                }
            }
        }
        return cantidad;
    }
    
    public int cantDeNodosConAmbosHijosEnNivel(int nivelObjetivo) { // ITERATIVO
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            int nivelActual = 0;
            while (!colaDeNodos.isEmpty()) {
                int cantDeNodos = colaDeNodos.size();
                for (int i = 0; i < cantDeNodos; i++) {
                    NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                    if (!nodoActual.esHijoIzquierdoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if (!nodoActual.esHijoDerechoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoDerecho());
                    }
                    if (nivelActual == nivelObjetivo) {
                        if (!nodoActual.esHijoIzquierdoVacio() && !nodoActual.esHijoDerechoVacio()) {
                            cantidad++;
                        }
                    }
                }
                nivelActual++;
            }
        }
        return cantidad;
    }

    // Cantidad de nodos con ambos hijos en un nivel
    public int nroDeNodosConAmbosHijosEnNivel(int nivelObjetivo) {
        return nroDeNodosConAmbosHijosEnNivel(this.raiz, nivelObjetivo, 0);
    }

    private int nroDeNodosConAmbosHijosEnNivel(NodoBinario<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int completosPorIzq = nroDeNodosConAmbosHijosEnNivel(nodoActual.getHijoIzquierdo(), nivelObjetivo, nivelActual + 1);
        int completosPorDer = nroDeNodosConAmbosHijosEnNivel(nodoActual.getHijoDerecho(), nivelObjetivo, nivelActual + 1);
        if (nivelActual == nivelObjetivo) {
            if (!nodoActual.esHijoIzquierdoVacio() && !nodoActual.esHijoDerechoVacio()) {
                return completosPorIzq + completosPorDer + 1;
            }
        }
        return completosPorIzq + completosPorDer;
    }

    // Otra forma
    public int nroNodosConAmbosHijosEnNivel(int nivelObjetivo) {
        return nroNodosConAmbosHijosEnNivel(this.raiz, nivelObjetivo, 0);
    }

    private int nroNodosConAmbosHijosEnNivel(NodoBinario<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        if (nivelActual < nivelObjetivo) {
            int completosPorIzq = nroNodosConAmbosHijosEnNivel(nodoActual.getHijoIzquierdo(), nivelObjetivo, nivelActual + 1);
            int completosPorDer = nroNodosConAmbosHijosEnNivel(nodoActual.getHijoDerecho(), nivelObjetivo, nivelActual + 1);
            return completosPorIzq + completosPorDer;
        }
        if (nivelActual == nivelObjetivo) {
            if (!nodoActual.esHijoIzquierdoVacio() && !nodoActual.esHijoDerechoVacio()) {
                return 1;
            }
        }
        return 0;
    }

    // Cantidad de nodos con un solo hijo
    public int cantidadDeNodosConUnHijo() { // POST_ORDEN
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            apilarParaPostOrden(pilaDeNodos, this.raiz);
            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                if (!nodoActual.esHijoIzquierdoVacio() && nodoActual.esHijoDerechoVacio()) {
                    cantidad++;
                }
                if (nodoActual.esHijoIzquierdoVacio() && !nodoActual.esHijoDerechoVacio()) {
                    cantidad++;
                }
                if (!pilaDeNodos.isEmpty()) {
                    NodoBinario<K, V> nodoDelTope = pilaDeNodos.peek();
                    if (!nodoDelTope.esHijoDerechoVacio() && nodoDelTope.getHijoDerecho() != nodoActual) {
                        apilarParaPostOrden(pilaDeNodos, nodoDelTope.getHijoDerecho());
                    }
                }
            }
        }
        return cantidad;
    }

    public int cantidadNodosConUnSoloHijo() {
        return cantidadNodosConUnSoloHijo(this.raiz);
    }

    private int cantidadNodosConUnSoloHijo(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantUnHijoPorIzq = cantidadNodosConUnSoloHijo(nodoActual.getHijoIzquierdo());
        int cantUnHijoPorDer = cantidadNodosConUnSoloHijo(nodoActual.getHijoDerecho());
        if ((nodoActual.esHijoIzquierdoVacio() && !nodoActual.esHijoDerechoVacio())
                || (!nodoActual.esHijoIzquierdoVacio() && nodoActual.esHijoDerechoVacio())) {
            return cantUnHijoPorIzq + cantUnHijoPorDer + 1;
        }
        return cantUnHijoPorIzq + cantUnHijoPorDer;
    }

    // Cantidad de hijos vacios desde un determinado nivel
    public int cantDeHijosVaciosDesdeUnNivel(int nivel) {
        return cantDeHijosVaciosDesdeUnNivel(this.raiz, nivel, 0);
    }

    private int cantDeHijosVaciosDesdeUnNivel(NodoBinario<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidad = 0;
        cantidad += cantDeHijosVaciosDesdeUnNivel(nodoActual.getHijoIzquierdo(), nivelObjetivo, nivelActual + 1);
        cantidad += cantDeHijosVaciosDesdeUnNivel(nodoActual.getHijoDerecho(), nivelObjetivo, nivelActual + 1);
        if (nivelActual >= nivelObjetivo) {
            if ((nodoActual.esHijoIzquierdoVacio() && !nodoActual.esHijoDerechoVacio())
                    || (!nodoActual.esHijoIzquierdoVacio() && nodoActual.esHijoDerechoVacio())) {
                cantidad++;
            }
        }
        return cantidad;
    }

    // Cantidad de nodos no Hojas
    public int cantidadDeNoHojas() { //POST_ORDEN
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            apilarParaPostOrden(pilaDeNodos, this.raiz);
            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                if (!nodoActual.esHoja()) {
                    cantidad++;
                }
                if (!pilaDeNodos.isEmpty()) {
                    NodoBinario<K, V> nodoDelTope = pilaDeNodos.peek();
                    if (!nodoDelTope.esHijoDerechoVacio() && nodoDelTope.getHijoDerecho() != nodoActual) {
                        apilarParaPostOrden(pilaDeNodos, nodoDelTope.getHijoDerecho());
                    }
                }
            }
        }
        return cantidad;
    }

    public int cantidadDeNodosNoHojas() {
        return cantidadDeNodosNoHojas(this.raiz);
    }

    private int cantidadDeNodosNoHojas(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int noHojasPorIzq = cantidadDeNodosNoHojas(nodoActual.getHijoIzquierdo());
        int noHojasPorDer = cantidadDeNodosNoHojas(nodoActual.getHijoDerecho());
        if (!nodoActual.esHoja()) {
            return noHojasPorIzq + noHojasPorDer + 1;
        }
        return noHojasPorIzq + noHojasPorDer;
    }

    // Cantidad de Hijos Izquierdos en la Rama Derecha
    public int cantidadHijosIzqRamaDer() {
        if (this.esArbolVacio()) {
            return 0;
        }
        int cantidad = 0;
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        pilaDeNodos.push(this.raiz.getHijoDerecho());
        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
            if (!nodoActual.esHijoDerechoVacio()) {
                pilaDeNodos.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esHijoIzquierdoVacio()) {
                cantidad++;
                pilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return cantidad;
    }

    // Cantidad de hijos vacios
    public int cantidadDeHijosVacios() { // IN_ORDEN
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            apilarParaInOrden(pilaDeNodos, this.raiz);
            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                if (nodoActual.esHijoIzquierdoVacio()) {
                    cantidad++;
                }
                if (nodoActual.esHijoDerechoVacio()) {
                    cantidad++;
                }
                if (!nodoActual.esHijoDerechoVacio()) {
                    apilarParaInOrden(pilaDeNodos, nodoActual.getHijoDerecho());
                }
            }
        }
        return cantidad;
    }

    /* Para un árbol binario de búsqueda implementar un método que usando 
       la lógica de un recorrido en InOrden iterativo determine el número 
       de nodos hojas que hay en el árbol. */
    public int nroNodosHojas() {
        int cantidad = 0;
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        apilarParaInOrden(pilaDeNodos, this.raiz);
        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
            if (nodoActual.esHoja()) {
                cantidad++;
            }
            apilarParaInOrden(pilaDeNodos, nodoActual.getHijoDerecho());
        }
        return cantidad;
    }

    /* Para un árbol binario implemente un método que determine la cantidad 
       de nodos que hay después del nivel n. */
    public int cantidadNodos(int nivel) {
        return cantidadNodos(this.raiz, nivel, 0);
    }

    private int cantidadNodos(NodoBinario<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidadPorIzq = cantidadNodos(nodoActual.getHijoIzquierdo(), nivelObjetivo, nivelActual + 1);
        int cantidadPorDer = cantidadNodos(nodoActual.getHijoDerecho(), nivelObjetivo, nivelActual + 1);
        if (nivelActual > nivelObjetivo) {
            return cantidadPorIzq + cantidadPorDer + 1;
        }
        return cantidadPorIzq + cantidadPorDer;
    }

    public int cantidadDeNodos(int nivelObjetivo) {
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            int nivelActual = 0;
            while (!colaDeNodos.isEmpty()) {
                int cantDeNodos = colaDeNodos.size();
                int nivel = 0;
                while (nivel < cantDeNodos) {
                    NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                    if (!nodoActual.esHijoIzquierdoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if (!nodoActual.esHijoDerechoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoDerecho());
                    }
                    if (nivelActual > nivelObjetivo) {
                        cantidad++;
                    }
                    nivel++;
                }
                nivelActual++;
            }
        }
        return cantidad;
    }

    /* Cantidad de nodos en un nivel determinado */
    public int cantidadNodosEnUnNivel(int nivelObjetivo) {
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            int nivelActual = 0;
            while (!colaDeNodos.isEmpty()) {
                int cantDeNodos = colaDeNodos.size();
                for (int i = 0; i < cantDeNodos; i++) {
                    NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                    if (!nodoActual.esHijoIzquierdoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if (!nodoActual.esHijoDerechoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoDerecho());
                    }
                    if (nivelActual == nivelObjetivo) {
                        cantidad++;
                    }
                }
                nivelActual++;
            }
        }
        return cantidad;
    }

    /* Cantidad de nodos hojas antes y despues de un nivel */
    public int cantidadNodosHojas(int nivelObjetivo) {
        return cantidadNodosHojas(this.raiz, nivelObjetivo, 0);
    }

    private int cantidadNodosHojas(NodoBinario<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantPorIzq = cantidadNodosHojas(nodoActual.getHijoIzquierdo(), nivelObjetivo, nivelActual + 1);
        int cantPorDer = cantidadNodosHojas(nodoActual.getHijoDerecho(), nivelObjetivo, nivelActual + 1);
        if (nivelActual < nivelObjetivo || nivelActual > nivelObjetivo) {
            if (nodoActual.esHoja()) {
                return cantPorIzq + cantPorDer + 1;
            }
        }
        return cantPorIzq + cantPorDer;
    }

    /* Metodo que devuelva verdadero si los nodos del nivel n tienen todos 
       sus hijos diferente de vacio */
    public boolean sonHijosDiferentesDeVacioEnNivel(int nivel) {
        return sonHijosDiferentesDeVacioEnNivel(this.raiz, nivel, 0);
    }

    private boolean sonHijosDiferentesDeVacioEnNivel(NodoBinario<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return true;
        }
        boolean HDVPorIzq = sonHijosDiferentesDeVacioEnNivel(nodoActual.getHijoIzquierdo(), nivelObjetivo, nivelActual + 1);
        boolean HDVPorDer = sonHijosDiferentesDeVacioEnNivel(nodoActual.getHijoDerecho(), nivelObjetivo, nivelActual + 1);
        if (nivelActual == nivelObjetivo) {
            return !nodoActual.esHijoIzquierdoVacio() && !nodoActual.esHijoDerechoVacio();
        }
        return HDVPorIzq && HDVPorDer;
    }

    // Cantidad de nodos con hijos izquierdos diferentes de vacio
    public int nroDeNodosConHijosIzq() {
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
            apilarParaPostOrden(pilaDeNodos, this.raiz);
            while (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
                if (!nodoActual.esHijoIzquierdoVacio()) {
                    cantidad++;
                }
                if (!pilaDeNodos.isEmpty()) {
                    NodoBinario<K, V> nodoTope = pilaDeNodos.peek();
                    if (!nodoTope.esHijoDerechoVacio() && nodoTope.getHijoDerecho() != nodoActual) {
                        apilarParaPostOrden(pilaDeNodos, nodoTope.getHijoDerecho());
                    }
                }
            }
        }
        return cantidad;
    }
    
    // Nro de nodos despues de un nivel n
    public int nroDeNodos(int nivelObjetivo) {
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            int nivelActual = 0;
            while (!colaDeNodos.isEmpty()) {
                int cantNodos = colaDeNodos.size();
                for (int i = 0; i < cantNodos; i++) {
                    NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                    if (nivelActual > nivelObjetivo) {
                        cantidad++;
                    }
                    if (!nodoActual.esHijoIzquierdoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                    }
                    if (!nodoActual.esHijoDerechoVacio()) {
                        colaDeNodos.offer(nodoActual.getHijoDerecho());
                    }
                }
                nivelActual++;
            }
        }
        return cantidad;
    }
    
    // ====================================================================================
    public JPanel getdibujo() {
        return new ArbolExpresionGrafico(this);
    }

}
