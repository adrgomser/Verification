package com.psi2.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import com.psi2.config.Configuration;

public class ExecutionUtils {
	private static Timer t;
	private static Integer hora = 0;
	private static Integer minuto = 0;
	private static Integer segundo = 0;
	private static Integer dia = 0;
	private static Configuration configuration;
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
				segundo++;
				if (segundo == 60) {
					minuto++;
					segundo = 0;
				}
				if (minuto == 60) {
					hora++;
					minuto = 0;
				}
				if(hora==24){
					dia++;
					hora=0;
				}
				updateLabel();
				repetitions++;
				if((repetitions%(new Integer(getConfiguration().getTiempo())/timerRepeater))==0){
					FileUtils.proveAll(getConfiguration());
				}
			
			}
			/**
		     *  Update the clock
		     */
			private void updateLabel() {
				if(dia>=1){
					
					String time = dia +" d "+(hora <= 9 ? "0" : "") + hora + " : " + (minuto <= 9 ? "0" : "") + minuto + " : "
							+ (segundo <= 9 ? "0" : "") + segundo;
					lblTimer.setText(time);
				}else{
					String time = (hora <= 9 ? "0" : "") + hora + " : " + (minuto <= 9 ? "0" : "") + minuto + " : "
				+ (segundo <= 9 ? "0" : "") + segundo;
					lblTimer.setText(time);	
				}
				
			}
		};

		return action;

	}

}
