package com.example.omarchh.minegociotest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.Activitys.DetallePedido;
import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.mCabeceraPedido;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 17/12/2017.
 */

public class RvAdapterPedidos extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<mCabeceraPedido> list;
    Context context;
    interfaceListaPedidos listenerPedidos;
    String simboloMoneda = Constantes.DivisaPorDefecto.SimboloDivisa;
    BdConnectionSql bdConnectionSql = BdConnectionSql.getSinglentonInstance();

    public RvAdapterPedidos() {
        list = new ArrayList<>();
    }

    public void setListenerPedidos(interfaceListaPedidos listenerPedidos) {
        this.listenerPedidos = listenerPedidos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_pedido_cabecera, parent, false);
        context = parent.getContext();
        return new CabeceraPedidoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        CabeceraPedidoViewHolder h = (CabeceraPedidoViewHolder) holder;
        h.txtNombrePedido.setText(list.get(position).getIdentificadorPedido());
        if (!list.get(position).getNombreCliente().equals("")) {
            h.txtNombreCliente.setText(list.get(position).getNombreCliente());
        } else {

        }
        if (!list.get(position).getNombreVendedor().equals("")) {
            h.txtNombreVendedor.setText(list.get(position).getNombreVendedor());
        } else {

        }
        h.txtFechaPedido.setText(convertirFormatoFecha(list.get(position).getFechaCreacion()));
        h.txtValorVenta.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", list.get(position).getTotalNeto()));

    }

    public String convertirFormatoFecha(int fecha) {
        int anio = fecha / 10000;
        int mes = (fecha % 10000) / 100;
        int dia = fecha % 100;

        return String.valueOf(dia) + "/" + String.valueOf(mes) + "/" + String.valueOf(anio);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void AddElement(List<mCabeceraPedido> list) {

        this.list = list;
        notifyDataSetChanged();
    }

    public interface interfaceListaPedidos {

        public void ObtenerDatoPedidoSupender(mCabeceraPedido cabeceraPedido);

        public void ObtenerDatoPedidoRegresarVenta(mCabeceraPedido cabeceraPedido);

        public void ActualizarListaPedidos();

    }

    private class CabeceraPedidoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView txtNombrePedido, txtFechaPedido, txtNombreCliente, txtNombreVendedor, txtValorVenta;
        ImageButton btnSuspenderPedido, btnPonerPedidoEnVenta;
        CardView cv;

        public CabeceraPedidoViewHolder(View itemView) {

            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_cabecera_pedido);
            txtFechaPedido = (TextView) itemView.findViewById(R.id.txtFechaPedido);
            txtNombreVendedor = (TextView) itemView.findViewById(R.id.txtNombreVendedor);

            txtNombreCliente = (TextView) itemView.findViewById(R.id.txtNombreCliente);
            txtNombrePedido = (TextView) itemView.findViewById(R.id.txtNombrePedido);
            txtValorVenta = (TextView) itemView.findViewById(R.id.txtValorVenta);
            btnSuspenderPedido = (ImageButton) itemView.findViewById(R.id.btnSuspenderPedido);
            btnPonerPedidoEnVenta = (ImageButton) itemView.findViewById(R.id.btnPonerPedidoEnVenta);
            cv.setOnClickListener(this);
            btnSuspenderPedido.setOnClickListener(this);
            btnPonerPedidoEnVenta.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btnSuspenderPedido:
                    if (bdConnectionSql.VerificarConexion()) {
                        MostrarDialogoConfirmacionSuspender();
                    } else {
                        Toast.makeText(context, "No hay conexion", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btnPonerPedidoEnVenta:
                    listenerPedidos.ObtenerDatoPedidoRegresarVenta(list.get(getAdapterPosition()));

                    break;
                case R.id.cv_cabecera_pedido:
                    if (bdConnectionSql.ObtenerEstadoPedido(list.get(getAdapterPosition()).getIdCabecera())) {
                        MostrarDetallePedido(list.get(getAdapterPosition()).getIdCabecera());
                    } else {

                        MostrarDialogNoDisponible();
                        listenerPedidos.ActualizarListaPedidos();
                    }
                    break;

            }
        }

        private void MostrarDialogNoDisponible() {
            Dialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Alerta").setMessage("El pedido no está disponible").setIcon(R.drawable.alert).setPositiveButton("Regresar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog = builder.create();
            dialog.show();

        }

        public void MostrarDialogoConfirmacionSuspender() {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("¿Desea cancelar el pedido?");
            builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listenerPedidos.ObtenerDatoPedidoSupender(list.get(getAdapterPosition()));
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.create().show();

        }

        public void MostrarDetallePedido(int idDetallePedido) {
            Intent intent = new Intent(context, DetallePedido.class);
            intent.putExtra("idCabeceraPedido", idDetallePedido);
            context.startActivity(intent);
        }
    }

}
