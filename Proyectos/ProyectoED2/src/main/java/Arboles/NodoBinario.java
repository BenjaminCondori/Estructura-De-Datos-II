package Arboles;

public class NodoBinario<K, V> {
    
    private K clave;
    private V valor;
    private NodoBinario<K, V> hijoIzquierdo;
    private NodoBinario<K, V> hijoDerecho;

    public NodoBinario() {
    }

    public NodoBinario(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public K getClave() {
        return clave;
    }

    public void setClave(K clave) {
        this.clave = clave;
    }

    public V getValor() {
        return valor;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    public NodoBinario<K, V> getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(NodoBinario<K, V> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public NodoBinario<K, V> getHijoDerecho() {
        return hijoDerecho;
    }

    public void setHijoDerecho(NodoBinario<K, V> hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }
    
    public static NodoBinario nodoVacio() {
        return null;
    }
    
    public static boolean esNodoVacio(NodoBinario nodo) {
        return nodo == NodoBinario.nodoVacio();
    }
    
    public boolean esHijoIzquierdoVacio() {
        return NodoBinario.esNodoVacio(this.getHijoIzquierdo());
    }
    
    public boolean esHijoDerechoVacio() {
        return NodoBinario.esNodoVacio(this.getHijoDerecho());
    }
    
    public boolean esHoja() {
        return this.esHijoIzquierdoVacio() && this.esHijoDerechoVacio();
    }
    
    public boolean esNodoCompleto() {
        return !this.esHijoIzquierdoVacio() && !this.esHijoDerechoVacio();
    }
    
}
