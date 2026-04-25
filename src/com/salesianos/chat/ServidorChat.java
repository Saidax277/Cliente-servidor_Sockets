package com.salesianos.chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorChat {
    private static final int PUERTO = 8085;
    private static Set<PrintWriter> escritoresClientes = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Servidor de chat iniciado en el puerto " + PUERTO);

        try (ServerSocket socketServidor = new ServerSocket(PUERTO)) {
            while (true) {
                new ManejadorCliente(socketServidor.accept()).start();
            }
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }

    public static void difundirMensaje(String mensaje) {
        for (PrintWriter escritor : escritoresClientes) {
            escritor.println(mensaje);
        }
    }
    public static void añadirEscritor(PrintWriter escritor) { escritoresClientes.add(escritor); }
    public static void eliminarEscritor(PrintWriter escritor) { escritoresClientes.remove(escritor); }
}