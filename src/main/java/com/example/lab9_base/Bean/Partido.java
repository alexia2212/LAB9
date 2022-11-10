package com.example.lab9_base.Bean;

public class Partido {
    private int idPartido;
    private String fecha;
    private int numeroJornada;
    private String seleccionLocal;
    private String seleccionVisitante;
    private String arbitro;

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumeroJornada() {
        return numeroJornada;
    }

    public void setNumeroJornada(int numeroJornada) {
        this.numeroJornada = numeroJornada;
    }

    public String getSeleccionLocal() {
        return seleccionLocal;
    }

    public void setSeleccionLocal(String seleccionLocal) {
        this.seleccionLocal = seleccionLocal;
    }

    public String getSeleccionVisitante() {
        return seleccionVisitante;
    }

    public void setSeleccionVisitante(String seleccionVisitante) {
        this.seleccionVisitante = seleccionVisitante;
    }

    public String getArbitro() {
        return arbitro;
    }

    public void setArbitro(String arbitro) {
        this.arbitro = arbitro;
    }
}
