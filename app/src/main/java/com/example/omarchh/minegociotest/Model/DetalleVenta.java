package com.example.omarchh.minegociotest.Model;

import java.util.ArrayList;
import java.util.List;

public class DetalleVenta {

    int itemPosition;
    List<ProductoEnVenta> productoEnVentaList;
    int longitud;
    int cantidad;
    float cantidadProducto;
    float nuevoSubtotal;
    public DetalleVenta(){
        productoEnVentaList=new ArrayList<>();
        longitud=0;
        cantidad=0;
        cantidadProducto=0;

    }

    public void EliminarProducto(int position){
        productoEnVentaList.remove(position);
        longitud--;
    }

    public List<ProductoEnVenta> getProductoEnVentaList() {
        return productoEnVentaList;
    }
    public void setProductoEnVentaList(List<ProductoEnVenta> list){
        this.productoEnVentaList=list;
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
    public void aumentarCantidadPorUnidadEnDetalleVentaPorUnidad(){
        int ultimaPosicion=productoEnVentaList.size()-1;
        cantidad= Math.round(productoEnVentaList.get(ultimaPosicion).getCantidad()+1);
        productoEnVentaList.get(ultimaPosicion).setCantidad(cantidad);
    }

    public void ModificarSubtotalVentaPorUnidad(){

        int ultimaPosicion=productoEnVentaList.size()-1;
        nuevoSubtotal=productoEnVentaList.get(ultimaPosicion).getCantidad()*productoEnVentaList.get(ultimaPosicion).precioOriginal;
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

    public String TotalCobrar(){

        float Total=0f;
        int longitud=productoEnVentaList.size();
        for(int i=0;i<longitud;i++){
         Total+=productoEnVentaList.get(i).getPrecioVentaFinal();
        }
        return "S/"+String.format("%.2f",Total);

    }
}


