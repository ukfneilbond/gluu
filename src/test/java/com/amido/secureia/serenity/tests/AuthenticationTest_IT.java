package com.amido.secureia.serenity.tests;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = { "src/test/resources/features/authentication.feature" }, glue = {
		"com.amido.secureia.serenity.steps.definitions" })
public class AuthenticationTest_IT {

}
