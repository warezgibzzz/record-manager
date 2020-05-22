package service;

import exception.InvalidPasswordException;
import exception.NoSuchUserException;
import model.User;
import security.HashUtilInterface;

import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

public class UserAuthenticator {
    protected HashUtilInterface hashUtil;

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

        return true;
    }
}
