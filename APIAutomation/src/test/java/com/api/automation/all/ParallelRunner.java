package com.api.automation.all;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.intuit.karate.Results;
import com.intuit.karate.Runner.Builder;

public class ParallelRunner {
	
	@Test
	public void executeKarateTest() {
		
		Builder aRunner = new Builder();
		aRunner.path("classpath:com/api/automation");
		aRunner.tags("@ChataAPI");
		Results result =aRunner.parallel(2);
		System.out.println("Total Features: "+result.getFeaturesTotal());
		System.out.println("Passed Scenarios: "+result.getScenariosPassed());
		System.out.println("Failed Scenarios: "+result.getScenariosFailed());
		System.out.println("Total Scenarios: "+result.getScenariosTotal());
		Assertions.assertEquals(0,result.getFailCount(), "There are some failed scenarios");
	}

}
