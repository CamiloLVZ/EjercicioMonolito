package com.example.controller;

import com.example.entity.CuentaDeAhorros;
import com.example.entity.CuentaDeCredito;
import com.example.service.CuentaService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class CuentaController {

    private final EntityManagerFactory entityManagerFactory;

    public CuentaController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public CuentaDeAhorros crearCuentaAhorros(Long clienteId, String numeroCuenta, double saldoInicial) {
        return ejecutarEnTransaccion(em -> new CuentaService(em).crearCuentaDeAhorros(clienteId, numeroCuenta, saldoInicial));
    }

    public CuentaDeCredito crearCuentaCredito(Long clienteId, String numeroCuenta, double cupoTotal) {
        return ejecutarEnTransaccion(em -> new CuentaService(em).crearCuentaDeCredito(clienteId, numeroCuenta, cupoTotal));
    }

    public boolean depositar(String numeroCuenta, double valor) {
        return ejecutarEnTransaccion(em -> new CuentaService(em).depositar(numeroCuenta, valor));
    }

    public boolean retirar(String numeroCuenta, double valor) {
        return ejecutarEnTransaccion(em -> new CuentaService(em).retirar(numeroCuenta, valor));
    }

    public boolean transferir(String origen, String destino, double valor) {
        return ejecutarEnTransaccion(em -> new CuentaService(em).transferir(origen, destino, valor));
    }

    public boolean retirarAvanceCredito(String numeroCuenta, double valor) {
        return ejecutarEnTransaccion(em -> new CuentaService(em).retirarAvanceCredito(numeroCuenta, valor));
    }

    public boolean abonarDeudaCredito(String numeroCuenta, double valor) {
        return ejecutarEnTransaccion(em -> new CuentaService(em).abonarDeudaCredito(numeroCuenta, valor));
    }

    public List<CuentaDeAhorros> listarCuentasAhorros() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return new CuentaService(em).listarCuentasAhorros();
        } finally {
            em.close();
        }
    }

    public List<CuentaDeCredito> listarCuentasCredito() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return new CuentaService(em).listarCuentasCredito();
        } finally {
            em.close();
        }
    }

    private <T> T ejecutarEnTransaccion(Operacion<T> operacion) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            T resultado = operacion.ejecutar(em);
            em.getTransaction().commit();
            return resultado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @FunctionalInterface
    private interface Operacion<T> {
        T ejecutar(EntityManager em);
    }
}
