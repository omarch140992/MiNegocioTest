package com.example.omarchh.minegociotest.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.omarchh.minegociotest.Controlador.ControladorProductos;
import com.example.omarchh.minegociotest.Controlador.ControladorVentas;
import com.example.omarchh.minegociotest.DialogFragments.DialogDetalleCarrito;
import com.example.omarchh.minegociotest.DialogFragments.DialogEditQuantity;
import com.example.omarchh.minegociotest.DialogFragments.dialogCalculadoraDescuento;
import com.example.omarchh.minegociotest.InterfaceDetalleCarritoVenta;
import com.example.omarchh.minegociotest.Model.DetalleVenta;
import com.example.omarchh.minegociotest.Model.ProductoEnVenta;
import com.example.omarchh.minegociotest.Model.mProduct;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapter;
import com.example.omarchh.minegociotest.RvAdapterCarSale;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VentasFragment extends Fragment implements InterfaceDetalleCarritoVenta,View.OnClickListener,TextWatcher,FloatingActionsMenu.OnFloatingActionsMenuUpdateListener,DialogDetalleCarrito.DetalleVentaInterface,RvAdapterCarSale.PasarCantidad{
    List<ProductoEnVenta> productoEnVentaList;
    mProduct product;
    ControladorProductos controladorProductos;
    DetalleVenta detalleVenta;
    String textoCantidad;
    ImageView imgBtnScan;
    AutoCompleteTextView autoCompleteTextView;
    RecyclerView rv;
    List<mProduct> productList;
    RvAdapter rvAdapter;
    ControladorVentas controladorVentas;
    mProduct Product;
    FloatingActionsMenu floatingActionsMenu;
    TextView textoCarrito,txtDescuentoVenta,txtElegirVendedor,txtElegirCliente;
    View f;
    Button btnCobrar;
    TextView txtNombreUltimoProductoEnCarrito,txtPrecioUltimoProductoEnCarrito,txtCantidadProducto;
    ViewGroup transantioContent;
    LinearLayout linearLayoutbtnDetalle;
    SlidingUpPanelLayout slidingUpPanelLayout;


    //Adapder Recycler view detalle Venta


    RecyclerView rvDetalleVenta;
    RvAdapterCarSale adapterDetalleVenta;

    //------------------------------//

   public VentasFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
              // Inflate the layout for this fragment


        View rootView=inflater.inflate(R.layout.fragment_ventas, container, false);
        detalleVenta=new DetalleVenta();
        textoCantidad="0.0";
        productList=new ArrayList<>();
        productoEnVentaList=new ArrayList<>();
        txtDescuentoVenta=(TextView)rootView.findViewById(R.id.txtDescuentoPrecioVenta);
        f=(View)rootView.findViewById(R.id.background_dimmer);
        rv=(RecyclerView)rootView.findViewById(R.id.rvProductsInSale);
        txtCantidadProducto=(TextView)rootView.findViewById(R.id.txtCantidadProducto);
        linearLayoutbtnDetalle=(LinearLayout)rootView.findViewById(R.id.linearLayoutBtnDetalleVenta);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        transantioContent=(ViewGroup)rootView.findViewById(R.id.content_Text);
        floatingActionsMenu=(FloatingActionsMenu)rootView.findViewById(R.id.floating_buttonSale);
        floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(this);
        textoCarrito=(TextView)rootView.findViewById(R.id.txtTextoTotalVenta);
        Product=new mProduct();

        //--Detalle Venta----//
        rvDetalleVenta=(RecyclerView)rootView.findViewById(R.id.rvCarritoVenta);

        adapterDetalleVenta=new RvAdapterCarSale(detalleVenta.getProductoEnVentaList());
        rvDetalleVenta.setAdapter(adapterDetalleVenta);
        rvDetalleVenta.setHasFixedSize(true);
        rvDetalleVenta.setLayoutManager(new LinearLayoutManager(getContext()));
        //--//


        controladorVentas=new ControladorVentas();
        txtDescuentoVenta.setOnClickListener(this);
        txtNombreUltimoProductoEnCarrito=(TextView)rootView.findViewById(R.id.txtNombreUltimoProductoEnCarrito);
        txtPrecioUltimoProductoEnCarrito=(TextView)rootView.findViewById(R.id.txtPrecioUltimoProductoEnCarrito);
        autoCompleteTextView=(AutoCompleteTextView)rootView.findViewById(R.id.acTVcodeProduct);
        imgBtnScan=(ImageButton)rootView.findViewById(R.id.imgBtnScan);
        rv=(RecyclerView)rootView.findViewById(R.id.rvProductsInSale);
        imgBtnScan.setOnClickListener(this);
        controladorProductos=new ControladorProductos(getContext());
        rvAdapter=new RvAdapter(getContext(),2);
        rv.setAdapter(rvAdapter);
        rvAdapter.setInterfaceDetalleVenta(this);
        textoCarrito=(TextView)rootView.findViewById(R.id.txtTextoCarrito);
        btnCobrar=(Button)rootView.findViewById(R.id.btnCobrar);
        btnCobrar.setOnClickListener(this);
        linearLayoutbtnDetalle.setOnClickListener(this);
        f.setOnClickListener(this);
        adapterDetalleVenta.setListenerCantidad(this);
        slidingUpPanelLayout= (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);

        new DownloadList().execute();
        return rootView;




    }

    public void onClick(View v) {
        if(v.getId()==R.id.imgBtnScan){
        }
        else if(v.getId()==R.id.btnCobrar){


            mensajeAlertaNoHayProducto();
        }

        else if(v.getId()==R.id.background_dimmer){

        }
        else if(v.getId()==R.id.linearLayoutBtnDetalleVenta){
            if(slidingUpPanelLayout.getPanelState()== SlidingUpPanelLayout.PanelState.COLLAPSED) {
                slidingUpPanelLayout.setTouchEnabled(false);
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                floatingActionsMenu.setVisibility(View.GONE);
            }
            else if(slidingUpPanelLayout.getPanelState()== SlidingUpPanelLayout.PanelState.EXPANDED){
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                floatingActionsMenu.setVisibility(View.VISIBLE);
            }
        }
        else if(v.getId()==R.id.txtDescuentoPrecioVenta){
            MostrarCalculadoraDescuento();
        }

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

       }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onMenuExpanded() {
        f.setVisibility(View.VISIBLE);


    }

    @Override
    public void onMenuCollapsed() {
        f.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void PasarInformacionProductoaDetalleVenta(int id) {

            if(adapterDetalleVenta.CodigoUltimoProduct()==id) {

                product= controladorVentas.GetProductBd(id);
                detalleVenta.aumentarCantidadPorUnidadEnDetalleVentaPorUnidad();
                detalleVenta.ModificarSubtotalVentaPorUnidad();
                btnCobrar.setText(detalleVenta.TotalCobrar());
                cambiarTextoUltimoProductoIngresado();
                CambiarCantidadProducto();

                adapterDetalleVenta.ModificarCantidad();
            }
            else if(adapterDetalleVenta.CodigoUltimoProduct()!=id){
                product = controladorVentas.GetProductBd(id);
                adapterDetalleVenta.addElement(new ProductoEnVenta(product.getIdProduct(), product.getcProductName(),0,1,product.getdQuantity(),product.getdSalesPrice(),product.getdSalesPrice(),0));
                cambiarTextoUltimoProductoIngresado();
                detalleVenta.RetornarCantidadTotalProductosEnVenta();
                btnCobrar.setText(detalleVenta.TotalCobrar());
                CambiarCantidadProducto();
            }

    }

    public void CambiarCantidadProducto(){

        txtCantidadProducto.setText("x"+String.valueOf(detalleVenta.cantidadTotalProductos()));
    }

    private void cambiarTextoUltimoProductoIngresado(){
        txtNombreUltimoProductoEnCarrito.setText(detalleVenta.getObtenerUltimoProducto().getProductName());
        txtPrecioUltimoProductoEnCarrito.setText("S/"+String.valueOf(detalleVenta.getObtenerUltimoProducto().getPrecioOriginal()));

    }

    @Override
    public void CantidadProductosEnCarrito(int cantidad) {

    }

    @Override
    public void InformacionUltimoProducto(String nombre, String precio) {
        txtNombreUltimoProductoEnCarrito.setText(nombre);
        txtPrecioUltimoProductoEnCarrito.setText(precio);
    }

    @Override
    public void MensajeSalida(DialogFragment dialogFragment) {
        mostrarEditarCantidadProducto();
    }

    @Override
    public void cantidad() {
         txtCantidadProducto.setText(String.valueOf(detalleVenta.cantidadTotalProductos()));
         btnCobrar.setText(detalleVenta.TotalCobrar());
    }

    @Override
    public void detalleListaVacio() {

        txtNombreUltimoProductoEnCarrito.setText("Sin Productos");
        txtPrecioUltimoProductoEnCarrito.setText("S/0.0");
    }


    private class DownloadList extends AsyncTask<Void,Void,List<mProduct>>{

        protected void onPreExecute() {

            super.onPreExecute();
        }
        @Override
        protected List<mProduct> doInBackground(Void... params) {

            return controladorProductos.mostrarListaProducto();
        }
        @Override
        protected void onPostExecute(List<mProduct> mProductList) {

            super.onPostExecute(mProductList);

            textoCarrito.setVisibility(View.GONE);

            rv.setVisibility(View.VISIBLE);

            CargarListaEnPantalla(mProductList);
        }


    }

    public void CargarListaEnPantalla(List<mProduct> list){

        rvAdapter.AddProduct(list);
    }
    public void mostrarEditarCantidadProducto(){

        DialogFragment dialogFragment=new DialogEditQuantity(getContext());
        dialogFragment.show(((Activity)getContext()).getFragmentManager(),"Detalle Venta");
    }

    public void mensajeAlertaNoHayProducto() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("No existe productos en el carrito")
                .setNegativeButton("Salir", null);
        builder.setTitle("Atencion").setIcon(R.drawable.alert);
        Dialog dialog = builder.create();
        dialog.show();


    }

    public void MostrarCalculadoraDescuento(){

        DialogFragment dialogFragment=new dialogCalculadoraDescuento(getContext());
        dialogFragment.show(((Activity)getContext()).getFragmentManager(),"Calculadora Descuento");

    }



}
