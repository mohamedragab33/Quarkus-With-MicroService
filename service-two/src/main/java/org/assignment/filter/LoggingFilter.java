package org.assignment.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOGGER.infof("Request Method: %s, URI: %s", requestContext.getMethod(), requestContext.getUriInfo().getRequestUri());
        // Log request body if applicable (you can also inspect headers)
        if (requestContext.hasEntity()) {
            LOGGER.info("Request body: " + requestContext.getEntityStream());
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        LOGGER.infof("Response Status: %d", responseContext.getStatus());
        // Log response entity if applicable
        if (responseContext.hasEntity()) {
            LOGGER.info("Response body: " + responseContext.getEntity());
        }
    }
    
}
