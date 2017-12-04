package com.example.omarchh.minegociotest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.omarchh.minegociotest.DialogFragments.DialogDetalleCarrito;
import com.example.omarchh.minegociotest.DialogFragments.DialogDiscountPrice;
import com.example.omarchh.minegociotest.Model.DetalleVenta;
import com.example.omarchh.minegociotest.Model.InterfaceDiscount;
import com.example.omarchh.minegociotest.Model.ListProduct;
import com.example.omarchh.minegociotest.Model.mProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 17/11/2017.
 */

public class RvAdapterSaleDemo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    DetalleVenta listDetalleVenta;
    ListProduct listProduct;
    boolean createTotalProduct;
    boolean estadoFinalCantidad;
    boolean estadoFinalPrecio;

    Context context;
    List<Float> subtotales;
    float SubTotalProducto;
    TextView txtTotalVenta=null;
    float Total;
    byte estadoCantidad;
    byte estadoPrecio;
    int longitud;
    AlertDialog.Builder builder;
    LayoutInflater inflater;
    View v;
    AlertDialog dialog;
    TextView txtCantidad,txtCantidadReserva,txtPrecioCompra,txtPrecioVenta,txtNombreProducto,txtCodigoProducto;
    TextView txtPrecioOriginal;

    InterfaceList interfaceList;
    PasarDatoTotal pasarDatoTotal;
    boolean estado;
    public RvAdapterSaleDemo(){
        estadoFinalCantidad =false;
        estadoFinalPrecio=false;
        listProduct=new ListProduct();
        createTotalProduct=true;
        subtotales=new ArrayList<>();
        SubTotalProducto =0;
        estadoCantidad=0;
        estadoPrecio=0;
        builder=null;
        estado=false;
        longitud=0;
        listDetalleVenta=new DetalleVenta();
     }

       class ViewHolderListaProductos extends RecyclerView.ViewHolder  implements View.OnClickListener,TextWatcher,InterfaceDiscount{
        float precio;
        float cantidad;
        CardView cv;
        EditText edtQuantity;
        EditText edtPriceSale;
        TextView SubTotal,txtNombreProduct;
        ImageButton btnInformation,btnDiscountinSale,btnDeleteinSale;
        DialogDiscountPrice dialogDiscountPrice;
        public ViewHolderListaProductos(View itemView) {
            super(itemView);
/*            cv=(CardView)itemView.findViewById(R.id.cvProduct_in_sale_quotation);
            edtQuantity=(EditText)itemView.findViewById(R.id.edt_QuantityInSale);
            edtPriceSale=(EditText)itemView.findViewById(R.id.edt_PriceSaleInSale);
            SubTotal=(TextView)itemView.findViewById(R.id.txtSubtotalInSale);
            btnInformation=(ImageButton)itemView.findViewById(R.id.btnInformationProductInSale);
            btnDiscountinSale=(ImageButton)itemView.findViewById(R.id.btnDiscountInSale);
            btnDeleteinSale=(ImageButton)itemView.findViewById(R.id.btnDeleteInSale);
            txtNombreProduct=(TextView)itemView.findViewById(R.id.txtNameProductInSale);
            btnDeleteinSale.setOnClickListener(this);
            btnDiscountinSale.setOnClickListener(this);
            btnInformation.setOnClickListener(this);
            edtQuantity.addTextChangedListener(this);
            edtPriceSale.addTextChangedListener(new TextWatcher()
            {   @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.toString().equals("")){
                            precio=0;
                            estadoPrecio=0;
                         }
                        else if(!s.toString().equals("")){
                            precio=Float.valueOf(s.toString());
                            estadoPrecio=1;
                        }
                        if(edtQuantity.getText().toString().equals("")){
                            estadoCantidad=0;
                            cantidad=0;
                        }
                        else if(!edtQuantity.getText().toString().equals("")){
                            estadoCantidad=1;
                            cantidad=Float.valueOf(edtQuantity.getText().toString());
                        }
                        ModificarPrecioCantidad(getAdapterPosition(),cantidad,precio);
                         PrecioVentaIngresadomayorAPrecioDeCompra(getAdapterPosition(),precio);
                         CantidadProductoIngresadoMenorAlDisponible(getAdapterPosition(),cantidad);
                        pasarDatoTotal.PasarEstado(estadoCantidad,estadoPrecio);

                        ValidarDatos();
                    pasarDatoTotal.PasarEstadoBoton(estadoFinalCantidad,estadoFinalPrecio);
                    CalcularTotal(cantidad,precio);
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            pasarDatoTotal.PasarEstadoBoton(estadoFinalCantidad,estadoFinalPrecio);
            btnInformation.setOnClickListener(this);
*/
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    estadoCantidad=0;
                    cantidad=0;
                }
                else if(!s.toString().equals("")){
                    estadoCantidad=1;
                    cantidad=Float.valueOf(s.toString());
                }
                if(edtPriceSale.getText().toString().equals("")){
                    precio=0;
                    estadoPrecio=0;
                }
                else if(!edtPriceSale.getText().toString().equals("")){
                    precio=Float.valueOf(edtPriceSale.getText().toString());
                    estadoPrecio=1;
                }
                ModificarPrecioCantidad(getAdapterPosition(),cantidad,precio);
                CantidadProductoIngresadoMenorAlDisponible(getAdapterPosition(),cantidad);
                PrecioVentaIngresadomayorAPrecioDeCompra(getAdapterPosition(),precio);
                pasarDatoTotal.PasarEstado(estadoCantidad,estadoPrecio);

                 CalcularTotal(cantidad,precio);
                 ValidarDatos();
                 pasarDatoTotal.PasarEstadoBoton(estadoFinalCantidad,estadoFinalPrecio);


        }

          @Override
          public void afterTextChanged(Editable s) {

        }

        private void CalcularTotal(float cantidad, float precio) {
            SubTotalProducto =cantidad*precio;
            SubTotal.setText(String.valueOf(SubTotalProducto));
            subtotales.set(getAdapterPosition(), SubTotalProducto);
            Total=SumarSubtotales(subtotales);
            if(txtTotalVenta!=null) {
                txtTotalVenta.setText(String.valueOf(Total));
            }
        }

        @Override
        public void onClick(View v) {

        }

        public void GenerarPantallaInformacion(){

           builder=new AlertDialog.Builder(context);
            inflater=((Activity)context).getLayoutInflater();
            v=inflater.inflate(R.layout.dialog_information_product,null);
            builder.setView(v).setTitle("Informacion del Producto").setIcon(R.drawable.information);
            txtCantidad=(TextView)v.findViewById(R.id.txtCantidadDato);
            txtCantidadReserva=(TextView)v.findViewById(R.id.txtCantidadReservaDato);
            txtPrecioCompra=(TextView)v.findViewById(R.id.txtPrecioCompraDato);
            txtPrecioVenta=(TextView)v.findViewById(R.id.txtPrecioVentaDato);
            txtNombreProducto=(TextView)v.findViewById(R.id.txtNombreInformacion);
            txtCodigoProducto=(TextView)v.findViewById(R.id.txtCodigoInformacion);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog=builder.create();
            dialog.show();
            txtNombreProducto.setText(listProduct.getProductPosition(getAdapterPosition()).getcProductName());
            txtCantidad.setText(String.valueOf(listProduct.getProductPosition(getAdapterPosition()).getdQuantity()));
            txtCantidadReserva.setText(String.valueOf(listProduct.getProductPosition(getAdapterPosition()).getdQuantityReserve()));
            txtPrecioCompra.setText(String.valueOf(listProduct.getProductPosition(getAdapterPosition()).getdPurcharsePrice()));
            txtPrecioVenta.setText(String.valueOf(listProduct.getProductPosition(getAdapterPosition()).getdSalesPrice()));
            txtCodigoProducto.setText(listProduct.getProductPosition(getAdapterPosition()).getcKey());
        }

        public void GenerarPantallaDescuento() {
            dialogDiscountPrice=new DialogDiscountPrice(context,listProduct.getmProducts().get(getAdapterPosition()).getdSalesPrice());
            dialogDiscountPrice.setListener(this);
            FragmentManager fragmentManager=((Activity)context).getFragmentManager();
            DialogFragment dialogFragment=dialogDiscountPrice;
            dialogFragment.show(fragmentManager,"");



        }

        private void CantidadProductoIngresadoMenorAlDisponible(int position,float cantidad){
                if (cantidad > listProduct.getProductPosition(position).getdQuantity()) {
                    edtQuantity.setError("La cantidad es mayor a lo disponible en el inventario");
                }
                else if(cantidad==0){
                    edtQuantity.setError("Ingrese un numero mayor a 0");
                }
                else if (cantidad>0 &&cantidad <= listProduct.getProductPosition(position).getdQuantity()) {
                    estadoCantidad=1;
                }

        }


        private void PrecioVentaIngresadomayorAPrecioDeCompra(int position,float precioVenta){
            if(precioVenta>=listProduct.getProductPosition(position).getdPurcharsePrice()) {
            }
            else if(precioVenta==0){
                edtPriceSale.setError("Ingrese un número mayor a 0");
            }
            else if( precioVenta<listProduct.getProductPosition(position).getdPurcharsePrice()){
                edtPriceSale.setError("El precio ingresado es menor al precio de Compra");
            }
        }

           @Override
           public void precioDescuentoFinal(float precio, float descuento) {
               edtPriceSale.setText(String.valueOf(precio));
               listDetalleVenta.getProductoEnVentaList().get(getAdapterPosition()).setDescuento(descuento);
           }
       }


    @Override
    public int getItemViewType(int position) {
        int a=0;
        if(listProduct.getProductPosition(position).getIdProduct()==0 )
        {
            a=1;
        }
        else if(listProduct.getProductPosition(position).getIdProduct()!=0){
            a=2;
        }
        return a;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();

        View v=null;
        switch(viewType){
            case 1:
                break;
            case 2:
                v=LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_product_in_sale_quotation,parent,false);
                return new ViewHolderListaProductos(v);
         }
         return null;

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch(holder.getItemViewType()){
            case 1:
                break;
            case 2:
                ViewHolderListaProductos viewHolderListaProductos=(ViewHolderListaProductos)holder;
                viewHolderListaProductos.txtNombreProduct.setText(listProduct.getProductPosition(position).getcProductName());
                viewHolderListaProductos.edtPriceSale.setText(String.valueOf(listProduct.getProductPosition(position).getdSalesPrice()));
              break;
        }

    }
    public void ValidarDatos(){
            int longitud=getItemCount();
            int i=0;
            do{
                if(listDetalleVenta.getProductoEnVentaList().get(i).getCantidad()<=listProduct.getmProducts().get(i).getdQuantity() &&
                        listDetalleVenta.getProductoEnVentaList().get(i).getCantidad()>0){

                    estadoFinalCantidad =true;
                }
                else if(listDetalleVenta.getProductoEnVentaList().get(i).getCantidad()>listProduct.getmProducts().get(i).getdQuantity()){
                    estadoFinalCantidad =false;
                }
                else if(listDetalleVenta.getProductoEnVentaList().get(i).getCantidad()==0){
                    estadoFinalCantidad =false;
                }

                if(listDetalleVenta.getProductoEnVentaList().get(i).getPrecioVentaFinal()>0){
                    estadoFinalPrecio=true;
                }
                else if(listDetalleVenta.getProductoEnVentaList().get(i).getPrecioVentaFinal()==0){
                    estadoFinalPrecio=false;
                }

                i++;
            }while(i<longitud && estadoFinalCantidad ==true && estadoFinalPrecio==true);

    }

    @Override
    public int getItemCount() {
        return listProduct.getLongitud();
    }

    public void addProductListSale(mProduct product){
        subtotales.add(0f);
        listProduct.addProductList(product);
       // listDetalleVenta.AgregarProducto(product.getIdProduct(),0,product.getdSalesPrice(),product.getcProductName(),0);
        interfaceList.habilitarLista(getItemCount());
        notifyDataSetChanged();


    }

    public void removeItem(int position){
        Total=SumarSubtotales(subtotales);
        listProduct.removeList(position);
        subtotales.remove(position);
        notifyItemRemoved(position);
        listDetalleVenta.EliminarProducto(position);
        interfaceList.habilitarLista(getItemCount());
    }



    public float SumarSubtotales(List<Float> f){

        int longitud=f.size();
        float suma=0;
        for(int i=0;i<longitud;i++){
            suma=suma+f.get(i);
        }
        pasarDatoTotal.PasarTotal(suma);
        return suma;
    }

    public void ModificarPrecioCantidad(int position,float cantidad,float precio){
       // listDetalleVenta.ModificarPrecioCantidad(position,precio,cantidad);
    }

}
