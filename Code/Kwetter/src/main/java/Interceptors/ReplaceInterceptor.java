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
import jdk.nashorn.internal.runtime.Debug;

/**
 *
 * @author Yannick van Leeuwen
 */
@ReplaceCheck
@Interceptor
public class ReplaceInterceptor {
    
    
    
    @AroundInvoke
    public Object replace(InvocationContext context) throws Exception {
        System.out.print("IN REPLACE INTERCEPTOR");
        Object[] parameters = context.getParameters();
        if (parameters.length > 0) {
            String tweetContent = (String) parameters[1];
            tweetContent = tweetContent.replace("vet", "dik");
            tweetContent = tweetContent.replace("cool", "gaaf");
            tweetContent = tweetContent.replace("#vet", "#dik");
            tweetContent = tweetContent.replace("#cool", "#gaaf");
            tweetContent = tweetContent.replace("Vet", "Dik");
            tweetContent = tweetContent.replace("Cool", "Gaaf");
            tweetContent = tweetContent.replace("#Vet", "#Dik");
            tweetContent = tweetContent.replace("#Cool", "#Gaaf");
            parameters[1] = tweetContent;
            context.setParameters(parameters);
        }
        return context.proceed();
    }
}
