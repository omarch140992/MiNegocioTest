package com.example.omarchh.minegociotest.DialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.omarchh.minegociotest.Controlador.ControladorCliente;
import com.example.omarchh.minegociotest.InterfaceDataCustomerProvider;
import com.example.omarchh.minegociotest.Model.mCustomer;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.ValidarExistenciaDialogAlert;

/**
 * Created by OMAR CHH on 26/11/2017.
 */

public class DialogAddEditCustomer extends DialogFragment implements View.OnClickListener {

    Context context;
    mCustomer cliente;
    ListenerAddCustomer listenerAddCustomer;
    Button btnGuardar, btnCancelar;
    InterfaceDataCustomerProvider interfaceDataCustomerProvider;
    EditText edtName, edtApellidoPaterno, edtApellidoMaterno, edtPhone, edtEmail, edtDireccion;
    ValidarExistenciaDialogAlert validarExistenciaDialogAlert;

    ControladorCliente controladorCliente;
    Dialog dialog;


    TextInputLayout tilNombre;
    TextInputLayout tilApellidoPaterno;
    TextInputLayout tilApellidoMaterno;
    TextInputLayout tilNumeroTelefono;
    TextInputLayout tilDireccion;
    TextInputLayout tilEmail;


    public DialogAddEditCustomer(Context context) {

        this.context=context;
        cliente = new mCustomer();
    }

    public DialogAddEditCustomer(Context context, mCustomer cliente) {

        this.context = context;
        this.cliente = cliente;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View v=((Activity)context).getLayoutInflater().inflate(R.layout.agregar_cliente,null);
        controladorCliente = new ControladorCliente();
        edtName=(EditText)v.findViewById(R.id.edtCustomerName);
        tilNombre = (TextInputLayout) v.findViewById(R.id.tilNombreCliente);
        tilApellidoPaterno = (TextInputLayout) v.findViewById(R.id.tilApellidoPaternoCliente);
        tilApellidoMaterno = (TextInputLayout) v.findViewById(R.id.tilApellidoMaternoCLiente);
        tilNumeroTelefono = (TextInputLayout) v.findViewById(R.id.tilNumeroTelefonoCustomer);
        tilDireccion = (TextInputLayout) v.findViewById(R.id.tilDireccionCliente);
        tilEmail = (TextInputLayout) v.findViewById(R.id.tilEmailCliente);

        edtApellidoPaterno = (EditText) v.findViewById(R.id.edtCustomerApellidoPaterno);
        edtApellidoMaterno = (EditText) v.findViewById(R.id.edtCustomerApellidoMaterno);
        edtPhone=(EditText)v.findViewById(R.id.edtCustomerPhone);
        edtEmail=(EditText)v.findViewById(R.id.edtCustomerEmail);
        edtDireccion=(EditText)v.findViewById(R.id.edtCustomerDirection);
        btnCancelar = (Button) v.findViewById(R.id.btnCancelar);
        btnGuardar = (Button) v.findViewById(R.id.btnGuardar);
        ExisteDatosCliente();
        builder.setView(v);

        btnGuardar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        dialog = builder.setTitle("Agregar Cliente").create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public void setListenerCustomer(InterfaceDataCustomerProvider interfaceDataCustomerProvider){
        this.interfaceDataCustomerProvider = interfaceDataCustomerProvider;
    }

    public void ExisteDatosCliente() {
        if (cliente.getiId() != 0) {
            edtName.setText(cliente.getcName());
            edtApellidoPaterno.setText(cliente.getcApellidoPaterno());
            edtApellidoMaterno.setText(cliente.getcApellidoMaterno());
            edtEmail.setText(cliente.getcEmail());
            edtDireccion.setText(cliente.getcDireccion());
            edtPhone.setText(cliente.getcNumberPhone());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnGuardar) {
            if (edtName.getText().toString().length() > 2) {
                new AddEditCliente().execute();
            }
            validar();
        } else if (v.getId() == R.id.btnCancelar) {
            dialog.dismiss();
        }

    }

    public void validar() {

        if (tilNombre.getEditText().getText().toString().length() < 2) {
            tilNombre.setError("El nombre debe tener mas de 2 caracteres");
        }
    }

    public void setListenerAddCustomer(ListenerAddCustomer listenerAddCustomer) {
        this.listenerAddCustomer = listenerAddCustomer;
    }

    public void setValidarExistenciaDialogAlert(ValidarExistenciaDialogAlert validarExistenciaDialogAlert) {
        this.validarExistenciaDialogAlert = validarExistenciaDialogAlert;
    }

    public void GuardarDatosCliente() {


        controladorCliente.InsertarCliente(new mCustomer(
                0, edtName.getText().toString(),
                edtApellidoPaterno.getText().toString(),
                edtApellidoMaterno.getText().toString(),
                edtPhone.getText().toString(),
                edtEmail.getText().toString(),
                edtDireccion.getText().toString()


        ));

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listenerAddCustomer.actualizar();
    }

    public interface ListenerAddCustomer {

        public void actualizar();
    }

    public class AddEditCliente extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            GuardarDatosCliente();
            validar();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }
}
