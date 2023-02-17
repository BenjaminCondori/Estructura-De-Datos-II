package Arboles;

import Excepciones.ClaveNoExisteException;
import Excepciones.ExcepcionOrdenInvalido;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArbolMVias<K extends Comparable<K>, V> implements IArbolBinario<K, V> {

    protected NodoMVias<K, V> raiz;
    protected int orden;
    protected int POSICION_INVALIDA = -1;
    protected static final int ORDEN_MINIMO = 3;

    public NodoMVias<K, V> getRaiz() {
        return this.raiz;
    }

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
    public void vaciar() {
        this.raiz = NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    public V buscar(K claveABuscar) {
        if (claveABuscar == NodoMVias.datoVacio()) {
            throw new IllegalArgumentException("La clave a buscar no puede ser nula");
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
        return (V) NodoMVias.datoVacio();
    }

    @Override
    public boolean existe(K claveABuscar) {
        return this.buscar(claveABuscar) != NodoMVias.datoVacio();
    }

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (claveAInsertar == NodoMVias.datoVacio()) {
            throw new IllegalArgumentException("La clave a insertar no puede ser nula");
        }
        if (valorAInsertar == NodoMVias.datoVacio()) {
            throw new IllegalArgumentException("El valor a insertar no puede ser nulo");
        }

        if (this.esArbolVacio()) {
            this.raiz = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
            return;
        }

        NodoMVias<K, V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            int posicionDeClave = obtenerPosicionDeClave(nodoActual, claveAInsertar);
            if (posicionDeClave != POSICION_INVALIDA) {
                nodoActual.setValor(posicionDeClave, valorAInsertar);
                nodoActual = NodoMVias.nodoVacio();
            } else if (nodoActual.esHoja()) {
                if (nodoActual.estanClavesLlenas()) {
                    int posicionPorDondeBajar = obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                    NodoMVias<K, V> nuevoHijo = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
                    nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                } else {
                    insertarOrdenadoEnNodo(nodoActual, claveAInsertar, valorAInsertar);
                }
                nodoActual = NodoMVias.nodoVacio();
            } else {
                int posicionPorDondeBajar = obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
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

    protected int obtenerPosicionDeClave(NodoMVias<K, V> nodoActual, K claveABuscar) {
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if (claveABuscar.compareTo(claveActual) == 0) {
                return i;
            }
        }
        return POSICION_INVALIDA;
    }

    protected int obtenerPosicionPorDondeBajar(NodoMVias<K, V> nodoActual, K claveABuscar) {
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if (claveABuscar.compareTo(claveActual) < 0) {
                return i;
            }
        }
        return nodoActual.nroDeClavesNoVacias();
    }

    protected void insertarOrdenadoEnNodo(NodoMVias<K, V> nodoActual, K claveAInsertar, V valorAInsertar) {
        int posicion = obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
        for (int i = posicion; i < orden - 1; i++) {
            K auxClave = nodoActual.getClave(i);
            V auxValor = nodoActual.getValor(i);
            nodoActual.setClave(i, claveAInsertar);
            nodoActual.setValor(i, valorAInsertar);
            claveAInsertar = auxClave;
            valorAInsertar = auxValor;
        }
    }

    @Override
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
        NodoMVias<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijo(orden - 1), claveAEliminar);
        nodoActual.setHijo(orden - 1, supuestoNuevoHijo);
        return nodoActual;
    }

    public void eliminarDatosDeNodo(NodoMVias<K, V> nodoActual, int posicion) {
        for (int i = posicion; i < orden - 2; i++) {
            K clave = nodoActual.getClave(i + 1);
            V valor = nodoActual.getValor(i + 1);
            nodoActual.setClave(i, clave);
            nodoActual.setValor(i, valor);
        }
        nodoActual.setClave(orden - 2, (K) NodoMVias.datoVacio());
        nodoActual.setValor(orden - 2, (V) NodoMVias.datoVacio());
    }

    protected boolean hayHijosMasAdelante(NodoMVias<K, V> nodoActual, int i) {
        for (int j = i; j < orden - 1; j++) {
            if (nodoActual.getHijo(j + 1) != NodoMVias.nodoVacio()) {
                return true;
            }
        }
        return false;
    }

    public K buscarClaveSucesoraInOrden(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        int posicion = obtenerPosicionDeClave(nodoActual, claveAEliminar);
        if (!nodoActual.esHijoVacio(posicion + 1)) {
            NodoMVias<K, V> nodoHijo = nodoActual.getHijo(posicion + 1);
            return nodoHijo.getClave(0);
        }
        return nodoActual.getClave(posicion + 1);
    }

    public K buscarClavePredecesoraInOrden(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        int posicion = obtenerPosicionDeClave(nodoActual, claveAEliminar);
        if (!nodoActual.esHijoVacio(posicion)) {
            NodoMVias<K, V> nodoHijo = nodoActual.getHijo(posicion);
//            for (int i = 0; i < nodoHijo.nroDeClavesNoVacias(); i++) {
//                if (nodoHijo.getClave(i) != NodoMVias.datoVacio()) {
//                    posicion = i;
//                }
//            }
//            return nodoHijo.getClave(posicion);
            return nodoHijo.getClave(nodoHijo.nroDeClavesNoVacias() - 1);
        }
        return nodoActual.getClave(posicion - 1);
    }

    @Override
    public int size() {
        int size = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while (!colaDeNodos.isEmpty()) {
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();
                size++;
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
        return size;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }

    private int altura(NodoMVias<K, V> nodoActual) {
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

    public int Altura() {
        int altura = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while (!colaDeNodos.isEmpty()) {
                int cantDeNodos = colaDeNodos.size();
                for (int i = 0; i < cantDeNodos; i++) {
                    NodoMVias<K, V> nodoActual = colaDeNodos.poll();
                    for (int j = 0; j < nodoActual.nroDeClavesNoVacias(); j++) {
                        if (!nodoActual.esHijoVacio(j)) {
                            colaDeNodos.offer(nodoActual.getHijo(j));
                        }
                    }
                    if (!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())) {
                        colaDeNodos.offer(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
                    }
                }
                altura++;
            }
        }
        return altura;
    }

    @Override
    public int nivel() {
        int nivel = -1;
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            while (!colaDeNodos.isEmpty()) {
                int cantDeNodos = colaDeNodos.size();
                for (int i = 0; i < cantDeNodos; i++) {
                    NodoMVias<K, V> nodoActual = colaDeNodos.poll();
                    for (int j = 0; j < nodoActual.nroDeClavesNoVacias(); j++) {
                        if (!nodoActual.esHijoVacio(j)) {
                            colaDeNodos.offer(nodoActual.getHijo(j));
                        }
                    }
                    if (!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())) {
                        colaDeNodos.offer(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
                    }
                }
                nivel++;
            }
        }
        return nivel;
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
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
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            recorrido.add(nodoActual.getClave(i));
            recorridoEnPreOrden(nodoActual.getHijo(i), recorrido);
        }
        recorridoEnPreOrden(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), recorrido);
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnInOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            recorridoEnInOrden(nodoActual.getHijo(i), recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
        recorridoEnInOrden(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), recorrido);
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new ArrayList<>();
        recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        recorridoEnPostOrden(nodoActual.getHijo(0), recorrido);
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            recorridoEnPostOrden(nodoActual.getHijo(i + 1), recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
    }

    /* =============================== EJERCICIOS ============================ */
    public K minimo() {
        if (!this.esArbolVacio()) {
            NodoMVias<K, V> nodoAnterior = NodoMVias.nodoVacio();
            NodoMVias<K, V> nodoActual = this.raiz;
            while (!NodoMVias.esNodoVacio(nodoActual)) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijo(0);
            }
            return nodoAnterior.getClave(0);
        }
        return (K) NodoMVias.datoVacio();
    }

    public K maximo() {
        if (!this.esArbolVacio()) {
            NodoMVias<K, V> nodoAnterior = NodoMVias.nodoVacio();
            NodoMVias<K, V> nodoActual = this.raiz;
            while (!NodoMVias.esNodoVacio(nodoActual)) {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.getHijo(nodoActual.nroDeClavesNoVacias());
            }
            return nodoAnterior.getClave(nodoAnterior.nroDeClavesNoVacias() - 1);
        }
        return (K) NodoMVias.datoVacio();
    }

    // Cantidad de datos vacios
    public int cantidadDeDatosVacios() {
        return cantidadDeDatosVacios2(this.raiz);
    }

    private int cantidadDeDatosVacios(NodoMVias<K, V> nodoActual) { // IN_ORDEN
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidad = 0;
        for (int i = 0; i < orden - 1; i++) {
            cantidad = cantidad + cantidadDeDatosVacios(nodoActual.getHijo(i));
            if (nodoActual.esClaveVacia(i)) {
                cantidad++;
            }
        }
        cantidad = cantidad + cantidadDeDatosVacios(nodoActual.getHijo(orden - 1));
        return cantidad;
    }

    private int cantidadDeDatosVacios1(NodoMVias<K, V> nodoActual) { // PRE_ORDEN
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidad = 0;
        for (int i = 0; i < orden - 1; i++) {
            if (nodoActual.esClaveVacia(i)) {
                cantidad++;
            }
            cantidad = cantidad + cantidadDeDatosVacios1(nodoActual.getHijo(i));
        }
        cantidad = cantidad + cantidadDeDatosVacios1(nodoActual.getHijo(orden - 1));
        return cantidad;
    }

    private int cantidadDeDatosVacios2(NodoMVias<K, V> nodoActual) { // POST_ORDEN
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidad = 0;
        cantidad = cantidad + cantidadDeDatosVacios2(nodoActual.getHijo(0));
        for (int i = 0; i < orden - 1; i++) {
            cantidad = cantidad + cantidadDeDatosVacios2(nodoActual.getHijo(i + 1));
            if (nodoActual.esClaveVacia(i)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    // Cantidad de Hijos Vacios
    public int cantidadDeHijosVacios() {
        return cantidadDeHijosVacios(this.raiz);
    }

    private int cantidadDeHijosVacios(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidad = 0;
        for (int i = 0; i < orden; i++) {
            cantidad = cantidad + cantidadDeHijosVacios(nodoActual.getHijo(i));
            if (nodoActual.esHijoVacio(i)) {
                cantidad++;
            }
        }
        return cantidad;
    }

    // Cantidad de Hijos Vacios en un Nivel
    public int cantidadDeHijosVaciosEnUnNivel(int nivelObjetivo) {
        return cantidadDeHijosVaciosEnUnNivel(this.raiz, nivelObjetivo, 0);
    }

    private int cantidadDeHijosVaciosEnUnNivel(NodoMVias<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidad = 0;
        for (int i = 0; i < orden; i++) {
            cantidad = cantidad + cantidadDeHijosVaciosEnUnNivel(nodoActual.getHijo(i), nivelObjetivo, nivelActual + 1);
            if (nivelActual == nivelObjetivo) {
                if (nodoActual.esHijoVacio(i)) {
                    cantidad++;
                }
            }
        }
        return cantidad;
    }

    // Cantidad de Nodos Hojas
    public int cantidadDeNodosHojas() {
        return cantidadDeNodosHojas(this.raiz);
    }

    private int cantidadDeNodosHojas(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        if (nodoActual.esHoja()) {
            return 1;
        }
        int cantidad = 0;
        for (int i = 0; i < orden; i++) {
            cantidad = cantidad + cantidadDeNodosHojas(nodoActual.getHijo(i));
        }
        return cantidad;
    }

    // Cantidad de Hojas a partir de un nivel
    public int cantidadDeHojasDesdeNivel(int nivelObjetivo) {
        return cantidadDeHojasDesdeNivel(this.raiz, nivelObjetivo, 0);
    }

    private int cantidadDeHojasDesdeNivel(NodoMVias<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        if (nivelActual >= nivelObjetivo) {
            if (nodoActual.esHoja()) {
                return 1;
            }
        } else {
            if (nodoActual.esHoja()) {
                return 0;
            }
        }
        int cantidad = 0;
        for (int i = 0; i < orden; i++) {
            cantidad = cantidad + cantidadDeHojasDesdeNivel(nodoActual.getHijo(i), nivelObjetivo, nivelActual + 1);
        }
        return cantidad;
    }

    // Cantidad de Nodos Hojas desde antes y despues de un nivel
    public int nroDeHojasDeNivel(int nivelObjetivo) {
        return nroDeHojasDeNivel(this.raiz, nivelObjetivo, 0);
    }

    private int nroDeHojasDeNivel(NodoMVias<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        if (nivelActual != nivelObjetivo) {
            if (nodoActual.esHoja()) {
                return 1;
            }
        } else {
            if (nodoActual.esHoja()) {
                return 0;
            }
        }
        int cantidad = 0;
        for (int i = 0; i < orden; i++) {
            cantidad = cantidad + nroDeHojasDeNivel(nodoActual.getHijo(i), nivelObjetivo, nivelActual + 1);
        }
        return cantidad;
    }

    // Cantidad de Nodos no Hojas
    public int nroDeNodosNoHojas() {
        return nroDeNodosNoHojas(this.raiz);
    }

    private int nroDeNodosNoHojas(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidad = 0;
        if (!nodoActual.esHoja()) {
            cantidad++;
        }
        for (int i = 0; i < orden; i++) {
            cantidad = cantidad + nroDeNodosNoHojas(nodoActual.getHijo(i));
        }
        return cantidad;
    }

    // Cantidad de datos vacios en un nivel
    public int nroDeDatosVaciosEnNivel(int nivelObjetivo) {
        return nroDeDatosVaciosEnNivel(this.raiz, nivelObjetivo, 0);
    }

    private int nroDeDatosVaciosEnNivel(NodoMVias<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidad = 0;
        for (int i = 0; i < orden - 1; i++) {
            cantidad = cantidad + nroDeDatosVaciosEnNivel(nodoActual.getHijo(i), nivelObjetivo, nivelActual + 1);
            if (nivelActual == nivelObjetivo) {
                if (nodoActual.esClaveVacia(i)) {
                    cantidad++;
                }
            }
        }
        cantidad = cantidad + nroDeDatosVaciosEnNivel(nodoActual.getHijo(orden - 1), nivelObjetivo, nivelActual + 1);
        return cantidad;
    }

    public int nroDeDatosVaciosNivel(int nivelObjetivo) {
        return nroDeDatosVaciosNivel(this.raiz, nivelObjetivo, 0);
    }

    private int nroDeDatosVaciosNivel(NodoMVias<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidad = 0;
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            if (nivelActual < nivelObjetivo) {
                cantidad = cantidad + nroDeDatosVaciosNivel(nodoActual.getHijo(i), nivelObjetivo, nivelActual + 1);
            } else if (nivelActual == nivelObjetivo) {
                cantidad = (orden - 1) - nodoActual.nroDeClavesNoVacias();
            }
        }
        cantidad = cantidad + nroDeDatosVaciosNivel(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), nivelObjetivo, nivelActual + 1);
        return cantidad;
    }

    // Mostrar los nodos de un nivel
    public List<K> nodosDeNivel(int nivel) {
        List<K> listaDeNodos = new ArrayList<>();
        nodosDeNivel(this.raiz, listaDeNodos, nivel, 0);
        return listaDeNodos;
    }

    private void nodosDeNivel(NodoMVias<K, V> nodoActual, List<K> listaDeNodos, int nivelObjetivo, int nivelActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            nodosDeNivel(nodoActual.getHijo(i), listaDeNodos, nivelObjetivo, nivelActual + 1);
            if (nivelActual == nivelObjetivo) {
                listaDeNodos.add(nodoActual.getClave(i));
            }
        }
        nodosDeNivel(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), listaDeNodos, nivelObjetivo, nivelActual + 1);
    }

    private void nodosDeNivel1(NodoMVias<K, V> nodoActual, List<K> listaDeNodos, int nivelObjetivo, int nivelActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            nodosDeNivel1(nodoActual.getHijo(i), listaDeNodos, nivelObjetivo, nivelActual + 1);
        }
        nodosDeNivel1(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), listaDeNodos, nivelObjetivo, nivelActual + 1);
        if (nivelActual == nivelObjetivo) {
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                listaDeNodos.add(nodoActual.getClave(i));
            }
        }
    }

    /* Metodo que devuelva verdadero si los nodos del nivel n tienen todos 
       sus hijos diferente de vacio */
    public boolean sonHijosDiferenteDeVacioEnNivel(int nivelObjetivo) {
        if (nivelObjetivo < 0 || nivelObjetivo > this.nivel()) {
            return false;
        }
        return sonHijosDiferenteDeVacioEnNivel(this.raiz, nivelObjetivo, 0);
    }

    private boolean sonHijosDiferenteDeVacioEnNivel(NodoMVias<K, V> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return true;
        }
        boolean sonVacios = true;
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            sonVacios = sonVacios && sonHijosDiferenteDeVacioEnNivel(nodoActual.getHijo(i), nivelObjetivo, nivelActual + 1);
        }
        sonVacios = sonVacios && sonHijosDiferenteDeVacioEnNivel(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), nivelObjetivo, nivelActual + 1);
        if (nivelActual == nivelObjetivo) {
            for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
                if (nodoActual.esHijoVacio(i)) {
                    return false;
                }
            }
            if (nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())) {
                return false;
            }
        }
        return sonVacios;
    }

    /* Para un árbol MVias sobreescriba el método equals para determinar 
    si dos arboles son iguales con respecto a su forma y claves. */
    public boolean equals(ArbolMVias<K, V> arbol) {
        if (this.orden != arbol.orden) {
            return false;
        }
        if (this.esArbolVacio() || arbol.esArbolVacio()) {
            return false;
        }
        Queue<NodoMVias> colaDeNodosArbol1 = new LinkedList<>();
        Queue<NodoMVias> colaDeNodosArbol2 = new LinkedList<>();
        colaDeNodosArbol1.offer(this.raiz);
        colaDeNodosArbol2.offer(arbol.raiz);
        while (!colaDeNodosArbol1.isEmpty() && !colaDeNodosArbol2.isEmpty()) {
            NodoMVias<K, V> nodoActual1 = colaDeNodosArbol1.poll();
            NodoMVias<K, V> nodoActual2 = colaDeNodosArbol2.poll();
            for (int i = 0; i < orden - 1; i++) {
                if (!nodoActual1.esClaveVacia(i) && !nodoActual2.esClaveVacia(i)) {
                    K claveActual1 = nodoActual1.getClave(i);
                    K claveActual2 = nodoActual2.getClave(i);
                    if (claveActual1.compareTo(claveActual2) != 0) {
                        return false;
                    }
                }
            }
            for (int i = 0; i <= orden - 1; i++) {
                if (!nodoActual1.esHijoVacio(i)) {
                    colaDeNodosArbol1.offer(nodoActual1.getHijo(i));
                }
                if (!nodoActual2.esHijoVacio(i)) {
                    colaDeNodosArbol2.offer(nodoActual2.getHijo(i));
                }
            }
        }
        if (colaDeNodosArbol1.size() != colaDeNodosArbol2.size()) {
            return false;
        }
        return true;
    }

    public boolean sonArbolesSimilares1(ArbolMVias<K, V> arbol) {
        if (this.esArbolVacio() || arbol.esArbolVacio()) {
            return false;
        }
        Queue<NodoMVias<K, V>> colaDeNodos1 = new LinkedList<>();
        Queue<NodoMVias<K, V>> colaDeNodos2 = new LinkedList<>();
        colaDeNodos1.offer(this.raiz);
        colaDeNodos2.offer(arbol.raiz);
        while (!colaDeNodos1.isEmpty() && !colaDeNodos2.isEmpty()) {
            NodoMVias<K, V> nodoActual1 = colaDeNodos1.poll();
            NodoMVias<K, V> nodoActual2 = colaDeNodos2.poll();
            for (int i = 0; i < orden - 1; i++) {
                if (!nodoActual1.esHijoVacio(i) && !nodoActual2.esHijoVacio(i)) {
                    colaDeNodos1.offer(nodoActual1.getHijo(i));
                    colaDeNodos2.offer(nodoActual2.getHijo(i));
                }
                if (nodoActual1.esHijoVacio(i) != nodoActual2.esHijoVacio(i)) {
                    return false;
                }
            }
            if (!nodoActual1.esHijoVacio(orden - 1) && !nodoActual2.esHijoVacio(orden - 1)) {
                colaDeNodos1.offer(nodoActual1.getHijo(orden - 1));
                colaDeNodos2.offer(nodoActual2.getHijo(orden - 1));
            }
            if (nodoActual1.esHijoVacio(orden - 1) != nodoActual2.esHijoVacio(orden - 1)) {
                return false;
            }
        }
        return true;
    }

    /* Para un árbol mvias de búsqueda implementar un método que reciba 
    una clave, la busque en el árbol, en caso de encontrar la clave que 
    retorne en que nivel está. Que retorne -1 en caso de no estar la clave 
    en el árbol. La implementación debe ser recursiva. */
    public int buscarClave(K claveABuscar) {
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<K,V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            int nivelActual = 0;
            while (!colaDeNodos.isEmpty()) {
                int cantDeNodos = colaDeNodos.size();
                for (int i = 0; i < cantDeNodos; i++) {
                    NodoMVias<K,V> nodoActual = colaDeNodos.poll();
                    for (int j = 0; j < nodoActual.nroDeClavesNoVacias(); j++) {
                        if (claveABuscar.compareTo(nodoActual.getClave(j)) == 0) {
                            return nivelActual;
                        }
                        if (!nodoActual.esHijoVacio(j)) {
                            colaDeNodos.offer(nodoActual.getHijo(j));
                        }
                    }
                    if (!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())) {
                        colaDeNodos.offer(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
                    }
                }
                nivelActual++;
            }
        }
        return -1;
    }

    // Cantidad de nodos a partir de un nivel
    public int nroDeNodosAPartirDeNivel(int nivelObjetivo) {
        int cantidad = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            int nivelActual = 0;
            while (!colaDeNodos.isEmpty()) {
                int cantDeNodos = colaDeNodos.size();
                for (int i = 0; i < cantDeNodos; i++) {
                    NodoMVias<K, V> nodoActual = colaDeNodos.poll();
                    if (nivelActual >= nivelObjetivo) {
                        if (!NodoMVias.esNodoVacio(nodoActual)) {
                            cantidad++;
                        }
                    }
                    for (int j = 0; j < nodoActual.nroDeClavesNoVacias(); j++) {
                        if (!nodoActual.esHijoVacio(j)) {
                            colaDeNodos.offer(nodoActual.getHijo(j));
                        }
                    }
                    if (!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())) {
                        colaDeNodos.offer(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
                    }
                }
                nivelActual++;
            }
        }
        return cantidad;
    }

}
