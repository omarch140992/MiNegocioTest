package com.example.omarchh.minegociotest;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.omarchh.minegociotest.Model.mCategoriaProductos;

import java.util.List;

/**
 * Created by OMAR CHH on 21/01/2018.
 */

public class CategoriaAdapter extends ArrayAdapter<mCategoriaProductos> {


    Context context;
    List<mCategoriaProductos> list;

    public CategoriaAdapter(@NonNull Context context, int resource, List<mCategoriaProductos> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public mCategoriaProductos getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView label = new TextView(context);
        label.setPadding(10, 60, 10, 60);
        label.setTextColor(Color.BLACK);

        label.setText(list.get(position).getNombreProducto());

        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView label = new TextView(context);
        label.setPadding(10, 60, 10, 60);
        label.setTextColor(Color.BLACK);
        label.setText(list.get(position).getNombreProducto());

        return label;
    }
}
