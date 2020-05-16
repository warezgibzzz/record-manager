import model.User;
import security.PBKDF2HashUtil;
import service.UserService;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        System.out.println("Hello");

        PBKDF2HashUtil hashUtil = new PBKDF2HashUtil();
        UserService userService = new UserService();


        User admin = new User();

        byte[] salt = hashUtil.generateSalt();
        byte[] password = hashUtil.hashPassword(salt, "123123123");

        admin.setName("admin");
        admin.setSalt((new String(salt)));
        admin.setPassword((new String(password)));

//        userService.saveUser(admin);

        for (User user : userService.findAllUsers()) {
            System.out.println(user);
        }
    }
}
