package Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Caja {

    private int idCaja;
    private double saldoActual;
    private EmpleadoCaja empleadoCaja;

    public Caja(){}

    public Caja(int idCaja, double saldoActual, EmpleadoCaja empleadoCaja) {
        this.idCaja = idCaja;
        this.saldoActual = saldoActual;
        this.empleadoCaja = empleadoCaja;
    }
    
	public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }
    public void setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public EmpleadoCaja getEmpleadoCaja() {
        return empleadoCaja;
    }

    public void setEmpleadoCaja(EmpleadoCaja empleadoCaja) {
        this.empleadoCaja = empleadoCaja;
    }


    public double getSaldoActual(int id) {

        Conexion con = new Conexion();
        double dineroDisponible = 0;

        try {
            Connection conexion = con.conectar();

            String sql = "SELECT SUM(total) AS suma_total\n" +
                    "FROM (\n" +
                    "    SELECT SUM(monto) AS total\n" +
                    "    FROM transaccion_caja_empleado\n" +
                    "    WHERE caja = 1\n" +
                    "    UNION ALL\n" +
                    "    SELECT SUM(monto) AS total\n" +
                    "    FROM transaccion_caja_cliente\n" +
                    "    WHERE caja = ?\n" +
                    ") AS subconsulta;";

            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                dineroDisponible = rs.getDouble("suma_total");
                this.setSaldoActual(dineroDisponible);
            }
        } catch (Exception e) {
            System.out.println("Hubo un error: " + e.getMessage());
        }

        return dineroDisponible;
    }




}
