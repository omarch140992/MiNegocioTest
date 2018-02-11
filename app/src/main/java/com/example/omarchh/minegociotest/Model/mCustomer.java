package com.example.omarchh.minegociotest.Model;

/**
 * Created by OMAR CHH on 19/10/2017.
 */

public class mCustomer {



    private int iId;
    private String cName;
    private String cApellidoPaterno;
    private String cApellidoMaterno;
    private String cNumberPhone;
    private String cDireccion;
    private String iIdCustomer;
    private String cEmail;

    public mCustomer() {
        cName="";
        cApellidoPaterno = "";
        cApellidoMaterno = "";
        cNumberPhone="";
        cEmail="";
        iId=0;
        iIdCustomer="";
    }

    public mCustomer(int iId, String cName, String cApellidoPaterno, String cApellidoMaterno, String cNumberPhone, String cEmail, String cDireccion) {
        this.iId = iId;
        this.cName = cName;
        this.cApellidoPaterno = cApellidoPaterno;
        this.cNumberPhone = cNumberPhone;
        this.cEmail = cEmail;
        this.cApellidoMaterno = cApellidoMaterno;
        this.cDireccion = cDireccion;
    }

    public String getcApellidoMaterno() {
        return cApellidoMaterno;
    }

    public void setcApellidoMaterno(String cApellidoMaterno) {
        this.cApellidoMaterno = cApellidoMaterno;
    }

    public String getcDireccion() {
        return cDireccion;
    }

    public void setcDireccion(String cDireccion) {
        this.cDireccion = cDireccion;
    }

    public String getiIdCustomer() {
        return iIdCustomer;
    }

    public void setiIdCustomer(String iIdCustomer) {
        this.iIdCustomer = iIdCustomer;
    }

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcApellidoPaterno() {
        return cApellidoPaterno;
    }

    public void setcApellidoPaterno(String cApellidoPaterno) {
        this.cApellidoPaterno = cApellidoPaterno;
    }

    public String getcNumberPhone() {
        return cNumberPhone;
    }

    public void setcNumberPhone(String cNumberPhone) {
        this.cNumberPhone = cNumberPhone;
    }

    public String getcEmail() {
        return cEmail;
    }

    public void setcEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    @Override
    public String toString() {
        return cName + " " + cApellidoPaterno;
    }
}
