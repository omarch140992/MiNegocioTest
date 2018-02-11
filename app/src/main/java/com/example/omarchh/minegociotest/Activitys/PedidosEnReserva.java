package com.example.omarchh.minegociotest.Activitys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Controlador.ControladorVentas;
import com.example.omarchh.minegociotest.DialogFragments.DialogDatePickerSelect;
import com.example.omarchh.minegociotest.DialogFragments.dialogSelectCustomer;
import com.example.omarchh.minegociotest.Model.mCabeceraPedido;
import com.example.omarchh.minegociotest.Model.mCustomer;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapterPedidos;

import java.util.Calendar;
import java.util.List;

public class PedidosEnReserva extends AppCompatActivity implements View.OnClickListener, DialogDatePickerSelect.interfaceFecha, dialogSelectCustomer.DatosCliente, RvAdapterPedidos.interfaceListaPedidos {
    RecyclerView rv;
    RvAdapterPedidos rvAdapterPedidos;
    ControladorVentas controladorVentas;
    Button btnSelectDate1, btnSelectDate2;
    int fechaInicio, fechaFinal;
    int year, month, day;
    int idCliente;
    byte origen;
    ImageButton btnDelete;
    TextView txtSelectCliente;
    dialogSelectCustomer selectCustomer;
    Intent i;
    BdConnectionSql bdConnectionSql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_en_reserva);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        controladorVentas = new ControladorVentas();
        bdConnectionSql = BdConnectionSql.getSinglentonInstance();
        rv = (RecyclerView) findViewById(R.id.rvPedidosGuardados);
        btnSelectDate1 = (Button) findViewById(R.id.btnSelectDate1);
        btnSelectDate2 = (Button) findViewById(R.id.btnSelectDate2);
        btnDelete = (ImageButton) findViewById(R.id.btnDeleteCliente);
        txtSelectCliente = (TextView) findViewById(R.id.txtSeleccionarCliente);
        btnSelectDate1.setOnClickListener(this);
        btnSelectDate2.setOnClickListener(this);
        txtSelectCliente.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        i = getIntent();
        rvAdapterPedidos = new RvAdapterPedidos();
        rvAdapterPedidos.setListenerPedidos(this);
        rv.setAdapter(rvAdapterPedidos);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));


        idCliente = 0;
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
        fechaFinal = (year * 10000) + (month * 100) + day;
        c.add(Calendar.DAY_OF_MONTH, -5);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
        fechaInicio = (year * 10000) + (month * 100) + day;

        btnSelectDate1.setText(convertirFormatoFecha(fechaInicio));
        btnSelectDate2.setText(convertirFormatoFecha(fechaFinal));

        new DownloadListPedidos().execute(idCliente, fechaInicio, fechaFinal);
    }

    private String convertirFormatoFecha(int fecha) {
        int anio = fecha / 10000;
        int mes = (fecha % 10000) / 100;
        int dia = fecha % 100;
        if (dia < 10) {
            return "0" + String.valueOf(dia) + "/" + String.valueOf(mes) + "/" + String.valueOf(anio);
        }

        return String.valueOf(dia) + "/" + String.valueOf(mes) + "/" + String.valueOf(anio);
    }

    private void MostrarDialogDatePicker(byte origen, int year, int month, int day) {
        DialogDatePickerSelect dialogDatePickerSelect = new DialogDatePickerSelect(origen, year, month, day);
        dialogDatePickerSelect.setFechaListener(this);
        DialogFragment dialogFragment = dialogDatePickerSelect;
        dialogFragment.show(this.getFragmentManager(), "Dialog Fecha");
    }


    @Override
    public void onClick(View v) {
        int a, m, d;
        switch (v.getId()) {
            case R.id.btnSelectDate1:
                origen = 1;
                a = fechaInicio / 10000;
                m = (fechaInicio % 10000) / 100;
                d = fechaInicio % 100;
                MostrarDialogDatePicker(origen, a, m, d);
                break;
            case R.id.btnSelectDate2:
                origen = 2;
                a = fechaFinal / 10000;
                m = (fechaFinal % 10000) / 100;
                d = fechaFinal % 100;
                MostrarDialogDatePicker(origen, a, m, d);
                break;
            case R.id.btnDeleteCliente:
                EliminarDatosCliente();
                new DownloadListPedidos().execute(idCliente, fechaInicio, fechaFinal);
                break;
            case R.id.txtSeleccionarCliente:
                MostrarDialogSeleccionCliente();
                break;

        }
    }

    private void EliminarDatosCliente() {
        idCliente = 0;
        txtSelectCliente.setText("Seleccione cliente para la busqueda");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void getFechaSelecionada(int day, int month, int year, byte origen) {

        if (origen == 1) {

            this.year = year;
            this.month = month;
            this.day = day;
            fechaInicio = (year * 10000) + (month * 100) + day;
            new DownloadListPedidos().execute(idCliente, fechaInicio, fechaFinal);
            btnSelectDate1.setText(convertirFormatoFecha(fechaInicio));
        } else if (origen == 2) {
            this.year = year;
            this.month = month;
            this.day = day;
            fechaFinal = (year * 10000) + (month * 100) + day;

            new DownloadListPedidos().execute(idCliente, fechaInicio, fechaFinal);
            btnSelectDate2.setText(convertirFormatoFecha(fechaFinal));
        }
    }

    private void MostrarDialogSeleccionCliente() {

        selectCustomer = new dialogSelectCustomer(this);
        DialogFragment dialogFragment = selectCustomer;
        selectCustomer.setListenerCliente(this);
        dialogFragment.show(this.getFragmentManager(), "Selecciona Cliente");
    }

    @Override
    public void obtenerDato(mCustomer customer) {
        idCliente = customer.getiId();
        txtSelectCliente.setText(customer.getcName() + " " + customer.getcApellidoMaterno());

        new DownloadListPedidos().execute(idCliente, fechaInicio, fechaFinal);
    }


    @Override
    public void ObtenerDatoPedidoSupender(mCabeceraPedido cabeceraPedido) {
        if (bdConnectionSql.ObtenerEstadoPedido(cabeceraPedido.getIdCabecera())) {
            controladorVentas.CambiarEstadoPedidoSuspender(cabeceraPedido.getIdCabecera());
            new DownloadListPedidos().execute(idCliente, fechaInicio, fechaFinal);
        } else {
            DialogPedidoNoDisponible();
            new DownloadListPedidos().execute(idCliente, fechaInicio, fechaFinal);
        }
    }

    @Override
    public void ObtenerDatoPedidoRegresarVenta(mCabeceraPedido cabeceraPedido) {


        if (bdConnectionSql.ObtenerEstadoPedido(cabeceraPedido.getIdCabecera())) {
            controladorVentas.CambiarEstadoPedidoSuspender(cabeceraPedido.getIdCabecera());
            bdConnectionSql.ActualizarTerminalPedido(cabeceraPedido.getIdCabecera());
            i.putExtra("RESULTADOID", cabeceraPedido.getIdCabecera());
            setResult(RESULT_OK, i);
            finish();
        } else {
            DialogPedidoNoDisponible();
            new DownloadListPedidos().execute(idCliente, fechaInicio, fechaFinal);
        }

    }

    @Override
    public void ActualizarListaPedidos() {

        new DownloadListPedidos().execute(idCliente, fechaInicio, fechaFinal);
    }

    public void DialogPedidoNoDisponible() {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alerta").setIcon(R.drawable.alert).setPositiveButton("Regresar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setMessage("El pedido no se encuentra disponible");

        dialog = builder.create();
        dialog.show();
    }

    private class DownloadListPedidos extends AsyncTask<Integer, Void, List<mCabeceraPedido>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<mCabeceraPedido> doInBackground(Integer... integers) {

            return controladorVentas.getListaPedidosReserva(integers[0], integers[1], integers[2]);
        }


        @Override
        protected void onPostExecute(List<mCabeceraPedido> mCabeceraPedidos) {
            super.onPostExecute(mCabeceraPedidos);
            rvAdapterPedidos.AddElement(mCabeceraPedidos);
        }
    }
}
