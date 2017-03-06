package com.psi2.client;

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

public class IntegrityVerifierClient {
	public IntegrityVerifierClient() {
		// Constructor que abre una conexión Socket para enviar mensaje/MAC al
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
			Integer i=r.nextInt(10000);
			String macdelNons = FileUtils.getMac(""+i, ExecutionUtils.getConfiguration());//TODO
			output.println(macdelNons);
			//Suelta el buffer
			output.flush();
			// crea un objeto BufferedReader para leer la respuesta del servidor
			BufferedReader input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String respuesta = readLine(input); // lee la respuesta del
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

	// ejecución del cliente de verificación de la integridad
	public static void main(String args[]) {
		GlobalConfiguration globalConfig=new GlobalConfiguration("C:\\Users\\ADRIAN\\Desktop\\Universidad\\SSI\\PAI\\PAI2\\config.properties", 
				"C:\\Users\\ADRIAN\\Desktop\\Universidad\\SSI\\PAI\\PAI2\\Logs\\");
		Configuration config=new Configuration(globalConfig);
		ExecutionUtils.setConfiguration(config);

		new IntegrityVerifierClient();
	}
	/**
	 * Read a line
	 */
	public static String readLine(BufferedReader br) throws IOException {
		StringBuffer sb = new StringBuffer();
		int intC;
		intC = br.read();
		String line = null;
		do {
			if (intC == -1)
				return null;
			char c = (char) intC;
			if (c == '\n') {
				break;
			}
			if (sb.length() >= 1000000) {
				throw new IOException("input too long");
			}
			sb.append(c);
		} while (((intC = br.read()) != -1));
		line = sb.toString();
		line = line.replaceAll("\r", "");
		return line;
	}

	

}
