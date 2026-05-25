package smartcashier.model;
/*
 * @author Kel Uyah
 */
public class User {
    private int idUser;
    private String namaUser;
    private String username;
    private String password;
    private String role;

    // Constructor kosong
    public User() {
    }

    // Constructor dengan parameter
    public User(int idUser, String namaUser, String username, String password, String role) {
        this.idUser = idUser;
        this.namaUser = namaUser;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter dan Setter
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Method tampil data user
    public void tampilkanInfoUser() {
        System.out.println("ID User     : " + idUser);
        System.out.println("Nama User   : " + namaUser);
        System.out.println("Username    : " + username);
        System.out.println("Role        : " + role);
    }
}
