package Test;

import Arboles.ArbolMVias;
import Arboles.NodoMVias;
import Excepciones.ExcepcionOrdenInvalido;

public class TestArbolMVias {
    
    public static void main(String[] args) throws ExcepcionOrdenInvalido {
        
        ArbolMVias<Integer, String> miArbol = new ArbolMVias<>(4);
        
//        NodoMVias<Integer, String> nodoActual = new NodoMVias<>(4);
//        NodoMVias<Integer, String> nodoActual1 = new NodoMVias<>(4);
//        NodoMVias<Integer, String> nodoActual2 = new NodoMVias<>(4);
//        NodoMVias<Integer, String> nodoActual3 = new NodoMVias<>(4);
        
//        nodoActual.setClave(0, 10);
//        nodoActual.setValor(0, "aa");
//        nodoActual.setClave(1, 20);
//        nodoActual.setValor(1, "bb");
//        nodoActual.setClave(2, 35);
//        nodoActual.setValor(2, "cc");
//        nodoActual.setClave(3, 50);
//        nodoActual.setValor(3, "dd");
//        nodoActual.setClave(4, 56);
//        nodoActual.setValor(4, "ee");
//        nodoActual.setClave(5, 74);
//        nodoActual.setValor(5, "ff");

//        nodoActual1.setClave(0, 37);
//        nodoActual1.setValor(0, "aa");
//        nodoActual1.setClave(1, 40);
//        nodoActual1.setValor(1, "bb");
//        nodoActual1.setClave(2, 45);
//        nodoActual1.setValor(2, "cc");
//        nodoActual1.setClave(3, 49);
//        nodoActual1.setValor(3, "dd");
//        
//        nodoActual2.setClave(0, 1);
//        nodoActual2.setValor(0, "aa");
//        nodoActual2.setClave(1, 5);
//        nodoActual2.setValor(1, "bb");
//        nodoActual2.setClave(2, 8);
//        nodoActual2.setValor(2, "cc");
//        nodoActual2.setClave(3, 9);
//        nodoActual2.setValor(3, "dd");
//        
//        nodoActual3.setClave(0, 22);
//        nodoActual3.setValor(0, "aa");
//        nodoActual3.setClave(1, 25);
//        nodoActual3.setValor(1, "bb");
//        nodoActual3.setClave(2, 30);
//        nodoActual3.setValor(2, "cc");
//        nodoActual3.setClave(3, 34);
//        nodoActual3.setValor(3, "dd");
//        
//        nodoActual.setHijo(0, nodoActual2);
//        nodoActual.setHijo(2, nodoActual3);
//        nodoActual.setHijo(3, nodoActual1);

//        System.out.println(nodoActual.getListaDeClaves().toString());
//        System.out.println(nodoActual.getListaDeValores().toString());
//        
//        miArbol.eliminarDatosDeNodo(nodoActual,1);
//        miArbol.eliminarDatosDeNodo(nodoActual,0);
//        miArbol.eliminarDatosDeNodo(nodoActual,3);
//        miArbol.eliminarDatosDeNodo(nodoActual,2);
//        
//        System.out.println(nodoActual.getListaDeClaves().toString());
//        System.out.println(nodoActual.getListaDeValores().toString());
//        
//        miArbol.eliminarDatosDeNodo(nodoActual,2);
//        miArbol.eliminarDatosDeNodo(nodoActual,0);
//        
//        System.out.println(nodoActual.getListaDeClaves().toString());
//        System.out.println(nodoActual.getListaDeValores().toString());
//        
//        int clave = miArbol.buscarClaveSucesoraInOrden(nodoActual, 20);
//        int clave1 = miArbol.buscarClavePredecesoraInOrden(nodoActual, 10);
//        
//        System.out.println("La clave sucesora es: " + clave);
//        System.out.println("La clave predecesora es: " + clave1);
        
        miArbol.insertar(120, "");
        miArbol.insertar(200, "");
        miArbol.insertar(80, "");
        miArbol.insertar(150, "");
        miArbol.insertar(130, "");
        miArbol.insertar(50, "");
        miArbol.insertar(140, "");
        miArbol.insertar(134, "");
        miArbol.insertar(400, "");
        miArbol.insertar(500, "");
        miArbol.insertar(560, "");
        miArbol.insertar(75, "");
        miArbol.insertar(110, "");
        miArbol.insertar(98, "");
        miArbol.insertar(70, "");
        miArbol.insertar(72, "");
        miArbol.insertar(190, "");
        miArbol.insertar(160, "");
        miArbol.insertar(170, "");
        miArbol.insertar(158, "");
        
        System.out.println(miArbol.recorridoPorNiveles());
        System.out.println(miArbol.recorridoEnPreOrden());
        System.out.println(miArbol.recorridoEnInOrden());
        System.out.println(miArbol.recorridoEnPostOrden());
        
        System.out.println("Cantidad datos vacios: " + miArbol.cantidadDatosVacios());
        System.out.println("Cantidad Hojas: " + miArbol.cantidadHojas());
        System.out.println("Cantidad Hojas desde nivel: " + miArbol.cantidadHojasDesdeNivel(2));
        System.out.println("Cantidad Hijos vacios: " + miArbol.cantidadHijosVacios());
        System.out.println("Cantidad no Hojas: " + miArbol.cantidadNoHojas());

    }
    
}
