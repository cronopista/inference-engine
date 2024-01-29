package com.cronopista.ai.file;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.cronopista.ai.core.Statement;
import com.cronopista.ai.datasources.InferenceEngineCSVFileReader;
import com.cronopista.ai.errors.UnequalDataException;

public class InferenceEngineCSVFileReaderTest {

	@Test
	public void testCSVFileReader() throws IOException, UnequalDataException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("basic_age_test.txt").getFile());
		InferenceEngineCSVFileReader reader = new InferenceEngineCSVFileReader(file);
		Statement st = reader.next();

		Assert.assertEquals(8, st.getData().get(0).getFrom(), 0.1);
	}

}
