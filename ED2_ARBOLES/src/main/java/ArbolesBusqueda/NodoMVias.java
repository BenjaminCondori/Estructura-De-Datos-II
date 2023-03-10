package ArbolesBusqueda;

import java.util.LinkedList;
import java.util.List;

public class NodoMVias<K, V> {
    
    private List<K> listaDeClaves;
    private List<V> listaDeValores;
    private List<NodoMVias<K, V>> listaDeHijos;
    
    public NodoMVias(int orden) {
        listaDeClaves = new LinkedList<>();
        listaDeValores = new LinkedList<>();
        listaDeHijos = new LinkedList<>();
        for (int i = 0; i < orden - 1; i++) {
            listaDeClaves.add((K)NodoMVias.datoVacio());
            listaDeValores.add((V)NodoMVias.datoVacio());
            listaDeHijos.add(NodoMVias.nodoVacio());
        }
        listaDeHijos.add(NodoMVias.nodoVacio());
    }
    
    public NodoMVias(int orden, K primerClave, V primerValor) {
        this(orden);
        this.listaDeClaves.set(0, primerClave);
        this.listaDeValores.set(0, primerValor);
    }
    
    public K getClave(int posicion) {
        return this.listaDeClaves.get(posicion);
    }
    
    public void setClave(int posicion, K unaClave) {
        this.listaDeClaves.set(posicion, unaClave);
    }
    
    public V getValor(int posicion) {
        return this.listaDeValores.get(posicion);
    }
    
    public void setValor(int posicion, V unValor) {
        this.listaDeValores.set(posicion, unValor);
    }
    
    public NodoMVias<K, V> getHijo(int posicion) {
        return this.listaDeHijos.get(posicion);
    }
    
    public void setHijo(int posicion, NodoMVias<K, V> unHijo) {
        this.listaDeHijos.set(posicion, unHijo);
    }

    public List<K> getListaDeClaves() {
        return listaDeClaves;
    }

    public List<V> getListaDeValores() {
        return listaDeValores;
    }

    public List<NodoMVias<K, V>> getListaDeHijos() {
        return listaDeHijos;
    }
    
    public static NodoMVias nodoVacio() {
        return null;
    }
    
    public static Object datoVacio() {
        return null;
    }
    
    public static boolean esNodoVacio(NodoMVias nodo) {
        return nodo == NodoMVias.nodoVacio();
    }
    
    public boolean esClaveVacia(int posicion) {
        return this.listaDeClaves.get(posicion) == NodoMVias.datoVacio();
    }
    
    public boolean esHijoVacio(int posicion) {
//        return this.listaDeHijos.get(posicion) == NodoMVias.nodoVacio();
        return NodoMVias.esNodoVacio(this.listaDeHijos.get(posicion));
    }
    
    public boolean esHoja() {
        for (int i = 0; i < listaDeHijos.size(); i++) {
            if (!this.esHijoVacio(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean estanClavesLlenas() {
        for (int i = 0; i < listaDeClaves.size(); i++) {
            if (this.esClaveVacia(i)) {
                return false;
            }
        }
        return true;
    }
    
    public int nroDeClavesNoVacias() {
        int cantidad = 0;
        for (int i = 0; i < listaDeClaves.size(); i++) {
            if (!this.esClaveVacia(i)) {
                cantidad++;
            }
        }
        return cantidad;
    }
    
    public boolean hayClavesNoVacias() {
        return nroDeClavesNoVacias() != 0;
    }
    
    public int nroDeHijosVacios() {
        int cantidad = 0;
        for (int i = 0; i < listaDeHijos.size(); i++) {
            if (this.esHijoVacio(i)) {
                cantidad++;
            }
        }
        return cantidad;
    }
    
    public int nroDeHijosNoVacios() {
        int cantidad = 0;
        for (int i = 0; i < listaDeHijos.size(); i++) {
            if (!esHijoVacio(i)) {
                cantidad++;
            }
        }
        return cantidad;
    }

}
