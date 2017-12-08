
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class JDBCApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;
        try {

            String dburl = "jdbc:derby://localhost:1527/sample";
            String username = "app";
            String password = "app";

            Class.forName("org.apache.derby.jdbc.ClientDriver"); // kalo kelas nya ga ketemu, no sql exeption, koneksi, internet, table

            conn = DriverManager.getConnection(dburl, username, password);
            stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("Select * from customer");
            while (result.next()) {
                
                String name = result.getString("name");
    
                String alamat = result.getString("addressline1")+", "+result.getString("addressline2");
                
                
                System.out.println(name + ", " + alamat);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCApplication.class.getName()).log(Level.SEVERE, "Drive tidak ketemu", ex);

        } catch (SQLException se) {
            Logger.getLogger(JDBCApplication.class.getName()).log(Level.SEVERE, "Masalah SQL", se);
        } finally {
            try {
                stmt.close();
                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(JDBCApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

// TODO code application logic here
}
