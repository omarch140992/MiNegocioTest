package com.example.omarchh.minegociotest.Controlador;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.mVendedor;

import java.util.List;

/**
 * Created by OMAR CHH on 07/12/2017.
 */

public class ControladorVendedor {

    BdConnectionSql bdConnectionSql = new BdConnectionSql();


    public mVendedor getVendedorPorId(int id) {
        return bdConnectionSql.getVendedor(id, "", Constantes.ParametrosVendedor.BusquedaPorId).get(0);
    }

    public List<mVendedor> getBusquedaNombreApellido(String parametro) {

        return bdConnectionSql.getVendedor(0, parametro, Constantes.ParametrosVendedor.BusquedaPorNombre);
    }

    public List<mVendedor> getAllVendedor() {

        return bdConnectionSql.getVendedor(0, "", Constantes.ParametrosVendedor.TodosVendedores);
    }


}
