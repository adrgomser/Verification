package com.psi2.server;

import java.io.BufferedReader;
import java.io.File;
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
				String cuentaOrigen = readLine(input);
				String macMensajeOrigen = readLine(input);
				String cuentaDestino = readLine(input);
				String macMensajeDestino = readLine(input);
				String cantidad = readLine(input);
				String macMensajeCantidad = readLine(input);
				String nons = readLine(input);

				String macdelMensajeOrigenCalculado = FileUtils.getMac(
					cuentaOrigen, ExecutionUtils.getConfiguration());
				
				String macdelMensajeDestinoCalculado = FileUtils.getMac(
						cuentaDestino, ExecutionUtils.getConfiguration());
				
				String macdelMensajeCantidadCalculado = FileUtils.getMac(
						cantidad, ExecutionUtils.getConfiguration());
				

				String total = macMensajeOrigen + macMensajeDestino
						+ macMensajeCantidad;
				String totalCalculado = macdelMensajeOrigenCalculado
						+ macdelMensajeDestinoCalculado
						+ macdelMensajeCantidadCalculado;
				// String macdelMensajeCalculado="asd";
				// a continuación habría que calcular el macdelMensajeEnviado
				// que podría ser
				// macdelMensajeCalculado y tener en cuenta los nonces para
				// evitar los ataques de replay
				// ………………………………..
				if (total.equals(totalCalculado)) {
					File saveTo = new File(ExecutionUtils.getConfiguration()
							.getGlobalConfig().getLogsDirectory(), "logMac.txt");
					String content = FileUtils.readFile(saveTo);
					if (!content.contains(total + nons)) {
						FileUtils
								.logToFile(" OK --- " + total + nons
										+ " integro",
										ExecutionUtils.getConfiguration());
						FileUtils
						.logFile(total + nons,
								ExecutionUtils.getConfiguration());
						output.println("Mensaje enviado integro ");
						ExecutionUtils.setOk(ExecutionUtils.getOk()+1);
					} else {
						FileUtils.logToFile(" ERROR --- " + total + nons
								+ " duplicado", ExecutionUtils.getConfiguration());
						output.println("Mensaje duplicado");
						ExecutionUtils.setErrors(ExecutionUtils.getErrors()+1);
					}
				} else {
					FileUtils.logToFile(" ERROR --- " + total + nons
							+ " no integro", ExecutionUtils.getConfiguration());
					output.println("Mensaje enviado no integro.");
					ExecutionUtils.setErrors(ExecutionUtils.getErrors()+1);
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
		GlobalConfiguration globalConfig = new GlobalConfiguration(
				"C:\\Users\\ADRIAN\\Desktop\\Universidad\\SSI\\PAI\\PAI2\\config.properties",
				"C:\\Users\\ADRIAN\\Desktop\\Universidad\\SSI\\PAI\\PAI2\\Logs\\");
		Configuration config = new Configuration(globalConfig);
		ExecutionUtils.setConfiguration(config);
		ExecutionUtils.setOk(0);
		ExecutionUtils.setErrors(0);


		IntegrityVerifierServer server = new IntegrityVerifierServer();
		server.runServer();
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
