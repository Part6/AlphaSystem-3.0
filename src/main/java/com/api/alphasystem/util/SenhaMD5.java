package com.api.alphasystem.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * Compatibilidade com a solicitação do projeto. MD5 não é adequado para
 * armazenamento seguro de senhas em sistemas reais; prefira BCrypt/Argon2.
 */
public final class SenhaMD5 {
    private SenhaMD5() {}

    public static String gerarHash(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(senha.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 não está disponível", e);
        }
    }
}
