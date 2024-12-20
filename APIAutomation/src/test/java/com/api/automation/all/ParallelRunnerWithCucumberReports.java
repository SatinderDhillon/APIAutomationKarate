package com.api.automation.all;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.apache.commons.io.FileUtils;
import com.intuit.karate.Results;
import com.intuit.karate.Runner.Builder;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

public class ParallelRunnerWithCucumberReports {
	

	@Test
	public void executeKarateTest() {
		
		Builder aRunner = new Builder();
		aRunner.path("classpath:com/api/automation");
		aRunner.tags("@ChataAPI");
		aRunner.outputCucumberJson(true);
		Results result =aRunner.parallel(2);
		System.out.println("Total Features: "+result.getFeaturesTotal());
		System.out.println("Passed Scenarios: "+result.getScenariosPassed());
		System.out.println("Failed Scenarios: "+result.getScenariosFailed());
		System.out.println("Total Scenarios: "+result.getScenariosTotal());
		generateCucumberReports(result.getReportDir());
	}

	private void generateCucumberReports(String reportDirLoc) {
		File reportDir = new File(reportDirLoc);
		Collection<File> jsonCollect = FileUtils.listFiles(reportDir, new String[] {"json"}, true);
		
		List<String> jsonFiles = new ArrayList<String>();
		jsonCollect.forEach(file-> jsonFiles.add(file.getAbsolutePath()));
		
		Configuration config = new Configuration(reportDir, "CHATA API");
		ReportBuilder reportBuild = new ReportBuilder(jsonFiles, config);
		reportBuild.generateReports();
	}
}
