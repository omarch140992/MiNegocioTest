package com.example.omarchh.minegociotest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.mDetalleVenta;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by OMAR CHH on 01/01/2018.
 */

public class RvAdapterDetalleVenta extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<mDetalleVenta> list;

    public RvAdapterDetalleVenta() {
        list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_producto_detalle_pedido, parent, false);

        return new DetalleVentaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DetalleVentaViewHolder h = (DetalleVentaViewHolder) holder;
        h.txtNombreProducto.setText(list.get(position).getProductName());
        h.txtCantidadProducto.setText("x" + String.valueOf(list.get(position).getCantidad()));
        h.txtSubtotalProducto.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", list.get(position).getPrecioSubtotal()));
    }

    public void AddElement(List<mDetalleVenta> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class DetalleVentaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreProducto;
        TextView txtCantidadProducto;
        TextView txtSubtotalProducto;
        TextView txtDetalleArticulo;

        public DetalleVentaViewHolder(View itemView) {
            super(itemView);

            txtNombreProducto = (TextView) itemView.findViewById(R.id.txtNombreArticuloDetalePedido);
            txtCantidadProducto = (TextView) itemView.findViewById(R.id.txtCantidadProductoDetalle);
            txtSubtotalProducto = (TextView) itemView.findViewById(R.id.txtSubtotalProducto);
            txtDetalleArticulo = (TextView) itemView.findViewById(R.id.txtDetalleArticulo);
        }
    }
}
