package com.cronopista.ai.mnist;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.cronopista.ai.core.Statement;
import com.cronopista.ai.core.Theory;

public class MNISTTest {

	public void doMNISTTest() throws FileNotFoundException, IOException {
		Theory theory = new Theory();
		learnPass(theory);
		testIt(theory);
		
	}

	private void testIt(Theory theory) throws IOException, FileNotFoundException {

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();

		MNISTFileReader testImageFile = new MNISTFileReader(classloader.getResourceAsStream("t10k-images.idx3-ubyte"),
				16, 784);
		MNISTFileReader testLabelFile = new MNISTFileReader(classloader.getResourceAsStream("t10k-labels.idx1-ubyte"),
				8, 1);

		int fails = 0;
		int success = 0;

		for (int i = 0; i < 10000; i++) {
			byte[] image = testImageFile.readNext();
			byte[] label = testLabelFile.readNext();

			Statement imageObject = buildImageObject(image, label);
			String labelFound = theory.evalPartial(imageObject);

			String shouldBe = imageObject.getLabel();

			if (shouldBe.equals(labelFound)) {
				success++;
			} else {
				fails++;
			}

		}

		System.out.println("S " + success);
		System.out.println("F " + fails);
	}

	private void learnPass(Theory theory) throws FileNotFoundException, IOException {

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		MNISTFileReader trainImageFile = new MNISTFileReader(classloader.getResourceAsStream("train-images.idx3-ubyte"),
				16, 784);
		MNISTFileReader trainLabelFile = new MNISTFileReader(classloader.getResourceAsStream("train-labels.idx1-ubyte"),
				8, 1);

		for (int i = 0; i < 60000; i++) {
			byte[] image = trainImageFile.readNext();
			byte[] label = trainLabelFile.readNext();

			Statement imageObject = buildImageObject(image, label);
			theory.addToHistory(imageObject);

			if (i % 500 == 0) {
				System.out.println("M " + i);
			}
		}

		for (int i = 0; i < theory.getHistory().size(); i++) {
			Statement statement = theory.getHistory().get(i);
			theory.addStatement(statement);

			if (i % 3 == 0) {
				System.out.println(i + " " + theory.countTotalStatements());
			}
			
			if(i%5000==0) {
				testIt(theory);
			}
		}

	}

	private Statement buildImageObject(byte[] image, byte[] label) {
		int unsignedLabel = label[0] & 0xff;
		double[] data = new double[image.length];

		for (int i = 0; i < image.length; i++) {
			byte b = image[i];
			int unsigned = b & 0xff;
			data[i] = unsigned;
		}

		return new Statement(unsignedLabel + "", data);
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		new MNISTTest().doMNISTTest();
	}

}
