package org.ua.oblik.rs.config;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.ua.oblik.soap.client.RedirectException;
import org.ua.oblik.soap.client.RedirectException_Exception;

@Provider
public class RedirectExceptionMapper implements ExceptionMapper<RedirectException_Exception> {

    @Override
    public Response toResponse(RedirectException_Exception e) {
        RedirectException faultInfo = e.getFaultInfo();
        return Response.status(faultInfo.getHttpStatus()).entity(faultInfo.getMessage()).build();
    }
}
