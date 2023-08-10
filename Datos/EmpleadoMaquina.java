package Datos;

import Interface.Menu;
import Logica.Validacion;

import java.sql.SQLException;
import java.util.Date;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmpleadoMaquina extends Empleado implements Menu{

    private int idEmpleadoMaquina;
    private JFrame ventana;
    private JButton botonEncender;
    private JButton botonApagar;
    private JPanel panel;

    public EmpleadoMaquina(int idUsuario, String nombre, String apellido, Date fecNacimiento, String contrasena, String direccion, String correoElectronico, int idEmpleado, String puesto) {
        super(idUsuario, nombre, apellido, fecNacimiento, contrasena, direccion, correoElectronico, idEmpleado, puesto);
    }


    public EmpleadoMaquina() {
		
	}


	public int getIdEmpleadoMaquina() {
        return idEmpleadoMaquina;
    }

    public void setIdEmpleadoMaquina(int idEmpleadoMaquina) {
        this.idEmpleadoMaquina = idEmpleadoMaquina;
    }

    
    // Metodos
    
    public boolean encenderMaquina(int id) {
		Conexion con = new Conexion();
		try {
			Connection conexion = con.conectar();

			String sql = "UPDATE maquina SET habilitada = true WHERE id_maquina = ?;";
			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();


		} catch (Exception e) {
			mostrarError("Hubo un error: " + e.getMessage());
			return false;
		}
		return true;
    }
    
    
    public boolean apagarMaquina(int id) {
        Conexion con = new Conexion();
        try {
            Connection conexion = con.conectar();

			String sql = "UPDATE maquina SET habilitada = false WHERE id_maquina = ?;";
			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();



		} catch (Exception e) {
			mostrarError("Hubo un error: " + e.getMessage());
			return false;
		}
		return true;
    }
    
    
    // Menu

	public boolean login(String nombreUsuario, String contrasena) {
		Conexion con = new Conexion();

		try (Connection conexion = con.conectar()) {
			String sql = "SELECT COUNT(*) FROM usuario u " +
					"LEFT JOIN empleado e ON u.id_usuario = e.id_usuario " +
					"WHERE u.nombre_usuario = ? AND u.contrasena = ? " +
					"AND e.tipo_empleado = 3";

			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setString(1, nombreUsuario);
			stmt.setString(2, contrasena);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);

				if (count > 0) {
					return true; // Existe un usuario con el ID y contraseña proporcionados y es EMaquina
				}
			}
		} catch (SQLException e) {
            mostrarError("Hubo un error al validar el login: " + e.getMessage());
		}

		return false;
	}


    public void mostrarMenu(String id) {
        String[] opcionesEMaquina = {"Encender Maquina", "Apagar Maquina", "Salir"};

        ventana = new JFrame("Empleado Maquina");
        ventana.setSize(400, 300);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(201, 183, 109));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 

        JLabel labelTitulo = new JLabel("Panel de Empleado Maquina");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(labelTitulo, gbc);

        botonEncender = new JButton("Encender Maquina");
        botonEncender.setPreferredSize(new Dimension(200, 40));
        botonEncender.setBackground(new Color(0, 0, 0));
        botonEncender.setForeground(Color.white);
        botonEncender.setFont(new Font("Cambria", Font.BOLD, 14));
        
        botonApagar = new JButton("Apagar Maquina");
        botonApagar.setPreferredSize(new Dimension(200, 40));
        botonApagar.setBackground(new Color(0, 0, 0));
        botonApagar.setForeground(Color.white);
        botonApagar.setFont(new Font("Cambria", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(botonEncender, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(botonApagar, gbc);

        ventana.add(panel);
        ventana.setVisible(true);
        Validacion validacion = new Validacion();

        botonEncender.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Encender máquina");
                JLabel labelMaquina = new JLabel("Ingrese el ID de la Máquina");
                JTextField textField = new JTextField(10);
                JButton button = new JButton("Aceptar");
                
                button.setPreferredSize(new Dimension(150, 30));
                button.setBackground(new Color(0, 0, 0));
                button.setForeground(Color.white);
                button.setFont(new Font("Cambria", Font.BOLD, 14));
                
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setLayout(new GridBagLayout());
                frame.getContentPane().setBackground(new Color(201, 183, 109));

                
                
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String idMaquinaStr = textField.getText();

                        if (idMaquinaStr != null && !idMaquinaStr.isEmpty()) {
                            int idMaquina = Integer.parseInt(idMaquinaStr);
                            if (validacion.validarExistenciaMaquina(idMaquina)) {
                                encenderMaquina(idMaquina);
                                mostrarOperacionExitosa("La máquina número " + idMaquina + " ha sido encendida exitosamente");
                            }
                        }else {
                        	mostrarError("Ingrese un valor numérico válido para el ID.");
                        }

                        frame.dispose();
                    }
                });

                JPanel panel = new JPanel();
                panel.add(labelMaquina);
                panel.add(textField);
                panel.add(button);
                panel.setBackground(new Color(201, 183, 109));

                frame.add(panel);
                frame.setSize(500, 200);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        botonApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Apagar máquina");
                JLabel labelMaquina = new JLabel("Ingrese el ID de la Máquina");
                JTextField textField = new JTextField(10);
                JButton button = new JButton("Aceptar");
                
                button.setPreferredSize(new Dimension(150, 30));
                button.setBackground(new Color(0, 0, 0));
                button.setForeground(Color.white);
                button.setFont(new Font("Cambria", Font.BOLD, 14));
                
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setLayout(new GridBagLayout());
                frame.getContentPane().setBackground(new Color(201, 183, 109));


                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String idMaquinaStr = textField.getText();

                        if (idMaquinaStr != null && !idMaquinaStr.isEmpty()) {
                            int idMaquina = Integer.parseInt(idMaquinaStr);
                            if (validacion.validarExistenciaMaquina(idMaquina)) {
                                apagarMaquina(idMaquina);
                                mostrarOperacionExitosa("La máquina número " + idMaquina + " ha sido apagada exitosamente");
                            }
                        }else {
                        	mostrarError("Ingrese un valor numérico válido para el ID.");
                        }

                        frame.dispose();
                    }
                });

                JPanel panel = new JPanel();
                panel.add(labelMaquina);
                panel.add(textField);
                panel.add(button);
                panel.setBackground(new Color(201, 183, 109));

                frame.add(panel);
                frame.setSize(500, 200);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }}