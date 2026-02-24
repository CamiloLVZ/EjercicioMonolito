package com.example.service;

import com.example.entity.Cliente;
import com.example.entity.CuentaDeAhorros;
import com.example.entity.CuentaDeCredito;
import com.example.persistence.ClienteRepository;
import com.example.persistence.CuentaRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CuentaService {

    private final ClienteRepository clienteRepository;
    private final CuentaRepository cuentaRepository;

    public CuentaService(EntityManager entityManager) {
        this.clienteRepository = new ClienteRepository(entityManager);
        this.cuentaRepository = new CuentaRepository(entityManager);
    }

    public CuentaDeAhorros crearCuentaDeAhorros(Long clienteId, String numeroCuenta, double saldoInicial) {
        Cliente titular = buscarCliente(clienteId);
        CuentaDeAhorros cuenta = new CuentaDeAhorros(numeroCuenta, titular, saldoInicial);
        return cuentaRepository.saveAhorros(cuenta);
    }

    public CuentaDeCredito crearCuentaDeCredito(Long clienteId, String numeroCuenta, double cupoTotal) {
        Cliente titular = buscarCliente(clienteId);
        CuentaDeCredito cuenta = new CuentaDeCredito(numeroCuenta, titular, cupoTotal);
        return cuentaRepository.saveCredito(cuenta);
    }

    public boolean depositar(String numeroCuenta, double valor) {
        CuentaDeAhorros cuenta = cuentaRepository.findAhorrosByNumero(numeroCuenta);
        return cuenta != null && cuenta.depositar(valor);
    }

    public boolean retirar(String numeroCuenta, double valor) {
        CuentaDeAhorros cuenta = cuentaRepository.findAhorrosByNumero(numeroCuenta);
        return cuenta != null && cuenta.retirar(valor);
    }

    public boolean transferir(String origen, String destino, double valor) {
        CuentaDeAhorros cuentaOrigen = cuentaRepository.findAhorrosByNumero(origen);
        CuentaDeAhorros cuentaDestino = cuentaRepository.findAhorrosByNumero(destino);

        if (cuentaOrigen == null || cuentaDestino == null) {
            return false;
        }

        return cuentaOrigen.transferir(valor, cuentaDestino);
    }

    public boolean retirarAvanceCredito(String numeroCuentaCredito, double valor) {
        CuentaDeCredito cuenta = cuentaRepository.findCreditoByNumero(numeroCuentaCredito);
        return cuenta != null && cuenta.retirarAvance(valor);
    }

    public boolean abonarDeudaCredito(String numeroCuentaCredito, double valor) {
        CuentaDeCredito cuenta = cuentaRepository.findCreditoByNumero(numeroCuentaCredito);
        return cuenta != null && cuenta.abonarDeuda(valor);
    }

    public List<CuentaDeAhorros> listarCuentasAhorros() {
        return cuentaRepository.findAllAhorros();
    }

    public List<CuentaDeCredito> listarCuentasCredito() {
        return cuentaRepository.findAllCredito();
    }

    private Cliente buscarCliente(Long clienteId) {
        Cliente titular = clienteRepository.findById(clienteId);
        if (titular == null) {
            throw new IllegalArgumentException("No existe el cliente con id " + clienteId);
        }
        return titular;
    }
}
