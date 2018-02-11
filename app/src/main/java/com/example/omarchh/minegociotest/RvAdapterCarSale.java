package com.example.omarchh.minegociotest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
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

import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Controlador.ControladorVentas;
import com.example.omarchh.minegociotest.Model.ProductoEnVenta;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by OMAR CHH on 24/11/2017.
 */

public class RvAdapterCarSale extends RecyclerView.Adapter<RvAdapterCarSale.ProductInCarSaleViewHolder> {


    String simboloMoneda;
    PasarCantidad ListenerCantidad;
    Context context;
    Button btnGuardarCantidadProducto,btnSalirDialog;
    TextInputLayout txtObservacion;
    EditText edtquantity;
    Dialog dialog;
    int numeroItem;
    ControladorVentas controladorVentas;
    private List<ProductoEnVenta> list;

    public RvAdapterCarSale(List<ProductoEnVenta> list) {
        this.list=list;
        simboloMoneda = Constantes.DivisaPorDefecto.SimboloDivisa;
        numeroItem = 1;
        controladorVentas = new ControladorVentas();
    }

    public void setNumeroItem(int numeroItem) {

        this.numeroItem = numeroItem;
    }

    public void setListenerCantidad(PasarCantidad ListenerCantidad) {

        this.ListenerCantidad = ListenerCantidad;

    }

    public void ModificarCantidad() {
        int position = list.size() - 1;
        notifyItemChanged(position);

    }

    @Override
    public ProductInCarSaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_product_in_sale_quotation, parent, false);
        return new ProductInCarSaleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductInCarSaleViewHolder holder, int position) {

        holder.productName.setText(list.get(position).getProductName());
        holder.subTotalPrice.setText(simboloMoneda + String.format("%.2f", list.get(position).getPrecioVentaFinal()));
        holder.quantityProduct.setText(String.valueOf(list.get(position).getCantidad()));


    }

    public void addElement(ProductoEnVenta productoEnVenta) {
        productoEnVenta.setItemNum(numeroItem);
        numeroItem++;
        list.add(productoEnVenta);

        ListenerCantidad.cantidad();
        notifyDataSetChanged();

    }

    public void RemoveElement(int position) {
        ListenerCantidad.eliminarProductoEnDetalle(list.get(position));
        list.remove(position);
        ListenerCantidad.cantidad();

        if (list.size() == 0) {
            ListenerCantidad.detalleListaVacio();
        }
        notifyItemRemoved(position);

    }

    public void RemoveAllElement() {
        list.clear();
        ListenerCantidad.cantidad();
        if (list.size() == 0) {
            ListenerCantidad.detalleListaVacio();
        }
        numeroItem = 1;
        notifyDataSetChanged();
    }

    public int CodigoUltimoProduct() {
        int id = 0;
        int posicion = 0;
        if (list.size() == 0) {
            id = 0;
        } else {
            posicion = getItemCount() - 1;
            id = list.get(posicion).getIdProducto();
        }
        return id;
    }

    public void AddElementList(List<ProductoEnVenta> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface PasarCantidad{

        public void cantidad();

        public void eliminarProductoEnDetalle(ProductoEnVenta productoEnVenta);
        public void detalleListaVacio();

        public void EditarCantidadProducto(int position);
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
                VerificarEliminacionProductoLista();
            }
            else if(v.getId()==R.id.llayoutCardViewProductInCarSale){

                modificarCantidadProducto(list.get(getAdapterPosition()).getCantidad(), list.get(getAdapterPosition()).getObservacionProducto());
            }

            else if(v.getId()==R.id.btnGuardarCantidadProducto){

                ModificarCantidaPrecioSubtotal(getAdapterPosition());
                ListenerCantidad.cantidad();
                dialog.dismiss();
                if (list.get(getAdapterPosition()).getCantidad() == 0) {
                    RemoveElement(getAdapterPosition());
                }
            }
            else if(v.getId()==R.id.btnSalirDialog){
                dialog.dismiss();
            } else if (v.getId() == R.id.btnPlus) {
                float cantidad = Float.valueOf(edtquantity.getText().toString());
                cantidad++;
                edtquantity.setText(String.valueOf(cantidad));
            } else if (v.getId() == R.id.btnMinusb) {
                float cantidad = Float.valueOf(edtquantity.getText().toString());
                if (cantidad - 1 >= 0) {
                    cantidad--;
                    edtquantity.setText(String.valueOf(cantidad));
                } else if (cantidad - 1 < 0) {

                }
            }

        }

        public void modificarCantidadProducto(float cantidad, String comentario) {

            ImageButton imgPlus,imgMinus;
            AlertDialog.Builder builder= new AlertDialog.Builder(context);
            View v=((Activity)context).getLayoutInflater().inflate(R.layout.dialog_edit_price,null);

            btnSalirDialog=(Button)v.findViewById(R.id.btnSalirDialog);
            btnGuardarCantidadProducto =(Button)v.findViewById(R.id.btnGuardarCantidadProducto);
            edtquantity=(EditText)v.findViewById(R.id.edtQuantityProduct);
            imgPlus=(ImageButton)v.findViewById(R.id.btnPlus);
            imgMinus=(ImageButton)v.findViewById(R.id.btnMinusb);
            txtObservacion = (TextInputLayout) v.findViewById(R.id.txtObservacionProducto);
            imgPlus.setOnClickListener(this);
            imgMinus.setOnClickListener(this);
            btnGuardarCantidadProducto.setOnClickListener(this);
            btnSalirDialog.setOnClickListener(this);
            edtquantity.setText(String.valueOf(Math.round(cantidad)));
            txtObservacion.getEditText().setText(comentario);
            dialog = builder.setView(v).create();
            dialog.show();
        }


        public void VerificarEliminacionProductoLista() {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Alerta").setMessage("Desea eliminar el articulo:" + productName.getText().toString());
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setPositiveButton(
                    "Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            RemoveElement(getAdapterPosition());
                        }
                    }
            );
            builder.create().show();
        }

        public void ModificarCantidaPrecioSubtotal(int position){

            list.get(position).setCantidad(Float.valueOf(edtquantity.getText().toString()));
            list.get(position).setObservacionProducto(txtObservacion.getEditText().getText().toString());
            list.get(position).setPrecioVentaFinal(list.get(position).getPrecioOriginal().multiply(BigDecimal.valueOf(list.get(position).getCantidad())));
            ListenerCantidad.EditarCantidadProducto(position);
            notifyItemChanged(position);
      }

    }


}
