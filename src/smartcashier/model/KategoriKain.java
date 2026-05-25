package smartcashier.model;
/*
 * @author Kel Uyah
 */

public class KategoriKain {
    private int idKategori;
    private String namaKategori;

    public KategoriKain() {
    }

    public KategoriKain(int idKategori, String namaKategori) {
        this.idKategori = idKategori;
        this.namaKategori = namaKategori;
    }

    public int getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(int idKategori) {
        this.idKategori = idKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    @Override
    public String toString() {
        return namaKategori;
    }
}