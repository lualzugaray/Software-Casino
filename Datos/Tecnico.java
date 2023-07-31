package Datos;

import Interface.Menu;
import Logica.Validacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Tecnico extends Empleado implements Menu {
	
	private int idTecnico;

	public Tecnico(int idUsuario, String nombre, String apellido, Date fecNacimiento, String contrasena, String direccion, String correoElectronico, int idEmpleado, String puesto) {
		super(idUsuario, nombre, apellido, fecNacimiento, contrasena, direccion, correoElectronico, idEmpleado, puesto);
	}

	public Tecnico(){
		
	}
	
	public int getIdTecnico() {
		return idTecnico;
	}

	public void setIdTecnico(int idTecnico) {
		this.idTecnico = idTecnico;
	}
	
	//metodos
	
	public boolean repararMaquina(int id) {
		Conexion con = new Conexion();
		try {
			Connection conexion = con.conectar();

			String sql = "UPDATE maquina SET daniada = false WHERE id_maquina = ?;";
			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setInt(1, id);



		} catch (Exception e) {
			mostrarError("Hubo un error: " + e.getMessage());
			return false;
		}
		return true;
	}


	public boolean encenderMaquina(int id) {
		Conexion con = new Conexion();
		try {
			Connection conexion = con.conectar();

			String sql = "UPDATE maquina SET habilitada = true WHERE id_maquina = ?;";
			PreparedStatement stmt = conexion.prepareStatement(sql);
			stmt.setInt(1, id);



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



		} catch (Exception e) {
			mostrarError("Hubo un error: " + e.getMessage());
			return false;
		}
		return true;
	}

    //MENU

	public boolean login(String nombreUsuario, String contrasena){

		Conexion con = new Conexion();

		try (Connection conexion = con.conectar()) {
			String sql = "SELECT COUNT(*) FROM usuario u " +
					"LEFT JOIN empleado e ON u.id_usuario = e.id_usuario " +
					"WHERE u.nombre_usuario = ? AND u.contrasena = ? " +
					"AND e.tipo_empleado = 2";

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
	    JFrame frame = new JFrame("Empleado Técnico");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(400, 200);
	    frame.setLayout(null);
	    frame.setLocationRelativeTo(null);

	    JLabel lblIdMaquina = new JLabel("ID de la Máquina:");
	    lblIdMaquina.setBounds(20, 20, 120, 30);
	    frame.add(lblIdMaquina);

	    JTextField txtIdMaquina = new JTextField();
	    txtIdMaquina.setBounds(150, 20, 100, 30);
	    frame.add(txtIdMaquina);

	    JButton btnReparar = new JButton("Reparar Máquina");
	    btnReparar.setBounds(20, 60, 150, 30);

	    JButton btnEncender = new JButton("Encender Máquina");
	    btnEncender.setBounds(180, 60, 150, 30);

	    JButton btnApagar = new JButton("Apagar Máquina");
	    btnApagar.setBounds(20, 100, 150, 30);

	    Validacion validacion = new Validacion();

	    btnReparar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int idmaquina = Integer.parseInt(txtIdMaquina.getText());
	            if (validacion.validarExistenciaMaquina(idmaquina)) {
	                repararMaquina(idmaquina);
					mostrarOperacionExitosa("La máquina número " + idmaquina + " ha sido reparada exitosamente");
	            }
				txtIdMaquina.setText("");
	        }
	    });

	    btnEncender.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int idmaquina = Integer.parseInt(txtIdMaquina.getText());
	            if (validacion.validarExistenciaMaquina(idmaquina)) {
	                encenderMaquina(idmaquina);
					mostrarOperacionExitosa("La máquina número " + idmaquina + " ha sido encendida exitosamente");
	            }
				txtIdMaquina.setText("");
	        }
	    });

	    btnApagar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int idmaquina = Integer.parseInt(txtIdMaquina.getText());
	            if (validacion.validarExistenciaMaquina(idmaquina)) {
	                apagarMaquina(idmaquina);
					mostrarOperacionExitosa("La máquina número " + idmaquina + " ha sido apagada exitosamente");
	            }
				txtIdMaquina.setText("");
	        }
	    });


	    txtIdMaquina.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String idMaquinaStr = txtIdMaquina.getText();
	            if (!idMaquinaStr.isEmpty()) {
	                int idMaquina = Integer.parseInt(idMaquinaStr);
	                if (validacion.validarExistenciaMaquina(idMaquina)) {
	                    btnReparar.setEnabled(true);
	                    btnEncender.setEnabled(true);
	                    btnApagar.setEnabled(true);
	                } else {
	                    btnReparar.setEnabled(false);
	                    btnEncender.setEnabled(false);
	                    btnApagar.setEnabled(false);
	                }

	            }


	        }
	    });

	    frame.add(btnReparar);
	    frame.add(btnEncender);
	    frame.add(btnApagar);

	    frame.setVisible(true);
	}
}
