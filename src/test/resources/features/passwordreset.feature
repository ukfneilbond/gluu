Feature: Password Reset

	In order to reset/create password
	As a registered user
	I want to be able to enter my username and e-mail address to receive code validation
	I want to be able to enter code to enable reset/create password
	I want to be able to enter new password and password confirmation to reset/create password
	I want to be able to login with new password 
	
  	Scenario: Should have reset/create link on Login screen
    	Given I have the login page url
    	When I enter the Login page
    	Then I should see the reset/create password link with text "Reset or create your password"
    
  	Scenario: Should have User Validation screen
    	Given I "john" am on the Login screen
    	When I click reset/create password link
    	Then I should see the User Validation screen
    	
  	Scenario: Should validate user with valid username and e-mail address
 			Given I am on the User Validation screen
    	When I enter "john" as username and "lennon@demo.com" as e-mail address and submit the page
    	Then I should be taken to Code Validation screen
    
  	Scenario Outline: Should feedback user with error message when invalid user details
    	Given I am on the User Validation screen
    	When I enter "<username>" as username and "<email>" as e-mail address and submit the page
    	Then I should see error message "Username and email are not recognised." on User Validation screen
    
	    Examples:
	    | username 			| email  			 	 |
	    | john   			| incorrect@demo.com	 |
	    | notregistered  	| harrison@demo.com 	 |
	    | george   			| incorrect@demo.com	 |
	    | notregistered  	| lennon@demo.com 		 |
	    | notregistered   	| notregistered@demo.com |
    
    Scenario: Should feedback user with error message when code is not sent
    	Given I am on the User Validation screen
    	When I enter "george" as username and "harrison@demo.com" as e-mail address and submit the page
			Then I should see error message "Security code could not be sent. Please contact your administrator." on User Validation screen
		
  	Scenario: Should have Code Validation screen
    	Given I am on the User Validation screen
			When I enter "john" as username and "lennon@demo.com" as e-mail address and submit the page
    	Then I should see Code Validation screen

    Scenario: Should validate code with valid code
 			Given I am on the Code Validation screen with user details "john" and "lennon@demo.com"
    	When I enter valid code and submit the page
    	Then I should be taken to Password Reset screen
    	
  	Scenario: Should feedback user with error message when invalid code
 			Given I am on the Code Validation screen with user details "john" and "lennon@demo.com"
			When I enter invalid code and submit the page
			Then I should see error message "Security code does not match." on Code Validation screen
		
  	Scenario: Should have Password Reset screen
 			Given I am on the Code Validation screen with user details "john" and "lennon@demo.com"
    	When I enter valid code and submit the page
    	Then I should see Password Reset screen

  	Scenario: Should take user to Password Reset screen when security code validation disabled
			Given I am on the User Validation screen
			When I enter "paul" as username and "mccartney@demo.com" as e-mail address and submit the page
			Then I should be taken to Password Reset screen

