package com.example.omarchh.minegociotest.DialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.omarchh.minegociotest.R;

/**
 * Created by OMAR CHH on 01/12/2017.
 */

public class dialogSelectVendedor extends DialogFragment implements TextWatcher{

    Dialog dialog;
    Context context;
    EditText edtBusquedaVendedor;
    ListView listView;

    public dialogSelectVendedor(Context context) {
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View v=((Activity)context).getLayoutInflater().inflate(R.layout.busqueda_vendedores_venta,null);
        edtBusquedaVendedor=(EditText)v.findViewById(R.id.edtBusquedaVendedorEnVenta);
        listView=(ListView)v.findViewById(R.id.listViewVendedoresEnVenta);





        builder.setView(v).create();


        return dialog;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
