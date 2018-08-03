package com.fiserv.tests;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.filechooser.FileSystemView;

import org.testng.TestNG;

public class TestRunner {

	public static String line;

	public static void main(String[] args) {
		if (login() == JOptionPane.OK_OPTION) {
			openConsole();
			TestNG testng = new TestNG();
			testng.setTestClasses(new Class[] { CreditProcessingTest.class });
			testng.run();
		}
	}

	private static void startProcess() {
		List<String> command = new ArrayList<String>();

		command.add("java");
		command.add("-jar");
		command.add("fiserv-1.0.jar");

		Process process = null;
		try {
			ProcessBuilder builder = new ProcessBuilder(command);
			process = builder.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
		BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		try {
			while ((line = stdout.readLine()) != null) {
			}
			while ((line = stderr.readLine()) != null) {
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	private static void openConsole() {
		JFrame frame = new JFrame("Output:");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextArea jTextArea = new JTextArea(30, 100);
		System.setOut(new PrintStream(new TextAreaOutputStream(jTextArea)));
		frame.add(new JScrollPane(jTextArea));
		frame.pack();
		frame.setVisible(true);
		Timer t = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (line != null) {
					System.out.println(line);
					line = null;
				}
			}
		});
		t.start();
	}

	private static int login() {
		JButton jbutton = new JButton("Choose File...");
		JTextField userName = new JTextField(20);
		JPasswordField password = new JPasswordField(20);
		JTextField excel = new JTextField(20);
		JTextField sheet = new JTextField(25);
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0);

		pane.add(new JLabel("User Name:"), gbc);

		gbc.gridy = 1;
		pane.add(new JLabel("Password:"), gbc);
		
		gbc.gridy = 2;
		pane.add(new JLabel("Choose Excel File:"), gbc);

		gbc.gridy = 3;
		pane.add(new JLabel("Excel File Absolute Path:"), gbc);

		gbc.gridy = 4;
		pane.add(new JLabel("Sheet Name:"), gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		pane.add(userName, gbc);

		gbc.gridy = 1;
		pane.add(password, gbc);			
		
		jbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Choose file");

                if (ret == JFileChooser.APPROVE_OPTION) {
                	excel.setText(fileopen.getSelectedFile().toString());
                }
			}
		});
		gbc.gridy = 2;
		pane.add(jbutton, gbc);
		gbc.gridy = 3;
		pane.add(excel, gbc);
		gbc.gridy = 4;
		pane.add(sheet, gbc);

		int reply = JOptionPane.showConfirmDialog(null, pane, "Please Log-In", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (reply == JOptionPane.OK_OPTION) {
			System.setProperty("user", userName.getText());
			System.setProperty("password", password.getText());					
			System.setProperty("excel_file_path", excel.getText());
			System.setProperty("sheet_name", sheet.getText());
		}
		return reply;
	}
	
	private static void action(JFileChooser fileChooser, JButton jbutton) {
		
		jbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.showSaveDialog(null);				
			}
		});

	}
}
