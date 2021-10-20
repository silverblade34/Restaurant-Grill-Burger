package Utils;

/**
 *
 * @author Michel
 */
import java.sql.*;
import java.sql.Connection;
import java.sql.Driver;
public class conexionDB {

     public static Connection getConexion(){
      Connection con=null;
      String cadena="jdbc:mysql://localhost/grillburger?user=root&password=";
      try{
         Class.forName("com.mysql.jdbc.Driver");
         con=DriverManager.getConnection(cadena);
         System.out.println("Conexion exitosa");
      }catch(Exception e){
         System.out.println("Error de conexion"+e);
      }
      return con;
    }
    public static void main(String[] args) {
        // TODO code application logic here
        conexionDB.getConexion();
    }
}
