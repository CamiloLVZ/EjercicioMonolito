package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("AHORROS")
public class CuentaDeAhorros extends Cuenta {

    @Column(nullable = true)
    private double saldo;

    public CuentaDeAhorros() {
    }

    public CuentaDeAhorros(String numeroCuenta, Cliente titular, double saldo) {
        super(numeroCuenta, titular);
        this.saldo = saldo;
    }

    public boolean depositar(double valor) {
        if (valor <= 0) {
            System.out.println("El valor a depositar debe ser positivo");
            return false;
        }

        this.saldo += valor;
        return true;
    }

    public boolean retirar(double valor) {
        if (valor <= 0) {
            System.out.println("El valor a retirar debe ser positivo");
            return false;
        }
        if (this.saldo < valor) {
            System.out.println("Saldo insuficiente para retiro");
            return false;
        }

        this.saldo -= valor;
        return true;
    }

    public boolean transferir(double valor, CuentaDeAhorros cuentaDestino) {
        if (valor <= 0) {
            System.out.println("El valor a transferir debe ser positivo");
            return false;
        }
        if (this.saldo < valor) {
            System.out.println("Saldo insuficiente para transferencia");
            return false;
        }

        this.saldo -= valor;
        cuentaDestino.depositar(valor);
        return true;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
