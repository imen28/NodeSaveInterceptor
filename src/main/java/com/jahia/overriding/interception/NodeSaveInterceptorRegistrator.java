package com.jahia.overriding.interception;

import com.jahia.overriding.interception.defaults.BaseNodeSaveInterceptor;
import org.jahia.bin.listeners.JahiaContextLoaderListener;
import org.jahia.services.content.interceptor.BaseInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by Imen Ben Rhouma on 04/01/2016.
 */
public class NodeSaveInterceptorRegistrator implements InitializingBean, DisposableBean {


    private static final Logger logger = LoggerFactory.getLogger(NodeSaveInterceptorRegistrator.class);
    private NodeSaveInterceptor nodeSaveInterceptor;
    private InterceptorNodeSaveList interceptorNodeSaveList;
    public NodeSaveInterceptorRegistrator() {
    }

    public void afterPropertiesSet() throws Exception {
        if (this.nodeSaveInterceptor != null) {
            logger.info("Registering node save interceptor {}", this.nodeSaveInterceptor);

            this.interceptorNodeSaveList.removeInterceptor(this.nodeSaveInterceptor);
            this.interceptorNodeSaveList.addInterceptor(this.nodeSaveInterceptor);
        }
    }

    public void destroy() throws Exception {
        if (JahiaContextLoaderListener.isRunning() && this.interceptorNodeSaveList != null && this.nodeSaveInterceptor != null) {
            if (this.nodeSaveInterceptor instanceof BaseInterceptor) {
                logger.info("Unregistering node save interceptor " + this.nodeSaveInterceptor.getClass().getName() + " for types " + ((BaseNodeSaveInterceptor) this.nodeSaveInterceptor).getNodeTypes()) ;



            } else {
                logger.info("Unregistering node save interceptor {}", this.nodeSaveInterceptor.getClass().getName());
            }

            this.interceptorNodeSaveList.removeInterceptor(this.nodeSaveInterceptor);
        }

    }


    public void setInterceptorNodeSaveList(InterceptorNodeSaveList interceptorNodeSaveList) {
        this.interceptorNodeSaveList = interceptorNodeSaveList;
    }

    public void setNodeSaveInterceptor(NodeSaveInterceptor nodeSaveInterceptor) {
        this.nodeSaveInterceptor = nodeSaveInterceptor;
    }
}