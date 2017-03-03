package com.psi2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

import com.psi2.config.Configuration;
import com.psi2.config.GlobalConfiguration;
import com.psi2.utils.ExecutionUtils;
import com.psi2.utils.FileUtils;

public class IntegrityVerifierServer {
	private ServerSocket serverSocket;

	// Constructor
	public IntegrityVerifierServer() throws Exception {
		// ServerSocketFactory para construir los ServerSockets
		ServerSocketFactory socketFactory = (ServerSocketFactory) ServerSocketFactory
				.getDefault();
		// creación de un objeto ServerSocket escuchando peticiones en el puerto
		// 7070
		serverSocket = (ServerSocket) socketFactory.createServerSocket(7070);
	}

	// ejecución del servidor para escuchar peticiones de los clientes
	public void runServer() {
		while (true) {
			// espera las peticiones del cliente para comprobar mensaje/MAC
			try {
				System.err.println("Esperando conexiones de clientes...");
				Socket socket = (Socket) serverSocket.accept();
				// abre un BufferedReader para leer los datos del cliente
				BufferedReader input = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));

				// abre un PrintWriter para enviar datos al cliente
				PrintWriter output = new PrintWriter(new OutputStreamWriter(
						socket.getOutputStream()));
				// se lee del cliente el mensaje y el macdelMensajeEnviado
				String mensaje = FileUtils.readLine(input);

				String macMensajeEnviado = FileUtils.readLine(input);
				String macdelMensajeCalculado = FileUtils.getMac(mensaje, ExecutionUtils.getConfiguration());;
				//String macdelMensajeCalculado="asd";
				// a continuación habría que calcular el macdelMensajeEnviado
				// que podría ser
				// macdelMensajeCalculado y tener en cuenta los nonces para
				// evitar los ataques de replay
				// ………………………………..
				if (macMensajeEnviado.equals(macdelMensajeCalculado)) {
					output.println("Mensaje enviado integro ");
				} else {
					System.out.println(macMensajeEnviado+" --- "+(macdelMensajeCalculado));
					output.println("Mensaje enviado no integro.");
				}
				output.close();
				input.close();
				socket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	// ejecucion del servidor
	public static void main(String args[]) throws Exception {
		GlobalConfiguration globalConfig=new GlobalConfiguration("C:\\Users\\ADRIAN\\Desktop\\Universidad\\SSI\\PAI\\PAI2\\config.properties", 
				"C:\\Users\\ADRIAN\\Desktop\\Universidad\\SSI\\PAI\\PAI2\\Logs\\");
		Configuration config=new Configuration(globalConfig);
		ExecutionUtils.setConfiguration(config);

		IntegrityVerifierServer server = new IntegrityVerifierServer();
		server.runServer();
	}

}
