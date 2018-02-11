package com.example.omarchh.minegociotest.Model;

import java.math.BigDecimal;

/**
 * Created by OMAR CHH on 17/11/2017.
 */

public class ProductoEnVenta {

    int IdProducto;
    int itemNum;
    String ProductName;
    String observacion;
    float cantidad;
    float cantidadEnStock;
    BigDecimal precioOriginal;
    float descuento;
    String observacionProducto;
    BigDecimal precioVentaFinal;

    public ProductoEnVenta(){
        itemNum=0;
        IdProducto=0;
        ProductName="";
        cantidad=0;
        descuento=0;
        observacion = "";
        precioVentaFinal = new BigDecimal(0);
        cantidadEnStock=0;
        precioVentaFinal = new BigDecimal(0);

    }

    public ProductoEnVenta(int idProducto,
                           String productName,
                           int itemNum, float cantidad,
                           BigDecimal precioOriginal,
                           BigDecimal precioVentaFinal,
                           String observacion) {
        this.IdProducto = idProducto;
        this.ProductName = productName;

        this.cantidad = cantidad;
        this.observacion = observacion;
        this.precioVentaFinal = precioVentaFinal;
        this.precioOriginal = precioOriginal;
        this.itemNum=itemNum;
    }

    public float getCantidadEnStock() {
        return cantidadEnStock;
    }

    public void setCantidadEnStock(float cantidadEnStock) {
        this.cantidadEnStock = cantidadEnStock;
    }

    public BigDecimal getPrecioOriginal() {
        return precioOriginal;
    }

    public void setPrecioOriginal(BigDecimal precioOriginal) {
        this.precioOriginal = precioOriginal;
    }


    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int idProducto) {
        IdProducto = idProducto;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getPrecioVentaFinal() {
        return precioVentaFinal;
    }


    public void setPrecioVentaFinal(BigDecimal precioVentaFinal) {
        this.precioVentaFinal = precioVentaFinal;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public String getObservacionProducto() {
        return observacionProducto;
    }

    public void setObservacionProducto(String observacionProducto) {
        this.observacionProducto = observacionProducto;
    }
}

