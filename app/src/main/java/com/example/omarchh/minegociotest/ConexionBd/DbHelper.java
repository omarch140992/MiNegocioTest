package com.example.omarchh.minegociotest.ConexionBd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.omarchh.minegociotest.Constantes.Constantes;

/**
 * Created by OMAR CHH on 04/10/2017.
 */

public class DbHelper extends SQLiteOpenHelper {


    public DbHelper(Context context) {
        super(context, Constantes.DBSQLITE_Database.DATABASE_NAME, null, Constantes.DBSQLITE_Database.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constantes.TransactionDbSqlLite.Create_Table_User);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long insertUser(String userName,String userPassword){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constantes.DBSQLITE_Usuario.User_Name,userName);
        values.put(Constantes.DBSQLITE_Usuario.User_Password,userPassword);
        return db.insert(Constantes.DBSQLITE_Usuario.TABLE_NAME,null,values);
    }
    public Cursor checkExistUserInDb(){
        SQLiteDatabase db=getReadableDatabase();
        String[]campos=new String []{Constantes.DBSQLITE_Usuario.User_Name,Constantes.DBSQLITE_Usuario.User_Password};

        return db.query(Constantes.DBSQLITE_Usuario.TABLE_NAME,campos,null,null,null,null,null);

    }
    public void deleteUser(){

        SQLiteDatabase db=getWritableDatabase();
        db.delete(Constantes.DBSQLITE_Usuario.TABLE_NAME,null,null);

    }
}
