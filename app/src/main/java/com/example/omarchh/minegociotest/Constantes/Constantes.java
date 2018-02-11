package com.example.omarchh.minegociotest.Constantes;

import android.provider.BaseColumns;

/**
 * Created by OMAR CHH on 23/09/2017.
 */

public abstract class  Constantes {



    public static abstract class Empresa{

        public final static String CodigoEmpresa="C0001";
        public static int idEmpresa;
    }

    public static abstract class Usuario {

        public static int idUsuario;
    }

    public static abstract class Tienda {

        public final static int idTienda = 1;
    }

    public static abstract class Terminal {
        public static int idTerminal;
    }

    public static abstract class DivisaPorDefecto {

        public static final String parametroSimbolo = "simbolo";
        public static String SimboloDivisa;
    }
    private static abstract class bdConstantes{

        private final static String url="192.168.8.111:1433;instance=OMARCHPC";
        private final static String db="DB_A2B05D_bdNegocio";
        private final static String user="prueba";
        private final static String password="prueba";

    }


    /*    Local
    private final static String url="192.168.8.111:1433;instance=OMARCHPC";
    private final static String db="DB_A2B05D_bdNegocio";
    private final static String user="prueba";
    private final static String password="prueba";

    */

    //SmarAsp
    /*

      private final static String url="SQL5037.site4now.net";
        private final static String db="DB_A33BA8_omar140992";
        private final static String user="DB_A33BA8_omar140992_admin";
        private final static String password="1409Chumioque";

     */


    public static abstract class UrlConnection{
       public final static String urlConnection="jdbc:jtds:sqlserver://"+bdConstantes.url+";databaseName="+bdConstantes.db+";user="+bdConstantes.user+";password="+bdConstantes.password+";"; }


    public static abstract class classForName{
        public final static String classForName="net.sourceforge.jtds.jdbc.Driver";
    }

    public static abstract class ParametrosCliente {
        public final static String metodoGuardar = "cMetodoRealizar";
        public final static String metodoBuscar = "MetodoBusqueda";
        public final static int BusquedaPorId = 100;
        public final static int BusquedaPorNombreApellido = 101;
        public final static int TodosLosClientes = 102;
        public final static String idCliente = "iIdCliente";
        public final static String nuevoCliente = "sp_NuevoCliente";
        public final static String editarCliente = "sp_EditarCliente";
        public final static String nombreUnoCliente = "cPrimerNombre";

        public final static String apellidoPaterno = "cApellidoPaterno";
        public final static String apellidoMaterno = "cApellidoMaterno";
        public final static String numeroTelefono = "cNumeroTelefono";
        public final static String email = "cEmail";
        public final static String direccion = "cDireccion";
    }

    public static abstract class ParametrosVendedor {

        public final static String IdVendedor = "iIdVendedor";
        public final static String NombreUnoVendedor = "cPrimerNombre";
        public final static String NombreDosVendedor = "cSegundoNombre";
        public final static String ApellidoPaterno = "cApellidoPaterno";
        public final static String ApellidoMaterno = "cApellidoMaterno";
        public final static String Comision = "dComision";
        public final static String MetodoBusqueda = "MetodoBusqueda";
        public final static String parametro = "ParametroBusqueda";
        public final static int BusquedaPorId = 107;
        public final static int BusquedaPorNombre = 109;
        public final static int TodosVendedores = 105;

    }

    public static abstract class storedProcedure {

        public final static String sp_getCliente = "sp_buscarCliente";
        public final static String sp_InsertarCliente = "sp_ingresarEditarCliente";
        public final static String sp_getVendedor = "sp_BuscarVendedor";

        public final static String insertProduct="sp_insertProduct";
        public final static String insertImageProduct = "sp_IngresarImagen";
        public final static String checkloginUser="sp_checkLoginUser";
        public final static String updateProduct="sp_editProduct";

    }
    public static abstract class SimboloMoneda{

        public final static String moneda="S/";

    }

    public static abstract class Parametros {

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
         public static final String TABLE_DEVICE_BLUETOOTH = "DeviceAddress";
         public static final String TABLE_OPTION_PRINT = "PrintDefault";
     }

     public static abstract class TransactionDbSqlLite{

         public static final String Create_Table_User="CREATE TABLE "
                 +DBSQLITE_Usuario.TABLE_NAME +" ("
                 +DBSQLITE_Usuario._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                 +DBSQLITE_Usuario.User_Name+" TEXT NOT NULL,"
                 +DBSQLITE_Usuario.User_Password+" TEXT NOT NULL)";

         public static final String Create_Table_Device = "CREATE TABLE DeviceAddress ( cDeviceAddress TEXT NOT NULL UNIQUE)";

         public static final String Create_Table_Print_Default = "Create Table PrintDefault (cNamePrint TEXT NOT NULL UNIQUE)";
     }
     public static abstract class EstadoProducto{

         public static final String EditarProducto="EditarProducto";
         public static final String NuevoProducto="NuevoProducto";
         public static final String EstadoProducto="EstadoProducto";
     }



}
