package com.example.omarchh.minegociotest.Activitys;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.R;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    final Calendar c = Calendar.getInstance();
    WebView mWebView;
    BdConnectionSql bdConnectionSql;
    String myIMEI = "";
    Dialog dialog;
    byte respuesta = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bdConnectionSql = BdConnectionSql.getSinglentonInstance();
        myIMEI = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        new loguinUser().execute();


    }

    private void LoadLog() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Cargando datos");
        progressDialog.setCanceledOnTouchOutside(false);
        dialog = progressDialog;
        dialog.show();

    }

    private void OpenApp() {
        Intent intent = new Intent(this, PantallaPrincipal.class);
        startActivity(intent);
        finish();
    }

    class loguinUser extends AsyncTask<Void, Void, Boolean> {
        boolean resp = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LoadLog();
            myIMEI = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);


        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            resp = bdConnectionSql.LoguinUsuario();
            bdConnectionSql.VerificarTerminalImei(myIMEI);
            bdConnectionSql.GetIdTerminal(myIMEI);
            respuesta = bdConnectionSql.SimboloMonedaPorDefecto();
            resp = resp;


            return resp;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean.equals(true)) {
                dialog.dismiss();


                OpenApp();

            } else if (aBoolean.equals(false)) {
                dialog.dismiss();
                Toast.makeText(getBaseContext(), "No", Toast.LENGTH_LONG).show();

            }

        }

    }
}
