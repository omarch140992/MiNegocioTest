package com.example.omarchh.minegociotest.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.Bluetooth.BluetoothConnection;
import com.example.omarchh.minegociotest.ConexionBd.DbHelper;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.ProductoEnVenta;
import com.example.omarchh.minegociotest.PrintOptions.PrintOptions;
import com.example.omarchh.minegociotest.R;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * Created by OMAR CHH on 27/12/2017.
 */

public class DialogVentaResultado extends DialogFragment implements View.OnClickListener {

    final Calendar c = Calendar.getInstance();
    Dialog dialog;
    Button btnImprimir, btnProximaVenta;
    List<ProductoEnVenta> list;
    TextView txtCambio;
    String deviceAddressBt = "";
    BluetoothConnection btConnection;
    BigDecimal CantidadCambio = new BigDecimal(0);
    PrintOptions printOptions;
    ListenerTerminarVenta listenerTerminarVenta;
    DbHelper dbHelper;
    private WebView mWebView;

    public DialogVentaResultado(List<ProductoEnVenta> list, BigDecimal CantidadCambio) {

        this.list = list;
        this.CantidadCambio = CantidadCambio;

    }

    public void setListenerTerminarVenta(ListenerTerminarVenta listenerTerminarVenta) {

        this.listenerTerminarVenta = listenerTerminarVenta;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_resultado_venta, null);
        btConnection = BluetoothConnection.getSinglentonInstance(getActivity());
        txtCambio = (TextView) v.findViewById(R.id.txtCambioVenta);
        btnImprimir = (Button) v.findViewById(R.id.btnImprimir);
        btnProximaVenta = (Button) v.findViewById(R.id.btnProximaVenta);

        btnProximaVenta.setOnClickListener(this);
        btnImprimir.setOnClickListener(this);
        txtCambio.setText("Cambio " + Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", CantidadCambio.multiply(new BigDecimal(-1))));

        /*----------------------------------------------*/

        dbHelper = new DbHelper(getActivity());







        /*-------------------------------------------*/


        builder.setView(v);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnImprimir:
                SeleccionarMetodo();
                break;

            case R.id.btnProximaVenta:

                dialog.dismiss();
                break;


        }

    }

    public void SeleccionarMetodo() {

        String option;
        Cursor c = dbHelper.SelectOptionPrint();
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                option = c.getString(0);
                switch (option) {

                    case "PDF o impresora en red":
                        doWebViewPrint();
                        //printOptions=new PrintOptions(getActivity());
                        //printOptions.PrintPdf();
                        break;
                    case "Bluetooth":
                        if (btConnection.VerifiedBt1()) {
                            Cursor cc = dbHelper.SelectDevice();
                            if (cc.getCount() > 0) {
                                while (cc.moveToNext()) {
                                    deviceAddressBt = cc.getString(0);
                                }
                                btConnection.selectDevice(deviceAddressBt);
                                btConnection.openBT();
                                printOptions = new PrintOptions(getActivity(), btConnection.getOutputStream(), btConnection.getMmInputStream());
                                //printOptions.imprimirTicketPago(list);
                                printOptions.ImpresionTicket(list);
                            } else if (cc.getCount() == 0) {
                                Toast.makeText(getActivity(), "No selecciono una impresora", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "El bluetooth no está conectado", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "Ninguno":
                        Toast.makeText(getActivity(), "No selecciono una impresora", Toast.LENGTH_LONG).show();

                        break;
                    default:
                        Toast.makeText(getActivity(), "No selecciono una impresora", Toast.LENGTH_LONG).show();

                        break;

                }
            }

        } else {
            Toast.makeText(getActivity(), "No se selecciono una impresora", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        listenerTerminarVenta.FinalizarVenta();

    }

    private void doWebViewPrint() {
        // Create a WebView object specifically for printing
        WebView webView = new WebView(getActivity());
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                mWebView = null;
            }
        });

        String htmlInicio = "<html><body>";
        String htmlCompania = "<h1  ALIGN=center>Nombre de la Compañia</h1>";
        String htmlTienda = "<h2  ALIGN=center>Nombre de la tienda</h2>";
        String Comprobante = "<h3 ALIGN=center>Comprobante de venta</h3>";
        String InicioTabla = "<table style='witdh:100%'  ALIGN=center>";
        String CabeceraTabla = "<tr><th>Nombre del producto</th><th>Unidades</th><th>Valor Subtotal</th></tr>";
        String finalTabla = "</table>";
        String htmlFinal = "</body></html>";
        String cuerpoTabla = "";
        BigDecimal total = new BigDecimal(0);
        int a = list.size();
        for (int i = 0; i < a; i++) {

            cuerpoTabla = cuerpoTabla + "<tr>" + "<td>" + list.get(i).getProductName() + "</td>" + "<td ALIGN=center>" + list.get(i).getCantidad() + "</td>" + "<td ALIGN=right>" + Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", list.get(i).getPrecioVentaFinal()) + "</td>" + "</tr>";
        }
        String htmlDocument = htmlInicio + htmlCompania + htmlTienda + Comprobante + InicioTabla + CabeceraTabla + cuerpoTabla + finalTabla + htmlFinal;
        webView.loadDataWithBaseURL("Texto", htmlDocument, "text/HTML", "UTF-8", null);

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createWebPrintJob(mWebView);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) getActivity()
                .getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = null;
        // Get a print adapter instance
        printAdapter = webView.createPrintDocumentAdapter(getActivity().getString(R.string.app_name) + " " + String.valueOf(c.get(Calendar.YEAR)) + "_" + String.valueOf(c.get(Calendar.MONTH) + 1)
                + "_" + String.valueOf(c.get(Calendar.DAY_OF_MONTH))
        );

        // Create a print job with name and adapter instance
        String jobName = getActivity().getString(R.string.app_name) + " Document";
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

        // Save the job object for later status checking

    }

    public interface ListenerTerminarVenta {

        public void FinalizarVenta();

    }
}
