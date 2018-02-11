package com.example.omarchh.minegociotest.DialogFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.mMedioPago;
import com.example.omarchh.minegociotest.Model.mPagosEnVenta;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RvAdapterGridMetodoPago;
import com.example.omarchh.minegociotest.RvAdapterPagosEnVenta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 09/12/2017.
 */

public class dialogCobroVenta extends DialogFragment implements View.OnClickListener, DialogCalculadoraPago.CantidadPago, RvAdapterPagosEnVenta.CantidadPagosEnVenta {
    BdConnectionSql bdConnectionSql;
    Dialog dialog;
    String simboloMoneda;
    int idCabeceraPedido;
    String TextoTituloPago;
    ImageButton imgArrowBack;
    RelativeLayout rvContenedorPago;
    TextView txtSinMetodoDePago, txtTituloPrecio;
    Button btnFinalizarVenta;
    RecyclerView rvMetodosDePago;
    RvAdapterPagosEnVenta rvAdapterPagosEnVenta;
    RvAdapterGridMetodoPago rvGridMPagos;
    Context context;
    GridView gridView;
    BigDecimal cantidadTotalPago;
    BigDecimal cantidadACuenta;
    ListenerVentaFinalizada listenerVentaFinalizada;
    byte MetodoRealizar = 1; //1 Guardar pagos //  2 GuardarPagos y realizar venta
    List<mPagosEnVenta> pagosEnVentaList;
    mPagosEnVenta pagosEnVenta;
    int idCliente = 0;

    public dialogCobroVenta() {

    }

    public dialogCobroVenta(int idCabeceraPedido, Context context, BigDecimal CantidadCobrar, int idCliente) {
        this.idCabeceraPedido = idCabeceraPedido;
        this.context = context;
        this.cantidadTotalPago = CantidadCobrar;
        this.idCliente = idCliente;


    }

    public void setListenerVentaFinalizada(ListenerVentaFinalizada listenerVentaFinalizada) {

        this.listenerVentaFinalizada = listenerVentaFinalizada;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        bdConnectionSql = BdConnectionSql.getSinglentonInstance();
        pagosEnVentaList = new ArrayList<>();
        View v = ((Activity) context).getLayoutInflater().inflate(R.layout.elegir_metodo_pago, null);
        declararVariables(v);
        setOnclick();
        dialog = builder.setView(v).create();

        setItemClickListener();

        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public void declararVariables(View v) {


        cantidadACuenta = cantidadTotalPago;
        rvContenedorPago = (RelativeLayout) v.findViewById(R.id.contenedorPago);
        imgArrowBack = (ImageButton) v.findViewById(R.id.imgArrowBack);
        txtTituloPrecio = (TextView) v.findViewById(R.id.txtPrecioTotal);
        txtSinMetodoDePago = (TextView) v.findViewById(R.id.txtSinMetodoDePago);
        rvMetodosDePago = (RecyclerView) v.findViewById(R.id.rvPagos);
        btnFinalizarVenta = (Button) v.findViewById(R.id.btnFinalizarVenta);
        gridView = (GridView) v.findViewById(R.id.gv_metodosPago);
        rvAdapterPagosEnVenta = new RvAdapterPagosEnVenta(idCabeceraPedido);
        rvMetodosDePago.setLayoutManager(new LinearLayoutManager(context));
        rvMetodosDePago.setAdapter(rvAdapterPagosEnVenta);
        rvAdapterPagosEnVenta.setListenerCantidadPagos(this);
        rvGridMPagos = new RvAdapterGridMetodoPago();
        gridView.setAdapter(rvGridMPagos);
        cantidadACuenta = cantidadTotalPago;
        simboloMoneda = Constantes.DivisaPorDefecto.SimboloDivisa;
        TextoTituloPago = "Total " + simboloMoneda + String.format("%.2f", cantidadACuenta);
        txtTituloPrecio.setText(TextoTituloPago);

        btnFinalizarVenta.setOnClickListener(this);

        new DownloadList().execute();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imgArrowBack:

                dialog.dismiss();
                break;
            case R.id.btnFinalizarVenta:


                listenerVentaFinalizada.GuardarPagos(cantidadACuenta);

                dialog.dismiss();
                break;
        }

    }

    public void setOnclick() {
        imgArrowBack.setOnClickListener(this);

    }

    private void setItemClickListener() {

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarCalculadoraPago((int) rvGridMPagos.getItemId(position), rvGridMPagos.getItem(position).isPorCobrar(), rvGridMPagos.getItem(position).getcCodigoMedioPago(), rvGridMPagos.getItem(position).getcDescripcionMedioPago(), cantidadACuenta);
            }
        });

    }

    public void mostrarCalculadoraPago(int idMetodoPago, boolean esPorCobrar, String codigoMetodoPago, String nombreTipoPago, BigDecimal CantidadTotalPago) {

        byte permitir = 1;

        if (esPorCobrar == true && idCliente != 0) {
            permitir = 1;
        } else if (esPorCobrar == true && idCliente == 0) {
            permitir = 0;
            Toast.makeText(getActivity(), "Debe seleccionar un cliente", Toast.LENGTH_LONG).show();
        }

        if (permitir == 1) {
            DialogCalculadoraPago dialogCalculadoraPago = new DialogCalculadoraPago(idMetodoPago, context, CantidadTotalPago, codigoMetodoPago, nombreTipoPago);
            DialogFragment dialogFragment = dialogCalculadoraPago;
            dialogFragment.show(getFragmentManager(), "CalculadoraPago");
            dialogCalculadoraPago.setListenerCantidadPago(this);
        }
    }

    @Override
    public void PasarCantidadCancelada(int idMetodoPago, BigDecimal cantidadCancelada, String tipoPago, String nombreTipoPago) {
        pagosEnVentaList.clear();
        pagosEnVentaList.addAll(rvAdapterPagosEnVenta.getList());
        int longitud = pagosEnVentaList.size();
        byte encontro = 0;
        BigDecimal cantidadPagada = new BigDecimal(0);
        if (longitud > 0) {
            for (int i = 0; i < longitud; i++) {
                if (pagosEnVentaList.get(i).getIdTipoPago() == idMetodoPago) {

                    cantidadPagada = pagosEnVentaList.get(i).getCantidadPagada().add(cantidadCancelada);
                    encontro = 1;
                }
            }
        } else if (longitud == 0) {
            cantidadPagada = cantidadCancelada;
        }
        if (encontro == 0) {

            cantidadPagada = cantidadCancelada;
        }


        pagosEnVenta = new mPagosEnVenta(idMetodoPago, tipoPago, nombreTipoPago, cantidadPagada);
        new AgregarPagoTemporal().execute(pagosEnVenta);

    }

    public void ocultarMetodoPago() {
        btnFinalizarVenta.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);
    }

    public void mostrarMetodosPago() {
        btnFinalizarVenta.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
    }

    @Override
    public void numeroElementos(int cantidad, BigDecimal montoPagado) {

        if (cantidad == 0) {

            rvMetodosDePago.setVisibility(View.GONE);
            txtSinMetodoDePago.setVisibility(View.VISIBLE);
        } else if (cantidad > 0) {

            rvMetodosDePago.setVisibility(View.VISIBLE);
            txtSinMetodoDePago.setVisibility(View.GONE);
        }

        cantidadACuenta = cantidadTotalPago.subtract(montoPagado);

        if (cantidadACuenta.compareTo(new BigDecimal(0)) <= 0) {
            ocultarMetodoPago();
            if (cantidadACuenta.compareTo(new BigDecimal(0)) == 0) {
                txtTituloPrecio.setText("Pago Terminado");

            } else {
                txtTituloPrecio.setText("Cambio " + simboloMoneda + String.format("%.2f", cantidadACuenta.multiply(new BigDecimal(-1))));
            }
        } else if (cantidadACuenta.compareTo(new BigDecimal(0)) > 0) {

            mostrarMetodosPago();
            txtTituloPrecio.setText("Total " + simboloMoneda + String.format("%.2f", cantidadACuenta));
        }
    }


    public interface ListenerVentaFinalizada {

        public void GuardarPagos(BigDecimal CantidadCambio);

    }

    public class AgregarPagoTemporal extends AsyncTask<mPagosEnVenta, Void, Byte> {

        Dialog dialog = progressDialog();

        public ProgressDialog progressDialog() {
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Guardando pago");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            return progressDialog;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Byte doInBackground(mPagosEnVenta... mPagosEnVentas) {
            return bdConnectionSql.GuardarPagoTemporal(idCabeceraPedido, mPagosEnVentas[0]);
        }

        @Override
        protected void onPostExecute(Byte aByte) {
            super.onPostExecute(aByte);
            dialog.dismiss();
            if (aByte == 1) {
                rvAdapterPagosEnVenta.AddElement(pagosEnVenta.getIdTipoPago(), pagosEnVenta.getcTipoPago(), pagosEnVenta.getTipoPago(), pagosEnVenta.getCantidadPagada());

            } else if (aByte == 0) {
                Toast.makeText(context, "No se logro guardar el pago", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Verifique su conexion a internet", Toast.LENGTH_SHORT).show();

            }
        }
    }


    public class DownloadList extends AsyncTask<Void, Void, List<mMedioPago>> {


        @Override
        protected List<mMedioPago> doInBackground(Void... voids) {
            return bdConnectionSql.getMPagos();
        }

        @Override
        protected void onPostExecute(List<mMedioPago> mMedioPagos) {
            super.onPostExecute(mMedioPagos);
            if (mMedioPagos != null) {
                rvGridMPagos.AddElement(mMedioPagos);
                pagosEnVentaList = bdConnectionSql.getPagosRealizados(idCabeceraPedido);
                if (pagosEnVentaList.size() > 0) {
                    rvAdapterPagosEnVenta.AgregarMetodoPagoTemporal(pagosEnVentaList);
                }
            } else {
                Toast.makeText(getActivity(), "Error al recuperar los medios de pago", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Verifique su conexion a internet", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
