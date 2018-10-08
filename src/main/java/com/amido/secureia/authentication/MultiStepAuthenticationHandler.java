package com.amido.secureia.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xdi.oxauth.model.common.User;
import com.amido.secureia.common.SessionUtils;

@Name(value = "multiStepAuthenticationHandler")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class MultiStepAuthenticationHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MultiStepAuthenticationHandler.class);

    @In(value = "sessionUtils", required = true, create = true)
    private SessionUtils sessionUtils;

    private List<AuthenticationPolicy> policies;

    public MultiStepAuthenticationHandler() {
        policies = new ArrayList<AuthenticationPolicy>();
    }

    public AuthenticationPolicy getPolicyFor(int step, User user) {
        printlnDebug("GET_POLICY_FOR");
        AuthenticationPolicy result = null;
        for (AuthenticationPolicy policy : policies) {
            if (policy.appliesTo(step, user)) {
                result = policy;
                break;
            }
        }
        if (result == null) {
            LOG.error(String.format("No registered policy applies for step (%d) and user (%s).", step,
                (user != null) ? user.getUserId() : "unknown"));
        } else {
            LOG.debug("Resolved policy: " + result.toString());
        }
        return result;
    }

    public void addPolicy(AuthenticationPolicy policy) {
        policies.add(policy);
    }

    public String getPageForStep(Map<String, Object> configurationAttributes, int step) {
        printlnDebug("GET_PAGE_FOR_STEP");
        AuthenticationPolicy policy = getPolicyFor(step, sessionUtils.getUser());
        sessionUtils.setCurrentPolicy(policy);
        return (policy != null) ? policy.getPage() : null;
    }

    public Boolean prepareForStep(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, int step) {
        printlnDebug("PREPARE_FOR_STEP");
        User user = sessionUtils.getUser();
        return sessionUtils.getCurrentPolicy().checkAndPrepare(configurationAttributes, requestParameters, user);
    }

    public Boolean authenticate(Map<String, Object> configurationAttributes, Map<String, String[]> requestParameters, int step) {
        printlnDebug("AUTHENTICATE");
        User user = sessionUtils.getUser();
        return sessionUtils.getCurrentPolicy().checkAndAuthenticate(configurationAttributes, requestParameters, user);
    }

    /**
     * Calculates the next step in the work-flow.
     */
    public int getNextStep(Map<String, Object> configurationAttributes, Map<String, String> requestParameters, int step) {
        printlnDebug("GET_NEXT_STEP");

        if(sessionUtils.isPasswordReset()) {
            printlnDebug("GET_NEXT_STEP -> BACK TO LOGIN.");
            return 1;
        } else {
            printlnDebug("GET_NEXT_STEP -> STATE UNCHANGED.");
            return -1; // leave step unchanged
        }
    }

    /**
     * Calculates and returns the total number of steps in the authentication work flow. Fire the authentication success
     * event handler when the current policy declares itself as the final step.
     */
    public int getCountAuthenticationSteps(Map<String, Object> configurationAttributes) {
        printlnDebug("GET_COUNT_AUTHENTICATION_STEPS");
        Integer authenticationSteps = sessionUtils.getAuthStep();
    	if (sessionUtils.getCurrentPolicy().isFinalStep()) {
    		handleAuthenticationSuccess();
    	} else {
            authenticationSteps = authenticationSteps + 1;
        }
        printlnDebug("GET_COUNT_AUTHENTICATION_STEPS step: " + authenticationSteps);
        return authenticationSteps;
    }

    /**
     * Handler method triggered when the user has successfully authenticated.
     */
    protected void handleAuthenticationSuccess() {
        printlnDebug("HANDLE_AUTHENTICATION_SUCCESS");
    	//
    }

    private void printlnDebug(String inputText){
        System.out.println("SECUREIA " + inputText);
    }

}
