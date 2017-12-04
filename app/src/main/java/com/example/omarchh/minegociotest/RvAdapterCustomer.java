package com.example.omarchh.minegociotest;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.omarchh.minegociotest.Model.mCustomer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 25/10/2017.
 */

public class RvAdapterCustomer extends RecyclerView.Adapter<RvAdapterCustomer.CustomerViewHolder>{
    Context context;
    private List<mCustomer> customerList=new ArrayList<>();
    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_customer,parent,false);
        context=parent.getContext();
        return new CustomerViewHolder(v) ;

    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {

        holder.tvName.setText(customerList.get(position).getcName());
        holder.tvAlias.setText(customerList.get(position).getcAlias());
    }


    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView cv;
        private TextView tvName;
        private TextView tvAlias;
        private ItemClickListener itemClickListener;


        public CustomerViewHolder(View itemView) {

            super(itemView);
            cv=(CardView)itemView.findViewById(R.id.cvCustomer);
            tvName=(TextView) itemView.findViewById(R.id.txtcvNameCustomer);
            tvAlias=(TextView)itemView.findViewById(R.id.txtcvAliasCustomer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ShowDialog(getAdapterPosition());

        }
    }

    public void Add(String name, String alias,String direccion,String Phone){
        customerList.add(new mCustomer(0,name,alias,Phone,direccion));
        notifyDataSetChanged();
    }

    public void ShowDialog(int position){

        mCustomer customer=new mCustomer();

        TextView tvNombre,tvAlias,tvDireccion;
        Button btnNumber,btnEmail,btnCancel,btnEdit;
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v= inflater.inflate(R.layout.dialog_detail_cliente,null);
        builder.setView(v);
        builder.setTitle("Informacion Cliente");

        tvNombre=(TextView)v.findViewById(R.id.txtDetailClientNombre);
        tvAlias=(TextView)v.findViewById(R.id.txtDetailClientAlias);
        tvDireccion=(TextView)v.findViewById(R.id.txtDetailDireccion);
        btnNumber=(Button)v.findViewById(R.id.btnCallFirstNumber);
        btnEmail=(Button)v.findViewById(R.id.btnSendEmail);
        btnCancel=(Button)v.findViewById(R.id.btnCloseDialogDetailCliente);
        btnEdit=(Button)v.findViewById(R.id.btnEditCliente);

        tvNombre.setText(customerList.get(position).getcName());
        tvAlias.setText(customerList.get(position).getcAlias());
        tvDireccion.setText(customerList.get(position).getcDireccion());
        if(customer.getcNumberPhone().equals("")){
            btnNumber.setVisibility(View.GONE);
        }
        else{

            btnNumber.setVisibility(View.VISIBLE);
        }
        builder.create();
        builder.show();

    }
}
