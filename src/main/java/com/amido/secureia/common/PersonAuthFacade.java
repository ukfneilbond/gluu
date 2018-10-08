package com.amido.secureia.common;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name(value="personAuthFacade")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class PersonAuthFacade {

    public PersonAuth instance() {
        return (PersonAuth) Component.getInstance("personAuth", ScopeType.SESSION);
    }

}
