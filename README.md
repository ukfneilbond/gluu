# SecureIA/ResilienceDirect Customisations for Gluu

## Gluu Installation

Although the customisation code is expected to be compatible with all CE 3.0.0 distributions, development and testing is done against a clean installation of Gluu CE 3.0.0 on Ubuntu16.

## Building Customisation Code

The customisation code is written in Java and built with Maven.

Clone the project locally and run `mvn clean install` to compile and generate the deployable zip file: `secureia-gluu-*-deployable.zip`.

The zip file is created using the Maven Assembly plugin and contains all the required binary files and static content.

## Unit Testing

The project includes a suite of unit tests covering Java component logic.

## Integration Testing

The project includes a suite of integration tests covering the key login and password reset logic.

These tests make use of Serenity (http://www.thucydides.info/) and Cucumber (https://cucumber.io/) BDD testing frameworks. They're configured to run with chrome so must be run on a machine that has chrome installed. They can be updated however to run via most modern browsers. 

The test scenarios covered by the automated tests can be seen in the feature files: `src/test/resources/*.feature`

The integration tests are configured to run on the INTEGRATION_TEST profile: `mvn clean verify -PINTEGRATION_TEST` . The output of tests are compiled into a serenity report in the project `target` directory: `target/site/serenity/index.html`.

## Application Properties

A properties file: `secureia.properties` is included in the deployable zip. This file contains all the configurable values used by the customisation code. See the comments in the properties file for more details on each property.

**Note:** All the properties are mandatory must be set in order for the customisation code to function.

## Deploying Customisation Code
 
The project includes a 2 shell script files to deploy and install the customisation code on the Gluu server. 

These scripts are configured for the Amido dev environment and would need to be amended appropriately for a different target environment.

1. `deploy.sh` - SCP's the deployable zip file and the install script to the Gluu server.
1. `install.sh` - Unpacks the deployable zip file and installs the customisation code into the Gluu server. See the comments within script for all details.

**Notes:** 
1. Due to an issue with CE3.0.0 custom libs have to be packaged into the oxauth and identity war files in order for Seam component manager to detect new managed beans. The install script re-packages the war files. The original war files must be made available for the installer to complete this step. See the notes in the script for more details.
1. Due to an issue with CE3.0.0 on Ubuntu16 the restart of the Gluu server via `service gluu-server-3.0.0 stop|start` does not always allow the identity application to start up correctly. This is related to user permissions and is expected to be fixed in 3.0.1+. To work around this, the gluu service should be manually restarted from within the chroot jail as follows:
```
> sudo service gluu-server-3.0.0 login
> cd /etc/init.d
> ./identity stop
> ./oxauth stop
> ./solserver stop
> ./solserver start
> ./oxauth start
> ./identity start
```

## Register Custom Attributes

The following custom attributes are used by the customisation code and must be registered so that they can be applied to new users.

1. `failedLoginAttempts` - The number of failed attempts the user has made to login.
```
Name=failedLoginAttempts
Type=Numeric
Multivalued=False
```
1. `securityCode` - The security code they will be required to enter to change password. 
```
Name=securityCode
Type=Text
Multivalued=False
```
1. `securityCodeExpiry` - The expiry datetime of the security code. Stored as a string timestamp.
```
Name=securityCodeExpiry
Type=Text
Multivalued=False
```
1. `securityCodeValidationDisabled` - Override for code verification on password reset.
```
Name=securityCodeValidationDisabled
Type=Text
Multivalued=False
```
1. `applicationGrant` - Comma-separated list of applications that the user has access to.
```
Name=applicationGrant
Type=Text
Multivalued=False
```

These attributes can be registered in the oxTrust Admin UI. Login as an administrator and navigate to `Configuration` | `Attributes` | `Register`.

If you are using an external LDAP, you may need to incorporate these attributes in that schema.

## Create Test Users

The following test users are used by the automated integration test suite and must be created and setup as follows.

1. `john` - Happy path for login and reset. 
```
userId=john
email=lennon@demo.com
failedLoginAttempts=0
```
1. `paul` - Security code validation disabled by default.
```
userId=paul
email=mccartney@demo.com
failedLoginAttempts=0
securityCodeValidationDisabled=true
```
1. `george` - Security code email fails to send.
```
userId=george
email=harrison@demo.com
failedLoginAttempts=0
```
1. ringo - default
```
userId=ringo
email=starr@demo.com
failedLoginAttempts=0
```

These users can be added in the oxTrust Admin UI. Login as an administrator and navigate to `Users` | `Add Person`.

## Configure Custom Scripts

The following intercept scripts must be set in Gluu in order to the customisation code to be run integrated appropriately. 

### Person Authentication

A new script must be created to handle the custom person authentication work flow.
1. Login to the oxTrust Admin UI as an administrator and navigate to `Configuration` | `Manage Custom Scripts` | `Person Authentication`.
2. Choose `Add custom script configuration`.
3. Set the new script to the contents of `python/secureia-login.py`.
4. Set this new script as the default authentication script. Navigate to `Configuration` | `Manage Authentication` | `Default Authentication Method` and choose the script you have just added as `Authentication Mode`. **WARNING:** Setting this will force all new logins via the custom work flow so be sure to check it is working in a separate browser before exiting to avoid locking yourself out!. The admin user must have the failedLoginAttempts attribute added in order to work with this custom work flow.

**Note:** in a production environment you should disable all other authentication scripts.

### User Registration

A new script must be created to handle the custom person authentication work flow.
1. Login to the oxTrust Admin UI as an administrator and navigate to `Configuration` | `Manage Custom Scripts` | `User Registration`. 
2. Choose `Add custom script configuration`.
3. Set the new script to the contents of `python/secureia-registration.py`.

### User Update

A new script must be created to handle the custom person authentication work flow.
1. Login to the oxTrust Admin UI as an administrator and navigate to `Configuration` | `Manage Custom Scripts` | `User Update`.
2. Choose `Add custom script configuration`.
3. Set the new script to the contents of `python/secureia-user-update.py`.

## EmailService Mock

The email service is responsible for the sending of notification emails on registration, password reset and security code validation. 

The project includes two `EmailService` implementations:
1. `EmailServiceImpl`: This is the production implementation that uses the configured mail server to send emails.
1. `EmailServiceMock`: This is a development/testing implementation that does not send emails. This is used for automated integration testing fromework.

By default the mock service is used.

To switch this to the production implementation update the `ResetPasswordPolicy.emailService` and `UserValidationPolicy.emailService` declarations.





