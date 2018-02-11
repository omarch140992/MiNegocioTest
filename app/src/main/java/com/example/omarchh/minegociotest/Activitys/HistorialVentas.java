package com.example.omarchh.minegociotest.Activitys;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.DialogFragments.DialogDatePickerSelect;
import com.example.omarchh.minegociotest.DialogFragments.dialogSelectCustomer;
import com.example.omarchh.minegociotest.Model.mCustomer;
import com.example.omarchh.minegociotest.Model.mVenta;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapterListVentas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistorialVentas extends AppCompatActivity implements DialogDatePickerSelect.interfaceFecha, View.OnClickListener, dialogSelectCustomer.DatosCliente, RvAdapterListVentas.ListenerCabeceraVenta {

    byte origen;
    RvAdapterListVentas rvAdapter;
    RecyclerView rv;
    List<mVenta> list;
    int fechaInicio, fechaFinal;
    int year, month, day;
    int idCliente;
    TextView txtTotalVentas;
    BdConnectionSql bdConnectionSql;
    Dialog dialog;
    int lenResult = 0;
    BigDecimal valorTotalVenta;
    int position = 0;

    dialogSelectCustomer selectCustomer;

    ImageButton btnDelete;
    Button btnSelectDate1, btnSelectDate2, btnSelectCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_ventas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rvAdapter = new RvAdapterListVentas();
        rvAdapter.setListenerCabeceraVenta(this);
        rv = (RecyclerView) findViewById(R.id.rvHistorialVentas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(rvAdapter);
        list = new ArrayList<>();

        valorTotalVenta = new BigDecimal(0);
        idCliente = 0;

        btnSelectDate1 = (Button) findViewById(R.id.btnDateVentas1);
        btnSelectDate2 = (Button) findViewById(R.id.btnDateVentas2);
        btnSelectCliente = (Button) findViewById(R.id.btnSelectCliente);
        btnDelete = (ImageButton) findViewById(R.id.btnDeleteCliente);
        txtTotalVentas = (TextView) findViewById(R.id.txtTotalDatoVentas);

        btnSelectDate1.setOnClickListener(this);
        btnSelectDate2.setOnClickListener(this);
        btnSelectCliente.setOnClickListener(this);

        bdConnectionSql = BdConnectionSql.getSinglentonInstance();

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
        fechaFinal = (year * 10000) + (month * 100) + day;
        c.add(Calendar.DAY_OF_MONTH, -2);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
        fechaInicio = (year * 10000) + (month * 100) + day;


        btnSelectDate1.setText(convertirFormatoFecha(fechaInicio));
        btnSelectDate2.setText(convertirFormatoFecha(fechaFinal));
        btnDelete.setOnClickListener(this);
        txtTotalVentas.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", valorTotalVenta));
        ActualizarListaVentas();
    }

    @Override
    public void getFechaSelecionada(int day, int month, int year, byte origen) {
        if (origen == 1) {
            this.year = year;
            this.month = month;
            this.day = day;
            fechaInicio = (year * 10000) + (month * 100) + day;
            btnSelectDate1.setText(convertirFormatoFecha(fechaInicio));
            ActualizarListaVentas();

        } else if (origen == 2) {

            this.year = year;
            this.month = month;
            this.day = day;
            fechaFinal = (year * 10000) + (month * 100) + day;
            btnSelectDate2.setText(convertirFormatoFecha(fechaFinal));
            ActualizarListaVentas();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {

        int a, m, d;
        switch (v.getId()) {

            case R.id.btnDateVentas1:
                origen = 1;
                a = fechaInicio / 10000;
                m = (fechaInicio % 10000) / 100;
                d = fechaInicio % 100;
                MostrarDialogDatePicker(origen, a, m, d);
                break;

            case R.id.btnDateVentas2:
                origen = 2;
                a = fechaFinal / 10000;
                m = (fechaFinal % 10000) / 100;
                d = fechaFinal % 100;
                MostrarDialogDatePicker(origen, a, m, d);
                break;
            case R.id.btnDeleteCliente:
                EliminarDatosCliente();

                break;
            case R.id.btnSelectCliente:
                MostrarDialogSeleccionCliente();
                break;
        }
    }

    private void EliminarDatosCliente() {
        idCliente = 0;
        btnSelectCliente.setText("Seleccione cliente para la busqueda");
        ActualizarListaVentas();
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
        btnSelectCliente.setText(customer.getcName() + " " + customer.getcApellidoMaterno());
        ActualizarListaVentas();
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
    public void EstadoVentaCancelada() {

        ActualizarListaVentas();

    }

    @Override
    public void CancelarVenta(int idCabeceraVenta, int position) {

        this.position = position;
        new CancelarVenta().execute(idCabeceraVenta);

    }

    private void MostrarProgressDialog(String mensaje) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(mensaje);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        dialog = progressDialog;
        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void ActualizarListaVentas() {
        if (bdConnectionSql.VerificarConexion()) {
            new DownloadListVentas().execute(fechaInicio, fechaFinal, idCliente);
        } else {
            Toast.makeText(this, "Error en la conexión", Toast.LENGTH_LONG).show();
        }
    }

    private void SumarVentas(int lenResult, List<mVenta> mVentas) {

        for (int i = 0; i < lenResult; i++) {
            if (mVentas.get(i).getcEstadoVenta().equals("N")) {
                valorTotalVenta = valorTotalVenta.add(mVentas.get(i).getTotalNeto());
            }
        }

    }

    private class CancelarVenta extends AsyncTask<Integer, Void, Byte> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MostrarProgressDialog("Cancelando Venta");
        }

        @Override
        protected Byte doInBackground(Integer... integers) {
            return bdConnectionSql.cancelarVenta(integers[0]);
        }

        @Override
        protected void onPostExecute(Byte aByte) {
            super.onPostExecute(aByte);

            if (aByte == 2) {
                rvAdapter.ChangeData("C", position);

                SumarVentas(list.size(), list);
            } else if (aByte == 1) {
                Toast.makeText(getBaseContext(), "Error al cancelar la venta", Toast.LENGTH_SHORT).show();
            } else if (aByte == 0) {
                Toast.makeText(getBaseContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }
    }

    private class DownloadListVentas extends AsyncTask<Integer, Void, List<mVenta>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MostrarProgressDialog("Cargando datos");
            rvAdapter.RemoveList();

        }


        @Override
        protected List<mVenta> doInBackground(Integer... integers) {
            return bdConnectionSql.getCabeceraVentaList(integers[0], integers[1], integers[2]);
        }

        @Override
        protected void onPostExecute(List<mVenta> mVentas) {
            super.onPostExecute(mVentas);
            dialog.dismiss();
            valorTotalVenta = new BigDecimal(0);
            if (mVentas != null) {
                lenResult = mVentas.size();
                list = mVentas;
                if (lenResult > 0) {
                    rvAdapter.AddElement(mVentas);
                    SumarVentas(lenResult, mVentas);
                } else {
                    rvAdapter.RemoveList();
                    valorTotalVenta = new BigDecimal(0);
                    Toast.makeText(getBaseContext(), "No existen resultados", Toast.LENGTH_LONG).show();
                }
            } else {
                rvAdapter.RemoveList();
                valorTotalVenta = new BigDecimal(0);
                Toast.makeText(getBaseContext(), "No existen resultados", Toast.LENGTH_LONG).show();
            }
            txtTotalVentas.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", valorTotalVenta));
        }
    }
}

