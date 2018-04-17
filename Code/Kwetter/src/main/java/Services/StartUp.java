/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Models.HashTag;
import Models.Profile;
import Models.Rol;
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
    @Inject
    private RoleService rService;
    
    static SentryClient sentry;
    
    public StartUp() {
        
    }

    @PostConstruct
    private void intData(){
        Rol roleOne = new Rol("admin", "SuperUser");
        Rol roleTwo = new Rol("user", "User of Kwetter");
        
        Profile pOne = new Profile("Yannick van Leeuwen", "Tilburg, Netherlands", "www.nu.nl", "Sportief en gaat graag op stap.", "https://scontent-amt2-1.xx.fbcdn.net/v/t1.0-1/p160x160/29513296_1186175838186308_7575881302090863203_n.jpg?_nc_cat=0&oh=0589767158c14270bd39109a08f463a2&oe=5B735461");
        Profile pTwo = new Profile("Dennis van Leeuwen", "Tilburg, Netherlands", "www.nu.nl", "Sportief en gaat graag op stap.", "https://scontent-amt2-1.xx.fbcdn.net/v/t1.0-1/p160x160/29513296_1186175838186308_7575881302090863203_n.jpg?_nc_cat=0&oh=0589767158c14270bd39109a08f463a2&oe=5B735461");
        
        User uOne = new User("yannickvanleeuwen@i-lion.nl", "", "Yale96", roleOne);
        uOne.setPassword("Yannick");
        User uTwo = new User("dennisvanleeuwen@i-lion.nl", "", "Admin", roleOne);
        uTwo.setPassword("Admin");
        uOne.setProfile(pOne);
        uTwo.setProfile(pTwo);
        
        //Tweet tOne = new Tweet("Test", new Date());
        HashTag hOne = new HashTag("#DitIsEenTest");
        rService.addRole(roleOne);
        rService.addRole(roleTwo);
        
        uService.addUser(uOne);
        uService.addUser(uTwo);
        
        pService.addProfile(pOne);
        pService.addProfile(pTwo);
        
        //tService.addTweet(tOne);
        
        hService.addHashTag(hOne);
    }
}
