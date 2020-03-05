/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spp_fatahillah;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dhimas
 */
public class Operator extends javax.swing.JFrame {

private Connection con;
private Statement st;
private ResultSet RsOperator;
private String sql="";

private String pengguna, sandi, nama, tipe, status, pembuat;
    /**
     * Creates new form Operator
     */
    public Operator() {
        initComponents();
        koneksitabel();
        tampildata("SELECT * FROM operator");
    }

    private void tampildata(String sql){
        DefaultTableModel datalist = new DefaultTableModel();
        datalist.addColumn("No");
        datalist.addColumn("Pengguna");
        datalist.addColumn("Sandi");
        datalist.addColumn("Nama");
        datalist.addColumn("Tipe");
        datalist.addColumn("Status");
        datalist.addColumn("Pembuat");
        try {
            int i = 1;
            st=con.createStatement();
            RsOperator=st.executeQuery("select * from operator");
            while (RsOperator.next()){
                datalist.addRow(new Object[]{
                    (""+i++),RsOperator.getString(1), RsOperator.getString(2), 
                    RsOperator.getString(3), RsOperator.getString(4), RsOperator.getString(5), RsOperator.getString(6)
                });
                Gridoperator.setModel(datalist);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "GAGAL TAMPIL \n"+e.getMessage());
        } 
    }
    
    private void form_awal(){
        form_disable();
        form_clear(); 
        Btn_simpan.setText("Simpan");
        Btn_tambah.requestFocus(true);
        Btn_tambah.setEnabled(true);
        Btn_simpan.setEnabled(false);
        Btn_batal.setEnabled(false);
        Btn_hapus.setEnabled(false);   
    }
    
    private void form_disable(){
        txt_pengguna.setEnabled(false);
        txt_sandi.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_tipe.setEnabled(false);
        txt_status.setEnabled(false);
        txt_pembuat.setEnabled(false);
    }
    
    public void disableData(){
        txt_pengguna.setEnabled(false);
        txt_sandi.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_tipe.setEnabled(false);
        txt_status.setEnabled(false);
        txt_pembuat.setEnabled(false);
        Btn_tambah.setEnabled(true);
        Btn_kembali.setEnabled(false);
        Btn_simpan.setEnabled(false);
        Btn_hapus.setEnabled(false);
        Btn_batal.setEnabled(false);
        Btn_tambah.requestFocus();
    }
    
    public void enableData(){
        txt_pengguna.setEnabled(true);
        txt_sandi.setEnabled(true);
        txt_nama.setEnabled(true);
        txt_tipe.setEnabled(true);
        txt_status.setEnabled(true);
        txt_pembuat.setEnabled(true);
        Btn_tambah.setEnabled(false);
        Btn_kembali.setEnabled(true);
        Btn_simpan.setEnabled(true);
        Btn_hapus.setEnabled(true);
        Btn_batal.setEnabled(true);
    }
    
    public void clearData(){
        txt_pengguna.setText("");
        txt_sandi.setText("");
        txt_nama.setText("");
        txt_tipe.setText("");
        txt_status.setText("");
        txt_pembuat.setText("");
        txt_pengguna.requestFocus();
    }
    
    private void form_clear(){
       txt_pengguna.setText("");
       txt_sandi.setText("");
       txt_nama.setText("");
       txt_tipe.setText("");
       txt_status.setText("");
       txt_pembuat.setText("");
    }
    
    public void refreshTable(){
    try{
        koneksi konek = new  koneksi();
        konek.config();
        Statement stmtTable = konek.con.createStatement();
        String sqlTabel = "select * from operator order by pengguna";
        ResultSet rs = stmtTable.executeQuery(sqlTabel);
        ResultSetMetaData meta = rs.getMetaData();
        String Header[] = {"Pengguna","Sandi","Nama","Tipe","Status","Pembuat"};
        int col = meta.getColumnCount();
        int brs = 0;
        while (rs.next()){
            brs = rs.getRow();
        }
        Object dataTable[][] = new Object[brs][col];
        int x = 0;
        rs.beforeFirst();
        while(rs.next()){
            dataTable[x][0]=rs.getString("pengguna");
            dataTable[x][1]=rs.getString("sandi");
            dataTable[x][2]=rs.getString("nama");
            dataTable[x][3]=rs.getString("tipe");
            dataTable[x][4]=rs.getString("status");
            dataTable[x][5]=rs.getString("pembuat");
            x++;  
        }
        Gridoperator.setModel(new DefaultTableModel(dataTable,Header));
        stmtTable.close();
    }catch(Exception ert) {
        System.out.println(ert.getMessage());
    }
}
    
    private void form_enable(){
        txt_pengguna.setEnabled(true);
        txt_sandi.setEnabled(true);
        txt_nama.setEnabled(true);
        txt_tipe.setEnabled(true);
        txt_status.setEnabled(true);
        txt_pembuat.setEnabled(true);
    }
    
    private void aksi_tambah(){
        form_enable();
        Btn_tambah.setEnabled(true);
        Btn_simpan.setEnabled(true);
        Btn_batal.setEnabled(true);
        Btn_hapus.setEnabled(false);
        Btn_tambah.setEnabled(true);
        
        txt_pengguna.setEnabled(true);
        txt_pengguna.requestFocus(true);
    }
    
     public void deleteData(){
        try{
            koneksi konek = new koneksi();
            konek.config();
            String user = txt_pengguna.getText();
            Statement stmt = konek.con.createStatement();
            String sql = "delete from operator where pengguna='" + user + "'";
            stmt.executeUpdate(sql);
            stmt.close();
            JOptionPane.showMessageDialog(null, "Delete User Sukses");
            clearData();
            refreshTable();
            disableData();
        } catch (Exception e){
            System.out.println(e);
    }
}

    public void simpanData(){
        try{
            koneksi konek = new koneksi();
            konek.config();
            Statement stmt = konek.con.createStatement();
            Statement stmt1 = konek.con.createStatement();
            String user = txt_pengguna.getText();
            String sql2 = "delete from operator where pengguna='" + user + "'";
            stmt1.executeUpdate(sql2);
            stmt1.close();
            String sql1 = "insert into operator(pengguna, sandi, nama, tipe, status, pembuat)"
                + "values('"
                +txt_pengguna.getText()+"','"+txt_sandi.getText()+"','"+txt_nama.getText()+"','"+txt_tipe.getText()+"','"+txt_status.getText()+"','"+txt_pembuat.getText()+"')";
            stmt.executeUpdate(sql1);
            stmt.close();
            JOptionPane.showMessageDialog(null, "Input/Update User Sukses.");
            clearData();
            refreshTable();
            disableData();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    
    private void koneksitabel (){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql:"
                    + "//localhost:3306/pembayaran", "root", "");
            System.out.println("KONEKSI BERHASIL");
            JOptionPane.showMessageDialog(null, "SELAMAT DATANG");  
        } catch (Exception e) {
            System.out.println("KONEKSI GAGAL \n"+e);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_pengguna = new javax.swing.JTextField();
        txt_sandi = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_tipe = new javax.swing.JTextField();
        txt_status = new javax.swing.JTextField();
        txt_pembuat = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Gridoperator = new javax.swing.JTable();
        Btn_tambah = new javax.swing.JButton();
        Btn_simpan = new javax.swing.JButton();
        Btn_batal = new javax.swing.JButton();
        Btn_hapus = new javax.swing.JButton();
        Btn_kembali = new javax.swing.JButton();
        Btn_cetak = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Pengguna");

        jLabel2.setText("Sandi");

        jLabel3.setText("Nama");

        jLabel4.setText("Tipe");

        jLabel5.setText("Status");

        jLabel6.setText("Pembuat");

        txt_pengguna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_penggunaKeyPressed(evt);
            }
        });

        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });

        Gridoperator.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Gridoperator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                GridoperatorMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(Gridoperator);

        Btn_tambah.setText("Tambah");
        Btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_tambahActionPerformed(evt);
            }
        });

        Btn_simpan.setText("Simpan");
        Btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_simpanActionPerformed(evt);
            }
        });
        Btn_simpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Btn_simpanKeyPressed(evt);
            }
        });

        Btn_batal.setText("Batal");
        Btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_batalActionPerformed(evt);
            }
        });

        Btn_hapus.setText("Hapus");
        Btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_hapusActionPerformed(evt);
            }
        });
        Btn_hapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Btn_hapusKeyPressed(evt);
            }
        });

        Btn_kembali.setText("Kembali");
        Btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_kembaliActionPerformed(evt);
            }
        });

        Btn_cetak.setText("Cetak");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Btn_tambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Btn_kembali)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_tipe, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_sandi, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_pengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_pembuat, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Btn_cetak)
                                .addGap(21, 21, 21))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(txt_pengguna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(txt_sandi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_pembuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_tipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Btn_cetak))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_tambah)
                    .addComponent(Btn_kembali)
                    .addComponent(Btn_simpan)
                    .addComponent(Btn_batal)
                    .addComponent(Btn_hapus))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void Btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_simpanActionPerformed
        // TODO add your handling code here:
        simpanData();
        
    }//GEN-LAST:event_Btn_simpanActionPerformed

    private void txt_penggunaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_penggunaKeyPressed
        // TODO add your handling code here:
        pengguna=txt_pengguna.getText();
        int tekanenter=evt.getKeyCode();
        if (tekanenter==10){
            try {
                sql="Select * from operator "
                + "where pengguna='"+ pengguna +"'";
                st=con.createStatement();
                RsOperator=st.executeQuery(sql);
                while (RsOperator.next()) {
                    txt_pengguna.setText(RsOperator.getString("pengguna"));
                    txt_sandi.setText(RsOperator.getString("sandi"));
                    txt_nama.setText(RsOperator.getString("nama"));
                    txt_tipe.setText(RsOperator.getString("tipe"));
                    txt_status.setText(RsOperator.getString("status"));
                    txt_pembuat.setText(RsOperator.getString("pembuat"));
                    
                    JOptionPane.showMessageDialog(null,
                        "Data Ditemukan");
                    Btn_tambah.setEnabled(false);
                    Btn_simpan.setEnabled(false);
                    Btn_hapus.setEnabled(true);
                    txt_pengguna.setEnabled(false);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan \n"+e.getMessage());
                txt_pengguna.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_penggunaKeyPressed

    private void Btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_tambahActionPerformed
        // TODO add your handling code here:
        aksi_tambah();
        txt_pengguna.requestFocus();
    }//GEN-LAST:event_Btn_tambahActionPerformed

    private void Btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_hapusActionPerformed
        // TODO add your handling code here:
       deleteData();
        /* pengguna=String.valueOf(txt_pengguna.getText());

        try {
            sql= "delete from operator where pengguna = '"+pengguna+"'";
            st=con.createStatement();
            st.execute(sql);
            tampildata("Select * from operator");
            form_awal();
            JOptionPane.showMessageDialog(null,
                "Data Berhasil Dihapus");
            txt_pengguna.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Dihapus \n"+e.getMessage());
        }*/
    }//GEN-LAST:event_Btn_hapusActionPerformed

    private void Btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_batalActionPerformed
        // TODO add your handling code here:
        form_clear();
        Btn_tambah.setEnabled(true);
        Btn_simpan.setEnabled(true);
        txt_pengguna.requestFocus();
        Btn_hapus.setEnabled(true);
        txt_pengguna.setEnabled(true);
    }//GEN-LAST:event_Btn_batalActionPerformed

    private void Btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_kembaliActionPerformed
        // TODO add your handling code here:
        login lg = new login();

        lg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_Btn_kembaliActionPerformed

    private void Btn_hapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Btn_hapusKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            deleteData();
        }
    }//GEN-LAST:event_Btn_hapusKeyPressed

    private void GridoperatorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GridoperatorMousePressed
        // TODO add your handling code here:
        JTable table = (JTable)evt.getSource();
        int row = table.getSelectedRow();
        String user = txt_pengguna.getText();
        txt_pengguna.setText((String)table.getValueAt(row, 0));
        txt_sandi.setText((String)table.getValueAt(row, 1));
        txt_nama.setText((String)table.getValueAt(row, 2));
        txt_tipe.setText((String)table.getValueAt(row, 3));
        txt_status.setText((String)table.getValueAt(row, 4));
        txt_pembuat.setText((String)table.getValueAt(row, 5));
        user = txt_pengguna.getText();
        enableData();
    }//GEN-LAST:event_GridoperatorMousePressed

    private void Btn_simpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Btn_simpanKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            simpanData();
        }
    }//GEN-LAST:event_Btn_simpanKeyPressed

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
            java.util.logging.Logger.getLogger(Operator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Operator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Operator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Operator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Operator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_batal;
    private javax.swing.JButton Btn_cetak;
    private javax.swing.JButton Btn_hapus;
    private javax.swing.JButton Btn_kembali;
    private javax.swing.JButton Btn_simpan;
    private javax.swing.JButton Btn_tambah;
    private javax.swing.JTable Gridoperator;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_pembuat;
    private javax.swing.JTextField txt_pengguna;
    private javax.swing.JTextField txt_sandi;
    private javax.swing.JTextField txt_status;
    private javax.swing.JTextField txt_tipe;
    // End of variables declaration//GEN-END:variables
}
