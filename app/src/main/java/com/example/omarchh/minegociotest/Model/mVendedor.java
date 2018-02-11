package com.example.omarchh.minegociotest.Model;

/**
 * Created by OMAR CHH on 07/12/2017.
 */

public class mVendedor {


    private int idVendedor;
    private String primerNombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private float comision;

    public mVendedor() {

        idVendedor = 0;
        primerNombre = "";
        apellidoMaterno = "";
        comision = 0;

    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public float getComision() {
        return comision;
    }

    public void setComision(float comision) {
        this.comision = comision;
    }

    @Override
    public String toString() {
        return primerNombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
}
