package com.psi2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.swing.JOptionPane;

public class IntegrityVerifierClient {
	public IntegrityVerifierClient() {
		// Constructor que abre una conexi�n Socket para enviar mensaje/MAC al
		// servidor
		try {
			SocketFactory socketFactory = (SocketFactory) SocketFactory
					.getDefault();
			Socket socket = (Socket) socketFactory.createSocket("localhost",
					7070);
			// crea un PrintWriter para enviar mensaje/MAC al servidor
			PrintWriter output = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			//TODO aqui cambie el nombre
			String mensaje = JOptionPane.showInputDialog(null,
					"Introduzca su mensaje:");
			output.println(mensaje); // envio del mensaje al servidor
			// habr�a que calcular el correspondiente MAC con la clave
			// compartida por servidor/cliente
			String macdelMensaje = null;//TODO
			output.println(macdelMensaje);
			output.flush();
			// crea un objeto BufferedReader para leer la respuesta del servidor
			BufferedReader input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String respuesta = input.readLine(); // lee la respuesta del
													// servidor
			JOptionPane.showMessageDialog(null, respuesta); // muestra la
															// respuesta al
															// cliente
			output.close();
			input.close();
			socket.close();
		} // end try
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
		// Salida de la aplicacion
		finally {
			System.exit(0);
		}
	}

	// ejecuci�n del cliente de verificaci�n de la integridad
	public static void main(String args[]) {
		new IntegrityVerifierClient();
	}

}
