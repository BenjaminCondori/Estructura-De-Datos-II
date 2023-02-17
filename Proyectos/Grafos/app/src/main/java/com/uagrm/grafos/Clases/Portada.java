package com.uagrm.grafos.Clases;

public class Portada {

    private String titulo;
    private int imagen;

    public Portada(String titulo, int imagen) {
        this.titulo = titulo;
        this.imagen = imagen;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
