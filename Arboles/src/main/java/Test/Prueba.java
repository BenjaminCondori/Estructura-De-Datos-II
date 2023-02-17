package Test;

import java.util.ArrayList;
import java.util.List;

public class Prueba {
    
    public static void main(String[] args) {
        
        List<Integer> listaInOrden = new ArrayList<>();
        listaInOrden.add(15);
        listaInOrden.add(20);
        listaInOrden.add(28);
        listaInOrden.add(30);
        listaInOrden.add(40);
        listaInOrden.add(45);
        listaInOrden.add(60);
        listaInOrden.add(70);
        listaInOrden.add(73);
        listaInOrden.add(75);
        listaInOrden.add(79);
        listaInOrden.add(90);
        listaInOrden.add(95);
        listaInOrden.add(120);

        List<Integer> listaPostOrden = new ArrayList<>();
        listaPostOrden.add(20);
        listaPostOrden.add(30);
        listaPostOrden.add(28);
        listaPostOrden.add(15);
        listaPostOrden.add(45);
        listaPostOrden.add(40);
        listaPostOrden.add(73);
        listaPostOrden.add(75);
        listaPostOrden.add(79);
        listaPostOrden.add(70);
        listaPostOrden.add(95);
        listaPostOrden.add(120);
        listaPostOrden.add(90);
        listaPostOrden.add(60);

        List<Integer> listaPreOrden = new ArrayList<>();
        listaPreOrden.add(60);
        listaPreOrden.add(40);
        listaPreOrden.add(15);
        listaPreOrden.add(28);
        listaPreOrden.add(20);
        listaPreOrden.add(30);
        listaPreOrden.add(45);
        listaPreOrden.add(90);
        listaPreOrden.add(70);
        listaPreOrden.add(79);
        listaPreOrden.add(75);
        listaPreOrden.add(73);
        listaPreOrden.add(120);
        listaPreOrden.add(95);
        
        System.out.println(listaInOrden);
        System.out.println(listaPostOrden);
        System.out.println(listaPreOrden);
        
        System.out.println();

        int pos = obtenerPosicion(60, listaInOrden);
//        System.out.println(pos);

        List<Integer> InOrdenIzq = listaInOrden.subList(0, pos);
        List<Integer> PostOrdenIzq = listaPostOrden.subList(0, pos);
        List<Integer> PreOrdenIzq = listaPreOrden.subList(1, pos + 1);

        List<Integer> InOrdenDer = listaInOrden.subList(pos + 1, listaInOrden.size());
        List<Integer> PostOrdenDer = listaPostOrden.subList(pos, listaInOrden.size() - 1);
        List<Integer> PreOrdenDer = listaPreOrden.subList(pos + 1, listaInOrden.size());

        
        System.out.println(InOrdenIzq);
        System.out.println(PostOrdenIzq);
        System.out.println(PreOrdenIzq);

        System.out.println();
        
        System.out.println(InOrdenDer);
        System.out.println(PostOrdenDer);
        System.out.println(PreOrdenDer);
        
    }
    
    public static int obtenerPosicion(int claveABuscar, List<Integer> listaDeClaves) {
        for (int i = 0; i < listaDeClaves.size(); i++) {
            int claveActual = listaDeClaves.get(i);
            if (claveActual == claveABuscar) {
                return i;
            }
        }
        return -1;
    }
    
}
