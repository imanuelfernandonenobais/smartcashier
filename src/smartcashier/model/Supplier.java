package smartcashier.model;
/*
 * @author Kel Uyah
 */

public class Supplier {
    private int idSupplier;
    private String namaSupplier;
    private String alamat;
    private String noHp;

    public Supplier() {
    }

    public Supplier(int idSupplier, String namaSupplier, String alamat, String noHp) {
        this.idSupplier = idSupplier;
        this.namaSupplier = namaSupplier;
        this.alamat = alamat;
        this.noHp = noHp;
    }

    public int getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getNamaSupplier() {
        return namaSupplier;
    }

    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
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
        return namaSupplier;
    }
}