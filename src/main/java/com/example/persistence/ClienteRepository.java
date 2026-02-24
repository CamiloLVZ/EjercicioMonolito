package com.example.persistence;

import com.example.entity.Cliente;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ClienteRepository {

    private final EntityManager entityManager;

    public ClienteRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Cliente save(Cliente cliente) {
        entityManager.persist(cliente);
        return cliente;
    }

    public Cliente findById(Long id) {
        return entityManager.find(Cliente.class, id);
    }

    public List findAll() {
        return entityManager
                .createQuery("SELECT c FROM Cliente c ORDER BY c.id")
                .getResultList();
    }
}
