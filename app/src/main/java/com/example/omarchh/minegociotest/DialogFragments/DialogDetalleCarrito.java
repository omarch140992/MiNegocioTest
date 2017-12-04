package com.example.omarchh.minegociotest.DialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.Model.ProductoEnVenta;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapterCarSale;

import java.util.List;

/**
 * Created by OMAR CHH on 23/11/2017.
 */

public class DialogDetalleCarrito extends DialogFragment{


     List<ProductoEnVenta>list;
     Context context;
     RecyclerView rv;
     TextView txtCarrito;
     RvAdapterCarSale rvAdapterCarSale;
     DetalleVentaInterface listener;


    public interface DetalleVentaInterface
    {
        public void MensajeSalida(DialogFragment dialogFragment);
    }

    public DialogDetalleCarrito(List<ProductoEnVenta> list,Context context,DetalleVentaInterface detalleVentaInterface) {
        this.list = list;
        this.context=context;
        this.listener=detalleVentaInterface;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View v=((Activity)context).getLayoutInflater().inflate(R.layout.dialog_detalle_carrito_venta,null);
            rv=(RecyclerView)v.findViewById(R.id.rvDetalleVenta);
            txtCarrito=(TextView)v.findViewById(R.id.txtTextoCarrito);
            txtCarrito.setVisibility(View.GONE);
            rvAdapterCarSale = new RvAdapterCarSale( list);
            rv.setLayoutManager(new LinearLayoutManager(context));
            rv.setHasFixedSize(true);
            rv.setAdapter(rvAdapterCarSale);
               rv.setVisibility(View.VISIBLE);
           builder.setNegativeButton("Regresar", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                        listener.MensajeSalida(DialogDetalleCarrito.this);
                        }
           });


            Dialog dialog=builder.setTitle("Detalle de venta").setIcon(R.drawable.car_sale).setView(v).create();
             return dialog;
    }


}

