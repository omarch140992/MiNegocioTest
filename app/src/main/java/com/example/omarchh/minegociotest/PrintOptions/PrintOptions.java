
package com.example.omarchh.minegociotest.PrintOptions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.ProductoEnVenta;
import com.example.omarchh.minegociotest.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * Created by OMAR CHH on 29/12/2017.
 */

public class PrintOptions {

    final Calendar c = Calendar.getInstance();
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Context context;
    WebView mWebView;

    public PrintOptions(Context context, OutputStream mmOutputStream, InputStream mmInputStream) {


        this.context = context;
        this.mmOutputStream = mmOutputStream;
        this.mmInputStream = mmInputStream;
    }

    public PrintOptions(Context context) {
        this.context = context;
        mWebView = new WebView(context);
    }


    public void imprimirTicketPago(List<ProductoEnVenta> list) {


        printNewLine();
        String dateTime[] = getDateTime();
        printText(dateTime[0] + " " + dateTime[1]);
        printNewLine();
        printNewLine();
        printText("-----------------");
        printNewLine();
        for (int i = 0; i < list.size(); i++) {
            printText(leftRightAlign(list.get(i).getProductName(), String.format("%.2f", list.get(i).getPrecioVentaFinal())));
            printNewLine();
        }

        printNewLine();
        printNewLine();
        printText("Gracias por su visita vuelva Pronto");
        printNewLine();
        printNewLine();
        printNewLine();
        printNewLine();
        printNewLine();
    }


    private void printPhoto(int img) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
                    img);
            if (bmp != null) {
                byte[] command = Utils.decodeBitmap(bmp);
                mmOutputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command);
            } else {
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }
    //print unicode

    public void printUnicode() {
        try {
            mmOutputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printText(Utils.UNICODE_TEXT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] getDateTime() {
        final Calendar c = Calendar.getInstance();

        String dateTime[] = new String[2];
        dateTime[0] = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + 1 + "/" + c.get(Calendar.YEAR);
        dateTime[1] = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
        return dateTime;
    }

    private String leftRightAlign(String str1, String str2) {
        String ans = str1 + str2;
        if (ans.length() < 31) {
            int n = (31 - str1.length() + str2.length());
            ans = str1 + new String(new char[n]).replace("\0", " ") + str2;
        }
        return ans;
    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            mmOutputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void imprimir(String a, String b) {


        String puntos = "";
        String textoImprimir = "";
        int longitudPuntos = 0;

        if (a.length() < 18) {
            longitudPuntos = 32 - (a.length() + b.length());

            for (int i = 0; i < longitudPuntos; i++) {
                puntos = puntos + ".";
            }
            textoImprimir = a + puntos + b;
            printText(textoImprimir);

        } else if (a.length() >= 18) {


            longitudPuntos = 32 - b.length();

            for (int i = 0; i < longitudPuntos; i++) {
                puntos = puntos + ".";
            }
            textoImprimir = puntos + b;
            printText(a);
            printNewLine();
            printText(textoImprimir);

        }


    }

    private void printText(String msg) {
        try {
            // Print normal text
            mmOutputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void printNewLine() {
        try {
            mmOutputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void printCustom(String msg, int size, int align) {


        //Print config "mode"
        byte[] cc = new byte[]{0x1B, 0x21, 0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B, 0x21, 0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B, 0x21, 0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B, 0x21, 0x10}; // 3- bold with large text
        try {
            switch (size) {
                case 0:
                    mmOutputStream.write(cc);
                    break;
                case 1:
                    mmOutputStream.write(bb);
                    break;
                case 2:
                    mmOutputStream.write(bb2);
                    break;
                case 3:

                    mmOutputStream.write(bb3);
                    break;
            }

            switch (align) {
                case 0:
                    //left align
                    mmOutputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    mmOutputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    mmOutputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            mmOutputStream.write(msg.getBytes());
            mmOutputStream.write(PrinterCommands.LF);
            //outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void ImpresionTicket(List<ProductoEnVenta> list) {
        String[] tiempo = getDateTime();
        int longitud = list.size();
        String linea = "";
        BigDecimal total = new BigDecimal(0);
        for (int i = 0; i < longitud; i++) {

        }
        printNewLine();
        printNewLine();
        printNewLine();
        printCustom("Nombre Empresa", 2, 1);
        printCustom("Nombre tienda", 1, 1);
        printNewLine();

        printCustom(tiempo[0] + " " + tiempo[1], 0, 1);
        printNewLine();
        printNewLine();
        for (int i = 0; i < longitud; i++) {
            total = total.add(list.get(i).getPrecioVentaFinal());
            imprimir("x" + String.format("%.0f", list.get(i).getCantidad()) + " " + list.get(i).getProductName(), String.format("%.2f", list.get(i).getPrecioVentaFinal()));
            printNewLine();
        }
        printNewLine();
        for (int i = 0; i < 32; i++) {
            linea = linea + "-";
        }
        printText(linea);
        printNewLine();
        imprimir("Total", Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", total));
        printNewLine();
        printNewLine();
        printNewLine();
        printNewLine();
        printNewLine();

    }

    public void PrintPdf() {

        doWebViewPrint();
    }

    private void doWebViewPrint() {
        // Create a WebView object specifically for printing
        WebView webView = new WebView(context);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    createWebPrintJob(view);
                }
                mWebView = null;
            }
        });


        String htmlDocument = "<html><body><h1>Test Content</h1><p>Testing, " +
                "testing, testing...</p></body></html>";
        webView.loadDataWithBaseURL("Texto", htmlDocument, "text/HTML", "UTF-8", null);

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) context
                .getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = null;
        // Get a print adapter instance
        printAdapter = webView.createPrintDocumentAdapter(context.getString(R.string.app_name) + " " + String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + String.valueOf(c.get(Calendar.MONTH) + 1)
                + String.valueOf(c.get(Calendar.YEAR))
        );

        // Create a print job with name and adapter instance
        String jobName = context.getString(R.string.app_name) + " Document";
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

        // Save the job object for later status checking

    }

}
