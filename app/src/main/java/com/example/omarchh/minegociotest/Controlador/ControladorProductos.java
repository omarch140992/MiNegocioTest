package com.example.omarchh.minegociotest.Controlador;
import android.content.Context;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Model.mProduct;
import com.example.omarchh.minegociotest.Constantes.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by OMAR CHH on 05/10/2017.
 */

public class ControladorProductos   {

    BdConnectionSql bdConnectionSql;
    Context context;
    public ControladorProductos(Context context){
        bdConnectionSql=new BdConnectionSql();
        this.context=context;
    }

    public List<mProduct> mostrarListaProducto(){

        try {
            return bdConnectionSql.getListProduct();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<mProduct> BuscarProducto(String Parametro){
           return bdConnectionSql.searchProduct(Parametro);
    }
    public void GuardarDatos(mProduct product,String Accion){

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
