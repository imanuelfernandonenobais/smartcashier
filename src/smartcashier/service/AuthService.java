package smartcashier.service;
/*
 * @author Kel Uyah
 */

import smartcashier.dao.UserDAO;
import smartcashier.model.User;
import smartcashier.utils.ValidationUtil;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        if (ValidationUtil.isEmpty(username)) {
            throw new IllegalArgumentException("Username tidak boleh kosong.");
        }

        if (ValidationUtil.isEmpty(password)) {
            throw new IllegalArgumentException("Password tidak boleh kosong.");
        }

        User user = userDAO.login(username.trim(), password.trim());

        if (user == null) {
            throw new IllegalArgumentException("Username atau password salah.");
        }

        return user;
    }
}