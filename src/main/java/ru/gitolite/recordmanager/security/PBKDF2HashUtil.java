package ru.gitolite.recordmanager.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.stream.IntStream;

public class PBKDF2HashUtil implements HashUtilInterface {
    protected SecretKeyFactory factory;
    protected SecureRandom random = new SecureRandom();

    public PBKDF2HashUtil() {
        try {
            this.factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public byte[] generateSalt() {
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt;
    }


    public byte[] hashPassword(String salt, String password) {
        char[] passwordArr = password.toCharArray();
        byte[] saltByteArr = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(passwordArr, saltByteArr, 65536, 128);

        try {
            return this.factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new AssertionError("Error while hashing a password:" + e.getMessage());
        }
    }

    public boolean checkPassword(String hashedPassword, String password, String salt) {
        byte[] checkedPasswordArray = this.hashPassword(salt, password);
        byte[] hashArray = Base64.getDecoder().decode(hashedPassword);

        return IntStream.range(0, checkedPasswordArray.length).allMatch(i -> checkedPasswordArray[i] == hashArray[i]);
    }
}
