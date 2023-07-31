package Datos;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Juego {


    private int idJuego;
    private String nombre;
    private String descripcion;

    private Maquina maquina;
    private  int jugadoresMinimos;
    private int jugadoresMaximos;

    public Juego(){};

    public Juego(int idJuego, String nombre, String descripcion, int jugadoresMinimos, int jugadoresMaximos, Maquina maquina) {
        this.idJuego = idJuego;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.jugadoresMinimos = jugadoresMinimos;
        this.jugadoresMaximos = jugadoresMaximos;
        this.maquina = maquina;
    }


    public Juego(int idJuego, String nombre, String descripcion, int jugadoresMinimos, int jugadoresMaximos) {
        this.idJuego = idJuego;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.jugadoresMinimos = jugadoresMinimos;
        this.jugadoresMaximos = jugadoresMaximos;

    }

    public int getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }

    public String getNombre(int id) {
        String nombre= "";
        Conexion con = new Conexion();
        try {
            Connection conexion = con.conectar();
            String sql = "SELECT nombre FROM juego WHERE id_juego = ?;";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nombre = rs.getString("nombre");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error: " + e.getMessage());
        }

        return nombre;
    }

    public void setNombre(String nombre, int id) {
        Conexion con = new Conexion();
        try {
            Connection conexion = con.conectar();

            String sql = "UPDATE juego SET nombre =  ? WHERE id_juego = ?;";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(2, id);
            stmt.setString(1, nombre);



        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hubo un error: " + e.getMessage());

        }
    }

    public String getDescripcion(int id) {
        String descripcion= "";
        Conexion con = new Conexion();
        try {
            Connection conexion = con.conectar();
            String sql = "SELECT descripcion FROM juego WHERE id_juego = ?;";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                descripcion = rs.getString("descripcion");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error: " + e.getMessage());
        }

        return descripcion;
    }

    public void setDescripcion(String descripcion, int id) {

        Conexion con = new Conexion();
        try {
            Connection conexion = con.conectar();

            String sql = "UPDATE juego SET descripcion =  ? WHERE id_juego = ?;";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(2, id);
            stmt.setString(1, descripcion);



        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hubo un error: " + e.getMessage());

        }
    }

    public int getJugadoresMinimos(int id) {

        int jugadoresMin = 0;
        Conexion con = new Conexion();
        try {
            Connection conexion = con.conectar();
            String sql = "SELECT jugadores_min FROM juego WHERE id_juego = ?;";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                jugadoresMin = rs.getInt("jugadores_min");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error: " + e.getMessage());
        }

        return jugadoresMin;
    }

    public void setJugadoresMinimos(int jugadoresMinimos, int id) {

        Conexion con = new Conexion();
        try {
            Connection conexion = con.conectar();

            String sql = "UPDATE juego SET jugadores_min =  ? WHERE id_juego = ?;";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(2, id);
            stmt.setInt(1, jugadoresMinimos);



        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hubo un error: " + e.getMessage());

        }
    }

    public int getJugadoresMaximos(int id) {
        int jugadoresMax = 0;
        Conexion con = new Conexion();
        try {
            Connection conexion = con.conectar();
            String sql = "SELECT jugadores_max FROM juego WHERE id_juego = ?;";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                jugadoresMax = rs.getInt("jugadores_max");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error: " + e.getMessage());
        }

        return jugadoresMax;
    }


    public void setJugadoresMaximos(int jugadoresMaximos, int id) {

        Conexion con = new Conexion();
        try {
            Connection conexion = con.conectar();

            String sql = "UPDATE juego SET jugadores_max =  ? WHERE id_juego = ?;";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(2, id);
            stmt.setInt(1, jugadoresMaximos);



        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Hubo un error: " + e.getMessage());

        }

    }


    public boolean generarResultado(){boolean rdo; rdo = Math.random() < 0.5; return rdo;}

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public Maquina getMaquina() {

        Maquina maquina = new Maquina();




        return maquina;
    }


}
