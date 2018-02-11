package com.example.omarchh.minegociotest.Activitys;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.omarchh.minegociotest.AsyncTask.AsyncCaja;
import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.DialogFragments.DialogAgregarEntrada;
import com.example.omarchh.minegociotest.DialogFragments.DialogAperturaCaja;
import com.example.omarchh.minegociotest.Fragment.detalleFlujoCaja;
import com.example.omarchh.minegociotest.Fragment.resumenFlujoCaja;
import com.example.omarchh.minegociotest.Model.mCierre;
import com.example.omarchh.minegociotest.Model.mDetalleMovCaja;
import com.example.omarchh.minegociotest.Model.mResumenFlujoCaja;
import com.example.omarchh.minegociotest.Model.mResumenMedioPago;
import com.example.omarchh.minegociotest.Model.mResumenTotalVentas;
import com.example.omarchh.minegociotest.Model.mVentasPorHora;
import com.example.omarchh.minegociotest.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CajaFlujoActivity extends AppCompatActivity implements View.OnClickListener, resumenFlujoCaja.CrearVista, detalleFlujoCaja.VistaCreada, DialogAgregarEntrada.EntradaRetiro, DialogAperturaCaja.AperturaCaja, AsyncCaja.ListenerAperturaCaja {

    final int CODE_RESULT_ID_CAJA = 1;
    mCierre cierre;
    resumenFlujoCaja flujoCajaresumen;
    detalleFlujoCaja flujoCajaDetalle;
    FloatingActionButton btnAddDinero, btnRetDinero, btnCerrarCaja, btnAbrirCaja, btnEnviarEmail, btnImpResumen;
    FloatingActionsMenu floatingActionsMenu;
    BdConnectionSql bdConnectionSql = BdConnectionSql.getSinglentonInstance();
    DialogAgregarEntrada dialogAgregarEntrada;
    List<mDetalleMovCaja> detalleMovCajaList;
    DialogAperturaCaja aperturaCaja;
    AsyncCaja asyncCaja;
    View view;
    int idCierreCaja = 0;


    View vistaTrasnparente;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_caja_flujo);
        dialogAgregarEntrada = new DialogAgregarEntrada();
        dialogAgregarEntrada.setEntradaRetiroListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        asyncCaja = new AsyncCaja(this);
        asyncCaja.setListenerAperturaCaja(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Flujo de caja");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        floatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.fab);
        view = findViewById(R.id.main_content);
        flujoCajaresumen = new resumenFlujoCaja();
        flujoCajaresumen.setCrearVista(this);
        flujoCajaDetalle = new detalleFlujoCaja();
        flujoCajaDetalle.setVistaCreada(this);
        detalleMovCajaList = new ArrayList<>();
        aperturaCaja = new DialogAperturaCaja();
        aperturaCaja.setAperturaCaja(this);
        btnAddDinero = (FloatingActionButton) findViewById(R.id.btnIngresos);
        btnRetDinero = (FloatingActionButton) findViewById(R.id.btnRetiros);
        btnCerrarCaja = (FloatingActionButton) findViewById(R.id.btnCerrarCaja);
        btnAbrirCaja = (FloatingActionButton) findViewById(R.id.btnAbrirCaja);
        btnImpResumen = (FloatingActionButton) findViewById(R.id.btnImprimir);
        btnEnviarEmail = (FloatingActionButton) findViewById(R.id.btnSendEmail);
        btnAddDinero.setOnClickListener(this);
        btnRetDinero.setOnClickListener(this);
        btnCerrarCaja.setOnClickListener(this);
        btnAbrirCaja.setOnClickListener(this);
        btnEnviarEmail.setOnClickListener(this);
        btnImpResumen.setOnClickListener(this);


        cierre = new mCierre();
        cierre.setIdCierre(getIntent().getIntExtra("idCierre", 0));
        cierre.setEstadoCierre(getIntent().getStringExtra("EstadoCierre"));

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_caja_flujo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        DialogFragment dialogFragment = dialogAgregarEntrada;
        switch (v.getId()) {

            case R.id.btnIngresos:
                floatingActionsMenu.collapse();
                dialogAgregarEntrada.setCierre(cierre);
                dialogFragment.show(getFragmentManager(), "Entrada");

                break;

            case R.id.btnRetiros:
                floatingActionsMenu.collapse();
                dialogAgregarEntrada.setCierre(cierre);
                dialogFragment.show(getFragmentManager(), "Retiro");
                break;

            case R.id.btnCerrarCaja:
                DialogCerrarCaja();
                floatingActionsMenu.collapse();
                break;

            case R.id.btnAbrirCaja:
                DialogAperturaCaja();
                floatingActionsMenu.collapse();
                break;

            case R.id.btnSendEmail:

                floatingActionsMenu.collapse();
                break;

            case R.id.btnImprimir:

                floatingActionsMenu.collapse();
                break;

        }


    }

    @Override
    public void VistaCreada() {

    }

    @Override
    public void ObtenerIdCierre(int idCierre) {
    }

    @Override
    public void seCreoVista() {

        new DescargarCabereceResumen().execute(cierre.getIdCierre());


    }

    @Override
    public void EstadoFloatingButton(boolean estado) {

        VisibilidadBotonFloat(estado);
    }

    protected void VisibilidadBotonFloat(boolean estado) {
        if (estado == false) {

            if (floatingActionsMenu.getVisibility() == View.VISIBLE)
                floatingActionsMenu.setVisibility(View.GONE);


        } else if (estado == true) {
            if (floatingActionsMenu.getVisibility() == View.GONE)
                floatingActionsMenu.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void InformacionRetiroEntrada() {
        new ResumenMovCaja().execute(cierre.getIdCierre());
        Snackbar snackbar = Snackbar.make(findViewById(R.id.fab), "Se realizo el movimiento", Snackbar.LENGTH_LONG)
                .setAction("Action", null).setDuration(4000);

        View snack = snackbar.getView();
        snack.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView tv = (TextView) snack.findViewById(android.support.design.R.id.snackbar_text);

        tv.setTextColor(Color.parseColor("#ffffff"));
        snackbar.show();


    }

    @Override
    public void VerificarCajaAbierta(BigDecimal montoApertura) {

        asyncCaja.AperturarCaja(montoApertura);
    }

    @Override
    public void ConfirmacionAperturaCaja() {

        asyncCaja.ObtenerIdCierreCaja();

    }

    @Override
    public void ExisteCierreAperturado(int idCierre) {
        cierre.setIdCierre(idCierre);
        CargarActivity();
    }

    @Override
    public void AperturarCaja() {

    }

    @Override
    public void ConfirmarCierreCaja() {
        asyncCaja.ObtenerIdCierreCaja();

    }

    private void DialogCerrarCaja() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alerta").setMessage("¿Desea cerrar la caja actual?").setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                asyncCaja.CerrarCaja(cierre.getIdCierre());
            }
        }).create().show();

    }

    private void DialogAperturaCaja() {

        DialogFragment dialogFragment = aperturaCaja;
        dialogFragment.show(getFragmentManager(), "Apertura Caja");

    }

    public void CargarActivity() {
        Intent intent = new Intent(this, CajaFlujoActivity.class);
        intent.putExtra("idCierre", cierre.getIdCierre());
        startActivity(intent);
        finish();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_caja_flujo, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */


    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);

        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {

                case 0:
                    fragment = flujoCajaresumen;
                    break;
                case 1:
                    fragment = flujoCajaDetalle;
                    break;

            }

            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return fragment;
        }


        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {

                case 0:
                    return "Resumen";

                case 1:
                    return "Detalle";

            }
            return null;

        }

    }

    private class DescargarCabereceResumen extends AsyncTask<Integer, Void, mResumenTotalVentas> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            floatingActionsMenu.setVisibility(View.GONE);
            flujoCajaresumen.OcultarPantalla();

        }

        @Override
        protected mResumenTotalVentas doInBackground(Integer... integers) {
            cierre = bdConnectionSql.getCabeceraCierreCaja(integers[0]);
            return bdConnectionSql.ObtenerCabeceraResumen(cierre.getIdCierre());
        }

        @Override
        protected void onPostExecute(mResumenTotalVentas mResumenTotalVentas) {
            super.onPostExecute(mResumenTotalVentas);
            flujoCajaresumen.ProcesoCabeceraResumen(mResumenTotalVentas);
            floatingActionsMenu.setVisibility(View.VISIBLE);
            flujoCajaresumen.MostrarPantalla();
            if (cierre.getEstadoCierre().equals("A")) {
                btnAbrirCaja.setVisibility(View.GONE);
            } else if (cierre.getEstadoCierre().equals("C")) {
                btnAddDinero.setVisibility(View.GONE);
                btnRetDinero.setVisibility(View.GONE);
                btnAbrirCaja.setVisibility(View.VISIBLE);
                btnCerrarCaja.setVisibility(View.GONE);
            }


            new CargarBarChart().execute(cierre.getIdCierre());
        }
    }

    private class ObtenerMovimientosCaja extends AsyncTask<Integer, Void, List<mDetalleMovCaja>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            flujoCajaDetalle.OcultarPantalla();
        }

        @Override
        protected List<mDetalleMovCaja> doInBackground(Integer... integers) {
            return bdConnectionSql.getMovimientoCaja(cierre.getIdCierre());

        }

        @Override
        protected void onPostExecute(List<mDetalleMovCaja> mDetalleMovCajas) {
            super.onPostExecute(mDetalleMovCajas);
            flujoCajaDetalle.AgregarListaDetalle(mDetalleMovCajas);
            flujoCajaDetalle.MostrarPantalla();
        }


    }

    private class CargarBarChart extends AsyncTask<Integer, Void, List<mVentasPorHora>> {

        @Override
        protected List<mVentasPorHora> doInBackground(Integer... integers) {
            return bdConnectionSql.getVentasPorHora(integers[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<mVentasPorHora> mVentasPorHoras) {
            super.onPostExecute(mVentasPorHoras);
            flujoCajaresumen.ObtenerDatosVentas(mVentasPorHoras);
            new ResumenMovCaja().execute(cierre.getIdCierre());
        }
    }

    private class ResumenMovCaja extends AsyncTask<Integer, Void, Byte> {
        List<mResumenFlujoCaja> flujoCajaList;
        List<mResumenMedioPago> resumenMedioPagoList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Byte doInBackground(Integer... integers) {

            flujoCajaList = bdConnectionSql.getResumenFlujoCaja(integers[0]);
            resumenMedioPagoList = bdConnectionSql.getResumenMP(integers[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Byte aByte) {
            super.onPostExecute(aByte);

            flujoCajaresumen.ModificarDatosCabecera(cierre);
            flujoCajaresumen.ModificarResumenFlujoCaja(flujoCajaList, resumenMedioPagoList);

            new ObtenerMovimientosCaja().execute(cierre.getIdCierre());

        }

    }
}


















