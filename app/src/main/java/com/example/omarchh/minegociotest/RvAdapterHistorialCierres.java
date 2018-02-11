package com.example.omarchh.minegociotest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.omarchh.minegociotest.Model.mCierre;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 28/01/2018.
 */

public class RvAdapterHistorialCierres extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<mCierre> list;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
    ObtenerCierre obtenerCierre;


    public RvAdapterHistorialCierres() {
        list = new ArrayList<>();
    }

    public void setObtenerCierre(ObtenerCierre obtenerCierre) {

        this.obtenerCierre = obtenerCierre;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_item_hcierre, parent, false);
        return new CierresViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        CierresViewHolder h = (CierresViewHolder) holder;
        h.fechaApertura.setText(dateFormat.format(list.get(position).getFechaApertura()));

        if (list.get(position).getEstadoCierre().equals("C")) {
            if (list.get(position).getFechaCierre() != null) {
                h.fechaCierre.setText(dateFormat.format(list.get(position).getFechaCierre()));
            } else {
                h.fechaCierre.setText("-");
            }
        } else if (list.get(position).getEstadoCierre().equals("A")) {
            if (list.get(position).getFechaCierre() != null) {
                h.fechaCierre.setText(dateFormat.format(list.get(position).getFechaCierre()));
            } else {
                h.fechaCierre.setText("-");
            }
        }

    }

    public void AddElement(List<mCierre> list) {

        this.list.clear();
        this.list.addAll(list);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ObtenerCierre {

        void ObtenerIdCierre(int idCierre);
    }

    private class CierresViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView fechaApertura, fechaCierre;
        RelativeLayout rlCierre;

        public CierresViewHolder(View itemView) {
            super(itemView);
            fechaApertura = (TextView) itemView.findViewById(R.id.txtFechaApertura);
            fechaCierre = (TextView) itemView.findViewById(R.id.txtFechaCierre);
            rlCierre = (RelativeLayout) itemView.findViewById(R.id.rlCierre);

            rlCierre.setOnClickListener(this);

            fechaApertura.setText("-----");
            fechaCierre.setText("------");
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.rlCierre) {

                obtenerCierre.ObtenerIdCierre(list.get(getAdapterPosition()).getIdCierre());

            }


        }
    }
}
