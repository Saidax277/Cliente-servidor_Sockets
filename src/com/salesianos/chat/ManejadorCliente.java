package com.salesianos.chat;

import java.io.*;
import java.net.*;

public class ManejadorCliente extends Thread {
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);
            ServidorChat.añadirEscritor(salida);
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                // El servidor recibe datos cifrados en Base64.
                String mensajeDesencriptado = GestorSeguridad.desencriptar(mensaje);
                System.out.println("Mensaje recibido cifrado: " + mensaje);
                System.out.println("Mensaje descifrado en consola: " + mensajeDesencriptado);
                
                // Difundimos el mensaje cifrado de vuelta
                ServidorChat.difundirMensaje(mensaje);
            }
        } catch (IOException e) {
            System.out.println("Un cliente se ha desconectado.");
        } finally {
            if (salida != null) ServidorChat.eliminarEscritor(salida);
            try { socket.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }
}