package com.example.omarchh.minegociotest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.mProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 11/12/2017.
 */

public class rvAdapterGridArticulo extends BaseAdapter {

    List<mProduct> list;
    Context context;

    public rvAdapterGridArticulo(Context context) {
        list = new ArrayList<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public mProduct getItem(int position) {
        return list.get(position);

    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getIdProduct();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_grid_articulo, parent, false);
        }
        byte[] imgData;
        ImageView imagenArticulo = (ImageView) convertView.findViewById(R.id.imageArticuloGrid);
        TextView nombreArticulo = (TextView) convertView.findViewById(R.id.txtNombreArticuloGrid);
        imgData = list.get(position).getbImage();
        if (imgData != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
            imagenArticulo.setImageBitmap(bmp);

        } else {
            String texto = list.get(position).getcProductName().substring(0, 1).toUpperCase() + list.get(position).getcProductName().substring(1, 2);
            imagenArticulo.setImageBitmap(textAsBitmap(texto));
            imagenArticulo.setPadding(40, 40, 40, 40);
        }
        //  int i=2131165274;
        // imagenArticulo.setImageResource(i);
        nombreArticulo.setText(getItem(position).getcProductName() + "\n" + Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", getItem(position).getPrecioVenta()));


        return convertView;
    }

    public void AddElement(List<mProduct> lista) {
        list = lista;
        notifyDataSetChanged();
    }

    public Bitmap textAsBitmap(String texto) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(120f);
        paint.setColor(Color.parseColor("#949494"));
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent();
        int width = (int) (paint.measureText(texto) - 1f);
        int height = (int) (baseline + paint.descent() - 1f);

        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(texto, 0, baseline, paint);
        return image;

    }
}
