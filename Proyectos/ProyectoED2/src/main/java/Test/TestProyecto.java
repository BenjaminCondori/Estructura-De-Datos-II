package Test;

import Negocio.Usuario;
import java.util.Arrays;

public class TestProyecto {
    
    public static void main(String[] args) {
        
//        String contacto = "Nombre: Benjamin Condori Vasquez, Telefono: 87916491";
//        contacto = contacto.trim();
//        
//        String[] cadena = extraerDatos(contacto.split(" "), contacto);
//        System.out.println(Arrays.toString(cadena));
/* 
Benjamin,benjamin123,Benjamin Condori Vasquez,benjamin@gmail.com
Roberto,roberto123,Roberto Gomez,roberto@gmail.com
Maria,maria123,Maria Sanchez,maria@gmail.com

*/
        String[] usuario1 = new String[] {"MariaCZ","Maria Chavez Zurita","Maria","MariaChavez.gmail.com"};  
        
        Usuario usuario = new Usuario();
        
//        usuario.eliminarUsuario(usuario1[0]);
//        usuario.eliminarUsuario("Juan");
        usuario.editarUsuario(usuario1, false);
        
    }
    
    // Extrae los datos del Array de String pasado por parametro
    private static String[] extraerDatos(String[] filas, String cadena) {
        String s = "";
        for (int i = 0; i < filas.length; i++) {
            if (i != 0 && i != filas.length - 2 && i != filas.length - 1) {
                char x = filas[i].charAt(filas[i].length() - 1);
                if (x == ',') {
                    filas[i] = filas[i].substring(0, filas[i].length() - 1);
                    s = s + filas[i] + " ";
                } else {
                    s = s + filas[i] + " ";
                }
            } else if (i == filas.length - 1) {
                s = s + filas[i];
            }
        }

        int pos = obtenerPosicion(cadena);

        String nombre = s.substring(0, s.length() - pos);
        String telefono = s.substring(s.length() - (pos - 1), s.length());
        filas = new String[]{nombre, telefono};
        return filas;
    }
    
    // Obtiene la posición de la última posición del caracter de espacio ' ';
    private static int obtenerPosicion(String cadena) {
        int posicion = 0;
        boolean b = false;
        cadena = cadena.trim();
        for (int i = 0; i < cadena.length() && b == false; i++) {
            char x = cadena.charAt(cadena.length() - 1);
            cadena = cadena.substring(0, cadena.length() - 1);
            if (x != ' ') {
                posicion++;
            } else {
                b = true;
            }
        }
        return posicion + 1;
    }
    
}
