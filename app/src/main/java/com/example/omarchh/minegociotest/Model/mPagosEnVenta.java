package com.example.omarchh.minegociotest.Model;

import java.math.BigDecimal;

/**
 * Created by OMAR CHH on 09/12/2017.
 */

public class mPagosEnVenta {
    int idTipoPago;
    String cTipoPago;
    String tipoPago;
    BigDecimal cantidadPagada;

    public mPagosEnVenta() {
        idTipoPago = 0;
        cTipoPago = "";
        tipoPago = "";
        cantidadPagada = new BigDecimal(0);
    }

    public mPagosEnVenta(int idTipoPago, String cTipoPago, String tipoPago, BigDecimal cantidadPagada) {
        this.idTipoPago = idTipoPago;
        this.cTipoPago = cTipoPago;
        this.tipoPago = tipoPago;
        this.cantidadPagada = cantidadPagada;
    }

    public int getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(int idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public String getcTipoPago() {
        return cTipoPago;
    }

    public void setcTipoPago(String cTipoPago) {
        this.cTipoPago = cTipoPago;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public BigDecimal getCantidadPagada() {
        return cantidadPagada;
    }

    public void setCantidadPagada(BigDecimal cantidadPagada) {
        this.cantidadPagada = cantidadPagada;
    }
}
