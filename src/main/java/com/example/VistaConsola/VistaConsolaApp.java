package com.example.VistaConsola;

import com.example.controller.ClienteController;
import com.example.controller.CuentaController;
import com.example.entity.Cliente;
import com.example.entity.CuentaDeAhorros;
import com.example.entity.CuentaDeCredito;

import java.util.List;
import java.util.Scanner;

public class VistaConsolaApp implements Runnable {

    private final ClienteController clienteController;
    private final CuentaController cuentaController;
    private final Scanner scanner;

    public VistaConsolaApp(ClienteController clienteController, CuentaController cuentaController) {
        this.clienteController = clienteController;
        this.cuentaController = cuentaController;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        System.out.println("=== Vista Consola iniciada ===");
        boolean continuar = true;

        while (continuar) {
            imprimirMenu();
            int opcion = leerEntero("Seleccione una opción: ");
            try {
                switch (opcion) {
                    case 1 -> crearCliente();
                    case 2 -> crearCuentaAhorros();
                    case 3 -> crearCuentaCredito();
                    case 4 -> depositar();
                    case 5 -> transferir();
                    case 6 -> retirar();
                    case 7 -> avanceCredito();
                    case 8 -> abonarCredito();
                    case 9 -> listarClientes();
                    case 10 -> listarCuentasAhorros();
                    case 11 -> listarCuentasCredito();
                    case 0 -> {
                        continuar = false;
                        System.out.println("Saliendo de Vista Consola...");
                    }
                    default -> System.out.println("Opción no válida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void imprimirMenu() {
        System.out.println("""

                --- Menú Consola ---
                1. Crear cliente
                2. Crear cuenta de ahorros
                3. Crear cuenta de crédito
                4. Depositar en ahorros
                5. Transferir entre ahorros
                6. Retirar de ahorros
                7. Retirar avance de crédito
                8. Abonar deuda de crédito
                9. Listar clientes
                10. Listar cuentas de ahorros
                11. Listar cuentas de crédito
                0. Salir
                """);
    }

    private void crearCliente() {
        String nombre = leerTexto("Nombre: ");
        String documento = leerTexto("Documento: ");
        Cliente cliente = clienteController.crearCliente(nombre, documento);
        System.out.println("Cliente creado con ID: " + cliente.getId());
    }

    private void crearCuentaAhorros() {
        Long clienteId = leerLong("ID del cliente: ");
        String numero = leerTexto("Número cuenta ahorros: ");
        double saldo = leerDouble("Saldo inicial: ");
        CuentaDeAhorros cuenta = cuentaController.crearCuentaAhorros(clienteId, numero, saldo);
        System.out.println("Cuenta ahorros creada: " + cuenta.getNumeroCuenta());
    }

    private void crearCuentaCredito() {
        Long clienteId = leerLong("ID del cliente: ");
        String numero = leerTexto("Número cuenta crédito: ");
        double cupo = leerDouble("Cupo total: ");
        CuentaDeCredito cuenta = cuentaController.crearCuentaCredito(clienteId, numero, cupo);
        System.out.println("Cuenta crédito creada: " + cuenta.getNumeroCuenta());
    }

    private void depositar() {
        String numero = leerTexto("Número de cuenta ahorros: ");
        double valor = leerDouble("Valor a depositar: ");
        boolean ok = cuentaController.depositar(numero, valor);
        System.out.println(ok ? "Depósito exitoso" : "No se pudo depositar");
    }

    private void transferir() {
        String origen = leerTexto("Cuenta origen: ");
        String destino = leerTexto("Cuenta destino: ");
        double valor = leerDouble("Valor a transferir: ");
        boolean ok = cuentaController.transferir(origen, destino, valor);
        System.out.println(ok ? "Transferencia exitosa" : "No se pudo transferir");
    }


    private void retirar() {
        String numero = leerTexto("Número de cuenta ahorros: ");
        double valor = leerDouble("Valor a retirar: ");
        boolean ok = cuentaController.retirar(numero, valor);
        System.out.println(ok ? "Retiro exitoso" : "No se pudo retirar");
    }

    private void avanceCredito() {
        String numero = leerTexto("Cuenta crédito: ");
        double valor = leerDouble("Valor de avance: ");
        boolean ok = cuentaController.retirarAvanceCredito(numero, valor);
        System.out.println(ok ? "Avance exitoso" : "No se pudo retirar avance");
    }


    private void abonarCredito() {
        String numero = leerTexto("Cuenta crédito: ");
        double valor = leerDouble("Valor a abonar: ");
        boolean ok = cuentaController.abonarDeudaCredito(numero, valor);
        System.out.println(ok ? "Abono exitoso" : "No se pudo abonar");
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteController.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes");
            return;
        }
        clientes.forEach(c -> System.out.println(c.getId() + " | " + c.getNombre() + " | " + c.getDocumento()));
    }

    @SuppressWarnings("unchecked")
    private void listarCuentasAhorros() {
        List<CuentaDeAhorros> cuentas = cuentaController.listarCuentasAhorros();
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas de ahorros");
            return;
        }
        cuentas.forEach(c -> System.out.println(c.getNumeroCuenta() + " | saldo=" + c.getSaldo()));
    }

    @SuppressWarnings("unchecked")
    private void listarCuentasCredito() {
        List<CuentaDeCredito> cuentas = cuentaController.listarCuentasCredito();
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas de crédito");
            return;
        }
        cuentas.forEach(c -> System.out.println(c.getNumeroCuenta() + " | deuda=" + c.getDeudaActual() + " | cupo=" + c.getCupoTotal()));
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private Long leerLong(String mensaje) {
        while (true) {
            try {
                return Long.parseLong(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private double leerDouble(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un valor numérico válido.");
            }
        }
    }
}
