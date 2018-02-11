package com.example.omarchh.minegociotest.DialogFragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by OMAR CHH on 18/12/2017.
 */

public class DialogDatePickerSelect extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    interfaceFecha fechaListener;
    byte origen;
    int fechaInicio;
    int fechaFinal;
    int year, month, day;
    int diaActual, mesActual, anioActual;

    public DialogDatePickerSelect(byte origen, int year, int month, int day) {
        this.origen = origen;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void setFechaListener(interfaceFecha fechaListener) {

        this.fechaListener = fechaListener;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();


        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month - 1, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

        fechaListener.getFechaSelecionada(day, month + 1, year, origen);
    }

    public interface interfaceFecha {

        public void getFechaSelecionada(int day, int month, int year, byte origen);

    }

}