package smartcashier.model;
/*
 * @author Kel Uyah
 */

public class Pelanggan {
    private int idPelanggan;
    private String namaPelanggan;
    private String alamat;
    private String noHp;

    public Pelanggan() {
    }

    public Pelanggan(int idPelanggan, String namaPelanggan, String alamat, String noHp) {
        this.idPelanggan = idPelanggan;
        this.namaPelanggan = namaPelanggan;
        this.alamat = alamat;
        this.noHp = noHp;
    }

    public int getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(int idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    @Override
    public String toString() {
        return namaPelanggan;
    }
}