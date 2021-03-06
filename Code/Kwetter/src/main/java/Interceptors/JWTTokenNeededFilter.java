/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interceptors;
import Utils.KeyGenerator;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.util.logging.Logger;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Yannick van Leeuwen
 */
@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {
    
    @Inject
    private Logger logger;
    
    @Inject
    private KeyGenerator keyGenerator;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        
        logger.info("IN THE TOKEN INTERCEPTOR");
        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String s = "Debug";
        logger.info("#### authorizationHeader : " + authorizationHeader);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.severe("#### invalid authorizationHeader : " + authorizationHeader);
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();
        String S = "Debug";

        try {
            Key key = keyGenerator.generateKey();
            UriInfo uriInfo = requestContext.getUriInfo();
            String shit = "debug";
            MultivaluedMap<String, String> queryParams =  uriInfo.getQueryParameters();
            String pageQuery = queryParams.getFirst("name");
            String user = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
            if(pageQuery.equals(user))
            {
                Jwts.parser().setSigningKey(key).parseClaimsJws(token);
                String sss = "Debug";
                logger.info("#### valid token : " + token);
            }
            else
            {
                throw new NotAuthorizedException("Authorization header must be provided");
            }
        } catch (Exception e) {
            logger.severe("#### invalid token : " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
    
}
