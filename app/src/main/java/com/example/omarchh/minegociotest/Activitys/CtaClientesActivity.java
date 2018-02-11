package com.example.omarchh.minegociotest.Activitys;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Model.mCustomer;
import com.example.omarchh.minegociotest.Model.mSaldoCliente;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapterSaldoCliente;

import java.util.List;

public class CtaClientesActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener, RvAdapterSaldoCliente.ClickItem {

    RecyclerView rv;
    CheckBox cb;
    BdConnectionSql bdConnectionSql = BdConnectionSql.getSinglentonInstance();
    RvAdapterSaldoCliente rvAdapterSaldoCliente;
    mCustomer customer;
    ProgressBar pb;
    SearchView searchView;
    byte saldoCero = 0;
    String nombreCliente = "";
    int idCliente;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_saldo_clientes, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.searchToolbar).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Busqueda por cliente");
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cta_clientes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        customer = new mCustomer();
        saldoCero = 0;
        nombreCliente = "";
        pb = (ProgressBar) findViewById(R.id.pbSaldo);
        rvAdapterSaldoCliente = new RvAdapterSaldoCliente();
        rvAdapterSaldoCliente.setClickItem(this);
        rv = (RecyclerView) findViewById(R.id.rvCuentasCliente);
        cb = (CheckBox) findViewById(R.id.cb_IgnorarCliente);
        cb.setOnClickListener(this);
        rv.setHasFixedSize(false);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(rvAdapterSaldoCliente);
        rv.setVisibility(View.GONE);
        pb.setVisibility(View.GONE);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cb_IgnorarCliente:

                if (cb.isChecked()) {
                    saldoCero = 1;
                    new DownloadListSaldos().execute();
                } else if (!cb.isChecked()) {

                    saldoCero = 0;
                    new DownloadListSaldos().execute();
                }

                break;


        }

    }


    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        nombreCliente = newText;
        new DownloadListSaldos().execute();

        return false;
    }

    @Override
    public void getCliente(int idCliente) {
        this.idCliente = idCliente;
        new VerificarConexion().execute();
    }

    private void MostrarDetalleCuentaCorriente() {

        Intent intent = new Intent(this, Activity_cta_x_cliente.class);
        intent.putExtra("idcliente", idCliente);
        intent.putExtra("Nombre", customer.toString());
        intent.putExtra("Email", customer.getcEmail());
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new DownloadListSaldos().execute();

    }

    private class VerificarConexion extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            return bdConnectionSql.VerificarConexion();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean == true) {
                customer = bdConnectionSql.getClienteId(idCliente);
                MostrarDetalleCuentaCorriente();
            } else {
                Toast.makeText(getBaseContext(), "Error al obtener la información", Toast.LENGTH_SHORT);
                Toast.makeText(getBaseContext(), "Verifique su conexión", Toast.LENGTH_SHORT);
            }
        }
    }

    private class DownloadListSaldos extends AsyncTask<Void, Void, List<mSaldoCliente>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rv.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);

        }

        @Override
        protected List<mSaldoCliente> doInBackground(Void... voids) {
            return bdConnectionSql.getSaldosClientes(saldoCero, nombreCliente);
        }

        @Override
        protected void onPostExecute(List<mSaldoCliente> mSaldoClientes) {
            super.onPostExecute(mSaldoClientes);
            pb.setVisibility(View.GONE);
            if (mSaldoClientes != null) {
                rv.setVisibility(View.VISIBLE);
                rvAdapterSaldoCliente.AddList(mSaldoClientes);
            } else {

                Toast.makeText(getBaseContext(), "No existe informacion", Toast.LENGTH_LONG).show();

            }

        }

    }
}
