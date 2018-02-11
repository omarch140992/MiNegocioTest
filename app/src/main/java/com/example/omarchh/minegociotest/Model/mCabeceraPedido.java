package com.example.omarchh.minegociotest.Model;

import java.math.BigDecimal;

/**
 * Created by OMAR CHH on 14/12/2017.
 */


public class mCabeceraPedido {
    private int idCabecera;
    private String identificadorPedido;
    private String observacion;
    private int idCliente;
    private String nombreCliente;
    private int idVendedor;
    private String nombreVendedor;
    private float valorDescuento;
    private int fechaCreacion;
    private BigDecimal descuentoPrecio;
    private BigDecimal totalBruto;
    private BigDecimal totalNeto;
    private char estadoPermanecia;

    public mCabeceraPedido() {
        idCabecera = 0;
        identificadorPedido = "";
        observacion = "";
        idCliente = 0;
        nombreCliente = "";
        idVendedor = 0;
        nombreVendedor = "";
        valorDescuento = 0.00f;
        estadoPermanecia = 'T';
        descuentoPrecio = new BigDecimal(0);
        totalBruto = new BigDecimal(0);
        totalNeto = new BigDecimal(0);
    }

    public mCabeceraPedido(int idCabecera, String identificadorPedido, String observacion, int idCliente, String nombreCliente, int idVendedor, String nombreVendedor, int fechaCreacion, BigDecimal descuentoPrecio, BigDecimal totalBruto, BigDecimal totalNeto, char estadoPermanecia) {
        this.idCabecera = idCabecera;
        this.identificadorPedido = identificadorPedido;
        this.observacion = observacion;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.idVendedor = idVendedor;
        this.nombreVendedor = nombreVendedor;
        this.fechaCreacion = fechaCreacion;
        this.descuentoPrecio = descuentoPrecio;
        this.totalBruto = totalBruto;
        this.totalNeto = totalNeto;
        this.estadoPermanecia = estadoPermanecia;
    }


    public mCabeceraPedido(int idCabecera, String identificadorPedido, String observacion, int idCliente, String nombreCliente, int idVendedor, String nombreVendedor, int fechaCreacion, char estadoPermanecia, BigDecimal totalBruto, BigDecimal descuentoPrecio, BigDecimal totalNeto) {

        this.idCabecera = idCabecera;
        this.identificadorPedido = identificadorPedido;
        this.observacion = observacion;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.idVendedor = idVendedor;
        this.nombreVendedor = nombreVendedor;
        this.totalBruto = totalBruto;
        this.descuentoPrecio = descuentoPrecio;
        this.totalNeto = totalNeto;
        this.estadoPermanecia = estadoPermanecia;
        this.fechaCreacion = fechaCreacion;

    }

    public mCabeceraPedido(int idCabecera, String identificadorPedido, String nombreCliente, String nombreVendedor, int fechaCreacion, BigDecimal totalBruto, BigDecimal descuentoPrecio, BigDecimal totalNeto) {
        this.idCabecera = idCabecera;
        this.identificadorPedido = identificadorPedido;
        this.nombreCliente = nombreCliente;
        this.nombreVendedor = nombreVendedor;
        this.fechaCreacion = fechaCreacion;
        this.totalBruto = totalBruto;

        this.totalNeto = totalNeto;
        this.descuentoPrecio = descuentoPrecio;
    }

    public int getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(int fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public char getEstadoPermanecia() {
        return estadoPermanecia;

    }

    public void setEstadoPermanecia(char estadoPermanecia) {
        this.estadoPermanecia = estadoPermanecia;
    }

    public int getIdCabecera() {
        return idCabecera;
    }

    public void setIdCabecera(int idCabecera) {
        this.idCabecera = idCabecera;
    }

    public String getIdentificadorPedido() {
        return identificadorPedido;
    }

    public void setIdentificadorPedido(String identificadorPedido) {
        this.identificadorPedido = identificadorPedido;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public float getValorDescuento() {
        return valorDescuento;
    }

    public void setValorDescuento(float valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    public BigDecimal getDescuentoPrecio() {
        return descuentoPrecio;
    }

    public void setDescuentoPrecio(BigDecimal descuentoPrecio) {
        this.descuentoPrecio = descuentoPrecio;
    }

    public BigDecimal getTotalBruto() {
        return totalBruto;
    }

    public void setTotalBruto(BigDecimal totalBruto) {
        this.totalBruto = totalBruto;
    }

    public BigDecimal getTotalNeto() {
        return totalNeto;
    }

    public void setTotalNeto(BigDecimal totalNeto) {
        this.totalNeto = totalNeto;
    }

    public enum EstadoPedido {
        T, R, S
    }
}
