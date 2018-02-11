package com.example.omarchh.minegociotest.Model;

import java.math.BigDecimal;

/**
 * Created by OMAR CHH on 31/12/2017.
 */

public class mDetalleVenta {

    private int idCabeceraVenta;
    private int idDetalleVenta;
    private int numItem;
    private int idTerminal;
    private int idUser;
    private int idDetallePedido;
    private int idProducto;
    private int cantidad;
    private String productName;
    private String cEliminado;
    private String cEstadoDetalleVenta;
    private BigDecimal precioVentaUnidad;
    private BigDecimal precioSubtotal;

    public mDetalleVenta() {
        idCabeceraVenta = 0;
        idDetalleVenta = 0;
        numItem = 0;
        idTerminal = 0;
        idUser = 0;
        idDetallePedido = 0;
        idProducto = 0;
        cantidad = 0;

        productName = "";
        cEliminado = "";
        cEstadoDetalleVenta = "";
        precioVentaUnidad = new BigDecimal(0);
        precioSubtotal = new BigDecimal(0);

    }

    public int getIdCabeceraVenta() {
        return idCabeceraVenta;
    }

    public void setIdCabeceraVenta(int idCabeceraVenta) {
        this.idCabeceraVenta = idCabeceraVenta;
    }

    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public int getNumItem() {
        return numItem;
    }

    public void setNumItem(int numItem) {
        this.numItem = numItem;
    }

    public int getIdTerminal() {
        return idTerminal;
    }

    public void setIdTerminal(int idTerminal) {
        this.idTerminal = idTerminal;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(int idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getcEliminado() {
        return cEliminado;
    }

    public void setcEliminado(String cEliminado) {
        this.cEliminado = cEliminado;
    }

    public String getcEstadoDetalleVenta() {
        return cEstadoDetalleVenta;
    }

    public void setcEstadoDetalleVenta(String cEstadoDetalleVenta) {
        this.cEstadoDetalleVenta = cEstadoDetalleVenta;
    }

    public BigDecimal getPrecioVentaUnidad() {
        return precioVentaUnidad;
    }

    public void setPrecioVentaUnidad(BigDecimal precioVentaUnidad) {
        this.precioVentaUnidad = precioVentaUnidad;
    }

    public BigDecimal getPrecioSubtotal() {
        return precioSubtotal;
    }

    public void setPrecioSubtotal(BigDecimal precioSubtotal) {
        this.precioSubtotal = precioSubtotal;
    }
}
