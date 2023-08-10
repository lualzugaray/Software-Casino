package Datos;

import Interface.Menu;
import Logica.Validacion;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Administrador extends Usuario implements Menu {

	private int idAdministrador;
	private JFrame ventana;
    private JButton botonVerCliente;
    private JButton botonEliminarCliente;
    private JButton botonVerCaja;
    private JButton botonEliminarJuego;
    private JButton botonEditarJuego;
    private JButton botonEditarDatosCliente;
    private JPanel panel;
	
	public Administrador() {
		
	}
	
	public Administrador(int idUsuario, String nombre, String apellido, Date fecNacimiento, String contrasena, String direccion, String correoElectronico, int idAdministrador) {
		super(idUsuario, nombre, apellido, fecNacimiento, contrasena, direccion, correoElectronico);
		this.idAdministrador = idAdministrador;
	}

	// getters y setters

	public int getIdAdministrador() {
		return idAdministrador;
	}

	public void setIdAdministrador(int idAdministrador) {
		this.idAdministrador = idAdministrador;
	}

	/* Metodos de usuario */

	public void editarJuego(String descripcion, int jugadoresMaximos, int idJuego) {
		Conexion con = new Conexion();
		try {
			Connection conexion = con.conectar();
			String sql = "UPDATE juego " +
					"SET descripcion = ?, jugadores_max = ? " +
					"WHERE id_juego = ?";

			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setString(1, descripcion);
			stmt.setInt(2, jugadoresMaximos);
			stmt.setInt(3, idJuego);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Hubo un error: " + e.getMessage());
		}
	}

	// ACA DEBERIAMOS HACER UN COMBOBOX CON LOS CLIENTES Y LISTO

	public String revisarCuentaCliente(int idUsuario) {
	    String resultado = "";
	    Conexion con = new Conexion();

	    try {
	        Connection conexion = con.conectar();
	        String sql = "SELECT u.nombre, u.apellido, u.direccion, u.email, u.fec_nacimiento " +
	                "FROM usuario AS u INNER JOIN cliente AS c " +
	                "ON u.id_usuario = c.id_usuario " +
	                "WHERE u.id_usuario = ?";

	        PreparedStatement stmt = conexion.prepareStatement(sql);
	        stmt.setInt(1, idUsuario);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            String nombre = rs.getString("nombre");
	            String apellido = rs.getString("apellido");
	            String direccion = rs.getString("direccion");
	            String email = rs.getString("email");
	            String fecNacimiento = rs.getString("fec_nacimiento");

	            JFrame frame = new JFrame("Información cuenta cliente");
	            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	            frame.setSize(300, 200);
	            frame.setLocationRelativeTo(null);

	            JPanel panel = new JPanel();
	            panel.setLayout(new GridLayout(5, 2));

	            JLabel labelNombre = new JLabel("Nombre:");
	            JTextField textFieldNombre = new JTextField(nombre);
	            textFieldNombre.setEditable(false);

	            JLabel labelApellido = new JLabel("Apellido:");
	            JTextField textFieldApellido = new JTextField(apellido);
	            textFieldApellido.setEditable(false);

	            JLabel labelDireccion = new JLabel("Dirección:");
	            JTextField textFieldDireccion = new JTextField(direccion);
	            textFieldDireccion.setEditable(false);

	            JLabel labelEmail = new JLabel("Email:");
	            JTextField textFieldEmail = new JTextField(email);
	            textFieldEmail.setEditable(false);

	            JLabel labelFecNacimiento = new JLabel("Fecha de Nacimiento:");
	            JTextField textFieldFecNacimiento = new JTextField(fecNacimiento);
	            textFieldFecNacimiento.setEditable(false);

	            panel.add(labelNombre);
	            panel.add(textFieldNombre);
	            panel.add(labelApellido);
	            panel.add(textFieldApellido);
	            panel.add(labelDireccion);
	            panel.add(textFieldDireccion);
	            panel.add(labelEmail);
	            panel.add(textFieldEmail);
	            panel.add(labelFecNacimiento);
	            panel.add(textFieldFecNacimiento);

	            frame.add(panel);
	            frame.setVisible(true);
	        } else {
	            resultado = "No se encontró información de la cuenta del cliente.";
	            mostrarError("No se encontró información de la cuenta del cliente.");
	        }

	        rs.close();
	        stmt.close();
	        conexion.close();
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Hubo un error: " + e.getMessage());
	        resultado = "Hubo un error al consultar la cuenta del cliente.";
	        mostrarError("Hubo un error al consultar la cuenta del cliente.");
	    }

	    return resultado;
	}

	public void eliminarUsuario(int idUsuario) {
		Conexion con = new Conexion();
		try {
			Connection conexion = con.conectar();
			String sql = "DELETE FROM usuario WHERE id_usuario = ?";

			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setLong(1, idUsuario);

			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Hubo un error: " + e.getMessage());
		}
	}

	public boolean eliminarJuego(int idJuego) {

		Conexion con = new Conexion();
		try {
			Connection conexion = con.conectar();
			String sql = "DELETE FROM juego WHERE id_juego = ?";

			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setLong(1, idJuego);

			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println("Hubo un error: " + e.getMessage());
			return false;
		}
	}

	public boolean actualizarCliente(String email, String direccion, int id) {
	    Conexion con = new Conexion();
	    try {
	        Connection conexion = con.conectar();
	        String sql = "UPDATE usuario AS u INNER JOIN cliente AS c " +
	                     "ON u.id_usuario = c.id_usuario " +
	                     "SET u.email = ?, u.direccion = ? " +
	                     "WHERE u.id_usuario = ?";

	        PreparedStatement stmt = conexion.prepareStatement(sql);
	        stmt.setString(1, email);
	        stmt.setString(2, direccion);
	        stmt.setInt(3, id);
	        stmt.executeUpdate();
	        return true;
	    } catch (Exception e) {
	        System.out.println("Hubo un error: " + e.getMessage());
	        return false;
	    }
	}
	
	public void verCaja(int idCaja) {
	    Conexion con = new Conexion();
	    double sumaMontos = 0.0;

	    try {
	        Connection conexion = con.conectar();
	        String sql = "SELECT c.id_caja, SUM(tcc.monto) AS suma_montos " +
	                     "FROM caja AS c " +
	                     "LEFT JOIN transaccion_caja_cliente AS tcc ON c.id_caja = tcc.caja " +
	                     "LEFT JOIN transaccion_caja_empleado AS tce ON c.id_caja = tce.caja " +
	                     "WHERE c.id_caja = ? " +
	                     "GROUP BY c.id_caja";

	        PreparedStatement stmt = conexion.prepareStatement(sql);
	        stmt.setInt(1, idCaja);

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            sumaMontos = rs.getDouble("suma_montos");
	        }

	        rs.close();
	        stmt.close();
	        conexion.close();
	    } catch (Exception e) {
	        mostrarError("Hubo un error al consultar la información de la caja.");
	        return;
	    }

	    JFrame infoFrame = new JFrame("Información de la Caja");
	    infoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    infoFrame.setSize(300, 100);
	    infoFrame.setLayout(new GridBagLayout());
	    infoFrame.getContentPane().setBackground(new Color(201, 183, 109));

	    JLabel labelInfo = new JLabel("La caja " + idCaja + " tiene un saldo de " + sumaMontos);
	    labelInfo.setFont(new Font("Cambria", Font.BOLD, 14));

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10, 10, 10, 10);
	    gbc.anchor = GridBagConstraints.CENTER;

	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 2;
	    infoFrame.add(labelInfo, gbc);

	    infoFrame.setLocationRelativeTo(null);
	    infoFrame.setVisible(true);
	}
	public boolean login(String nombreUsuario, String contrasena){

		Conexion con = new Conexion();

		try (Connection conexion = con.conectar()) {
			String sql = "SELECT COUNT(*) FROM usuario u " +
					"LEFT JOIN empleado e ON u.id_usuario = e.id_usuario " +
					"WHERE u.nombre_usuario = ? AND u.contrasena = ? " +
					"AND e.tipo_empleado = 4";

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
			JOptionPane.showMessageDialog(null,
					"Hubo un error al validar el login: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		return false;


	}


	public void mostrarMenu(String idAdm) {
		String[] opcionesAdministrador = {"Ver cliente", "Eliminar cliente", "Ver caja", "Eliminar juego",
                "Editar juego", "Editar datos cliente", "Salir"};

	    ventana = new JFrame("Administrador");
	    ventana.setSize(400, 300);
	    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    ventana.setLocationRelativeTo(null);
	    ventana.setBackground(new Color(201, 183, 109));

	    panel = new JPanel(new BorderLayout());
	    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

	    JLabel labelTitulo = new JLabel("Panel de Administrador");
	    labelTitulo.setFont(new Font("Cambria", Font.BOLD, 16));
	    labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
	    panel.add(labelTitulo, BorderLayout.NORTH);

	    JPanel panelBotones = new JPanel(new GridBagLayout());

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(5, 5, 5, 5); 

	    botonVerCliente = new JButton("Ver cliente");
	    botonVerCliente.setPreferredSize(new Dimension(200, 40));
	    botonVerCliente.setBackground(new Color(0, 0, 0));
	    botonVerCliente.setForeground(Color.white);
	    botonVerCliente.setFont(new Font("Cambria", Font.BOLD, 14));
        
	    botonEliminarCliente = new JButton("Eliminar cliente");
	    botonEliminarCliente.setPreferredSize(new Dimension(200, 40));
	    botonEliminarCliente.setBackground(new Color(0, 0, 0));
	    botonEliminarCliente.setForeground(Color.white);
	    botonEliminarCliente.setFont(new Font("Cambria", Font.BOLD, 14));
	    
	    botonVerCaja = new JButton("Ver caja");
	    botonVerCaja.setPreferredSize(new Dimension(200, 40));
	    botonVerCaja.setBackground(new Color(0, 0, 0));
	    botonVerCaja.setForeground(Color.white);
	    botonVerCaja.setFont(new Font("Cambria", Font.BOLD, 14));
	    
	    botonEliminarJuego = new JButton("Eliminar juego");
	    botonEliminarJuego.setPreferredSize(new Dimension(200, 40));
	    botonEliminarJuego.setBackground(new Color(0, 0, 0));
	    botonEliminarJuego.setForeground(Color.white);
	    botonEliminarJuego.setFont(new Font("Cambria", Font.BOLD, 14));
	    
	    botonEditarJuego = new JButton("Editar juego");
	    botonEditarJuego.setPreferredSize(new Dimension(200, 40));
	    botonEditarJuego.setBackground(new Color(0, 0, 0));
	    botonEditarJuego.setForeground(Color.white);
	    botonEditarJuego.setFont(new Font("Cambria", Font.BOLD, 14));
	    
	    botonEditarDatosCliente = new JButton("Editar datos cliente");
	    botonEditarDatosCliente.setPreferredSize(new Dimension(200, 40));
	    botonEditarDatosCliente.setBackground(new Color(0, 0, 0));
	    botonEditarDatosCliente.setForeground(Color.white);
	    botonEditarDatosCliente.setFont(new Font("Cambria", Font.BOLD, 14));
	    

	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    panelBotones.add(botonVerCliente, gbc);

	    gbc.gridx = 0;
	    gbc.gridy = 1;
	    panelBotones.add(botonEliminarCliente, gbc);

	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    panelBotones.add(botonVerCaja, gbc);

	    gbc.gridx = 1;
	    gbc.gridy = 0;
	    panelBotones.add(botonEliminarJuego, gbc);

	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    panelBotones.add(botonEditarJuego, gbc);

	    gbc.gridx = 1;
	    gbc.gridy = 2;
	    panelBotones.add(botonEditarDatosCliente, gbc);

	    panel.add(panelBotones, BorderLayout.CENTER);
	    panelBotones.setBackground(new Color(201, 183, 109));
	    panel.setBackground(new Color(201, 183, 109));

	    ventana.add(panel);
	    ventana.setVisible(true);
	    
        Validacion validacion = new Validacion();

        botonVerCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new JFrame("Ingreso del ID del Cliente");
                inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                inputFrame.setSize(350, 200);
                inputFrame.setLayout(new GridBagLayout());
                inputFrame.getContentPane().setBackground(new Color(201, 183, 109));

                JLabel labelIdCliente = new JLabel("ID del Cliente:");
                JTextField textFieldIdCliente = new JTextField(15); // Aumentamos el ancho del campo de texto
                JButton btnAceptar = new JButton("Aceptar");
                btnAceptar.setPreferredSize(new Dimension(150, 30));
                btnAceptar.setBackground(new Color(0, 0, 0));
                btnAceptar.setForeground(Color.white);
                btnAceptar.setFont(new Font("Cambria", Font.BOLD, 14));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.anchor = GridBagConstraints.WEST;

                gbc.gridx = 0;
                gbc.gridy = 0;
                inputFrame.add(labelIdCliente, gbc);

                gbc.gridy = 1;
                inputFrame.add(textFieldIdCliente, gbc);

                gbc.gridy = 2;
                gbc.gridwidth = 2; // Hacemos que el botón abarque dos columnas
                inputFrame.add(btnAceptar, gbc);

                btnAceptar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String inputIdCliente = textFieldIdCliente.getText();
                        if (!inputIdCliente.isEmpty()) {
                            try {
                                int idCliente = Integer.parseInt(inputIdCliente);
                                if (validacion.validarExistenciaCliente(idCliente)) {
                                    revisarCuentaCliente(idCliente);
                                } else {
                                    mostrarError("No se encontró información de la cuenta del cliente.");
                                }
                            } catch (NumberFormatException ex) {
                                mostrarError("Ingrese un valor numérico válido para el ID del cliente.");
                            }
                        } else {
                            mostrarError("Por favor, ingrese el ID del cliente.");
                        }

                        inputFrame.dispose();
                    }
                });

                inputFrame.setLocationRelativeTo(null);
                inputFrame.setVisible(true);
            }
        });
        
        botonEliminarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new JFrame("Eliminar Cliente");
                inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                inputFrame.setSize(300, 200);
                inputFrame.setLayout(new GridBagLayout());
                inputFrame.getContentPane().setBackground(new Color(201, 183, 109));

                JLabel labelIdCliente = new JLabel("ID del Cliente:");
                labelIdCliente.setFont(new Font("Cambria", Font.BOLD, 14));
                JTextField textFieldIdCliente = new JTextField(10);

                JButton btnAceptar = new JButton("Aceptar");
                btnAceptar.setPreferredSize(new Dimension(100, 30));
                btnAceptar.setBackground(new Color(0, 0, 0));
                btnAceptar.setForeground(Color.white);
                btnAceptar.setFont(new Font("Cambria", Font.BOLD, 14));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.anchor = GridBagConstraints.CENTER;

                gbc.gridx = 0;
                gbc.gridy = 0;
                inputFrame.add(labelIdCliente, gbc);

                gbc.gridx = 1;
                inputFrame.add(textFieldIdCliente, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                inputFrame.add(btnAceptar, gbc);

                btnAceptar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String inputIdCliente = textFieldIdCliente.getText();
                        if (!inputIdCliente.isEmpty()) {
                            try {
                                int idCliente = Integer.parseInt(inputIdCliente);
                                if (validacion.validarExistenciaCliente(idCliente)) {
                                    eliminarUsuario(idCliente);
                                    mostrarOperacionExitosa("Se eliminó con éxito el cliente con ID: " + idCliente);
                                } else {
                                    mostrarError("No se encontró el cliente con el ID especificado.");
                                }
                            } catch (NumberFormatException ex) {
                                mostrarError("Ingrese un valor numérico válido para el ID del cliente.");
                            }
                        } else {
                            mostrarError("Por favor, ingrese el ID del cliente.");
                        }

                        inputFrame.dispose();
                    }
                });

                inputFrame.setLocationRelativeTo(null);
                inputFrame.setVisible(true);
            }
        });

        botonVerCaja.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new JFrame("Ver Caja");
                inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                inputFrame.setSize(300, 200);
                inputFrame.setLayout(new GridBagLayout());
                inputFrame.getContentPane().setBackground(new Color(201, 183, 109));

                JLabel labelIdCaja = new JLabel("ID de la Caja:");
                labelIdCaja.setFont(new Font("Cambria", Font.BOLD, 14));
                JTextField textFieldIdCaja = new JTextField(10);

                JButton btnAceptar = new JButton("Aceptar");
                btnAceptar.setPreferredSize(new Dimension(100, 30));
                btnAceptar.setBackground(new Color(0, 0, 0));
                btnAceptar.setForeground(Color.white);
                btnAceptar.setFont(new Font("Cambria", Font.BOLD, 14));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.anchor = GridBagConstraints.CENTER;

                gbc.gridx = 0;
                gbc.gridy = 0;
                inputFrame.add(labelIdCaja, gbc);

                gbc.gridx = 1;
                inputFrame.add(textFieldIdCaja, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                inputFrame.add(btnAceptar, gbc);

                btnAceptar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String inputIdCaja = textFieldIdCaja.getText();
                        if (!inputIdCaja.isEmpty()) {
                            try {
                                int idCaja = Integer.parseInt(inputIdCaja);
                                if (validacion.validarVerCaja(idCaja)) {
                                    verCaja(idCaja);
                                } else {
                                    mostrarError("No se encontró la caja con el ID especificado.");
                                }
                            } catch (NumberFormatException ex) {
                                mostrarError("Ingrese un valor numérico válido para el ID de la caja.");
                            }
                        } else {
                            mostrarError("Por favor, ingrese el ID de la caja.");
                        }

                        inputFrame.dispose();
                    }
                });

                inputFrame.setLocationRelativeTo(null);
                inputFrame.setVisible(true);
            }
        });

        botonEliminarJuego.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new JFrame("Eliminar Juego");
                inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                inputFrame.setSize(300, 150);
                inputFrame.setLayout(new GridBagLayout());
                inputFrame.getContentPane().setBackground(new Color(201, 183, 109));

                JLabel labelIdJuego = new JLabel("ID del Juego:");
                labelIdJuego.setFont(new Font("Cambria", Font.BOLD, 14));
                JTextField textFieldIdJuego = new JTextField(10);
                textFieldIdJuego.setFont(new Font("Cambria", Font.PLAIN, 14));
                JButton btnAceptar = new JButton("Aceptar");
                btnAceptar.setPreferredSize(new Dimension(100, 30));
                btnAceptar.setBackground(new Color(0, 0, 0));
                btnAceptar.setForeground(Color.white);
                btnAceptar.setFont(new Font("Cambria", Font.BOLD, 14));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.anchor = GridBagConstraints.WEST;

                gbc.gridx = 0;
                gbc.gridy = 0;
                inputFrame.add(labelIdJuego, gbc);

                gbc.gridx = 1;
                gbc.gridy = 0;
                inputFrame.add(textFieldIdJuego, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                inputFrame.add(btnAceptar, gbc);

                btnAceptar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String inputIdJuego = textFieldIdJuego.getText();
                        if (!inputIdJuego.isEmpty()) {
                            try {
                                int idJuego = Integer.parseInt(inputIdJuego);
                                if (validacion.validarEliminarJuego(idJuego)) {
                                    eliminarJuego(idJuego);
                                    mostrarOperacionExitosa("Se eliminó con éxito el juego con ID: " + idJuego);
                                } else {
                                    mostrarError("No se encontró el juego con el ID especificado.");
                                }
                            } catch (NumberFormatException ex) {
                                mostrarError("Ingrese un valor numérico válido para el ID del juego.");
                            } 
                        } else {
                        	mostrarError("Por favor, ingrese el ID del juego.");
                        }                        
                        inputFrame.dispose();
                    }
                });

                inputFrame.setLocationRelativeTo(null);
                inputFrame.setVisible(true);
            }
        });
        
        botonEditarJuego.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new JFrame("Edición de juego");
                inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                inputFrame.setSize(400, 300);
                inputFrame.getContentPane().setBackground(new Color(201, 183, 109));

                JPanel panelEditarJuego = new JPanel();
                panelEditarJuego.setLayout(new GridLayout(5, 2, 10, 10));
                panelEditarJuego.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                panelEditarJuego.setBackground(new Color(201, 183, 109));

                JLabel labelIdJuego = new JLabel("ID del Juego:");
                JTextField textFieldIdJuego = new JTextField(10);

                JLabel labelDescripcion = new JLabel("Descripción:");
                JTextField textFieldDescripcion = new JTextField(20);

                JLabel labelJugadoresMinimos = new JLabel("Jugadores mínimos:");
                JTextField textFieldJugadoresMinimos = new JTextField(10);

                JLabel labelJugadoresMaximos = new JLabel("Jugadores máximos:");
                JTextField textFieldJugadoresMaximos = new JTextField(10);

                JButton btnAceptar = new JButton("Aceptar");
                btnAceptar.setForeground(Color.white);
                btnAceptar.setBackground(new Color(0, 0, 0));
                btnAceptar.setFont(new Font("Cambria", Font.BOLD, 14));

                panelEditarJuego.add(labelIdJuego);
                panelEditarJuego.add(textFieldIdJuego);
                panelEditarJuego.add(labelDescripcion);
                panelEditarJuego.add(textFieldDescripcion);
                panelEditarJuego.add(labelJugadoresMinimos);
                panelEditarJuego.add(textFieldJugadoresMinimos);
                panelEditarJuego.add(labelJugadoresMaximos);
                panelEditarJuego.add(textFieldJugadoresMaximos);
                panelEditarJuego.add(btnAceptar);

                btnAceptar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String inputIdJuego = textFieldIdJuego.getText();
                        String descripcion = textFieldDescripcion.getText();
                        String inputJugadoresMinimos = textFieldJugadoresMinimos.getText();
                        String inputJugadoresMaximos = textFieldJugadoresMaximos.getText();

                        if (!inputIdJuego.isEmpty() && !descripcion.isEmpty() && !inputJugadoresMinimos.isEmpty() && !inputJugadoresMaximos.isEmpty()) {
                            try {
                                int idJuego = Integer.parseInt(inputIdJuego);
                                int jugadoresMinimos = Integer.parseInt(inputJugadoresMinimos);
                                int jugadoresMaximos = Integer.parseInt(inputJugadoresMaximos);

                                if (validacion.validarEditarJuego(descripcion, jugadoresMinimos, jugadoresMaximos)) {
                                    editarJuego(descripcion, jugadoresMaximos, idJuego);
                                    mostrarOperacionExitosa("Se editó con éxito el juego con ID: " + idJuego);
                                    inputFrame.dispose();
                                } else {
                                    mostrarError("Ingrese valores válidos");
                                }
                            } catch (NumberFormatException ex) {
                                mostrarError("Ingrese valores válidos");
                            }
                        } else {
                            mostrarError("Ingrese valores válidos");
                        }
                    }
                });
                inputFrame.add(panelEditarJuego);
                inputFrame.setLocationRelativeTo(null);
                inputFrame.setVisible(true);
            }
        });


        botonEditarDatosCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new JFrame("Actualización de cliente");
                inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                inputFrame.setSize(400, 250);
                inputFrame.setLayout(new GridBagLayout());
                inputFrame.getContentPane().setBackground(new Color(201, 183, 109));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(5, 10, 5, 10);

                JLabel labelId = new JLabel("ID del cliente:");
                JTextField textFieldId = new JTextField(20); // Ajusta el ancho del campo

                JLabel labelEmail = new JLabel("Nuevo email:");
                JTextField textFieldEmail = new JTextField(20); // Ajusta el ancho del campo

                JLabel labelDireccion = new JLabel("Nueva dirección:");
                JTextField textFieldDireccion = new JTextField(20); // Ajusta el ancho del campo

                JButton btnAceptar = new JButton("Aceptar");
                btnAceptar.setForeground(Color.white);
                btnAceptar.setBackground(new Color(0, 0, 0));
                btnAceptar.setFont(new Font("Cambria", Font.BOLD, 14));

                inputFrame.add(labelId, gbc);
                gbc.gridx++;
                inputFrame.add(textFieldId, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                inputFrame.add(labelEmail, gbc);
                gbc.gridx++;
                inputFrame.add(textFieldEmail, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                inputFrame.add(labelDireccion, gbc);
                gbc.gridx++;
                inputFrame.add(textFieldDireccion, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                gbc.gridwidth = 2; // Para que el botón abarque dos columnas
                inputFrame.add(btnAceptar, gbc);
                
                btnAceptar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	String inputId = textFieldId.getText();
                        String nuevoEmail = textFieldEmail.getText();
                        String nuevaDireccion = textFieldDireccion.getText();

                        if (!inputId.isEmpty() && !nuevoEmail.isEmpty() && !nuevaDireccion.isEmpty()) {
                            try {
                                int idCliente = Integer.parseInt(inputId);
                                if (validacion.validarActualizarCliente(nuevoEmail, nuevaDireccion, idCliente)) {
                                	mostrarOperacionExitosa("Se editó con éxito el cliente con ID: " + idCliente);
                                    actualizarCliente(nuevoEmail, nuevaDireccion, idCliente);
                                } else {
                                    mostrarError("No se pudo actualizar el cliente.");
                                }
                            } catch (NumberFormatException ex) {
                                mostrarError("Ingrese un valor numérico válido para el ID del cliente.");
                            }
                        } else {
                            mostrarError("Por favor, complete todos los campos.");
                        }

                        inputFrame.dispose();
                    }
                });
                inputFrame.setLocationRelativeTo(null);
                inputFrame.setVisible(true);
            }
        });
	}

}
