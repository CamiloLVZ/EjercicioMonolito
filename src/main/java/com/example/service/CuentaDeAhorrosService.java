package com.example.service;

import com.example.entity.CuentaDeAhorros;

import java.util.ArrayList;
import java.util.List;

public class CuentaDeAhorrosService {


    private List<CuentaDeAhorros> cuentas = new ArrayList<>();

    // CREATE
    public void crearCuenta(CuentaDeAhorros cuenta) {
        if (cuenta == null) {
            System.out.println("Cuenta inválida");
            return;
        }
        cuentas.add(cuenta);
    }

    // READ (por número de cuenta)
    public CuentaDeAhorros buscarPorNumero(String numeroCuenta) {
        for (CuentaDeAhorros c : cuentas) {
            if (c.getNumeroCuenta().equals(numeroCuenta)) {
                return c;
            }
        }
        return null;
    }

    // READ (todas)
    public List<CuentaDeAhorros> listarCuentas() {
        return cuentas;
    }


    // DELETE
    public void eliminarCuenta(String numeroCuenta) {
        CuentaDeAhorros c = buscarPorNumero(numeroCuenta);
        if (c == null) {
            System.out.println("Cuenta no encontrada");
            return;
        }
        cuentas.remove(c);
    }

}
