package Arboles;

import Excepciones.ClaveNoExisteException;
import java.util.List;

public interface IArbolBusqueda<K extends Comparable<K>, V> {
    
    void insertar(K clave, V valor);
    
    V eliminar(K clave) throws ClaveNoExisteException;
    
    V buscar(K clave);
    
    boolean existe(K clave);
    
    int size();
    
    int altura();
    
    int nivel();
    
    void vaciar();
    
    boolean esArbolVacio();
    
    List<K> recorridoInOrden();
    
    List<K> recorridoPreOrden();
    
    List<K> recorridoPostOrden();
    
    List<String> recorridoPorNiveles();
    
}
