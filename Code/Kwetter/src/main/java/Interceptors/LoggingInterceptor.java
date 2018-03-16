/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interceptors;


import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author yanni
 */
@LoggingCheck
@Interceptor
public class LoggingInterceptor {
        
    private static final Logger sentrylogger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        try {
            System.out.print("IN INTERCEPTOR");
            return context.proceed();
        } catch (Exception x) {
            sentrylogger.error("An uncaught exception had been thrown.", x);
            throw x;
        }
    }
}
