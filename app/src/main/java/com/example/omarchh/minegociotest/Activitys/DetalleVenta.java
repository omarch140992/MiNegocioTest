package com.example.omarchh.minegociotest.Activitys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.mDetalleVenta;
import com.example.omarchh.minegociotest.Model.mPagosEnVenta;
import com.example.omarchh.minegociotest.Model.mVenta;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapterDetalleVenta;
import com.example.omarchh.minegociotest.RvAdapterPagosEnVenta;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetalleVenta extends AppCompatActivity implements View.OnClickListener {

    BdConnectionSql bdConnectionSql = BdConnectionSql.getSinglentonInstance();
    TextView txtFechaVenta, txtEstadoVenta, txtNombreCliente, txtNombreVendedor, txtValorBruto, txtValorDescuento, txtValorNeto, txtValorCambio;
    RvAdapterDetalleVenta adapter;
    RvAdapterPagosEnVenta rvAdapterPagosEnVenta;
    RecyclerView rvDetallePedido, rvMetodosPago;
    ScrollView svContent;
    ProgressBar progressBar;
    FloatingActionButton fcancelButton;
    FloatingActionsMenu floatingActionsMenu;
    mVenta venta;
    List<mDetalleVenta> listDetalleVenta;
    List<mPagosEnVenta> listPagosVenta;
    Dialog dialog;
    int idCabeceraVenta = 0;
    byte type = 2;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_venta);
        idCabeceraVenta = getIntent().getIntExtra("idCabeceraVenta", 0);
        progressBar = (ProgressBar) findViewById(R.id.pbDetalleVenta);
        venta = new mVenta();
        listDetalleVenta = new ArrayList<>();
        listPagosVenta = new ArrayList<>();
        svContent = (ScrollView) findViewById(R.id.svContent);
        txtFechaVenta = (TextView) findViewById(R.id.txtFechaPedido);
        txtEstadoVenta = (TextView) findViewById(R.id.txtEstadoVenta);
        txtNombreCliente = (TextView) findViewById(R.id.txtNombreCliente);
        txtNombreVendedor = (TextView) findViewById(R.id.txtNombreVendedor);
        txtValorBruto = (TextView) findViewById(R.id.txtValorBrutoDato);
        txtValorDescuento = (TextView) findViewById(R.id.valorDescuentoDato);
        txtValorNeto = (TextView) findViewById(R.id.txtValorNetoDato);
        rvDetallePedido = (RecyclerView) findViewById(R.id.rvDetallePedido);
        rvMetodosPago = (RecyclerView) findViewById(R.id.rvMetodosDePago);
        txtValorCambio = (TextView) findViewById(R.id.txtValorCambio);
        fcancelButton = (FloatingActionButton) findViewById(R.id.fabCancelarVenta);
        fcancelButton.setOnClickListener(this);
        floatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.floating_button_Venta);

        if (idCabeceraVenta == 0) {

            finish();
            Toast.makeText(this, "Error al descargar la venta", Toast.LENGTH_SHORT).show();
        }

        adapter = new RvAdapterDetalleVenta();
        rvDetallePedido.setAdapter(adapter);
        rvDetallePedido.setLayoutManager(new LinearLayoutManager(this));
        rvDetallePedido.setHasFixedSize(true);
        rvDetallePedido.setNestedScrollingEnabled(false);
        rvMetodosPago.setLayoutManager(new LinearLayoutManager(this));
        rvMetodosPago.setHasFixedSize(true);
        rvAdapterPagosEnVenta = new RvAdapterPagosEnVenta(0);
        rvAdapterPagosEnVenta.setTypeView(type);
        rvMetodosPago.setAdapter(rvAdapterPagosEnVenta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        new DownloadDetalle().execute(idCabeceraVenta);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fabCancelarVenta:
                ConfirmarCancelarVenta();
                break;

        }
    }

    private void ConfirmarCancelarVenta() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ATENCIÓN").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                new CancelarVenta().execute();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setMessage("¿Desea cancelar la venta?").show();

    }

    private void MostrarProgressDialog() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Descargando informacion");
        dialog = progressDialog;
        dialog.show();
    }

    private ProgressDialog mostrarMensaje() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Procesando solicitud");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        return progressDialog;

    }

    private class DownloadDetalle extends AsyncTask<Integer, Void, mVenta> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            svContent.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            floatingActionsMenu.setVisibility(View.GONE);
        }

        @Override
        protected mVenta doInBackground(Integer... integers) {
            venta = bdConnectionSql.getCabeceraVenta(integers[0]);
            if (venta != null) {
                listDetalleVenta = bdConnectionSql.getListDetalleVenta(venta.getIdCabeceraVenta());
                listPagosVenta = bdConnectionSql.getPagosVenta(venta.getIdCabeceraVenta());
            }
            return venta;
        }


        @Override
        protected void onPostExecute(mVenta result) {
            super.onPostExecute(result);
            if (result != null) {
                txtFechaVenta.setText(dateFormat.format(result.getFechaVentaRealizada()).toString());
                if (result.getcEstadoVenta().equals("N")) {
                    txtEstadoVenta.setText("COMPLETA");
                    txtEstadoVenta.setTextColor(Color.parseColor("#FF53F637"));
                } else if (result.getcEstadoVenta().equals("C")) {
                    fcancelButton.setVisibility(View.GONE);
                    txtEstadoVenta.setText("CANCELADA");
                    txtEstadoVenta.setTextColor(Color.parseColor("#FFF73838"));
                }
                if (result.getIdCliente() != 0) {
                    txtNombreCliente.setText(result.getNombreCliente());
                }

                if (result.getIdVendedor() != 0) {
                    txtNombreVendedor.setText(result.getNombreVendedor());
                }
                txtValorBruto.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", result.getTotalBruto()));
                txtValorDescuento.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", result.getDescuento()));
                txtValorNeto.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", result.getTotalNeto()));
                txtValorCambio.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", result.getCambio()));
                if (listDetalleVenta != null) {
                    adapter.AddElement(listDetalleVenta);

                } else {

                    finish();
                    Toast.makeText(getBaseContext(), "Error al descargar la informacion del detalle", Toast.LENGTH_SHORT).show();
                }

                if (listPagosVenta != null) {
                    rvAdapterPagosEnVenta.AgregarPagosEnVenta(listPagosVenta);
                } else {

                    finish();
                    Toast.makeText(getBaseContext(), "Error al descargar la informacion del detalle", Toast.LENGTH_SHORT).show();

                }
            } else if (result == null) {

                finish();
                Toast.makeText(getBaseContext(), "Error al descargar la informacion", Toast.LENGTH_SHORT).show();
            }
            svContent.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            floatingActionsMenu.setVisibility(View.VISIBLE);

        }
    }

    private class CancelarVenta extends AsyncTask<Void, Void, Byte> {
        byte respuesta = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = mostrarMensaje();
            dialog.show();
        }

        @Override
        protected Byte doInBackground(Void... voids) {
            return bdConnectionSql.getEstadoVenta(idCabeceraVenta);
        }

        @Override
        protected void onPostExecute(Byte aByte) {
            super.onPostExecute(aByte);
            if (aByte == 0) {
                Toast.makeText(getBaseContext(), "Error al cancelar la venta", Toast.LENGTH_SHORT).show();

                Toast.makeText(getBaseContext(), "Verifique su conexión", Toast.LENGTH_SHORT).show();
            } else if (aByte == 1) {

                Toast.makeText(getBaseContext(), "La venta ya se encuentra cancelada", Toast.LENGTH_SHORT).show();
                txtEstadoVenta.setText("CANCELADA");
                txtEstadoVenta.setTextColor(Color.parseColor("#FFF73838"));
            } else if (aByte == 2) {
                respuesta = bdConnectionSql.cancelarVenta(idCabeceraVenta);
                if (respuesta == 2) {

                    Toast.makeText(getBaseContext(), "Venta cancelada con éxito", Toast.LENGTH_SHORT).show();
                    txtEstadoVenta.setText("CANCELADA");
                    txtEstadoVenta.setTextColor(Color.parseColor("#FFF73838"));
                    floatingActionsMenu.collapse();
                    fcancelButton.setVisibility(View.GONE);

                } else if (respuesta == 0) {


                    Toast.makeText(getBaseContext(), "Error al cancelar la venta", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getBaseContext(), "Verifique su conexión", Toast.LENGTH_SHORT).show();

                }

            }
            dialog.dismiss();

        }
    }

}
