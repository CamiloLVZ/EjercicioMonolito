package com.example.controller;

import com.example.entity.Cliente;
import com.example.service.ClienteService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ClienteController {

    private final EntityManagerFactory entityManagerFactory;

    public ClienteController(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Cliente crearCliente(String nombre, String documento) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            Cliente cliente = new ClienteService(em).crearCliente(nombre, documento);
            em.getTransaction().commit();
            return cliente;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List listarClientes() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return new ClienteService(em).listarClientes();
        } finally {
            em.close();
        }
    }
}
