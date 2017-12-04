package com.example.omarchh.minegociotest.ConexionBd;

import android.os.StrictMode;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.mProduct;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OMAR CHH on 23/09/2017.
 */

public class BdConnectionSql {

   // private ResultSet rs;
    String ConnectionUrl="";
    List<mProduct> listProduct;




    static{
        try{

            Class.forName(Constantes.classForName.classForName).newInstance();
        }
        catch (Exception e){
             e.printStackTrace();
        }

    }

    private static Connection getConnection(){
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn=null;
        try{
            Class.forName(Constantes.classForName.classForName).newInstance();
            conn=DriverManager.getConnection(Constantes.UrlConnection.urlConnection);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }

  /*  public void Conn() throws SQLException {
        listProduct=new ArrayList<>();
        conn=bdConnectionClass(Constantes.bdConstantes.user, Constantes.bdConstantes.password, Constantes.bdConstantes.url, Constantes.bdConstantes.db);
        Statement statement=conn.createStatement();
        if(conn==null){


            for(int i=0;i<20;i++){
                Log.e("Mesagge","Check your internet Access!");
            }xº
        }

        //   PreparedStatement preparedStatement=conn.prepareStatement("EXEC selectProduct");
        //   rs=preparedStatement.executeQuery();




    }
  */
    public Connection bdConnectionClass(String User,String Password, String Url, String database){

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection=null;
        ConnectionUrl="jdbc:jtds:sqlserver://"+Url+":1433;databaseName="+database+";user="+User+";password="+Password+";";
        try{

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection= DriverManager.getConnection(ConnectionUrl);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return connection;
    }
     public List<mProduct> getListProduct() throws SQLException {
          List<mProduct> mProducts=new ArrayList<>();
            mProduct p;
          Connection con=getConnection();

          CallableStatement cs=null;
         String a="";
         String b="";
         int id=0;
         float c=0;
         float d=0;
         byte[]f=null;
         String e="";

          try{

            cs=con.prepareCall("{call "+ Constantes.storedProcedure.getViewProduct+"(?)}");

              cs.setString(Constantes.Parametros.parametercodigoCompania,Constantes.Empresa.CodigoEmpresa);
              cs.execute();
              ResultSet rs=cs.getResultSet();

                  while(rs.next()){
                      id=rs.getInt(Constantes.columnsProduct.idProduct);
                      a=rs.getString(Constantes.columnsProduct.key);
                      b=rs.getString(Constantes.columnsProduct.productName);
                      c=rs.getFloat(Constantes.columnsProduct.quantity);
                          d=rs.getFloat(Constantes.columnsProduct.salesPrice);
                          f=rs.getBytes(Constantes.columnsProduct.imageFile);

                          p= new mProduct();
                          p.setIdProduct(id);
                          p.setcKey(a);
                          p.setcProductName(b);
                          p.setbImage(f);
                      p.setdQuantity(c);
                      p.setdSalesPrice(d);
                      mProducts.add(p);
                  }
          }catch(SQLException ex){
              ex.getMessage().toString();

          }finally {
              con.close();
          }

           return mProducts;
        }

    public mProduct getProductbyId(int id){
        int idProduct=0;
        String productName="";
        String codigo="";
        float cantidad=0;
        float cantidadReserva=0;
        float precioCompra=0;
        float precioVenta=0;
        Connection con=getConnection();
        CallableStatement cs=null;
        try {
            cs=con.prepareCall("call "+Constantes.storedProcedure.sp_getProduct+"(?,?,?,?,?,?)");
            cs.setString(Constantes.Parametros.parameterBusqueda,"");
            cs.setString(Constantes.Parametros.parametercodigoCompania,Constantes.Empresa.CodigoEmpresa);
            cs.setInt(Constantes.Parametros.parameteriCodigoProducto,id);
            cs.setString(Constantes.Parametros.parameterCodigoProducto,"");
            cs.setString(Constantes.Parametros.parameterNombreProducto,"");
            cs.setString(Constantes.Parametros.parameterCantidadInformacion,"busquedaId");

            cs.execute();

            ResultSet rs=cs.getResultSet();




            while(rs.next()){
                    idProduct=rs.getInt(Constantes.DatosNombre.IDPRODUCTO);
                    productName=rs.getString(Constantes.DatosNombre.NOMBREPRODUCTO);
                    codigo=rs.getString(Constantes.DatosNombre.CODIGOPRODUCTO);
                    cantidad=rs.getFloat(Constantes.DatosNombre.CANTIDADPRODUCTO);
                    cantidadReserva=rs.getFloat(Constantes.DatosNombre.CANTIDADRESERVAPRODUCTO);
                    precioCompra=rs.getFloat(Constantes.DatosNombre.PRECIOCOMPRAPRODUCTO);
                    precioVenta=rs.getFloat(Constantes.DatosNombre.PRECIOVENTAPRODUCTO);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new mProduct(idProduct,codigo,productName,"",cantidad,cantidadReserva,precioCompra,precioVenta,"",null);
    }

    public List<mProduct> getProductName(String TextoBusqueda,String NombreProducto,int idProducto,String CodigoProducto,String CantidadInformacion){
        listProduct=new ArrayList<>();
        Connection con=getConnection();
        CallableStatement cs=null;

         try {
             cs=con.prepareCall("{call "+Constantes.storedProcedure.sp_getProduct+"(?,?,?,?,?,?)}");
             cs.setString(Constantes.Parametros.parameterBusqueda,TextoBusqueda);
             cs.setString(Constantes.Parametros.parametercodigoCompania,Constantes.Empresa.CodigoEmpresa);
             cs.setString(Constantes.Parametros.parameterNombreProducto,NombreProducto);
             cs.setString(Constantes.Parametros.parameterCodigoProducto,CodigoProducto);
             cs.setString(Constantes.Parametros.parameterCantidadInformacion,CantidadInformacion);
             cs.setInt(Constantes.Parametros.parameteriCodigoProducto,idProducto);
             cs.execute();

             ResultSet rs=cs.getResultSet();
             while(rs.next())
             {
                 int cod=0;
                 String cKey="";
                 String cProductName="";

                 cod=rs.getInt(Constantes.DatosNombre.IDPRODUCTO);
                 cKey=rs.getString(Constantes.DatosNombre.CODIGOPRODUCTO);
                 cProductName=rs.getString(Constantes.DatosNombre.NOMBREPRODUCTO);

                 listProduct.add(new mProduct(cod,cKey,cProductName,"",0,0,0,0,"",null));
             }

         } catch (SQLException e) {
             e.printStackTrace();
         }

         return listProduct;

     }
     public List<mProduct> getProductInReserve() throws  SQLException{
         listProduct=new ArrayList<>();

         Connection con=getConnection();
         CallableStatement cs=null;
         String a="";
         String b="";
         float c=0;
         float d=0;
         byte[]f;

         try{

            cs=con.prepareCall("{call "+Constantes.storedProcedure.searchProductInReserve+"(?)}");
             cs.setString(1,"C0001");
             cs.execute();
             ResultSet rs=cs.getResultSet();
             while(rs.next()){
                 a=rs.getString(Constantes.columnsProduct.key);
                 b=rs.getString(Constantes.columnsProduct.productName);
                 c=rs.getFloat(Constantes.columnsProduct.quantity);
                 d=rs.getFloat(Constantes.columnsProduct.quantityReserve);
                 f=rs.getBytes(Constantes.columnsProduct.imageFile);

                  listProduct.add(new mProduct(a,b,c,d,f));
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             con.close();
             getConnection().close();
         }


         return listProduct;
     }
    public List<mProduct> searchProduct(String parametro){

        List<mProduct> mProducts=new ArrayList<>();
        Connection con=getConnection();
        CallableStatement cs=null;
        String a="";
        String b;
        float c;
        float d;
        byte[] f=null;
        try {
            cs = con.prepareCall("{call " + Constantes.storedProcedure.searchProcedure + " (?,?)}");
            cs.setString("parametro",parametro);
            cs.setString(Constantes.Parametros.parametercodigoCompania,Constantes.Empresa.CodigoEmpresa);
            cs.execute();

            ResultSet rs=cs.getResultSet();
            while(rs.next()){
                a=rs.getString(Constantes.columnsProduct.key);
                b=rs.getString(Constantes.columnsProduct.productName);
                c=rs.getFloat(Constantes.columnsProduct.quantity);
                d=rs.getFloat(Constantes.columnsProduct.salesPrice);
                f=rs.getBytes(Constantes.columnsProduct.imageFile);
                mProducts.add(new mProduct(a,b,c,d,f));
            }

        }
       catch (SQLException e) {
            e.printStackTrace();

        }
       return mProducts;
    }
    public mProduct obtenerProducto(String codigoProducto,String codigoEmpresa){
        mProduct product=new mProduct();
        Connection con= getConnection();
        String a="";
        CallableStatement cs=null;
        try {
            cs=con.prepareCall("{call "+Constantes.storedProcedure.getProduct+"(?,?)}");
            cs.setString(Constantes.Parametros.parametercodigoCompania,codigoEmpresa);
            cs.setString(Constantes.Parametros.parameterCodigoProducto,codigoProducto);
            cs.execute();
            ResultSet rs=cs.getResultSet();
            while(rs.next()){

                product=new mProduct();
                product.setIdProduct(rs.getInt(Constantes.columnsProduct.idProduct));
                product.setcKey(rs.getString(Constantes.columnsProduct.key));
                product.setbImage(rs.getBytes(Constantes.columnsProduct.imageFile));
                product.setcProductName(rs.getString(Constantes.columnsProduct.productName));
                product.setcUnit(rs.getString(Constantes.columnsProduct.unit));
                product.setcAdditionalInformation(rs.getString(Constantes.columnsProduct.additionalInformation));
                product.setdQuantity(rs.getFloat(Constantes.columnsProduct.quantity));
                product.setdQuantityReserve(rs.getFloat(Constantes.columnsProduct.quantityReserve));
                product.setdPurcharsePrice(rs.getFloat(Constantes.columnsProduct.purcharsePrice));
                product.setdSalesPrice(rs.getFloat(Constantes.columnsProduct.salesPrice));
                product.setbImage(rs.getBytes(Constantes.columnsProduct.imageFile));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return product;
    }

    public boolean UpdateProduct(mProduct product){
        boolean a=false;
        Connection con=getConnection();
        CallableStatement cs=null;

        try {
            cs=con.prepareCall("{call "+Constantes.storedProcedure.updateProduct+"(?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setString(Constantes.Parametros.parametercodigoCompania,Constantes.Empresa.CodigoEmpresa);
            cs.setInt(Constantes.Parametros.parameterIdProducto,product.getIdProduct());
            cs.setString(Constantes.Parametros.parameterCodigoProducto,product.getcKey());
            cs.setString(Constantes.Parametros.parameterNombreProducto,product.getcProductName());
            cs.setString(Constantes.Parametros.parameterUnidadProducto,product.getcUnit());
            cs.setString(Constantes.Parametros.parameterInformacionAdicionalProducto,product.getcAdditionalInformation());
            cs.setFloat(Constantes.Parametros.parameterCantidadProducto,product.getdQuantity());
            cs.setFloat(Constantes.Parametros.parameterCantidadReservaProducto,product.getdQuantityReserve());
            cs.setFloat(Constantes.Parametros.parameterPrecioCompra,product.getdPurcharsePrice());
            cs.setFloat(Constantes.Parametros.parameterPrecioVenta,product.getdSalesPrice());
            cs.setBytes(Constantes.Parametros.parameterImagen,product.getbImage());
            cs.execute();
            a=true;
        } catch (SQLException e) {
            e.printStackTrace();
            a=false;
        }
        return a;
    }

    public boolean saveAddSalesPrice(String cKey,List<Integer> a,String codigoProducto){

        Connection con=getConnection();
        CallableStatement cs=null;
        int i=0;
        while(i<a.size()) {
            try {
                cs = con.prepareCall("{call (?,?,?)}");
                cs.setInt(Constantes.Parametros.parameterPrecioAdicional,a.get(0));
                cs.setString(Constantes.Parametros.parameterCodigoProducto,cKey);
                cs.setString(Constantes.Parametros.parametercodigoCompania,Constantes.Empresa.CodigoEmpresa);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            i++;
        }

        return false;
    }
    public boolean addProduct(mProduct product){

       Connection con =getConnection();
        CallableStatement cs=null;
        try{
            cs=con.prepareCall("{call "+Constantes.storedProcedure.insertProduct+"(?,?,?,?,?,?,?,?,?)}");
            cs.setString(1,"C0001");
            cs.setString(2,product.getcKey());
            cs.setString(3,product.getcProductName());
            cs.setString(4,product.getcUnit());
            cs.setFloat(5,product.getdQuantity());
            cs.setFloat(6,product.getdQuantityReserve());
            cs.setFloat(7,product.getdSalesPrice());
            cs.setFloat(8,product.getdPurcharsePrice());
            cs.setString(9,product.getcAdditionalInformation());
            cs.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public String checkLogBdUser(String userName,String userPassword){
        String message="";
        Connection con=getConnection();
        CallableStatement cs=null;


        try {
            cs=con.prepareCall("call "+Constantes.storedProcedure.checkloginUser+"(?,?,?,?)");

            cs.setString(Constantes.Parametros.parameterUser,userName);
            cs.setString(Constantes.Parametros.parameterPassword,userPassword);
            cs.setString(Constantes.Parametros.parametercodigoCompania,"C0001");
            cs.registerOutParameter(Constantes.Parametros.parameterExisteUsuario, Types.VARCHAR);
            cs.execute();

           message=cs.getString(Constantes.Parametros.parameterExisteUsuario);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return message;

    }

    public boolean saveCustomer(){

        return true;
    }


    public boolean saveImageProduct(mProduct product){

        Connection con=getConnection();
        CallableStatement cs=null;

        try {
            cs=con.prepareCall("call "+Constantes.storedProcedure.insertImageProduct+"(?,?,?)}");
            cs.setString(1,"C0001");
            cs.setString(2,product.getcKey());

            InputStream is=new ByteArrayInputStream(product.getbImage());
            cs.setBinaryStream(3,is,product.getbImage().length);

            cs.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public byte[] imagen() throws SQLException {
        byte[]a=null;
        Connection con=getConnection();
        Statement statement=con.createStatement();

        ResultSet rs=statement.executeQuery("SELECT IMAGEFILE FROM PRODUCTIMAGE WHERE iIdProduct>81");

        while(rs.next()){

            a=rs.getBytes(1);
        }


        return a;
    }







}
















