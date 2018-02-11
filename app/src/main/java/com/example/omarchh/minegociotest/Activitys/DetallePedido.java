package com.example.omarchh.minegociotest.Activitys;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Controlador.ControladorVentas;
import com.example.omarchh.minegociotest.Model.DetalleVenta;
import com.example.omarchh.minegociotest.Model.ProductoEnVenta;
import com.example.omarchh.minegociotest.Model.mCabeceraPedido;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapterDetallePedido;

import java.util.List;

public class DetallePedido extends AppCompatActivity {

    ControladorVentas controladorVentas;
    TextView txtValorDescuento, txtFechaPedido, txtValorBruto, txtValorNeto, txtNombreCliente, txtNombreVendedor;
    RecyclerView rvDetalle;
    RvAdapterDetallePedido rvAdapterDetallePedido;
    int idCabeceraPedido;
    mCabeceraPedido cabeceraPedido;
    DetalleVenta detalleVenta;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);
        cabeceraPedido = new mCabeceraPedido();
        detalleVenta = new DetalleVenta();
        controladorVentas = new ControladorVentas();

        txtFechaPedido = (TextView) findViewById(R.id.txtFechaPedido);
        txtNombreCliente = (TextView) findViewById(R.id.txtNombreCliente);
        txtNombreVendedor = (TextView) findViewById(R.id.txtNombreVendedor);
        txtValorDescuento = (TextView) findViewById(R.id.valorDescuentoDato);
        txtValorBruto = (TextView) findViewById(R.id.txtValorBrutoDato);
        txtValorNeto = (TextView) findViewById(R.id.txtValorNetoDato);
        rvAdapterDetallePedido = new RvAdapterDetallePedido();
        rvDetalle = (RecyclerView) findViewById(R.id.rvDetallePedido);
        rvDetalle.setAdapter(rvAdapterDetallePedido);
        rvDetalle.setLayoutManager(new LinearLayoutManager(this));
        idCabeceraPedido = getIntent().getIntExtra("idCabeceraPedido", 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        new DownloadCabecera().execute(idCabeceraPedido);
        rvAdapterDetallePedido.AddElement(controladorVentas.getDetallePedidoId(idCabeceraPedido));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void AsignarValorCabecera(mCabeceraPedido cabeceraPedido) {
        if (cabeceraPedido.getIdCliente() != 0) {
            txtNombreCliente.setText(cabeceraPedido.getNombreCliente());
        }
        if (cabeceraPedido.getIdVendedor() != 0) {
            txtNombreVendedor.setText(cabeceraPedido.getNombreVendedor());
        }

        txtFechaPedido.setText(convertirFormatoFecha(cabeceraPedido.getFechaCreacion()));
        txtValorDescuento.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", cabeceraPedido.getDescuentoPrecio()));
        txtValorBruto.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", cabeceraPedido.getTotalBruto()));
        txtValorNeto.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", cabeceraPedido.getTotalNeto()));

    }

    public String convertirFormatoFecha(int fecha) {
        int anio = fecha / 10000;
        int mes = (fecha % 10000) / 100;
        int dia = fecha % 100;

        return String.valueOf(dia) + "/" + String.valueOf(mes) + "/" + String.valueOf(anio);
    }

    private class DownloadCabecera extends AsyncTask<Integer, Void, mCabeceraPedido> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected mCabeceraPedido doInBackground(Integer... integers) {
            return controladorVentas.getCabeceraUltimoPedido(integers[0]);
        }

        @Override
        protected void onPostExecute(mCabeceraPedido cabeceraPedido) {
            super.onPostExecute(cabeceraPedido);
            AsignarValorCabecera(cabeceraPedido);

        }
    }

    private class DownloadDetalle extends AsyncTask<Integer, Void, List<ProductoEnVenta>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<ProductoEnVenta> doInBackground(Integer... integers) {
            return controladorVentas.getDetallePedidoId(integers[0]);
        }


        @Override
        protected void onPostExecute(List<ProductoEnVenta> list) {
            super.onPostExecute(list);
            rvAdapterDetallePedido.AddElement(list);
        }
    }
}
