package com.example.omarchh.minegociotest.Model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by OMAR CHH on 28/01/2018.
 */

public class mCierre {

    private int idCierre;
    private Timestamp fechaApertura;
    private Timestamp fechaCierre;
    private String estadoCierre;


    public mCierre() {

        idCierre = 0;
        estadoCierre = "";
        java.util.Date utilDate = new Date(); //fecha actual
        long lnMilisegundos = utilDate.getTime();

        fechaApertura = new Timestamp(lnMilisegundos);
        fechaCierre = new Timestamp(lnMilisegundos);
    }

    public int getIdCierre() {
        return idCierre;
    }

    public void setIdCierre(int idCierre) {
        this.idCierre = idCierre;
    }

    public Timestamp getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Timestamp fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Timestamp getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Timestamp fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getEstadoCierre() {
        return estadoCierre;
    }

    public void setEstadoCierre(String estadoCierre) {
        this.estadoCierre = estadoCierre;
    }
}
