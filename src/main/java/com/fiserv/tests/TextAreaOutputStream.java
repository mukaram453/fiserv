package com.fiserv.tests;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class TextAreaOutputStream extends OutputStream {

	private final JTextArea textArea;

	private final StringBuilder sb = new StringBuilder();

	public TextAreaOutputStream(final JTextArea textArea) {
		this.textArea = textArea;
	}

	@Override
	public void write(int b) throws IOException {
		if (b == '\r') {
			return;
		}
		if (b == '\n') {
			final String text = sb.toString() + "\n";
			textArea.append(text);
			sb.setLength(0);
		} else {
			sb.append((char) b);
		}
	}
}