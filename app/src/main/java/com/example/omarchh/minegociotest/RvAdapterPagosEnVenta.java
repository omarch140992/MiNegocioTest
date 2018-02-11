package com.example.omarchh.minegociotest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.mPagosEnVenta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 06/12/2017.
 */

public class RvAdapterPagosEnVenta extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<mPagosEnVenta> list;
    String simboloMoneda;
    CantidadPagosEnVenta cantidadPagosEnVenta;
    int longitudRecyclerView;
    Context context;
    BigDecimal montoPagado;
    BdConnectionSql bdConnectionSql;
    int idMetodoPago;
    BigDecimal cantidadCancelada;
    String tipoPago;
    String nombreTipoPago;
    int idCabeceraPedido;
    int position;
    byte type;

    public RvAdapterPagosEnVenta(int idCabeceraPedido) {
        list = new ArrayList<>();
        longitudRecyclerView = 0;
        montoPagado = new BigDecimal(0);
        simboloMoneda = Constantes.DivisaPorDefecto.SimboloDivisa;
        bdConnectionSql = BdConnectionSql.getSinglentonInstance();
        idMetodoPago = 0;
        cantidadCancelada = new BigDecimal(0);
        tipoPago = "";
        nombreTipoPago = "";
        this.idCabeceraPedido = idCabeceraPedido;
    }

    public void setListenerCantidadPagos(CantidadPagosEnVenta cantidadPagosEnVenta) {
        this.cantidadPagosEnVenta = cantidadPagosEnVenta;
        longitudRecyclerView = 0;
        position = 0;
        type = 1;
    }

    public void setIdCabeceraPedido(int idCabeceraPedido) {
        this.idCabeceraPedido = idCabeceraPedido;
    }

    @Override
    public int getItemViewType(int position) {
        if (type == 1) {
            return 1;
        } else if (type == 2) {
            return 2;
        }
        return 0;
    }

    public void setTypeView(byte type) {
        this.type = type;
    }

    public List<mPagosEnVenta> getList() {
        return list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = null;
        if (viewType == 1) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_tipo_pago_monto, parent, false);
            context = parent.getContext();
            return new PagoEnVentaViewhHolder(v);
        } else if (viewType == 2) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pago_venta_simple, parent, false);
            return new PagosEnVentaSimple(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1:
                PagoEnVentaViewhHolder viewHolder = (PagoEnVentaViewhHolder) holder;
                viewHolder.txtTipoPago.setText(list.get(position).getTipoPago());
                viewHolder.txtCantidadPagada.setText(simboloMoneda + " " + String.format("%.2f", list.get(position).getCantidadPagada()));
                break;
            case 2:
                PagosEnVentaSimple vHolder = (PagosEnVentaSimple) holder;
                vHolder.txtNombre.setText(list.get(position).getTipoPago());
                vHolder.txtMonto.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", list.get(position).getCantidadPagada()));

                break;
        }

    }

    @Override
    public int getItemCount() {
        return longitudRecyclerView;
    }

    public void eliminarPago(int idCabeceraPedido, int position) {
        this.position = position;
        new EliminaPago().execute(idCabeceraPedido, position);

    }

    public void removeItem(int position) {
        montoPagado = montoPagado.subtract(list.get(position).getCantidadPagada());
        list.remove(position);
        notifyItemRemoved(position);
        longitudRecyclerView--;
        cantidadPagosEnVenta.numeroElementos(longitudRecyclerView, montoPagado);

    }

    public void AgregarPagosEnVenta(List<mPagosEnVenta> list) {
        this.list = list;
        longitudRecyclerView = list.size();
        notifyDataSetChanged();
    }

    public void AgregarMetodoPagoTemporal(List<mPagosEnVenta> list) {
        longitudRecyclerView = list.size();
        this.list.addAll(list);
        notifyDataSetChanged();
        for (int i = 0; i < longitudRecyclerView; i++) {
            montoPagado = montoPagado.add(list.get(i).getCantidadPagada());
        }
        cantidadPagosEnVenta.numeroElementos(longitudRecyclerView, montoPagado);

    }

    public void AddElement(int idTipoPago, String tipoPago, String nombreTipoPago, BigDecimal cantidadPagada) {

        BigDecimal mP = new BigDecimal(0);
        boolean existe = false;
        for (int i = 0; i < longitudRecyclerView; i++) {

            if (list.get(i).getIdTipoPago() == idTipoPago) {
                existe = true;
                list.get(i).setCantidadPagada(cantidadPagada);
                notifyItemChanged(i);
            }

        }
        if (existe != true) {
            list.add(new mPagosEnVenta(idTipoPago, tipoPago, nombreTipoPago, cantidadPagada));
            notifyDataSetChanged();
            longitudRecyclerView++;
        }

        for (int i = 0; i < longitudRecyclerView; i++) {
            mP = mP.add(list.get(i).getCantidadPagada());
        }
        montoPagado = mP;

        cantidadPagosEnVenta.numeroElementos(longitudRecyclerView, mP);


    }

    public interface CantidadPagosEnVenta {
        public void numeroElementos(int cantidad, BigDecimal montoPagado);
    }

    class PagoEnVentaViewhHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTipoPago;
        TextView txtCantidadPagada;
        ImageView imgBorrar;

        public PagoEnVentaViewhHolder(View itemView) {
            super(itemView);
            txtTipoPago = (TextView) itemView.findViewById(R.id.txtTipodePagoEnVenta);
            txtCantidadPagada = (TextView) itemView.findViewById(R.id.txtMontoPagado);
            imgBorrar = (ImageView) itemView.findViewById(R.id.imgDeleteTipoPago);
            imgBorrar.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.imgDeleteTipoPago) {
                ConfirmacionEliminarElemento();
            }
        }

        public void ConfirmacionEliminarElemento() {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(txtTipoPago.getText().toString() + " " + txtCantidadPagada.getText().toString());
            builder.setMessage("¿Desea realmente eliminar el pago?");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    eliminarPago(idCabeceraPedido, getAdapterPosition());
                }
            });
            builder.create().show();

        }
    }

    class PagosEnVentaSimple extends RecyclerView.ViewHolder {

        TextView txtNombre;
        TextView txtMonto;

        public PagosEnVentaSimple(View itemView) {
            super(itemView);
            txtNombre = (TextView) itemView.findViewById(R.id.txtNombreTipoPago);
            txtMonto = (TextView) itemView.findViewById(R.id.txtPrecioPagado);


        }

    }

    public class EliminaPago extends AsyncTask<Integer, Void, Byte> {
        Dialog dialogEliminar = crearDialog();

        public ProgressDialog crearDialog() {
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Eliminando pago");
            progressDialog.setCanceledOnTouchOutside(false);

            return progressDialog;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogEliminar.show();
        }

        @Override
        protected Byte doInBackground(Integer... integers) {
            return bdConnectionSql.EliminarPagoTemporal(integers[0], list.get(integers[1]).getIdTipoPago());
        }


        @Override
        protected void onPostExecute(Byte aByte) {
            super.onPostExecute(aByte);
            if (aByte == 1) {
                removeItem(position);
            } else if (aByte == 0) {
                Toast.makeText(context, "No se elimino el pago con éxito", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Verifique si conexion a internet", Toast.LENGTH_SHORT).show();
            }
            dialogEliminar.dismiss();

        }
    }
}
