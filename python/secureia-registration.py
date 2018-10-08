from org.xdi.model.custom.script.type.user import UserRegistrationType
from org.xdi.ldap.model import GluuStatus
from org.xdi.util import StringHelper, ArrayHelper
from java.util import Arrays, ArrayList
from org.jboss.seam import ScopeType
from org.jboss.seam import Component

import java

class UserRegistration(UserRegistrationType):
    def __init__(self, currentTimeMillis):
        self.currentTimeMillis = currentTimeMillis
        self.__emailService = Component.getInstance("emailServiceImpl", ScopeType.APPLICATION)

    def init(self, configurationAttributes):
        print "SECUREIA User registration. Initialization"
        print "SECUREIA User registration. Initialized successfully"
        return True   

    def destroy(self, configurationAttributes):
        print "SECUREIA User registration. Destroy"
        print "SECUREIA User registration. Destroyed successfully"
        return True   

    # User registration init method
    #   user is org.gluu.oxtrust.model.GluuCustomPerson
    #   requestParameters is java.util.Map<String, String[]>
    #   configurationAttributes is java.util.Map<String, SimpleCustomProperty>
    def initRegistration(self, user, requestParameters, configurationAttributes):
        print "SECUREIA User registration. Init method"
        return True

    # User registration pre method
    #   user is org.gluu.oxtrust.model.GluuCustomPerson
    #   requestParameters is java.util.Map<String, String[]>
    #   configurationAttributes is java.util.Map<String, SimpleCustomProperty>
    def preRegistration(self, user, requestParameters, configurationAttributes):
        print "SECUREIA User registration. Pre method"
        userStatus = GluuStatus.ACTIVE
        user.setStatus(userStatus)
        return True

    # User registration post method
    #   user is org.gluu.oxtrust.model.GluuCustomPerson
    #   requestParameters is java.util.Map<String, String[]>
    #   configurationAttributes is java.util.Map<String, SimpleCustomProperty>
    def postRegistration(self, user, requestParameters, configurationAttributes):
        print "SECUREIA User registration. Post method"

        print user.getMail()
        print user.getUid()
        if self.__emailService:
            self.__emailService.sendRegistrationNotification(user.getMail(), user.getUid())

        return True

    def getApiVersion(self):
        return 1
