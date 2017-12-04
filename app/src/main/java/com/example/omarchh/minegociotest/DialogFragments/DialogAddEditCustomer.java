package com.example.omarchh.minegociotest.DialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.omarchh.minegociotest.InterfaceDataCustomerProvider;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.ValidarExistenciaDialogAlert;

/**
 * Created by OMAR CHH on 26/11/2017.
 */

public class DialogAddEditCustomer extends DialogFragment{

    Context context;
    InterfaceDataCustomerProvider interfaceDataCustomerProvider;
    EditText edtName,edtAlias,edtPhone,edtEmail,edtDireccion;
    ValidarExistenciaDialogAlert validarExistenciaDialogAlert;

    public void setValidarExistenciaDialogAlert(ValidarExistenciaDialogAlert validarExistenciaDialogAlert){
        this.validarExistenciaDialogAlert=validarExistenciaDialogAlert;
    }

    public DialogAddEditCustomer(Context context) {
        this.context=context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View v=((Activity)context).getLayoutInflater().inflate(R.layout.agregar_cliente,null);


        edtName=(EditText)v.findViewById(R.id.edtCustomerName);
        edtAlias=(EditText)v.findViewById(R.id.edtCustomerAlias);
        edtPhone=(EditText)v.findViewById(R.id.edtCustomerPhone);
        edtEmail=(EditText)v.findViewById(R.id.edtCustomerEmail);
        edtDireccion=(EditText)v.findViewById(R.id.edtCustomerDirection);

        builder.setView(v);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                interfaceDataCustomerProvider.setDataCustomer(edtName.getText().toString(),edtAlias.getText().toString(),edtPhone.getText().toString(),edtEmail.getText().toString(),edtDireccion.getText().toString());
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.setTitle("Agregar Cliente").create();
    }

    public void setListenerCustomer(InterfaceDataCustomerProvider interfaceDataCustomerProvider){
        this.interfaceDataCustomerProvider = interfaceDataCustomerProvider;
    }



}
