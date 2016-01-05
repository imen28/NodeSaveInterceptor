package com.jahia.overriding.interception.defaults;

import com.jahia.overriding.interception.NodeSaveInterceptor;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.jahia.services.content.JCRContentUtils;
import org.jahia.services.content.JCRNodeWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Imen Ben Rhouma on 18/12/2015.
 */
public abstract class BaseNodeSaveInterceptor implements NodeSaveInterceptor {


    protected static final Logger log = LoggerFactory.getLogger(BaseNodeSaveInterceptor.class);

    private Set<String> nodeTypes = Collections.emptySet();


     @Override
    public boolean canApplyOnNode(JCRNodeWrapper node)
            throws RepositoryException {
        // enforce constraints on the property name, required property type, selector type and node type if they were specified
        return  (getNodeTypes().size() == 0 || JCRContentUtils.isNodeType(node, getNodeTypes()));
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }

        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        BaseNodeSaveInterceptor rhs = (BaseNodeSaveInterceptor) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj))
                .append(getNodeTypes(), rhs.getNodeTypes()).isEquals();
    }

    public Set<String> getNodeTypes() {
        return nodeTypes;
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 37)
                .append(getNodeTypes()).toHashCode();
    }

    public void setNodeTypes(Set<String> nodeTypes) {
        if (nodeTypes != null) {
            this.nodeTypes = nodeTypes;
        } else {
            this.nodeTypes = Collections.emptySet();
        }
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }



}
