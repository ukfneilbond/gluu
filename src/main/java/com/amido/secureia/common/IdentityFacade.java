package com.amido.secureia.common;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Identity;

@Name(value="identityFacade")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class IdentityFacade {
    
    public Identity sessionInstance() {
        return Identity.instance();
    }

}
