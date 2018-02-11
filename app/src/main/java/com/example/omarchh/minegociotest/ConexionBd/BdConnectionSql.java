package com.example.omarchh.minegociotest.ConexionBd;

import android.os.StrictMode;

import com.example.omarchh.minegociotest.Constantes.Constantes;
import com.example.omarchh.minegociotest.Model.DetalleCuentaCorriente;
import com.example.omarchh.minegociotest.Model.ProductoEnVenta;
import com.example.omarchh.minegociotest.Model.mCabeceraPedido;
import com.example.omarchh.minegociotest.Model.mCategoriaProductos;
import com.example.omarchh.minegociotest.Model.mCierre;
import com.example.omarchh.minegociotest.Model.mCustomer;
import com.example.omarchh.minegociotest.Model.mDetalleMovCaja;
import com.example.omarchh.minegociotest.Model.mDetalleVenta;
import com.example.omarchh.minegociotest.Model.mMedioPago;
import com.example.omarchh.minegociotest.Model.mMotivo_Ingreso_Retiro;
import com.example.omarchh.minegociotest.Model.mPagosEnVenta;
import com.example.omarchh.minegociotest.Model.mProduct;
import com.example.omarchh.minegociotest.Model.mResumenFlujoCaja;
import com.example.omarchh.minegociotest.Model.mResumenMedioPago;
import com.example.omarchh.minegociotest.Model.mResumenTotalVentas;
import com.example.omarchh.minegociotest.Model.mSaldoCliente;
import com.example.omarchh.minegociotest.Model.mVendedor;
import com.example.omarchh.minegociotest.Model.mVenta;
import com.example.omarchh.minegociotest.Model.mVentasPorHora;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by OMAR CHH on 23/09/2017.
 */

public class BdConnectionSql {

    // private ResultSet rs;

    private static BdConnectionSql bdConnectionSql;

    static{
        try{

            Class.forName(Constantes.classForName.classForName).newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    int idLockProc;

    public static BdConnectionSql getSinglentonInstance() {

        if (bdConnectionSql == null) {
            bdConnectionSql = new BdConnectionSql();
        }
        return bdConnectionSql;
    }

    private static Connection getConnection(){
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn=null;
        try{
            Class.forName(Constantes.classForName.classForName).newInstance();
            conn=DriverManager.getConnection(Constantes.UrlConnection.urlConnection);

        } catch(Exception e){
            e.printStackTrace();
            conn = null;
        }
        return conn;
    }

    @Override
    protected BdConnectionSql clone() {

        try{
            throw new CloneNotSupportedException();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean VerificarConexion() {
        Connection con = getConnection();
        if (con != null) {
            return true;
        } else {

            return false;
        }


    }

    public boolean GetIdTerminal(String idDevicePhone) {

        Connection con=getConnection();
        PreparedStatement ps = null;
        final String query = "Select iId_Terminal from Terminal where cImei_Terminal=?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, idDevicePhone);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                Constantes.Terminal.idTerminal = rs.getInt(1);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }

    public boolean UpdateProduct(mProduct product) {
        boolean a = false;
        Connection con=getConnection();
        CallableStatement cs=null;

        try {
            cs = con.prepareCall("{call " + Constantes.storedProcedure.updateProduct + "(?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setString(Constantes.Parametros.parametercodigoCompania, Constantes.Empresa.CodigoEmpresa);
            cs.setInt(Constantes.Parametros.parameterIdProducto, product.getIdProduct());
            cs.setString(Constantes.Parametros.parameterCodigoProducto, product.getcKey());
            cs.setString(Constantes.Parametros.parameterNombreProducto, product.getcProductName());
            cs.setString(Constantes.Parametros.parameterUnidadProducto, product.getcUnit());
            cs.setString(Constantes.Parametros.parameterInformacionAdicionalProducto, product.getcAdditionalInformation());
            cs.setFloat(Constantes.Parametros.parameterCantidadProducto, product.getdQuantity());
            cs.setFloat(Constantes.Parametros.parameterCantidadReservaProducto, product.getdQuantityReserve());
            cs.setFloat(Constantes.Parametros.parameterPrecioCompra, product.getdPurcharsePrice());
            cs.setFloat(Constantes.Parametros.parameterPrecioVenta, product.getdSalesPrice());
            cs.setBytes(Constantes.Parametros.parameterImagen, product.getbImage());
            cs.execute();
            a = true;
        } catch (SQLException e) {
            e.printStackTrace();
            a = false;
        }
        return a;
    }

    public List<mVendedor> getVendedor(int id, String parametro, int MetodoBusqueda) {
        List<mVendedor> list = new ArrayList<>();
        mVendedor vendedor = new mVendedor();
        Connection con = getConnection();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{call " + Constantes.storedProcedure.sp_getVendedor + "(?,?,?,?) }");
            cs.setInt(Constantes.ParametrosVendedor.IdVendedor, id);
            cs.setString(Constantes.ParametrosVendedor.parametro, parametro);
            cs.setString(Constantes.Parametros.parametercodigoCompania, Constantes.Empresa.CodigoEmpresa);
            cs.setInt(Constantes.ParametrosVendedor.MetodoBusqueda, MetodoBusqueda);
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while (rs.next()) {
                vendedor = new mVendedor();
                vendedor.setIdVendedor(rs.getInt(Constantes.ParametrosVendedor.IdVendedor));
                vendedor.setPrimerNombre(rs.getString(Constantes.ParametrosVendedor.NombreUnoVendedor));
                vendedor.setApellidoPaterno(rs.getString(Constantes.ParametrosVendedor.ApellidoPaterno));
                vendedor.setApellidoMaterno(rs.getString(Constantes.ParametrosVendedor.ApellidoMaterno));
                list.add(vendedor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseConnection(con);
        }

        return list;
    }

    public List<mCustomer> getClientes(int id, String parametro, int metodoBusqueda) {

        List<mCustomer> customers = new ArrayList<>();
        mCustomer customer = new mCustomer();
        Connection con=getConnection();
        CallableStatement cs=null;


        try {
            cs = con.prepareCall("{call " + Constantes.storedProcedure.sp_getCliente + " (?,?,?,?)}");
            cs.setInt(Constantes.ParametrosCliente.idCliente, id);
            cs.setString(Constantes.Parametros.parameterBusqueda, parametro);
            cs.setString(Constantes.Parametros.parametercodigoCompania,Constantes.Empresa.CodigoEmpresa);
            cs.setInt(Constantes.ParametrosCliente.metodoBuscar, metodoBusqueda);
            cs.execute();
            ResultSet rs=cs.getResultSet();

            while (rs.next()) {
                customer = new mCustomer();
                customer.setcName(rs.getString(Constantes.ParametrosCliente.nombreUnoCliente));
                customer.setiId(rs.getInt(Constantes.ParametrosCliente.idCliente));
                customer.setcApellidoPaterno(rs.getString(Constantes.ParametrosCliente.apellidoPaterno));
                customer.setcApellidoMaterno(rs.getString(Constantes.ParametrosCliente.apellidoMaterno));

                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseConnection(con);
        }

        return customers;
    }

    public boolean ClienteInsertEdit(mCustomer customer, String metodoElegido) {
        Connection con=getConnection();
        CallableStatement cs=null;
        try {
            cs = con.prepareCall("{call " + Constantes.storedProcedure.sp_InsertarCliente + "(?,?,?,?,?,?,?,?,?) }");
            cs.setInt(Constantes.ParametrosCliente.idCliente, customer.getiId());
            cs.setString(Constantes.ParametrosCliente.nombreUnoCliente, customer.getcName());
            cs.setString(Constantes.ParametrosCliente.apellidoPaterno, customer.getcApellidoPaterno());
            cs.setString(Constantes.ParametrosCliente.apellidoMaterno, customer.getcApellidoMaterno());
            cs.setString(Constantes.ParametrosCliente.email, customer.getcEmail());
            cs.setString(Constantes.ParametrosCliente.direccion, customer.getcDireccion());
            cs.setString(Constantes.ParametrosCliente.numeroTelefono, customer.getcNumberPhone());
            cs.setString(Constantes.ParametrosCliente.metodoGuardar, metodoElegido);
            cs.setString(Constantes.Parametros.parametercodigoCompania,Constantes.Empresa.CodigoEmpresa);
            cs.execute();
            ResultSet set = cs.getResultSet();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    public boolean LoguinUsuario() {
        Connection con=getConnection();
        PreparedStatement preparedStatement = null;
        if (con != null) {
            try {
                preparedStatement = con.prepareStatement("select iIdUser,iIdCompany from Maestro_Usuarios where cNameUser='SamuelChumioque'");
                preparedStatement.execute();

                ResultSet resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {

                    Constantes.Usuario.idUsuario = resultSet.getInt("iIdUser");
                    Constantes.Empresa.idEmpresa = resultSet.getInt("iIdCompany");

                }


            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {

                if (con != null) {
                    CloseConnection(con);
                    return true;
                } else {
                    return false;
                }

            }

        } else {
            return false;
        }

    }

    public int VerificarExistePedido() {
        Connection con = getConnection();
        CallableStatement cs = null;
        int id = 0;
        int a = 1;
        try {
            cs = con.prepareCall("{call sp_get_existe_pedido_usuario_terminal(?,?,?,?)}");
            cs.setInt("iIdUser", Constantes.Usuario.idUsuario);
            cs.setInt("iIdCompany", Constantes.Empresa.idEmpresa);
            cs.setInt("iIdTienda", Constantes.Tienda.idTienda);
            cs.setInt("iIdTerminal", Constantes.Terminal.idTerminal);
            cs.execute();
            ResultSet resultSet = cs.getResultSet();
            while (resultSet.next()) {
                id = resultSet.getInt("IdCabeceraPedido");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseConnection(con);
        }

        return id;
    }

    public boolean ObtenerEstadoPedido(int id) {

        Connection con = getConnection();
        PreparedStatement ps;
        String cEstadoPedido = "";
        if (con != null) {

            try {
                ps = con.prepareStatement("select cEstadoPermanencia from Cabecera_Pedido where iId_Cabecera_Pedido=? and iIdCompany=? and iIdTienda=?");
                ps.setInt(1, id);
                ps.setInt(2, Constantes.Empresa.idEmpresa);
                ps.setInt(3, Constantes.Tienda.idTienda);
                ps.execute();

                ResultSet set = ps.getResultSet();

                while (set.next()) {
                    cEstadoPedido = set.getString(1);

                }


            } catch (SQLException e) {
                e.printStackTrace();
                cEstadoPedido = "";
            }


        }
        if (cEstadoPedido.equals("R")) {
            return true;
        } else {
            return false;
        }

    }


    public List<ProductoEnVenta> getDetallePedidoPorId(int id) {
        List<ProductoEnVenta> list = new ArrayList<>();
        Connection con = getConnection();
        PreparedStatement ps = null;

        final String query = "select iIdProducto,cProductName,iNumItem,iCantidad,dPrecioUnidad,dPrecioVentaFinal,cObservacionProducto from Detalle_Pedido  where iId_Cabecera_Pedido=?  order by iNumItem asc";

        if (con != null) {
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, id);
                ps.execute();

                ResultSet rs = ps.getResultSet();

                while (rs.next()) {

                    list.add(new ProductoEnVenta(

                            rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getFloat(4),
                            rs.getBigDecimal(5),
                            rs.getBigDecimal(6),
                            rs.getString(7)


                    ));

                }

            } catch (SQLException e) {
                e.printStackTrace();
                list.clear();
            }
        }

        return list;
    }


    public boolean GuardarDetallePedido(int id, ProductoEnVenta productoEnVenta, char metodo) {
        CallableStatement cs = null;
        Connection con = getConnection();

        if (con != null) {
            try {
                cs = con.prepareCall("call sp_guardar_producto_detalle_pedido(?,?,?,?,?,?,?,?)");
                cs.setInt("idCabeceraPedido", id);
                cs.setInt("numeroItem", productoEnVenta.getItemNum());
                cs.setInt("idProducto", productoEnVenta.getIdProducto());
                cs.setString("nombreProducto", productoEnVenta.getProductName());
                cs.setInt("cantidad", Math.round(productoEnVenta.getCantidad()));
                cs.setBigDecimal("precioUnidad", productoEnVenta.getPrecioOriginal());
                cs.setBigDecimal("subtotalUnidad", productoEnVenta.getPrecioVentaFinal());
                cs.setString("metodo", String.valueOf(metodo));
                cs.execute();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                CloseConnection(con);
            }
        }

        return false;

    }

    public int GuardarTotalPagoCabeceraPedido(int id, mCabeceraPedido cabeceraPedido) {

        int i = 0;
        int a = 0;
        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "update Cabecera_Pedido set dValorBrutoVenta=?, " +
                "dDescuentoPedido=? ,dTotalVenta=? where iId_Cabecera_Pedido=? and iIdCompany=?";

        try {
            ps = con.prepareStatement(query);
            ps.setBigDecimal(1, cabeceraPedido.getTotalBruto());
            ps.setBigDecimal(2, cabeceraPedido.getDescuentoPrecio());
            ps.setBigDecimal(3, cabeceraPedido.getTotalNeto());
            ps.setInt(4, id);
            ps.setInt(5, Constantes.Empresa.idEmpresa);
            i = ps.executeUpdate();
            a = i;
        } catch (SQLException e) {
            e.printStackTrace();
            i = 0;
        }
        return i;
    }

    public boolean GuardarCabeceraPedido(int id, mVendedor vendedor, mCustomer customer) {

        Connection con = getConnection();
        CallableStatement cs = null;

        if (con != null) {

            try {
                cs = con.prepareCall("call sp_guardar_cabecera_pedido (?,?,?,?,?,?,?,?,?,?)");
                cs.setInt("idCabeceraPedido", id);
                cs.setInt("idVendedor", vendedor.getIdVendedor());
                cs.setString("NombreVendedor", vendedor.getPrimerNombre());
                cs.setInt("idCliente", customer.getiId());
                cs.setString("NombreCliente", customer.getcName());
                cs.setInt("idTerminal", Constantes.Terminal.idTerminal);
                cs.setInt("idTienda", Constantes.Tienda.idTienda);
                cs.setInt("idCompany", Constantes.Empresa.idEmpresa);
                cs.setInt("idUser", Constantes.Usuario.idUsuario);
                cs.setInt("fecha", GenerarFecha());
                cs.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }


    public mCabeceraPedido getCabeceraPedidoPorId(int id) {
        mCabeceraPedido cabeceraPedido = null;
        Connection con = getConnection();
        PreparedStatement cs = null;

        final String query = "select cp.iId_Cabecera_Pedido,cp.cIdentificador_Pedido,cp.cObservacion,cp.iIdCliente,cp.cNombreCliente,cp.iIdVendedor,cp.cEstadoPermanencia,cp.cPrimerNombre as cNombreVendedor,dValorBrutoVenta,dDescuentoPedido,dTotalVenta," +
                "DateKey from Cabecera_Pedido as cp " +
                "where cp.iId_Cabecera_Pedido=? and cp.iIdCompany=?  and cp.iIdUser=? and cp.iIdTienda=?";
        if (con != null) {
            try {
                cs = con.prepareStatement(query);
                cs.setInt(1, id);
                cs.setInt(2, Constantes.Empresa.idEmpresa);

                cs.setInt(3, Constantes.Usuario.idUsuario);
                cs.setInt(4, Constantes.Tienda.idTienda);
                cs.getUpdateCount();
                cs.execute();

                ResultSet rs = cs.getResultSet();
                while (rs.next()) {
                    cabeceraPedido = new mCabeceraPedido(
                            rs.getInt("iId_Cabecera_Pedido"),
                            rs.getString("cIdentificador_Pedido"),
                            rs.getString("cObservacion"),
                            rs.getInt("iIdCliente"),
                            rs.getString("cNombreCliente"),
                            rs.getInt("iIdVendedor"),
                            rs.getString("cNombreVendedor"),
                            rs.getInt("DateKey"),
                            rs.getString("cEstadoPermanencia").charAt(0),
                            rs.getBigDecimal("dValorBrutoVenta"),
                            rs.getBigDecimal("dDescuentoPedido"),

                            rs.getBigDecimal("dTotalVenta")

                    );


                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    cs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                CloseConnection(con);
            }
        }
        return cabeceraPedido;
    }

    public byte SimboloMonedaPorDefecto() {

        byte respuesta = 0;
        Connection con = getConnection();
        PreparedStatement preparedStatement = null;
        final String query = "select Simbolo from Divisas where bDefault=1 and iIdCompany=? and cEstado_Divisa='A'";
        if (con != null) {
            try {


                preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, Constantes.Empresa.idEmpresa);
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    String a = resultSet.getString(Constantes.DivisaPorDefecto.parametroSimbolo);
                    Constantes.DivisaPorDefecto.SimboloDivisa = resultSet.getString(Constantes.DivisaPorDefecto.parametroSimbolo);
                }
                resultSet.close();
                respuesta = 2;
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta = 1;
            } finally {
                try {
                    preparedStatement.close();

                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();

                }

            }

        } else if (con == null) {
            respuesta = 1;
        }
        return respuesta;
    }

    public int generarNuevoPedido() {

        Connection con = getConnection();
        PreparedStatement ps = null;
        int idCabecera = 0;
        final String queryInsert = "insert into Cabecera_Pedido(iIdUser,iId_Terminal,iIdTienda,iIdCompany,DateKey) values(?,?,?,?,?) ";
        final String querySelect = "select iId_Cabecera_Pedido from Cabecera_Pedido where  iId_Terminal=? and iIdTienda=? and iIdUser=? and iIdCompany=? and cEstadoPermanencia=?";
        if (con != null) {
            try {
                ps = con.prepareStatement(queryInsert);
                ps.setInt(1, Constantes.Usuario.idUsuario);
                ps.setInt(2, Constantes.Terminal.idTerminal);
                ps.setInt(3, Constantes.Tienda.idTienda);
                ps.setInt(4, Constantes.Empresa.idEmpresa);
                ps.setInt(5, GenerarFecha());
                ps.execute();
                ps.clearParameters();
                ps = null;
                ps = con.prepareStatement(querySelect);
                ps.setInt(1, Constantes.Terminal.idTerminal);
                ps.setInt(2, Constantes.Tienda.idTienda);
                ps.setInt(3, Constantes.Usuario.idUsuario);
                ps.setInt(4, Constantes.Empresa.idEmpresa);
                ps.setString(5, "T");
                ps.execute();

                ResultSet rs = ps.getResultSet();

                while (rs.next()) {

                    idCabecera = rs.getInt(1);
                }


            } catch (SQLException e) {
                e.printStackTrace();

            } finally {
                try {
                    con.close();
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return idCabecera;
    }

    private void CloseConnection(Connection connection) {

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int GenerarFecha() {
        Date fecha = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt(dateFormat.format(fecha).toString());
    }


    public List<mProduct> getProductList(int idProducto, String parametroBusqueda, byte metodoBusqueda) {

       /*int id,String parametro,int metodoBusqueda,int PermitirImagen*/
        // metodo busqueda : Todos los productos=100  Por Id detalle=101   Por Id sin Imagen =102  Por parametro =103
        List<mProduct> list = new ArrayList<>();
        PreparedStatement ps = null;
        Connection con = getConnection();
        mProduct product;
        String cadena = "select ";
        String camposBasicos = "p.iIdProduct,p.cKey,p.cProductName,p.dQuantity,p.dSalesPrice";
        String camposDetalle = ",p.dQuantityReserve,p.dPurcharsePrice,p.cAdditionalInformation";
        String nombreTabla = " from product as p ";
        String campoImagen = ",prI.imageFile";
        String unionImagen = " inner join productImage as prI on p.iIdProduct=prI.iIdProduct ";
        String comparacionCompany = "p.iIdCompany=?";
        String busquedaParametro = " and (p.cKey like '%'+?+'%' or p.cProductName like '%'+?+'%')";
        String busquedaPorId = " and p.iIdProduct=?";

        if (metodoBusqueda == 100) {
            cadena = cadena + camposBasicos + campoImagen + nombreTabla + unionImagen + " where " + comparacionCompany;
        } else if (metodoBusqueda == 101) {
            cadena = cadena + camposBasicos + campoImagen + camposDetalle + nombreTabla + unionImagen + " where " + comparacionCompany + busquedaPorId;
        } else if (metodoBusqueda == 102) {
            cadena = cadena + camposBasicos + camposDetalle + nombreTabla + " where " + comparacionCompany + busquedaPorId;
        } else if (metodoBusqueda == 103) {
            cadena = cadena + camposBasicos + campoImagen + nombreTabla + unionImagen + " where " + comparacionCompany + busquedaParametro;

        }
        try {
            ps = con.prepareStatement(cadena);
            ps.setInt(1, Constantes.Empresa.idEmpresa);


            if (metodoBusqueda == 100) {

            } else if (metodoBusqueda == 101) {
                ps.setInt(2, idProducto);
            } else if (metodoBusqueda == 102) {
                ps.setInt(2, idProducto);
            } else if (metodoBusqueda == 103) {
                ps.setString(2, parametroBusqueda);
                ps.setString(3, parametroBusqueda);
            }


            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                product = new mProduct();
                product.setIdProduct(rs.getInt(1));
                product.setcKey(rs.getString(2));
                product.setcProductName(rs.getString(3));
                product.setdQuantity(rs.getFloat(4));
                product.setPrecioVenta(rs.getBigDecimal(5));
                if (metodoBusqueda == 100) {
                    product.setbImage(rs.getBytes(6));
                } else if (metodoBusqueda == 101) {
                    product.setbImage(rs.getBytes(6));
                    product.setdQuantityReserve(rs.getFloat(7));
                    product.setPrecioCompra(rs.getBigDecimal(8));
                    product.setcAdditionalInformation(rs.getString(9));
                } else if (metodoBusqueda == 102) {
                    product.setdQuantityReserve(rs.getFloat(6));
                    product.setPrecioCompra(rs.getBigDecimal(7));
                    product.setcAdditionalInformation(rs.getString(8));

                } else if (metodoBusqueda == 103) {

                    product.setbImage(rs.getBytes(6));

                }


                list.add(product);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return list;
    }

    public int ModificarEstadoPedido(int idCabeceraPedido, String identificador, String observacion, String estado) {

        int num = 0;
        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "update Cabecera_Pedido set DateKey=?,cEstadoPermanencia=?,cIdentificador_Pedido=?,cObservacion=? " +
                "where  iIdCompany=? and iIdTienda=? and iId_Terminal=? and iIdUser=? and iId_Cabecera_Pedido=?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, GenerarFecha());
            ps.setString(2, estado);
            ps.setString(3, identificador);
            ps.setString(4, observacion);
            ps.setInt(5, Constantes.Empresa.idEmpresa);
            ps.setInt(6, Constantes.Tienda.idTienda);
            ps.setInt(7, Constantes.Terminal.idTerminal);
            ps.setInt(8, Constantes.Usuario.idUsuario);
            ps.setInt(9, idCabeceraPedido);
            num = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            num = 0;
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return num;
    }

    public void ActualizarTerminalPedido(int idPedido) {

        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "update Cabecera_Pedido set iId_Terminal=? where iId_Cabecera_Pedido=? and iIdCompany=? and iIdTienda=?";

        if (con != null) {

            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, Constantes.Terminal.idTerminal);
                ps.setInt(2, idPedido);
                ps.setInt(3, Constantes.Empresa.idEmpresa);
                ps.setInt(4, Constantes.Tienda.idTienda);

                ps.execute();


            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    public int ModificarEstadoPedidoSuspendido(int idCabeceraPedido, String estado) {

        int num = 0;
        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "update Cabecera_Pedido set cEstadoPermanencia=? " +
                "where  iIdCompany=? and iIdTienda=? and iId_Terminal=? and iIdUser=? and iId_Cabecera_Pedido=?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, estado);
            ps.setInt(2, Constantes.Empresa.idEmpresa);
            ps.setInt(3, Constantes.Tienda.idTienda);
            ps.setInt(4, Constantes.Terminal.idTerminal);
            ps.setInt(5, Constantes.Usuario.idUsuario);
            ps.setInt(6, idCabeceraPedido);
            num = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            num = 0;
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return num;
    }

    public List<mCabeceraPedido> getListCabeceraPedidos(int fechaInicio, int fechaFinal, int idCliente) {

        List<mCabeceraPedido> list = new ArrayList<>();
        PreparedStatement ps = null;
        Connection con = getConnection();
        String query = "select iId_Cabecera_Pedido,cIdentificador_Pedido," +
                "cNombreCliente,cPrimerNombre,DateKey,dValorBrutoVenta,dDescuentoPedido,dTotalVenta " +
                "from Cabecera_Pedido where iIdCompany=? and iIdTienda=? and cEstadoPermanencia='R'  and DateKey BETWEEN ? and ? ";

        final String filtroCliente = " and iIdCliente=? ";


        if (idCliente != 0) {
            query = query + filtroCliente;
        }
        try {

            ps = con.prepareStatement(query + " order by DateKey desc");
            ps.setInt(1, Constantes.Empresa.idEmpresa);
            ps.setInt(2, Constantes.Tienda.idTienda);
            ps.setInt(3, fechaInicio);
            ps.setInt(4, fechaFinal);


            if (idCliente != 0) {
                ps.setInt(5, idCliente);
            }
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                list.add(new mCabeceraPedido(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getBigDecimal(6), rs.getBigDecimal(7), rs.getBigDecimal(8)));
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            CloseConnection(con);

        }

        return list;
    }

    public List<mMedioPago> getMPagos() {

        //Filtro 0 se usa query original -- 1 se filtra con los tipos de pago solo efectivo
        List<mMedioPago> list = new ArrayList<>();
        mMedioPago medioPago;
        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "select mp.iId_Medio_Pago,mp.cDescripcion_Medio_Pago,mp.iId_Tipo_Pago,mp.cNombre_Image,tp.b_PorCobrar from Medio_Pago as mp left join Tipo_Pago as Tp on mp.iId_Tipo_Pago=tp.iId_Tipo_Pago" +
                " where (mp.iIdCompany=0 or mp.iIdCompany=?)   and mp.cEstado_Medio_Pago=?";

        if (con != null) {
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, Constantes.Empresa.idEmpresa);
                ps.setString(2, "A");
                ps.execute();
                ResultSet rs = ps.getResultSet();

                while (rs.next()) {
                    medioPago = new mMedioPago();
                    medioPago.setiIdMedioPago(rs.getInt(1));
                    medioPago.setcDescripcionMedioPago(rs.getString(2));
                    medioPago.setiIdTipoPago(rs.getInt(3));
                    medioPago.setIdImagen(rs.getString(4));
                    medioPago.setPorCobrar(rs.getBoolean(5));
                    list.add(medioPago);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                list = null;
            }
        } else {
            list = null;
        }
        return list;
    }


    public byte IniciarVenta(int idCabeceraPedido) {
        idLockProc = 0;
        byte respuesta = 0;  // 0 sin conexion  1 error Procedimiento  2 procedimiento con exito
        PreparedStatement ps = null;
        int a;
        Connection con = getConnection();
        final String query = "insert into lock_process(iIdCabeceraPedido,cStartProcess,dateStart) values (?,?,GETUTCDATE())";
        final String proc_name = "Inicio sp_Generar_Venta";

        if (con != null) {
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, idCabeceraPedido);
                ps.setString(2, proc_name);
                a = ps.executeUpdate();
                if (a > 0) {
                    respuesta = 2;
                    ps.clearParameters();
                    ps = con.prepareStatement("select id_lock from lock_process where iIdCabeceraPedido=?");
                    ps.setInt(1, idCabeceraPedido);
                    ps.execute();

                    ResultSet set = ps.getResultSet();
                    while (set.next()) {
                        idLockProc = set.getInt(1);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                respuesta = 1;
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            respuesta = 0;
        }

        return respuesta;
    }

    private byte GenerarVenta(int idCabeceraPedido, BigDecimal cambio) {

        byte respuesta = 0;
        CallableStatement callableStatement = null;
        Connection con = getConnection();

        if (con != null) {
            try {
                callableStatement = con.prepareCall("{call sp_Generar_Venta(?,?,?,?,?,?)}");
                callableStatement.setInt(1, Constantes.Empresa.idEmpresa);
                callableStatement.setInt(2, Constantes.Terminal.idTerminal);
                callableStatement.setInt(3, Constantes.Tienda.idTienda);
                callableStatement.setInt(4, idCabeceraPedido);
                callableStatement.setInt(5, Constantes.Usuario.idUsuario);
                callableStatement.setBigDecimal(6, cambio);
                callableStatement.execute();
                respuesta = 2;

            } catch (SQLException e) {
                e.printStackTrace();
                respuesta = 1;

            } finally {
                try {
                    callableStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } else {
            respuesta = 0;
        }


        return respuesta;
    }

    private byte FinalizarVenta(int idLock) {
        byte resultado = 0;
        String lockResult = "";
        final String query = "select cEndProcess from lock_process where id_lock=?";
        PreparedStatement ps = null;
        Connection con = getConnection();
        if (con != null) {
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, idLock);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                while (rs.next()) {
                    lockResult = rs.getString(1);

                }
                if (!lockResult.equals("")) {

                    resultado = 2;
                } else {
                    resultado = 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                resultado = 1;
            } finally {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                CloseConnection(con);
            }
        } else {
            resultado = 0;
        }
        return resultado;
    }

    public byte ProcesarVenta(int idCabeceraVenta, BigDecimal cambio) {
        byte respuesta = 0;
        byte r = 0;
        byte a = 0;


        respuesta = IniciarVenta(idCabeceraVenta);
        if (respuesta == 2) {
            respuesta = GenerarVenta(idCabeceraVenta, cambio);
        }
        if (respuesta == 2) {

            respuesta = FinalizarVenta(idLockProc);
        }

        return respuesta;

    }


    public List<mVenta> getCabeceraVentaList(int FechaInicio, int FechaFinal, int idCliente) {
        List<mVenta> list = new ArrayList<>();
        mVenta venta;
        PreparedStatement ps = null;
        Connection connection = getConnection();
        String query = "select iId_Cabecera_Venta,iId_Cliente,cNombre_Cliente,iId_Vendedor,cNombre_Vendedor,cEstadoVenta,dTotal_Neto_Venta,FechaVentaRealizada-'5:00'" +
                " from Cabecera_Venta where iId_Company=? and iId_Tienda=? and cFechaEmision BETWEEN ? and ? ";

        final String filtroCliente = " and iId_Cliente=? ";
        if (idCliente != 0) {
            query = query + filtroCliente;
        }
        if (connection != null) {
            try {
                ps = connection.prepareStatement(query + " order by FechaVentaRealizada desc");
                ps.setInt(1, Constantes.Empresa.idEmpresa);
                ps.setInt(2, Constantes.Tienda.idTienda);
                ps.setInt(3, FechaInicio);
                ps.setInt(4, FechaFinal);
                if (idCliente != 0) {
                    ps.setInt(5, idCliente);
                }
                ps.execute();
                ResultSet rs = ps.getResultSet();
                while (rs.next()) {
                    venta = new mVenta();
                    venta.setIdCabeceraVenta(rs.getInt(1));
                    venta.setIdCliente(rs.getInt(2));
                    venta.setNombreCliente(rs.getString(3));
                    venta.setIdVendedor(rs.getInt(4));
                    venta.setNombreVendedor(rs.getString(5));
                    venta.setcEstadoVenta(rs.getString(6));
                    venta.setTotalNeto(rs.getBigDecimal(7));
                    venta.setFechaVentaRealizada(rs.getTimestamp(8));

                    list.add(venta);

                }

            } catch (SQLException e) {
                e.printStackTrace();
                list = null;
            }
        } else {
            list = null;
        }
        return list;
    }

    public byte getEstadoVenta(int idCabeceraVenta) {
        byte estado = 0;
        String EstadoVenta = "";
        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "select cEstadoVenta from Cabecera_Venta where iId_Cabecera_Venta=? and iId_Company=?";
        if (con != null) {
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, idCabeceraVenta);
                ps.setInt(2, Constantes.Empresa.idEmpresa);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                while (rs.next()) {
                    EstadoVenta = rs.getString(1);
                }
                if (EstadoVenta.equals("N")) {
                    estado = 2;
                } else if (EstadoVenta.equals("C")) {
                    estado = 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                estado = 0;
            }
        } else {

            estado = 0;
        }
        return estado;
    }

    public List<mDetalleVenta> getListDetalleVenta(int idCabeceraVenta) {

        PreparedStatement ps = null;
        Connection con = getConnection();
        List<mDetalleVenta> list = new ArrayList<>();
        mDetalleVenta detalleVenta = new mDetalleVenta();
        final String query = "select cProductName,iCantidad,dPrecio_Subtotal from Detalle_Venta where iIdCabecera_Venta=?";

        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, idCabeceraVenta);
            ps.execute();
            ResultSet rs = ps.getResultSet();

            while (rs.next()) {
                detalleVenta = new mDetalleVenta();

                detalleVenta.setProductName(rs.getString(1));
                detalleVenta.setCantidad(rs.getInt(2));
                detalleVenta.setPrecioSubtotal(rs.getBigDecimal(3));

                list.add(detalleVenta);

            }


        } catch (SQLException e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }

    public mVenta getCabeceraVenta(int idCabeceraVenta) {

        mVenta venta = new mVenta();

        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "select iId_Cabecera_Venta,iId_Cliente,cNombre_Cliente,iId_Vendedor,cNombre_Vendedor,dTotal_Bruto_Venta,dDescuento_Venta,dTotal_Neto_Venta,cEstadoVenta,dCambio_Venta,FechaVentaRealizada-'5:00' from Cabecera_Venta where iId_Cabecera_Venta=? and iId_Company=?";


        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, idCabeceraVenta);
            ps.setInt(2, Constantes.Empresa.idEmpresa);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {

                venta = new mVenta();
                venta.setIdCabeceraVenta(rs.getInt(1));
                venta.setIdCliente(rs.getInt(2));
                venta.setNombreCliente(rs.getString(3));
                venta.setIdVendedor(rs.getInt(4));
                venta.setNombreVendedor(rs.getString(5));
                venta.setTotalBruto(rs.getBigDecimal(6));
                venta.setDescuento(rs.getBigDecimal(7));
                venta.setTotalNeto(rs.getBigDecimal(8));
                venta.setcEstadoVenta(rs.getString(9));
                venta.setCambio(rs.getBigDecimal(10));
                venta.setFechaVentaRealizada(rs.getTimestamp(11));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            venta = null;
        }
        return venta;
    }

    public byte cancelarVenta(int idCabeceraVenta) {

        byte respuesta = 0; // 0 sin conexion 1 error  2 exito

        CallableStatement cs = null;
        Connection con = getConnection();
        final String procedure = "call sp_cancelar_venta(?,?,?,?,?)";
        if (con != null) {

            try {
                cs = con.prepareCall(procedure);
                cs.setInt(1, idCabeceraVenta);
                cs.setInt(2, Constantes.Empresa.idEmpresa);
                cs.setInt(3, Constantes.Usuario.idUsuario);
                cs.setInt(4, Constantes.Terminal.idTerminal);
                cs.registerOutParameter(5, Types.TINYINT);
                cs.execute();
                respuesta = cs.getByte(5);
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta = 1;
            }
        } else {
            respuesta = 0;
        }


        return respuesta;
    }

    public byte GuardarPagoTemporal(int idCabeceraPedido, mPagosEnVenta pagosEnVenta) {
        byte respuesta = 0;
        CallableStatement cs = null;
        Connection con = getConnection();
        final String query = "call sp_guardar_pagos_temporales(?,?,?,?,?,?)";
        if (con != null) {

            try {
                cs = con.prepareCall(query);
                cs.setInt(1, idCabeceraPedido);
                cs.setInt(2, pagosEnVenta.getIdTipoPago());
                cs.setString(3, pagosEnVenta.getcTipoPago());
                cs.setString(4, pagosEnVenta.getTipoPago());
                cs.setBigDecimal(5, pagosEnVenta.getCantidadPagada());
                cs.setInt(6, Constantes.Empresa.idEmpresa);

                cs.execute();
                respuesta = 1;
            } catch (SQLException e) {
                e.printStackTrace();
                respuesta = 0;
            }

        } else {
            respuesta = 0;
        }


        return respuesta;


    }

    public List<mPagosEnVenta> getPagosRealizados(int idCabeceraPedido) {

        List<mPagosEnVenta> list = new ArrayList<>();
        mPagosEnVenta pagosEnVenta;
        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "select p.id_Pago_Temporal,p.id_Cabecera_Pedido,p.id_Metodo_Pago,mp.cCodigo_Medio_Pago,mp.cDescripcion_Medio_Pago,p.m_Cantidad_Pagada " +
                "from Pagos_Temporales as p inner join  Medio_Pago as mp on p.id_Metodo_Pago=mp.iId_Medio_Pago where id_Cabecera_Pedido=? and id_company=? and cEstado_Pago_Temporal=?";

        final String estadoNormal = "N";
        if (con != null) {

            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, idCabeceraPedido);
                ps.setInt(2, Constantes.Empresa.idEmpresa);
                ps.setString(3, estadoNormal);
                ps.execute();

                ResultSet rs = ps.getResultSet();

                while (rs.next()) {
                    pagosEnVenta = new mPagosEnVenta();
                    pagosEnVenta.setIdTipoPago(rs.getInt(3));
                    pagosEnVenta.setcTipoPago(rs.getString(4));
                    pagosEnVenta.setTipoPago(rs.getString(5));
                    pagosEnVenta.setCantidadPagada(rs.getBigDecimal(6));
                    list.add(pagosEnVenta);

                }

            } catch (SQLException e) {
                e.printStackTrace();
                list = null;
            }

        } else {
            list = null;
        }

        return list;
    }

    public byte EliminarPagoTemporal(int idCabeceraPedido, int idMetodoPago) {

        PreparedStatement ps = null;
        Connection connection = getConnection();
        byte resultado = 0;
        final String query = "delete Pagos_Temporales where id_Cabecera_Pedido=?  and id_Metodo_Pago=? and id_company=?";
        if (connection != null) {
            try {
                ps = connection.prepareStatement(query);
                ps.setInt(1, idCabeceraPedido);
                ps.setInt(2, idMetodoPago);
                ps.setInt(3, Constantes.Empresa.idEmpresa);
                ps.executeUpdate();
                resultado = 1;
            } catch (SQLException e) {
                e.printStackTrace();
                resultado = 0;
            }
        } else {
            resultado = 0;
        }
        return resultado;
    }

    public List<mPagosEnVenta> getPagosVenta(int idCabeceraVenta) {

        List<mPagosEnVenta> list = new ArrayList<>();
        mPagosEnVenta pagosEnVenta;
        final String query = "select mp.cDescripcion_Medio_Pago,p.d_Monto_Pagado from Pagos as p inner join Medio_Pago as mp on p.id_Medio_Pago=mp.iId_Medio_Pago where id_Cabecera_Venta=?";
        PreparedStatement ps = null;
        Connection connection = getConnection();

        if (connection != null) {

            try {
                ps = connection.prepareStatement(query);
                ps.setInt(1, idCabeceraVenta);
                ps.execute();
                ResultSet resultSet = ps.getResultSet();
                while (resultSet.next()) {
                    pagosEnVenta = new mPagosEnVenta();
                    pagosEnVenta.setTipoPago(resultSet.getString(1));
                    pagosEnVenta.setCantidadPagada(resultSet.getBigDecimal(2));
                    list.add(pagosEnVenta);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                list = null;
            }
        } else {
            list = null;

        }

        return list;
    }

    public byte EliminarPagosTemporales(int idCabeceraPedido) {

        PreparedStatement ps = null;
        Connection connection = getConnection();
        byte resultado = 0;
        final String EstadoCancelado = "S";
        final String query = "update Pagos_Temporales set cEstado_Pago_Temporal=?  where id_Cabecera_Pedido=? and id_company=?";
        if (connection != null) {
            try {
                ps = connection.prepareStatement(query);
                ps.setString(1, EstadoCancelado);
                ps.setInt(2, idCabeceraPedido);
                ps.setInt(3, Constantes.Empresa.idEmpresa);
                ps.executeUpdate();
                resultado = 1;
            } catch (SQLException e) {
                e.printStackTrace();
                resultado = 0;
            }
        } else {
            resultado = 0;
        }
        return resultado;


    }

    public List<mSaldoCliente> getSaldosClientes(byte saldoCero, String nombreCliente) {

        List<mSaldoCliente> list = new ArrayList<>();
        byte usoNombre = 0;
        String query = "select sc.i_id_saldo_Cliente,sc.i_id_cliente,mc.cPrimerNombre,sc.c_observacion,sc.m_Monto from saldo_cliente as sc inner join maestroCliente as mc on sc.i_id_cliente=mc.iIdCliente where sc.i_id_company=?";
        String querySaldo = " and sc.m_Monto!=0 ";
        String queryNombre = " and mc.cPrimerNombre like '%'+?+'%'";
        PreparedStatement ps = null;
        Connection con = getConnection();

        if (saldoCero == 1) {
            query = query + querySaldo;
        }

        if (nombreCliente.length() > 2) {
            query = query + queryNombre;
            usoNombre = 1;
        }


        if (con != null) {
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, Constantes.Empresa.idEmpresa);
                if (usoNombre == 1) {
                    ps.setString(2, nombreCliente);
                }
                ps.execute();
                ResultSet rs = ps.getResultSet();
                while (rs.next()) {

                    list.add(new mSaldoCliente(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getBigDecimal(5)));

                }

            } catch (SQLException e) {
                e.printStackTrace();
                list = null;
            }
        } else {
            list = null;
        }

        return list;
    }

    public List<DetalleCuentaCorriente> getDetalleCuentaCorriente(int idCliente) {

        List<DetalleCuentaCorriente> list = new ArrayList<>();
        DetalleCuentaCorriente detalleCuentaCorriente;
        final String query = "select cta.id_CtaCte_Cliente,tp.c_Tipo_Transaccion,cta.dFecha_Creacion,cta.id_Cabecera_Venta,cta.mMonto,cta.id_Transaccion,tp.cod_Tipo_Transaccion,cta.cObservacion,mp.cDescripcion_medio_pago,cta.cod_Estado_Cta_Cte from Cuenta_Corriente_Cliente as cta inner join Tipo_Transaccion as tp on cta.cod_Tipo_Transaccion=tp.cod_Tipo_Transaccion " +
                " left join Pagos as p on cta.id_Transaccion=p.id_Pago left join Medio_Pago as mp on cta.id_Medio_Pago=mp.iId_Medio_Pago where cta.id_company=? and cta.id_cliente=?  order by cta.dFecha_Creacion desc";
        PreparedStatement ps = null;
        Connection con = getConnection();

        if (con != null) {

            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, Constantes.Empresa.idEmpresa);
                ps.setInt(2, idCliente);
                ps.execute();

                ResultSet rs = ps.getResultSet();

                while (rs.next()) {

                    detalleCuentaCorriente = new DetalleCuentaCorriente();
                    detalleCuentaCorriente.setIdCtaCteCliente(rs.getInt(1));
                    detalleCuentaCorriente.setTipoTransaccion(rs.getString(2));
                    detalleCuentaCorriente.setFechaOrigen(rs.getTimestamp(3));
                    detalleCuentaCorriente.setIdCabeceraVenta(rs.getInt(4));
                    detalleCuentaCorriente.setMonto(rs.getBigDecimal(5));
                    detalleCuentaCorriente.setIdTransaccion(rs.getInt(6));
                    detalleCuentaCorriente.setIdTipoTransaccion(rs.getString(7));
                    detalleCuentaCorriente.setcObservacion(rs.getString(8));
                    detalleCuentaCorriente.setMetodoPago(rs.getString(9));
                    detalleCuentaCorriente.setEstadoCtaCte(rs.getString(10));
                    list.add(detalleCuentaCorriente);


                }


            } catch (SQLException e) {
                e.printStackTrace();
                list = null;
            }

        } else {

            list = null;

        }

        return list;

    }

    public byte VerificarTerminalImei(String codImei) {
        byte estado = 100;
        CallableStatement cs = null;
        Connection connection = getConnection();
        final String query = "call sp_ValidarIngresoTerminal(?,?)";

        if (connection != null) {

            try {
                cs = connection.prepareCall(query);
                cs.setString(1, codImei);
                cs.registerOutParameter(2, Types.TINYINT);
                cs.execute();

                estado = cs.getByte(2);


            } catch (SQLException e) {
                e.printStackTrace();
                estado = 100;
            }

        } else {
            estado = 100;
        }

        return estado;
    }


    public byte ProcesarPagoCtaCte(BigDecimal monto, int idMetodoPago, String cObservacion, int idCliente) {

        byte resultado = 0;
        byte c = 0;
        //resultado 101 pago exitoso   100 pago error  99 medio pago Eliminado 0 sin conexion
        CallableStatement cs = null;

        Connection con = getConnection();
        final String query = "call sp_procesarPagoCtaCte(?,?,?,?,?,?,?,?,?)";
        if (con != null) {
            try {
                cs = con.prepareCall(query);
                cs.setInt(1, idMetodoPago);
                cs.setBigDecimal(2, monto);
                cs.setInt(3, idCliente);
                cs.setInt(4, Constantes.Empresa.idEmpresa);
                cs.setInt(5, Constantes.Tienda.idTienda);
                cs.setInt(6, Constantes.Usuario.idUsuario);
                cs.setInt(7, Constantes.Terminal.idTerminal);
                cs.setString(8, cObservacion);
                cs.registerOutParameter(9, Types.TINYINT);
                cs.execute();
                resultado = cs.getByte(9);
                c = resultado;

            } catch (SQLException e) {
                e.printStackTrace();
                resultado = 100;
            }
        } else {
            resultado = 0;
        }


        return resultado;
    }

    public byte CancelarPagoCtaCte(int idCtaCte, int idCliente) {
        byte resultado = 100;
        CallableStatement cs = null;
        Connection con = getConnection();
        final String query = "call sp_cancelarPagoCtaCte(?,?,?,?,?,?,?)";
        if (con != null) {
            try {
                cs = con.prepareCall(query);
                cs.setInt(1, idCtaCte);
                cs.setInt(2, Constantes.Usuario.idUsuario);
                cs.setInt(3, Constantes.Empresa.idEmpresa);
                cs.setInt(4, Constantes.Tienda.idTienda);
                cs.setInt(5, Constantes.Terminal.idTerminal);
                cs.setInt(6, idCliente);
                cs.registerOutParameter(7, Types.TINYINT);

                cs.execute();

                resultado = cs.getByte(7);


            } catch (SQLException e) {
                e.printStackTrace();
                resultado = 100;
            }
        } else {

            resultado = 99;

        }

        return resultado;
    }

    public mCustomer getClienteId(int idcliente) {

        mCustomer customer = new mCustomer();

        PreparedStatement ps = null;
        Connection connection = getConnection();
        final String query = "select cPrimerNombre,cApellidoPaterno,cEmail from maestroCliente where iIdCliente=? and iIdCompany=?";

        if (connection != null) {

            try {
                ps = connection.prepareStatement(query);
                ps.setInt(1, idcliente);
                ps.setInt(2, Constantes.Empresa.idEmpresa);
                ps.execute();

                ResultSet rs = ps.getResultSet();

                while (rs.next()) {

                    customer.setcName(rs.getString(1));
                    customer.setcApellidoPaterno(rs.getString(2));
                    customer.setcEmail(rs.getString(3));


                }

            } catch (SQLException e) {
                e.printStackTrace();
                customer = null;
            }


        } else {
            customer = null;
        }

        return customer;

    }

    public String getSaldoCliente(int idCliente) {

        String saldo = " ";
        PreparedStatement ps = null;
        Connection con = getConnection();

        if (con != null) {

            try {
                ps = con.prepareStatement("select m_monto from saldo_Cliente where i_id_cliente=? and i_id_company=?");
                ps.setInt(1, idCliente);
                ps.setInt(2, Constantes.Empresa.idEmpresa);
                ps.execute();

                ResultSet rs = ps.getResultSet();

                while (rs.next()) {
                    saldo = Constantes.DivisaPorDefecto.SimboloDivisa + String.format("%.2f", rs.getBigDecimal(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                saldo = null;
            }
        } else {
            saldo = null;
        }

        return saldo;

    }

    public List<mCategoriaProductos> getCategorias(int idCategoria, String nombreCategoria) {

        List<mCategoriaProductos> list = new ArrayList<>();

        final String query = "select id_categoria_producto,cDescripcion_categoria from categoria_productos where (id_company=? or id_company=?) and cEstado_Categoria=?";
        PreparedStatement ps = null;
        Connection con = getConnection();


        if (con != null) {

            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, 0);
                ps.setInt(2, Constantes.Empresa.idEmpresa);
                ps.setString(3, "A");

                ps.execute();

                ResultSet rs = ps.getResultSet();

                while (rs.next()) {
                    list.add(new mCategoriaProductos(rs.getInt(1), rs.getString(2)));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                list = null;
            }

        } else {

            list = null;
        }

        return list;

    }


    public byte VerificarAperturaCaja() {

        byte estado = 0;
        //Consultar si la caja esta aperturada
        //2 si la caja esta abierta
        //1 no encuentra caja abierta
        //0 sin conexion

        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "select id_cierre from cierres where id_Company=? and id_Tienda=? and cEstado_Cierre=? and cEliminado!=?";
        final String estadoEliminado = "E";
        final String estadoActivo = "A";
        if (con != null) {

            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, Constantes.Empresa.idEmpresa);
                ps.setInt(2, Constantes.Tienda.idTienda);
                ps.setString(3, estadoActivo);
                ps.setString(4, estadoEliminado);
                ps.execute();

                ResultSet rs = ps.getResultSet();

                if (!rs.next()) {

                    estado = 10;
                } else {
                    do {

                    } while (rs.next());
                    estado = 20;
                }


            } catch (SQLException e) {
                e.printStackTrace();
                estado = 0;

            }


        } else {

            estado = 0;
        }
        return estado;

    }


    public byte aperturarCaja(BigDecimal monto) {

        byte resultado = 0;
        byte a = 0;

        final String query = "call sp_generarAperturaCierre(?,?,?,?,?,?)";
        CallableStatement cs = null;
        Connection con = getConnection();

        if (con != null) {

            try {
                cs = con.prepareCall(query);
                cs.setInt(1, Constantes.Empresa.idEmpresa);
                cs.setInt(2, Constantes.Tienda.idTienda);
                cs.setInt(3, Constantes.Usuario.idUsuario);
                cs.setInt(4, Constantes.Terminal.idTerminal);
                cs.setBigDecimal(5, monto);
                cs.setByte(6, (byte) 99);
                cs.registerOutParameter(6, Types.TINYINT);
                cs.execute();
                resultado = cs.getByte(6);
                resultado = resultado;


            } catch (SQLException e) {
                e.printStackTrace();
                resultado = 99;
            }

        } else {

            resultado = 0;

        }

        return resultado;

    }

    public List<mResumenFlujoCaja> getResumenFlujoCaja(int idCierreCaja) {

        List<mResumenFlujoCaja> list = new ArrayList<>();
        mResumenFlujoCaja resumenFlujoCaja;
        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "SELECT  dbo.VistaFlujoMedioPago.cdescripcion, dbo.Flujo_Caja.mMonto, dbo.VistaFlujoMedioPago.titulo, " +
                "                         dbo.Titulo_Pago_Caja.cTitulo_Pago, dbo.VistaFlujoMedioPago.color, dbo.VistaFlujoMedioPago.[Subtitulo caja], dbo.VistaFlujoMedioPago.cSimbolo " +
                "FROM dbo.Flujo_Caja INNER JOIN " +
                " dbo.VistaFlujoMedioPago ON dbo.Flujo_Caja.cTipo_Registro = dbo.VistaFlujoMedioPago.tipo_registro AND dbo.Flujo_Caja.id_concepto_caja = dbo.VistaFlujoMedioPago.[codigo Flujo] INNER JOIN " +
                " dbo.Titulo_Pago_Caja ON dbo.VistaFlujoMedioPago.titulo = dbo.Titulo_Pago_Caja.id_titulo where id_cierre=?" +
                "ORDER BY dbo.VistaFlujoMedioPago.titulo, dbo.VistaFlujoMedioPago.orden";
        if (con != null) {
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, idCierreCaja);
                ps.execute();
                ResultSet resultSet = ps.getResultSet();
                while (resultSet.next()) {
                    resumenFlujoCaja = new mResumenFlujoCaja();

                    resumenFlujoCaja.setDescripcionTitulo(resultSet.getString(1));
                    resumenFlujoCaja.setMonto(resultSet.getBigDecimal(2));
                    resumenFlujoCaja.setCodtitulo(resultSet.getString(3));
                    resumenFlujoCaja.setTitutloPago(resultSet.getString(4));
                    resumenFlujoCaja.setCodColor(resultSet.getString(5));
                    resumenFlujoCaja.setSubtituloCaja(resultSet.getString(6));
                    resumenFlujoCaja.setSimbolo(resultSet.getString(7));
                    list.add(resumenFlujoCaja);
                }

                list.size();
                list.size();
            } catch (SQLException e) {
                e.printStackTrace();
                list = null;
            }
        } else {
            list = null;
        }

        return list;
    }

    public mCierre getCabeceraCierreCaja(int idCierre) {
        mCierre cierre = new mCierre();
        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "select id_cierre,cEstado_cierre,dFecha_Apertura-'5:00',dFecha_Cierre-'5:00' from Cierres where ";
        String filtro = "";
        if (con != null) {

            try {
                if (idCierre == 0) {
                    filtro = "id_company=? and id_tienda=? and cEstado_Cierre='A'";
                } else if (idCierre != 0) {
                    filtro = "id_cierre=?";
                }
                ps = con.prepareStatement(query + filtro);
                if (idCierre == 0) {
                    ps.setInt(1, Constantes.Empresa.idEmpresa);
                    ps.setInt(2, Constantes.Tienda.idTienda);
                } else if (idCierre != 0) {
                    ps.setInt(1, idCierre);
                }
                ps.execute();

                ResultSet rs = ps.getResultSet();

                while (rs.next()) {

                    cierre.setIdCierre(rs.getInt(1));
                    cierre.setEstadoCierre(rs.getString(2));
                    cierre.setFechaApertura(rs.getTimestamp(3));
                    cierre.setFechaCierre(rs.getTimestamp(4));

                }


            } catch (SQLException e) {
                e.printStackTrace();
                cierre = null;
            }

        } else {
            cierre = null;
        }

        return cierre;
    }

    public List<mCierre> getCierresHistorial(String fechaInicio, String fechaFinal) {

        PreparedStatement ps = null;
        Connection connection = getConnection();
        List<mCierre> list = new ArrayList<>();
        mCierre cierre;
        final String query = "select id_cierre,dFecha_apertura-'5:00',dFecha_cierre-'5:00',cEstado_cierre from Cierres where  cEliminado!='E' and id_company=? and  id_tienda=? and  dFecha_apertura between cast(? as datetime)-'5:00' and cast(? as datetime) order by dFecha_apertura desc ";


        if (connection != null) {

            try {
                ps = connection.prepareStatement(query);
                ps.setInt(1, Constantes.Empresa.idEmpresa);
                ps.setInt(2, Constantes.Tienda.idTienda);
                ps.setString(3, fechaInicio);
                ps.setString(4, fechaFinal + " 23:59:59");
                ps.execute();
                ResultSet rs = ps.getResultSet();
                while (rs.next()) {

                    cierre = new mCierre();
                    cierre.setIdCierre(rs.getInt(1));
                    cierre.setFechaApertura(rs.getTimestamp(2));
                    cierre.setFechaCierre(rs.getTimestamp(3));
                    cierre.setEstadoCierre(rs.getString(4));

                    list.add(cierre);

                }

            } catch (SQLException e) {
                e.printStackTrace();
                list = null;
            }

        } else {
            list = null;

        }
        return list;
    }

    public List<mMedioPago> getMedioPagoEfectivo() {

        PreparedStatement ps = null;
        Connection con = getConnection();
        List<mMedioPago> list = new ArrayList<>();
        mMedioPago medioPago;
        final String query = "select iId_Medio_Pago,cDescripcion_Medio_Pago from Medio_Pago where (iIdCompany=0 or iIdCompany=?) and iId_Tipo_Pago=100";
        if (con != null) {

            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, Constantes.Empresa.idEmpresa);
                ps.execute();

                ResultSet rs = ps.getResultSet();
                while (rs.next()) {

                    medioPago = new mMedioPago();
                    medioPago.setiIdMedioPago(rs.getInt(1));
                    medioPago.setcDescripcionMedioPago(rs.getString(2));
                    list.add(medioPago);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


        } else {

            list = null;

        }

        return list;
    }

    public List<mMotivo_Ingreso_Retiro> getMotivoIngresoRetiro(String tipo) {

        //tipo= 1 ingreso  2 retiro
        byte tipoRegistro = 0;

        if (tipo.equals("Entrada")) {
            tipoRegistro = 1;
        } else if (tipo.equals("Retiro")) {
            tipoRegistro = 2;
        }

        List<mMotivo_Ingreso_Retiro> list = new ArrayList<>();
        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "select id_motivo,cDescripcion_Motivo from Motivo_Ingreso_Retiro where iTipo_Registro=?";


        if (con != null) {

            try {
                ps = con.prepareStatement(query);
                ps.setByte(1, tipoRegistro);
                ps.execute();

                ResultSet rs = ps.getResultSet();

                while (rs.next()) {

                    list.add(new mMotivo_Ingreso_Retiro(rs.getInt(1), rs.getString(2)));

                }

            } catch (SQLException e) {
                e.printStackTrace();
                list = null;
            }

        } else {

            list = null;
        }
        list.size();
        return list;
    }

    public List<mResumenMedioPago> getResumenMP(int idCierre) {

        List<mResumenMedioPago> list = new ArrayList<>();

        PreparedStatement ps = null;
        Connection con = getConnection();

        final String query = "select mp.iId_Medio_Pago,mp.cDescripcion_Medio_Pago ,rm.mMonto_Entrada,mMonto_Salida,mMonto_Saldo_Disponible from Resumen_Medio_Pago_Cierre as rm inner join Medio_Pago as mp on rm.id_Medio_Pago=mp.iId_Medio_Pago where id_cierre=?";
        if (con != null) {
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, idCierre);
                ps.execute();

                ResultSet rs = ps.getResultSet();
                while (rs.next()) {

                    list.add(new mResumenMedioPago(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3), rs.getBigDecimal(4), rs.getBigDecimal(5)));

                }
            } catch (SQLException e) {
                e.printStackTrace();
                list = null;
            }
        } else {

            list = null;
        }


        return list;
    }

    public List<mDetalleMovCaja> getMovimientoCaja(int idCierre) {

        //Obtener los movimientos de la caja segun el cierre requerido
        List<mDetalleMovCaja> list = new ArrayList<>();
        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "select mc.iTipo_registro,mr.cDescripcion_Motivo,mp.cDescripcion_Medio_Pago,mc.cDescipcion_movimiento,mc.dFecha_Movimiento-'5:00',mc.mMonto from Movimiento_Caja mc inner join Medio_Pago as mp on mc.id_medio_pago=mp.iId_Medio_Pago inner join Motivo_Ingreso_Retiro as mr on mc.id_motivo=mr.id_motivo where mc.id_cierre_caja=?";

        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, idCierre);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            int count = 0;
            while (rs.next()) {

                list.add(new mDetalleMovCaja());
                list.get(count).setTipoRegistro(rs.getByte(1));
                list.get(count).setDescripcionMotivo(rs.getString(2));
                list.get(count).setNombreMedioPago(rs.getString(3));
                list.get(count).setDescripcion(rs.getString(4));
                list.get(count).setFechaTransaccion(rs.getTimestamp(5));
                list.get(count).setMonto(rs.getBigDecimal(6));
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<mVentasPorHora> getVentasPorHora(int idCierre) {

        List<mVentasPorHora> list = new ArrayList<>();
        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "select [iHora],[mMonto],[iNum_Ventas] from Ventas_Cierre_Hora where id_cierre=? and mMonto!=0";
        if (con != null)
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, idCierre);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                while (rs.next()) {

                    list.add(new mVentasPorHora(rs.getInt(1), rs.getBigDecimal(2)));
                }
                list.size();
            } catch (SQLException e) {
                e.printStackTrace();
                list = new ArrayList<>();
            }
        return list;
    }

    public mResumenTotalVentas ObtenerCabeceraResumen(int idCierre) {

        mResumenTotalVentas resumenTotalVentas = new mResumenTotalVentas();
        CallableStatement cs = null;
        Connection con = getConnection();
        final String procedure = "call sp_actualizar_total_ventas(?,?,?,?)";
        if (con != null) {
            try {
                cs = con.prepareCall(procedure);
                cs.setInt(1, idCierre);
                cs.setBigDecimal(2, new BigDecimal(0));
                cs.setInt(3, 0);
                cs.setBigDecimal(4, new BigDecimal(0));
                cs.registerOutParameter(2, Types.DECIMAL);
                cs.registerOutParameter(3, Types.INTEGER);
                cs.registerOutParameter(4, Types.DECIMAL);

                cs.execute();
                resumenTotalVentas = new mResumenTotalVentas(cs.getInt(3), cs.getBigDecimal(2), cs.getBigDecimal(4));
                resumenTotalVentas.getNum_Ventas();
            } catch (SQLException e) {
                e.printStackTrace();
                resumenTotalVentas = null;
            }


        } else {
            resumenTotalVentas = null;
        }


        return resumenTotalVentas;
    }

    public byte AgregarIngresoRetiro(byte tipoRegistro, BigDecimal monto, int idMotivo, int idMedioPago, String descripcion, int idCierre) {
        //tipo Registro 1 ingreso. 2 Retiro
        //Validacion de Ingreso de retiro  en base de datos
        //Registro de movimientos de caja

        byte respuesta = 0;
        CallableStatement cs = null;
        Connection con = getConnection();
        final String query = "call sp_AgregarIngresoRetiro(?,?,?,?,?,?,?,?,?,?)";
        if (con != null) {

            try {
                cs = con.prepareCall(query);
                cs.setByte(1, tipoRegistro);
                cs.setBigDecimal(2, monto);
                cs.setInt(3, idCierre);
                cs.setInt(4, Constantes.Empresa.idEmpresa);
                cs.setInt(5, Constantes.Usuario.idUsuario);
                cs.setInt(6, Constantes.Terminal.idTerminal);
                cs.setInt(7, idMedioPago);
                cs.setInt(8, idMotivo);
                cs.setString(9, descripcion);
                cs.setByte(10, (byte) 0);
                cs.registerOutParameter(10, Types.TINYINT);

                cs.execute();

                respuesta = cs.getByte(10); //1 se realizo la transaccion// 0 fallo la conexion o error al ejecutar// 2 se verifico que el monto ingresado es mayor al disponible


            } catch (SQLException e) {
                e.printStackTrace();
                respuesta = 0;
            }

        } else {
            respuesta = 0;
        }
        return respuesta;
    }

    public mCierre ObtenerIdCierre() {

        mCierre cierre = new mCierre();
        int id = 0;
        int c = 2;
        PreparedStatement ps = null;
        Connection con = getConnection();
        final String query = "select id_cierre,cEstado_Cierre_Caja from cierres_caja where id_tienda=?";
        if (con != null) {
            try {
                ps = con.prepareStatement(query);
                ps.setInt(1, Constantes.Tienda.idTienda);
                ps.execute();
                ResultSet rs = ps.getResultSet();
                while (rs.next()) {
                    cierre.setIdCierre(rs.getInt(1));
                    cierre.setEstadoCierre(rs.getString(2));

                }

            } catch (SQLException e) {
                e.printStackTrace();
                cierre.setIdCierre(-1);
            }
        } else {

            cierre.setIdCierre(-2);

        }

        return cierre;
    }

    public byte CerrarCaja(int idCierre) {
        byte Respuesta = 0;
        CallableStatement cs = null;
        Connection con = getConnection();
        final String procedure = "call sp_cerrar_caja(?,?,?,?)";
        if (con != null) {
            try {
                cs = con.prepareCall(procedure);
                cs.setInt(1, Constantes.Usuario.idUsuario);
                cs.setInt(2, Constantes.Terminal.idTerminal);
                cs.setInt(3, idCierre);
                cs.setByte(4, (byte) 0);
                cs.registerOutParameter(4, Types.TINYINT);
                cs.execute();

                Respuesta = cs.getByte(4);


            } catch (SQLException e) {
                e.printStackTrace();
                Respuesta = 1;
            }
        } else {
            Respuesta = 0;
        }
        return Respuesta;
    }

}










