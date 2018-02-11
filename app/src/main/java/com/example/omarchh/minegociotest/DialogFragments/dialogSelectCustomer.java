package com.example.omarchh.minegociotest.DialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
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
import android.widget.Toast;

import com.example.omarchh.minegociotest.Controlador.ControladorCliente;
import com.example.omarchh.minegociotest.Model.mCustomer;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapterCustomer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 01/12/2017.
 */

public class dialogSelectCustomer extends DialogFragment implements DialogAddEditCustomer.ListenerAddCustomer, View.OnClickListener, TextWatcher, RvAdapterCustomer.ClienteListener {
    ImageButton imgArrowBack;
    Dialog dialog;
    Context context;
    EditText edtBusquedaCliente;
    Button btnAgregar;
    ListView listView;
    RecyclerView rv;
    RvAdapterCustomer rvAdapterCustomer;
    ControladorCliente controladorCliente;
    DatosCliente datosCliente;
    DialogAddEditCustomer dialogAddEditCustomer;
    public dialogSelectCustomer(Context context) {
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View v=((Activity)context).getLayoutInflater().inflate(R.layout.busqueda_cliente_venta,null);
        controladorCliente = new ControladorCliente();
        dialogAddEditCustomer = new DialogAddEditCustomer(context);
        edtBusquedaCliente = (EditText) v.findViewById(R.id.edtBusquedaCliente);
        edtBusquedaCliente.addTextChangedListener(this);
        rv = (RecyclerView) v.findViewById(R.id.rvClientesParaVenta);
        imgArrowBack = (ImageButton) v.findViewById(R.id.imgArrowBack);
        rvAdapterCustomer = new RvAdapterCustomer();
        btnAgregar = (Button) v.findViewById(R.id.btnAgregarCliente);
        rvAdapterCustomer = new RvAdapterCustomer();
        rv.setAdapter(rvAdapterCustomer);

        rv.setLayoutManager(new LinearLayoutManager(context));
        rvAdapterCustomer.setListener(this);
        btnAgregar.setOnClickListener(this);
        dialogAddEditCustomer.setListenerAddCustomer(this);
        imgArrowBack.setOnClickListener(this);
        builder.setView(v);

        new DownloadListClientes().execute("");
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void obtenerDatosClienteSeleccionado(mCustomer customer) {
        datosCliente.obtenerDato(customer);
        dialog.dismiss();
    }

    public void setListenerCliente(DatosCliente datosCliente) {
        this.datosCliente = datosCliente;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAgregarCliente) {
            mostrarDialogAgregar();
        } else if (v.getId() == R.id.imgArrowBack) {
            dialog.dismiss();
        }
    }

    public void mostrarDialogAgregar() {

        DialogFragment dialogFragment = dialogAddEditCustomer;
        dialogFragment.show(((Activity) context).getFragmentManager(), "Agregar Cliente");
    }

    @Override
    public void actualizar() {
        new DownloadListClientes().execute("");
        Toast.makeText(context, "Se Actualiza", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        new DownloadListClientes().execute(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {

        return super.show(transaction, tag);
    }

    public interface DatosCliente {
        public void obtenerDato(mCustomer customer);
    }

    private class DownloadListClientes extends AsyncTask<String, Void, List<mCustomer>> {

        @Override
        protected List<mCustomer> doInBackground(String... strings) {
            List<mCustomer> list = new ArrayList<>();
            if (strings[0].length() <= 1) {

                list = controladorCliente.getAllCliente();
            } else if (strings[0].length() > 1) {
                list = controladorCliente.getClienteNombreApellido(strings[0]);
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<mCustomer> mCustomers) {
            super.onPostExecute(mCustomers);
            rvAdapterCustomer.Add(mCustomers);
        }

    }


}
