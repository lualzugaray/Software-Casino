
package Logica;

import Datos.*;

import javax.swing.*;
import java.awt.*;

public class Validacion {

    public void mostrarError(String mensaje) {
        JFrame errorFrame = new JFrame("Error");
        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorFrame.setSize(600, 100);
        errorFrame.setLayout(new FlowLayout());
        
        JLabel labelError = new JLabel(mensaje);
        labelError.setForeground(Color.RED);  // Establece el color del texto en rojo
        labelError.setFont(new Font("Cambria", Font.BOLD, 12));  // Establece la fuente del texto
        
        errorFrame.add(labelError);
        errorFrame.setVisible(true);
        errorFrame.setLocationRelativeTo(null);
    }
    
//VALIDACIONES LOGIN


	public boolean verificarUsuario(String idUsuario, String contrasena) {

		int longitudContrasena = contrasena.length();
		int longitudID= idUsuario.length();

		if (longitudID < 0 || longitudID > 99 || idUsuario.isEmpty()) {


			mostrarError("El usuario debe ser un numero entre 1 y 99");
			return false; // El ID de usuario debe estar entre 0 y 99
		}
		else if(longitudContrasena < 6 || longitudContrasena > 15){
			mostrarError("La contraseña debe tener entre 6 y 15 caracteres");
			return false;
		}
		else {
			return true;
		}
	}



//VALIDACIONES CLIENTE



	public boolean validarCargaDinero(int idCliente, double monto){

			if(idCliente>0 && idCliente<100 && monto>0){
				return true;
			}
			else{
				mostrarError("No se ha completado la operación");
				return false;
			}

	}

	public boolean validarRetiroDinero(double monto) {


		if (monto <0 || monto == 0) {
			mostrarError("No se ha completado la operación");
			return false;
		}

		return true;
	}

	public boolean validarJugar(double monto){
			if(monto<=0){
				return false;}
			else{
				return true;
			}
	}


	
//VALIDACIONES ADMINISTRADOR

	public boolean validarVerCaja(int idCaja){

			if(idCaja<1 || idCaja>3){
				mostrarError("La caja " + idCaja + " no existe o no tiene transacciones.");
				return false;
			}
			else{

				return true;
			}
	}

	public boolean validarEditarJuego (String descripcion, int jugadoresMinimos, int jugadoresMaximos) {


		if(jugadoresMinimos < 1 || jugadoresMaximos > 11) {
			mostrarError("La cantidad de jugadores mínimos debe ser mayor o igual a 1 y menor o igual a 10");
            return false;
		}else if (descripcion.length() < 10 || descripcion.length() > 100){
			mostrarError("La descripcion debe tener entre 10 y 100 caracteres");
			 return false;
		}else {
			return true;
		}

	}



	public boolean validarEliminarJuego(int idJuego){

			if(idJuego<0 || idJuego>4){
				return false;
			}
			else {
				return true;
			}

	}

	public boolean validarActualizarCliente (String email, String direccion, int idCliente) {

		if(idCliente<= 0 || idCliente >99 || email.equals("") || direccion.equals("")
			||	email.length() < 10 || email.length() > 30 || direccion.length() < 10 || direccion.length() > 30){

			return false;
		}
		else{
			return true;
		}

		}



	public boolean validarExistenciaCliente(int idUsuario) {

		if(idUsuario < 11 || idUsuario >16 ){

			mostrarError("El ID del Usuario debe ser un numero entre 1 y 99");
			return false;
		}
		else{
			return true;
	}
		}



//VALIDACIONES EMPLEADO CAJA 

// FALTA CORREGIR NOMBRE DE VARIABLES EN EL METODO

	public boolean validarAgregarDinero (double montoAAgregar, int idCaja) {

		if(montoAAgregar > 0 && (idCaja >0 && idCaja<4)) {
            return true;
		}else {
			mostrarError("El monto debe ser un valor mayor a 0 y el numero de la caja debe ser un numero entero entre 1 y 4");
            return false;
		}	
	}
	

// Validaciones Tecnico

	
	
// Validaciones Empleado Maquina


	public boolean validarExistenciaMaquina(int idMaquina) {
	    if (idMaquina < 1 || idMaquina > 3) {
	        mostrarError("La máquina número " + idMaquina + " no existe");
	        return false;
	    } else {
	        return true;
	    }
	}
    
}
