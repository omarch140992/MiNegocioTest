
package com.example.omarchh.minegociotest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.mProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 25/09/2017.
 */

public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    String simboloMoneda;
    BdConnectionSql bdConnectionSql;
    int tipoLista=0;
    InterfaceDetalleCarritoVenta interfaceDetalleVenta;
    private List<mProduct> mProductList;
    private Context contexto;

    public RvAdapter(Context context,int tipoLista){
        simboloMoneda = Constantes.DivisaPorDefecto.SimboloDivisa;
        mProductList=new ArrayList<>();
        this.contexto=context;
        bdConnectionSql=new BdConnectionSql();
        this.tipoLista=tipoLista;

    }

    public void setInterfaceDetalleVenta(InterfaceDetalleCarritoVenta interfaceDetalleVenta) {
        this.interfaceDetalleVenta = interfaceDetalleVenta;
    }

    public void AddProduct(List<mProduct> list){

        mProductList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        int a=0;
        if(tipoLista==1){
            a=1;
        }
        else if(tipoLista==2){
            a=2;
        }
        return a;
    }

    public void clear(){
        mProductList.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=null;
        switch (viewType){

            case 1:
                v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_product,parent,false);
                return new ProductosViewHolder(v);


            case 2:
                v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewproductsinsale,parent,false);
               return new ProductosInSaleViewHolder(v);


        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        byte[]imgData;
         switch (holder.getItemViewType()){
            case 1:
                ProductosViewHolder productosViewHolder=(ProductosViewHolder)holder;
                productosViewHolder.tvProductName.setText(mProductList.get(position).getcProductName());
                productosViewHolder.tvPrecio.setText(simboloMoneda + String.format("%.2f", mProductList.get(position).getPrecioVenta()));
                productosViewHolder.tvCantidad.setText(String.valueOf(mProductList.get(position).getdQuantity()));

                imgData=mProductList.get(position).getbImage();
                if(imgData!=null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                    productosViewHolder.ciProductImage.setImageBitmap(bmp);
                }
                final Context context=productosViewHolder.tvCantidad.getContext();
                productosViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(context,"Click->"+mProductList.get(position).getcProductName(),Toast.LENGTH_SHORT).show();
                    }

                });

                break;

            case 2:
                ProductosInSaleViewHolder productosInSaleViewHolder=(ProductosInSaleViewHolder)holder;
                productosInSaleViewHolder.productName.setText(mProductList.get(position).getcProductName());
                productosInSaleViewHolder.productPrice.setText(simboloMoneda + String.format("%.2f", mProductList.get(position).getPrecioVenta()));

               imgData=mProductList.get(position).getbImage();
                if(imgData!=null) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                    productosInSaleViewHolder.imageViewProduct.setImageBitmap(bmp);
                }
                break;

        }

    }

    @Override
    public int getItemCount(){
        return mProductList.size();
    }

    class ProductosInSaleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        ImageView imageViewProduct;
        TextView productName;
        TextView productPrice;
        int position;

        public ProductosInSaleViewHolder(View itemView) {

            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cvProductInSale);
            imageViewProduct = (ImageView) itemView.findViewById(R.id.ImageProductPhoto);
            productName = (TextView) itemView.findViewById(R.id.txtNombreProducto);
            productPrice = (TextView) itemView.findViewById(R.id.txtPrecioProducto);
            cv.setOnClickListener(this);
            position = 0;

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cvProductInSale:
                    position = getAdapterPosition();
                    interfaceDetalleVenta.PasarInformacionProductoaDetalleVenta(mProductList.get(getAdapterPosition()).getIdProduct());

                    break;
            }
        }
    }

    class ProductosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView tvPrecio;
        TextView tvCantidad;
        TextView tvProductId;
        TextView tvProductName;
        ImageView ciProductImage;
        ItemClickListener itemClickListener;

        public ProductosViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cvProduct);
            tvPrecio = (TextView) itemView.findViewById(R.id.tvPrecio);
            tvCantidad = (TextView) itemView.findViewById(R.id.tvCantidad);
            tvProductName = (TextView) itemView.findViewById(R.id.tvproductName);
            ciProductImage = (ImageView) itemView.findViewById(R.id.ImageProductPhoto);
            cv.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;

        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.cvProduct) {
                interfaceDetalleVenta.PasarInformacionProductoaDetalleVenta(mProductList.get(getAdapterPosition()).getIdProduct());
            }
        }

    }


}
