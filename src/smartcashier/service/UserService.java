package smartcashier.service;
/*
 * @author Kel Uyah
 */

import java.util.List;
import smartcashier.dao.UserDAO;
import smartcashier.model.User;
import smartcashier.utils.ValidationUtil;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public boolean simpanUser(User user) {
        validateUser(user);

        if (user.getIdUser() <= 0) {
            return userDAO.insert(user);
        } else {
            return userDAO.update(user);
        }
    }

    public boolean hapusUser(int idUser) {
        if (idUser <= 0) {
            throw new IllegalArgumentException("ID user tidak valid.");
        }
        return userDAO.delete(idUser);
    }

    public User getUserById(int idUser) {
        if (idUser <= 0) {
            throw new IllegalArgumentException("ID user tidak valid.");
        }
        return userDAO.getById(idUser);
    }

    public List<User> getAllUser() {
        return userDAO.getAll();
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Data user tidak boleh null.");
        }

        if (ValidationUtil.isEmpty(user.getNamaUser())) {
            throw new IllegalArgumentException("Nama user tidak boleh kosong.");
        }

        if (ValidationUtil.isEmpty(user.getUsername())) {
            throw new IllegalArgumentException("Username tidak boleh kosong.");
        }

        if (ValidationUtil.isEmpty(user.getPassword())) {
            throw new IllegalArgumentException("Password tidak boleh kosong.");
        }

        if (ValidationUtil.isEmpty(user.getRole())) {
            throw new IllegalArgumentException("Role user tidak boleh kosong.");
        }

        String role = user.getRole().trim().toLowerCase();
        if (!role.equals("admin") && !role.equals("kasir")) {
            throw new IllegalArgumentException("Role user harus admin atau kasir.");
        }
    }
}