package com.salesianos.chat;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class GestorSeguridad {

    private static final String CLAVE_SECRETA = "Clav3S3cr3t412345"; 
    private static final String ALGORITMO = "AES";

    /**
     * Encripta una cadena de texto plano usando AES y la devuelve en Base64.
     */
    public static String encriptar(String textoPlano) {
        try {
            SecretKeySpec claveSpec = new SecretKeySpec(CLAVE_SECRETA.getBytes(), ALGORITMO);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, claveSpec);
            
            byte[] bytesCifrados = cipher.doFinal(textoPlano.getBytes());
            return Base64.getEncoder().encodeToString(bytesCifrados);
        } catch (Exception e) {
            System.err.println("Error al encriptar: " + e.getMessage());
            return null;
        }
    }

    /**
     * Desencripta una cadena en Base64 usando AES y devuelve el texto original.
     */
    public static String desencriptar(String textoCifradoBase64) {
        try {
            SecretKeySpec claveSpec = new SecretKeySpec(CLAVE_SECRETA.getBytes(), ALGORITMO);
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, claveSpec);
            
            byte[] bytesCifrados = Base64.getDecoder().decode(textoCifradoBase64);
            byte[] bytesDescifrados = cipher.doFinal(bytesCifrados);
            return new String(bytesDescifrados);
        } catch (Exception e) {
            return "[Error al desencriptar mensaje corrupto]";
        }
    }
}