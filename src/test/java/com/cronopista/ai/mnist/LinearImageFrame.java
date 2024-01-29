package com.cronopista.ai.mnist;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;

import com.cronopista.ai.core.Statement;

public class LinearImageFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int PIXEL_SIZE = 5;
	private static final int IMAGE_SIZE = 28;

	public LinearImageFrame(List<Statement> outcomes) {
		super();

		this.setLayout(null);
		for (int i = 0; i < outcomes.size(); i++) {
			LinearMNISTImageViewer image = new LinearMNISTImageViewer(outcomes.get(i), PIXEL_SIZE, IMAGE_SIZE);
			image.setBounds(i * PIXEL_SIZE * IMAGE_SIZE, 0, PIXEL_SIZE * IMAGE_SIZE, PIXEL_SIZE * IMAGE_SIZE);
			this.getContentPane().add(image);

		}

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(1500, 500));
		this.setVisible(true);

	}

}
