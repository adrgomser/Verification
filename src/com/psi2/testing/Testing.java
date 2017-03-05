package com.psi2.testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

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
				String cuentaOrigen = JOptionPane.showInputDialog(null,
						"Introduzca cuenta origen:");
				output.println(cuentaOrigen); // envio del mensaje al servidor
				// habría que calcular el correspondiente MAC con la clave
				// compartida por servidor/cliente
				String macdelCuentaOrigen = FileUtils.getMac(cuentaOrigen, ExecutionUtils.getConfiguration());//TODO
				//String macdelMensaje="asd";
				output.println(macdelCuentaOrigen);
				
				
				String cuentaDestino = JOptionPane.showInputDialog(null,
						"Introduzca cuenta destino:");
				output.println(cuentaDestino); // envio del mensaje al servidor
				// habría que calcular el correspondiente MAC con la clave
				// compartida por servidor/cliente
				String macdelCuentaDestino = FileUtils.getMac(cuentaDestino, ExecutionUtils.getConfiguration());//TODO
				//String macdelMensaje="asd";
				output.println(macdelCuentaDestino);
				
				
				
				String cantidad = JOptionPane.showInputDialog(null,
						"Introduzca la cantidad a transferir:");
				output.println(cantidad); // envio del mensaje al servidor
				// habría que calcular el correspondiente MAC con la clave
				// compartida por servidor/cliente
				String macdelCantidad = FileUtils.getMac(cantidad, ExecutionUtils.getConfiguration());//TODO
				//String macdelMensaje="asd";
				output.println(macdelCantidad);
				Random r=new Random();
				Integer e=r.nextInt(10000);
				String macdelNons = FileUtils.getMac(""+e, ExecutionUtils.getConfiguration());//TODO
				output.println(macdelNons);
				//Suelta el buffer
				output.flush();
				// crea un objeto BufferedReader para leer la respuesta del servidor
				BufferedReader input = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				String respuesta = FileUtils.readLine(input); // lee la respuesta del
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
