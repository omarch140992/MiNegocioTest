package com.example.omarchh.minegociotest.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by OMAR CHH on 17/11/2017.
 */

public class mCabeceraVenta {


    private int idVenta;
    private String nombreCliente;
    private String tipoDocumento;
    private String fechaEmision;
    private boolean Estado;
    private float totalVenta;
    private int numeroRuc;
    private String numeroCorrelativo;
    private String informacionAdicional;
    private byte EstadoCobrar;
    private float totalPagado;
    private float porCobrar;

    public mCabeceraVenta() {

        idVenta=0;
        nombreCliente="";
        tipoDocumento="";
        fechaEmision=GenerarFechar();
        numeroRuc=0;
        numeroCorrelativo="";
        EstadoCobrar=0;
        totalPagado=0;
        porCobrar=0;

   }

    private String GenerarFechar(){
        DateFormat dateFormat=new SimpleDateFormat("yyyymmdd");

        return dateFormat.format(new Date());
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public boolean isEstado() {
        return Estado;
    }

    public void setEstado(boolean estado) {
        Estado = estado;
    }

    public float getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(float totalVenta) {
        this.totalVenta = totalVenta;
    }

    public int getNumeroRuc() {
        return numeroRuc;
    }

    public void setNumeroRuc(int numeroRuc) {
        this.numeroRuc = numeroRuc;
    }

    public String getNumeroCorrelativo() {
        return numeroCorrelativo;
    }

    public void setNumeroCorrelativo(String numeroCorrelativo) {
        this.numeroCorrelativo = numeroCorrelativo;
    }

    public String getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public byte getEstadoCobrar() {
        return EstadoCobrar;
    }

    public void setEstadoCobrar(byte estadoCobrar) {
        EstadoCobrar = estadoCobrar;
    }

    public float getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(float totalPagado) {
        this.totalPagado = totalPagado;
    }

    public float getPorCobrar() {
        return porCobrar;
    }

    public void setPorCobrar(float porCobrar) {
        this.porCobrar = porCobrar;
    }
}
