package com.cronopista.ai.purger;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.cronopista.ai.datasources.InferenceEngineCSVFileReader;
import com.cronopista.ai.errors.UnequalDataException;
import com.cronopista.ai.purger.PermutationalDataPurger.PurgeInfo;

@Ignore
public class PermutationalDataPurgerTest {

	@Test
	public void testDataPurger() throws IOException, UnequalDataException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("ranges.txt").getFile());
		InferenceEngineCSVFileReader inferenceEngineCSVFileReader = new InferenceEngineCSVFileReader(file);
		PermutationalDataPurger fileTheoryBuilder = new PermutationalDataPurger(inferenceEngineCSVFileReader);
		List<PurgeInfo> purged = fileTheoryBuilder.find();
		Assert.assertEquals(3, purged.get(0).getSelection().size());
		Assert.assertEquals(Integer.valueOf(0), purged.get(0).getSelection().get(0));
		Assert.assertEquals(Integer.valueOf(1), purged.get(0).getSelection().get(1));
		Assert.assertEquals(Integer.valueOf(2), purged.get(0).getSelection().get(2));

	}

}
