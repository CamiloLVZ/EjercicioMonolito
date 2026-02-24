package com.example.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("miPU");

    private JpaUtil() {
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return EMF;
    }

    public static void close() {
        if (EMF.isOpen()) {
            EMF.close();
        }
    }
}
