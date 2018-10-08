package com.amido.secureia.common;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

@Name(value="facesMessagesFacade")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class FacesMessagesFacade {
    
    public FacesMessages instance() {
        return FacesMessages.instance();
    }

}
