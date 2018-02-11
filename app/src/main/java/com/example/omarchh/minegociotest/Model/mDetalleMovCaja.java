package com.example.omarchh.minegociotest.Model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by OMAR CHH on 02/02/2018.
 */

public class mDetalleMovCaja {

    private byte tipoRegistro;
    private String nombreMedioPago;
    private String descripcionMotivo;
    private String descripcion;
    private BigDecimal monto;
    private Timestamp fechaTransaccion;

    public mDetalleMovCaja() {

        tipoRegistro = 0;
        nombreMedioPago = "";
        descripcionMotivo = "";
        descripcion = "";
        monto = new BigDecimal(0);
        java.util.Date utilDate = new Date(); //fecha actual
        long lnMilisegundos = utilDate.getTime();
        fechaTransaccion = new java.sql.Timestamp(lnMilisegundos);

    }

    public Timestamp getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Timestamp fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public String getNombreMedioPago() {
        return nombreMedioPago;
    }

    public void setNombreMedioPago(String nombreMedioPago) {
        this.nombreMedioPago = nombreMedioPago;
    }

    public String getDescripcionMotivo() {
        return descripcionMotivo;
    }

    public void setDescripcionMotivo(String descripcionMotivo) {
        this.descripcionMotivo = descripcionMotivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public byte getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(byte tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
}

