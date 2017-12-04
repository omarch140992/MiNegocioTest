package com.example.omarchh.minegociotest.Activitys;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.omarchh.minegociotest.ConexionBd.DbHelper;
import com.example.omarchh.minegociotest.Fragment.FragmentInventario;
import com.example.omarchh.minegociotest.Fragment.VentasFragment;
import com.example.omarchh.minegociotest.Fragment.clienteFragment;
import com.example.omarchh.minegociotest.Fragment.proovedorFragment;
import com.example.omarchh.minegociotest.R;

public class PantallaPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentInventario fragmentInventario;
    VentasFragment fragmentVentas;

    DbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        helper=new DbHelper(this);
        VerificarSiExisteUsuarioRegistrado();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentInventario=new FragmentInventario();
        fragmentVentas=new VentasFragment();
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.setCheckedItem(0);

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
            FragmentoCliente();

        } else if (id == R.id.nav_Proveedores) {
            FragmentoProveedor();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
   private void VerificarSiExisteUsuarioRegistrado(){
        Cursor c=helper.checkExistUserInDb();
        if(c.getCount()==0){
            pantallaLogin();
        }
    }

    private void pantallaLogin(){

        Intent i=new Intent (this,LogActivity.class);
        startActivity(i);
        finish();
    }

}
