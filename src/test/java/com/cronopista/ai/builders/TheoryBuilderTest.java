package com.cronopista.ai.builders;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.cronopista.ai.core.Theory;
import com.cronopista.ai.datasources.InferenceEngineCSVFileReader;
import com.cronopista.ai.errors.UnequalDataException;

public class TheoryBuilderTest {

	@Test
	public void testFileTheoryBuilder() throws IOException, UnequalDataException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("ranges.txt").getFile());
		InferenceEngineCSVFileReader inferenceEngineCSVFileReader = new InferenceEngineCSVFileReader(file);
		TheoryBuilder fileTheoryBuilder = new TheoryBuilder(inferenceEngineCSVFileReader);
		Theory theory = fileTheoryBuilder.buildTheory();

		System.out.println(theory.prettyPrint(inferenceEngineCSVFileReader.getLabelNames()));
		System.out.println(theory.getSteps());

	}

	@Test
	public void testIris() throws IOException, UnequalDataException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("iris.txt").getFile());
		InferenceEngineCSVFileReader inferenceEngineCSVFileReader = new InferenceEngineCSVFileReader(file);

		TheoryBuilder fileTheoryBuilder = new TheoryBuilder(inferenceEngineCSVFileReader);
		Theory theory = fileTheoryBuilder.buildTheory();
		System.out.println(theory.toString());
		System.out.println(theory.getSteps());

	}

}
