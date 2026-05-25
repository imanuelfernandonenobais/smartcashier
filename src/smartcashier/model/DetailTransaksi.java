package smartcashier.model;
/*
 * @author Kel Uyah
 */

public class DetailTransaksi {
    private int idDetail;
    private Kain kain;
    private String satuan;
    private double jumlah;
    private double harga;
    private double subtotal;

    public DetailTransaksi() {
    }

    public DetailTransaksi(int idDetail, Kain kain, String satuan, double jumlah) {
        this.idDetail = idDetail;
        this.kain = kain;
        this.satuan = satuan;
        this.jumlah = jumlah;
        refreshHargaDanSubtotal();
    }

    public int getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    public Kain getKain() {
        return kain;
    }

    public void setKain(Kain kain) {
        this.kain = kain;
        refreshHargaDanSubtotal();
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
        refreshHargaDanSubtotal();
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
        refreshHargaDanSubtotal();
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
        this.subtotal = hitungSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double hitungSubtotal() {
        return jumlah * harga;
    }

    public void refreshHargaDanSubtotal() {
        if (kain != null && satuan != null && !satuan.isEmpty()) {
            this.harga = kain.getHargaBySatuan(satuan);
        }
        this.subtotal = hitungSubtotal();
    }
}