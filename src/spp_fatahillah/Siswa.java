package spp_fatahillah;

public class Siswa {
    String nis, nisn, nama;

    public Siswa(String nis, String nisn, String nama) {
        this.nis = nis;
        this.nisn = nisn;
        this.nama = nama;
    }

    public String getNis() {
        return nis;
    }

    public String getNisn() {
        return nisn;
    }

    public String getNama() {
        return nama;
    }
}
