Feature: Authentication

 	In order to authenticate
 	As a registered user
 	I want to be able to enter my username and password and be authenticated on Gluu

	Scenario: Should have Login screen
		Given I have the login page url
		When I enter the Login page
		Then I should see the Login screen

	Scenario: Should authenticate user with valid username and password
		Given I "john" am on the Login screen
		When I enter "john" as username and "password" as password and submit the page
		Then I "john" should be authenticated on Gluu
    
	@revertPasswordLockdown
	Scenario Outline: Should feedback user with error message when invalid credentials
		Given I "<username>" am on the Login screen
    	When I enter "<username>" as username and "<password>" as password and submit the page
    	Then I "<username>" should see error message "Username and password do not match."
    
    	Examples:
    	| username 			| password  |
    	| john   			| incorrect |
    	| notregistered   	| incorrect |
    
	@revertPasswordLockdown
	Scenario Outline: Should feedback user with username and password mismatch on the 4th invalid attempt to login
		Given I "<username>" am on the Login screen
  		When I try to authenticate 4 times "<username>" using "<password>" password
  		Then I "<username>" should see error message "Username and password do not match."
  	
  		Examples:
		| username 		| password |
		|	john   		| invalid  |
    	| notregistered | invalid  |

    
	@revertPasswordLockdown
	Scenario Outline: Should feedback user lockdown on the 5th invalid attempt to login
		Given I "<username>" am on the Login screen
  		When I try to authenticate 5 times "<username>" using "<password>" password
  		Then I "<username>" should see error message "<errormessage>"
  
  		Examples:
		| username 		| password | errormessage 													  |
		|	john   		| invalid  | Your account has been locked. Please contact your administrator. |
    	| notregistered | invalid  | Username and password do not match.							  |


	@revertPasswordLockdown
  	Scenario: Should feedback user lockdown when user is locked
		Given I "john" am locked and I am on the Login screen
		When I enter "john" as username and "password" as password and submit the page
  		Then I "john" should see error message "Your account has been locked. Please contact your administrator."
    
	@revertPasswordLockdown
	Scenario: Should reset invalid password counter after successful authentication
		Given I "john" tried 4 times unsucessful login and I am on the Login screen
		When I enter "john" as username and "password" as password and submit the page
			And I logout
			And I enter the Login page
			And I enter "john" as username and "invalidpassword" as password and submit the page
    	Then I "john" should see error message "Username and password do not match."