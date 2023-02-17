package Test;

import Arboles.ArbolMVias;
import Excepciones.ExcepcionOrdenInvalido;

public class TestArbolMVias {
    
    public static void main(String[] args) throws ExcepcionOrdenInvalido {
        
        ArbolMVias<Integer, String> arbol = new ArbolMVias(4);
        ArbolMVias<Integer, String> arbol2 = new ArbolMVias(4);
        
        arbol.insertar(120, "");
        arbol.insertar(200, "");
        arbol.insertar(80, "");
        arbol.insertar(150, "");
        arbol.insertar(130, "");
        arbol.insertar(50, "");
        arbol.insertar(140, "");
        arbol.insertar(134, "");
        arbol.insertar(400, "");
        arbol.insertar(500, "");
        arbol.insertar(560, "");
        arbol.insertar(75, "");
        arbol.insertar(110, "");
        arbol.insertar(98, "");
        arbol.insertar(70, "");
        arbol.insertar(72, "");
        arbol.insertar(190, "");
        arbol.insertar(160, "");
        arbol.insertar(170, "");
        arbol.insertar(158, "");
        
//        arbol.insertar(165, "");
//        arbol.insertar(180, "");
//        arbol.insertar(200, "");

        arbol2.insertar(120, "");
        arbol2.insertar(200, "");
        arbol2.insertar(80, "");
        arbol2.insertar(150, "");
        arbol2.insertar(130, "");
        arbol2.insertar(50, "");
        arbol2.insertar(140, "");
        arbol2.insertar(134, "");
        arbol2.insertar(400, "");
        arbol2.insertar(500, "");
        arbol2.insertar(560, "");
        arbol2.insertar(75, "");
        arbol2.insertar(110, "");
        arbol2.insertar(98, "");
        arbol2.insertar(70, "");
        arbol2.insertar(72, "");
        arbol2.insertar(190, "");
        arbol2.insertar(160, "");
        arbol2.insertar(170, "");
        arbol2.insertar(158, "");
        
        System.out.println("Recorrido por Niveles: " + arbol.recorridoPorNiveles());
        System.out.println("Recorrido En PreOrden: " + arbol.recorridoEnPreOrden());
        System.out.println("Recorrido En InOrden: " + arbol.recorridoEnInOrden());
        System.out.println("Recorrido En PostOrden: " + arbol.recorridoEnPostOrden());
        
        System.out.println("Size: " + arbol.size());
        System.out.println("Altura: " + arbol.altura());
        System.out.println("Altura: " + arbol.Altura());
        System.out.println("Nivel: " + arbol.nivel());
        System.out.println("Minimo: " + arbol.minimo());
        System.out.println("Maximo: " + arbol.maximo());
        System.out.println("Cantidad de datos vacios: " + arbol.cantidadDeDatosVacios());
        System.out.println("Cantidad de hijos vacios: " + arbol.cantidadDeHijosVacios());
        System.out.println("Cantidad de hijos vacios en un Nivel: " + arbol.cantidadDeHijosVaciosEnUnNivel(3));
        System.out.println("Cantidad de Hojas: " + arbol.cantidadDeNodosHojas());
        System.out.println("Cantidad de Hojas desde nivel: " + arbol.cantidadDeHojasDesdeNivel(2));
        System.out.println("Nro de Hojas antes y despues de nivel: " + arbol.nroDeHojasDeNivel(3));
        System.out.println("Nro de no Hojas: " + arbol.nroDeNodosNoHojas());
        System.out.println("Nro de Datos Vacios: " + arbol.nroDeDatosVaciosEnNivel(2));
        System.out.println("Nro de Datos Vacios: " + arbol.nroDeDatosVaciosNivel(2));
        System.out.println("Nodos de un nivel: " + arbol.nodosDeNivel(0));
        System.out.println("Son Hijos No Vacios En Nivel: " + arbol.sonHijosDiferenteDeVacioEnNivel(2));
        System.out.println("Son Arboles similares: " + arbol.equals(arbol2));
        System.out.println("Son Arboles similares: " + arbol.sonArbolesSimilares1(arbol2));
        System.out.println("Nivel de clave: " + arbol.buscarClave(201));
        System.out.println("Nro de Nodos a partir de Nivel: " + arbol.nroDeNodosAPartirDeNivel(2));
        
        
    }
    
}
