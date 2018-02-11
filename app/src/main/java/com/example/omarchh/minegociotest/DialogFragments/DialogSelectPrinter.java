package com.example.omarchh.minegociotest.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.Bluetooth.BluetoothConnection;
import com.example.omarchh.minegociotest.ConexionBd.DbHelper;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapterBluetoothDevice;

/**
 * Created by OMAR CHH on 28/12/2017.
 */

public class DialogSelectPrinter extends DialogFragment implements View.OnClickListener, RvAdapterBluetoothDevice.DeviceListener {

    Dialog dialog;
    RadioButton rbNinguno, rbBluetooth, rbPdfPrinter;
    RvAdapterBluetoothDevice rvAdapterBluetoothDevice;
    RecyclerView rv;
    TextView txt;
    BluetoothConnection btConnection;
    DbHelper dbHelper;
    String opcionImpresion;
    String comparacion;
    TextView txtSeleccionImpresora;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_seleccionar_impresora, null);
        rbBluetooth = (RadioButton) v.findViewById(R.id.rbBluetooth);
        rbNinguno = (RadioButton) v.findViewById(R.id.rbNinguno);
        rbPdfPrinter = (RadioButton) v.findViewById(R.id.rbPdfPrinter);
        rv = (RecyclerView) v.findViewById(R.id.rvDriversBT);
        txt = (TextView) v.findViewById(R.id.txtTextoInformacionBluetooth);
        txtSeleccionImpresora = (TextView) v.findViewById(R.id.txtSeleccionImpresora);
        btConnection = new BluetoothConnection(getActivity());
        dbHelper = new DbHelper(getActivity());

        rvAdapterBluetoothDevice = new RvAdapterBluetoothDevice();
        rv.setAdapter(rvAdapterBluetoothDevice);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAdapterBluetoothDevice.setDeviceListener(this);
        rv.setVisibility(View.GONE);
        txt.setVisibility(View.GONE);
        txtSeleccionImpresora.setVisibility(View.GONE);
        rbPdfPrinter.setOnClickListener(this);
        rbNinguno.setOnClickListener(this);
        rbBluetooth.setOnClickListener(this);


        comparacion = "";
        Cursor c = dbHelper.SelectOptionPrint();
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                comparacion = c.getString(0);

                if (comparacion.equals(rbBluetooth.getText().toString())) {
                    rbBluetooth.setChecked(true);
                    txtSeleccionImpresora.setVisibility(View.VISIBLE);
                    VerificarBluetooth();
                } else if (comparacion.equals(rbPdfPrinter.getText().toString())) {
                    rbPdfPrinter.setChecked(true);
                } else if (comparacion.equals(rbNinguno.getText().toString())) {
                    rbNinguno.setChecked(true);
                }

            }
        } else if (c.getCount() == 0) {
            opcionImpresion = rbNinguno.getText().toString();
            rbNinguno.setChecked(true);
        }

        builder.setView(v).setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbHelper.DeletePrint();
                if (rbNinguno.isChecked()) {
                    dbHelper.InsertOptionPrint(rbNinguno.getText().toString());
                } else if (rbPdfPrinter.isChecked()) {
                    dbHelper.InsertOptionPrint(rbPdfPrinter.getText().toString());
                } else if (rbBluetooth.isChecked()) {

                    //dbHelper.InsertOptionPrint(rbBluetooth.getText().toString());
                    Toast.makeText(getActivity(), "No selecciono una impresora", Toast.LENGTH_SHORT).show();
                }
            }
        }).setTitle("Seleccione una impresora");
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.rbBluetooth:
                VerificarBluetooth();
                txtSeleccionImpresora.setVisibility(View.VISIBLE);
                opcionImpresion = rbBluetooth.getText().toString();
                break;
            case R.id.rbNinguno:
                txt.setVisibility(View.GONE);
                rv.setVisibility(View.GONE);
                txtSeleccionImpresora.setVisibility(View.GONE);
                opcionImpresion = rbNinguno.getText().toString();
                break;
            case R.id.rbPdfPrinter:
                txt.setVisibility(View.GONE);
                rv.setVisibility(View.GONE);
                txtSeleccionImpresora.setVisibility(View.GONE);
                opcionImpresion = rbPdfPrinter.getText().toString();
                break;

        }

    }

    @Override
    public void getDeviceBluetooth(BluetoothDevice device) {
        dbHelper.DeletePrint();
        dbHelper.DeleteBluetooth();
        dbHelper.InsertDevice(device.getAddress());
        dbHelper.InsertOptionPrint(opcionImpresion);

        Toast.makeText(getActivity(), "Impresora seleccionada: " + device.getName(), Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    private void VerificarBluetooth() {
        opcionImpresion = rbBluetooth.getText().toString();
        if (btConnection.verifiedDriverSync().size() == 0 || btConnection.VerifiedBt1() == false) {
            txt.setVisibility(View.VISIBLE);

        } else if (btConnection.verifiedDriverSync().size() > 0 && btConnection.VerifiedBt1() == true) {
            txt.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            rvAdapterBluetoothDevice.Add(btConnection.verifiedDriverSync());
        }
    }
}
