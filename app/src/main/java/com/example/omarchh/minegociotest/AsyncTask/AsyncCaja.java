package com.example.omarchh.minegociotest.AsyncTask;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.DialogFragments.DialogCargaAsync;
import com.example.omarchh.minegociotest.Model.mCierre;

import java.math.BigDecimal;

/**
 * Created by OMAR CHH on 09/02/2018.
 */

public class AsyncCaja {

    ListenerAperturaCaja aperturaCaja;
    BdConnectionSql bdConnectionSql=BdConnectionSql.getSinglentonInstance();
    private BigDecimal Monto;
    private Context context;
    private DialogCargaAsync dialogCargaAsync;

    public AsyncCaja(Context context) {
        Monto = new BigDecimal(0);
        this.context=context;
        dialogCargaAsync = new DialogCargaAsync(context);
    }

    public void setListenerAperturaCaja(ListenerAperturaCaja aperturaCaja) {

        this.aperturaCaja = aperturaCaja;
    }

     public void AperturarCaja(BigDecimal monto) {

        Monto = monto;
        new AperturaCargaAsync().execute(Monto);

     }

    public void ObtenerIdCierreCaja() {

        new ObtenerUltimoCierreDisponiblePorCaja().execute();

    }

    public void CerrarCaja(int idCierre) {
        new CerrarCajaAsync().execute(idCierre);
    }

    public interface ListenerAperturaCaja {

        public void ConfirmacionAperturaCaja();

        public void ExisteCierreAperturado(int idCierre);

        public void AperturarCaja();

        public void ConfirmarCierreCaja();

    }

    private class CerrarCajaAsync extends AsyncTask<Integer, Void, Byte> {
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = dialogCargaAsync.getDialogCarga("Cerrando caja");
            dialog.show();
        }

        @Override
        protected void onPostExecute(Byte aByte) {
            super.onPostExecute(aByte);
            dialog.dismiss();
            if (aByte == 100) {
                Toast.makeText(context, "Se cerro la caja", Toast.LENGTH_SHORT).show();
                aperturaCaja.ConfirmarCierreCaja();
            } else if (aByte == 99) {
                Toast.makeText(context, "Error al cerrar", Toast.LENGTH_SHORT).show();
            } else if (aByte == 0) {
                Toast.makeText(context, "Verificar conexion", Toast.LENGTH_SHORT).show();
            } else if (aByte == 1) {
                Toast.makeText(context, "Error al cerrar", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected Byte doInBackground(Integer... integers) {
            return bdConnectionSql.CerrarCaja(integers[0]);
        }
    }
     private class AperturaCargaAsync extends AsyncTask<BigDecimal,Void,Byte>{


         @Override
         protected void onPreExecute() {
             super.onPreExecute();
         }

         @Override
         protected void onPostExecute(Byte aByte) {
             super.onPostExecute(aByte);
             if(aByte==99){

                 Toast.makeText(context,"Error al aperturar la caja",Toast.LENGTH_SHORT).show();
             }
             else if(aByte==0){
                 Toast.makeText(context,"Verifique su conexion",Toast.LENGTH_SHORT).show();
             }
             else {
                 Toast.makeText(context,"La caja se aperturo",Toast.LENGTH_SHORT).show();
                 aperturaCaja.ConfirmacionAperturaCaja();
             }
         }

         @Override
         protected Byte doInBackground(BigDecimal... bigDecimals) {
             return bdConnectionSql.aperturarCaja(bigDecimals[0]);
         }
     }

    private class ObtenerUltimoCierreDisponiblePorCaja extends AsyncTask<Void, Void, mCierre> {
        Dialog dialog = dialogCargaAsync.getDialogCarga("Obteniendo Caja");

        @Override
        protected void onPreExecute() {

            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected mCierre doInBackground(Void... voids) {
            return bdConnectionSql.ObtenerIdCierre();
        }


        @Override
        protected void onPostExecute(mCierre cierre) {
            super.onPostExecute(cierre);
            dialog.dismiss();
            if (cierre.getIdCierre() > 0) {
                aperturaCaja.ExisteCierreAperturado(cierre.getIdCierre());

            } else if (cierre.getIdCierre() == 0) {
                Toast.makeText(context, "Debe abrir caja", Toast.LENGTH_SHORT).show();
                aperturaCaja.AperturarCaja();
                //DialogFragment dialogFragment=dialogAperturaCaja;
                //dialogFragment.show(getFragmentManager(),"Apertura caja");
            } else if (cierre.getIdCierre() == -2) {

                Toast.makeText(context, "Verifique su conexion", Toast.LENGTH_SHORT).show();

            }
        }
    }

}
