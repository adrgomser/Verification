package com.psi2.testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.swing.JOptionPane;

import com.psi2.config.Configuration;
import com.psi2.config.GlobalConfiguration;
import com.psi2.utils.ExecutionUtils;
import com.psi2.utils.FileUtils;



public class Testing {

	public static void main(String[] args) {
		// Primero debes correr la clase IntegrityVerifierServer del paquete
		// com.psi2.server
		GlobalConfiguration globalConfig=new GlobalConfiguration("C:\\Users\\ADRIAN\\Desktop\\Universidad\\SSI\\PAI\\PAI2\\config.properties", 
				"C:\\Users\\ADRIAN\\Desktop\\Universidad\\SSI\\PAI\\PAI2\\Logs\\");
		Configuration config=new Configuration(globalConfig);
		ExecutionUtils.setConfiguration(config);
		for(int i=0;i<100;i++){
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
				// habría que calcular el correspondiente MAC con la clave
				// compartida por servidor/cliente
				String macdelMensaje = FileUtils.getMac(mensaje, ExecutionUtils.getConfiguration());//TODO
				//String macdelMensaje="asd";
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
		}

	}
}
