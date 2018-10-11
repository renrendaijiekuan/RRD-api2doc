package com.renrendai.loan.beetle.commons.api2doc.codewriter;

public interface CodeOutput {

	void writeCodeFile(String fileName, String fileContent);
	
	void setPercent(int percent);
	
	void log(String log, String... args);
}
