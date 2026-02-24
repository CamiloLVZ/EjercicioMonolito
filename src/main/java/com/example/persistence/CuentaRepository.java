package com.example.persistence;

import com.example.entity.CuentaDeAhorros;
import com.example.entity.CuentaDeCredito;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CuentaRepository {

    private final EntityManager entityManager;

    public CuentaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CuentaDeAhorros saveAhorros(CuentaDeAhorros cuenta) {
        entityManager.persist(cuenta);
        return cuenta;
    }

    public CuentaDeCredito saveCredito(CuentaDeCredito cuenta) {
        entityManager.persist(cuenta);
        return cuenta;
    }

    public CuentaDeAhorros findAhorrosByNumero(String numeroCuenta) {
        List<CuentaDeAhorros> resultado = entityManager
                .createQuery("SELECT c FROM CuentaDeAhorros c WHERE c.numeroCuenta = :numero", CuentaDeAhorros.class)
                .setParameter("numero", numeroCuenta)
                .setMaxResults(1)
                .getResultList();

        return resultado.isEmpty() ? null : resultado.getFirst();
    }

    public CuentaDeCredito findCreditoByNumero(String numeroCuenta) {
        List<CuentaDeCredito> resultado = entityManager
                .createQuery("SELECT c FROM CuentaDeCredito c WHERE c.numeroCuenta = :numero", CuentaDeCredito.class)
                .setParameter("numero", numeroCuenta)
                .setMaxResults(1)
                .getResultList();

        return resultado.isEmpty() ? null : resultado.getFirst();
    }

    public List<CuentaDeAhorros> findAllAhorros() {
        return entityManager
                .createQuery("SELECT c FROM CuentaDeAhorros c ORDER BY c.id", CuentaDeAhorros.class)
                .getResultList();
    }

    public List<CuentaDeCredito> findAllCredito() {
        return entityManager
                .createQuery("SELECT c FROM CuentaDeCredito c ORDER BY c.id", CuentaDeCredito.class)
                .getResultList();
    }

    public void deleteAhorros(CuentaDeAhorros cuenta) {
        entityManager.remove(cuenta);
    }
}
