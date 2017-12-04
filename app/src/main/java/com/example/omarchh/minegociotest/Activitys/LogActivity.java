package com.example.omarchh.minegociotest.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.ConexionBd.DbHelper;
import com.example.omarchh.minegociotest.Controlador.ControladorLogin;
import com.example.omarchh.minegociotest.R;

public class LogActivity extends AppCompatActivity implements View.OnClickListener{

    ControladorLogin controladorLogin;

    Button btnAccessLog;
    EditText edtUserLog;
    EditText edtPasswordLog;
    BdConnectionSql bdConnectionSql;
    ProgressBar progressBar;
    DbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        helper=new DbHelper(this);
        bdConnectionSql=new BdConnectionSql();
        btnAccessLog=(Button)findViewById(R.id.btnAccessLog);
        edtUserLog=(EditText)findViewById(R.id.edtUserLog);
        edtPasswordLog=(EditText)findViewById(R.id.edtPasswordLog);
        progressBar=(ProgressBar)findViewById(R.id.progressBarLogin);
        btnAccessLog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnAccessLog){
            LoguinUser();
         }
    }


    public void LoguinUser(){

        controladorLogin=new ControladorLogin(this,progressBar);
        controladorLogin.execute(edtUserLog.getText().toString(),edtPasswordLog.getText().toString());

    }

}
