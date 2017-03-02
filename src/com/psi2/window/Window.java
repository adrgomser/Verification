package com.psi2.window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.UIManager;

import com.psi2.config.Configuration;
import com.psi2.config.GlobalConfiguration;
import com.psi2.utils.ExecutionUtils;

import javax.swing.JPanel;

import java.awt.CardLayout;
import javax.swing.JTextPane;

public class Window {

	private JFrame frmJ;
	private JTextField textField;
	private JTextField textField_1;
	JLabel lblTimeOfExecution;
	private GlobalConfiguration config;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frmJ.setVisible(true);
				} catch (Exception e) {
					
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJ = new JFrame();
		frmJ.setForeground(Color.WHITE);
		frmJ.setFont(new Font("Arial", Font.PLAIN, 12));
		frmJ.setTitle("Security");
		frmJ.getContentPane().setForeground(Color.BLUE);
		frmJ.getContentPane().setLayout(new CardLayout(0, 0));

		JPanel panel1 = new JPanel();
		frmJ.getContentPane().add(panel1, "name_506056528830070");
		panel1.setLayout(null);

		JPanel panel2 = new JPanel();
		frmJ.getContentPane().add(panel2, "name_506056538446282");
		panel2.setLayout(null);
		
		JPanel filesNotCorrectPanel = new JPanel();
		frmJ.getContentPane().add(filesNotCorrectPanel, "name_856334331178795");
		filesNotCorrectPanel.setLayout(null);

		JPanel securitySave = new JPanel();
		securitySave.setLayout(null);
		frmJ.getContentPane().add(securitySave, "name_863139376486520");
		
		textField = new JTextField();
		textField.setBounds(67, 59, 276, 22);
		panel1.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(65, 123, 278, 22);
		panel1.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblInsertPathOf = new JLabel("Insert path of configuration file");
		lblInsertPathOf.setBounds(67, 25, 176, 16);
		panel1.add(lblInsertPathOf);

		JLabel lblTimer = new JLabel("00 : 00 : 00");

		JButton btnExamine = new JButton("Next");
		btnExamine.setBounds(251, 173, 92, 22);
		panel1.add(btnExamine);
		btnExamine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				config = new GlobalConfiguration("", "");
				if(textField_1.getText().isEmpty()||textField.getText().isEmpty()){
					filesNotCorrectPanel.setVisible(true);
					panel1.setVisible(false);
				}else{
				config.setConfigurationFile(textField.getText());
				config.setLogsDirectory(textField_1.getText());
				ExecutionUtils.setConfiguration(new Configuration(config));
				panel2.setVisible(true);
				panel1.setVisible(false);
				}
			}
		});

		JLabel lblInsertLogsDirectory = new JLabel("Insert logs directory");
		lblInsertLogsDirectory.setBounds(67, 94, 114, 16);
		panel1.add(lblInsertLogsDirectory);

		JButton btnHelp = new JButton("Help");
		btnHelp.setBounds(71, 172, 97, 25);
		panel1.add(btnHelp);

		JButton btnNewButton = new JButton("Run ");
		JButton btnNewButton_1 = new JButton("Stop");
		JButton btnNewButton_2 = new JButton("Save");
		
		btnNewButton_1.setBounds(231, 174, 97, 25);
		panel2.add(btnNewButton_1);
		
		btnNewButton_2.setBounds(231, 174, 97, 25);
		panel2.add(btnNewButton_2);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ExecutionUtils.RunUtility(lblTimer);
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(true);
				btnNewButton_1.setVisible(true);
				btnNewButton_2.setEnabled(false);
				btnNewButton_2.setVisible(false);
				


			}
		});
		btnNewButton.setBounds(98, 174, 97, 25);
		panel2.add(btnNewButton);

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ExecutionUtils.StopUtility();
				btnNewButton.setEnabled(true);
				btnNewButton_1.setEnabled(false);
				btnNewButton_1.setVisible(false);
				btnNewButton_2.setEnabled(true);
				btnNewButton_2.setVisible(true);


			}
		});
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				securitySave.setVisible(true);
				panel2.setVisible(false);
				



			}
		});

		lblTimeOfExecution = new JLabel("Time of execution");
		lblTimeOfExecution.setFont(new Font("Arial", Font.BOLD, 18));
		lblTimeOfExecution.setBounds(127, 26, 176, 16);
		panel2.add(lblTimeOfExecution);

		lblTimer.setFont(new Font("Arial", Font.PLAIN, 40));
		lblTimer.setBounds(98, 79, 264, 68);
		panel2.add(lblTimer);
		
		

		JPanel helpPanel = new JPanel();
		frmJ.getContentPane().add(helpPanel, "name_601527980303771");
		helpPanel.setLayout(null);

		JButton btnBack = new JButton("Back");
		btnBack.setBounds(152, 177, 97, 25);
		helpPanel.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				panel2.setVisible(false);
				panel1.setVisible(true);
				helpPanel.setVisible(false);

			}
		});

		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				panel2.setVisible(false);
				panel1.setVisible(false);
				helpPanel.setVisible(true);

			}
		});

		JLabel lblNewLabel = new JLabel("The Configuration File should be a .properties file.");
		lblNewLabel.setBounds(66, 55, 287, 16);
		helpPanel.add(lblNewLabel);

		JLabel lblTheLogFiles = new JLabel("The Log files are stored in the logs directory file.");
		lblTheLogFiles.setBounds(66, 103, 287, 16);
		helpPanel.add(lblTheLogFiles);
		
	
		
		JTextPane txtpnPleaseInsertA = new JTextPane();
		txtpnPleaseInsertA.setEditable(false);
		txtpnPleaseInsertA.setBounds(90, 56, 220, 84);
		txtpnPleaseInsertA.setText("Please, insert a valid directory for the files. If you have any doubt click on Help button.");
		filesNotCorrectPanel.add(txtpnPleaseInsertA);
		
		JButton btnUnderstand = new JButton("Okay, I understand");
		btnUnderstand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filesNotCorrectPanel.setVisible(false);
				panel1.setVisible(true);
			}
		});
		btnUnderstand.setBounds(133, 180, 141, 25);
		filesNotCorrectPanel.add(btnUnderstand);
		
		
		
		JButton button = new JButton("Save and close");
		button.setBounds(60, 195, 129, 25);
		securitySave.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExecutionUtils.getConfiguration().saveHashes();
				System.exit(0);
			}
		});
		
		JLabel label = new JLabel("If you save and close there will be a security vulnerability");
		label.setBounds(60, 34, 326, 16);
		securitySave.add(label);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("Please, do this on your own responsibility. If you do not want to generate the hashes log, just close this window.");
		textPane.setEditable(false);
		textPane.setBounds(89, 63, 271, 60);
		securitySave.add(textPane);
		
		JButton button_1 = new JButton("Go back");
		button_1.setBounds(263, 198, 97, 25);
		securitySave.add(button_1);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				securitySave.setVisible(false);
				panel2.setVisible(true);
			}
		});
		frmJ.setBounds(100, 100, 450, 300);
		frmJ.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@SuppressWarnings("unused")
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
