package com.example;

import com.example.config.JpaUtil;
import com.example.controller.ClienteController;
import com.example.controller.CuentaController;
import com.example.entity.Cliente;

public class Main {
    public static void main(String[] args) {
        ClienteController clienteController = new ClienteController(JpaUtil.getEntityManagerFactory());
        CuentaController cuentaController = new CuentaController(JpaUtil.getEntityManagerFactory());

        try {
            Cliente cliente1 = clienteController.crearCliente("Ana Torres", "CC-10527");
            Cliente cliente2 = clienteController.crearCliente("Luis Díaz", "CC-107272");

            cuentaController.crearCuentaAhorros(cliente1.getId(), "AHO-0001", 500000);
            cuentaController.crearCuentaAhorros(cliente2.getId(), "AHO-0002", 300000);
            cuentaController.crearCuentaCredito(cliente1.getId(), "CRE-0001", 2000000);

            cuentaController.depositar("AHO-0001", 100000);
            cuentaController.transferir("AHO-0001", "AHO-0002", 150000);
            cuentaController.retirarAvanceCredito("CRE-0001", 200000);

            System.out.println("Clientes registrados: " + clienteController.listarClientes().size());
            System.out.println("Cuentas de ahorro registradas: " + cuentaController.listarCuentasAhorros().size());
            System.out.println("Cuentas de crédito registradas: " + cuentaController.listarCuentasCredito().size());
        } catch (Exception e) {
            System.err.println("Error ejecutando operaciones: " + e.getMessage());
        } finally {
            JpaUtil.close();
        }
    }
}
