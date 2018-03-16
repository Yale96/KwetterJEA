/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interceptors;

import DAOJpa.UserDaoJpa;
import io.sentry.Sentry;
import io.sentry.event.Event;
import io.sentry.event.EventBuilder;
import io.sentry.event.interfaces.ExceptionInterface;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author yanni
 */
public class LoggingInterceptor {
    
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception{
        try
        {
            return context.proceed();
        }
        catch(Exception e)
        {
            EventBuilder eventBuilder = new EventBuilder()
                               .withMessage("Exception caught")
                               .withLevel(Event.Level.ERROR)
                               .withLogger(context.getClass().getName())
                               .withSentryInterface(new ExceptionInterface(e));

               Sentry.capture(eventBuilder);
            return null;
        }
    }
}
