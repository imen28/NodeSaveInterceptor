package com.jahia.overriding.interception;

import org.jahia.ajax.gwt.client.service.GWTCompositeConstraintViolationException;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.nodetypes.ExtendedPropertyDefinition;

import javax.jcr.RepositoryException;
import javax.jcr.nodetype.ConstraintViolationException;

/**
 * Created by Imen Ben Rhouma on 04/01/2016.
 */
public interface NodeSaveInterceptor {

    boolean canApplyOnNode(JCRNodeWrapper node) throws RepositoryException;
    void beforeNodeSave(JCRNodeWrapper node)
            throws ConstraintViolationException, RepositoryException, GWTCompositeConstraintViolationException;

}
