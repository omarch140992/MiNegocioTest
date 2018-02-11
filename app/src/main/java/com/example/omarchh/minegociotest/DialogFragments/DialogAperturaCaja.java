package com.example.omarchh.minegociotest.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.R;

import java.math.BigDecimal;

/**
 * Created by OMAR CHH on 22/01/2018.
 */

public class DialogAperturaCaja extends DialogFragment implements TextWatcher, View.OnClickListener {

    Dialog dialog;
    String montoApertura = "";
    boolean cambio = false;
    BigDecimal bMonto;
    Button btn1, btn5, btn10, btn50;
    TextView txtMonto;
    EditText editText;
    AperturaCaja aperturaCaja;
    BdConnectionSql bdConnectionSql = BdConnectionSql.getSinglentonInstance();

    public void setAperturaCaja(AperturaCaja aperturaCaja) {

        this.aperturaCaja = aperturaCaja;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_apertura_caja, null);

        bMonto = new BigDecimal(0);
        montoApertura = Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", bMonto);

        btn1 = (Button) v.findViewById(R.id.btn1);
        btn5 = (Button) v.findViewById(R.id.btn5);
        btn10 = (Button) v.findViewById(R.id.btn10);
        btn50 = (Button) v.findViewById(R.id.btn50);
        txtMonto = (TextView) v.findViewById(R.id.txtMonto);
        editText = (EditText) v.findViewById(R.id.edtMontoApertura);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        btn1.setText("+" + Constantes.DivisaPorDefecto.SimboloDivisa + "1");
        btn5.setText("+" + Constantes.DivisaPorDefecto.SimboloDivisa + "5");
        btn10.setText("+" + Constantes.DivisaPorDefecto.SimboloDivisa + "10");
        btn50.setText("+" + Constantes.DivisaPorDefecto.SimboloDivisa + "50");

        editText.setText(montoApertura);
        editText.setSelection(montoApertura.length());
        editText.addTextChangedListener(this);
        btn1.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn50.setOnClickListener(this);


        builder.setPositiveButton("Abrir Caja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                aperturaCaja.VerificarCajaAbierta(bMonto);
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog = builder.setView(v).create();
        dialog.setCanceledOnTouchOutside(false);


        return dialog;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        int position = 0;
        montoApertura = s.toString();
        montoApertura = montoApertura.replace(Constantes.DivisaPorDefecto.SimboloDivisa, "");
        //montoApertura=s.toString().substring(lonDivisa);
        position = montoApertura.indexOf(".");
        String sub1 = "", sub2 = "";
        sub1 = montoApertura.substring(0, position);
        sub2 = montoApertura.substring(position + 1);
        montoApertura = sub1.concat(sub2);
        bMonto = new BigDecimal(montoApertura);
        bMonto = bMonto.divide(new BigDecimal(100));

        montoApertura = Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", bMonto);
    }

    @Override
    public void afterTextChanged(Editable s) {

        editText.removeTextChangedListener(this);
        editText.setText(montoApertura);
        editText.setSelection(montoApertura.length());
        editText.addTextChangedListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn1:
                modificarMonto(new BigDecimal(1));
                break;


            case R.id.btn5:
                modificarMonto(new BigDecimal(5));
                break;

            case R.id.btn10:
                modificarMonto(new BigDecimal(10));
                break;

            case R.id.btn50:
                modificarMonto(new BigDecimal(50));
                break;
        }

    }

    private void modificarMonto(BigDecimal monto) {
        editText.removeTextChangedListener(this);
        bMonto = bMonto.add(monto);
        montoApertura = Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", bMonto);
        editText.setText(montoApertura);
        editText.addTextChangedListener(this);
        editText.setSelection(montoApertura.length());

    }

    public interface AperturaCaja {

        public void VerificarCajaAbierta(BigDecimal montoApertura);
    }

}
