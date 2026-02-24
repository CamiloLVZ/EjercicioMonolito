package com.example.service;

import com.example.entity.Cliente;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ClienteService {

    private final EntityManager entityManager;

    public ClienteService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Cliente crearCliente(String nombre, String documento) {
        Cliente cliente = new Cliente(nombre, documento);
        entityManager.persist(cliente);
        return cliente;
    }

    public Cliente buscarPorId(Long id) {
        return entityManager.find(Cliente.class, id);
    }

    public List<Cliente> listarClientes() {
        return entityManager
                .createQuery("SELECT c FROM Cliente c ORDER BY c.id", Cliente.class)
                .getResultList();
    }
}
