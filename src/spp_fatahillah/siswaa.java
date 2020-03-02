/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spp_fatahillah;

import java.sql.Connection;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.awt.Toolkit;
import java.sql.DriverManager;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
public class siswaa extends javax.swing.JFrame {
private Connection con;
private DefaultTableModel tabModel;
private Statement st;
private ResultSet RsSiswa;
private String sql="";
private String nis, nisn, nama, alamat, status, pengguna;
    /**
     * Creates new form siswaa
     */
    public siswaa() {
        initComponents();
        //initTable();
        koneksitabel();
        tampildata("SELECT * FROM siswa");
    }
    
   /* public void initTable()
    {
        Object[] columnNames = { 
                                    "NIS", 
                                    "NISN",
                                    "Nama",
                                    "Alamat",
                                    "Status",
                                    "Pengguna",
                                    "Dibuat",
                                    "Diubah",
                                    };
        
        DefaultTableModel model =  new DefaultTableModel();
        tabModel = new DefaultTableModel(null, columnNames);
        Gridsiswa.setModel(tabModel);
    }*/

    private void form_awal(){
        form_disable();
        form_clear(); 
        btn_Simpan.setText("Simpan");
        btn_Tambah.requestFocus(true);
        btn_Tambah.setEnabled(true);
        btn_Simpan.setEnabled(false);
        btn_Batal.setEnabled(false);
        btn_Hapus.setEnabled(false);
    }
    
    private void form_disable(){
        txt_nis.setEnabled(false);
        txt_nisn.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_alamat.setEnabled(false);
        txt_status.setEnabled(false);
        txt_pengguna.setEnabled(false);
    }
    
    private void form_enable(){
        txt_nis.setEnabled(false);
        txt_nisn.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_alamat.setEnabled(false);
        txt_status.setEnabled(false);
        txt_pengguna.setEnabled(false);
    }
    
    private void form_clear(){
       txt_nis.setText("");
       txt_nisn.setText("");
       txt_nama.setText("");
       txt_alamat.setText("");
       txt_status.setText("");
       txt_pengguna.setText("");
    }
    
    public void disableData(){
        txt_nis.setEnabled(false);
        txt_nisn.setEnabled(false);
        txt_nama.setEnabled(false);
        txt_alamat.setEnabled(false);
        txt_status.setEnabled(false);
        txt_pengguna.setEnabled(false);
        btn_Tambah.setEnabled(true);
        btn_Kembali.setEnabled(false);
        btn_Simpan.setEnabled(false);
        btn_Hapus.setEnabled(false);
        btn_Batal.setEnabled(false);
        btn_Keluar.setEnabled(false);
        btn_Tambah.requestFocus();
    }
    
    public void enableData(){
        txt_nis.setEnabled(true);
        txt_nisn.setEnabled(true);
        txt_nama.setEnabled(true);
        txt_alamat.setEnabled(true);
        txt_status.setEnabled(true);
        txt_pengguna.setEnabled(true);
        btn_Tambah.setEnabled(false);
        btn_Kembali.setEnabled(true);
        btn_Simpan.setEnabled(true);
        btn_Hapus.setEnabled(true);
        btn_Batal.setEnabled(true);
        btn_Keluar.setEnabled(true);
    }
    
    public void clearData(){
        txt_nis.setText("");
        txt_nisn.setText("");
        txt_nama.setText("");
        txt_alamat.setText("");
        txt_status.setText("");
        txt_pengguna.setText("");
        txt_nis.requestFocus();
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
        
        datalist.addColumn("NIS");
        datalist.addColumn("NISN");
        datalist.addColumn("Nama Siswa");
        datalist.addColumn("Alamat");
        datalist.addColumn("Status");
        datalist.addColumn("Pengguna");
        
        try {
            int i = 1;
            st = con.createStatement();
            RsSiswa = st.executeQuery("SELECT * FROM siswa");
            while (RsSiswa.next()){
                datalist.addRow(new Object[]{
                    (""+i++),RsSiswa.getString(1), RsSiswa.getString(2), 
                    RsSiswa.getString(3), RsSiswa.getString(4), RsSiswa.getString(5) 
                });
                Gridsiswa.setModel(datalist);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "GAGAL TAMPIL \n"+e.getMessage());
        }
    }
    
    public void refreshTable(){
        try{
            koneksi konek = new  koneksi();
            konek.config();
            Statement stmtTable = konek.con.createStatement();
            String sqlTabel = "select * from siswa order by nis";
            ResultSet rs = stmtTable.executeQuery(sqlTabel);
            ResultSetMetaData meta = rs.getMetaData();
            String Header[] = {"NIS","NISN","Nama","Alamat","Status","Pengguna"};
            int col = meta.getColumnCount();
            int brs = 0;
            while (rs.next()){
                brs = rs.getRow();
           }
            Object dataTable[][] = new Object[brs][col];
            int x = 0;
            rs.beforeFirst();
            while(rs.next()){
                dataTable[x][0]=rs.getString("nis");
                dataTable[x][1]=rs.getString("nisn");
                dataTable[x][2]=rs.getString("nama");
                dataTable[x][3]=rs.getString("alamat");
                dataTable[x][4]=rs.getString("status");
                dataTable[x][5]=rs.getString("pengguna");
            
                x++;  
            }
        Gridsiswa.setModel(new DefaultTableModel(dataTable,Header));
        stmtTable.close();
    }catch(Exception ert) {
        System.out.println(ert.getMessage());
    }
}

    /*private void simpandetail(){
            int jumlah_baris = Gridsiswa.getRowCount();
            if(jumlah_baris == 0){
                JOptionPane.showMessageDialog(rootPane, "Tabel Masih Kosong!");
            }else{
                try {
                    int i=0;
                    while(i < jumlah_baris){
                        st.executeUpdate("insert into siswa"
                        + "(nis,nisn,nama,alamat,status,pengguna) "
                        + "values('"+txt_nis.getText() +"', "
                        + "'"+Gridsiswa.getValueAt(i, 0)+"',"
                        + "'"+Gridsiswa.getValueAt(i, 1)+"',"
                        + "'"+Gridsiswa.getValueAt(i, 2)+"',"
                        + "'"+Gridsiswa.getValueAt(i, 3)+"',"
                        + "'"+Gridsiswa.getValueAt(i, 4)+"',"
                        + "'"+Gridsiswa.getValueAt(i, 5)+"')");
                    try {
                        sql="SELECT * FROM siswa WHERE "
                                + "nis='" + Gridsiswa.getValueAt(i, 0) +"'";
                        st=con.createStatement();
                        RsSiswa=st.executeQuery(sql);
                        while(RsSiswa.next()){
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
        }*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txt_nis = new javax.swing.JTextField();
        txt_nisn = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_alamat = new javax.swing.JTextField();
        txt_status = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Gridsiswa = new javax.swing.JTable();
        btn_Tambah = new javax.swing.JButton();
        btn_Kembali = new javax.swing.JButton();
        btn_Simpan = new javax.swing.JButton();
        btn_Hapus = new javax.swing.JButton();
        btn_Batal = new javax.swing.JButton();
        btn_Keluar = new javax.swing.JButton();
        btn_Refresh = new javax.swing.JButton();
        txt_cetak = new javax.swing.JButton();
        txt_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_pengguna = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 153));

        txt_nis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nisActionPerformed(evt);
            }
        });
        txt_nis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nisKeyPressed(evt);
            }
        });

        txt_nisn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nisnActionPerformed(evt);
            }
        });

        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });

        txt_alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_alamatActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("NIS");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setText("NISN");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("Nama Siswa");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setText("Alamat");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("Status");

        Gridsiswa.setModel(new javax.swing.table.DefaultTableModel(
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
        Gridsiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                GridsiswaMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(Gridsiswa);

        btn_Tambah.setText("Tambah");
        btn_Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TambahActionPerformed(evt);
            }
        });

        btn_Kembali.setText("Kembali");
        btn_Kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_KembaliActionPerformed(evt);
            }
        });

        btn_Simpan.setText("Simpan");
        btn_Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SimpanActionPerformed(evt);
            }
        });
        btn_Simpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_SimpanKeyPressed(evt);
            }
        });

        btn_Hapus.setText("Hapus");
        btn_Hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HapusActionPerformed(evt);
            }
        });
        btn_Hapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_HapusKeyPressed(evt);
            }
        });

        btn_Batal.setText("Batal");
        btn_Batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BatalActionPerformed(evt);
            }
        });

        btn_Keluar.setText("Keluar");
        btn_Keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_KeluarActionPerformed(evt);
            }
        });
        btn_Keluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_KeluarKeyPressed(evt);
            }
        });

        btn_Refresh.setText("Refresh");
        btn_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RefreshActionPerformed(evt);
            }
        });

        txt_cetak.setText("Cetak");
        txt_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cetakActionPerformed(evt);
            }
        });

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

        jPanel2.setBackground(new java.awt.Color(102, 255, 102));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ikon/logo.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 48)); // NOI18N
        jLabel7.setText("FORM DATA SISWA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("Pengguna");

        txt_pengguna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_penggunaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_nisn, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_nis, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel8))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(71, 71, 71)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_pengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_cetak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_Tambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(btn_Kembali)
                                .addGap(18, 18, 18)
                                .addComponent(btn_Simpan)
                                .addGap(18, 18, 18)
                                .addComponent(btn_Hapus)
                                .addGap(18, 18, 18)
                                .addComponent(btn_Batal)
                                .addGap(18, 18, 18)
                                .addComponent(btn_Keluar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_Refresh)
                                .addGap(0, 42, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cari)))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nis, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nisn, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_status, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Kembali)
                    .addComponent(btn_Simpan)
                    .addComponent(btn_Hapus)
                    .addComponent(btn_Batal)
                    .addComponent(btn_Keluar)
                    .addComponent(jLabel4)
                    .addComponent(btn_Refresh)
                    .addComponent(btn_Tambah))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_pengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cetak))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

    private void btn_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cariMouseClicked
        // TODO add your handling code here:
        pencarian_siswa();
    }//GEN-LAST:event_btn_cariMouseClicked

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        // TODO add your handling code here:
        pencarian_siswa();
    }//GEN-LAST:event_txt_cariActionPerformed

    private void txt_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cetakActionPerformed
        // TODO add your handling code here:
        try {
            koneksi konek = new koneksi();
            konek.config();
            Map prs = new HashMap();
            JasperReport Jrpt =
            JasperCompileManager.compileReport("E:\\Documents\\NetBeansProjects\\spp_fatahillah\\src\\spp_fatahillah\\reportsiswa.jrxml");
            JasperPrint jp = JasperFillManager.fillReport(Jrpt, prs,konek.con);
            JasperViewer.viewReport(jp,false);
        } catch(Exception e) {
        }
    }//GEN-LAST:event_txt_cetakActionPerformed

    private void btn_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RefreshActionPerformed
        // TODO add your handling code here:
        refreshTable();
    }//GEN-LAST:event_btn_RefreshActionPerformed

    private void btn_KeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_KeluarKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            dispose();
        }
    }//GEN-LAST:event_btn_KeluarKeyPressed

    private void btn_KeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_KeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btn_KeluarActionPerformed

    private void btn_BatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BatalActionPerformed
        // TODO add your handling code here:
        form_clear();
        btn_Tambah.setEnabled(true);
        btn_Simpan.setEnabled(true);
        txt_nis.requestFocus();
        btn_Hapus.setEnabled(true);
        txt_nis.setEnabled(true);
    }//GEN-LAST:event_btn_BatalActionPerformed

    private void btn_HapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_HapusKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            deleteData();
        }
    }//GEN-LAST:event_btn_HapusKeyPressed

    private void btn_HapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HapusActionPerformed
        // TODO add your handling code here:
        deleteData();
    }//GEN-LAST:event_btn_HapusActionPerformed

    private void btn_SimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_SimpanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_SimpanKeyPressed

    private void btn_SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SimpanActionPerformed
        // TODO add your handling code here:
        nis = String.valueOf(txt_nis.getText());
        nisn = String.valueOf(txt_nisn.getText());
        nama = String.valueOf(txt_nama.getText());
        alamat = String.valueOf(txt_alamat.getText());
        status = String.valueOf(txt_alamat.getText());
        pengguna = String.valueOf(txt_pengguna.getText());
        try {
            sql="INSERT INTO siswa (nis, "
            + "nisn, "
            + "nama, "
            + "alamat, "
            + "status, "
            + "pengguna)VALUES"
            + "('"+ nis +"',"
            + "'"+ nisn +"',"
            + "'"+ nama +"',"
            + "'"+ alamat +"',"
            + "'"+ status +"',"
            + "'"+ pengguna +"')";
            st=con.createStatement();
            st.execute(sql);
            tampildata("Select * from siswa");
            form_awal();
            JOptionPane.showMessageDialog(null,
                "Data Tersimpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan \n"+e.getMessage());
        }
    }//GEN-LAST:event_btn_SimpanActionPerformed

    private void btn_KembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_KembaliActionPerformed
        // TODO add your handling code here:
        masterutama mu = new masterutama();

        mu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_KembaliActionPerformed

    private void btn_TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TambahActionPerformed
        // TODO add your handling code here:
        enableData();
        clearData();
    }//GEN-LAST:event_btn_TambahActionPerformed

    private void GridsiswaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GridsiswaMousePressed
        // TODO add your handling code here:
        JTable table = (JTable)evt.getSource();
        int row = table.getSelectedRow();
        String user = txt_nis.getText();
        txt_nis.setText((String)table.getValueAt(row, 0));
        txt_nisn.setText((String)table.getValueAt(row, 1));
        txt_nama.setText((String)table.getValueAt(row, 2));
        txt_alamat.setText((String)table.getValueAt(row, 3));
        txt_status.setText((String)table.getValueAt(row, 4));
        txt_pengguna.setText((String)table.getValueAt(row, 5));
        user = txt_nis.getText();
        enableData();
    }//GEN-LAST:event_GridsiswaMousePressed

    private void txt_alamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_alamatActionPerformed
        // TODO add your handling code here:
        txt_status.requestFocus();
    }//GEN-LAST:event_txt_alamatActionPerformed

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
        txt_alamat.requestFocus();
    }//GEN-LAST:event_txt_namaActionPerformed

    private void txt_nisnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nisnActionPerformed
        // TODO add your handling code here:
        txt_nama.requestFocus();
    }//GEN-LAST:event_txt_nisnActionPerformed

    private void txt_nisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nisKeyPressed

    private void txt_nisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nisActionPerformed
        // TODO add your handling code here:
        txt_nisn.requestFocus();
    }//GEN-LAST:event_txt_nisActionPerformed

    private void txt_penggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_penggunaActionPerformed
        // TODO add your handling code here:
        txt_pengguna.requestFocus();
    }//GEN-LAST:event_txt_penggunaActionPerformed

    public void pencarian_siswa(){
        String cari = txt_cari.getText();
        Object[] Baris={"NIS","NISN","Nama","Alamat","Status","Pengguna"};
        tabModel = new DefaultTableModel(null, Baris);
        Gridsiswa.setModel(tabModel);
        Connection koneksi = new koneksii().getConnection();
        try{
            String sql="Select * from siswa where "
                    + "nis like '%"+cari+"%' "
                    + "OR nisn like '%"+cari+"%' "
                    + "OR nama like '%"+cari+"%' "
                    + "OR alamat like '%"+cari+"%' "
                    + "OR status like '%"+cari+"%' "
                    + "OR pengguna like '%"+cari+"%' "
                    
                    + "order by kode asc";
            java.sql.Statement stmt=koneksi.createStatement();
            java.sql.ResultSet rslt=stmt.executeQuery(sql);
            while(rslt.next()){
                String nis = rslt.getString("nis");
                String nisn = rslt.getString("nisn");
                String nama = rslt.getString("nama");
                String alamat = rslt.getString("alamat");
                String status = rslt.getString("status");
                String pengguna = rslt.getString("pengguna");
                
                String[] dataField={nis, nisn, nama, alamat, status, pengguna};
                tabModel.addRow(dataField);
            }
        }
        catch(Exception ex){
        }
    }

/*public void simpanData(){
    try{
        koneksi konek = new koneksi();
        konek.config();
        Statement stmt = konek.con.createStatement();
        Statement stmt1 = konek.con.createStatement();
        String user = txt_nis.getText();
        String sql2 = "delete from siswa where nis='" + user + "'";
        stmt1.executeUpdate(sql2);
        stmt1.close();
        String sql1 = "insert into siswa(nis, nisn, nama, alamat, status, pengguna)"
                + "values('"
                +txt_nis.getText()+"','"+txt_nisn.getText()+"','"
                +txt_nama.getText()+"','"+txt_alamat.getText()+"','"+"','"+txt_status.getText()+"','"+txt_pengguna.getText()+"')";
        stmt.executeUpdate(sql1);
        stmt.close();
        JOptionPane.showMessageDialog(null, "Input/Update User Sukses.");
        clearData();
        refreshTable();
        disableData();
    }catch (Exception e){
        System.out.println(e);
    }
}*/
public void deleteData(){
try{
    koneksi konek = new koneksi();
    konek.config();
    String user = txt_nis.getText();
    Statement stmt = konek.con.createStatement();
    String sql = "delete from siswa where nis='" + user + "'";
    stmt.executeUpdate(sql);
    stmt.close();
    JOptionPane.showMessageDialog(null, "Delete User Sukses");
    clearData();
    refreshTable();
    disableData();
}catch (Exception e){
     System.out.println(e);
}
}
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
            java.util.logging.Logger.getLogger(siswaa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(siswaa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(siswaa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(siswaa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new siswaa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Gridsiswa;
    private javax.swing.JButton btn_Batal;
    private javax.swing.JButton btn_Hapus;
    private javax.swing.JButton btn_Keluar;
    private javax.swing.JButton btn_Kembali;
    private javax.swing.JButton btn_Refresh;
    private javax.swing.JButton btn_Simpan;
    private javax.swing.JButton btn_Tambah;
    private javax.swing.JButton btn_cari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JButton txt_cetak;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_nis;
    private javax.swing.JTextField txt_nisn;
    private javax.swing.JTextField txt_pengguna;
    private javax.swing.JTextField txt_status;
    // End of variables declaration//GEN-END:variables
}
