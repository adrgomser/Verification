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

	private static boolean whiteBoxTesting = false;
	private static boolean grayBoxTesting = true;

	public static void main(String[] args) {
		// Primero debes correr la clase IntegrityVerifierServer del paquete
		// com.psi2.server
		GlobalConfiguration globalConfig = new GlobalConfiguration(
				"C:\\Users\\<User>\\Desktop\\Universidad\\SSI\\PAI\\PAI2\\config.properties",
				"C:\\Users\\<User>\\Desktop\\Universidad\\SSI\\PAI\\PAI2\\Logs\\");
		Configuration config = new Configuration(globalConfig);
		ExecutionUtils.setConfiguration(config);
		ExecutionUtils.setOk(0);
		ExecutionUtils.setErrors(0);
		ExecutionUtils.runUtility();

		if (whiteBoxTesting) {
			System.out.println(
					"I have configured the configuration without any problems. If you want to know about the values, enable Gray Box Testing");
		}

		if (grayBoxTesting) {
			System.out.println(
					"I am going to give you the value of the Configuration, it should be the value of globalConfig variable up this line, the value taken is "
							+ ExecutionUtils.getConfiguration());
		}

		for (int i = 0; i < 100; i++) {
			try {
				SocketFactory socketFactory = (SocketFactory) SocketFactory.getDefault();

				Socket socket = (Socket) socketFactory.createSocket("localhost", 7070);
				// crea un PrintWriter para enviar mensaje/MAC al servidor
				PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				// TODO aqui cambie el nombre
				String cuentaOrigen = JOptionPane.showInputDialog(null, "Introduzca cuenta origen:");
				output.println(cuentaOrigen);

				if (whiteBoxTesting) {
					System.out.println(
							"I have configured the origin account without any problems. If you want to know about the values, enable Gray Box Testing");
				}

				if (grayBoxTesting) {
					System.out.println(
							"I am going to give you the value of the Configuration, it should be the value given in the input, the value taken is "
									+ cuentaOrigen);
				}

				// envio del mensaje al servidor
				// habría que calcular el correspondiente MAC con la clave
				// compartida por servidor/cliente
				String macdelCuentaOrigen = FileUtils.getMac(cuentaOrigen, ExecutionUtils.getConfiguration());// TODO
				// String macdelMensaje="asd";
				output.println(macdelCuentaOrigen);
				if (whiteBoxTesting) {
					System.out.println(
							"I have configured the mac of the origin account without any problems. If you want to know about the values, enable Gray Box Testing");
				}

				if (grayBoxTesting) {
					System.out.println(
							"I am going to give you the value of the mac, it should be the value given in the input, the value taken is "
									+ macdelCuentaOrigen);
				}
				String cuentaDestino = JOptionPane.showInputDialog(null, "Introduzca cuenta destino:");
				output.println(cuentaDestino); // envio del mensaje al servidor
				// habría que calcular el correspondiente MAC con la clave
				// compartida por servidor/cliente
				if (whiteBoxTesting) {
					System.out.println(
							"I have configured the destination account without any problems. If you want to know about the values, enable Gray Box Testing");
				}

				if (grayBoxTesting) {
					System.out.println(
							"I am going to give you the value of the mac, it should be the value given in the input, the value taken is "
									+ cuentaDestino);
				}

				String macdelCuentaDestino = FileUtils.getMac(cuentaDestino, ExecutionUtils.getConfiguration());// TODO
				// String macdelMensaje="asd";
				output.println(macdelCuentaDestino);

				if (whiteBoxTesting) {
					System.out.println(
							"I have configured the mac of the destination account without any problems. If you want to know about the values, enable Gray Box Testing");
				}

				if (grayBoxTesting) {
					System.out.println(
							"I am going to give you the value of the mac, it should be the value given in the input, the value taken is "
									+ macdelCuentaDestino);
				}

				String cantidad = JOptionPane.showInputDialog(null, "Introduzca la cantidad a transferir:");
				output.println(cantidad); // envio del mensaje al servidor
				// habría que calcular el correspondiente MAC con la clave
				// compartida por servidor/cliente
				if (whiteBoxTesting) {
					System.out.println(
							"I have configured the quantity account without any problems. If you want to know about the values, enable Gray Box Testing");
				}

				if (grayBoxTesting) {
					System.out.println(
							"I am going to give you the value of the quantity, it should be the value given in the input, the value taken is "
									+ cantidad);
				}
				String macdelCantidad = FileUtils.getMac(cantidad, ExecutionUtils.getConfiguration());// TODO
				// String macdelMensaje="asd";
				output.println(macdelCantidad);

				if (whiteBoxTesting) {
					System.out.println(
							"I have configured the mac of the quantity without any problems. If you want to know about the values, enable Gray Box Testing");
				}

				if (grayBoxTesting) {
					System.out.println(
							"I am going to give you the value of the mac, it should be the value given in the input, the value taken is "
									+ macdelCantidad);
				}

				Random r = new Random();
				Integer e = r.nextInt(10000);
				String macdelNons = FileUtils.getMac("" + e, ExecutionUtils.getConfiguration());// TODO
				output.println(macdelNons);
				// Suelta el buffer
				output.flush();
				// crea un objeto BufferedReader para leer la respuesta del
				// servidor
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String respuesta = readLine(input); // lee la
																// respuesta del
				// servidor
				JOptionPane.showMessageDialog(null, respuesta); // muestra la
																// respuesta al
																// cliente
				output.close();
				input.close();
				socket.close();

				if (whiteBoxTesting) {
					System.out.println("I have done everything.");
				}

			} // end try
			catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}

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
