package com.example.omarchh.minegociotest.Fragment;


import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.omarchh.minegociotest.DialogFragments.DialogAddEditCustomer;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapterCustomer;

/**
 * A simple {@link Fragment} subclass.
 */
public class clienteFragment extends Fragment implements View.OnClickListener {

    EditText  edtSearchClient;
    ImageButton btnAddContact,btnViewContact,btnDeleteText;
    RecyclerView rv;
    RvAdapterCustomer rvAdapterCustomer;
    LinearLayoutManager llm;
    DialogAddEditCustomer dialogAddEditCustomer;

    public clienteFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_cliente, container, false);
        dialogAddEditCustomer=new DialogAddEditCustomer(getContext());

        rv=(RecyclerView)rootView.findViewById(R.id.rvListClients);
        rvAdapterCustomer=new RvAdapterCustomer();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setHasFixedSize(true);
        rv.setAdapter(rvAdapterCustomer);
        edtSearchClient=(EditText)rootView.findViewById(R.id.edtSearchCliente);
        btnViewContact=(ImageButton) rootView.findViewById(R.id.btnViewContact);
        btnAddContact=(ImageButton)rootView.findViewById(R.id.btnAddContact);
        btnDeleteText=(ImageButton)rootView.findViewById(R.id.btnDeleteTextSearchCliente);

        btnDeleteText.setOnClickListener(this);
        btnViewContact.setOnClickListener(this);
        btnAddContact.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnDeleteTextSearchCliente){

        }
        else if(v.getId()==R.id.btnAddContact){

            DialogAddEditCustomer();
        }
    }

    public void borrarTexto(){
    }

    public void DialogAddEditCustomer(){


        DialogFragment dialogFragment=dialogAddEditCustomer;
        dialogFragment.show(((Activity)getContext()).getFragmentManager(),"");

    }


}
