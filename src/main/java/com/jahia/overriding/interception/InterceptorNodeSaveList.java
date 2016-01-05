package com.jahia.overriding.interception;

import org.jahia.ajax.gwt.client.service.GWTCompositeConstraintViolationException;
import org.jahia.services.content.JCRNodeWrapper;

import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Imen Ben Rhouma on 18/12/2015.
 */
public class InterceptorNodeSaveList {

    private List<NodeSaveInterceptor> interceptors;
    public InterceptorNodeSaveList(){
        interceptors = new ArrayList<NodeSaveInterceptor>();
    }


    public void addInterceptor(NodeSaveInterceptor interceptor) {
        this.interceptors.add(interceptor);
    }
    public void removeInterceptor(NodeSaveInterceptor interceptor) {
        this.interceptors.remove(interceptor);
    }
    public void setInterceptors(List<NodeSaveInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

    /**
     *

     * @throws RepositoryException
     * @throws GWTCompositeConstraintViolationException
     */
    void beforeNodeSave(JCRNodeWrapper node)
            throws RepositoryException, GWTCompositeConstraintViolationException {
        for (NodeSaveInterceptor interceptor : interceptors) {
            if (interceptor.canApplyOnNode(node)) {
                interceptor.beforeNodeSave(node);
            }
        }

    }



}
