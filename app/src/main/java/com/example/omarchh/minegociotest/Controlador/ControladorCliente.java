package com.example.omarchh.minegociotest.Controlador;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.mCustomer;

import java.util.List;

/**
 * Created by OMAR CHH on 06/12/2017.
 */

public class ControladorCliente {

    BdConnectionSql bd = new BdConnectionSql();
    boolean result = false;


    public void InsertarCliente(mCustomer cliente) {

        bd.ClienteInsertEdit(cliente, Constantes.ParametrosCliente.nuevoCliente);

    }

    public void EditarCliente(mCustomer cliente) {

        bd.ClienteInsertEdit(cliente, Constantes.ParametrosCliente.editarCliente);


    }

    public mCustomer getIdCliente(int id) {

        return bd.getClientes(id, "", Constantes.ParametrosCliente.BusquedaPorId).get(0);
    }

    public List<mCustomer> getClienteNombreApellido(String Parametro) {

        return bd.getClientes(0, Parametro, Constantes.ParametrosCliente.BusquedaPorNombreApellido);
    }

    public List<mCustomer> getAllCliente() {

        return bd.getClientes(0, "", Constantes.ParametrosCliente.TodosLosClientes);
    }


}
