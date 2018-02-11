package com.example.omarchh.minegociotest.Controlador;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Model.ProductoEnVenta;
import com.example.omarchh.minegociotest.Model.mCabeceraPedido;
import com.example.omarchh.minegociotest.Model.mCustomer;
import com.example.omarchh.minegociotest.Model.mVendedor;

import java.util.List;

/**
 * Created by OMAR CHH on 07/11/2017.
 */

public class ControladorVentas {

    BdConnectionSql bdConnectionSql;

    public ControladorVentas(){

        bdConnectionSql = BdConnectionSql.getSinglentonInstance();
    }

    public int verificarExistePedido() {
        return bdConnectionSql.VerificarExistePedido();
    }

    public mCabeceraPedido getCabeceraUltimoPedido(int id) {

        return bdConnectionSql.getCabeceraPedidoPorId(id);

    }

    public List<ProductoEnVenta> getDetallePedidoId(int id) {

        return bdConnectionSql.getDetallePedidoPorId(id);
    }

    public int CambiarEstadoPedido(int idCabeceraPedido, String identificador, String observacion) {

        return bdConnectionSql.ModificarEstadoPedido(idCabeceraPedido, identificador, observacion, mCabeceraPedido.EstadoPedido.R.toString());

    }

    public int CambiarEstadoPedidoSuspender(int idCabeceraPedido) {

        return bdConnectionSql.ModificarEstadoPedidoSuspendido(idCabeceraPedido, mCabeceraPedido.EstadoPedido.S.toString());

    }

    public int CambiarEstadoPedidoTemporal(int idCabeceraPedido) {
        return bdConnectionSql.ModificarEstadoPedidoSuspendido(idCabeceraPedido, mCabeceraPedido.EstadoPedido.T.toString());
    }

    public int GenerarNuevoPedido() {

        return bdConnectionSql.generarNuevoPedido();
    }

    public int GuardarValorVenta(int id, mCabeceraPedido cabeceraPedido) {

        return bdConnectionSql.GuardarTotalPagoCabeceraPedido(id, cabeceraPedido);
    }

    public void GuardarCabeceraPedido(int id, mVendedor vendedor, mCustomer customer) {
        bdConnectionSql.GuardarCabeceraPedido(id, vendedor, customer);
    }

    public void GuardarProductoDetallePedido(int idCabecera, ProductoEnVenta productoEnVenta, char c) {

        bdConnectionSql.GuardarDetallePedido(idCabecera, productoEnVenta, c);
    }

    public List<mCabeceraPedido> getListaPedidosReserva(int id, int fechaInicio, int fechaFinal) {

        return bdConnectionSql.getListCabeceraPedidos(fechaInicio, fechaFinal, id);
    }




}
