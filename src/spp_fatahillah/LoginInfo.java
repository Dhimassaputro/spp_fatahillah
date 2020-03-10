/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spp_fatahillah;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Dhimas
 */
public class LoginInfo {
    String pengguna, tipe;

    public void setPengguna(String pengguna) {
        this.pengguna = pengguna;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    

    public String getPengguna() {
        return pengguna;
    }

    public String getTipe() {
        return tipe;
    }
    
    
}
