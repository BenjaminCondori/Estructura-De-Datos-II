package Utilidades;

import Animacion.Animacion;
import button.PanelRound;
import javax.swing.JPanel;

public class Mover {
    
    boolean estado = true;
    
    public void animar(PanelRound panel) {
        if (estado) {
            Animacion.mover_izquierda(0, -268, 10, 12, panel);
            estado = false;
        } else {
            Animacion.mover_derecha(-268, 0, 10, 12, panel);
            estado = true;
        }
    }
    
    public void hidePanel(JPanel... panel) {
        for (JPanel panel1 : panel) {
            panel1.setVisible(false);
        }
    }
    
}
