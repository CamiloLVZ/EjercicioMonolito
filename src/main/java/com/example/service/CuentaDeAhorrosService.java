package com.example.service;

import com.example.entity.CuentaDeAhorros;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CuentaDeAhorrosService {

    private final EntityManager entityManager;

    public CuentaDeAhorrosService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void crearCuenta(CuentaDeAhorros cuenta) {
        if (cuenta == null) {
            System.out.println("Cuenta inválida");
            return;
        }
        entityManager.persist(cuenta);
    }

    public CuentaDeAhorros buscarPorNumero(String numeroCuenta) {
        List<CuentaDeAhorros> resultado = entityManager
                .createQuery("SELECT c FROM CuentaDeAhorros c WHERE c.numeroCuenta = :numero", CuentaDeAhorros.class)
                .setParameter("numero", numeroCuenta)
                .setMaxResults(1)
                .getResultList();

        return resultado.isEmpty() ? null : resultado.getFirst();
    }

    public List<CuentaDeAhorros> listarCuentas() {
        return entityManager
                .createQuery("SELECT c FROM CuentaDeAhorros c", CuentaDeAhorros.class)
                .getResultList();
    }

    public void eliminarCuenta(String numeroCuenta) {
        CuentaDeAhorros cuenta = buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println("Cuenta no encontrada");
            return;
        }
        entityManager.remove(cuenta);
    }
}
