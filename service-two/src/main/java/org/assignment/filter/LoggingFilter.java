package org.assignment.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOGGER.infof("Request Method: %s, URI: %s", requestContext.getMethod(), requestContext.getUriInfo().getRequestUri());

        if (requestContext.hasEntity()) {
            InputStream entityStream = requestContext.getEntityStream();
            String requestBody = readStream(entityStream);
            LOGGER.info("Request body: " + requestBody);
            requestContext.setEntityStream(new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8)));
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        LOGGER.infof("Response Status: %d", responseContext.getStatus());
        if (responseContext.hasEntity()) {
            Object responseBody = responseContext.getEntity();
            LOGGER.info("Response body: " + responseBody);
        }
    }

    private String readStream(InputStream stream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = stream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        return byteArrayOutputStream.toString(StandardCharsets.UTF_8);
    }
}
