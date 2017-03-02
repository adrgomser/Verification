package com.psi2.config;

public class GlobalConfiguration {

	
	private String configurationFile;
	private String logsDirectory;
	
	/**
     *  Global Configuration just have the path to the logs directory and the configuration file
     */
	public GlobalConfiguration(String configurationFile, String logsDirectory) {
		super();
		this.configurationFile = configurationFile;
		this.logsDirectory = logsDirectory;
	}
	
	public String getConfigurationFile() {
		return configurationFile;
	}
	public void setConfigurationFile(String configurationFile) {
		this.configurationFile = configurationFile;
	}
	public String getLogsDirectory() {
		return logsDirectory;
	}
	public void setLogsDirectory(String logsDirectory) {
		this.logsDirectory = logsDirectory;
	}
	
	
	
	
}
