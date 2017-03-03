package com.psi2.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


import com.psi2.config.Configuration;

public class FileUtils {
	
	/**
	 * Method to get the mac of a message
	 */
	public static String getMac(String mensaje,Configuration config){
		Mac mac1 = null;
		try {
			mac1 = Mac.getInstance(config.getAlgoritmo());
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(config.getAlgoritmo());
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] b = null;
		SecretKey clave = new SecretKeySpec(config.getClave().getBytes(), 0, config.getClave().getBytes().length, config.getAlgoritmo());
			try {
				mac1.init(clave);
				mac1.update(mensaje.getBytes());
				b = mac1.doFinal();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Conversion.byteArrayToHexString(b);

		
	}

	/**
	 * Method to read a file
	 */
	public static String readFile(String directory) {
		File file = new File(directory);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		byte[] data = new byte[(int) file.length()];
		try {
			if (fis != null)
				fis.read(data);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			if (fis != null)
				fis.close();
		} catch (IOException e) {
			
		}

		String str = null;
		try {
			str = new String(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		return str;
	}

	/**
	 * Method to read a file
	 */
	public static String readFile(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		byte[] data = new byte[(int) file.length()];
		try {
			if (fis != null)
				fis.read(data);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			if (fis != null)
				fis.close();
		} catch (IOException e) {
			
		}

		String str = null;
		try {
			str = new String(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		return str;
	}

	/**
	 * Method to hash the content with salt
	 */
	public static String hashContent(String content, Configuration config) {
		byte[] convertme = null;
		try {
			content = content + "A>0@3%`sx4bvP35YuAe|";
			convertme = content.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(config.getAlgoritmo());
		} catch (NoSuchAlgorithmException e) {
			
		}
		String res = "";
		if (convertme != null) {
			res = byteArrayToHexString(md.digest(convertme));
		}
		return res;

	}

	public static String byteArrayToHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	/**
	 * Method to log a string
	 */
	public static void logToFile(String message, Configuration config)

	{

		try {
			File dataFolder = new File(config.getGlobalConfig()
					.getLogsDirectory());
			if (!dataFolder.exists()) {
				dataFolder.mkdirs();
			}
			Date now2 = new Date();
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			boolean res = true;
			File saveTo = new File(config.getGlobalConfig().getLogsDirectory(),
					formato.format(now2) + "-log.txt");
			if (!saveTo.exists()) {
				saveTo.createNewFile();
				res = false;
			}

			FileWriter fw = new FileWriter(saveTo, true);

			PrintWriter pw = new PrintWriter(fw);
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat(
					"dd-MM-yyyy HH:mm:ss");
			if (!res) {
				pw.println("################################");
				pw.println("#                              #");
				pw.println("#          REVISION            #");
				pw.println("#                              #");
				pw.println("################################");
			}
			pw.println(format.format(now) + " : " + message);

			pw.flush();

			pw.close();

		} catch (IOException e) {

			

		}

	}

	/**
	 * Method to log a string for the daily report
	 */
	public static void reportToFile(String message, Configuration config)

	{

		try {
			File dataFolder = new File(config.getGlobalConfig()
					.getLogsDirectory() + "\\dailyreports");
			if (!dataFolder.exists()) {
				dataFolder.mkdirs();
			}
			Date now2 = new Date();
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			boolean res = true;
			File saveTo = new File(config.getGlobalConfig().getLogsDirectory()
					+ "\\dailyreports", formato.format(now2) + "-report.txt");
			if (!saveTo.exists()) {
				saveTo.createNewFile();
				res = false;
			}

			FileWriter fw = new FileWriter(saveTo, true);

			PrintWriter pw = new PrintWriter(fw);
			if (!res) {
				pw.println("################################");
				pw.println("#                              #");
				pw.println("#            REPORT            #");
				pw.println("#                              #");
				pw.println("################################");
			}
			pw.println(message);

			pw.flush();

			pw.close();

		} catch (IOException e) {

			

		}

	}

	/**
	 * Method to log a string for the montly report
	 */
	public static void reportToFileMonthly(String message, Configuration config)

	{

		try {
			File dataFolder = new File(config.getGlobalConfig()
					.getLogsDirectory() + "\\monthlyreports");
			if (!dataFolder.exists()) {
				dataFolder.mkdirs();
			}
			Date now2 = new Date();
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			boolean res = true;
			File saveTo = new File(config.getGlobalConfig().getLogsDirectory()
					+ "\\monthlyreports", formato.format(now2) + "-report.txt");
			if (!saveTo.exists()) {
				saveTo.createNewFile();
				res = false;
			}

			FileWriter fw = new FileWriter(saveTo, true);

			PrintWriter pw = new PrintWriter(fw);
			if (!res) {
				pw.println("################################");
				pw.println("#                              #");
				pw.println("#       MONTHLY REPORT         #");
				pw.println("#                              #");
				pw.println("################################");
			}
			pw.println(message);

			pw.flush();

			pw.close();

		} catch (IOException e) {

			

		}

	}

	/**
	 * Create the daily log, looking if all the directories/files/webs are
	 * correct
	 */
	public static void proveAll(Configuration config) {
		Set<String> directories = config.getProperties().keySet();
		for (String s : directories) {
			if (!s.equalsIgnoreCase("urls")) {
				logToFile("#Checking directory: " + s, config);
				logToFile(DirectoryUtils.readDirectory(s, config), config);
				List<String> files = config.getProperties().get(s);
				for (String file : files) {
					logToFile(DirectoryUtils.proveFile(s, file, config), config);
				}
			}
		}
		logToFile("#Checking webs", config);
		List<String> webs = config.getProperties().get("urls");
		for (String s : webs) {

			logToFile(DirectoryUtils.proveWeb(s, config), config);

		}
		createDailyReport(config);
	}

	/**
	 * Create the daily report
	 */
	public static void createDailyReport(Configuration config) {
		int correct = 0;
		int errors = 0;
		int total = 0;
		Date now2 = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(now2);

		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day == 1) {
			createMonthlyReport(config);
		}
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		File log = new File(config.getGlobalConfig().getLogsDirectory(),
				formato.format(now2) + "-log.txt");
		if (log.exists()) {
			try (BufferedReader br = new BufferedReader(new FileReader(log))) {
				String line;
				while ((line = readLine(br)) != null) {
					if (line.contains("OK ---")) {
						correct++;
						total++;
					} else if (line.contains("ERROR ---")) {
						errors++;
						total++;
					}
				}
			} catch (IOException e) {
				
			}
			reportToFile("Total checks = " + total, config);
			reportToFile("OK = " + correct, config);
			reportToFile("ERROR = " + errors, config);
			reportToFile(
					"Ratio = " + ((double) correct / (double) total) * 100,
					config);
		}

	}
	/**
	 * Read a line
	 */
	public static String readLine(BufferedReader br) throws IOException{
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
	 * Create the monthly report
	 */
	public static void createMonthlyReport(Configuration config) {
		double ratio = 0.;
		int total = 0;
		Date fecha = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.MONTH, -1);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		for (int i = 0; i < 32; i++) {
			File log = null;
			if (i < 10) {
				if (month < 10) {
					log = new File(config.getGlobalConfig().getLogsDirectory()
							+ "\\dailyreports", year + "-0" + month + "-0" + i
							+ "-report.txt");

				} else {
					log = new File(config.getGlobalConfig().getLogsDirectory()
							+ "\\dailyreports", year + "-" + month + "-0" + i
							+ "-report.txt");

				}

			} else {
				if (month < 10) {
					log = new File(config.getGlobalConfig().getLogsDirectory()
							+ "\\dailyreports", year + "-0" + month + "-" + i
							+ "-report.txt");

				} else {
					log = new File(config.getGlobalConfig().getLogsDirectory()
							+ "\\dailyreports", year + "-" + month + "-" + i
							+ "-report.txt");

				}
			}
			if (log != null) {
				if (log.exists()) {
					reportToFileMonthly("#Checking log: " + year + "-" + month
							+ "-" + i + "-report.txt", config);
					try (BufferedReader br = new BufferedReader(new FileReader(
							log))) { // Reading line by line the daily report to
										// find the ratio
						String line;
						while ((line = readLine(br)) != null) {
							if (line.contains("Ratio")) {
								reportToFileMonthly(line, config);
								String[] linea = line.split("=");
								double ratioLinea = new Double(linea[1]);
								ratio += ratioLinea;
								total++;
								break;
							}
						}
					} catch (IOException e) {
						
					}

				}
			}
		}
		double totalRatio = ratio / total;
		reportToFileMonthly("################", config);
		reportToFileMonthly("Monthly Ratio = " + totalRatio, config);
		reportToFileMonthly("################", config);

	}

}
