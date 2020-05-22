package service;

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

    public boolean checkUser(String username, String password) {
        Optional<User> user = (new UserService()).findUserByName(username);

        if (!user.isPresent()) {
            System.out.println("Invalid login");
            return false;
        }

        try {
            if (!hashUtil.checkPassword(user.get().getPassword(), password, user.get().getSalt())) {
                System.out.println("Invalid password");
                return false;
            }
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
