package com.practica02.mbussiness.clases;

public class Marca {

    private String codigo;
    private String nombre;
    private String status;

    public Marca(String codigo, String nombre, String status) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.status = status;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
