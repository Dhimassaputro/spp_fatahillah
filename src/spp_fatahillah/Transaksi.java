package spp_fatahillah;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.web.*;
/**
 *
 * @author Dhimas
 */
public class Transaksi extends javax.swing.JFrame {
private Connection con;
private DefaultTableModel tabmode;
private Statement st;
private ResultSet RsSiswa;
private ResultSet RsTransaksi;
private ResultSet RsSpp;
private ResultSet RsDetail;
private String sql="";
private String no, nis, nisn, nama, alamat, kelas, kodekelas;
private int biayaspp, jumlahbulan, jumlahbulan1, jumlahtotal, bayar, kembali;

public static String tempNis, tempNisn, tempNama;

    /**
     * Creates new form transaksii
     */
    public Transaksi() {
        initComponents();
        koneksitabel();
        DaftarSiswa();
        DaftarSpp();
        tampildata("SELECT * FROM transaksi");
        tampildetailtransaksi("select * from detailtransaksi");
    }

    public void setSiswa(String nis, String nisn, String nama)
    {
        txt_nis.setText(nis);
        txt_nisn.setText(nisn);
        txt_nama.setText(nama);
    }
    
    public void pencarian_transaksi(){
        String cari = txt_cari.getText();
        Object[] Baris={"No Transaksi","Tanggal","NIS","Kode Kelas","Jumlah Bulan","Jumlah Total","Jumlah Bayar","Kembalian"};
        tabmode = new DefaultTableModel(null, Baris);
        Gridtransaksi.setModel(tabmode);
        Connection koneksi = new koneksii().getConnection();
        try{
            String sql="Select * from transaksi where "
                    + "NIS like '%"+cari+"%' "
                    + "OR tanggal like '%"+cari+"%' "
                    + "OR kode_siswa like '%"+cari+"%' "
                    + "OR kode_kelas like '%"+cari+"%' "
                    + "OR jumlah_bulan like '%"+cari+"%' "
                    + "OR jumlah_total like '%"+cari+"%' "
                    + "OR jumlah_bayar like '%"+cari+"%' "
                    + "OR kembali like '%"+cari+"%' "
                    + "order by no asc";
            java.sql.Statement stmt=koneksi.createStatement();
            java.sql.ResultSet rslt=stmt.executeQuery(sql);
            while(rslt.next()){
                String NIS=rslt.getString("NIS");
                String tanggal=rslt.getString("tanggal");
                String kode_kelas=rslt.getString("kode_kelas");
                String jumlah_bulan=rslt.getString("jumlah_bulan");
                String jumlah_total=rslt.getString("jumlah_total");
                String jumlah_bayar=rslt.getString("jumlah_bayar");
                String kembali=rslt.getString("kembali");
                String[] dataField={NIS, tanggal, kode_kelas, jumlah_bulan, jumlah_total, jumlah_bayar, kembali};
                tabmode.addRow(dataField);
            }
        }
        catch(Exception ex){
        }
    }
    private void form_awal(){
        form_disable();
        form_clear(); 
        Btn_Simpan.setText("Simpan");
        Btn_Tambah.setEnabled(true);
        Btn_Simpan.setEnabled(false);
    }
    private void form_disable(){
        txt_nisn.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_alamat.setEnabled(false);
        txt_kelas.setEnabled(false);
        txt_biaya.setEnabled(false);

        Cmb_kl.setEnabled(false);
    }
    private void form_enable(){
        txt_no.setEnabled(true);
        txt_nis.setEnabled(true);
        txt_nisn.setEnabled(true);
        txt_nama.setEnabled(true);
        txt_alamat.setEnabled(true);
        txt_kelas.setEnabled(true);
        txt_biaya.setEnabled(true);
       
        Cmb_kl.setEnabled(true);
    }
public void clearData(){
    txt_no.setText("");
    txt_nis.setText("");
    txt_nisn.setText("");
    txt_nama.setText("");
    txt_alamat.setText("");
    txt_kelas.setText("");
    txt_biaya.setText("");
    txt_total.setText("");
        
        txt_jumlah.setText("");
        txt_jumlahbayar.setText("");
        txt_jumlah1.setText("");
        txt_total1.setText("");
        txt_kembali.setText("");
}
    private void form_clear(){
        txt_no.setText("");
        txt_nisn.setText("");
        txt_nama.setText("");
        txt_alamat.setText("");
        txt_kelas.setText("");
        txt_biaya.setText("");
        txt_total.setText("");
        
        txt_jumlah.setText("");
        txt_jumlahbayar.setText("");
        txt_jumlah1.setText("");
        txt_total1.setText("");
        txt_kembali.setText("");

        Cmb_kl.setSelectedItem("Pilih");
    }
    private void aksi_tambah(){
        form_enable();
        Btn_Tambah.setEnabled(true);
        Btn_Kurang.setEnabled(true);
        Btn_Simpan.setEnabled(true);
        Btn_Batal.setEnabled(true);
        txt_no.requestFocus(true);
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
    private void tampildata(String sql){
        DefaultTableModel datalist = new DefaultTableModel();
        datalist.addColumn("No transaksi");
        datalist.addColumn("Tanggal Transaksi");
        datalist.addColumn("NIS");
        datalist.addColumn("Kode Kelas");
        datalist.addColumn("Jumlah Bulan");
        datalist.addColumn("Jumlah Total");
        datalist.addColumn("Jumlah Bayar");
        datalist.addColumn("Kembalian");
        try {
            int i = 1;
            st=con.createStatement();
            RsTransaksi=st.executeQuery("SELECT * FROM transaksi");
            while (RsTransaksi.next()){
                datalist.addRow(new Object[]{
                    (""+i++), 
                    RsTransaksi.getString(2), RsTransaksi.getString(1), RsTransaksi.getString(3), 
                    RsTransaksi.getString(4), RsTransaksi.getString(5), RsTransaksi.getString(6), RsTransaksi.getString(7)
                });
                Gridtransaksi.setModel(datalist);
        }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "GAGAL TAMPIL \n"+e.getMessage());
        }
        }
    private void DaftarSiswa(){
        try {
            String sql ="Select * FROM siswa";
            Statement st=con.createStatement();
            RsSiswa=st.executeQuery(sql);
            while(RsSiswa.next()){
                String Alliasob=RsSiswa.getString("NIS");
                txt_nis.setText(Alliasob);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Menampilkan \n"
                +e.getMessage());
        }
    }
    private void DaftarSpp(){
        Cmb_kl.removeAllItems();
        Cmb_kl.addItem("Pilih");
        try {
            String sql ="Select * FROM spp";
            Statement st=con.createStatement();
            RsSpp=st.executeQuery(sql);
            while(RsSpp.next()){
                String Alliasps=RsSpp.getString("kelas");
                Cmb_kl.addItem(Alliasps);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Menampilkan Kode SPP \n"
                +e.getMessage());
        }
    }
    private void prosestambah(){
    try {
        DefaultTableModel tableModel = (DefaultTableModel)Gridspp.getModel();
        String[]data = new String[6];
        data[0] = String.valueOf(Cmb_kl.getSelectedItem());
        data[1] = txt_biaya.getText();
        data[2] = txt_jumlah.getText();
        data[3] = txt_total.getText();
        tableModel.addRow(data);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error Memasukkan Data \n" +e.getMessage());
    }
}
    private void total(){
        int jumlahBaris = Gridspp.getRowCount();
        int jumlahtotal =0, jumlahitem=0;
        int jumlahbiaya, totalharga;
        
        TableModel tblmodel;
        tblmodel = Gridspp.getModel();
        for (int i=0; i<jumlahBaris; i++){
            jumlahbiaya = Integer.parseInt(tblmodel.getValueAt(i, 2).toString());
            jumlahitem=jumlahitem+jumlahbiaya;
            totalharga = Integer.parseInt(tblmodel.getValueAt(i, 3).toString());
            jumlahtotal=jumlahtotal+totalharga;}
        txt_jumlah1.setText(String.valueOf(jumlahitem));
        txt_total1.setText(String.valueOf(jumlahtotal));
    }
    private void simpandetail(){
            int jumlah_baris = Gridspp.getRowCount();
            if(jumlah_baris == 0){
                JOptionPane.showMessageDialog(rootPane, "Tabel Masih Kosong!");
            }else{
                try {
                    int i=0;
                    while(i < jumlah_baris){
                        st.executeUpdate("insert into detailtransaksi"
                        + "(NIS,biaya,jumlah,total) "
                        + "values('"+txt_nis.getText() +"', "
                        + "'"+Gridspp.getValueAt(i, 0)+"',"
                        + "'"+Gridspp.getValueAt(i, 1)+"',"
                        + "'"+Gridspp.getValueAt(i, 2)+"')");
                    try {
                        sql="SELECT * FROM spp WHERE "
                                + "kelas='" + Gridspp.getValueAt(i, 0) +"'";
                        st=con.createStatement();
                        RsSpp=st.executeQuery(sql);
                        while(RsSpp.next()){
                            try {
                            st=con.createStatement();
                            st.execute(sql);
                            
                            } catch (Exception err) {
                                JOptionPane.showConfirmDialog(null, "Tidak Ada Barang Update!\n"+err.getMessage());
                            }
                        }
                        }catch (Exception se) {
                                JOptionPane.showConfirmDialog(null, "Data Tidak Ditemukan!!\n"+se.getMessage());
                                txt_nisn.requestFocus();
                                }
                        i++;
                        
                    } //JOptionPane.showMessageDialog(rootPane, "Berhasil Disimpan!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan ! ERROR : \n"+e);
                }
            }
        }
    private void tampildetailtransaksi(String sql){
        try {

            Connection koneksi = new koneksii().getConnection();
            java.sql.Statement st = koneksi.createStatement();
            RsSiswa = st.executeQuery("SELECT bulan, biaya, dibuat FROM v_transaksi_pembayaran where NIS='"+ kelas +"'");
            Gridspp.setModel(DbUtils.resultSetToTableModel(RsSiswa));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "GAGAL TAMPIL \n"+e.getMessage());
        }
    /*DefaultTableModel datalist = new DefaultTableModel();
        datalist.addColumn("No");
        datalist.addColumn("Kelas");
        datalist.addColumn("Biaya");
        datalist.addColumn("Jumlah");
        datalist.addColumn("Total");
        try {
            int i = 1;
            st=con.createStatement();
            RsDetail=st.executeQuery("SELECT bulan, biaya, dibuat FROM v_transaksi_pembayaran where NIS='"+ kelas +"'");
            while (RsDetail.next()){
                datalist.addRow(new Object[]{
                    (""+i++),RsDetail.getString(1), RsDetail.getString(2), 
                    RsDetail.getString(3), RsDetail.getString(4), 
                   
                });
                Gridspp.setModel(datalist);
        
        Btn_Kurang.setEnabled(false);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "GAGAL TAMPIL \n"+e.getMessage());
        } */
        
        
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
        jLabel13 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        txt_nisn = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_alamat = new javax.swing.JTextField();
        txt_kelas = new javax.swing.JTextField();
        txt_jumlah = new javax.swing.JTextField();
        txt_biaya = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Cmb_kl = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txt_jumlah1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_total1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_jumlahbayar = new javax.swing.JTextField();
        txt_kembali = new javax.swing.JTextField();
        Btn_Tambah = new javax.swing.JButton();
        Btn_Kurang = new javax.swing.JButton();
        Btn_Kembali = new javax.swing.JButton();
        Btn_Simpan = new javax.swing.JButton();
        Btn_Batal = new javax.swing.JButton();
        Btn_Keluar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Gridtransaksi = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_no = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        Tanggal_transaksi = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        Gridspp = new javax.swing.JTable();
        txt_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        txt_nis = new javax.swing.JTextField();
        btn_tambah = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 255, 102));

        jLabel13.setText("NIS");

        jLabel9.setText("NISN");

        jLabel12.setText("Nama");

        jLabel11.setText("Alamat");

        jLabel10.setText("Kelas");

        jLabel14.setText("Biaya SPP     RP.");

        jLabel1.setText("Kelas");

        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });

        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });

        txt_jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahActionPerformed(evt);
            }
        });

        jLabel2.setText("Jumlah / Bulan");

        jLabel3.setText("Total");

        Cmb_kl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih" }));
        Cmb_kl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Cmb_klItemStateChanged(evt);
            }
        });

        jLabel4.setText("Jumlah / Bulan");

        txt_jumlah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlah1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Total");

        jLabel6.setText("Jumlah Bayar");

        jLabel7.setText("Kembali");

        txt_jumlahbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahbayarActionPerformed(evt);
            }
        });

        Btn_Tambah.setText("+");
        Btn_Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_TambahActionPerformed(evt);
            }
        });

        Btn_Kurang.setText("-");
        Btn_Kurang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_KurangActionPerformed(evt);
            }
        });

        Btn_Kembali.setText("Kembali");
        Btn_Kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_KembaliActionPerformed(evt);
            }
        });

        Btn_Simpan.setText("Simpan");
        Btn_Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_SimpanActionPerformed(evt);
            }
        });
        Btn_Simpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Btn_SimpanKeyPressed(evt);
            }
        });

        Btn_Batal.setText("Batal");
        Btn_Batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_BatalActionPerformed(evt);
            }
        });

        Btn_Keluar.setText("Keluar");
        Btn_Keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_KeluarActionPerformed(evt);
            }
        });
        Btn_Keluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Btn_KeluarKeyPressed(evt);
            }
        });

        Gridtransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(Gridtransaksi);

        jLabel15.setText("No Transaksi");

        jLabel16.setText("Tanggal Transaksi");

        Gridspp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bulan", "Biaya", "Dibuat"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(Gridspp);

        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });

        btn_cari.setText("Cari");
        btn_cari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_cariMouseClicked(evt);
            }
        });

        txt_nis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nisActionPerformed(evt);
            }
        });

        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cari...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(27, 27, 27)
                                .addComponent(txt_no))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btn_tambah)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel1))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_nisn, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                                            .addComponent(txt_nama)
                                            .addComponent(txt_alamat)
                                            .addComponent(txt_kelas)
                                            .addComponent(txt_nis)))
                                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_cari))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(Btn_Tambah)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(Btn_Kurang))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_jumlah1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_total1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 66, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txt_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addGap(18, 18, 18)
                                            .addComponent(txt_jumlahbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel14)
                                            .addGap(18, 18, 18))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel10)
                                            .addGap(72, 72, 72)))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_biaya)
                                        .addComponent(Cmb_kl, 0, 121, Short.MAX_VALUE))
                                    .addGap(45, 45, 45)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(43, 43, 43)
                                    .addComponent(jLabel16)
                                    .addGap(18, 18, 18)
                                    .addComponent(Tanggal_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Btn_Kembali)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Btn_Simpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Btn_Batal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Btn_Keluar))
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel8)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txt_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel13)
                                .addComponent(jLabel10)
                                .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(txt_nis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Cmb_kl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel14)
                                    .addComponent(txt_nisn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_biaya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(16, 16, 16)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_kelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(29, 29, 29))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Btn_Kurang)
                            .addComponent(Btn_Tambah))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txt_jumlah1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_tambah))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txt_total1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_jumlahbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_cari)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Btn_Kembali)
                                .addComponent(Btn_Simpan)
                                .addComponent(Btn_Batal)
                                .addComponent(Btn_Keluar)
                                .addComponent(jButton1)
                                .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Tanggal_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void txt_jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahActionPerformed
        // TODO add your handling code here:
        biayaspp=Integer.parseInt(txt_biaya.getText());
        jumlahbulan=Integer.parseInt(txt_jumlah.getText());
        jumlahtotal=biayaspp*jumlahbulan;
        txt_total.setText(String.valueOf(jumlahtotal));
    }//GEN-LAST:event_txt_jumlahActionPerformed

    private void Cmb_klItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Cmb_klItemStateChanged
        // TODO add your handling code here:
        try {
            sql="Select * FROM spp WHERE "
            + "kelas='" + Cmb_kl.getSelectedItem() +"'";
            st=con.createStatement();
            RsSpp=st.executeQuery(sql);
            while(RsSpp.next()){
                txt_biaya.setText(RsSpp.getString("biaya"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Menampilkan Detail doktor \n"
                +e.getMessage());
        }
    }//GEN-LAST:event_Cmb_klItemStateChanged

    private void txt_jumlah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlah1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlah1ActionPerformed

    private void txt_jumlahbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahbayarActionPerformed
        // TODO add your handling code here:
        jumlahtotal=Integer.parseInt(txt_total1.getText());
        bayar=Integer.parseInt(txt_jumlahbayar.getText());
        kembali=bayar-jumlahtotal;
        txt_kembali.setText(String.valueOf(kembali));
    }//GEN-LAST:event_txt_jumlahbayarActionPerformed

    private void Btn_TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_TambahActionPerformed
        // TODO add your handling code here:
        prosestambah();
        total();
    }//GEN-LAST:event_Btn_TambahActionPerformed

    private void Btn_KurangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_KurangActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) Gridspp.getModel();
        int row = Gridspp.getSelectedRow();
        if(row>=0){
            int oke=JOptionPane.showConfirmDialog(null,
                "Yakin Mau Hapus?","Konfirmasi",
                JOptionPane.YES_NO_OPTION);
            if(oke==0){
                model.removeRow(row);
            }
        }
    }//GEN-LAST:event_Btn_KurangActionPerformed

    private void Btn_KembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_KembaliActionPerformed
        // TODO add your handling code here:
        masterutama mu = new masterutama();

        mu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_Btn_KembaliActionPerformed

    private void Btn_SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_SimpanActionPerformed
        // TODO add your handling code here:
        String Tanggal , NIS;
        int totalItem, total, bayar, kembali, totalbayar;

        nis=String.valueOf(txt_nis.getText());
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
        Tanggal=format.format(Tanggal_transaksi.getDate());
        kodekelas=Cmb_kl.getItemAt(Cmb_kl.getSelectedIndex()).toString();

        totalItem=Integer.parseInt(txt_jumlah1.getText());
        total=Integer.parseInt(txt_jumlah.getText());
        bayar=Integer.parseInt(txt_jumlahbayar.getText());
        kembali=Integer.parseInt(txt_kembali.getText());
        totalbayar=Integer.parseInt(txt_total1.getText());
        simpandetail();
        try {
            sql="INSERT INTO transaksi(NIS, "
            + "tanggal, "
            + "kode_kelas, "
            + "jumlah_bulan, "
            + "jumlah_total, "
            + "jumlah_bayar, "
            + "kembali)VALUES"
            + "('"+ nis +"',"
            + "'"+ Tanggal +"',"
            + "'"+ kodekelas +"',"
            + "'"+ total +"',"
            + "'"+ totalbayar +"',"
            + "'"+ bayar +"',"
            + "'"+ kembali +"')";
            st=con.createStatement();
            st.execute(sql);
            tampildata("Select * from transaksi");
            form_awal();
            JOptionPane.showMessageDialog(null,
                "Data Tersimpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan \n"+e.getMessage());
        }
    }//GEN-LAST:event_Btn_SimpanActionPerformed

    private void Btn_SimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Btn_SimpanKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_Btn_SimpanKeyPressed

    private void Btn_BatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_BatalActionPerformed
        // TODO add your handling code here:
        form_clear();
        form_enable();
        Btn_Tambah.setEnabled(true);
        Btn_Simpan.setEnabled(true);
        Btn_Kurang.setEnabled(true);
        txt_no.requestFocus();
        txt_no.setEnabled(true);
    }//GEN-LAST:event_Btn_BatalActionPerformed

    private void Btn_KeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_KeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_Btn_KeluarActionPerformed

    private void Btn_KeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Btn_KeluarKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_Btn_KeluarKeyPressed

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        // TODO add your handling code here:
        pencarian_transaksi();
    }//GEN-LAST:event_txt_cariActionPerformed

    private void btn_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cariMouseClicked
        // TODO add your handling code here:
        pencarian_transaksi();
    }//GEN-LAST:event_btn_cariMouseClicked

    private void txt_nisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nisActionPerformed
        // TODO add your handling code here:
        try {
            sql="Select * FROM siswa WHERE "
            + "NIS='" + txt_nis.getText() +"'";
            st=con.createStatement();
            RsSiswa=st.executeQuery(sql);
            while(RsSiswa.next()){
                txt_nisn.setText(RsSiswa.getString("nisn"));
                txt_nama.setText(RsSiswa.getString("nama"));
                txt_alamat.setText(RsSiswa.getString("alamat"));
                txt_kelas.setText(RsSiswa.getString("kelas"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Menampilkan Detail Pelanggan \n"
                +e.getMessage());
        }
    }//GEN-LAST:event_txt_nisActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        form_enable();
        clearData();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            koneksi konek = new koneksi();
            konek.config();
            Map prs = new HashMap();
            JasperReport Jrpt =
            JasperCompileManager.compileReport("E:\\Documents\\NetBeansProjects\\spp_fatahillah\\src\\spp_fatahillah\\reporttransaksi.jrxml");
            JasperPrint jp = JasperFillManager.fillReport(Jrpt, prs,konek.con);
            JasperViewer.viewReport(jp,false);
        } catch(Exception e) {
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         // TODO add your handling code here:
        DataSiswa ds = new DataSiswa();
        ds.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    
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
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Batal;
    private javax.swing.JButton Btn_Keluar;
    private javax.swing.JButton Btn_Kembali;
    private javax.swing.JButton Btn_Kurang;
    private javax.swing.JButton Btn_Simpan;
    private javax.swing.JButton Btn_Tambah;
    private javax.swing.JComboBox Cmb_kl;
    private javax.swing.JTable Gridspp;
    private javax.swing.JTable Gridtransaksi;
    private com.toedter.calendar.JDateChooser Tanggal_transaksi;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_biaya;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_jumlah1;
    private javax.swing.JTextField txt_jumlahbayar;
    private javax.swing.JTextField txt_kelas;
    private javax.swing.JTextField txt_kembali;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_nis;
    private javax.swing.JTextField txt_nisn;
    private javax.swing.JTextField txt_no;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_total1;
    // End of variables declaration//GEN-END:variables
}
