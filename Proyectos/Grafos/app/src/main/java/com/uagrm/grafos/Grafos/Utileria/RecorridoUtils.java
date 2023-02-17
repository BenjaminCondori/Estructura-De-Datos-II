package com.uagrm.grafos.Grafos.Utileria;

import java.util.ArrayList;
import java.util.List;

public class RecorridoUtils {
    
    private List<Boolean> marcados;
    
    public RecorridoUtils(int nroDeVertices) {
        this.marcados = new ArrayList<>();
        for (int i = 0; i < nroDeVertices; i++) {
            this.marcados.add(Boolean.FALSE);
        }
    }

    public void desmarcarTodo() {
        for (int i = 0; i < this.marcados.size(); i++) {
            this.marcados.set(i, Boolean.FALSE);
        }
    }
    
    public void marcarVertice(int posDeVertice) {
        this.marcados.set(posDeVertice, Boolean.TRUE);
    }
    
    public boolean estaVerticeMarcado(int posDeVertice) { 
        return this.marcados.get(posDeVertice);
    }
    
    public boolean estanTodosMarcados() { // Están todos los vertices marcados
        for (Boolean marcado : this.marcados) {
            if (!marcado) {
                return false;
            }
        }
        return true;
    }
    
}
