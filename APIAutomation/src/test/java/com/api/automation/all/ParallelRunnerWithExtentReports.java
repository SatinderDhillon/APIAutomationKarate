package com.api.automation.all;

import org.junit.jupiter.api.Test;
import com.intuit.karate.Results;
import com.intuit.karate.Runner.Builder;

public class ParallelRunnerWithExtentReports {

	@Test
	public void executeKarateTest() {
		
		Builder aRunner = new Builder();
		aRunner.path("classpath:com/api/automation");
		aRunner.tags("@ChataAPI");
		Results result =aRunner.parallel(2);
		//Report 
		ExtentCustomReport extentReport = new ExtentCustomReport().withKarateTests(result)
				                          .withReportDir(result.getReportDir())
				                          .withReportTitile("Chata API Resuts");
		extentReport.generateExtentReports();
	}
}
