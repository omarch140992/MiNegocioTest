package com.example.omarchh.minegociotest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.Activitys.DetalleVenta;
import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.mVenta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 31/12/2017.
 */

public class RvAdapterListVentas extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<mVenta> list;
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
    String formattedDate;
    BdConnectionSql bdConnectionSql = BdConnectionSql.getSinglentonInstance();
    byte ventaNormal = 2;
    byte ventaCancelada = 1;
    byte estadoVenta = 0;
    ListenerCabeceraVenta listenerCabeceraVenta;

    public RvAdapterListVentas() {
        list = new ArrayList<>();
    }

    public void setListenerCabeceraVenta(ListenerCabeceraVenta listenerCabeceraVenta) {
        this.listenerCabeceraVenta = listenerCabeceraVenta;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cabecera_venta, parent, false);
        context = parent.getContext();
        return new CabeceraVentaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CabeceraVentaViewHolder h = (CabeceraVentaViewHolder) holder;
        h.txtNombreVenta.setText("Venta " + (String.valueOf(list.get(position).getIdCabeceraVenta())));
        if (list.get(position).getIdCliente() != 0) {
            h.txtNombreCliente.setText(list.get(position).getNombreCliente());
        } else {
            h.txtNombreCliente.setText("Cliente no especificado");
        }
        if (list.get(position).getIdVendedor() != 0) {
            h.txtNombreVendedor.setText(list.get(position).getNombreVendedor());
        } else {
            h.txtNombreVendedor.setText("Vendedor no especificado");
        }


        if (list.get(position).getcEstadoVenta().equals("N")) {
            h.txtEstadoVenta.setVisibility(View.GONE);
        } else if (list.get(position).getcEstadoVenta().equals("C")) {
            h.txtEstadoVenta.setVisibility(View.VISIBLE);
        }

        h.txtValorVenta.setText(Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", list.get(position).getTotalNeto()));
        h.txtFechaVenta.setText(dateFormat.format(list.get(position).getFechaVentaRealizada()).toString());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void AddElement(List<mVenta> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    public void RemoveList() {
        list.clear();
        notifyDataSetChanged();
    }

    public void ChangeData(String Estado, int position) {

        list.get(position).setcEstadoVenta(Estado);
        notifyItemChanged(position);
    }

    public interface ListenerCabeceraVenta {

        public void EstadoVentaCancelada();

        public void CancelarVenta(int idCabeceraVenta, int position);
    }

    class CabeceraVentaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Dialog dialog;
        TextView txtNombreVenta, txtFechaVenta, txtNombreCliente, txtNombreVendedor, txtEstadoVenta, txtValorVenta;
        CardView cv;

        public CabeceraVentaViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cvCabeceraVenta);
            txtNombreVenta = (TextView) itemView.findViewById(R.id.txtTituloVenta);
            txtFechaVenta = (TextView) itemView.findViewById(R.id.txtFechaVenta);
            txtNombreCliente = (TextView) itemView.findViewById(R.id.txtDatoCliente);
            txtNombreVendedor = (TextView) itemView.findViewById(R.id.txtDatoVendedor);
            txtEstadoVenta = (TextView) itemView.findViewById(R.id.txtEstadoVenta);
            txtValorVenta = (TextView) itemView.findViewById(R.id.txtDatoValorVenta);
            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.cvCabeceraVenta:

                    new VerificarConexion().execute();

                    break;

            }
        }

        private void MostrarDialogVentaSeleccionada() {
            Dialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Venta seleccionada").setMessage(txtNombreVenta.getText().toString() + " - " + txtValorVenta.getText().toString());
            builder.setPositiveButton("Ver detalle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (bdConnectionSql.VerificarConexion()) {
                        MostrarDetalleVenta(list.get(getAdapterPosition()).getIdCabeceraVenta());
                    } else {
                        Toast.makeText(context, "Error al conectar", Toast.LENGTH_SHORT).show();
                    }
                }
            }).setNegativeButton("Cancelar venta", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    MostrarDialogConfirmacionCancelar();
                }
            });
            dialog = builder.create();
            dialog.show();

        }

        private void MostrarDialogConfirmacionCancelar() {
            Dialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Alerta").setIcon(R.drawable.alert);
            builder.setMessage("¿Desea cancelar la venta seleccionada?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    listenerCabeceraVenta.CancelarVenta(list.get(getAdapterPosition()).getIdCabeceraVenta(), getAdapterPosition());


                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        private void MostrarDetalleVenta(int idCabeceraVenta) {

            Intent intent = new Intent(context, DetalleVenta.class);
            intent.putExtra("idCabeceraVenta", idCabeceraVenta);
            context.startActivity(intent);
        }

        void MostrarProgressDialog() {

            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Verificar Conexion");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog = progressDialog;
            dialog.show();
        }

        private class VerificarConexion extends AsyncTask<Void, Void, Boolean> {
            boolean respuesta;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                MostrarProgressDialog();
                respuesta = false;
            }


            @Override
            protected Boolean doInBackground(Void... voids) {
                estadoVenta = bdConnectionSql.getEstadoVenta(list.get(getAdapterPosition()).getIdCabeceraVenta());
                if (estadoVenta != 0) {
                    respuesta = true;
                } else {
                    respuesta = false;
                }

                return respuesta;

            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                dialog.dismiss();
                if (aBoolean == true) {
                    if (estadoVenta == ventaNormal) {
                        MostrarDialogVentaSeleccionada();
                    } else if (estadoVenta == ventaCancelada) {

                        ChangeData("C", getAdapterPosition());
                        MostrarDetalleVenta(list.get(getAdapterPosition()).getIdCabeceraVenta());

                    }
                } else {
                    Toast.makeText(context, "Error en la conexion", Toast.LENGTH_LONG).show();
                }
            }


        }

    }

}
