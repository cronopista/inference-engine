package com.cronopista.ai.datasources;

import java.util.List;

import com.cronopista.ai.core.Statement;
import com.cronopista.ai.errors.UnequalDataException;

public interface DataSource {

	public Statement next() throws UnequalDataException;

	public List<String> getLabels();

}
