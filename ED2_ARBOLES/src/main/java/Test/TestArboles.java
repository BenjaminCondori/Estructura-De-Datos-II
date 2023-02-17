package Test;

import Arboles.ArbolBinario;
import Excepciones.ClaveNoExisteException;

public class TestArboles {
    
    public static void main(String[] args) throws ClaveNoExisteException {
        
        ArbolBinario<Integer, String> miArbol = new ArbolBinario<>();
        
        miArbol.insertar(60, "");
//        miArbol.insertar(40, "");
//        miArbol.insertar(45, "");
//        miArbol.insertar(15, "");
//        miArbol.insertar(28, "");
//        miArbol.insertar(90, "");
//        miArbol.insertar(120, "");
//        miArbol.insertar(70, "");
//        miArbol.insertar(95, "");
//        miArbol.insertar(79, "");
//        miArbol.insertar(75, "");
//        miArbol.insertar(73, "");
//        miArbol.insertar(30, "");
//        miArbol.insertar(20, "");
        
        System.out.println("Recorrido por Niveles:  " + miArbol.recorridoPorNiveles());
//        System.out.println("Recorrido en PreOrden:  " + miArbol.recorridoPreOrden());
//        System.out.println("Recorrido en InOrden:   " + miArbol.recorridoInOrden());
//        System.out.println("Recorrido en PostOrden: " + miArbol.recorridoPostOrden());
//        
//        System.out.println("Size: " + miArbol.size());
//        System.out.println("Altura: " + miArbol.altura());
//        System.out.println("Nivel: " + miArbol.nivel());

        miArbol.eliminar(60);
//        miArbol.eliminar(45);
        
        System.out.println("Recorrido por Niveles:  " + miArbol.recorridoPorNiveles());
        
    }
    
}
