package smartcashier.model;
/*
 * @author Kel Uyah
 */

public class Admin extends User {

    public Admin() {
        setRole("admin");
    }

    public Admin(int idUser, String namaUser, String username, String password) {
        super(idUser, namaUser, username, password, "admin");
    }

    public String kelolaDataKain() {
        return "Admin mengelola data kain.";
    }

    public String kelolaKategori() {
        return "Admin mengelola kategori kain.";
    }

    public String kelolaStok() {
        return "Admin mengelola stok kain.";
    }

    public String kelolaPelanggan() {
        return "Admin mengelola data pelanggan.";
    }

    @Override
    public void tampilkanInfoUser() {
        System.out.println("=== DATA ADMIN ===");
        System.out.println("ID User   : " + getIdUser());
        System.out.println("Nama User : " + getNamaUser());
        System.out.println("Username  : " + getUsername());
        System.out.println("Role      : " + getRole());
    }
}