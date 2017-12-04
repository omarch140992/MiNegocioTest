package com.example.omarchh.minegociotest.Model;

/**
 * Created by OMAR CHH on 19/10/2017.
 */

public class mCustomer {



    private int iId;
    private String cName;
    private String cAlias;
    private String cNumberPhone;
    private String cDireccion;
    private String iIdCustomer;
    private String cEmail;

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

    public mCustomer() {
        cName="";
        cAlias="";
        cNumberPhone="";
        cEmail="";
        iId=0;
        iIdCustomer="";
    }

    public mCustomer(int iId, String cName, String cAlias, String cNumberPhone, String cEmail) {
        this.iId = iId;
        this.cName = cName;
        this.cAlias = cAlias;
        this.cNumberPhone = cNumberPhone;
        this.cEmail = cEmail;
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

    public String getcAlias() {
        return cAlias;
    }

    public void setcAlias(String cAlias) {
        this.cAlias = cAlias;
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


}
