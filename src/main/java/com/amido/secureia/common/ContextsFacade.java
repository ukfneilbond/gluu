package com.amido.secureia.common;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Context;
import org.jboss.seam.contexts.Contexts;

@Name(value="contextsFacade")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class ContextsFacade {
    
    public Context sessionContext() {
        return Contexts.getSessionContext();
    }

    public Context eventContext() {
        return Contexts.getEventContext();
    }
}
