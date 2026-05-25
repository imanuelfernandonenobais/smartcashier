package smartcashier.model;
/*
 * @author Kel Uyah
 */

public class Kasir extends User {

    public Kasir() {
        setRole("kasir");
    }

    public Kasir(int idUser, String namaUser, String username, String password) {
        super(idUser, namaUser, username, password, "kasir");
    }

    public String inputTransaksi() {
        return "Kasir melakukan input transaksi.";
    }

    public String cetakStruk() {
        return "Kasir mencetak struk transaksi.";
    }

    @Override
    public void tampilkanInfoUser() {
        System.out.println("=== DATA KASIR ===");
        System.out.println("ID User   : " + getIdUser());
        System.out.println("Nama User : " + getNamaUser());
        System.out.println("Username  : " + getUsername());
        System.out.println("Role      : " + getRole());
    }
}