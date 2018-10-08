package com.amido.secureia.service;

import com.amido.secureia.common.ApplicationProperties;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("serviceInjector")
@Scope(ScopeType.APPLICATION)
public class ServiceInjector {

    @In(value="applicationProperties", required=true, create=true)
    private ApplicationProperties applicationProperties;

    public Object injectEmailService() {
        String name = applicationProperties.get(applicationProperties.EMAIL_SERVICE_NAME);
        Object result = Component.getInstance(name);
        return result;
    }
}
