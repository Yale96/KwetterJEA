/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Models.HashTag;
import Models.Profile;
import Models.Tweet;
import Models.User;
import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Yannick van Leeuwen
 */
@Singleton
@Startup
public class StartUp {
    @Inject
    private UserService uService;
    @Inject
    private TweetService tService;
    @Inject
    private ProfileService pService;
    @Inject
    private HashTagService hService;
    
    static SentryClient sentry;
    
    public StartUp() {
        
    }

    @PostConstruct
    private void intData(){
        
        Profile pOne = new Profile("TestOne", "TestOne", "TestOne", "TestOne", "TestOne");
        
        User uOne = new User("yannickvanleeuwen@i-lion.nl", "", "Yale96", "Admin");
        uOne.setPassword("Yannick");
        User uTwo = new User("dennisvanleeuwen@i-lion.nl", "", "Dendi78", "Admin");
        uTwo.setPassword("Dennis");
        uOne.setProfile(pOne);
        
        Tweet tOne = new Tweet("Test", new Date());
        
        HashTag hOne = new HashTag("#DitIsEenTest");
        
        uService.addUser(uOne);
        uService.addUser(uTwo);
        
        pService.addProfile(pOne);
        
        tService.addTweet(tOne);
        
        hService.addHashTag(hOne);
        
        tService.logException();
    }
}
