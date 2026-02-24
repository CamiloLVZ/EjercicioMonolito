package com.example.service;

import com.example.entity.CuentaDeAhorros;
import com.example.persistence.CuentaRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CuentaDeAhorrosService {

    private final CuentaRepository cuentaRepository;

    public CuentaDeAhorrosService(EntityManager entityManager) {
        this.cuentaRepository = new CuentaRepository(entityManager);
    }

    public void crearCuenta(CuentaDeAhorros cuenta) {
        if (cuenta == null) {
            System.out.println("Cuenta inválida");
            return;
        }
        cuentaRepository.saveAhorros(cuenta);
    }

    public CuentaDeAhorros buscarPorNumero(String numeroCuenta) {
        return cuentaRepository.findAhorrosByNumero(numeroCuenta);
    }

    public List listarCuentas() {
        return cuentaRepository.findAllAhorros();
    }

    public void eliminarCuenta(String numeroCuenta) {
        CuentaDeAhorros cuenta = buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println("Cuenta no encontrada");
            return;
        }
        cuentaRepository.deleteAhorros(cuenta);
    }
}
