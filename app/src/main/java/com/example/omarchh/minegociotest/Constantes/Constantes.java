package com.example.omarchh.minegociotest.Constantes;

import android.provider.BaseColumns;

/**
 * Created by OMAR CHH on 23/09/2017.
 */

public abstract class  Constantes {



    public static abstract class Empresa{

        public final static String CodigoEmpresa="C0001";
    }

    private static abstract class bdConstantes{

        private final static String url="192.168.8.111:1433;instance=OMARCHPC";
        private final static String db="DB_A2B05D_bdNegocio";
        private final static String user="prueba";
        private final static String password="prueba";
    }


    //private final static String url="192.168.8.111:1433;instance=OMARCHPC";
   /* private final static String url="SQL7001.SmarterASP.NET";
    private final static String db="DB_A2B05D_bdNegocio";
    private final static String user="DB_A2B05D_bdNegocio_admin";
    private final static String password="ochumioque1409";
    public final static String urlConnection="jdbc:jtds:sqlserver://"+bdConstantes.url+":1433;databaseName="+bdConstantes.db+";user="+bdConstantes.user+";password="+bdConstantes.password+";";

    */
    public static abstract class UrlConnection{
       public final static String urlConnection="jdbc:jtds:sqlserver://"+bdConstantes.url+";databaseName="+bdConstantes.db+";user="+bdConstantes.user+";password="+bdConstantes.password+";"; }


    public static abstract class classForName{
        public final static String classForName="net.sourceforge.jtds.jdbc.Driver";
    }

    public static abstract class EstadoBusqueda{

        public final static String soloNombre="justName";
        public final static String listaProducto="allProduct";
        public final static String BusquedaProducto="searchProduct";


    }



    public static abstract class storedProcedure {
        public final static String sp_getProduct="sp_getProduct";
        public final static String getViewProduct="sp_allviewProduct";
        public final static String insertProduct="sp_insertProduct";
        public final static String searchProcedure="sp_buscarProducto";
        public final static String searchProductInReserve="sp_getProductReserve";
        public final static String insertImageProduct="sp_IngresarImagen";
        public final static String checkloginUser="sp_checkLoginUser";
        public final static String getProduct="sp_getOneProduct";
        public final static String updateProduct="sp_editProduct";
        public final static String saveAdditionalSalesPrice="sp_saveAdditionalPrice";
        public final static String saveCustomer="sp_saveCustomer";
    }
    public static abstract class columnsProduct{
        public final static String idProduct="iIdProduct";
        public final static String key="cKey";
        public final static String productName="cProductName";
        public final static String unit="cUnit";
        public final static String quantity="dQuantity";
        public final static String quantityReserve="dQuantityReserve";
        public final static String salesPrice="dSalesPrice";
        public final static String purcharsePrice="dPurcharsePrice";
        public final static String additionalInformation ="cAdditionalInformation";
        public final static String imageFile="imageFile";
    }
    public static abstract class SimboloMoneda{

        public final static String moneda="S/";

    }

    public static abstract class DatosNombre{

        public final static String IDPRODUCTO="iIdProduct";
        public final static String CODIGOPRODUCTO="cKey";
        public final static String NOMBREPRODUCTO="cProductName";
        public final static String UNIDADPRODUCTO="cUnit";
        public final static String CANTIDADPRODUCTO="dQuantity";
        public final static String CANTIDADRESERVAPRODUCTO="dQuantityReserve";
        public final static String PRECIOCOMPRAPRODUCTO="dPurcharsePrice";
        public final static String PRECIOVENTAPRODUCTO="dSalesPrice";
        public final static String INFORMACIONADICIONALPRODUCTO="cAdditionalInformation";



    }
    public static abstract class CantidadInformacion{

        public final static String justNameProduct="justName";
        public final static String listAllProduct="allProduct";
        public final static String oneProduct="oneProduct";
        public final static String productInReserve="productReserve";

    }
    public static abstract class Parametros {
        public final static String parameteriCodigoProducto="iCodigoProducto";
        public final static String parameterCantidadInformacion="cCantidadInformacion";
        public final static String parameterBusqueda="cParametroBusqueda";
        public final static String parameterUser="cCodigoUsuario";
        public final static String parameterPassword="cPasswordUser";
        public final static String  parametercodigoCompania="cCodigoEmpresa";
        public final static String parameterExisteUsuario="existe";
        public final static String parameterCodigoProducto="cCodigoProducto";
        public final static String parameterIdProducto="iIdProducto";
        public final static String parameterNombreProducto="cNombreProducto";
        public final static String parameterPrecioVenta="dPrecioVenta";
        public final static String parameterPrecioCompra="dPrecioCompra";
        public final static String parameterCantidadProducto="dCantidadProducto";
        public final static String parameterCantidadReservaProducto="dCantidadReservaProducto";
        public final static String parameterUnidadProducto="cUnidadProducto";
        public final static String parameterInformacionAdicionalProducto="cInformacionAdicionalProducto";
        public final static String parameterImagen="@bImagen";
        public final static String parameterPrecioAdicional="dPrecioAdicional";
        public final static String parameterNombreCliente="cName";
        public final static String parameterAliasCliente="cAlias";
        public final static String parameterPrimerNumeroTelefono="cFirstPhoneNumber";
        public final static String parameterSegundoNumeroTelefono="cSecondPhoneNumber";
        public final static String parameterEmail="cEmail";
        public final static String parameterNombreEmpresa="cBusinessName";
        public final static String parameterNombreProveedor="cSupplierName";
        public final static String parameterComentario="cComment";


    }
    public static abstract class AccesUser{

        public final static String messageAccesOk="accessOk";
        public final static String messageAccesDenied="accessDenied";
     }

    public static abstract class DBSQLITE_Database{

        public final static int DATABASE_VERSION=1;
        public final static String DATABASE_NAME="MiNegocioDemo.db";
    }

     public static abstract class DBSQLITE_Usuario implements BaseColumns{

         public static final String TABLE_NAME="Usuario";
         public static final String User_Name="userName";
         public static final String User_Password="userPassword";

     }

     public static abstract class TransactionDbSqlLite{

         public static final String Create_Table_User="CREATE TABLE "
                 +DBSQLITE_Usuario.TABLE_NAME +" ("
                 +DBSQLITE_Usuario._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                 +DBSQLITE_Usuario.User_Name+" TEXT NOT NULL,"
                 +DBSQLITE_Usuario.User_Password+" TEXT NOT NULL)";

     }
     public static abstract class EstadoProducto{

         public static final String EditarProducto="EditarProducto";
         public static final String NuevoProducto="NuevoProducto";
         public static final String EstadoProducto="EstadoProducto";
     }




}
