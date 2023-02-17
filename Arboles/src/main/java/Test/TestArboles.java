package Test;

import Arboles.ArbolMVias;
import Arboles.NodoMVias;
import Excepciones.ClaveNoExisteException;
import Excepciones.ExcepcionOrdenInvalido;
import java.util.LinkedList;
import java.util.List;

public class TestArboles {

    public static void main(String[] args) throws ExcepcionOrdenInvalido, ClaveNoExisteException {

//        ArbolBinarioBusqueda<Integer, String> arbol1 = new ArbolBinarioBusqueda<>();
//
//        arbol1.insertar(50, "aa");
//        arbol1.insertar(40, "bb");
//        arbol1.insertar(80, "cc");
//        arbol1.insertar(30, "dd");
//        arbol1.insertar(45, "ee");
//        
//        System.out.println(arbol1);
//
//        List<Integer> lista = arbol1.recorridoEnPreOrden();
//        System.out.println(lista);
        
//        ArbolBinarioBusqueda<Integer, String> arbol2 = new ArbolBinarioBusqueda<>();
//
//        arbol2.Insertar(50, "aa");
//        arbol2.Insertar(40, "bb");
//        arbol2.Insertar(80, "cc");
//        arbol2.Insertar(30, "dd");
//
//        List<Integer> lista2 = arbol2.recorridoPostOrden();
//        System.out.println(lista2);
//        
//        lista2 = arbol1.mostrarNivel(1);
//        System.out.println(lista2);
//        
//        int hojas = arbol1.contarHojasPost();
//        System.out.println(hojas);
        
        ArbolMVias<Integer, String> miArbol = new ArbolMVias<>(4);
        
//        NodoMVias<Integer, String> nodoActual = new NodoMVias<>(7);
//        nodoActual.setClave(0, 5);
//        nodoActual.setValor(0, "aa");
//        nodoActual.setClave(1, 25);
//        nodoActual.setValor(1, "bb");
//        nodoActual.setClave(2, 50);
//        nodoActual.setValor(2, "cc");
//        
//        System.out.println(nodoActual.listaDeClaves.toString());
//        System.out.println(nodoActual.listaDeValores.toString());
//        
//        miArbol.insertarDatosOrdenadoEnNodo(nodoActual,10, "dd");
//        
//        System.out.println(nodoActual.listaDeClaves.toString());
//        System.out.println(nodoActual.listaDeValores.toString());
//        
//        miArbol.insertarDatosOrdenadoEnNodo(nodoActual,60, "ee");
//        miArbol.insertarDatosOrdenadoEnNodo(nodoActual,30, "ff");
//        
//        System.out.println(nodoActual.listaDeClaves.toString());
//        System.out.println(nodoActual.listaDeValores.toString());

        miArbol.insertar(25, "");
        miArbol.insertar(50, "");
        miArbol.insertar(70, "");
        miArbol.insertar(5, "");
        miArbol.insertar(10, "");
        miArbol.insertar(20, "");
        miArbol.insertar(27, "");
        miArbol.insertar(35, "");
        miArbol.insertar(40, "");
        miArbol.insertar(71, "");
        miArbol.insertar(85, "");
        miArbol.insertar(90, "");
        miArbol.insertar(28, "");
        miArbol.insertar(30, "");
        miArbol.insertar(72, "");
        miArbol.insertar(75, "");
        miArbol.insertar(80, "");
        miArbol.insertar(89, "");
        miArbol.insertar(73, "");
        miArbol.insertar(81, "");
        miArbol.insertar(83, "");
        
//        System.out.println(miArbol.toString());

        System.out.println(miArbol.recorridoEnPreOrden());
        System.out.println(miArbol.recorridoEnInOrden());
        System.out.println(miArbol.recorridoPorNiveles());
        System.out.println(miArbol.altura());
        System.out.println(miArbol.size());
        
        miArbol.eliminar(40);
        miArbol.eliminar(81);
        miArbol.eliminar(75);
        miArbol.eliminar(5);
        miArbol.eliminar(50);
        
        System.out.println(miArbol.recorridoEnPreOrden());
        System.out.println(miArbol.recorridoEnInOrden());
        System.out.println(miArbol.recorridoPorNiveles());
        System.out.println(miArbol.altura());
        System.out.println(miArbol.size());
        
//        NodoMVias<Integer, String> nodoActual = new NodoMVias<>(5);
//        NodoMVias<Integer, String> nodoActual1 = new NodoMVias<>(5);
//        NodoMVias<Integer, String> nodoActual2 = new NodoMVias<>(5);
//        NodoMVias<Integer, String> nodoActual3 = new NodoMVias<>(5);
//        
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
        
//        nodoActual2.setClave(0, 1);
//        nodoActual2.setValor(0, "aa");
//        nodoActual2.setClave(1, 5);
//        nodoActual2.setValor(1, "bb");
//        nodoActual2.setClave(2, 8);
//        nodoActual2.setValor(2, "cc");
//        nodoActual2.setClave(3, 9);
//        nodoActual2.setValor(3, "dd");
        
//        nodoActual3.setClave(0, 22);
//        nodoActual3.setValor(0, "aa");
//        nodoActual3.setClave(1, 25);
//        nodoActual3.setValor(1, "bb");
//        nodoActual3.setClave(2, 30);
//        nodoActual3.setValor(2, "cc");
//        nodoActual3.setClave(3, 34);
//        nodoActual3.setValor(3, "dd");
        
//        nodoActual.setHijo(0, nodoActual2);
//        nodoActual.setHijo(2, nodoActual3);
//        nodoActual.setHijo(3, nodoActual1);

//        System.out.println(nodoActual.getListaDeClaves().toString());
//        System.out.println(nodoActual.getListaDeValores().toString());
        
//        miArbol.eliminarDatosDeNodo(nodoActual,1);
//        
//        System.out.println(nodoActual.getListaDeClaves().toString());
//        System.out.println(nodoActual.getListaDeValores().toString());
//        
//        miArbol.eliminarDatosDeNodo(nodoActual,2);
//        miArbol.eliminarDatosDeNodo(nodoActual,0);
        
//        System.out.println(nodoActual.getListaDeClaves().toString());
//        System.out.println(nodoActual.getListaDeValores().toString());
        
//        int clave = miArbol.buscarClaveSucesoraInOrden(nodoActual, 20);
//        int clave1 = miArbol.buscarClavePredecesoraInOrden(nodoActual, 10);
//        
//        System.out.println("La clave sucesora es: " + clave);
//        System.out.println("La clave predecesora es: " + clave1);

    }

}
