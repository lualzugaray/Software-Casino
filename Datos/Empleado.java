package Datos;

import java.util.Date;

public class Empleado extends Usuario{

    private int idEmpleado;

    private String puesto;

public Empleado(){};
    public Empleado(int idUsuario, String nombre, String apellido, Date fecNacimiento, String contrasena, String direccion,
                    String correoElectronico, int idEmpleado, String puesto) {
        super(idUsuario, nombre, apellido, fecNacimiento, contrasena, direccion, correoElectronico);
        this.idEmpleado = idEmpleado;
        this.puesto = puesto;
    }


    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
}
