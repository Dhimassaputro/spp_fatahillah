/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spp_fatahillah;
import java.awt.BorderLayout;
import java.awt.Container;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
/**
 *
 * @author Dhimas
 */
public class masterutama extends javax.swing.JFrame {

    static String P;
    
    /**
     * Creates new form masterutama
     */
    public void disableMenu(){
        Input.setEnabled(false);
        Transaksi.setEnabled(false);
        data_siswa.setEnabled(false);
    }
    
    public masterutama() {
        initComponents();
        this.setLocationRelativeTo(null);
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
        jMenuBar1 = new javax.swing.JMenuBar();
        Input = new javax.swing.JMenu();
        data_siswa = new javax.swing.JMenuItem();
        data_spp = new javax.swing.JMenuItem();
        Transaksi = new javax.swing.JMenu();
        pembayaran = new javax.swing.JMenuItem();
        Laporan = new javax.swing.JMenu();
        lap_siswa = new javax.swing.JMenuItem();
        lap_transaksi = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );

        jMenuBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Input.setText("Input");

        data_siswa.setText("Data Siswa");
        data_siswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                data_siswaActionPerformed(evt);
            }
        });
        Input.add(data_siswa);

        data_spp.setText("SPP");
        data_spp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                data_sppActionPerformed(evt);
            }
        });
        Input.add(data_spp);

        jMenuBar1.add(Input);

        Transaksi.setText("Transaksi");

        pembayaran.setText("Pembayaran SPP");
        pembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pembayaranActionPerformed(evt);
            }
        });
        Transaksi.add(pembayaran);

        jMenuBar1.add(Transaksi);

        Laporan.setText("Laporan");

        lap_siswa.setText("Laporan Siswa");
        Laporan.add(lap_siswa);

        lap_transaksi.setText("Laporan Transaksi");
        Laporan.add(lap_transaksi);

        jMenuBar1.add(Laporan);

        jMenu1.setText("Exit");
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pembayaranActionPerformed
        // TODO add your handling code here:
        Transaksi tr = new Transaksi();
        tr.setVisible(true);
    }//GEN-LAST:event_pembayaranActionPerformed

    private void data_siswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_data_siswaActionPerformed
        // TODO add your handling code here:
        siswaa sw = new siswaa();
        sw.setVisible(true);
    }//GEN-LAST:event_data_siswaActionPerformed

    private void data_sppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_data_sppActionPerformed
        // TODO add your handling code here:
        DataSpp sp = new DataSpp();
        sp.setVisible(true);
    }//GEN-LAST:event_data_sppActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        setExtendedState(masterutama.MAXIMIZED_BOTH);
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(masterutama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(masterutama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(masterutama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(masterutama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new masterutama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Input;
    private javax.swing.JMenu Laporan;
    private javax.swing.JMenu Transaksi;
    private javax.swing.JMenuItem data_siswa;
    private javax.swing.JMenuItem data_spp;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuItem lap_siswa;
    private javax.swing.JMenuItem lap_transaksi;
    private javax.swing.JMenuItem pembayaran;
    // End of variables declaration//GEN-END:variables
}
