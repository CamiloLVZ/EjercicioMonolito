package com.example.entity;

public class CuentaDeCredito extends Cuenta{

    private final double cupoTotal;
    private double deudaActual;

    public CuentaDeCredito(String numeroCuenta, Cliente titular, double cupoTotal) {
        super(numeroCuenta, titular);
        this.cupoTotal = cupoTotal;
        this.deudaActual = 0;
    }

    public boolean retirarAvance(double valor){
        if(valor<=0){
            System.out.println("El valor a retirar debe ser positivo");
            return false;
        }
        if(valor+deudaActual>cupoTotal){
            System.out.println("Cupo insuficiente para avance");
            return false;
        }
        this.deudaActual += valor;
        return true;
    }

    public boolean abonarDeuda(double valor){
        if(valor<=0){
            System.out.println("El valor a abonar debe ser positivo");
            return false;
        }

        if(valor>deudaActual){
            System.out.println("El valor no puede ser mayor a la deuda");
            return false;
        }

        this.deudaActual -= valor;
        return true;
    }

    public double getCupoTotal() {
        return cupoTotal;
    }

    public double getDeudaActual() {
        return deudaActual;
    }

}
