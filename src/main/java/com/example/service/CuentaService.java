package com.example.service;

import com.example.entity.Cliente;
import com.example.entity.CuentaDeAhorros;
import com.example.entity.CuentaDeCredito;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CuentaService {

    private final EntityManager entityManager;

    public CuentaService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CuentaDeAhorros crearCuentaDeAhorros(Long clienteId, String numeroCuenta, double saldoInicial) {
        Cliente titular = buscarCliente(clienteId);
        CuentaDeAhorros cuenta = new CuentaDeAhorros(numeroCuenta, titular, saldoInicial);
        entityManager.persist(cuenta);
        return cuenta;
    }

    public CuentaDeCredito crearCuentaDeCredito(Long clienteId, String numeroCuenta, double cupoTotal) {
        Cliente titular = buscarCliente(clienteId);
        CuentaDeCredito cuenta = new CuentaDeCredito(numeroCuenta, titular, cupoTotal);
        entityManager.persist(cuenta);
        return cuenta;
    }

    public boolean depositar(String numeroCuenta, double valor) {
        CuentaDeAhorros cuenta = buscarCuentaAhorrosPorNumero(numeroCuenta);
        return cuenta != null && cuenta.depositar(valor);
    }

    public boolean retirar(String numeroCuenta, double valor) {
        CuentaDeAhorros cuenta = buscarCuentaAhorrosPorNumero(numeroCuenta);
        return cuenta != null && cuenta.retirar(valor);
    }

    public boolean transferir(String origen, String destino, double valor) {
        CuentaDeAhorros cuentaOrigen = buscarCuentaAhorrosPorNumero(origen);
        CuentaDeAhorros cuentaDestino = buscarCuentaAhorrosPorNumero(destino);

        if (cuentaOrigen == null || cuentaDestino == null) {
            return false;
        }

        return cuentaOrigen.transferir(valor, cuentaDestino);
    }

    public boolean retirarAvanceCredito(String numeroCuentaCredito, double valor) {
        CuentaDeCredito cuenta = buscarCuentaCreditoPorNumero(numeroCuentaCredito);
        return cuenta != null && cuenta.retirarAvance(valor);
    }

    public boolean abonarDeudaCredito(String numeroCuentaCredito, double valor) {
        CuentaDeCredito cuenta = buscarCuentaCreditoPorNumero(numeroCuentaCredito);
        return cuenta != null && cuenta.abonarDeuda(valor);
    }

    public List<CuentaDeAhorros> listarCuentasAhorros() {
        return entityManager
                .createQuery("SELECT c FROM CuentaDeAhorros c ORDER BY c.id", CuentaDeAhorros.class)
                .getResultList();
    }

    public List<CuentaDeCredito> listarCuentasCredito() {
        return entityManager
                .createQuery("SELECT c FROM CuentaDeCredito c ORDER BY c.id", CuentaDeCredito.class)
                .getResultList();
    }

    public CuentaDeAhorros buscarCuentaAhorrosPorNumero(String numeroCuenta) {
        List<CuentaDeAhorros> resultado = entityManager
                .createQuery("SELECT c FROM CuentaDeAhorros c WHERE c.numeroCuenta = :numero", CuentaDeAhorros.class)
                .setParameter("numero", numeroCuenta)
                .setMaxResults(1)
                .getResultList();

        return resultado.isEmpty() ? null : resultado.getFirst();
    }

    public CuentaDeCredito buscarCuentaCreditoPorNumero(String numeroCuenta) {
        List<CuentaDeCredito> resultado = entityManager
                .createQuery("SELECT c FROM CuentaDeCredito c WHERE c.numeroCuenta = :numero", CuentaDeCredito.class)
                .setParameter("numero", numeroCuenta)
                .setMaxResults(1)
                .getResultList();

        return resultado.isEmpty() ? null : resultado.getFirst();
    }

    private Cliente buscarCliente(Long clienteId) {
        Cliente titular = entityManager.find(Cliente.class, clienteId);
        if (titular == null) {
            throw new IllegalArgumentException("No existe el cliente con id " + clienteId);
        }
        return titular;
    }
}
