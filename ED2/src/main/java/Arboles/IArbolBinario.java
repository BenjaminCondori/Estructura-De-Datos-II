
package Arboles;

import Excepciones.ClaveNoExisteException;
import java.util.List;

public interface IArbolBinario<K extends Comparable<K>, V> {
    
    void vaciar();
    
    boolean esArbolVacio();
    
    V buscar(K clave);
    
    boolean existe(K clave);
    
    void insertar(K clave, V valor);
    
    V eliminar(K clave) throws ClaveNoExisteException;
    
    int size();
    
    int altura();
    
    int nivel();
    
    List<K> recorridoEnInOrden();
    
    List<K> recorridoEnPreOrden();
    
    List<K> recorridoEnPostOrden();
    
    List<K> recorridoPorNiveles();
    
}
