package com.example.omarchh.minegociotest.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DetalleVenta {

    int itemPosition;
    List<ProductoEnVenta> productoEnVentaList;
    int longitud;
    int cantidad;
    float cantidadProducto;
    BigDecimal nuevoSubtotal;
    public DetalleVenta(){
        productoEnVentaList=new ArrayList<>();
        longitud=0;
        cantidad=0;
        cantidadProducto=0;
        nuevoSubtotal = new BigDecimal(0);
    }

    public void EliminarProducto(int position){
        productoEnVentaList.remove(position);
        longitud--;
    }

    public List<ProductoEnVenta> getProductoEnVentaList() {
        return productoEnVentaList;
    }
    public void setProductoEnVentaList(List<ProductoEnVenta> list){
        productoEnVentaList.addAll(list);
        longitud = list.size();
    }


    public int getLongitud() {
        return longitud;
    }

    public int cantidadTotalProductos(){
        cantidad=0;
        longitud=productoEnVentaList.size();
       if(longitud>0){
                for(int i=0;i<longitud;i++){
                    cantidad+=productoEnVentaList.get(i).getCantidad();
                }
           }
       return cantidad;
    }

    public ProductoEnVenta getProductoEnPosicion(int position) {
        return productoEnVentaList.get(position);
    }
    public void aumentarCantidadPorUnidadEnDetalleVentaPorUnidad(){
        //
        int ultimaPosicion = longitud - 1;
        cantidad= Math.round(productoEnVentaList.get(ultimaPosicion).getCantidad()+1);
        productoEnVentaList.get(ultimaPosicion).setCantidad(cantidad);
    }

    public void ModificarSubtotalVentaPorUnidad(){

        //
        int ultimaPosicion = longitud - 1;
        nuevoSubtotal = productoEnVentaList.get(ultimaPosicion).precioOriginal.multiply(BigDecimal.valueOf(productoEnVentaList.get(ultimaPosicion).getCantidad()));
        productoEnVentaList.get(ultimaPosicion).setPrecioVentaFinal(nuevoSubtotal);


    }


    public ProductoEnVenta getObtenerUltimoProducto(){
        int ultimaPosicion=productoEnVentaList.size()-1;
        return productoEnVentaList.get(ultimaPosicion);
    }
    public int RetornarCantidadTotalProductosEnVenta(){
        cantidad=0;
        int c=0;
        if(longitud>=0){
            for(int i=0;i<longitud;i++){
                c+=Math.round(productoEnVentaList.get(i).cantidad);

            }

        }

        return c;
    }

    public String ObtenerNombreUltimoProducto() {
        if (longitud > 0) {
            return productoEnVentaList.get(longitud - 1).getProductName();
        }
        return "Sin productos";
    }

    public BigDecimal ObtenerPrecioUltimoProducto() {
        if (longitud > 0) {
            return productoEnVentaList.get(longitud - 1).getPrecioOriginal();
        }
        return new BigDecimal(0);
    }

    public ProductoEnVenta getUltimoProductoIngresado() {

        return getProductoEnVentaList().get(longitud - 1);
    }

    public BigDecimal TotalCobrar() {

        BigDecimal Total = new BigDecimal(0);

        int longitud=productoEnVentaList.size();
        for(int i=0;i<longitud;i++){
            //Total+=productoEnVentaList.get(i).getPrecioVentaFinal();

            Total = Total.add(productoEnVentaList.get(i).getPrecioVentaFinal());
        }
        return Total;

    }
}


