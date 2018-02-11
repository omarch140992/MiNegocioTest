package com.example.omarchh.minegociotest.Controlador;

import android.content.Context;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.mProduct;

import java.util.List;

/**
 * Created by OMAR CHH on 05/10/2017.
 */

public class ControladorProductos   {

    BdConnectionSql bdConnectionSql;
    Context context;
    public ControladorProductos(Context context){
        bdConnectionSql = BdConnectionSql.getSinglentonInstance();
        this.context=context;
    }


    public List<mProduct> getListaProductos() {
        // metodo busqueda : Todos los productos=100  Por Id detalle=101   Por Id sin Imagen =102  Por parametro =103

        byte metodo = 100;
        return bdConnectionSql.getProductList(0, "", metodo);
    }

    public List<mProduct> getListaProductosPorParametro(String parametroBusqueda) {
        // metodo busqueda : Todos los productos=100  Por Id detalle=101   Por Id sin Imagen =102  Por parametro =103

        byte metodo = 103;
        return bdConnectionSql.getProductList(0, parametroBusqueda, metodo);
    }

    public mProduct getProductIdSinImagen(int idProducto) {
        byte metodo = 102;
        return bdConnectionSql.getProductList(idProducto, "", metodo).get(0);
    }

    public mProduct getProductoPorIdImagen(int idProducto) {
        byte metodo = 101;
        return bdConnectionSql.getProductList(idProducto, "", metodo).get(0);
    }

    public void GuardarDatos(mProduct product, String Accion) {

        switch (Accion){
            case Constantes.EstadoProducto.NuevoProducto:
                if(bdConnectionSql.addProduct(product)) {
                    if(product.getbImage()!=null) {
                        bdConnectionSql.saveImageProduct(product);
                    }
                    Toast.makeText(context,"Producto->"+product.getcProductName()+" AGREGADO CON EXITO",Toast.LENGTH_SHORT).show();
                }
                else{

                    Toast.makeText(context,"Error al agregar el producto",Toast.LENGTH_SHORT).show();

                }
                break;

            case Constantes.EstadoProducto.EditarProducto:
                if(bdConnectionSql.UpdateProduct(product)){
                    Toast.makeText(context,"Producto->"+product.getcProductName()+" EDITADO CON EXITO",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"Error al modificar",Toast.LENGTH_SHORT).show();

                }
                break;

        }

    }


}
