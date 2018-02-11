package com.example.omarchh.minegociotest.Model;

import java.math.BigDecimal;

/**
 * Created by OMAR CHH on 13/12/2017.
 */

public class mMedioPago {

    private int iIdMedioPago;
    private String cCodigoMedioPago;
    private int iIdTipoPago;
    private String cDescripcionMedioPago;
    private boolean bImprimeTicket;
    private int IdTipoPago;
    private boolean porCobrar;

    private String idImagen;
    private BigDecimal dValorMinimo;

    public mMedioPago() {

        iIdMedioPago = 0;
        cCodigoMedioPago = "";
        iIdTipoPago = 0;
        cDescripcionMedioPago = "";
        bImprimeTicket = false;
        idImagen = "";
        dValorMinimo = new BigDecimal(0);
    }

    public mMedioPago(int iIdMedioPago, String cCodigoMedioPago, int iIdTipoPago, String cDescripcionMedioPago, boolean bImprimeTicket, String idImagen, BigDecimal dValorMinimo) {
        this.iIdMedioPago = iIdMedioPago;
        this.cCodigoMedioPago = cCodigoMedioPago;
        this.iIdTipoPago = iIdTipoPago;
        this.cDescripcionMedioPago = cDescripcionMedioPago;
        this.bImprimeTicket = bImprimeTicket;
        this.idImagen = idImagen;
        this.dValorMinimo = dValorMinimo;
    }

    public int getIdTipoPago() {
        return IdTipoPago;
    }

    public void setIdTipoPago(int idTipoPago) {
        IdTipoPago = idTipoPago;
    }

    public boolean isPorCobrar() {
        return porCobrar;
    }

    public void setPorCobrar(boolean porCobrar) {
        this.porCobrar = porCobrar;
    }

    public int getiIdMedioPago() {
        return iIdMedioPago;
    }

    public void setiIdMedioPago(int iIdMedioPago) {
        this.iIdMedioPago = iIdMedioPago;
    }

    public String getcCodigoMedioPago() {
        return cCodigoMedioPago;
    }

    public void setcCodigoMedioPago(String cCodigoMedioPago) {
        this.cCodigoMedioPago = cCodigoMedioPago;
    }

    public int getiIdTipoPago() {
        return iIdTipoPago;
    }

    public void setiIdTipoPago(int iIdTipoPago) {
        this.iIdTipoPago = iIdTipoPago;
    }

    public String getcDescripcionMedioPago() {
        return cDescripcionMedioPago;
    }

    public void setcDescripcionMedioPago(String cDescripcionMedioPago) {
        this.cDescripcionMedioPago = cDescripcionMedioPago;
    }

    public boolean isbImprimeTicket() {
        return bImprimeTicket;
    }

    public void setbImprimeTicket(boolean bImprimeTicket) {
        this.bImprimeTicket = bImprimeTicket;
    }

    public String getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(String idImagen) {
        this.idImagen = idImagen;
    }

    public BigDecimal getdValorMinimo() {
        return dValorMinimo;
    }

    public void setdValorMinimo(BigDecimal dValorMinimo) {
        this.dValorMinimo = dValorMinimo;
    }
}
