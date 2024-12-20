package com.api.automation.all;

import java.util.List;
import java.util.function.Consumer;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.gherkin.model.And;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.gherkin.model.Given;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.gherkin.model.Then;
import com.aventstack.extentreports.gherkin.model.When;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.intuit.karate.Results;
import com.intuit.karate.core.Result;
import com.intuit.karate.core.ScenarioResult;
import com.intuit.karate.core.Step;

public class ExtentCustomReport {
	
	private ExtentReports extentReports;
	private ExtentSparkReporter extentSparkReporter;
	private String reportDir;
	private String reportTitile = "Karate Test Execution Report";
	private Results testResults;
	private ExtentTest featureNode;
	private String featureTitle="";
	private ExtentTest scenarioNode;
	private String scenarioTitle = "";
	
	public ExtentCustomReport() {
		extentReports = new ExtentReports();
	}
	
	public ExtentCustomReport withReportDir(String reportDir) {
		this.reportDir = reportDir;
		return this;
	}
	
	public ExtentCustomReport withKarateTests(Results testResults) {
		this.testResults = testResults;
		return this;
	}
		
	public ExtentCustomReport withReportTitile(String reportTitile) {
		this.reportTitile = reportTitile;
		return this;
	}
	
	public void generateExtentReports() {
		// Check for ReportDir and Test Results if not present throw exception
		// Using testResults, get list of scenario results
		// loop over the list of scenario results
		// using scenario result get the scenario object
		// using scenario object, get info about feature file
		// using same scenario object get info about the scenario
		// using scenario result get list of test steps
		// loop over the steps result list and get info about scenario step and its execution status
		// use all info to generate the report
		extentSparkReporter = new ExtentSparkReporter(reportDir);
		extentReports.attachReporter(extentSparkReporter);
		styles();
		if(this.reportDir != null && !this.reportDir.isEmpty() && this.testResults != null) {
			List<ScenarioResult> scenarioResults = getScenarioResults();
		  	scenarioResults.forEach((scenarioResult)->{
				String featureName = getFeatureName(scenarioResult);
				String featureDesc = getFeatureDesc(scenarioResult);
				ExtentTest featureNode = createFeatureNode(featureName, featureDesc);
				String scenarioTitle = getScenarioTitle(scenarioResult);
				ExtentTest scenarioNode = createScenarioNode(featureNode, scenarioTitle);
				scenarioResult.getStepResults().forEach((step)->{
					//Adding scenario step with scenario node 
					addScenarioStep(scenarioNode, step.getStep(), step.getResult());
				});
			});
			
			extentReports.flush();
			return;
			
		}
		throw new RuntimeException("Missing Karate Test Results / Report Dir location");
		
	}
	
	private List<ScenarioResult> getScenarioResults(){
		return (List<ScenarioResult>) this.testResults.getScenarioResults();
	}

	private String getFeatureName(ScenarioResult scenarioResult) {
		return scenarioResult.getScenario().getFeature().getName();
	}
	
	private String getFeatureDesc(ScenarioResult scenarioResult) {
		return scenarioResult.getScenario().getFeature().getDescription();
	}

	private ExtentTest createFeatureNode(String featureName, String featureDesc) {
		// if feature name is same return same instance of extent test
		// else create a new instance and then return it
		if(this.featureTitle.equalsIgnoreCase(featureName)) {
			return featureNode;
		}
		this.featureTitle = featureName;
		featureNode = extentReports.createTest(Feature.class, featureName, featureDesc);
		return featureNode;
	}

	private ExtentTest createScenarioNode(ExtentTest featureNode, String scenarioTitle) {
		// if feature name is same return same instance of extent test
		// else create a new instance and then return it
		if(this.scenarioTitle.equalsIgnoreCase(scenarioTitle)) {
			return scenarioNode;
		}
		this.scenarioTitle = scenarioTitle;
		scenarioNode = featureNode.createNode(Scenario.class, scenarioTitle);
		return scenarioNode;
	}
	
	private String getScenarioTitle(ScenarioResult scenarioResult) {
		return scenarioResult.getScenario().getName();
	}
	
	private void addScenarioStep(ExtentTest scenarioNode, Step step, Result stepResult) {
		String type = step.getPrefix(); // Given When Then
		String stepTitle = step.getText();
		String status = stepResult.getStatus();
		Throwable error = stepResult.getError();
		ExtentTest stepnode;
		switch(type) {
		case "Given":
			stepnode = scenarioNode.createNode(Given.class, stepTitle);
			addStatus(stepnode, status, error);
			break;
		case "When":
			stepnode = scenarioNode.createNode(When.class,stepTitle);
			addStatus(stepnode, status, error);
			break;
		case "Then":
			stepnode = scenarioNode.createNode(Then.class, stepTitle);
			addStatus(stepnode, status, error);
			break;
		case "And":
			stepnode = scenarioNode.createNode(And.class, stepTitle);
			addStatus(stepnode, status, error);
			break;
			default:
				stepnode = scenarioNode.createNode(type + " "+ stepTitle);
				addStatus(stepnode, status, error);
				break;
		}
		
	}
	
	private void addStatus (ExtentTest stepnode, String status, Throwable error) {
		switch (status) {
		case "passed":
			stepnode.pass("");
			break;
		case "failed":
			stepnode.fail(error);
			
		default :
			stepnode.skip("");
			break;
			}
	}
	
	private void styles() {
		extentSparkReporter.config().enableOfflineMode(true);
		extentSparkReporter.config().setDocumentTitle(reportTitile);
		extentSparkReporter.config().setTimelineEnabled(true);
		extentSparkReporter.config().setTheme(Theme.DARK);
	}
}
