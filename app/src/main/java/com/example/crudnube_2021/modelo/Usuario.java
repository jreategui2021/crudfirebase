package com.example.crudnube_2021.modelo;

public class Usuario {
    private String id;
    private String nombres;
    private String correo;
    public Usuario() {
    }
    public Usuario(String nombres, String correo) {
        this.nombres = nombres;
        this.correo = correo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCorreo() {
        return correo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
