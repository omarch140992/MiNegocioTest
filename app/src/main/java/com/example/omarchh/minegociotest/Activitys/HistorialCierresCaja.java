package com.example.omarchh.minegociotest.Activitys;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.DialogFragments.DialogDatePickerSelect;
import com.example.omarchh.minegociotest.Model.mCierre;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapterHistorialCierres;

import java.util.Calendar;
import java.util.List;

public class HistorialCierresCaja extends AppCompatActivity implements View.OnClickListener, DialogDatePickerSelect.interfaceFecha, RvAdapterHistorialCierres.ObtenerCierre {

    Button btnFechaInicio, btnFechaFinal;
    int dia, mes, anio, diaf, mesf, aniof;
    int fechaInicio, fechaFinal;
    String sFechaInicio, sFechaFinal;
    Intent intent;

    RecyclerView rv;
    RvAdapterHistorialCierres rvAdapterHistorialCierres;
    BdConnectionSql bdConnectionSql = BdConnectionSql.getSinglentonInstance();

    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_cierres_caja);


        btnFechaInicio = (Button) findViewById(R.id.btnFechaInicio);
        btnFechaFinal = (Button) findViewById(R.id.btnFechaFinal);

        btnFechaFinal.setOnClickListener(this);
        btnFechaInicio.setOnClickListener(this);

        intent = getIntent();

        diaf = c.get(Calendar.DAY_OF_MONTH);
        mesf = c.get(Calendar.MONTH) + 1;
        aniof = c.get(Calendar.YEAR);
        fechaFinal = (aniof * 10000) + (mesf * 100) + diaf;
        c.add(Calendar.DAY_OF_MONTH, -5);
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH) + 1;
        anio = c.get(Calendar.YEAR);
        fechaInicio = (anio * 10000) + (mes * 100) + dia;


        btnFechaInicio.setText(convertirFormatoFecha(fechaInicio));
        btnFechaFinal.setText(convertirFormatoFecha(fechaFinal));

        rv = (RecyclerView) findViewById(R.id.rvHistorialCierres);
        rvAdapterHistorialCierres = new RvAdapterHistorialCierres();

        rvAdapterHistorialCierres.setObtenerCierre(this);


        getSupportActionBar().setTitle("Historial de caja");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(false);
        rv.setAdapter(rvAdapterHistorialCierres);


        new DonwloadHistorialCierres().execute(sFecha(dia, mes, anio), sFecha(diaf, mesf, aniof));

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

            case R.id.btnFechaInicio:

                a = fechaInicio / 10000;
                m = (fechaInicio % 10000) / 100;
                d = fechaInicio % 100;
                MostrarDatePicker((byte) 1, d, m, a);
                break;

            case R.id.btnFechaFinal:
                a = fechaFinal / 10000;
                m = (fechaFinal % 10000) / 100;
                d = fechaFinal % 100;
                MostrarDatePicker((byte) 2, d, m, a);
                break;

        }

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

    private void MostrarDatePicker(byte origen, int dia, int mes, int anio) {
        DialogDatePickerSelect datePickerSelect;
        datePickerSelect = new DialogDatePickerSelect(origen, anio, mes, dia);
        datePickerSelect.setFechaListener(this);
        DialogFragment dialogFragment = datePickerSelect;
        dialogFragment.show(getFragmentManager(), "Seleccionar Fechas");


    }

    @Override
    public void getFechaSelecionada(int day, int month, int year, byte origen) {

        if (origen == 1) {

            dia = day;
            mes = month;
            anio = year;
            fechaInicio = (year * 10000) + (month * 100) + dia;
            btnFechaInicio.setText(convertirFormatoFecha(fechaInicio));
            new DonwloadHistorialCierres().execute(sFecha(dia, mes, anio), sFecha(diaf, mesf, aniof));

        } else if (origen == 2) {

            diaf = day;
            mesf = month;
            aniof = year;
            fechaFinal = (year * 10000) + (month * 100) + day;
            btnFechaFinal.setText(convertirFormatoFecha(fechaFinal));
            new DonwloadHistorialCierres().execute(sFecha(dia, mes, anio), sFecha(diaf, mesf, aniof));

        }


    }

    public String sFecha(int d, int m, int a) {

        return String.valueOf(a) + "/" + String.valueOf(m) + "/" + String.valueOf(d);
    }

    @Override
    public void ObtenerIdCierre(int idCierre) {

        intent.putExtra("idCierre", idCierre);
        setResult(RESULT_OK, intent);
        finish();

    }


    private class DonwloadHistorialCierres extends AsyncTask<String, Void, List<mCierre>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected List<mCierre> doInBackground(String... strings) {
            return bdConnectionSql.getCierresHistorial(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(List<mCierre> mCierres) {
            super.onPostExecute(mCierres);
            if (mCierres != null) {
                rvAdapterHistorialCierres.AddElement(mCierres);
                rvAdapterHistorialCierres.notifyDataSetChanged();
            } else if (mCierres == null) {

                Toast.makeText(getBaseContext(), "No se encontraron resultados", Toast.LENGTH_SHORT).show();
            }

        }


    }
}
