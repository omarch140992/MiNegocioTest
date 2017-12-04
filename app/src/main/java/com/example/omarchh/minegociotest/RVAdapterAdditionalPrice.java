package com.example.omarchh.minegociotest;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.AdditionalPriceProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 01/10/2017.
 */

public class RVAdapterAdditionalPrice extends  RecyclerView.Adapter<RVAdapterAdditionalPrice.AdditionalPriceViewHolder>{

    private List<AdditionalPriceProduct> additionalPriceProductList=new ArrayList<>();


    @Override
    public AdditionalPriceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_additionalprice,parent,false);
        return new AdditionalPriceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdditionalPriceViewHolder holder,int position) {

        holder.tvPrecio.setText(Constantes.SimboloMoneda.moneda+String.valueOf(additionalPriceProductList.get(position).getAdditionalPrice()));

    }

    @Override
    public int getItemCount() {
        return additionalPriceProductList.size();
    }


    public class AdditionalPriceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView cv;
         TextView tvPrecio;
        ImageButton imgDeletePrice;
        public AdditionalPriceViewHolder(View itemView) {
            super(itemView);

            cv=(CardView)itemView.findViewById(R.id.cvAdditionalPrice);
            tvPrecio=(TextView)itemView.findViewById(R.id.txtAdditionalPrice);
            imgDeletePrice=(ImageButton)itemView.findViewById(R.id.imgbtnDeletePriceAdditional);
            imgDeletePrice.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            removeItem(getAdapterPosition());
        }
    }


    public RVAdapterAdditionalPrice(List<AdditionalPriceProduct> additionalPriceProducts){

        this.additionalPriceProductList=additionalPriceProducts;
    }

    public void add(float price){
        additionalPriceProductList.add(getItemCount(),new AdditionalPriceProduct("",price));
        notifyItemInserted(getItemCount());
    }
    public void removeItem(int position){
        additionalPriceProductList.remove(position);
        notifyItemRemoved(position);
    }
    public List<AdditionalPriceProduct> AdditionalPrice(){

        return additionalPriceProductList;
    }


}
