package Arboles;

import Excepciones.ClaveNoExisteException;

public class AVL<K extends Comparable<K>, V> extends ArbolBinario<K, V> {
    
    private static final int LIMITE_MAX = 1;
    
    // Metodo para insertar un nodo en el Árbol
    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("La clave a insertar no puede ser nula");
        }
        if (valorAInsertar == null) {
            throw new IllegalArgumentException("El valor a insertar no puede ser nulo");
        }
//        if (super.existe(claveAInsertar)) {
//            throw new IllegalArgumentException("La clave a insertar ya existe");
//        }
        this.raiz = insertar(this.raiz, claveAInsertar, valorAInsertar);
    }
    
    private NodoBinario<K, V> insertar(NodoBinario<K, V> nodoActual, K claveAInsertar, V valorAInsertar) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            NodoBinario<K, V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return nuevoNodo;
        }
        
        K claveActual = nodoActual.getClave();
        
        if (claveAInsertar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzquierdo  = insertar(nodoActual.getHijoIzquierdo(), 
                    claveAInsertar, valorAInsertar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return this.balancear(nodoActual);
        }
        
        if (claveAInsertar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> supuestoNuevoHijoDerecho = insertar(nodoActual.getHijoDerecho(), 
                    claveAInsertar, valorAInsertar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return this.balancear(nodoActual);
        }
        
        nodoActual.setValor(valorAInsertar);
        return nodoActual;
    }
    
    // Metodo para balancear el árbol
    private NodoBinario<K, V> balancear(NodoBinario<K, V> nodoActual) {
        int alturaPorIzquierda = alturaR(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = alturaR(nodoActual.getHijoDerecho());
        int diferenciaDeAlturas = alturaPorIzquierda - alturaPorDerecha;
        if (diferenciaDeAlturas > LIMITE_MAX) {
            /* Rama izquierda más larga, entonces hay que rotar a la derecha.
               Averigüemos si es simple o doble */
            NodoBinario<K, V> hijoIzqDelNodoActual = nodoActual.getHijoIzquierdo();
            alturaPorIzquierda = alturaR(hijoIzqDelNodoActual.getHijoIzquierdo());
            alturaPorDerecha = alturaR(hijoIzqDelNodoActual.getHijoDerecho());
            if (alturaPorDerecha > alturaPorIzquierda) {
                return rotacionDobleADerecha(nodoActual);
            }
            return rotacionSimpleADerecha(nodoActual);
        } else if (diferenciaDeAlturas < -LIMITE_MAX) {
            /* Rama derecha más larga, entonces hay que rotar a la izquierda.
               Averigüemos si es simple o doble */
            NodoBinario<K, V> hijoDerDelNodoActual = nodoActual.getHijoDerecho();
            alturaPorIzquierda = alturaR(hijoDerDelNodoActual.getHijoIzquierdo());
            alturaPorDerecha = alturaR(hijoDerDelNodoActual.getHijoDerecho());
            if (alturaPorIzquierda > alturaPorDerecha) {
                return rotacionDobleAIzquierda(nodoActual);
            }
            return rotacionSimpleAIzquierda(nodoActual);
        }
        return nodoActual;
    }
    
    // Rotaciones Simples y Dobles
    private NodoBinario<K, V> rotacionSimpleADerecha(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoQueRota = nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
        nodoQueRota.setHijoDerecho(nodoActual);
        return nodoQueRota;
    }
    
    private NodoBinario<K, V> rotacionDobleADerecha(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> primerNodoQueRota = rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo());
        nodoActual.setHijoIzquierdo(primerNodoQueRota);
        return rotacionSimpleADerecha(nodoActual);
    }
    
    private NodoBinario<K, V> rotacionSimpleAIzquierda(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoQueRota = nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoQueRota.getHijoIzquierdo());
        nodoQueRota.setHijoIzquierdo(nodoActual);
        return nodoQueRota;
    }
    
    private NodoBinario<K, V> rotacionDobleAIzquierda(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> primerNodoQueRota = rotacionSimpleADerecha(nodoActual.getHijoDerecho());
        nodoActual.setHijoDerecho(primerNodoQueRota);
        return rotacionSimpleAIzquierda(nodoActual);
    }
    
    // Metodo para eliminar un nodo de un Árbol en RECURSIVO
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
    
    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        K claveDelNodoActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveDelNodoActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return balancear(nodoActual);
        }
        if (claveAEliminar.compareTo(claveDelNodoActual) > 0) {
            NodoBinario<K, V> supuestoNuevoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return balancear(nodoActual);
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
        return balancear(nodoActual);
    }
    
}
