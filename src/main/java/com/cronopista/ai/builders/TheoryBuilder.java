package com.cronopista.ai.builders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cronopista.ai.core.Statement;
import com.cronopista.ai.core.Theory;
import com.cronopista.ai.datasources.DataSource;
import com.cronopista.ai.errors.UnequalDataException;

public class TheoryBuilder {

	private DataSource source;

	private List<Statement> allStatements;

	public TheoryBuilder(DataSource source) {
		this.source = source;
	}

	public Theory buildTheory() throws UnequalDataException {

		allStatements = new ArrayList<Statement>();
		Statement s = null;
		Theory theory = new Theory();
		while ((s = source.next()) != null) {
			allStatements.add(s);
			theory.addToHistory(s);
		}

		Collections.shuffle(allStatements);

		for (Statement statement : allStatements) {
			theory.addStatement(statement);
		}

		return theory;
	}

	public List<Statement> getAllStatements() {
		return allStatements;
	}

}
