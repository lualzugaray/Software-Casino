package Datos;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class Maquina {
	
	private int idMaquina;

	private boolean daniada;
	private boolean habilitada;


		
	//constructor
	
	public Maquina() {
		
	}
	
	
	public Maquina(int idMaquina, boolean daniada, boolean habilitada) {
        this.idMaquina = idMaquina;
        this.daniada = daniada;
        this.habilitada = habilitada;
    }
	
	// Metodos
	
	public void reparar() {
		Conexion con = new Conexion();
        try {
            Connection conexion = con.conectar();
            String sql = "UPDATE maquina SET daniada = ? WHERE id_maquina = ?";

            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setBoolean(1, false);
            stmt.setInt(2, this.idMaquina);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Hubo un error al registrar la transaccion: " + e.getMessage());
        }
	}
	
	public void encender() {
        Conexion con = new Conexion();
        try {
            Connection conexion = con.conectar();
            String sql = "UPDATE maquina SET habilitada = ? WHERE id_maquina = ?";

            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setBoolean(1, true);
            stmt.setInt(2, this.idMaquina);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Hubo un error al registrar la transaccion: " + e.getMessage());
        }
	}
	
	public void apagar() {
		Conexion con = new Conexion();
        try {
            Connection conexion = con.conectar();
            String sql = "UPDATE maquina SET habilitada = ? WHERE id_maquina = ?";

            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setBoolean(1, false);
            stmt.setInt(2, this.idMaquina);
            stmt.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Hubo un error al registrar la transaccion: " + e.getMessage());
        }
	}

	
	
	//setters y getters
	
	public int getIdMaquina() {
		return idMaquina;
	}

	public void setIdMaquina(int idMaquina) {
		this.idMaquina = idMaquina;
	}





    public boolean getDaniada(int id) {
        Conexion con = new Conexion();
        boolean daniada = false;

        try {
            Connection conexion = con.conectar();

            String sql = "SELECT daniada FROM maquina WHERE id_maquina = ?";

            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                daniada = rs.getBoolean("daniada");
            }
        } catch (Exception e) {
            System.out.println("Hubo un error: " + e.getMessage());
        }

        return daniada;
    }

	public void setDaniada(int id) {


        Conexion con = new Conexion();
        try {
            Connection conexion = con.conectar();

            String sql = "UPDATE maquina SET daniada = true WHERE id_maquina = ?;";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);



        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hubo un error: " + e.getMessage());

        }
	}

	public boolean getHabilitada(int id) {

        Conexion con = new Conexion();
        boolean habilitada = false;

        try {
            Connection conexion = con.conectar();

            String sql = "SELECT habilitada FROM maquina WHERE id_maquina = ?";

            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                habilitada = rs.getBoolean("habilitada");
            }
        } catch (Exception e) {
            System.out.println("Hubo un error: " + e.getMessage());
        }

        return habilitada;
	}

	public void setHabilitada(int id) {

        Conexion con = new Conexion();
        try {
            Connection conexion = con.conectar();

            String sql = "UPDATE maquina SET habilitada = true WHERE id_maquina = ?;";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);



        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hubo un error: " + e.getMessage());

        }
	}





}
