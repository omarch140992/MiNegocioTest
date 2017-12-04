package com.example.omarchh.minegociotest.Model;

/**
 * Created by OMAR CHH on 17/11/2017.
 */

 public class ProductoEnVenta {

    int IdProducto;
    int itemNum;
    String ProductName;
    float cantidad;
    float cantidadEnStock;
    float precioOriginal;
    float descuento;
    float precioVentaFinal;

    public ProductoEnVenta(){
        itemNum=0;
        IdProducto=0;
        ProductName="";
        cantidad=0;
        descuento=0;
        precioVentaFinal=0;
        cantidadEnStock=0;
        precioVentaFinal=0;

    }

    public ProductoEnVenta(int idProducto,String productName,int itemNum, float cantidad, float cantidadEnStock, float precioOriginal,float precioVentaFinal,float descuento) {
        this.IdProducto = idProducto;
        this.ProductName = productName;
        this.cantidadEnStock=cantidadEnStock;
        this.cantidad = cantidad;
        this.descuento = descuento;
        this.precioVentaFinal = precioVentaFinal;
        this.precioOriginal=precioVentaFinal;
        this.itemNum=itemNum;
    }

    public float getCantidadEnStock() {
        return cantidadEnStock;
    }

    public void setCantidadEnStock(float cantidadEnStock) {
        this.cantidadEnStock = cantidadEnStock;
    }

    public float getPrecioOriginal() {
        return precioOriginal;
    }

    public void setPrecioOriginal(float precioOriginal) {
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

    public float getPrecioVentaFinal() {
        return precioVentaFinal;
    }

    public void setPrecioVentaFinal(float precioVentaFinal) {
        this.precioVentaFinal = precioVentaFinal;
    }
    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }
}
