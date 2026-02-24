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
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            CuentaDeAhorros cuenta = new CuentaService(em).crearCuentaDeAhorros(clienteId, numeroCuenta, saldoInicial);
            em.getTransaction().commit();
            return cuenta;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public CuentaDeCredito crearCuentaCredito(Long clienteId, String numeroCuenta, double cupoTotal) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            CuentaDeCredito cuenta = new CuentaService(em).crearCuentaDeCredito(clienteId, numeroCuenta, cupoTotal);
            em.getTransaction().commit();
            return cuenta;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public boolean depositar(String numeroCuenta, double valor) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            boolean resultado = new CuentaService(em).depositar(numeroCuenta, valor);
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

    public boolean retirar(String numeroCuenta, double valor) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            boolean resultado = new CuentaService(em).retirar(numeroCuenta, valor);
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

    public boolean transferir(String origen, String destino, double valor) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            boolean resultado = new CuentaService(em).transferir(origen, destino, valor);
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

    public boolean retirarAvanceCredito(String numeroCuenta, double valor) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            boolean resultado = new CuentaService(em).retirarAvanceCredito(numeroCuenta, valor);
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

    public boolean abonarDeudaCredito(String numeroCuenta, double valor) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            boolean resultado = new CuentaService(em).abonarDeudaCredito(numeroCuenta, valor);
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

    public List listarCuentasAhorros() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return new CuentaService(em).listarCuentasAhorros();
        } finally {
            em.close();
        }
    }

    public List listarCuentasCredito() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return new CuentaService(em).listarCuentasCredito();
        } finally {
            em.close();
        }
    }
}
