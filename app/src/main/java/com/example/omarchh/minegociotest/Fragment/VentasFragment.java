package com.example.omarchh.minegociotest.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.Activitys.HistorialVentas;
import com.example.omarchh.minegociotest.Activitys.PedidosEnReserva;
import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Controlador.ControladorProductos;
import com.example.omarchh.minegociotest.Controlador.ControladorVentas;
import com.example.omarchh.minegociotest.DialogFragments.DialogAperturaCaja;
import com.example.omarchh.minegociotest.DialogFragments.DialogCargaAsync;
import com.example.omarchh.minegociotest.DialogFragments.DialogDetalleCarrito;
import com.example.omarchh.minegociotest.DialogFragments.DialogEditQuantity;
import com.example.omarchh.minegociotest.DialogFragments.DialogGuardarPedido;
import com.example.omarchh.minegociotest.DialogFragments.DialogVentaResultado;
import com.example.omarchh.minegociotest.DialogFragments.dialogCalculadoraDescuento;
import com.example.omarchh.minegociotest.DialogFragments.dialogCobroVenta;
import com.example.omarchh.minegociotest.DialogFragments.dialogSelectCustomer;
import com.example.omarchh.minegociotest.DialogFragments.dialogSelectVendedor;
import com.example.omarchh.minegociotest.InterfaceDetalleCarritoVenta;
import com.example.omarchh.minegociotest.Model.DetalleVenta;
import com.example.omarchh.minegociotest.Model.ProductoEnVenta;
import com.example.omarchh.minegociotest.Model.mCabeceraPedido;
import com.example.omarchh.minegociotest.Model.mCustomer;
import com.example.omarchh.minegociotest.Model.mProduct;
import com.example.omarchh.minegociotest.Model.mVendedor;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapter;
import com.example.omarchh.minegociotest.RvAdapterCarSale;
import com.example.omarchh.minegociotest.rvAdapterGridArticulo;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;

/**
 * A simple {@link Fragment} subclass.
 */
public class VentasFragment extends Fragment implements DialogGuardarPedido.CapturaDato, dialogSelectCustomer.DatosCliente, InterfaceDetalleCarritoVenta, View.OnClickListener, TextWatcher, FloatingActionsMenu.OnFloatingActionsMenuUpdateListener, DialogDetalleCarrito.DetalleVentaInterface, RvAdapterCarSale.PasarCantidad, dialogSelectVendedor.InformacionVendedor, dialogCalculadoraDescuento.Descuento, dialogCobroVenta.ListenerVentaFinalizada, DialogVentaResultado.ListenerTerminarVenta, DialogAperturaCaja.AperturaCaja {


    static final int CODE_REQUEST_RESULT = 1;
    LinearLayout layoutContenedorProductos;
    DialogCargaAsync dialogCargaAsync;
    BdConnectionSql bdConnectionSql;
    BigDecimal cantidadADescontar;
    String simboloMoneda;
    mVendedor vendedor;
    ImageView imgCandado;
    BigDecimal CantidadCambio;
    BigDecimal CantidadCobrar;
    BigDecimal CantidadDescuento;
    BigDecimal CobrarSinDescuento;
    dialogSelectCustomer selectCustomer;
    dialogSelectVendedor selectVendedor;
    boolean EstadoBloqueoVendedor = false;
    DialogAperturaCaja dialogAperturaCaja;
    BigDecimal montoApertura;
    List<ProductoEnVenta> productoEnVentaList;
    List<ProductoEnVenta> listTemporal = new ArrayList<>();
    mProduct product;
    ControladorProductos controladorProductos;
    DetalleVenta detalleVenta;
    String textoCantidad;
    ImageView imgBtnScan;
    ImageView imgArrowDisplay;
    AutoCompleteTextView autoCompleteTextView;
    RecyclerView rv;
    List<mProduct> productList;
    RvAdapter rvAdapter;
    ControladorVentas controladorVentas;
    mProduct Product;
    FloatingActionsMenu floatingActionsMenu;
    TextView textoCarrito, txtDescuentoVenta;
    Button btnElegirCliente;
    byte TipoDescuento;
    byte tipoVistaArticulos;
    String parametroBusqueda;
    mCabeceraPedido cabeceraPedido;
    ProgressBar pb, pbImagenProducto;
    CircleImageView imagenProducto;
    View f;
    Button btnCobrar, btnElegirVendedor;
    TextView txtNombreUltimoProductoEnCarrito,txtPrecioUltimoProductoEnCarrito,txtCantidadProducto;
    ViewGroup transantioContent;
    LinearLayout linearLayoutbtnDetalle;
    SlidingUpPanelLayout slidingUpPanelLayout;
    mCustomer cliente;
    String ultimoProducto, ultimoPrecio;
    ImageButton btnOpenDialogSalvarPedido;
    //Adapder Recycler view detalle Venta
    RelativeLayout rvVentas;
    RecyclerView rvDetalleVenta;
    RvAdapterCarSale adapterDetalleVenta;
    ImageButton imgSelectViewTypeList;
    Button btnCancelarVenta;
    boolean descargaDatos;
    Dialog dialog;
    LinearLayout linearLayout;
    int idCabeceraActual = 0;
    private GridView gridview;
    //------------------------------//
    private rvAdapterGridArticulo rvAdapterGridArticulo;

   public VentasFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_ventas, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.actionHistorialPedidos:
                if (detalleVenta.getLongitud() == 0) {
                    MostrarHistorialPedidos();
                } else if (detalleVenta.getLongitud() > 0) {
                    mostrarDialogGuardarPedido();
                }
                return true;

            case R.id.actionHistorialVentas:
                new MostrarHistorialVentas().execute();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView=inflater.inflate(R.layout.fragment_ventas, container, false);
        setHasOptionsMenu(true);
        montoApertura = new BigDecimal(0);
        dialogCargaAsync = new DialogCargaAsync(getContext());
        dialogAperturaCaja = new DialogAperturaCaja();
        dialogAperturaCaja.setAperturaCaja(this);
        rvVentas = (RelativeLayout) rootView.findViewById(R.id.rVentas);
        gridview = (GridView) rootView.findViewById(R.id.gv_articulosVenta);
        controladorVentas = new ControladorVentas();
        //--Detalle Venta----//
        layoutContenedorProductos = (LinearLayout) rootView.findViewById(R.id.layoutContenedorProductos);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.layoutInfo);
        rvAdapterGridArticulo = new rvAdapterGridArticulo(getContext());
        gridview.setAdapter(rvAdapterGridArticulo);
        tipoVistaArticulos = 2; //  1 para mostrar como lista  --- 2 para mostrar como grid
        CantidadCobrar = new BigDecimal(0);
        bdConnectionSql = BdConnectionSql.getSinglentonInstance();
        imgCandado = (ImageView) rootView.findViewById(R.id.imgLock);
        imgArrowDisplay = (ImageView) rootView.findViewById(R.id.imgArrowDisplay);
        cliente = new mCustomer();
        detalleVenta=new DetalleVenta();
        descargaDatos = false;
        simboloMoneda = Constantes.DivisaPorDefecto.SimboloDivisa;
        ultimoProducto = "No se ingreso producto";
        ultimoPrecio = simboloMoneda + "0.00";
        parametroBusqueda = "";
        CantidadCobrar = new BigDecimal(0);
        CantidadDescuento = new BigDecimal(0);
        textoCantidad="0.0";
        TipoDescuento = 0;
        cantidadADescontar = new BigDecimal(0);
        CobrarSinDescuento = new BigDecimal(0);
        productList=new ArrayList<>();
        productoEnVentaList=new ArrayList<>();
        txtDescuentoVenta=(TextView)rootView.findViewById(R.id.txtDescuentoPrecioVenta);
        f=(View)rootView.findViewById(R.id.background_dimmer);
        rv=(RecyclerView)rootView.findViewById(R.id.rvProductsInSale);
        vendedor = new mVendedor();
        selectVendedor = new dialogSelectVendedor(getContext());
        selectCustomer = new dialogSelectCustomer(getContext());
        txtCantidadProducto=(TextView)rootView.findViewById(R.id.txtCantidadProducto);
        linearLayoutbtnDetalle=(LinearLayout)rootView.findViewById(R.id.linearLayoutBtnDetalleVenta);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        transantioContent=(ViewGroup)rootView.findViewById(R.id.content_Text);
        floatingActionsMenu=(FloatingActionsMenu)rootView.findViewById(R.id.floating_buttonSale);
        floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(this);
        textoCarrito=(TextView)rootView.findViewById(R.id.txtTextoTotalVenta);
        btnElegirVendedor = (Button) rootView.findViewById(R.id.btnElegirVendedor);
        btnElegirVendedor.setOnClickListener(this);
        Product=new mProduct();
        btnElegirCliente = (Button) rootView.findViewById(R.id.btnElegirCliente);
        btnCancelarVenta = (Button) rootView.findViewById(R.id.btnCancelarVenta);
        setItemClickListener();
        //--//

        selectVendedor.setListenerVendedor(this);
        selectCustomer.setListenerCliente(this);
        txtDescuentoVenta.setOnClickListener(this);

        txtNombreUltimoProductoEnCarrito=(TextView)rootView.findViewById(R.id.txtNombreUltimoProductoEnCarrito);
        txtPrecioUltimoProductoEnCarrito=(TextView)rootView.findViewById(R.id.txtPrecioUltimoProductoEnCarrito);
        autoCompleteTextView=(AutoCompleteTextView)rootView.findViewById(R.id.acTVcodeProduct);
        imgSelectViewTypeList = (ImageButton) rootView.findViewById(R.id.imgTipoVistaLista);
        autoCompleteTextView.addTextChangedListener(this);
        imgBtnScan=(ImageButton)rootView.findViewById(R.id.imgBtnScan);
        rv=(RecyclerView)rootView.findViewById(R.id.rvProductsInSale);
        btnOpenDialogSalvarPedido = (ImageButton) rootView.findViewById(R.id.btnOpenGuardarPedido);
        imgBtnScan.setOnClickListener(this);
        controladorProductos=new ControladorProductos(getContext());
        rvAdapter=new RvAdapter(getContext(),2);
        rv.setAdapter(rvAdapter);
        rvAdapter.setInterfaceDetalleVenta(this);
        btnCancelarVenta.setOnClickListener(this);
        textoCarrito=(TextView)rootView.findViewById(R.id.txtTextoCarrito);
        btnCobrar=(Button)rootView.findViewById(R.id.btnCobrar);
        btnCobrar.setOnClickListener(this);
        linearLayoutbtnDetalle.setOnClickListener(this);
        f.setOnClickListener(this);

        cabeceraPedido = new mCabeceraPedido();
        btnOpenDialogSalvarPedido.setOnClickListener(this);
        btnElegirCliente.setOnClickListener(this);
        slidingUpPanelLayout= (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setTouchEnabled(true);
        imgArrowDisplay.setOnClickListener(this);
        imgArrowDisplay.setVisibility(View.GONE);
        imgCandado.setOnClickListener(this);
        imgSelectViewTypeList.setOnClickListener(this);
        imgSelectViewTypeList.setImageDrawable(getResources().getDrawable(R.drawable.list));
        rvDetalleVenta = (RecyclerView) rootView.findViewById(R.id.rvCarritoVenta);
        adapterDetalleVenta = new RvAdapterCarSale(detalleVenta.getProductoEnVentaList());
        rvDetalleVenta.setAdapter(adapterDetalleVenta);
        rvDetalleVenta.setHasFixedSize(true);
        rvDetalleVenta.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterDetalleVenta.setListenerCantidad(this);
        pb = (ProgressBar) rootView.findViewById(R.id.pbPedido);
        VerificarPedidoEnProceso();
        // new DownloadPedido().execute();
        pb.setVisibility(View.GONE);
        rvVentas.setVisibility(View.VISIBLE);

        new DownloadList().execute(parametroBusqueda);

        return rootView;


    }

    public void VerificarPedidoEnProceso() {
        idCabeceraActual = controladorVentas.verificarExistePedido();
        if (idCabeceraActual != 0) {

            descargaDatos = true;
            cabeceraPedido = controladorVentas.getCabeceraUltimoPedido(idCabeceraActual);

            if (CantidadDescuento.compareTo(new BigDecimal(0)) > 0) {
                TipoDescuento = 3;
            }
            detalleVenta.setProductoEnVentaList(controladorVentas.getDetallePedidoId(cabeceraPedido.getIdCabecera()));
            if (detalleVenta.getLongitud() > 0) {

                adapterDetalleVenta.setNumeroItem(detalleVenta.getUltimoProductoIngresado().getItemNum() + 1);
            } else if (detalleVenta.getLongitud() == 0) {
                adapterDetalleVenta.setNumeroItem(1);
            }

            adapterDetalleVenta.AddElementList(detalleVenta.getProductoEnVentaList());
            modificarCantidadProductos();
            vendedor.setIdVendedor(cabeceraPedido.getIdVendedor());
            vendedor.setPrimerNombre(cabeceraPedido.getNombreVendedor());
            ObtenerInformacionVendedor(vendedor);
            cliente.setiId(cabeceraPedido.getIdCliente());
            cliente.setcName(cabeceraPedido.getNombreCliente());

            ObtenerInformacionCliente(cliente);
            txtNombreUltimoProductoEnCarrito.setText(detalleVenta.ObtenerNombreUltimoProducto());
            txtPrecioUltimoProductoEnCarrito.setText(simboloMoneda + String.format("%.2f", detalleVenta.ObtenerPrecioUltimoProducto()));
            txtDescuentoVenta.setText(simboloMoneda + String.format("%.2f", CantidadDescuento));
            btnCobrar.setText("Cobrar" + "\n" + simboloMoneda + String.format("%.2f", CantidadCobrar));
            descargaDatos = false;
        } else if (idCabeceraActual == 0) {
            idCabeceraActual = controladorVentas.GenerarNuevoPedido();
            cabeceraPedido.setIdCabecera(idCabeceraActual);

        }

    }
    public void onClick(View v) {
        if(v.getId()==R.id.imgBtnScan){
        } else if (v.getId() == R.id.imgTipoVistaLista) {
            CambiarTipoDeVistaLista();
        } else if (v.getId() == R.id.btnCobrar) {// Cobrar la venta en proceso

            new ConsultarCajaAbierta().execute();

        } else if (v.getId() == R.id.btnOpenGuardarPedido) {//Colocar venta en proceso como pedido en reserva

            mostrarDialogGuardarPedido();
        } else if (v.getId() == R.id.btnElegirVendedor) {//Elegir vendedor para la venta

            DialogVendedor();
        }

        else if(v.getId()==R.id.background_dimmer){

        } else if (v.getId() == R.id.linearLayoutBtnDetalleVenta || v.getId() == R.id.imgArrowDisplay) {
            if (detalleVenta.cantidadTotalProductos() > 0) {
                if(slidingUpPanelLayout.getPanelState()== SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    slidingUpPanelLayout.setTouchEnabled(false);
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    floatingActionsMenu.setVisibility(View.GONE);
                    imgArrowDisplay.setImageDrawable(getResources().getDrawable(R.drawable.arrow_hidden));

                } else if(slidingUpPanelLayout.getPanelState()== SlidingUpPanelLayout.PanelState.EXPANDED){
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    floatingActionsMenu.setVisibility(View.VISIBLE);
                    imgArrowDisplay.setImageDrawable(getResources().getDrawable(R.drawable.arrowdisplay));
                }
            }

        } else if (v.getId() == R.id.txtDescuentoPrecioVenta) {// abrir calculadora descuento
            MostrarCalculadoraDescuento();
        } else if (v.getId() == R.id.imgLock) {//Bloquear vendedor
            VerificarListenerVendedor();
        } else if (v.getId() == R.id.btnElegirCliente) {//Elegir cliente para la venta

            mostrarSeleccionCliente();
        } else if (v.getId() == R.id.btnCancelarVenta) {//cancelar Pedido y generar nuevo pedido
            MensajeConfirmacionEliminarPedido();
        }
    }

    public void MensajeConfirmacionEliminarPedido() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("¿Desea eliminar la venta actual?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new CancelarVenta().execute();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setTitle("Advertencia").setIcon(R.drawable.alert).show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

       }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        parametroBusqueda = s.toString();
        new DownloadList().execute(parametroBusqueda);
    }


    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onMenuExpanded() {
        f.setVisibility(View.VISIBLE);


    }

    public void DialogVendedor() {
        DialogFragment dialogFragment = selectVendedor;
        dialogFragment.show(((Activity) getContext()).getFragmentManager(), "Elegir Vendedor");

    }

    public void BusquedaProductoPorId(int id) {

        if (adapterDetalleVenta.CodigoUltimoProduct() == id) {
            product = controladorProductos.getProductIdSinImagen(id);
            detalleVenta.aumentarCantidadPorUnidadEnDetalleVentaPorUnidad();
            detalleVenta.ModificarSubtotalVentaPorUnidad();
            controladorVentas.GuardarProductoDetallePedido(idCabeceraActual, detalleVenta.getObtenerUltimoProducto(), 'M');
            ModificarValorDescuento();
            cambiarTextoUltimoProductoIngresado();
            CambiarCantidadProducto();
            adapterDetalleVenta.ModificarCantidad();
        } else if (adapterDetalleVenta.CodigoUltimoProduct() != id) {
            product = controladorProductos.getProductIdSinImagen(id);
            adapterDetalleVenta.addElement(new ProductoEnVenta(product.getIdProduct(), product.getcProductName(), 0, 1, product.getPrecioVenta(), product.getPrecioVenta(), ""));
            cambiarTextoUltimoProductoIngresado();
            detalleVenta.RetornarCantidadTotalProductosEnVenta();
            controladorVentas.GuardarProductoDetallePedido(idCabeceraActual, detalleVenta.getObtenerUltimoProducto(), 'N');
            ModificarValorDescuento();
            CambiarCantidadProducto();
        }

    }

    private void setItemClickListener() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusquedaProductoPorId((int) rvAdapterGridArticulo.getItemId(position));
            }
        });
    }

    @Override
    public void onMenuCollapsed() {
        f.setVisibility(View.GONE);
    }

    @Override
    public void PasarInformacionProductoaDetalleVenta(int id) {
        BusquedaProductoPorId(id);
    }

    public void CambiarCantidadProducto(){
        // txtCantidadProducto.startAnimation(AnimationUtils.loadAnimation(getContext(),android.R.anim.slide_out_right));
        txtCantidadProducto.setText("x"+String.valueOf(detalleVenta.cantidadTotalProductos()));

    }

    private void cambiarTextoUltimoProductoIngresado(){

        //txtNombreUltimoProductoEnCarrito.startAnimation(AnimationUtils.loadAnimation(getContext(),android.R.anim.slide_out_right));

        txtNombreUltimoProductoEnCarrito.setText(detalleVenta.getObtenerUltimoProducto().getProductName());
        linearLayout.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        txtPrecioUltimoProductoEnCarrito.setText(simboloMoneda + String.format("%.2f", detalleVenta.getObtenerUltimoProducto().getPrecioOriginal()));


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
        modificarCantidadProductos();
        if (detalleVenta.getLongitud() > 0) {
            ProductoEnVenta productoEnVenta = detalleVenta.getUltimoProductoIngresado();
            txtNombreUltimoProductoEnCarrito.setText(productoEnVenta.getProductName());
            txtPrecioUltimoProductoEnCarrito.setText(simboloMoneda + String.format("%.2f", productoEnVenta.getPrecioOriginal()));
        }
    }

    @Override
    public void eliminarProductoEnDetalle(ProductoEnVenta productoEnVenta) {
        controladorVentas.GuardarProductoDetallePedido(idCabeceraActual, productoEnVenta, 'E');
    }

    public void modificarCantidadProductos() {
        int i;
        i = detalleVenta.cantidadTotalProductos();
        txtCantidadProducto.setText(String.valueOf(i));


        if (i == 0 && slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            floatingActionsMenu.setVisibility(View.VISIBLE);
            imgArrowDisplay.setImageDrawable(getResources().getDrawable(R.drawable.arrowdisplay));

        } else if (i > 0) {

            if (imgArrowDisplay.getVisibility() == View.GONE) {
                imgArrowDisplay.setVisibility(View.VISIBLE);
                imgArrowDisplay.setImageDrawable(getResources().getDrawable(R.drawable.arrowdisplay));
            }
        }
        ModificarValorDescuento();
    }
    @Override
    public void detalleListaVacio() {

        txtNombreUltimoProductoEnCarrito.setText("Sin Productos");
        txtPrecioUltimoProductoEnCarrito.setText(simboloMoneda + "0.00");
        imgArrowDisplay.setVisibility(View.GONE);
        adapterDetalleVenta.setNumeroItem(1);
    }

    @Override
    public void EditarCantidadProducto(int position) {
        controladorVentas.GuardarProductoDetallePedido(idCabeceraActual, detalleVenta.getProductoEnPosicion(position), 'M');
    }

    @Override
    public void ObtenerInformacion(mVendedor vendedor) {
        ObtenerInformacionVendedor(vendedor);
    }

    @Override
    public void obtenerDato(mCustomer customer) {

        ObtenerInformacionCliente(customer);
    }

    public void ObtenerInformacionCliente(mCustomer customer) {
        cliente = customer;
        if (cliente.getiId() != 0) {
            btnElegirCliente.setText(cliente.getcName());
        }

        GuardarCabeceraVenta(idCabeceraActual, vendedor, cliente);
    }

    public void ObtenerInformacionVendedor(mVendedor vendedor) {
        this.vendedor = vendedor;
        if (vendedor.getIdVendedor() != 0) {
            btnElegirVendedor.setText(vendedor.getPrimerNombre());
        }
        GuardarCabeceraVenta(idCabeceraActual, this.vendedor, cliente);
    }

    @Override
    public void InformacionDescuento(byte tipoDescuento, BigDecimal valorDescuento) {

        TipoDescuento = tipoDescuento;
        CantidadDescuento = valorDescuento;
        ModificarValorDescuento();

    }

    @Override
    public void ObtenerDatoPedido(String identificador, String observacion) {


        controladorVentas.CambiarEstadoPedido(idCabeceraActual, identificador, observacion);
        GenerarNuevoPedido();
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void GuardarPagos(BigDecimal CantidadCambio) {

        listTemporal.addAll(detalleVenta.getProductoEnVentaList());
        new ProcesarVenta().execute(cabeceraPedido.getIdCabecera());
        this.CantidadCambio = CantidadCambio;

    }

    @Override
    public void FinalizarVenta() {
        listTemporal.clear();
    }

    @Override
    public void VerificarCajaAbierta(BigDecimal montoApertura) {

        this.montoApertura = montoApertura;
        new ConfirmarCajaAbierta().execute();
    }

    private void CambiarTipoDeVistaLista() {

        if (tipoVistaArticulos == 1) {
            tipoVistaArticulos = 2;
            imgSelectViewTypeList.setImageDrawable(getResources().getDrawable(R.drawable.list));
            rv.setVisibility(View.GONE);


            new DownloadList().execute(parametroBusqueda);
        } else if (tipoVistaArticulos == 2) {
            tipoVistaArticulos = 1;
            imgSelectViewTypeList.setImageDrawable(getResources().getDrawable(R.drawable.grid));
            gridview.setVisibility(View.GONE);

            new DownloadList().execute(parametroBusqueda);
        }
    }

    public void CargarListaEnPantallaLista(List<mProduct> list) {
        rvAdapter.AddProduct(list);
    }

    public void CargarGridEnPantallaList(List<mProduct> list) {
        rvAdapterGridArticulo.AddElement(list);
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

    private void mostrarMetodosDePago() {
        dialogCobroVenta CobroVenta = new dialogCobroVenta(cabeceraPedido.getIdCabecera(), getContext(), CantidadCobrar, cliente.getiId());
        CobroVenta.setListenerVentaFinalizada(this);
        DialogFragment dialogFragmet = CobroVenta;
        dialogFragmet.show(((Activity) getContext()).getFragmentManager(), "Metodos de Pago");

    }

    private void MostrarCalculadoraDescuento() {
        dialogCalculadoraDescuento dialogCalculadoraDescuento = new dialogCalculadoraDescuento(getContext(), CobrarSinDescuento, CantidadDescuento, TipoDescuento);
        dialogCalculadoraDescuento.setListenerDescuento(this);
        DialogFragment dialogFragment = dialogCalculadoraDescuento;
        dialogFragment.show(((Activity)getContext()).getFragmentManager(),"Calculadora Descuento");

    }

    private void VerificarListenerVendedor() {

        if (EstadoBloqueoVendedor == false) {
            if (vendedor.getIdVendedor() == 0) {
                alertaFaltaDeVendedor();
            } else if (vendedor.getIdVendedor() != 0) {
                imgCandado.setImageDrawable(getResources().getDrawable(R.drawable.lock_close));
                //btnElegirVendedor.setOnClickListener(null);

                EstadoBloqueoVendedor = true;

            }
        } else if (EstadoBloqueoVendedor == true) {
            imgCandado.setImageDrawable(getResources().getDrawable(R.drawable.lock_open));
            // btnElegirVendedor.setOnClickListener(this);
            EstadoBloqueoVendedor = false;
        }
    }

    private void mostrarSeleccionCliente() {

        DialogFragment dialogFragment = selectCustomer;
        dialogFragment.show(((Activity) getContext()).getFragmentManager(), "seleccionCliente");

    }

    private void alertaFaltaDeVendedor() {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Seleccione a un vendedor antes de guardarlo");
        builder.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setTitle("Alerta").setIcon(R.drawable.alert);
        dialog = builder.create();
        dialog.show();


    }

    public void mostrarDialogGuardarPedido() {
        DialogGuardarPedido dialogGuardarPedido = new DialogGuardarPedido(getContext());
        DialogFragment dialogFragment = dialogGuardarPedido;
        dialogFragment.show(((Activity) getContext()).getFragmentManager(), "Guardar Pedido");
        dialogGuardarPedido.setListenerCapturaDato(this);

    }

    private void ModificarValorDescuento() {

        CobrarSinDescuento = detalleVenta.TotalCobrar();
        cabeceraPedido.setTotalBruto(CobrarSinDescuento);
        if (TipoDescuento == 0) {
            CantidadDescuento = new BigDecimal(0);
            CantidadCobrar = CobrarSinDescuento;
            cabeceraPedido.setTotalNeto(CantidadCobrar);
            cabeceraPedido.setDescuentoPrecio(CantidadDescuento);
            cabeceraPedido.setTotalBruto(CobrarSinDescuento);
        } else if (TipoDescuento == 1) {
            CantidadCobrar = CobrarSinDescuento.subtract(CantidadDescuento);
            cabeceraPedido.setTotalNeto(CantidadCobrar);
            cabeceraPedido.setDescuentoPrecio(CantidadDescuento);
            cabeceraPedido.setTotalBruto(CobrarSinDescuento);
            txtDescuentoVenta.setText(
                    simboloMoneda + String.format("%.2f", CantidadDescuento)
            );
        } else if (TipoDescuento == 2) {

            cantidadADescontar = (CantidadDescuento.divide(new BigDecimal(100)).multiply(detalleVenta.TotalCobrar()));
            CantidadCobrar = CobrarSinDescuento.subtract(cantidadADescontar);
            txtDescuentoVenta.setText(
                    simboloMoneda + String.format("%.2f", cantidadADescontar)
                            + "(" +
                            String.format("%.2f", CantidadDescuento)
                            + "%)"
            );
            cabeceraPedido.setTotalNeto(CantidadCobrar);
            cabeceraPedido.setDescuentoPrecio(CantidadDescuento);
            cabeceraPedido.setTotalBruto(CobrarSinDescuento);

        }

        controladorVentas.GuardarValorVenta(idCabeceraActual, cabeceraPedido);
        //   btnCobrar.startAnimation(AnimationUtils.loadAnimation(getContext(),android.R.anim.fade_out));
        btnCobrar.setText("Cobrar" + "\n" + simboloMoneda + String.format("%.2f", CantidadCobrar));


    }

    private void GuardarCabeceraVenta(int id, mVendedor vendedor, mCustomer customer) {
        controladorVentas.GuardarCabeceraPedido(id, vendedor, customer);

    }

    private void GenerarNuevoPedido() {
        idCabeceraActual = controladorVentas.GenerarNuevoPedido();
        cabeceraPedido = new mCabeceraPedido();
        cabeceraPedido.setIdCabecera(idCabeceraActual);
        if (EstadoBloqueoVendedor != true) {
            vendedor = new mVendedor();
            btnElegirVendedor.setText("Vendedor");
        }
        cliente = new mCustomer();
        CobrarSinDescuento = new BigDecimal(0);
        CantidadCobrar = new BigDecimal(0);
        CantidadDescuento = new BigDecimal(0);
        btnElegirCliente.setText("Cliente");

        btnCobrar.setText("Cobrar" + "\n" + simboloMoneda + "0.00");
        btnCobrar.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        adapterDetalleVenta.RemoveAllElement();
        GuardarCabeceraVenta(idCabeceraActual, vendedor, cliente);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_REQUEST_RESULT) {
            if (resultCode == RESULT_CANCELED) {

            } else {
                int id = data.getIntExtra("RESULTADOID", 0);
                controladorVentas.CambiarEstadoPedidoSuspender(idCabeceraActual);
                idCabeceraActual = id;
                controladorVentas.CambiarEstadoPedidoTemporal(id);
                cabeceraPedido = controladorVentas.getCabeceraUltimoPedido(id);
                detalleVenta.setProductoEnVentaList(controladorVentas.getDetallePedidoId(id));
                if (detalleVenta.getLongitud() > 0) {
                    adapterDetalleVenta.setNumeroItem(detalleVenta.getUltimoProductoIngresado().getItemNum() + 1);
                } else if (detalleVenta.getLongitud() == 0) {
                    adapterDetalleVenta.setNumeroItem(1);
                }
                EstadoBloqueoVendedor = false;
                imgCandado.setImageDrawable(getResources().getDrawable(R.drawable.lock_open));

                adapterDetalleVenta.AddElementList(detalleVenta.getProductoEnVentaList());
                modificarCantidadProductos();
                vendedor.setIdVendedor(cabeceraPedido.getIdVendedor());
                vendedor.setPrimerNombre(cabeceraPedido.getNombreVendedor());
                cliente.setiId(cabeceraPedido.getIdCliente());
                cliente.setcName(cabeceraPedido.getNombreCliente());
                txtNombreUltimoProductoEnCarrito.setText(detalleVenta.ObtenerNombreUltimoProducto());
                txtPrecioUltimoProductoEnCarrito.setText(simboloMoneda + String.format("%.2f", detalleVenta.ObtenerPrecioUltimoProducto()));
                if (vendedor.getIdVendedor() != 0) {
                    btnElegirVendedor.setText(vendedor.getPrimerNombre());
                } else if (vendedor.getIdVendedor() == 0) {
                    btnElegirVendedor.setText("VENDEDOR");
                }
                if (cliente.getiId() != 0) {
                    btnElegirCliente.setText(cliente.getcName());
                } else if (cliente.getiId() == 0) {
                    btnElegirCliente.setText("CLIENTE");
                }

                btnElegirCliente.setText(cliente.getcName());
            }
        }
    }

    private void MostrarHistorialPedidos() {

        Intent intent = new Intent(getContext(), PedidosEnReserva.class);
        startActivityForResult(intent, CODE_REQUEST_RESULT);
    }

    private void MostrarResultadoVenta(List<ProductoEnVenta> list) {
        DialogVentaResultado ventaResultado = new DialogVentaResultado(list, CantidadCambio);
        ventaResultado.setListenerTerminarVenta(this);
        DialogFragment dialogFragment = ventaResultado;
        dialogFragment.show(getActivity().getFragmentManager(), "Venta Finalizada");
    }

    private void MostrarVentaFallida() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error").setMessage("Existe un error al procesar la venta");
        builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setIcon(R.drawable.alert);
        builder.create().show();

    }

    private void MostrarFallaConexion() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error").setMessage("No tiene conexion");
        builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setIcon(R.drawable.alert);
        builder.create().show();


    }

    private void MostrarHistorialVentas() {

        Intent intent = new Intent(getContext(), HistorialVentas.class);
        startActivity(intent);

    }

    public ProgressDialog mostrarDialog(String mensaje) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(mensaje);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;

    }

    public void AperturarCaja() {

        DialogFragment dialogFragment = dialogAperturaCaja;
        dialogFragment.show(getActivity().getFragmentManager(), "AperturaCaja");
    }

    private class DownloadList extends AsyncTask<String, Void, List<mProduct>> {

        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected List<mProduct> doInBackground(String... params) {
            List<mProduct> list = new ArrayList<>();
            if (params[0].equals("")) {
                list = controladorProductos.getListaProductos();
            } else if (!params[0].equals("") && params[0].length() > 1) {
                list = controladorProductos.getListaProductosPorParametro(params[0]);
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<mProduct> mProductList) {
            super.onPostExecute(mProductList);

            if (mProductList.size() > 0) {

                textoCarrito.setVisibility(View.GONE);
                if (tipoVistaArticulos == 1) {
                    rv.setVisibility(View.VISIBLE);
                    CargarListaEnPantallaLista(mProductList);
                } else if (tipoVistaArticulos == 2) {
                    gridview.setVisibility(View.VISIBLE);
                    CargarGridEnPantallaList(mProductList);
                }
                layoutContenedorProductos.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_top));
            } else if (mProductList.size() == 0) {
                if (rv.getVisibility() == View.VISIBLE) {
                    rv.setVisibility(View.GONE);
                } else if (gridview.getVisibility() == View.VISIBLE) {
                    gridview.setVisibility(View.GONE);
                }
                textoCarrito.setVisibility(View.VISIBLE);
                layoutContenedorProductos.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_top));
            }
        }
    }

    private class DownloadPedido extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            rvVentas.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            VerificarPedidoEnProceso();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pb.setVisibility(View.GONE);
            rvVentas.setVisibility(View.VISIBLE);
        }
    }

    private class ProcesarVenta extends AsyncTask<Integer, Void, Byte> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = dialogCargaAsync.getDialogCarga("Venta en proceso...");
            dialog.show();
        }

        @Override
        protected Byte doInBackground(Integer... integers) {
            byte resultado = 0;
            resultado = bdConnectionSql.VerificarAperturaCaja();

            if (resultado == 20) {
                resultado = bdConnectionSql.ProcesarVenta(integers[0], CantidadCambio.multiply(new BigDecimal(-1)));
            }

            return resultado;
        }

        @Override
        protected void onPostExecute(Byte aByte) {

            if (aByte == 2) {

                controladorVentas.CambiarEstadoPedidoSuspender(cabeceraPedido.getIdCabecera());
                MostrarResultadoVenta(listTemporal);
                GenerarNuevoPedido();
            } else if (aByte == 1) {

                MostrarVentaFallida();
                listTemporal.clear();

            } else if (aByte == 0) {
                MostrarFallaConexion();
                listTemporal.clear();

            } else if (aByte == 10) {
                AperturarCaja();
            }
            dialog.dismiss();

        }
    }

    private class CancelarVenta extends AsyncTask<Void, Void, Byte> {

        Dialog dialogCancelar = getProgressDialog("Cancelando venta");

        private ProgressDialog getProgressDialog(String Mensaje) {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(Mensaje);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);


            return progressDialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogCancelar.show();
        }


        @Override
        protected Byte doInBackground(Void... voids) {
            return bdConnectionSql.EliminarPagosTemporales(cabeceraPedido.getIdCabecera());
        }

        @Override
        protected void onPostExecute(Byte aByte) {
            super.onPostExecute(aByte);
            if (aByte == 1) {
                controladorVentas.CambiarEstadoPedidoSuspender(idCabeceraActual);
                dialogCancelar.dismiss();
                dialogCancelar = getProgressDialog("Generando nuevo pedido");
                GenerarNuevoPedido();
                dialogCancelar.dismiss();
                Toast.makeText(getContext(), "Se generó un nuevo pedido", Toast.LENGTH_LONG).show();
            } else if (aByte == 0) {
                Toast.makeText(getContext(), "Error al cancelar la venta.Verifique su conexión a internet", Toast.LENGTH_LONG).show();

            }

        }

    }

    public class MostrarHistorialVentas extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = mostrarDialog("Accediendo al historial...");
            dialog.show();
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            return bdConnectionSql.VerificarConexion();
        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dialog.dismiss();
            if (aBoolean == true) {
                MostrarHistorialVentas();
            } else {
                Toast.makeText(getActivity(), "No se realizo la operación", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Verifique su conexión", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class ConsultarCajaAbierta extends AsyncTask<Void, Void, Byte> {

        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = dialogCargaAsync.getDialogCarga("Verificando...");
            dialog.show();
        }

        @Override
        protected Byte doInBackground(Void... voids) {
            return bdConnectionSql.VerificarAperturaCaja();
        }

        @Override
        protected void onPostExecute(Byte aByte) {
            super.onPostExecute(aByte);
            if (aByte == 20) {

                if (CantidadCobrar.equals(new BigDecimal(0))) {//No mostrar si no tiene producto
                    mensajeAlertaNoHayProducto();
                } else if (!CantidadCobrar.equals(0)) {//Mostrar si tiene producto
                    mostrarMetodosDePago();
                }
            } else if (aByte == 10) {
                AperturarCaja();
                Toast.makeText(getActivity(), "Debe aperturar caja", Toast.LENGTH_LONG).show();

            } else if (aByte == 0) {
                Toast.makeText(getActivity(), "Verifique su conexion", Toast.LENGTH_LONG).show();

            }
            dialog.dismiss();
        }
    }

    private class ConfirmarCajaAbierta extends AsyncTask<Void, Void, Byte> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = dialogCargaAsync.getDialogCarga("Verificando datos...");
            dialog.show();
        }

        @Override
        protected Byte doInBackground(Void... voids) {
            return bdConnectionSql.aperturarCaja(montoApertura);
        }

        @Override
        protected void onPostExecute(Byte aByte) {
            super.onPostExecute(aByte);
            if (aByte == 99) {
                Toast.makeText(getContext(), "Error al abrir caja", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Verifique su conexión", Toast.LENGTH_SHORT).show();
            } else if (aByte == 100) {

                Toast.makeText(getContext(), "Ya existe una caja abierta", Toast.LENGTH_SHORT).show();
            } else if (aByte == 101) {
                Toast.makeText(getContext(), "La caja se aperturo", Toast.LENGTH_SHORT).show();
            } else if (aByte == 0) {
                Toast.makeText(getContext(), "Error al abrir caja", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Verifique su conexión", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }
    }


}
