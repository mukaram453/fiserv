package com.fiserv.tests;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class TextAreaLogProgram extends JFrame {
	/**
	 * The text area which is used for displaying logging information.
	 */
	private JTextArea textArea;

	private PrintStream standardOut;

	public TextAreaLogProgram() {
		super("Output:");

		textArea = new JTextArea(50, 10);
		textArea.setEditable(false);
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));

		// keeps reference of standard output stream
		standardOut = System.out;

		// re-assigns standard output stream and error output stream
		System.setOut(printStream);
		System.setErr(printStream);

		// creates the GUI
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.anchor = GridBagConstraints.WEST;

		// add(buttonStart, constraints);
		constraints.gridx = 1;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		add(new JScrollPane(textArea), constraints);

//        myButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//            	System.out.println("Time now is " + (new Date()));
//    			try {
//                  Thread.sleep(5000);
//              } catch (InterruptedException ex) {
//                  ex.printStackTrace();
//              }
//    			System.out.println("Time now is " + (new Date()));
//            }
//        });

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 700);
		setLocationRelativeTo(null); // centers on screen
	}

//    /**
//     * Prints log statements for testing in a thread
//     */
//    public void printLog() {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    System.out.println("Time now is " + (new Date()));
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//        });
//        thread.start();
//    }

	/**
	 * Runs the program
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TextAreaLogProgram().setVisible(true);
			}
		});
	}
}