package com.example.omarchh.minegociotest.Activitys;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.DialogFragments.DialogDatePickerSelect;
import com.example.omarchh.minegociotest.DialogFragments.DialogPagoCtaCte;
import com.example.omarchh.minegociotest.MedioPagoSpinnerAdapter;
import com.example.omarchh.minegociotest.Model.mMedioPago;
import com.example.omarchh.minegociotest.R;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class ActivityPagoCtaCte extends AppCompatActivity implements DialogPagoCtaCte.ListenerCalculadoraPago, View.OnClickListener, DialogDatePickerSelect.interfaceFecha {

    int idCliente;
    int idMetodoPago;
    int dia, mes, anio, fecha;
    BdConnectionSql bdConnectionSql;
    Button btnIngresarMonto, btnConfirmarPago, btnElegirFecha;
    Spinner spinnerMedioPago;
    TextInputLayout edtObservacion;
    TextView txtMonto;
    DialogPagoCtaCte pagoCtaCte;
    DialogFragment dialogFragment;
    BigDecimal monto;
    Dialog dialog;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_cta_cte);


        bdConnectionSql = BdConnectionSql.getSinglentonInstance();
        btnIngresarMonto = (Button) findViewById(R.id.btnIngresarMonto);
        btnConfirmarPago = (Button) findViewById(R.id.btnConfirmarPago);
        btnElegirFecha = (Button) findViewById(R.id.btnSeleccionarFecha);
        spinnerMedioPago = (Spinner) findViewById(R.id.spn_Medio_Pago);
        edtObservacion = (TextInputLayout) findViewById(R.id.edtObservacion);

        txtMonto = (TextView) findViewById(R.id.txtMonto);
        pagoCtaCte = new DialogPagoCtaCte();
        pagoCtaCte.setListenerCalculadoraPago(this);
        edtObservacion.getEditText().setText("");
        btnIngresarMonto.setOnClickListener(this);


        btnConfirmarPago.setOnClickListener(this);
        btnElegirFecha.setOnClickListener(this);

        monto = new BigDecimal(0);
        idCliente = getIntent().getIntExtra("idcliente", 0);
        txtMonto.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", monto));

        dialogFragment = pagoCtaCte;
        dialogFragment.show(getFragmentManager(), "Pago");

        List<mMedioPago> list = bdConnectionSql.getMPagos();
        Iterator<mMedioPago> i = list.iterator();

        while (i.hasNext()) {

            mMedioPago a = i.next();

            if (a.isPorCobrar() == true) {
                i.remove();
            }
        }


        MedioPagoSpinnerAdapter medioPagoSpinnerAdapter = new MedioPagoSpinnerAdapter(this, list);
        spinnerMedioPago.setAdapter(medioPagoSpinnerAdapter);
        spinnerMedioPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idMetodoPago = ((mMedioPago) parent.getItemAtPosition(position)).getiIdMedioPago();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final Calendar c = Calendar.getInstance();
        anio = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH) + 1;
        dia = c.get(Calendar.DAY_OF_MONTH);
        fecha = (anio * 10000) + (mes * 100) + dia;
        btnElegirFecha.setText(convertirFormatoFecha(fecha));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void CapturarMontoPago(BigDecimal monto) {
        this.monto = monto;
        txtMonto.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", monto));
    }

    @Override
    public void onClick(View v) {
        int a, m, d;
        switch (v.getId()) {

            case R.id.btnIngresarMonto:
                dialogFragment.show(getFragmentManager(), "Pago");

                break;

            case R.id.btnConfirmarPago:

                if (monto.compareTo(new BigDecimal(0)) == 0) {

                    Toast.makeText(this, "Ingresa un monto mayor a CERO", Toast.LENGTH_SHORT).show();
                } else {
                    new ProcesarPagoCtaCte().execute();
                }
                //bdConnectionSql.ProcesarPagoCtaCte()

                break;
            case R.id.btnSeleccionarFecha:

                a = fecha / 10000;
                m = (fecha % 10000) / 100;
                d = fecha % 100;
                MostrarDialogDatePicker((byte) 1, a, m, d);
                break;

        }
    }

    private ProgressDialog mostrarProgressDialog(String Mensaje) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(Mensaje);

        return progressDialog;

    }

    @Override
    public void getFechaSelecionada(int day, int month, int year, byte origen) {

        int fechaTemp = (year * 10000) + (month * 100) + day;
        if (fecha >= fechaTemp) {
            this.anio = year;
            this.mes = month;
            this.dia = day;
            fecha = (year * 10000) + (month * 100) + day;
            btnElegirFecha.setText(convertirFormatoFecha(fecha));
        } else {

            Toast.makeText(this, "Seleccione una fecha menor o igual a la actual", Toast.LENGTH_LONG).show();
        }

    }

    private String convertirFormatoFecha(int fecha) {
        int anio = fecha / 10000;
        int mes = (fecha % 10000) / 100;
        int dia = fecha % 100;
        if (dia < 10) {
            return " Seleccionar fecha de pago: \n 0" + String.valueOf(dia) + "/" + String.valueOf(mes) + "/" + String.valueOf(anio);
        }

        return "Seleccionar fecha de pago: \n" + String.valueOf(dia) + "/" + String.valueOf(mes) + "/" + String.valueOf(anio);
    }

    private void MostrarDialogDatePicker(byte origen, int year, int month, int day) {
        DialogDatePickerSelect dialogDatePickerSelect = new DialogDatePickerSelect(origen, year, month, day);
        dialogDatePickerSelect.setFechaListener(this);
        DialogFragment dialogFragment = dialogDatePickerSelect;
        dialogFragment.show(this.getFragmentManager(), "Dialog Fecha");
    }

    private class ProcesarPagoCtaCte extends AsyncTask<Void, Void, Byte> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = mostrarProgressDialog("Procesando pago...");
            dialog.show();
        }

        @Override
        protected Byte doInBackground(Void... voids) {
            return bdConnectionSql.ProcesarPagoCtaCte(monto, idMetodoPago, edtObservacion.getEditText().getText().toString(), idCliente);
        }

        @Override
        protected void onPostExecute(Byte aByte) {
            super.onPostExecute(aByte);
            if (aByte == 0) {
                Toast.makeText(getBaseContext(), "No se realizó el pago", Toast.LENGTH_SHORT).show();
                Toast.makeText(getBaseContext(), "Verifique su conexión", Toast.LENGTH_SHORT).show();
            } else if (aByte == 99) {
                Toast.makeText(getBaseContext(), "El metodo de pago no se encuentra disponible", Toast.LENGTH_LONG).show();
            } else if (aByte == 100) {
                Toast.makeText(getBaseContext(), "Error al realizar el pago", Toast.LENGTH_LONG).show();

            } else if (aByte == 101) {
                Toast.makeText(getBaseContext(), "El pago se realizo con éxito", Toast.LENGTH_LONG).show();
                onBackPressed();
                finish();
            }

            dialog.dismiss();
        }


    }

}

