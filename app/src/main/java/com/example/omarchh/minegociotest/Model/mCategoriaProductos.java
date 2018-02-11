package com.example.omarchh.minegociotest.Model;

/**
 * Created by OMAR CHH on 20/01/2018.
 */

public class mCategoriaProductos {

    int idCategoria;
    String NombreProducto;
    String EstadoCategoria;

    public mCategoriaProductos(int idCategoria, String nombreProducto) {
        this.idCategoria = idCategoria;
        NombreProducto = nombreProducto;
    }

    public mCategoriaProductos(int idCategoria, String nombreProducto, String estadoCategoria) {
        this.idCategoria = idCategoria;
        NombreProducto = nombreProducto;
        EstadoCategoria = estadoCategoria;
    }


    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreProducto() {
        return NombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        NombreProducto = nombreProducto;
    }

    public String getEstadoCategoria() {
        return EstadoCategoria;
    }

    public void setEstadoCategoria(String estadoCategoria) {
        EstadoCategoria = estadoCategoria;
    }
}
