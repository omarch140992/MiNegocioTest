package com.example.omarchh.minegociotest.Model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by OMAR CHH on 23/09/2017.
 */

public class mProduct {

    private int idProduct;
    private String cKey;
    private String cProductName;
    private String cUnit;
    private float dQuantity;
    private float dQuantityReserve;
    private float dPurcharsePrice;
    private float dSalesPrice;
    private List<Float> additionalSalesPrice;
    private String cAdditionalInformation;
    private byte[] bImage;
    private BigDecimal precioVenta;
    private BigDecimal precioCompra;


    private String observacionProducto;



    public mProduct(String cKey,String cProductName,float dQuantity,float dSalesPrice,byte[] bImage){

        this.cKey=cKey;
        this.cProductName=cProductName;
        this.dQuantity=dQuantity;
        this.dSalesPrice=dSalesPrice;
        this.bImage=bImage;
    }

    public mProduct() {
        this.idProduct=0;
        this.cProductName="";
        this.cKey="";
        this.dQuantityReserve=0;
        this.dQuantity=0;
        this.dPurcharsePrice=0;
        this.dSalesPrice=0;
        this.cAdditionalInformation="";
        this.cUnit="";
        this.bImage=null;
        this.precioCompra = new BigDecimal(0);
        this.precioVenta = new BigDecimal(0);
    }

    public mProduct(int idProduct, String cKey, String cProductName, String cUnit, float dQuantity, float dQuantityReserve, float dPurcharsePrice, float dSalesPrice, String cAdditionalInformation,byte[]img) {

        this.idProduct=idProduct;
        this.cKey = cKey;
        this.cProductName = cProductName;
        this.cUnit = cUnit;
        this.dQuantity = dQuantity;
        this.dQuantityReserve = dQuantityReserve;
        this.dPurcharsePrice = dPurcharsePrice;
        this.dSalesPrice = dSalesPrice;
        this.cAdditionalInformation = cAdditionalInformation;
        this.bImage=img;

    }

    public mProduct(int idProduct, String cKey, String cProductName, String cUnit, float dQuantity, float dQuantityReserve, BigDecimal precioCompra, BigDecimal precioVenta, String cAdditionalInformation, byte[] bImage) {
        this.idProduct = idProduct;
        this.cKey = cKey;
        this.cProductName = cProductName;
        this.cUnit = cUnit;
        this.dQuantity = dQuantity;
        this.dQuantityReserve = dQuantityReserve;
        this.precioVenta = precioVenta;
        this.precioCompra = precioCompra;
        this.cAdditionalInformation = cAdditionalInformation;
        this.bImage = bImage;

    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public byte[] getbImage() {
        return bImage;
    }

    public void setbImage(byte[] imageData) {

        this.bImage = imageData;
    }

    public String getcKey() {
        return cKey;
    }

    public void setcKey(String cKey) {
        this.cKey = cKey;
    }

    public String getcProductName() {
        return cProductName;
    }

    public void setcProductName(String cProductName) {
        this.cProductName = cProductName;
    }

    public String getcUnit() {
        return cUnit;
    }

    public void setcUnit(String cUnit) {
        this.cUnit = cUnit;
    }

    public float getdQuantity() {
        return dQuantity;
    }

    public void setdQuantity(float dQuantity) {
        this.dQuantity = dQuantity;
    }

    public float getdQuantityReserve() {
        return dQuantityReserve;
    }

    public void setdQuantityReserve(float dQuantityReserve) {
        this.dQuantityReserve = dQuantityReserve;
    }

    public float getdPurcharsePrice() {
        return dPurcharsePrice;
    }

    public void setdPurcharsePrice  (float dPurcharsePrice) {
        this.dPurcharsePrice = dPurcharsePrice;
    }

    public float getdSalesPrice() {
        return dSalesPrice;
    }

    public void setdSalesPrice(float dSalesPrice) {
        this.dSalesPrice = dSalesPrice;
    }

    public List<Float> getAdditionalSalesPrice() {
        return additionalSalesPrice;
    }

    public void setAdditionalSalesPrice(List<Float> additionalSalesPrice) {
        this.additionalSalesPrice = additionalSalesPrice;
    }

    public String getcAdditionalInformation() {
        return cAdditionalInformation;
    }

    public void setcAdditionalInformation(String cAdditionalInformation) {
        this.cAdditionalInformation = cAdditionalInformation;
    }

    @Override
    public String toString() {
        final String nuevaLinea=System.getProperty("line.separator");
        return " "+cKey+nuevaLinea+ " "+cProductName;
    }


    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getObservacionProducto() {
        return observacionProducto;
    }

    public void setObservacionProducto(String observacionProducto) {
        this.observacionProducto = observacionProducto;
    }


}
