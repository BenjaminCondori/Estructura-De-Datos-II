package com.uagrm.grafos.Grafos.Pesados;

import java.util.Objects;

public class AdyacenteConPeso implements Comparable<AdyacenteConPeso> {

    private int indiceDeVertice;
    private double peso;
    
    public AdyacenteConPeso(int indiceDeVertice) {
        this.indiceDeVertice = indiceDeVertice;
    }
    
    public AdyacenteConPeso(int indiceDeVertice, double peso) {
        this.indiceDeVertice = indiceDeVertice;
        this.peso = peso;
    }

    public int getIndiceDeVertice() {
        return indiceDeVertice;
    }

    public void setIndiceDeVertice(int indiceDeVertice) {
        this.indiceDeVertice = indiceDeVertice;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }  
    
    @Override
    public int compareTo(AdyacenteConPeso elOtroAdyacente) {
        Integer esteVertice = this.indiceDeVertice;
        Integer elOtroVertice = elOtroAdyacente.indiceDeVertice;
        return esteVertice.compareTo(elOtroVertice);
    }
    
    @Override
    public boolean equals(Object otroAdyacente) {
        if (this == otroAdyacente) return true;
        if (otroAdyacente == null || getClass() != otroAdyacente.getClass()) return false;
        AdyacenteConPeso that = (AdyacenteConPeso) otroAdyacente;
        return indiceDeVertice == that.indiceDeVertice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(indiceDeVertice);
    }
    
    @Override
    public String toString() {
        String adyacente = "(";
        adyacente = adyacente + getIndiceDeVertice() + ", " + getPeso() + ")";
        return adyacente;
    }
    
}
