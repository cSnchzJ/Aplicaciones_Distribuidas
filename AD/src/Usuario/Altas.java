package Usuario;
import java.util.Scanner;
import SQL.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Altas {
    public Altas() {
        // Corrección del error tipográfico: String en lugar de Sting
        String correo;
        String numero;
        String matricula;
        String password;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su correo:");
        correo = scanner.nextLine();
        System.out.println("Ingrese su numero telefonico:");
        numero = scanner.nextLine();
        System.out.println("Ingrese su matricula de su vehiculo:");
        matricula = scanner.nextLine();
        System.out.println("Ingrese su contraseña:");
        password = scanner.nextLine();
        try{
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement("INSERT INTO USUARIO (CORREO,NUMERO, MATRICULA,CONTRASEÑA) VALUES (?,?,?,?)");
            ps.setString(1, correo);
            ps.setString(2, numero);
            ps.setString(3, matricula);
            ps.setString(4, password);
            ps.executeUpdate();
        }catch(SQLException e) {
            System.out.println("Error"+e);
        }
    }
}