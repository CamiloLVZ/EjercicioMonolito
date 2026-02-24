package com.example.VistaEscritorio;

import com.example.controller.ClienteController;
import com.example.controller.CuentaController;
import com.example.entity.Cliente;
import com.example.entity.CuentaDeAhorros;
import com.example.entity.CuentaDeCredito;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class VistaEscritorioApp extends JFrame {

    private final ClienteController clienteController;
    private final CuentaController cuentaController;
    private final JTextArea salida;

    public VistaEscritorioApp(ClienteController clienteController, CuentaController cuentaController) {
        super("Banco - Vista Escritorio");
        this.clienteController = clienteController;
        this.cuentaController = cuentaController;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Cliente", panelCrearCliente());
        tabs.add("Ahorros", panelAhorros());
        tabs.add("Crédito", panelCredito());
        tabs.add("Listados", panelListados());

        salida = new JTextArea();
        salida.setEditable(false);

        add(tabs, BorderLayout.CENTER);
        add(new JScrollPane(salida), BorderLayout.SOUTH);
    }

    public static void iniciar(ClienteController clienteController, CuentaController cuentaController) {
        SwingUtilities.invokeLater(() -> new VistaEscritorioApp(clienteController, cuentaController).setVisible(true));
    }

    public static void iniciarYEsperar(ClienteController clienteController, CuentaController cuentaController)
            throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            VistaEscritorioApp app = new VistaEscritorioApp(clienteController, cuentaController);
            app.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    latch.countDown();
                }
            });
            app.setVisible(true);
        });
        latch.await();
    }

    private JPanel panelCrearCliente() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        JTextField nombre = new JTextField();
        JTextField documento = new JTextField();
        JButton crear = new JButton("Crear cliente");

        crear.addActionListener(e -> ejecutar(() -> {
            Cliente cliente = clienteController.crearCliente(nombre.getText(), documento.getText());
            log("Cliente creado: ID=" + cliente.getId());
        }));

        panel.add(new JLabel("Nombre:"));
        panel.add(nombre);
        panel.add(new JLabel("Documento:"));
        panel.add(documento);
        panel.add(new JLabel());
        panel.add(crear);
        return panel;
    }

    private JPanel panelAhorros() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 8, 8));
        JTextField clienteId = new JTextField();
        JTextField numero = new JTextField();
        JTextField destino = new JTextField();
        JTextField valor = new JTextField();

        JButton crear = new JButton("Crear ahorros");
        JButton depositar = new JButton("Depositar");
        JButton retirar = new JButton("Retirar");
        JButton transferir = new JButton("Transferir");

        crear.addActionListener(e -> ejecutar(() -> {
            CuentaDeAhorros cuenta = cuentaController.crearCuentaAhorros(
                    Long.parseLong(clienteId.getText()), numero.getText(), Double.parseDouble(valor.getText()));
            log("Cuenta ahorros creada: " + cuenta.getNumeroCuenta());
        }));

        depositar.addActionListener(e -> ejecutar(() -> {
            boolean ok = cuentaController.depositar(numero.getText(), Double.parseDouble(valor.getText()));
            log(ok ? "Depósito exitoso" : "Depósito fallido");
        }));

        retirar.addActionListener(e -> ejecutar(() -> {
            boolean ok = cuentaController.retirar(numero.getText(), Double.parseDouble(valor.getText()));
            log(ok ? "Retiro exitoso" : "Retiro fallido");
        }));

        transferir.addActionListener(e -> ejecutar(() -> {
            boolean ok = cuentaController.transferir(numero.getText(), destino.getText(), Double.parseDouble(valor.getText()));
            log(ok ? "Transferencia exitosa" : "Transferencia fallida");
        }));

        panel.add(new JLabel("ID cliente:"));
        panel.add(clienteId);
        panel.add(new JLabel("Cuenta origen:"));
        panel.add(numero);
        panel.add(new JLabel("Cuenta destino (para transferir):"));
        panel.add(destino);
        panel.add(new JLabel("Valor / saldo inicial:"));
        panel.add(valor);
        panel.add(crear);
        panel.add(depositar);
        panel.add(retirar);
        panel.add(transferir);
        return panel;
    }

    private JPanel panelCredito() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
        JTextField clienteId = new JTextField();
        JTextField numero = new JTextField();
        JTextField valor = new JTextField();
        JButton crear = new JButton("Crear crédito");
        JButton avance = new JButton("Retirar avance");
        JButton abonar = new JButton("Abonar deuda");

        crear.addActionListener(e -> ejecutar(() -> {
            CuentaDeCredito cuenta = cuentaController.crearCuentaCredito(
                    Long.parseLong(clienteId.getText()), numero.getText(), Double.parseDouble(valor.getText()));
            log("Cuenta crédito creada: " + cuenta.getNumeroCuenta());
        }));

        avance.addActionListener(e -> ejecutar(() -> {
            boolean ok = cuentaController.retirarAvanceCredito(numero.getText(), Double.parseDouble(valor.getText()));
            log(ok ? "Avance exitoso" : "Avance fallido");
        }));

        abonar.addActionListener(e -> ejecutar(() -> {
            boolean ok = cuentaController.abonarDeudaCredito(numero.getText(), Double.parseDouble(valor.getText()));
            log(ok ? "Abono exitoso" : "Abono fallido");
        }));

        panel.add(new JLabel("ID cliente:"));
        panel.add(clienteId);
        panel.add(new JLabel("Número cuenta:"));
        panel.add(numero);
        panel.add(new JLabel("Valor / cupo:"));
        panel.add(valor);
        panel.add(crear);
        panel.add(avance);
        panel.add(new JLabel());
        panel.add(abonar);
        return panel;
    }

    private JPanel panelListados() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton clientes = new JButton("Listar clientes");
        JButton ahorros = new JButton("Listar ahorros");
        JButton creditos = new JButton("Listar créditos");

        clientes.addActionListener(e -> ejecutar(() -> {
            List<Cliente> lista = clienteController.listarClientes();
            if (lista.isEmpty()) {
                log("No hay clientes");
                return;
            }
            lista.forEach(c -> log("Cliente " + c.getId() + " - " + c.getNombre() + " - " + c.getDocumento()));
        }));

        ahorros.addActionListener(e -> ejecutar(() -> {
            @SuppressWarnings("unchecked")
            List<CuentaDeAhorros> lista = cuentaController.listarCuentasAhorros();
            if (lista.isEmpty()) {
                log("No hay cuentas de ahorros");
                return;
            }
            lista.forEach(c -> log("Ahorros " + c.getNumeroCuenta() + " saldo=" + c.getSaldo()));
        }));

        creditos.addActionListener(e -> ejecutar(() -> {
            @SuppressWarnings("unchecked")
            List<CuentaDeCredito> lista = cuentaController.listarCuentasCredito();
            if (lista.isEmpty()) {
                log("No hay cuentas de crédito");
                return;
            }
            lista.forEach(c -> log("Crédito " + c.getNumeroCuenta() + " deuda=" + c.getDeudaActual()));
        }));

        panel.add(clientes);
        panel.add(ahorros);
        panel.add(creditos);
        return panel;
    }

    private void ejecutar(Runnable accion) {
        try {
            accion.run();
        } catch (Exception ex) {
            log("Error: " + ex.getMessage());
        }
    }

    private void log(String mensaje) {
        salida.append(mensaje + "\n");
    }
}
