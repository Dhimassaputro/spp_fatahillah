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
    public static String pengguna;
    public static String tipe;

    public static void setPengguna(String pengguna) {
        LoginInfo.pengguna = pengguna;
    }

    public static void setTipe(String tipe) {
        LoginInfo.tipe = tipe;
    }

    public static String getPengguna() {
        return pengguna;
    }

    public static String getTipe() {
        return tipe;
    }
}
