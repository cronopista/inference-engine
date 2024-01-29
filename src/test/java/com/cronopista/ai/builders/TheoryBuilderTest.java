package com.cronopista.ai.builders;

import static org.junit.Assert.assertEquals;

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

		//System.out.println(theory.prettyPrint(inferenceEngineCSVFileReader.getLabelNames()));
		System.out.println(theory.getSteps());

	}

	@Test
	public void testIris() throws IOException, UnequalDataException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("iris.txt").getFile());
		InferenceEngineCSVFileReader inferenceEngineCSVFileReader = new InferenceEngineCSVFileReader(file);

		TheoryBuilder fileTheoryBuilder = new TheoryBuilder(inferenceEngineCSVFileReader);
		Theory theory = fileTheoryBuilder.buildTheory();
		
		String desiredOutcome = "0( 0: 4.3-5.8 & 2.3-4.4 & 1.0-1.9 & 0.1-0.6)\n"
		+ "1( 0: 4.9-7.0 & 2.0-3.4 & 3.0-4.9 & 1.0-1.6) || ( 0: 6.0-6.7 & 2.7-3.0 & 5.0-5.1 & 1.6-1.7) || ( 0: 5.9-5.9 & 3.2-3.2 & 4.8-4.8 & 1.8-1.8)\n"
		+ "2( 0: 4.9-7.7 & 2.5-2.9 & 4.5-6.9 & 1.7-2.4) || ( 0: 5.9-7.9 & 2.8-3.8 & 5.1-6.7 & 1.5-2.5) || ( 0: 6.0-6.1 & 2.2-3.0 & 4.8-5.0 & 1.5-1.8) || ( 0: 6.1-6.1 & 2.6-2.6 & 5.6-5.6 & 1.4-1.4)";
		assertEquals(desiredOutcome, theory.toString());
		
	}

}
