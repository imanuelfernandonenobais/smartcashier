package smartcashier.model;
/*
 * @author Kel Uyah
 */

public class Kain {
    private int idKain;
    private String namaKain;
    private KategoriKain kategori;

    private double hargaPerMeter;
    private double hargaPerRoll;
    private double hargaPerYard;
    private double hargaPerKg;

    private double stokMeter;
    private double stokRoll;
    private double stokYard;
    private double stokKg;

    public Kain() {
    }

    public Kain(int idKain, String namaKain, KategoriKain kategori,
                double hargaPerMeter, double hargaPerRoll, double hargaPerYard, double hargaPerKg,
                double stokMeter, double stokRoll, double stokYard, double stokKg) {
        this.idKain = idKain;
        this.namaKain = namaKain;
        this.kategori = kategori;
        this.hargaPerMeter = hargaPerMeter;
        this.hargaPerRoll = hargaPerRoll;
        this.hargaPerYard = hargaPerYard;
        this.hargaPerKg = hargaPerKg;
        this.stokMeter = stokMeter;
        this.stokRoll = stokRoll;
        this.stokYard = stokYard;
        this.stokKg = stokKg;
    }

    public int getIdKain() {
        return idKain;
    }

    public void setIdKain(int idKain) {
        this.idKain = idKain;
    }

    public String getNamaKain() {
        return namaKain;
    }

    public void setNamaKain(String namaKain) {
        this.namaKain = namaKain;
    }

    public KategoriKain getKategori() {
        return kategori;
    }

    public void setKategori(KategoriKain kategori) {
        this.kategori = kategori;
    }

    public double getHargaPerMeter() {
        return hargaPerMeter;
    }

    public void setHargaPerMeter(double hargaPerMeter) {
        this.hargaPerMeter = hargaPerMeter;
    }

    public double getHargaPerRoll() {
        return hargaPerRoll;
    }

    public void setHargaPerRoll(double hargaPerRoll) {
        this.hargaPerRoll = hargaPerRoll;
    }

    public double getHargaPerYard() {
        return hargaPerYard;
    }

    public void setHargaPerYard(double hargaPerYard) {
        this.hargaPerYard = hargaPerYard;
    }

    public double getHargaPerKg() {
        return hargaPerKg;
    }

    public void setHargaPerKg(double hargaPerKg) {
        this.hargaPerKg = hargaPerKg;
    }

    public double getStokMeter() {
        return stokMeter;
    }

    public void setStokMeter(double stokMeter) {
        this.stokMeter = stokMeter;
    }

    public double getStokRoll() {
        return stokRoll;
    }

    public void setStokRoll(double stokRoll) {
        this.stokRoll = stokRoll;
    }

    public double getStokYard() {
        return stokYard;
    }

    public void setStokYard(double stokYard) {
        this.stokYard = stokYard;
    }

    public double getStokKg() {
        return stokKg;
    }

    public void setStokKg(double stokKg) {
        this.stokKg = stokKg;
    }

    public double getHargaBySatuan(String satuan) {
        switch (satuan.toLowerCase()) {
            case "meter":
                return hargaPerMeter;
            case "roll":
                return hargaPerRoll;
            case "yard":
                return hargaPerYard;
            case "kg":
                return hargaPerKg;
            default:
                throw new IllegalArgumentException("Satuan tidak valid: " + satuan);
        }
    }

    public double getStokBySatuan(String satuan) {
        switch (satuan.toLowerCase()) {
            case "meter":
                return stokMeter;
            case "roll":
                return stokRoll;
            case "yard":
                return stokYard;
            case "kg":
                return stokKg;
            default:
                throw new IllegalArgumentException("Satuan tidak valid: " + satuan);
        }
    }

    public void tambahStok(String satuan, double jumlah) {
        if (jumlah < 0) {
            throw new IllegalArgumentException("Jumlah stok tidak boleh negatif.");
        }

        switch (satuan.toLowerCase()) {
            case "meter":
                stokMeter += jumlah;
                break;
            case "roll":
                stokRoll += jumlah;
                break;
            case "yard":
                stokYard += jumlah;
                break;
            case "kg":
                stokKg += jumlah;
                break;
            default:
                throw new IllegalArgumentException("Satuan tidak valid: " + satuan);
        }
    }

    public void kurangiStok(String satuan, double jumlah) {
        if (jumlah < 0) {
            throw new IllegalArgumentException("Jumlah stok tidak boleh negatif.");
        }

        switch (satuan.toLowerCase()) {
            case "meter":
                if (stokMeter < jumlah) {
                    throw new IllegalArgumentException("Stok meter tidak mencukupi.");
                }
                stokMeter -= jumlah;
                break;
            case "roll":
                if (stokRoll < jumlah) {
                    throw new IllegalArgumentException("Stok roll tidak mencukupi.");
                }
                stokRoll -= jumlah;
                break;
            case "yard":
                if (stokYard < jumlah) {
                    throw new IllegalArgumentException("Stok yard tidak mencukupi.");
                }
                stokYard -= jumlah;
                break;
            case "kg":
                if (stokKg < jumlah) {
                    throw new IllegalArgumentException("Stok kg tidak mencukupi.");
                }
                stokKg -= jumlah;
                break;
            default:
                throw new IllegalArgumentException("Satuan tidak valid: " + satuan);
        }
    }

    @Override
    public String toString() {
        return namaKain;
    }
}