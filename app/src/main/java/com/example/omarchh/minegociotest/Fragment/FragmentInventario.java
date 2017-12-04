package com.example.omarchh.minegociotest.Fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Controlador.ControladorProductos;
import com.example.omarchh.minegociotest.Model.CabeceraVenta;
import com.example.omarchh.minegociotest.Model.mProduct;
import com.example.omarchh.minegociotest.RvAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.example.omarchh.minegociotest.Activitys.AddEditProduct;
import com.example.omarchh.minegociotest.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInventario extends Fragment implements View.OnClickListener ,TextWatcher{

    ControladorProductos c;
    boolean escaneo=true;
    EditText searchView;
    Button btnScan;
    int w;
    int h;
    Button btnAgregarProducto;
    Button btnProductosEnReserva;
    RecyclerView rv;
    RvAdapter rvAdapter;
    ProgressBar mProgressBar;
    boolean regreso=false;
    LinearLayoutManager llm;
    List<mProduct> mProductList;
    BdConnectionSql bdConnectionSql;
    ImageButton imgSearch;
    TextView txtTitulo1;
    TextView txtTitulo2;
    public FragmentInventario() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        c=new ControladorProductos(getContext());
        View rootView=inflater.inflate(R.layout.fragment_fragment_inventario, container, false);
        txtTitulo1=(TextView)rootView.findViewById(R.id.txtTitulo1);
        txtTitulo2=(TextView)rootView.findViewById(R.id.txtTitulo2);
        rv=(RecyclerView)rootView.findViewById(R.id.rvInventario);
        rv.setHasFixedSize(true);
        mProgressBar=(ProgressBar)rootView.findViewById(R.id.progressBar);
        imgSearch=(ImageButton)rootView.findViewById(R.id.btnBuscarProducto);
        llm=new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        bdConnectionSql=new BdConnectionSql();
        btnAgregarProducto=(Button)rootView.findViewById(R.id.btnAgregarProducto);
        btnProductosEnReserva=(Button)rootView.findViewById(R.id.btnReservaProducto);
        btnScan=(Button)rootView.findViewById(R.id.btnScanProduct);
        searchView=(EditText)rootView.findViewById(R.id.sv_Product);
        btnAgregarProducto.setOnClickListener(this);
        btnProductosEnReserva.setOnClickListener(this);
        btnScan.setOnClickListener(this);
        mProductList=new ArrayList<>();
        rvAdapter=new RvAdapter(getContext(),1);
        rv.setAdapter(rvAdapter);
        w=0;
        h=0;
        DisplayMetrics metrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        w=metrics.widthPixels;
        h=metrics.heightPixels;

        imgSearch.setOnClickListener(this);
        imgSearch.getLayoutParams().height=h*1/16;
        searchView.addTextChangedListener(this);
        searchView.getLayoutParams().width=w*3/5;

        imgSearch.getLayoutParams().width=w*1/7;

        searchView.getLayoutParams().height=h*1/16;
        btnScan.getLayoutParams().width=w*1/4;
        btnAgregarProducto.getLayoutParams().width=w*2/5;
        btnProductosEnReserva.getLayoutParams().width=w*2/5;

        new DownloadList().execute();

        return rootView;
    }





    @Override
    public void onClick(View v) {


        if(v.getId()==R.id.btnAgregarProducto){
            Intent intentInventario=new Intent(getContext(), AddEditProduct.class);
            intentInventario.putExtra(Constantes.EstadoProducto.EstadoProducto,Constantes.EstadoProducto.NuevoProducto);
            startActivity(intentInventario);

        }
        if(v.getId()==R.id.btnReservaProducto){

            rvAdapter.clear();
            new ProductInReserve().execute();

        }
        if(v.getId()==R.id.btnScanProduct){
            ScanCode();
        }
        if(v.getId()==R.id.btnBuscarProducto){
            if(!searchView.getText().toString().isEmpty()) {
                rvAdapter.clear();
                new SearchProducto().execute(searchView.getText().toString());
            }
        }
    }

    private void ScanCode(){

        escaneo=true;
        IntentIntegrator.forSupportFragment(FragmentInventario.this).setPrompt("Escannea el codigo de barras")
        .setBeepEnabled(true).setBarcodeImageEnabled(true).initiateScan();



    }
    @Override
    public void onActivityResult(final int requestCode,int resultCode,Intent intent){

        super.onActivityResult(requestCode,resultCode,intent);
        IntentResult scanningResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);

        String scanContent=scanningResult.getContents().toString();

        searchView.setText(scanContent);
        rvAdapter.clear();
        new SearchProducto().execute(scanContent);
        Toast.makeText(getActivity(),scanContent,Toast.LENGTH_LONG).show();

    }



    @Override
    public void onPause() {
        super.onPause();
        regreso=true;

    }

    @Override
    public void onResume() {
        super.onResume();

        if(regreso) {
            if(!escaneo) {
                rvAdapter.clear();
                new DownloadList().execute();
            }
        }
        escaneo=false;
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if(searchView.getText().toString().isEmpty()){
            new DownloadList().execute();
        }
    }


    private class ProductInReserve extends  AsyncTask<Void,Void,List<mProduct>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rv.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected List<mProduct> doInBackground(Void... params) {
            return ViewProductInReserve();
        }
        @Override
        protected void onPostExecute(List<mProduct> mProductList) {
            super.onPostExecute(mProductList);
            mProgressBar.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            CargarListaEnPantalla(mProductList);
        }
    }

    private class SearchProducto extends  AsyncTask<String,Void,List<mProduct>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rv.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<mProduct> doInBackground(String... params) {

          return SearchProduct(params[0]);
        }

        @Override
        protected void onPostExecute(List<mProduct> mProductList) {
            super.onPostExecute(mProductList);
            mProgressBar.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            CargarListaEnPantalla(mProductList);
        }
    }

    private class DownloadList extends AsyncTask<Void,Void,List<mProduct>>{
        @Override
        protected void onPreExecute() {
            rv.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected List<mProduct> doInBackground(Void... params) {

            return c.mostrarListaProducto();
        }
        @Override
        protected void onPostExecute(List<mProduct> mProductList) {
            if(mProductList!=null) {
                super.onPostExecute(mProductList);
                mProgressBar.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);

                CargarListaEnPantalla(mProductList);
            }
        }


    }


    public void CargarListaEnPantalla(List<mProduct> list){

            rvAdapter.AddProduct(list);

    }
    public List<mProduct> SearchProduct(String parametro){
      return  c.BuscarProducto(parametro);
    }

    public List<mProduct> ViewProductInReserve(){

        List<mProduct>list=new ArrayList<>();

        try {

            list=bdConnectionSql.getProductInReserve();
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return list;
    }

}
