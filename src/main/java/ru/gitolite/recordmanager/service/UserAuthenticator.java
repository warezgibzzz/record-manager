package ru.gitolite.recordmanager.service;

import ru.gitolite.recordmanager.exception.InvalidPasswordException;
import ru.gitolite.recordmanager.exception.NoSuchUserException;
import ru.gitolite.recordmanager.model.User;
import ru.gitolite.recordmanager.security.HashUtilInterface;

import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

public class UserAuthenticator {
    protected HashUtilInterface hashUtil;
    private User user;

    public UserAuthenticator(HashUtilInterface hashUtil)
    {
        this.hashUtil = hashUtil;
    }

    public boolean checkUser(String username, String password) throws NoSuchUserException, InvalidKeySpecException, InvalidPasswordException {
        Optional<User> user = (new UserService()).findUserByName(username);

        if (!user.isPresent()) {
            throw new NoSuchUserException();
        }
        if (!hashUtil.checkPassword(user.get().getPassword(), password, user.get().getSalt())) {
            throw new InvalidPasswordException();
        }

        this.setUser(user.get());

        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
