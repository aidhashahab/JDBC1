
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCApplication_3 {

    public static void main(String[] args) {

        Connection conn = null; //untuk mengkoneksikan database dengan java
        Statement stmt = null;  //untuk membuat statement yg isinya bisa di panggil dr database

        try {

            String dburl = "jdbc:derby://localhost:1527/sample";
            String username = "app";
            String password = "app";

            Class.forName("org.apache.derby.jdbc.ClientDriver");

            conn = DriverManager.getConnection(dburl, username, password);
            stmt = conn.createStatement();
            String sqlDB = "select p.description as DESCRIPTION, po.quantity as QTY, p.purchase_cost as PURCHASE\n"
                    + "from purchase_order po\n"
                    + "join product p on p.PRODUCT_ID=po.PRODUCT_ID";
            ResultSet result = stmt.executeQuery(sqlDB);
            double totalOrder = 0.0;

//            System.out.println(" ==================================================================================");
//            System.out.println("|\tDESCRIPTION\t|\tQTY\t|\tTOTAL SEMUA\t");
//            System.out.println(" =================================================================================");
            while (result.next()) {
                String produk = result.getString("DESCRIPTION");
                int qty = result.getInt("QTY");
                double cost = result.getDouble("PURCHASE");
                double linecost = cost * qty;

                System.out.printf("%s\t %d\t %.2f\t %.2f\t", produk, qty, cost, linecost);
                System.out.println("");
                totalOrder += linecost;
                System.out.println("\t\t\t Total = " + totalOrder);
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

}
