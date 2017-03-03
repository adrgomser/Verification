package com.psi2.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class Configuration {
	private String dir;
	private Map<String, List<String>> properties;
	private Map<String, String> hashes;
	private String algoritmo;
	private String clave;
	private String tiempo;
	private GlobalConfiguration globalConfig;

	/**
	 * This class have all the variables that we will need to run the
	 * application
	 */
	public Configuration(GlobalConfiguration global) {
		super();
		this.dir = global.getConfigurationFile();
		this.globalConfig = global;
		this.hashes = loadHashes();
		loadConfig(dir);
	}

	/**
	 * Load Hashes from the logHash.txt found at log directory
	 */
	public Map<String, String> loadHashes() {
		String logDir = globalConfig.getLogsDirectory();
		File hashLog = new File(logDir + "\\logHash.txt");
		hashes = new HashMap<String, String>();
		if (hashLog.exists()) {
			BufferedReader r;
			try {
				r = new BufferedReader(new FileReader(hashLog));
				String line;
				while ((line = readLine(r)) != null) {
					String linea = line;
					String[] palabras = linea.split(";");
					hashes.put(palabras[0], palabras[1]);
				}
				r.close();
			} catch (IOException e) {
				
			}
		}
		hashLog.delete();
		return hashes;
	}

	/**
	 * Save the logHash.txt when you click on the save button
	 */
	public void saveHashes() {
		String logDir = globalConfig.getLogsDirectory();
		File logs = new File(logDir);
		File hashLog = new File(logDir + "\\logHash.txt");
		if (!logs.exists()) {
			logs.mkdirs();
		}
		if (!hashLog.exists()) {
			try {
				hashLog.createNewFile();
			} catch (IOException e) {
				
			}
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter(hashLog, true);
		} catch (IOException e) {
			
		}
		PrintWriter pw = null;
		if (fw != null) {

			pw = new PrintWriter(fw);
			if (pw != null) {
				for (String s : hashes.keySet()) {
					pw.println((s + ";" + hashes.get(s)));
				}

				pw.flush();

				pw.close();
			}
		}

	}

	
	/**
	 * Read a line
	 */
	public String readLine(BufferedReader br) throws IOException{
		StringBuffer sb=new StringBuffer();
		int intC;
		intC=br.read();
		String line=null;
		do{
			if(intC==-1)
				return null;
			char c=(char) intC;
			if(c=='\n'){
				break;
			}
			if(sb.length()>=1000000){
				throw new IOException("input too long");
			}
			sb.append(c);
		} while(((intC=br.read())!=-1));
		line=sb.toString();
		return line;
		}
	

	/**
	 * Loading all the variables from the configuration file
	 */
	public void loadConfig(String dir) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Properties propiedades = new Properties();

		try {
			File file = new File(dir);
			if (file.exists()) {
				propiedades.load(new FileInputStream(dir));
				this.algoritmo = propiedades.getProperty("algoritmo");
				this.clave = propiedades.getProperty("clave");
				this.tiempo = propiedades.getProperty("tiempo");
				String directories = propiedades.getProperty("directories");
				String[] direc = directories.split(";");
				int length = 1;
				for (String s : direc) {
					List<String> lista = new ArrayList<String>();
					String files = propiedades.getProperty("" + length);
					for (String r : files.split(";")) {
						lista.add(r);
					}
					map.put(s, lista);
					length++;
				}
				String urls = propiedades.getProperty("urls");
				List<String> lista = new ArrayList<String>();
				for (String s : urls.split(";")) {
					lista.add(s);
				}
				map.put("urls", lista);
			} 
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		}
		this.properties = map;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public Map<String, List<String>> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, List<String>> properties) {
		this.properties = properties;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public Map<String, String> getHashes() {
		return hashes;
	}

	public void setHashes(Map<String, String> hashes) {
		this.hashes = hashes;
	}

	public GlobalConfiguration getGlobalConfig() {
		return globalConfig;
	}

	public void setGlobalConfig(GlobalConfiguration globalConfig) {
		this.globalConfig = globalConfig;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}
