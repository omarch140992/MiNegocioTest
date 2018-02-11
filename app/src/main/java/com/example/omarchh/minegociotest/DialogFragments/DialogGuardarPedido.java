package com.example.omarchh.minegociotest.DialogFragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ImageButton;

import com.example.omarchh.minegociotest.R;

/**
 * Created by OMAR CHH on 15/12/2017.
 */

public class DialogGuardarPedido extends DialogFragment {

    Context context;
    TextInputLayout txtIndentificador;
    TextInputLayout txtObservacion;
    ImageButton btnScan;
    CapturaDato capturaDato;
    Dialog dialog;

    public DialogGuardarPedido() {

    }

    @SuppressLint("ValidFragment")
    public DialogGuardarPedido(Context context) {
        this.context = context;
    }

    public void setListenerCapturaDato(CapturaDato capturaDato) {
        this.capturaDato = capturaDato;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View v = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_salvar_perdido, null);
        txtIndentificador = (TextInputLayout) v.findViewById(R.id.InLIndentificadorPedido);
        txtObservacion = (TextInputLayout) v.findViewById(R.id.InLObservacionPedido);
        btnScan = (ImageButton) v.findViewById(R.id.btnScanCode);

        builder.setView(v).setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                capturaDato.ObtenerDatoPedido(txtIndentificador.getEditText().getText().toString(), txtObservacion.getEditText().getText().toString());
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setTitle("Guardar Pedido");
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public interface CapturaDato {

        public void ObtenerDatoPedido(String identificador, String observacion);
    }


}
