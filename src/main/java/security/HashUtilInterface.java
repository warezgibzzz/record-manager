package security;

import java.security.spec.InvalidKeySpecException;

public interface HashUtilInterface {
    byte[] hashPassword(byte[] salt, String password) throws InvalidKeySpecException;
    boolean checkPassword(String hash, String password, byte[] salt) throws InvalidKeySpecException;
}
