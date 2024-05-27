package Usuario;
import java.util.Scanner;
import SQL.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Bajas {
    public Bajas() {
        // Corrección del error tipográfico: String en lugar de Sting
        String matricula;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su matricula:");
        matricula = scanner.nextLine();
        try{
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement("DELETE FROM USUARIO WHERE MATRICULA = ?");
            ps.setString(1, matricula);
            ps.executeUpdate();
        }catch(SQLException e) {
            System.out.println("Error"+e);
        }
    }
}
