package com.example.omarchh.minegociotest.Activitys;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.omarchh.minegociotest.AsyncTask.AsyncCaja;
import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.ConexionBd.DbHelper;
import com.example.omarchh.minegociotest.Controlador.ControladorCliente;
import com.example.omarchh.minegociotest.DialogFragments.DialogAperturaCaja;
import com.example.omarchh.minegociotest.DialogFragments.DialogCargaAsync;
import com.example.omarchh.minegociotest.DialogFragments.DialogSelectPrinter;
import com.example.omarchh.minegociotest.Fragment.FragmentInventario;
import com.example.omarchh.minegociotest.Fragment.VentasFragment;
import com.example.omarchh.minegociotest.Fragment.clienteFragment;
import com.example.omarchh.minegociotest.Fragment.proovedorFragment;
import com.example.omarchh.minegociotest.R;

import java.math.BigDecimal;

public class PantallaPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DialogAperturaCaja.AperturaCaja, AsyncCaja.ListenerAperturaCaja {

    static final int CODE_REQUEST_RESULT = 1;
    FragmentInventario fragmentInventario;
    VentasFragment fragmentVentas;
    DbHelper helper;
    DialogCargaAsync dialogCargaAsync;
    DialogAperturaCaja dialogAperturaCaja;
    AsyncCaja asyncCaja;
    BdConnectionSql bdConnectionSql = BdConnectionSql.getSinglentonInstance();
    ControladorCliente c;
    int id = 0;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        helper = new DbHelper(this);
        c = new ControladorCliente();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            int permissionCheck = ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_PHONE_STATE );
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                Log.i("Mensaje", "No se tiene permiso.");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE }, 225);
            } else {
                Log.i("Mensaje", "Se tiene permiso!");
            } return;
        }
        if (mTelephony.getDeviceId() != null) {
            myIMEI = mTelephony.getDeviceId();
            myIMEI.toString();

        }
        Toast.makeText(this,myIMEI,Toast.LENGTH_SHORT).show();
        */
        asyncCaja = new AsyncCaja(this);
        asyncCaja.setListenerAperturaCaja(this);
        fragmentInventario=new FragmentInventario();
        fragmentVentas=new VentasFragment();
        dialogCargaAsync = new DialogCargaAsync(this);
        dialogAperturaCaja = new DialogAperturaCaja();
        dialogAperturaCaja.setAperturaCaja(this);


        onNavigationItemSelected(navigationView.getMenu().getItem(1));
        navigationView.setCheckedItem(1);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pantalla_principal, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Inventario) {
            FragmentoInventario();

        }
        else if(id== R.id.nav_Ventas){
            FragmentoVentas();

        }
        else if (id == R.id.nav_Clientes) {
            //FragmentoCliente();

        } else if (id == R.id.nav_Proveedores) {
            // FragmentoProveedor();

        } else if (id == R.id.nav_Impresoras) {

            DialogFragment dialogFragment = new DialogSelectPrinter();
            dialogFragment.show(getFragmentManager(), "Seleccionar Impresora");
        } else if (id == R.id.nav_CuentasCliente) {

            MostrarCuentasCliente();

        } else if (id == R.id.nav_Registros) {

            ActivityRegistros();

        } else if (id == R.id.nav_ControlCaja) {

            asyncCaja.ObtenerIdCierreCaja();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void ActivityRegistros() {

        Intent intent = new Intent(this, ActivityRegistros.class);
        startActivity(intent);

    }

    private void FragmentoInventario(){

        FragmentManager fm1=getSupportFragmentManager();
        FragmentTransaction ft1=fm1.beginTransaction();
        ft1.replace(R.id.content_frame,new FragmentInventario());
        ft1.commit();
    }

    private void FragmentoCompra(){


    }
    private void FragmentoVentas(){
        FragmentManager fml=getSupportFragmentManager();
        FragmentTransaction ftl=fml.beginTransaction();
        ftl.replace(R.id.content_frame,new VentasFragment());
        ftl.commit();
    }
    private void FragmentoCliente(){
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new clienteFragment()).commit();
    }
    private void FragmentoProveedor(){

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new proovedorFragment()).commit();

    }

    private void MostrarCuentasCliente() {

        Intent intent = new Intent(this, CtaClientesActivity.class);
        startActivity(intent);

    }


    public void MostrarFlujoCaja(int idCierre) {
        Intent intent = new Intent(this, CajaFlujoActivity.class);
        intent.putExtra("idCierre", idCierre);
        intent.putExtra("cEstadoCierre", "A");
        startActivity(intent);
    }

    @Override
    public void VerificarCajaAbierta(BigDecimal montoApertura) {
        bdConnectionSql.aperturarCaja(montoApertura);
    }

    @Override
    public void ConfirmacionAperturaCaja() {

    }

    @Override
    public void ExisteCierreAperturado(int idCierre) {
        MostrarFlujoCaja(idCierre);
    }

    @Override
    public void AperturarCaja() {

        DialogFragment dialogFragment = dialogAperturaCaja;
        dialogFragment.show(getFragmentManager(), "Apertura caja");
    }

    @Override
    public void ConfirmarCierreCaja() {

    }

}
