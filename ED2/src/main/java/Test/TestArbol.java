package Test;

import Arboles.ArbolBinario;
import Excepciones.ClaveNoExisteException;

public class TestArbol {
    
    public static void main(String[] args) throws ClaveNoExisteException {
        
        ArbolBinario<Integer, String> miArbol = new ArbolBinario<>();
        
        miArbol.insertar(60, "");
        miArbol.insertar(40, "");
        miArbol.insertar(45, "");
        miArbol.insertar(15, "");
        miArbol.insertar(28, "");
        miArbol.insertar(90, "");
        miArbol.insertar(120, "");
        miArbol.insertar(70, "");
        miArbol.insertar(95, "");
        miArbol.insertar(79, "");
        miArbol.insertar(75, "");
        
        miArbol.insertar(73, "");
        miArbol.insertar(30, "");
        miArbol.insertar(20, "");
//        miArbol.insertar(80, "");
//        miArbol.insertar(77, "");
        
//        miArbol.insertar(10, "");
//        miArbol.insertar(41, "");
//        miArbol.insertar(50, "");
//        miArbol.insertar(65, "");
//        miArbol.insertar(130, "");
        
//        System.out.println("Recorrido por Niveles:  " + miArbol.recorridoPorNiveles());
//        System.out.println("Recorrido en PreOrden:  " + miArbol.recorridoEnPreOrden());
//        System.out.println("Recorrido en InOrden:   " + miArbol.recorridoEnInOrden());
//        System.out.println("Recorrido en PostOrden: " + miArbol.recorridoEnPostOrden());
//        
//        System.out.println("");
//        System.out.println("Size: " + miArbol.sizeR());
//        System.out.println("Altura: " + miArbol.alturaR());
//        System.out.println("Nivel: " + miArbol.nivel());
//        
//        miArbol.eliminar(45);
//        miArbol.eliminar(60);
        
//        System.out.println("Recorrido por Niveles:  " + miArbol.recorridoPorNiveles());
//        System.out.println("Recorrido en PreOrden:  " + miArbol.recorridoEnPreOrdenR());
//        System.out.println("Recorrido en InOrden:   " + miArbol.recorridoEnInOrdenR());
//        System.out.println("Recorrido en PostOrden: " + miArbol.recorridoEnPostOrdenR());

        System.out.println("Cantidad de Hijos Derechos: " + miArbol.cantidadDeHijosDerechos());
        System.out.println("Cantidad de Hijos Derechos: " + miArbol.cantDeHijosDerechos());
        System.out.println("Cantidad de Hijos Derechos en nivel: " + miArbol.nroDeHijosDerechosEnNivel(3));
        System.out.println("Cantidad de Hijos Derechos en nivel: " + miArbol.cantDeHijosDerechosEnNivel(3));
        System.out.println("Cantidad de Hijos Izquierdos: " + miArbol.cantidadDeHijosIzquierdos());
        System.out.println("Nodos del Nivel: " + miArbol.mostrarNodosDeLNivel(3));
        System.out.println("Nodos del Nivel: " + miArbol.mostrarNodosDeNivel(3));
//        System.out.println("Mínimo: " + miArbol.minimo());
//        System.out.println("Máximo: " + miArbol.maximo());
//        System.out.println("Cantidad de Hojas: " + miArbol.cantidadDeHojasPorNivel());
//        System.out.println("Cantidad de Hojas: " + miArbol.cantidadDeHojasPreOrden());
//        System.out.println("Cantidad de Hojas: " + miArbol.cantidadDeHojasInOrden());
//        System.out.println("Cantidad de Hojas: " + miArbol.cantidadDeHojasPostOrden());
        System.out.println("Cantidad de Hojas: " + miArbol.cantidadDeHojas());
        System.out.println("Cantidad de Nodos con Ambos Hijos: " + miArbol.cantidadDeNodosConAmbosHijos());
        System.out.println("Cantidad de Nodos con Ambos Hijos en nivel: " + miArbol.nroDeNodosConAmbosHijosEnNivel(1));
        System.out.println("Cantidad de Nodos con Ambos Hijos en nivel: " + miArbol.nroNodosConAmbosHijosEnNivel(1));
        System.out.println("Cantidad de Nodos con Ambos Hijos en nivel: " + miArbol.cantDeNodosConAmbosHijosEnNivel(1));
        System.out.println("Cantidad de Nodos con Un Hijo: " + miArbol.cantidadDeNodosConUnHijo());
//        System.out.println("Cantidad de Nodos con Un Hijo: " + miArbol.cantidadNodosConUnSoloHijo());
//        System.out.println("Cantidad de No Hojas: " + miArbol.cantidadDeNoHojas());
        System.out.println("Cantidad de No Hojas: " + miArbol.cantidadDeNodosNoHojas());
        System.out.println("Cantidad de Hijos Izq en Rama Der: " + miArbol.cantidadHijosIzqRamaDer());
        System.out.println("Cantidad de Nodos Vacios desde un nivel: " + miArbol.cantDeHijosVaciosDesdeUnNivel(2));
        System.out.println("Cantidad de Hojas en un nivel: " + miArbol.cantidadHojasEnUnNivel(4));
        System.out.println("Hijos Diferentes de vacio en nivel: " + miArbol.sonHijosDiferentesDeVacioEnNivel(3));
        System.out.println("Cantidad de Hijos vacios: " + miArbol.cantidadDeHijosVacios());
        
        System.out.println("Cantidad de Hojas: " + miArbol.nroNodosHojas());
        System.out.println("Cantidad de Nodos despues de un nivel: " + miArbol.cantidadNodos(2));
        System.out.println("Cantidad de Nodos despues de un nivel: " + miArbol.cantidadDeNodos(2));
        System.out.println("Cantidad de Nodos en un nivel: " + miArbol.cantidadNodosEnUnNivel(2));
        System.out.println("Cantidad de Hojas antes y despues de un nivel: " + miArbol.cantidadNodosHojas(4));
        System.out.println("Cantidad de Nodos con hijos Izq: " + miArbol.nroDeNodosConHijosIzq());
        System.out.println("Cantidad de Nodos despues de un nivel: " + miArbol.nroDeNodos(3)); 
        
    }
    
}
