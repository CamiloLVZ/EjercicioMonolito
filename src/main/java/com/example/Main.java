package com.example;

import com.example.entity.Cliente;
import com.example.entity.CuentaDeAhorros;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Cliente cliente = new Cliente("Ana Torres", "CC-1001");
            em.persist(cliente);

            CuentaDeAhorros cuenta = new CuentaDeAhorros("AHO-0001", cliente, 500_000);
            em.persist(cuenta);

            em.getTransaction().commit();
            System.out.println("Datos de prueba guardados correctamente en MySQL.");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("No fue posible guardar los datos: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
    }
}
