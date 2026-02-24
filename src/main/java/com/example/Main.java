package com.example;

import com.example.VistaConsola.VistaConsolaApp;
import com.example.VistaEscritorio.VistaEscritorioApp;
import com.example.config.JpaUtil;
import com.example.controller.ClienteController;
import com.example.controller.CuentaController;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ClienteController clienteController = new ClienteController(JpaUtil.getEntityManagerFactory());
        CuentaController cuentaController = new CuentaController(JpaUtil.getEntityManagerFactory());

        Runtime.getRuntime().addShutdownHook(new Thread(JpaUtil::close));

        String modo = obtenerModo(args);

        try {
            switch (modo) {
                case "consola" -> new VistaConsolaApp(clienteController, cuentaController).run();
                case "escritorio" -> VistaEscritorioApp.iniciarYEsperar(clienteController, cuentaController);
                case "ambas" -> {
                    Thread consola = new Thread(new VistaConsolaApp(clienteController, cuentaController), "vista-consola");
                    consola.start();
                    VistaEscritorioApp.iniciar(clienteController, cuentaController);
                    consola.join();
                }
                default -> System.out.println("Modo no soportado: " + modo);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Ejecución interrumpida");
        } finally {
            JpaUtil.close();
        }
    }

    private static String obtenerModo(String[] args) {
        if (args.length > 0) {
            return args[0].toLowerCase(Locale.ROOT);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione modo de ejecución (consola/escritorio/ambas): ");
        return scanner.nextLine().trim().toLowerCase(Locale.ROOT);
    }
}
