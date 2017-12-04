package com.example.omarchh.minegociotest.Activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omarchh.minegociotest.ConexionBd.BdConnectionSql;
import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Controlador.ControladorProductos;
import com.example.omarchh.minegociotest.DialogFragments.SelectUnitDialog;
import com.example.omarchh.minegociotest.Model.AdditionalPriceProduct;
import com.example.omarchh.minegociotest.Model.mProduct;
import com.example.omarchh.minegociotest.R;
import com.example.omarchh.minegociotest.RVAdapterAdditionalPrice;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddEditProduct extends AppCompatActivity implements View.OnClickListener,View.OnFocusChangeListener {

    ControladorProductos controladorProductos;
    String campoUnidad="Seleccione una Unidad";
    String EstadoProducto;
    RecyclerView recyclerView;
    RVAdapterAdditionalPrice rvAdapterAdditionalPrice;
    boolean estadoImagen=false;
    ImageButton btnAceptar;
    ImageButton btnCancelar;
    CircleImageView circleImageViewProducto;
    EditText edtCategoriaProduct;
    EditText edtKey;
    Button btnScan;
    ImageButton imgElegirFoto;
    EditText edtUnit;
    EditText edtAdditionalInformation;
    EditText edtProductName;
    EditText edtQuantity;
    LinearLayoutManager llm;
    EditText edtQuantityReserve;
    EditText edtPurcharsePrice;
    EditText edtSalesPrice;
    ImageButton imgBtnAddPrice;
    BdConnectionSql bdConnectionSql;
    final static int cons=0;
    private final int SELECT_PICTURE=200;
    Bitmap bmp;
    ImageButton btnTomarFoto;
    int codigoProducto=0;
    String option="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        edtCategoriaProduct=(EditText)findViewById(R.id.edtCategoryProduct);
        bmp=null;
        controladorProductos=new ControladorProductos(this);






        EstadoProducto=getIntent().getStringExtra(Constantes.EstadoProducto.EstadoProducto);
        llm=new LinearLayoutManager(this);
        bdConnectionSql=new BdConnectionSql();
        recyclerView=(RecyclerView)findViewById(R.id.rvAdditionalPrice);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        imgBtnAddPrice=(ImageButton)findViewById(R.id.imgbtnOpenDialogAddPrice);
        btnTomarFoto=(ImageButton)findViewById(R.id.btnCamaraImagen);
        //circleImageViewProducto=(CircleImageView)findViewById(R.id.CircleImageProducto);
        edtKey=(EditText)findViewById(R.id.editKey);
        edtProductName=(EditText)findViewById(R.id.edtProductName);
        edtQuantity=(EditText)findViewById(R.id.edtQuantity);
        edtQuantityReserve=(EditText)findViewById(R.id.edtQuantityReserve);
        edtPurcharsePrice=(EditText)findViewById(R.id.edtPurcharsePrice);
        edtSalesPrice=(EditText)findViewById(R.id.edtSalesPrice);

        edtAdditionalInformation=(EditText)findViewById(R.id.edtAdditionalInformation);
        btnAceptar=(ImageButton)findViewById(R.id.btnInventarioAceptar);
        btnCancelar=(ImageButton)findViewById(R.id.btnInventarioCancel);
        btnScan=(Button)findViewById(R.id.btnScan);
        imgElegirFoto=(ImageButton)findViewById(R.id.btnBuscarImagen);
        edtCategoriaProduct.setOnClickListener(this);
        VerificarEstado();





    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.edtCategoryProduct){

            CreateDialogInsertPrice();
            }

        if(v.getId()==R.id.imgbtnOpenDialogAddPrice){
            CreateDialogInsertPrice();

        }
        if(v.getId()==R.id.btnScan){
            option="ScanearCodigo";
           // ScanCode();


        }

        if(v.getId()==R.id.btnCamaraImagen)
        {
            option="TomarFoto";

            //takephoto();
        }
        if(v.getId()==R.id.btnBuscarImagen){
            option="ElegirFoto";
            choisePhoto();
        }
        if(v.getId()==R.id.btnInventarioAceptar){

            if(ValidarPrecioCompraMayorPrecioVenta() && VerificarCamposCompletados() ){

                controladorProductos.GuardarDatos(ObtenerDatos(),EstadoProducto);
            }

        }
    }
    private void ScanCode(){

        IntentIntegrator scanIntegrator=new IntentIntegrator(this);
        scanIntegrator.setPrompt("Escannear Codigo de barras");
        scanIntegrator.setBeepEnabled(true);
        scanIntegrator.setOrientationLocked(true);
        scanIntegrator.setBarcodeImageEnabled(true);
        scanIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(final int requestCode,int resultCode,Intent intent){

        super.onActivityResult(requestCode,resultCode,intent);
        IntentResult scanningResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
        if(option=="TomarFoto"){
          if(resultCode== Activity.RESULT_OK){
              Bundle ext=intent.getExtras();
              bmp=(Bitmap)ext.get("data");
              circleImageViewProducto.setImageBitmap(bmp);
              estadoImagen=true;
          }
        }
        else if(option=="ScanearCodigo") {
            String scanContent = scanningResult.getContents().toString();

            edtKey.setText(scanContent);
            Toast.makeText(this, scanContent, Toast.LENGTH_LONG).show();
        }
        else if(option=="ElegirFoto"){
            if(resultCode==Activity.RESULT_OK){
                Uri path=intent.getData();
                try {
                    bmp=MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                circleImageViewProducto.setImageBitmap(bmp);
                estadoImagen=true;
            }


        }
    }
    public void dialogUnit(){
        DialogFragment newFragment=new SelectUnitDialog();
        newFragment.show(getSupportFragmentManager(),null);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    public boolean validarCampos(){

        boolean vacio=false;

        if(!edtProductName.getText().toString().isEmpty() &&
                !edtUnit.getText().toString().isEmpty() && !edtQuantity.getText().toString().isEmpty()
                && !edtQuantityReserve.getText().toString().isEmpty() && !edtPurcharsePrice.getText().toString().isEmpty()
                && !edtSalesPrice.getText().toString().isEmpty() && !edtKey.getText().toString().isEmpty()){

            vacio=true;

        }
        return vacio;
    }

    public Dialog onCreateDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        return builder.create();
    }

    public void CreateDialogInsertPrice(){

         AlertDialog.Builder builder=new AlertDialog.Builder(this);
         LayoutInflater inflater=this.getLayoutInflater();
         View v=inflater.inflate(R.layout.dialog_insert_price_additional,null);
         builder.setView(v);

         final EditText edtAddPrice=(EditText)v.findViewById(R.id.edtAddPrice);

        builder.setTitle("Ingresa un nuevo Precio");

        builder.setPositiveButton(
                "Ingresar",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String a=edtAddPrice.getText().toString();
                        a.length();
                        a.toString();

                       edtCategoriaProduct.setText(a);
                    }
                }
        );
        builder.setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtCategoriaProduct.setText("");
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

    public void CreateDialogSelectUnit(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final List<String> unidades =new ArrayList<>();
        unidades.add("Kg");
        unidades.add("Gramos");
        unidades.add("Pieza");
        unidades.add("Litros");
        unidades.add("Metro Cubico");
        unidades.add("Metro");
        unidades.add("Metro Cuadrado");
        final CharSequence[]cs=unidades.toArray(new CharSequence[unidades.size()]);
        builder.setTitle("Elige una Unidad");

        builder.setItems(cs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtUnit.setText(cs[which]);
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }
    public void takephoto(){

        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,cons);

    }
    public void choisePhoto(){

        Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(getIntent().createChooser(i,"Selecciona una imagen"),SELECT_PICTURE);
    }

    private void EnviarDatosCampos(){
        codigoProducto=getIntent().getIntExtra("IdProducto",0);
        edtKey.setText(getIntent().getStringExtra("CodigoProducto"));
        edtProductName.setText(getIntent().getStringExtra("NombreProducto"));
        edtUnit.setText(getIntent().getStringExtra("Unidad"));
        edtQuantity.setText(String.valueOf(getIntent().getFloatExtra("Cantidad",0)));
        edtQuantityReserve.setText(String.valueOf(getIntent().getFloatExtra("CantidadReserva",0)));
        edtAdditionalInformation.setText(getIntent().getStringExtra("InformacionAdicional"));
        edtPurcharsePrice.setText(String.valueOf(getIntent().getFloatExtra("PrecioCompra",0)));
        edtSalesPrice.setText(String.valueOf(getIntent().getFloatExtra("PrecioVenta",0)));
        byte[]data=getIntent().getByteArrayExtra("DataImagen");
        if(data!=null) {
            bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            circleImageViewProducto.setImageBitmap(bmp);
        }
    }

    private void VaciarCampos(){
        edtKey.setText("");
        edtProductName.setText("");
        edtQuantity.setText("");
        edtQuantityReserve.setText("");
        edtSalesPrice.setText("");
        edtPurcharsePrice.setText("");
        edtAdditionalInformation.setText("");
    }
    private void VerificarEstado() {

        switch (EstadoProducto) {
            case Constantes.EstadoProducto.EditarProducto:
                EnviarDatosCampos();
                break;
            case Constantes.EstadoProducto.NuevoProducto:
                VaciarCampos();
                break;
        }
    }

    private boolean ValidarPrecioCompraMayorPrecioVenta(){
        boolean a;
        if(Float.parseFloat(edtPurcharsePrice.getText().toString())<Float.parseFloat(edtSalesPrice.getText().toString())){
            a=true;

        }
        else{
            Toast.makeText(this,"El precio de venta debe ser mayor al precio de Compra",Toast.LENGTH_SHORT).show();
            edtSalesPrice.setError("Debe ser mayor al precio de compra");
            a=false;
        }
        return a;
    }

    private boolean VerificarCamposCompletados(){

        boolean permitir;
        if(!edtKey.getText().toString().isEmpty() && !edtProductName.getText().toString().isEmpty()
                && !edtUnit.getText().toString().equals(campoUnidad) && !edtQuantity.getText().toString().isEmpty()
                && !edtQuantityReserve.getText().toString().isEmpty() && !edtSalesPrice.getText().toString().isEmpty()
                && !edtPurcharsePrice.getText().toString().isEmpty()){

            permitir=true;
        }
        else{

            permitir=false;
        }
        return permitir;

    }
    private mProduct ObtenerDatos(){
        mProduct product = new mProduct();
        product.setIdProduct(codigoProducto);
        product.setcKey(edtKey.getText().toString());
        product.setcProductName(edtProductName.getText().toString());
        product.setcUnit(edtUnit.getText().toString());
        product.setdQuantity(Float.parseFloat(edtQuantity.getText().toString()));
        product.setdQuantityReserve(Float.parseFloat(edtQuantityReserve.getText().toString()));
        product.setdPurcharsePrice(Float.parseFloat(edtPurcharsePrice.getText().toString()));
        product.setdSalesPrice(Float.parseFloat(edtSalesPrice.getText().toString()));
        product.setcAdditionalInformation(edtAdditionalInformation.getText().toString());
        if(bmp!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            product.setbImage(stream.toByteArray());
        }
        else{
            product.setbImage(null);
        }
        return product;
    }


}
