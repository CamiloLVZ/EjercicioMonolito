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
        List resultado = entityManager
                .createQuery("SELECT c FROM CuentaDeAhorros c WHERE c.numeroCuenta = :numero")
                .setParameter("numero", numeroCuenta)
                .setMaxResults(1)
                .getResultList();

        return resultado.isEmpty() ? null : (CuentaDeAhorros) resultado.get(0);
    }

    public CuentaDeCredito findCreditoByNumero(String numeroCuenta) {
        List resultado = entityManager
                .createQuery("SELECT c FROM CuentaDeCredito c WHERE c.numeroCuenta = :numero")
                .setParameter("numero", numeroCuenta)
                .setMaxResults(1)
                .getResultList();

        return resultado.isEmpty() ? null : (CuentaDeCredito) resultado.get(0);
    }

    public List findAllAhorros() {
        return entityManager
                .createQuery("SELECT c FROM CuentaDeAhorros c ORDER BY c.id")
                .getResultList();
    }

    public List findAllCredito() {
        return entityManager
                .createQuery("SELECT c FROM CuentaDeCredito c ORDER BY c.id")
                .getResultList();
    }

    public void deleteAhorros(CuentaDeAhorros cuenta) {
        entityManager.remove(cuenta);
    }
}
