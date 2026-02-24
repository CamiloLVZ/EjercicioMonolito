package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CREDITO")
public class CuentaDeCredito extends Cuenta {

    @Column(nullable = true)
    private double cupoTotal;

    @Column(nullable = true)
    private double deudaActual;

    public CuentaDeCredito() {
    }

    public CuentaDeCredito(String numeroCuenta, Cliente titular, double cupoTotal) {
        super(numeroCuenta, titular);
        this.cupoTotal = cupoTotal;
        this.deudaActual = 0;
    }

    public boolean retirarAvance(double valor) {
        if (valor <= 0) {
            System.out.println("El valor a retirar debe ser positivo");
            return false;
        }
        if (valor + deudaActual > cupoTotal) {
            System.out.println("Cupo insuficiente para avance");
            return false;
        }
        this.deudaActual += valor;
        return true;
    }

    public boolean abonarDeuda(double valor) {
        if (valor <= 0) {
            System.out.println("El valor a abonar debe ser positivo");
            return false;
        }

        if (valor > deudaActual) {
            System.out.println("El valor no puede ser mayor a la deuda");
            return false;
        }

        this.deudaActual -= valor;
        return true;
    }

    public double getCupoTotal() {
        return cupoTotal;
    }

    public void setCupoTotal(double cupoTotal) {
        this.cupoTotal = cupoTotal;
    }

    public double getDeudaActual() {
        return deudaActual;
    }

    public void setDeudaActual(double deudaActual) {
        this.deudaActual = deudaActual;
    }
}
