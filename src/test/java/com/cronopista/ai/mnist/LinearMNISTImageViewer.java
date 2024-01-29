package com.cronopista.ai.mnist;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.cronopista.ai.core.Statement;

public class LinearMNISTImageViewer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int pixelSize;

	private int imageSize;

	private int[] data;

	private Color[] grays;

	public LinearMNISTImageViewer(Statement s, int size, int imageSize) {

		this.pixelSize = size;
		this.imageSize = imageSize;
		grays = new Color[256];
		for (int i = 0; i < 256; i++) {
			grays[i] = new Color(i, i, i);
		}
		this.data = new int[s.getData().size()];
		for (int i = 0; i < s.getData().size(); i++) {
			if (s.getData().get(i).getFrom() > 0)
				this.data[i] = (int) s.getData().get(i).getTo();
			else if( s.getData().get(i).getTo()==0)
				this.data[i] = 200;
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		for (int i = 0; i < 784; i++) {
			int gray = data[i];

			g.setColor(grays[gray]);

			g.fillRect(i % imageSize * pixelSize, i / imageSize * pixelSize, pixelSize, pixelSize);
		}

	}

}
