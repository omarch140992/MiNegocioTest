package com.example.omarchh.minegociotest.Activitys;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.omarchh.minegociotest.DialogFragments.DialogSelectCategoria;
import com.example.omarchh.minegociotest.DialogFragments.dialogSelectCustomer;
import com.example.omarchh.minegociotest.DialogFragments.dialogSelectVendedor;
import com.example.omarchh.minegociotest.R;

public class ActivityRegistros extends AppCompatActivity implements View.OnClickListener {

    Button btnRCliente;
    Button btnRVendedor;
    Button btnRInventario;
    Button btnRCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        btnRCliente = (Button) findViewById(R.id.btnRCliente);
        btnRVendedor = (Button) findViewById(R.id.btnRVendedor);
        btnRInventario = (Button) findViewById(R.id.btnRInventario);
        btnRCategoria = (Button) findViewById(R.id.btnCategoria);
        btnRCliente.setOnClickListener(this);
        btnRInventario.setOnClickListener(this);
        btnRVendedor.setOnClickListener(this);
        btnRCategoria.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnRCliente:

                MostrarDialogCliente();

                break;


            case R.id.btnRVendedor:
                MostrarVendedores();
                break;

            case R.id.btnRInventario:
                MostrarInventario();
                break;
            case R.id.btnCategoria:
                MostrarDialogCategorias();
                break;
        }

    }

    private void MostrarInventario() {
        Intent intent = new Intent(this, ActivityInventario.class);
        startActivity(intent);
    }

    private void MostrarVendedores() {
        dialogSelectVendedor selectVendedor = new dialogSelectVendedor(this);

        DialogFragment dialogFragment = selectVendedor;
        dialogFragment.show(getFragmentManager(), "Mostrar Vendedores Registro");


    }

    private void MostrarDialogCliente() {
        DialogFragment dialogFragment = new dialogSelectCustomer(this);
        dialogFragment.show(getFragmentManager(), "Editar Cliente");
    }

    private void MostrarDialogCategorias() {

        DialogFragment dialogFragment = new DialogSelectCategoria();
        dialogFragment.show(getFragmentManager(), "Seleccionar sCategorias");

    }
}









