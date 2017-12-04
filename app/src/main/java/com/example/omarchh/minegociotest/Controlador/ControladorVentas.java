package com.example.omarchh.minegociotest.Controlador;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Model.DetalleVenta;
import com.example.omarchh.minegociotest.Model.ListProduct;
import com.example.omarchh.minegociotest.Model.mProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 07/11/2017.
 */

public class ControladorVentas {

    BdConnectionSql bdConnectionSql=new BdConnectionSql();
    ListProduct listaProductos;
    List<String> listaResultados;
    DetalleVenta detalleVenta;

    public ControladorVentas(){
        listaProductos=new ListProduct();
        listaResultados=new ArrayList<>();
        detalleVenta=new DetalleVenta();
    }

    public List<String> BusquedaProductoNombreCodigo(String p){
        if(!listaResultados.isEmpty()) {
            listaResultados.clear();
        }
        listaProductos.setmProducts(bdConnectionSql.getProductName(p,"",0,"","justName"));
        for(int i=0;i<listaProductos.getLongitud();i++){
            listaResultados.add(listaProductos.getmProducts().get(i).toString());
        }
        listaProductos.getLongitud();
        return listaResultados;
    }

     public mProduct GetProductBd(int id){
        return bdConnectionSql.getProductbyId(id);
    }


    public void AgregarProductoDetalleVenta(int id,float cantidad,String ProductName,float precio,float descuento){

        // detalleVenta.AgregarProducto(id,cantidad,precio,ProductName,descuento);

    }
    public void ModificarProductoDetalleVenta(int position,int id,float cantidad,float precio,float descuento){

    }





}
