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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by OMAR CHH on 30/11/2017.
 */

public class dialogCalculadoraDescuento extends DialogFragment implements View.OnClickListener{

    int posicionCaracterModificado;
    int posicionComa=0;
    int numeroCaracteresIngresados;
    String cadenaIngresada;
    String cadenaDescuento;
    Context context;
    RadioGroup radioGroup;
    RadioButton rbValue,rbPercent;
    Button btnNumber1,btnNumber2,btnNumber3,btnNumber4,btnNumber5,btnNumber6,btnNumber7,btnNumber8,btnNumber9,btnNumber0,btnCancel,btnSalir,btnGuardar;
    ImageButton btnDelete;
    float precioOriginal;
    Double valorDescuento;
    TextView txtPrecioOriginal,txtValorDescuento;
    String simboloPorcentaje;
    Dialog dialog;
    public dialogCalculadoraDescuento(Context context){
        this.context=context;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View v= ((Activity)context).getLayoutInflater().inflate(R.layout.calculador_descuento,null);
        valorDescuento =0.00d;
        cadenaIngresada="";
        simboloPorcentaje="%";
        //---------Float------//
        precioOriginal=0.00f;
        valorDescuento =0.00d;


        //----------TextView----------//

        txtPrecioOriginal=(TextView)v.findViewById(R.id.txtValorOriginal);
        txtValorDescuento=(TextView)v.findViewById(R.id.txtValorDescuento);

        //---------Declarar Botones---------//
        btnDelete=(ImageButton)v.findViewById(R.id.btnNumberDelete);
        btnCancel=(Button)v.findViewById(R.id.btnNumberCancel);
        btnSalir=(Button)v.findViewById(R.id.btnCancelar);

        btnGuardar=(Button)v.findViewById(R.id.btnGuardar);
        btnNumber0=(Button)v.findViewById(R.id.btnNumber0);
        btnNumber1=(Button)v.findViewById(R.id.btnNumber1);
        btnNumber2=(Button)v.findViewById(R.id.btnNumber2);
        btnNumber3=(Button)v.findViewById(R.id.btnNumber3);
        btnNumber4=(Button)v.findViewById(R.id.btnNumber4);
        btnNumber5=(Button)v.findViewById(R.id.btnNumber5);
        btnNumber6=(Button)v.findViewById(R.id.btnNumber6);
        btnNumber7=(Button)v.findViewById(R.id.btnNumber7);
        btnNumber8=(Button)v.findViewById(R.id.btnNumber8);
        btnNumber9=(Button)v.findViewById(R.id.btnNumber9);

        //---------Declarar Radio Button Group--------//

        radioGroup=(RadioGroup)v.findViewById(R.id.rGroupTipoDescuento);
        rbValue=(RadioButton)v.findViewById(R.id.rbValue);
        rbPercent=(RadioButton)v.findViewById(R.id.rbPercent);
        //-----String------------//

        cadenaDescuento="00.00";
       posicionCaracterModificado=cadenaDescuento.length()-1;
        posicionComa=cadenaDescuento.length()-3;
        numeroCaracteresIngresados=1;
        //----------Enviar Valores----///

        txtPrecioOriginal.setText("S/ "+String.format("%.2f",precioOriginal));
        txtValorDescuento.setText("% "+cadenaDescuento);
        rbPercent.setChecked(true);

        setListenerClick();

        //----------------------------------//
        dialog=builder.setView(v).create();


        return dialog;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.btnNumber0:
                IngresarCadenaCalculadora("0");
                break;
            case R.id.btnCancelar:
                dialog.dismiss();
                break;
            case R.id.btnGuardar:
                break;
            case R.id.btnNumber1:
                IngresarCadenaCalculadora("1");
                break;
            case R.id.btnNumber2:
                IngresarCadenaCalculadora("2");
                break;
            case R.id.btnNumber3:
                IngresarCadenaCalculadora("3");
                break;
            case R.id.btnNumber4:
                IngresarCadenaCalculadora("4");
                break;
            case R.id.btnNumber5:
                IngresarCadenaCalculadora("5");
                break;
            case R.id.btnNumber6:
                IngresarCadenaCalculadora("6");
                break;
            case R.id.btnNumber7:
                IngresarCadenaCalculadora("7");
                break;
            case R.id.btnNumber8:
                IngresarCadenaCalculadora("8");
                break;
            case R.id.btnNumber9:
                IngresarCadenaCalculadora("9");
                break;
            case R.id.btnNumberDelete:
                EliminarCifras();
                EnviarDatoTexto();
                break;
            case R.id.btnNumberCancel:
               valorDescuento=0d;
                EnviarDatoTexto();
                break;
        }

    }

    public void ValorDescuento(){
        VerificarValorDescuento();
        EnviarDatoTexto();
    }


    public void IngresarCadenaCalculadora(String caracter){


         cadenaIngresada=cadenaIngresada+caracter;

         if(cadenaIngresada.length()==2){

             cadenaIngresada="."+cadenaIngresada;
         }






    }

    public void ModificarValorDescuentoPorcentaje(double valorIngresado){

        if(valorDescuento==0.00d) {
            valorDescuento = valorIngresado / 100;
        }
        else if(valorDescuento>0.00d){
            valorDescuento=(valorDescuento*10)+(valorIngresado/100);
        }
    }

    public void EliminarCifras(){
        if(valorDescuento>0.00d) {
            valorDescuento = valorDescuento /10;
        }
        else if(valorDescuento<=0.0099d){
            valorDescuento=0.00d;

        }
        Toast.makeText(context,String.valueOf(valorDescuento),Toast.LENGTH_LONG).show();

    }
    public void VerificarValorDescuento(){

        if(valorDescuento>=99.00d){
            valorDescuento=99.00d;
        }
    }

    public void EnviarDatoTexto(){


        txtValorDescuento.setText("%"+String.format("%.2f", valorDescuento));
    }

    public void setListenerClick(){
        btnDelete.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSalir.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        btnNumber0.setOnClickListener(this);
        btnNumber1.setOnClickListener(this);
        btnNumber2.setOnClickListener(this);
        btnNumber3.setOnClickListener(this);
        btnNumber4.setOnClickListener(this);
        btnNumber5.setOnClickListener(this);
        btnNumber6.setOnClickListener(this);
        btnNumber7.setOnClickListener(this);
        btnNumber8.setOnClickListener(this);
        btnNumber9.setOnClickListener(this);

   }


}
