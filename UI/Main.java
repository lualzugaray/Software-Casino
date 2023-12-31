package UI;
import Logica.*;
import Datos.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Main {

    private static Validacion validacion;
    private static Administrador adm;
    private static Cliente cliente;
    private static EmpleadoCaja empCaja;
    private static Tecnico tecnico;
    private static EmpleadoMaquina empMaquina;

    public static void main(String[] args) {
        validacion = new Validacion();
        adm = new Administrador();
        cliente = new Cliente();
        empCaja = new EmpleadoCaja();
        tecnico = new Tecnico();
        empMaquina = new EmpleadoMaquina();

        mostrarPantallaBienvenida();
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVentanaLogin();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private static void mostrarPantallaBienvenida() {
        JFrame ventana = new JFrame("Casino Jocker");
        ventana.setSize(400, 300);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(201, 183, 109));
        String rutaImagenInicio = "img/casinoInicio.jpg";
        ImageIcon iconoInicio = new ImageIcon(rutaImagenInicio);
        JLabel labelImagen = new JLabel(iconoInicio);
        labelImagen.setBackground(new Color(240, 240, 240));
        labelImagen.setForeground(new Color(0, 0, 0));
        labelImagen.setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
        JLabel labelMensaje = new JLabel("Bienvenido al casino Jocker");
        labelMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        labelMensaje.setVerticalAlignment(SwingConstants.TOP);
        labelMensaje.setLabelFor(ventana);
        labelMensaje.setBackground(new Color(170, 151, 83));
        labelMensaje.setForeground(new Color(0, 0, 0));
        labelMensaje.setFont(new Font("Cambria Math", Font.PLAIN, 27));

        panel.add(labelImagen);
        panel.add(labelMensaje);

        ventana.getContentPane().add(panel);
        ventana.setVisible(true);
    }

    private static void mostrarVentanaLogin() {
        Border bordeComponentes = BorderFactory.createLineBorder(Color.BLACK, 1);
        JFrame ventanaLogin = new JFrame("Inicio de sesión");
        ventanaLogin.setSize(400, 300);
        ventanaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaLogin.setLocationRelativeTo(null);

        JPanel panelLogin = new JPanel(new GridBagLayout());
        panelLogin.setBackground(new Color(201, 183, 109));
        panelLogin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel labelNombreUsuario = new JLabel("Nombre de usuario:");
        labelNombreUsuario.setFont(new Font("Cambria", Font.BOLD, 14));
        JTextField textNombreUsuario = new JTextField(15);
        textNombreUsuario.setBorder(bordeComponentes);

        JLabel labelContrasena = new JLabel("Contraseña:");
        labelContrasena.setFont(new Font("Cambria", Font.BOLD, 14));
        JPasswordField textContrasena = new JPasswordField(15);
        textContrasena.setBorder(bordeComponentes);

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.setPreferredSize(new Dimension(150, 30));
        btnLogin.setBackground(new Color(0, 0, 0));
        btnLogin.setForeground(Color.white);
        btnLogin.setFont(new Font("Cambria", Font.BOLD, 14));
        btnLogin.setBorder(bordeComponentes);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelLogin.add(labelNombreUsuario, gbc);

        gbc.gridx = 1;
        panelLogin.add(textNombreUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelLogin.add(labelContrasena, gbc);

        gbc.gridx = 1;
        panelLogin.add(textContrasena, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelLogin.add(btnLogin, gbc);

        JLabel labelError = new JLabel();
        labelError.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelLogin.add(labelError, gbc);

        btnLogin.addActionListener(new ActionListener() {
            int intentosLogin = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre_usuario = textNombreUsuario.getText();
                String contrasena = new String(textContrasena.getPassword());

                if (validacion.verificarUsuario(nombre_usuario, contrasena)) {
                    if (cliente.login(nombre_usuario, contrasena)) {
                        cliente.mostrarMenu(nombre_usuario);
                        ventanaLogin.dispose();
                    } else if (adm.login(nombre_usuario, contrasena)) {
                        adm.mostrarMenu(nombre_usuario);
                        ventanaLogin.dispose();
                    } else if (empCaja.login(nombre_usuario, contrasena)) {
                        empCaja.mostrarMenu(nombre_usuario);
                        ventanaLogin.dispose();
                    } else if (tecnico.login(nombre_usuario, contrasena)) {
                        tecnico.mostrarMenu(nombre_usuario);
                        ventanaLogin.dispose();
                    } else if (empMaquina.login(nombre_usuario, contrasena)) {
                        empMaquina.mostrarMenu(nombre_usuario);
                        ventanaLogin.dispose();
                    } else {
                        JFrame ventanaError = new JFrame("Error");
                        ventanaError.setSize(300, 150);
                        ventanaError.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        ventanaError.setLocationRelativeTo(null);

                        JPanel panelError = new JPanel(new BorderLayout());

                        JLabel labelMensaje = new JLabel("Tipo de usuario inválido");
                        labelMensaje.setFont(new Font("Arial", Font.PLAIN, 11));
                        labelMensaje.setHorizontalAlignment(JLabel.CENTER);
                        panelError.add(labelMensaje, BorderLayout.CENTER);

                        ventanaError.getContentPane().add(panelError);
                        ventanaError.setVisible(true);
                    }
                } else {
                    intentosLogin++;
                    int intentosRestantes = 3 - intentosLogin;
                    if (intentosRestantes > 0) {
                        JFrame ventanaError = new JFrame("Error");
                        ventanaError.setSize(300, 150);
                        ventanaError.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        ventanaError.setLocationRelativeTo(null);

                        JPanel panelError = new JPanel(new BorderLayout());

                        JLabel labelMensaje = new JLabel("Usuario o contraseña incorrectos. Intentos restantes: " + intentosRestantes);
                        labelMensaje.setFont(new Font("Arial", Font.PLAIN, 11));
                        labelMensaje.setHorizontalAlignment(JLabel.CENTER);
                        panelError.add(labelMensaje, BorderLayout.CENTER);

                        ventanaError.getContentPane().add(panelError);
                        ventanaError.setVisible(true);
                    } else {
                        JFrame ventanaError = new JFrame("Error");
                        ventanaError.setSize(300, 150);
                        ventanaError.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        ventanaError.setLocationRelativeTo(null);

                        JPanel panelError = new JPanel(new BorderLayout());

                        JLabel labelMensaje = new JLabel("Ha excedido el número máximo de intentos. Saliendo del programa.");
                        labelMensaje.setFont(new Font("Arial", Font.PLAIN, 11));
                        labelMensaje.setHorizontalAlignment(JLabel.CENTER);
                        panelError.add(labelMensaje, BorderLayout.CENTER);

                        ventanaError.getContentPane().add(panelError);
                        ventanaError.setVisible(true);
                        System.exit(0);
                    }
                }
            }
        });

        ventanaLogin.getContentPane().add(panelLogin);
        ventanaLogin.setVisible(true);
    }










    
    
    
   



		// Pantalla pre login . Seleccion de tipo de usuario

		String rutaImagenPreLogin = "img/userPreLogin.png";

	}