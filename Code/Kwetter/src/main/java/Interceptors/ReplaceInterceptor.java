/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interceptors;

import Models.Tweet;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InterceptorBinding;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Yannick van Leeuwen
 */
@ReplaceCheck
@Interceptor
public class ReplaceInterceptor {

    @AroundInvoke
    public Object replace(InvocationContext context) throws Exception {
        Object[] parameters = context.getParameters();
        if (parameters.length > 0 && parameters[1] instanceof Tweet) {
            Tweet tweet = (Tweet) parameters[1];
            String content = tweet.getContent();
            content = content.replace("vet", "dik");
            tweet.setContent(content);
        }
        return context.proceed();
    }
}
