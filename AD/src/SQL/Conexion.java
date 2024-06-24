/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

/**
 *
 * @author alang
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public static Connection getConexion(){
        Connection conn=null;
        String host = "localhost";
        String port = "3306";
        String dbName = "as";
        String username = "root";
        String password = "123";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://"+host+":"+port+"/"+dbName;
            conn = DriverManager.getConnection(url,username,password);
            System.out.println("Conexion exitosa");
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error"+e);
        }
        return conn;
    }
}