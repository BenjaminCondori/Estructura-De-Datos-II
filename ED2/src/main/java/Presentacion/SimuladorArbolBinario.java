
package Presentacion;

import Arboles.ArbolBinario;
import java.util.List;
import javax.swing.JPanel;
import Arboles.IArbolBinario;

/**
 *
 * @author Toloza XD
 */
public class SimuladorArbolBinario {

    ArbolBinario<Integer, String> miArbol = new ArbolBinario();

    public SimuladorArbolBinario() {
    }

    public void insertar(Integer clave, String valor) {
        this.miArbol.insertar(clave, valor);
    }
    //metodo para mostrar los recorridos del arbol
    public String preOrden() {
        List it = this.miArbol.recorridoEnPreOrden();
        return (recorrido(it, "Recorrido PreOrden"));
    }

    public String inOrden() {
        List it = this.miArbol.recorridoEnInOrden();
        return (recorrido(it, "Recorrido InOrden"));
    }

    public String postOrden() {
        List it = this.miArbol.recorridoEnPostOrden();
        return (recorrido(it, "Recorrido PosOrden"));
    }
    
    //metodo para poder mostrar los tipos d recorrido
    private String recorrido(List it, String msg) {
        int i = 0;
        String r = msg + "\n";
        while (i < it.size()) {
            r += "\t" + it.get(i).toString() + "";
            i++;
        }
        return (r);
    }
    
    //Metodo para buscar dato en el nodo
    public String buscar(Integer clave) {
        boolean siEsta = this.miArbol.existe(clave);
        String r = "El dato:" + clave.toString() + "\n";
        r += siEsta ? "Si se encuentra en el arbol" : "No se encuentra en el arbol";
        return (r);
    }

    public JPanel getDibujo() {
        return this.miArbol.getdibujo();
    }
}
