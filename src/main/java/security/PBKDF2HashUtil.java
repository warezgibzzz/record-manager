package security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class PBKDF2HashUtil implements HashUtilInterface {
    protected SecretKeyFactory factory;

    public PBKDF2HashUtil() {
        try {
            this.factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt;
    }


    public byte[] hashPassword(byte[] salt, String password) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);

        byte[] hash = new byte[0];

        try {
            hash = this.factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return hash;
    }

    public boolean checkPassword(String hash, String password, byte[] salt) {
        byte[] hashArray = hash.getBytes();
        byte[] checkedPasswordArray = this.hashPassword(salt, password);

        return Arrays.equals(hashArray, checkedPasswordArray);
    }
}
