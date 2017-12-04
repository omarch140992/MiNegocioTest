package com.example.omarchh.minegociotest.DialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.omarchh.minegociotest.R;

/**
 * Created by OMAR CHH on 01/12/2017.
 */

public class dialogSelectCustomer extends DialogFragment {
    Dialog dialog;
    Context context;
    EditText edtBusquedaCliente;
    ListView listView;

    public dialogSelectCustomer(Context context) {
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v=((Activity)context).getLayoutInflater().inflate(R.layout.busqueda_cliente_venta,null);

        return dialog;
    }
}
