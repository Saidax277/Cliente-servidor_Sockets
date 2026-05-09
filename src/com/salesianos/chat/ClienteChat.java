package com.salesianos.chat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteChat {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8085)) {
            System.out.println("Conectado al chat seguro. ¡Empieza a escribir!");
            new Thread(() -> {
                try {
                    BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String msg;
                    while ((msg = entrada.readLine()) != null) {
                        // DESENCRIPTAMOS el mensaje recibido antes de mostrarlo
                        String msgDesencriptado = GestorSeguridad.desencriptar(msg);
                        System.out.println("\n[Chat]: " + msgDesencriptado);
                    }
                } catch (IOException e) {
                    System.out.println("Conexión cerrada por el servidor.");
                }
            }).start();

            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            try (Scanner teclado = new Scanner(System.in)) {
                while (teclado.hasNextLine()) {
                    String linea = teclado.nextLine();
                    // ENCRIPTAMOS el mensaje antes de enviarlo
                    String lineaEncriptada = GestorSeguridad.encriptar(linea);
                    salida.println(lineaEncriptada);
                }
            }

        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor: " + e.getMessage());
        }
    }
}