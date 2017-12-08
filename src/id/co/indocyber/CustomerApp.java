/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.indocyber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class CustomerApp extends javax.swing.JFrame {

    Connection connection = null;

    //Statement st = null;
    /**
     * Creates new form CustomerApp
     */
    public CustomerApp() {
        initComponents();
        this.connection = connectToDatabase();
        showCustomer();
    }

    private Connection connectToDatabase() {
        Connection conn = null;
        try {
            String url = "jdbc:derby://localhost:1527/sample";
            String username = "app";
            String password = "app";

            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerApp.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(CustomerApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    private List<Customer> getCustomers() throws SQLException {
        Statement st = connection.createStatement();
        ResultSet results = st.executeQuery("SELECT * FROM CUSTOMER");
        List<Customer> customers = new ArrayList<>();
        while (results.next()) {
            Customer c = new Customer();
            c.setName(results.getString("name"));
            c.setAddressline1(results.getString("addressline1"));
            c.setAddressline2(results.getString("addressline2"));
            c.setCity(results.getString("city"));
            c.setCustomerId(results.getInt("customer_id"));
            c.setDiscountCode(results.getString("discount_code"));
            c.setCreditLimit(results.getDouble("credit_limit"));
            c.setEmail(results.getString("email"));
            c.setFax(results.getString("fax"));
            c.setPhone(results.getString("phone"));
            c.setState(results.getString("state"));
            c.setZip(results.getString("zip"));
            customers.add(c);
        }
        return customers;
    }

    private void showCustomer() {
        DefaultTableModel tableModel = (DefaultTableModel) customerTable.getModel();
        try {
            List<Customer> customers = getCustomers();
            Object[] row = new Object[12];
            for (Customer customer : customers) {

                row[0] = customer.getCustomerId();
                row[1] = customer.getName();
                row[2] = customer.getAddressline1();
                row[3] = customer.getAddressline2();
                row[4] = customer.getCity();
                row[5] = customer.getState();
                row[6] = customer.getEmail();
                row[7] = customer.getPhone();
                row[8] = customer.getFax();
                row[9] = customer.getZip();
                row[10] = customer.getDiscountCode();
                row[11] = customer.getCreditLimit();

                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        ListSelectionModel rowSelMod = customerTable.getSelectionModel();
        rowSelMod.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int i = customerTable.getSelectedRow();
                    //String nama = (String) tableModel.getValueAt(i, 0);                    
                    int customerId = (int) tableModel.getValueAt(i, 0);
                    Customer customer = findCustomer(customerId);
                    namaTextField.setText(customer.getName());
                    addressTextField.setText(customer.getAddressline1() + "\n" + customer.getAddressline2());
                    cityTextField.setText(customer.getCity());
                    phoneTextField.setText(customer.getPhone());
                    emailTextField.setText(customer.getEmail());
                    creditLimitTextField.setText(String.valueOf(customer.getCreditLimit()));
                    // MENAMPILKAN ORDER CUSTOMER PADA TABLE 
                    List<Order> orders = findOrders(customerId);
                    DefaultTableModel orderTableModel = (DefaultTableModel) orderTable.getModel();
                    orderTableModel.setRowCount(0);
                    Object[] orderRow = new Object[4];
                    double totalOrder = 0.0;
                    for (Order order : orders) {
                        orderRow[0] = order.getProdukDescription();
                        orderRow[1] = order.getQuantity();
                        orderRow[2] = order.getCost();
                        orderRow[3] = order.getTotalCost();
                        orderTableModel.addRow(orderRow);

                    }
                    

                }
            }
        });
    }

    private Customer findCustomer(int customerId) {
        System.out.println("Customer ID " + customerId);
        Customer c = new Customer();
        try {
            PreparedStatement pstm
                    = connection.prepareStatement("SELECT * FROM CUSTOMER WHERE CUSTOMER_ID=?");
            pstm.setInt(1, customerId);
            ResultSet results = pstm.executeQuery();
            while (results.next()) {
                c.setCustomerId(results.getInt("customer_id"));
                c.setName(results.getString("name"));
                c.setAddressline1(results.getString("addressline1"));
                c.setAddressline2(results.getString("addressline2"));
                c.setCity(results.getString("city"));
                c.setState(results.getString("state"));
                c.setEmail(results.getString("email"));
                c.setPhone(results.getString("phone"));
                c.setFax(results.getString("fax"));
                c.setZip(results.getString("zip"));
                c.setDiscountCode(results.getString("discount_code"));
                c.setCreditLimit(results.getDouble("credit_limit"));

            }
            namaTextField.setText(c.getName());
            addressTextField.setText(c.getAddressline1() + "\n" + c.getAddressline2());
            cityTextField.setText(c.getCity());
            phoneTextField.setText(c.getPhone());
            emailTextField.setText(c.getEmail());
            faxTextField.setText(c.getFax());
            diskonCodeTextField.setText(c.getDiscountCode());
            creditLimitTextField.setText(String.valueOf(c.getCreditLimit()));
        } catch (SQLException ex) {
            Logger.getLogger(CustomerApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    private List<Order> findOrders(int customerId) {
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement("select po.quantity, p.PURCHASE_COST, p.description from purchase_order po join product p on po.PRODUCT_ID = p.PRODUCT_ID where po.CUSTOMER_ID = ?");
            pstm.setInt(1, customerId);
            ResultSet results = pstm.executeQuery();
            while (results.next()) {
                System.out.println("Ada order");
                Order order = new Order();
                order.setProdukDescription(results.getString("description"));
                order.setCost(results.getDouble("purchase_cost"));
                order.setQuantity(results.getInt("quantity"));
                orders.add(order);
            }
        } catch (SQLException ex) {
        }
        return orders;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        customerPanel = new javax.swing.JPanel();
        creditLimitTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cityTextField = new javax.swing.JTextField();
        addressTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        namaTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();
        phoneTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        emailTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        diskonCodeTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        faxTextField = new javax.swing.JTextField();
        orderPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        customerPanel.setBackground(new java.awt.Color(204, 204, 255));
        customerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Customer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(102, 0, 102))); // NOI18N
        customerPanel.setForeground(new java.awt.Color(204, 255, 204));

        jLabel3.setText("Credit Limit");

        jLabel2.setText("City");

        jLabel1.setText("Address");

        jLabel4.setText("Nama");

        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer_ID", "Name", "AddressLine1"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(customerTable);

        jLabel5.setText("Phone");

        jLabel6.setText("Email");

        emailTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTextFieldActionPerformed(evt);
            }
        });

        jLabel7.setText("Discount Code");

        jLabel8.setText("Fax");

        faxTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faxTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout customerPanelLayout = new javax.swing.GroupLayout(customerPanel);
        customerPanel.setLayout(customerPanelLayout);
        customerPanelLayout.setHorizontalGroup(
            customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerPanelLayout.createSequentialGroup()
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(cityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(customerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                                .addComponent(phoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(customerPanelLayout.createSequentialGroup()
                                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8))
                                .addGap(64, 64, 64)
                                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(emailTextField)
                                    .addComponent(creditLimitTextField)
                                    .addComponent(diskonCodeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                                    .addComponent(faxTextField)))))
                    .addGroup(customerPanelLayout.createSequentialGroup()
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(59, 59, 59))
                            .addGroup(customerPanelLayout.createSequentialGroup()
                                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2))
                                .addGap(71, 71, 71)))
                        .addGap(35, 35, 35)
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(namaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        customerPanelLayout.setVerticalGroup(
            customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerPanelLayout.createSequentialGroup()
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(addressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(phoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(faxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(diskonCodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(creditLimitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Customer", customerPanel);

        orderPanel.setBackground(new java.awt.Color(255, 204, 255));
        orderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ProdukDescription", "Cost", "Quantity", "Total Cost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(orderTable);

        javax.swing.GroupLayout orderPanelLayout = new javax.swing.GroupLayout(orderPanel);
        orderPanel.setLayout(orderPanelLayout);
        orderPanelLayout.setHorizontalGroup(
            orderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );
        orderPanelLayout.setVerticalGroup(
            orderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orderPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(248, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Order", orderPanel);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void emailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTextFieldActionPerformed

    private void faxTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faxTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_faxTextFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CustomerApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerApp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressTextField;
    private javax.swing.JTextField cityTextField;
    private javax.swing.JTextField creditLimitTextField;
    private javax.swing.JPanel customerPanel;
    private javax.swing.JTable customerTable;
    private javax.swing.JTextField diskonCodeTextField;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JTextField faxTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField namaTextField;
    private javax.swing.JPanel orderPanel;
    private javax.swing.JTable orderTable;
    private javax.swing.JTextField phoneTextField;
    // End of variables declaration//GEN-END:variables
}
