package com.example.omarchh.minegociotest;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.omarchh.minegociotest.Model.mMotivo_Ingreso_Retiro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 01/02/2018.
 */

public class MotivoRetiroSpinnerAdapter extends ArrayAdapter<mMotivo_Ingreso_Retiro> {

    Context context;
    List<mMotivo_Ingreso_Retiro> list;


    public MotivoRetiroSpinnerAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public mMotivo_Ingreso_Retiro getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView label = new TextView(context);
        label.setPadding(10, 30, 10, 30);
        label.setTextColor(Color.BLACK);
        label.setText(list.get(position).getDescripcionMotivo());
        return label;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView label = new TextView(context);
        label.setPadding(10, 30, 10, 30);
        label.setTextColor(Color.BLACK);
        label.setText(list.get(position).getDescripcionMotivo());

        return label;
    }

    public void AddElement(List<mMotivo_Ingreso_Retiro> list) {
        this.list = list;
        notifyDataSetChanged();
    }



}
