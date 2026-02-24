package com.example.service;

import com.example.entity.Cliente;
import com.example.persistence.ClienteRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(EntityManager entityManager) {
        this.clienteRepository = new ClienteRepository(entityManager);
    }

    public Cliente crearCliente(String nombre, String documento) {
        Cliente cliente = new Cliente(nombre, documento);
        return clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public List listarClientes() {
        return clienteRepository.findAll();
    }
}
