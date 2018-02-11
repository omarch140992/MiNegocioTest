package com.example.omarchh.minegociotest.DialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.omarchh.minegociotest.Controlador.ControladorVendedor;
import com.example.omarchh.minegociotest.Model.mVendedor;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapterVendedor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 01/12/2017.
 */

public class dialogSelectVendedor extends DialogFragment implements View.OnClickListener, TextWatcher, RvAdapterVendedor.Vendedor {

    Dialog dialog;
    Context context;
    EditText edtBusquedaVendedor;
    ListView listView;
    RecyclerView rv;
    RvAdapterVendedor rvAdapterVendedor;
    ControladorVendedor controladorVendedor;
    InformacionVendedor informacionVendedor;
    ImageButton imgArrowBack;
    Button btnAñadirVendedor;


    public dialogSelectVendedor(Context context) {
        this.context = context;
    }

    public void setListenerVendedor(InformacionVendedor informacionVendedor) {
        this.informacionVendedor = informacionVendedor;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View v=((Activity)context).getLayoutInflater().inflate(R.layout.busqueda_vendedores_venta,null);
        controladorVendedor = new ControladorVendedor();
        imgArrowBack = (ImageButton) v.findViewById(R.id.imgArrowBack);
        edtBusquedaVendedor = (EditText) v.findViewById(R.id.edtBusquedaVendedor);
        listView=(ListView)v.findViewById(R.id.listViewVendedoresEnVenta);
        rv = (RecyclerView) v.findViewById(R.id.rvVendedoresParaVenta);
        btnAñadirVendedor = (Button) v.findViewById(R.id.btnAgregarVendedor);
        if (getTag().equals("Mostrar Vendedores Registro")) {
            btnAñadirVendedor.setVisibility(View.VISIBLE);
        }

        btnAñadirVendedor.setOnClickListener(this);
        rvAdapterVendedor = new RvAdapterVendedor();
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setHasFixedSize(true);
        rv.setAdapter(rvAdapterVendedor);
        rvAdapterVendedor.setListener(this);
        new DownloadListVendedor().execute("");
        edtBusquedaVendedor.addTextChangedListener(this);


        imgArrowBack.setOnClickListener(this);
        dialog = builder.setView(v).create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgArrowBack) {

            dialog.dismiss();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        new DownloadListVendedor().execute(s.toString());

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void ObtenerVendedor(mVendedor vendedor) {
        informacionVendedor.ObtenerInformacion(vendedor);
        dialog.dismiss();
    }

    public void SetVisibilityBtnAñadir() {

        btnAñadirVendedor.setVisibility(View.VISIBLE);
    }

    public interface InformacionVendedor {
        public void ObtenerInformacion(mVendedor vendedor);
    }

    private class DownloadListVendedor extends AsyncTask<String, Void, List<mVendedor>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<mVendedor> doInBackground(String... strings) {
            List<mVendedor> list = new ArrayList<>();
            if (strings[0].length() <= 1) {
                list = controladorVendedor.getAllVendedor();
            } else if (strings[0].length() > 1) {
                list = controladorVendedor.getBusquedaNombreApellido(strings[0]);
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<mVendedor> mVendedors) {
            super.onPostExecute(mVendedors);
            rvAdapterVendedor.AddElement(mVendedors);

        }
    }
}
