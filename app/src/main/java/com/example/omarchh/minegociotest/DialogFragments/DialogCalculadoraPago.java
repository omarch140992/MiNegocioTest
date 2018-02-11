package com.example.omarchh.minegociotest.DialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.R;

import java.math.BigDecimal;

/**
 * Created by OMAR CHH on 09/12/2017.
 */

public class DialogCalculadoraPago extends DialogFragment implements View.OnClickListener {


    boolean empezoEscribir;
    String tipoPago;
    String simboloMoneda;
    BigDecimal cantidadTotalAPagar1;
    BigDecimal cantidadCancelada;
    int idTipoPago;
    Context context;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnCancelar, btnGuardar, btnBorrar, btnBorrarTodo;
    ImageButton btnDeleteCifra;
    TextView txtCantidadPago, TxtCantidadCancelada, txtTipoDePago;
    Dialog dialog;
    CantidadPago cantidadPago;
    String codigoTipoPago;
    String nombreTipoPago;
    int idMetodoPago;

    public DialogCalculadoraPago(int idMetodoPago, Context context, BigDecimal cantidadTotalPagar, String codigoTipoPago, String nombreTipoPago) {
        this.idMetodoPago = idMetodoPago;
        this.context = context;
        this.cantidadTotalAPagar1 = cantidadTotalPagar;
        this.codigoTipoPago = codigoTipoPago;
        this.nombreTipoPago = nombreTipoPago;

        simboloMoneda = Constantes.DivisaPorDefecto.SimboloDivisa;

    }

    public void setListenerCantidadPago(CantidadPago cantidadPago) {

        this.cantidadPago = cantidadPago;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View v = ((Activity) context).getLayoutInflater().inflate(R.layout.calculadora_pago, null, false);
        declararBotones(v);
        declararOnClick();
        builder.setView(v);
        empezoEscribir = true;
        cantidadCancelada = cantidadTotalAPagar1;
        txtCantidadPago.setText(simboloMoneda + String.format("%.2f", cantidadTotalAPagar1));
        TxtCantidadCancelada.setText(simboloMoneda + String.format("%.2f", cantidadCancelada));
        txtTipoDePago.setText(nombreTipoPago);
        dialog = builder.create();

        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public void declararBotones(View v) {
        btn1 = (Button) v.findViewById(R.id.btnNumber1);
        btn2 = (Button) v.findViewById(R.id.btnNumber2);
        btn3 = (Button) v.findViewById(R.id.btnNumber3);
        btn4 = (Button) v.findViewById(R.id.btnNumber4);
        btn5 = (Button) v.findViewById(R.id.btnNumber5);
        btn6 = (Button) v.findViewById(R.id.btnNumber6);
        btn7 = (Button) v.findViewById(R.id.btnNumber7);
        btn8 = (Button) v.findViewById(R.id.btnNumber8);
        btn9 = (Button) v.findViewById(R.id.btnNumber9);
        btnDeleteCifra = (ImageButton) v.findViewById(R.id.btnNumberDelete);
        btnBorrarTodo = (Button) v.findViewById(R.id.btnNumberCancel);
        btn0 = (Button) v.findViewById(R.id.btnNumber0);
        btnCancelar = (Button) v.findViewById(R.id.btnCancelar);
        btnGuardar = (Button) v.findViewById(R.id.btnGuardar);
        txtCantidadPago = (TextView) v.findViewById(R.id.txtValorOriginal);
        TxtCantidadCancelada = (TextView) v.findViewById(R.id.txtCantidadPagado);
        txtTipoDePago = (TextView) v.findViewById(R.id.txtTipodePago);
        txtTipoDePago.setText(tipoPago);
    }

    public void declararOnClick() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        btnDeleteCifra.setOnClickListener(this);
        btnBorrarTodo.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnNumber0:
                ModificarValorPago(new BigDecimal(0));
                break;
            case R.id.btnNumber1:
                ModificarValorPago(new BigDecimal(1));
                break;
            case R.id.btnNumber2:
                ModificarValorPago(new BigDecimal(2));

                break;
            case R.id.btnNumber3:
                ModificarValorPago(new BigDecimal(3));

                break;
            case R.id.btnNumber4:
                ModificarValorPago(new BigDecimal(4));

                break;
            case R.id.btnNumber5:
                ModificarValorPago(new BigDecimal(5));

                break;
            case R.id.btnNumber6:
                ModificarValorPago(new BigDecimal(6));

                break;
            case R.id.btnNumber7:
                ModificarValorPago(new BigDecimal(7));

                break;
            case R.id.btnNumber8:
                ModificarValorPago(new BigDecimal(8));
                break;
            case R.id.btnNumber9:
                ModificarValorPago(new BigDecimal(9));
                break;
            case R.id.btnNumberCancel:
                VaciarCantidadPago();
                break;
            case R.id.btnCancelar:
                dialog.dismiss();
                break;
            case R.id.btnGuardar:
                GuardarMetodoPagoConCantidad();
                dialog.dismiss();
                break;
            case R.id.btnNumberDelete:
                EliminarCifras();
                EnviarDatoTexto();
                break;

        }
    }

    public void ModificarValorPago(BigDecimal valorIngresado) {
        if (empezoEscribir == true) {
            cantidadCancelada = new BigDecimal(0.00);
            EnviarDatoTexto();
            empezoEscribir = false;
        }
        cantidadCancelada = (cantidadCancelada.multiply(BigDecimal.valueOf(10))).add(valorIngresado.divide(new BigDecimal(100)));
        EnviarDatoTexto();


    }

    public void GuardarMetodoPagoConCantidad() {
        cantidadPago.PasarCantidadCancelada(idMetodoPago, cantidadCancelada, codigoTipoPago, nombreTipoPago);

    }

    public void EliminarCifras() {

        String nuevaCadena = "";
        nuevaCadena = TxtCantidadCancelada.getText().toString();
        nuevaCadena = nuevaCadena.substring(simboloMoneda.length(), nuevaCadena.length() - 1);
        cantidadCancelada = new BigDecimal(nuevaCadena).divide(new BigDecimal(10));
        EnviarDatoTexto();

    }

    public void VaciarCantidadPago() {
        cantidadCancelada = new BigDecimal(0);
        TxtCantidadCancelada.setText(simboloMoneda + String.format("%.2f", cantidadCancelada));

    }

    public void EnviarDatoTexto() {

        TxtCantidadCancelada.setText(simboloMoneda + String.format("%.2f", cantidadCancelada));
    }

    @Override
    public void onDetach() {
        empezoEscribir = true;
        super.onDetach();
    }

    public interface CantidadPago {

        public void PasarCantidadCancelada(int idMetodoPago, BigDecimal cantidadCancelada, String codigoTipoPago, String nombreTipoPago);
    }
}
