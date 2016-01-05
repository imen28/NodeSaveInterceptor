package com.jahia.overriding.interception;

import org.jahia.ajax.gwt.client.data.definition.GWTJahiaNodeProperty;
import org.jahia.ajax.gwt.client.service.GWTCompositeConstraintViolationException;
import org.jahia.ajax.gwt.client.service.GWTJahiaServiceException;
import org.jahia.ajax.gwt.helper.ContentManagerHelper;
import org.jahia.ajax.gwt.helper.PropertiesHelper;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.NodeConstraintViolationException;
import org.jahia.utils.i18n.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Imen Ben Rhouma on 24/12/2015.
 */
public class CustomContentManagerHelper  extends ContentManagerHelper{
    private static final transient Logger logger = LoggerFactory.getLogger(CustomContentManagerHelper.class);
    private PropertiesHelper properties;
    private InterceptorNodeSaveList interceptorNodeSaveList;

    @Override
    public JCRNodeWrapper addNode(JCRNodeWrapper parentNode, String name, String nodeType, List<String> mixin, List<GWTJahiaNodeProperty> props, Locale uiLocale) throws GWTCompositeConstraintViolationException, GWTJahiaServiceException {
        if(!parentNode.hasPermission("{http://www.jcp.org/jcr/1.0}addChildNodes")) {
            throw new GWTJahiaServiceException(parentNode.getPath() + " - ACCESS DENIED");
        } else {
            JCRNodeWrapper childNode;
            try {

                parentNode.getSession().checkout(parentNode);
                childNode = parentNode.addNode(name, nodeType);
                if(mixin != null) {
                    Iterator e = mixin.iterator();

                    while(e.hasNext()) {
                        String m = (String)e.next();
                        childNode.addMixin(m);
                    }
                }
                this.properties.setProperties(childNode, props);
                interceptorNodeSaveList.beforeNodeSave(childNode);
            } catch (Exception var10) {
                if(var10 instanceof NodeConstraintViolationException){
                   this.properties.convertException((NodeConstraintViolationException)var10);
                }
                logger.error("Exception", var10);
                throw new GWTJahiaServiceException(Messages.getInternalWithArguments("label.gwt.error.cannot.get.node", uiLocale, new Object[]{var10.getLocalizedMessage()}));
            }

            if(childNode == null) {
                throw new GWTJahiaServiceException(Messages.getInternal("label.gwt.error.node.creation.failed", uiLocale));
            } else {
                return childNode;
            }
        }
    }

    @Override
    public void setProperties(PropertiesHelper properties) {
      this.properties=properties;
    }

    public InterceptorNodeSaveList getInterceptorNodeSaveList() {
        return interceptorNodeSaveList;
    }

    public void setInterceptorNodeSaveList(InterceptorNodeSaveList interceptorNodeSaveList) {
        this.interceptorNodeSaveList = interceptorNodeSaveList;
    }
}
