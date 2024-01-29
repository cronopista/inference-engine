package com.cronopista.ai.datasources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cronopista.ai.core.Statement;
import com.cronopista.ai.errors.UnequalDataException;

public class InferenceEngineCSVFileReader implements DataSource {

	private BufferedReader reader;

	private List<String> labelNames;

	private int lineNumber;

	public InferenceEngineCSVFileReader(File file) throws IOException {
		this.reader = new BufferedReader(new FileReader(file));
		String[] labels = this.reader.readLine().split(",");
		this.labelNames = new ArrayList<String>();
		for (int i = 1; i < labels.length; i++) {
			this.labelNames.add(labels[i]);
		}

		lineNumber++;
	}

	public Statement next() throws UnequalDataException {
		try {
			String line = this.reader.readLine();
			lineNumber++;
			if (line == null || line.length() == 0) {
				this.reader.close();
				return null;
			}

			String[] strValues = line.split(",");
			if (strValues.length != labelNames.size() + 1) {
				throw new UnequalDataException("Unequal length found at line " + lineNumber + ". Expected "
						+ (labelNames.size() + 1) + " but was " + strValues.length);
			}

			double[] doubleValues = new double[strValues.length - 1];
			for (int i = 1; i < strValues.length; i++) {
				doubleValues[i - 1] = Double.parseDouble(strValues[i].trim());
			}

			return new Statement(strValues[0], doubleValues);
		} catch (NumberFormatException e) {
			throw new UnequalDataException("NumberFormatException");
		} catch (IOException e) {
			throw new UnequalDataException("IOException");
		}

	}

	public List<String> getLabelNames() {
		return labelNames;
	}

	public List<String> getLabels() {

		return labelNames;
	}

}
