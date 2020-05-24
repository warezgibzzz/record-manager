package ru.gitolite.recordmanager.security;

import java.security.spec.InvalidKeySpecException;

public interface HashUtilInterface {
    byte[] hashPassword(String salt, String password) throws InvalidKeySpecException;
    boolean checkPassword(String hashedPassword, String password, String salt) throws InvalidKeySpecException;
}
