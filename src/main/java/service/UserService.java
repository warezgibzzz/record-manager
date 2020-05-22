package service;

import dao.UserDao;
import model.User;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserDao usersDao;

    public UserService() {
        usersDao = new UserDao();
    }

    public Optional<User> findUser(int id) {
        return usersDao.findById(id);
    }

    public Optional<User> findUserByName(String name) {
        return usersDao.findByName(name);
    }

    public void saveUser(User user) {
        usersDao.save(user);
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public List<User> findAllUsers() {
        return usersDao.findAll();
    }


}