package com.psi2.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import com.psi2.config.Configuration;

public class ExecutionUtils {

	private static Timer t;
	private static Configuration configuration;
	
	private static Integer ok;
	private static Integer errors;

	private static Integer timerRepeater = 1000;
	private static Integer repetitions;
	/**
     *  Start the clock
     */
	public static void RunUtility(JLabel lblTimer) {
		if(t==null){
		t = new Timer(timerRepeater, clock(lblTimer));
		repetitions=0;
		}
		t.start();
	}
	/**
     *  Stop the clock
     */
	public static void StopUtility() {

		t.stop();
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static void setConfiguration(Configuration c) {

		configuration = c;

	}

	/**
     *  Initialize the clock label
     */
	public static ActionListener clock(JLabel lblTimer) {

		ActionListener action = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				repetitions++;
				if((repetitions%(new Integer(getConfiguration().getTiempo())/timerRepeater))==0){
					FileUtils.createDailyReport(getConfiguration());
				}
			}
			
		};

		return action;

	}
	public static Integer getOk() {
		return ok;
	}
	public static void setOk(Integer ok) {
		ExecutionUtils.ok = ok;
	}
	public static Integer getErrors() {
		return errors;
	}
	public static void setErrors(Integer errors) {
		ExecutionUtils.errors = errors;
	}
	

}
