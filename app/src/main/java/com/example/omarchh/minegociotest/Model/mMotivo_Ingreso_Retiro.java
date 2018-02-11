package com.example.omarchh.minegociotest.Model;

/**
 * Created by OMAR CHH on 30/01/2018.
 */

public class mMotivo_Ingreso_Retiro {

    int idMotivo;
    String descripcionMotivo;

    public mMotivo_Ingreso_Retiro() {
        idMotivo = 0;
        descripcionMotivo = "";
    }

    public mMotivo_Ingreso_Retiro(int idMotivo, String descripcionMotivo) {
        this.idMotivo = idMotivo;
        this.descripcionMotivo = descripcionMotivo;
    }

    public int getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(int idMotivo) {
        this.idMotivo = idMotivo;
    }

    public String getDescripcionMotivo() {
        return descripcionMotivo;
    }

    public void setDescripcionMotivo(String descripcionMotivo) {
        this.descripcionMotivo = descripcionMotivo;
    }
}
