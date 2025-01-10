package com.vitorpg.clothingstore.services;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHashing {
    private static final int ITERATIONS = 100000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;

    public static String hashPassword(String password) {
        byte[] salt = generateSalt();
        byte[] hash = pbkdf2(password, salt, ITERATIONS, KEY_LENGTH);
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verifyPassword(String password, String storedHash) {
        String[] parts = storedHash.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] hash = Base64.getDecoder().decode(parts[1]);

        byte[] calculatedHash = pbkdf2(password, salt, ITERATIONS, KEY_LENGTH);
        return constantTimeEquals(hash, calculatedHash);
    }

    private static byte[] pbkdf2(String password, byte[] salt, int iterations, int keyLength) {
        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator(new SHA256Digest());
        generator.init(password.getBytes(), salt, iterations);
        return ((KeyParameter) generator.generateDerivedParameters(keyLength)).getKey();
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        return MessageDigest.isEqual(a, b);
    }
}
