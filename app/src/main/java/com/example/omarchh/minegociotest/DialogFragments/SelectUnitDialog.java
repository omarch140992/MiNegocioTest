package com.example.omarchh.minegociotest.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.example.omarchh.minegociotest.R;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by OMAR CHH on 27/09/2017.
 */
public class SelectUnitDialog extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        final List<String> unidades =new ArrayList<>();
        unidades.add("Kg");
        unidades.add("Gramos");
        unidades.add("Pieza");
        unidades.add("Litros");
        unidades.add("Metro Cubico");
        unidades.add("Metro");
        unidades.add("Metro Cuadrado");
        CharSequence[]cs=unidades.toArray(new CharSequence[unidades.size()]);
        builder.setView(inflater.inflate(R.layout.dialog_select_unit,null));

        builder.setTitle("Seleccione una unidad");
        builder.setItems(cs, new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {


        }
        });
        return builder.create();

    }
}
