# Custom Authentication handler for Secure IA
# Delegates to a Multi-step Authentication Handler.
#

from org.xdi.model.custom.script.type.auth import PersonAuthenticationType
from org.jboss.seam import ScopeType
from org.jboss.seam import Component

import java

class PersonAuthentication(PersonAuthenticationType):

    def __init__(self, currentTimeMillis):
        self.currentTimeMillis = currentTimeMillis
        self.__multiStepAuthenticationHandler = Component.getInstance("multiStepAuthenticationHandler", ScopeType.APPLICATION)
        self.__multiStepAuthenticationHandler.addPolicy(Component.getInstance("loginPolicy", ScopeType.APPLICATION))
        self.__multiStepAuthenticationHandler.addPolicy(Component.getInstance("userValidationPolicy", ScopeType.APPLICATION))
        self.__multiStepAuthenticationHandler.addPolicy(Component.getInstance("codeValidationPolicy", ScopeType.APPLICATION))
        self.__multiStepAuthenticationHandler.addPolicy(Component.getInstance("resetPasswordPolicy", ScopeType.APPLICATION))

    def init(self, configurationAttributes):
        return True    

    def destroy(self, configurationAttributes):
        return True

    def getApiVersion(self):
        return 2

    def getPageForStep(self, configurationAttributes, step):
        return self.__multiStepAuthenticationHandler.getPageForStep(configurationAttributes, step)

    def getExtraParametersForStep(self, configurationAttributes, step):
        return None

    def isValidAuthenticationMethod(self, usageType, configurationAttributes):
        return True

    def getAlternativeAuthenticationMethod(self, usageType, configurationAttributes):
        return None

    def prepareForStep(self, configurationAttributes, requestParameters, step):
        return self.__multiStepAuthenticationHandler.prepareForStep(configurationAttributes, requestParameters, step)

    def authenticate(self, configurationAttributes, requestParameters, step):
        return self.__multiStepAuthenticationHandler.authenticate(configurationAttributes, requestParameters, step)

    def getCountAuthenticationSteps(self, configurationAttributes):
        return self.__multiStepAuthenticationHandler.getCountAuthenticationSteps(configurationAttributes)
        
    def getNextStep(self, configurationAttributes, requestParameters, step):
        return self.__multiStepAuthenticationHandler.getNextStep(configurationAttributes, requestParameters, step)

    def logout(self, configurationAttributes, requestParameters):
        return True
