package com.chakray.test.users.security;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class AesEncryptionService {

    private static final String ALGORITHM = "AES";


    private static final String SECRET_KEY =
            "12345678901234567890123456789012";

    public String encrypt(String value) {
        try {
            SecretKeySpec key = new SecretKeySpec(
                    SECRET_KEY.getBytes(),
                    ALGORITHM
            );

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    public String decrypt(String encrypted) {
        try {
            SecretKeySpec key = new SecretKeySpec(
                    SECRET_KEY.getBytes(),
                    ALGORITHM
            );

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decoded = Base64.getDecoder().decode(encrypted);

            return new String(cipher.doFinal(decoded));

        } catch (Exception e) {
            throw new RuntimeException("Error decrypting password", e);
        }
    }
    public boolean matches(String raw, String encrypted) {
        return encrypt(raw).equals(encrypted);
    }

}
