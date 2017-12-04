package com.example.omarchh.minegociotest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.Model.ProductoEnVenta;

import java.util.List;

/**
 * Created by OMAR CHH on 24/11/2017.
 */

public class RvAdapterCarSale extends RecyclerView.Adapter<RvAdapterCarSale.ProductInCarSaleViewHolder> {




    private List<ProductoEnVenta> list;
    PasarCantidad ListenerCantidad;
    Context context;
    Button btnGuardarCantidadProducto,btnSalirDialog;
    EditText edtquantity;
    Dialog dialog;

    public RvAdapterCarSale(List<ProductoEnVenta> list) {
       this.list=list;

    }

    public interface PasarCantidad{

        public void cantidad();
        public void detalleListaVacio();
    }

    public void setListenerCantidad(PasarCantidad ListenerCantidad){

        this.ListenerCantidad=ListenerCantidad;

    }

    public class ProductInCarSaleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout linearLayout;
        CardView cv;
        TextView productName,quantityProduct,subTotalPrice;
        ImageButton imgDelete;
        float PrecioSubtotal=0;
        public ProductInCarSaleViewHolder(View itemView) {
            super(itemView);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.llayoutCardViewProductInCarSale);
            cv=(CardView)itemView.findViewById(R.id.cvProduct_in_sale_quotation);
            productName=(TextView)itemView.findViewById(R.id.productNameInSale);
            quantityProduct=(TextView)itemView.findViewById(R.id.quantityInSale);
            subTotalPrice=(TextView)itemView.findViewById(R.id.priceInSale);
            imgDelete=(ImageButton)itemView.findViewById(R.id.deleteInSale);
            linearLayout.setOnClickListener(this);
            cv.setOnClickListener(this);
            imgDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.deleteInSale){
                RemoveElement(getAdapterPosition());
      }
            else if(v.getId()==R.id.llayoutCardViewProductInCarSale){
                Toast.makeText(context,"Mensaje",Toast.LENGTH_SHORT).show();
               modificarCantidadProducto(list.get(getAdapterPosition()).getCantidad());
            }

            else if(v.getId()==R.id.btnGuardarCantidadProducto){

                ModificarCantidaPrecioSubtotal(getAdapterPosition());
                ListenerCantidad.cantidad();
                dialog.dismiss();
            }
            else if(v.getId()==R.id.btnSalirDialog){
                dialog.dismiss();
            }

        }
        public void modificarCantidadProducto(float cantidad){

            ImageButton imgPlus,imgMinus;
            AlertDialog.Builder builder= new AlertDialog.Builder(context);
            View v=((Activity)context).getLayoutInflater().inflate(R.layout.dialog_edit_price,null);

            btnSalirDialog=(Button)v.findViewById(R.id.btnSalirDialog);
            btnGuardarCantidadProducto =(Button)v.findViewById(R.id.btnGuardarCantidadProducto);
            edtquantity=(EditText)v.findViewById(R.id.edtQuantityProduct);
            imgPlus=(ImageButton)v.findViewById(R.id.btnPlus);
            imgMinus=(ImageButton)v.findViewById(R.id.btnMinusb);
            imgPlus.setOnClickListener(this);
            imgMinus.setOnClickListener(this);
            btnGuardarCantidadProducto.setOnClickListener(this);
            btnSalirDialog.setOnClickListener(this);
            edtquantity.setText(String.valueOf(Math.round(cantidad)));
             dialog=builder.setTitle("Editar Cantidad Producto").setView(v).create();
            dialog.show();
        }




        public void ModificarCantidaPrecioSubtotal(int position){

            list.get(position).setCantidad(Float.valueOf(edtquantity.getText().toString()));
            list.get(position).setPrecioVentaFinal(list.get(position).getCantidad()*list.get(position).getPrecioOriginal());
            notifyItemChanged(position);
      }

    }


    public void ModificarCantidad(){
        int position=list.size()-1;
        notifyItemChanged(position);

    }


    @Override
    public ProductInCarSaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_product_in_sale_quotation,parent,false);
        return new ProductInCarSaleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductInCarSaleViewHolder holder, int position) {

        holder.productName.setText(list.get(position).getProductName());
        holder.subTotalPrice.setText(String.valueOf(list.get(position).getPrecioVentaFinal()));
        holder.quantityProduct.setText(String.valueOf(list.get(position).getCantidad()));


    }

    public void addElement(ProductoEnVenta productoEnVenta){

        list.add(productoEnVenta);
        notifyDataSetChanged();

    }


    public void RemoveElement(int position){
        list.remove(position);
        ListenerCantidad.cantidad();
        if(list.size()==0){
            ListenerCantidad.detalleListaVacio();
        }
        notifyItemRemoved(position);

    }

    public int CodigoUltimoProduct(){
        int id=0;
        int posicion=0;
        if(list.size()==0){
            id=0;
        }
        else {
           posicion = getItemCount() - 1;
            id=list.get(posicion).getIdProducto();
        }
        return id;
    }

    public void AddElement(List<ProductoEnVenta> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


}
