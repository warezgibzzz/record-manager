package security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class PBKDF2HashUtil implements HashUtilInterface {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    protected SecretKeyFactory factory;

    public PBKDF2HashUtil() {
        try {
            this.factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return salt;
    }


    public byte[] hashPassword(String salt, String password) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);

        byte[] hash = new byte[0];

        try {
            hash = this.factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return hash;
    }

    public boolean checkPassword(String hashedPassword, String password, String salt) {
        byte[] hashArray = hashedPassword.getBytes();
        byte[] checkedPasswordArray = this.hashPassword(salt, password);

        return Arrays.equals(hashArray, checkedPasswordArray);
    }
}
