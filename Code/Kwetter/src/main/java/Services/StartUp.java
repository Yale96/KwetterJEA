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
        Profile pTwo = new Profile("Admin", "Tilburg, Netherlands", "localhost:4200", "Beheert Kwetter.", "https://scontent-amt2-1.xx.fbcdn.net/v/t1.0-1/p160x160/29513296_1186175838186308_7575881302090863203_n.jpg?_nc_cat=0&oh=0589767158c14270bd39109a08f463a2&oe=5B735461");
        
        User uOne = new User("yannickvanleeuwen@i-lion.nl", "", "Yale96", roleOne);
        uOne.setPassword("Yannick");
        User uTwo = new User("admin@kwetter.nl", "", "Admin", roleOne);
        uTwo.setPassword("Admin");
        uOne.setProfile(pOne);
        uTwo.setProfile(pTwo);
        
        Tweet tOne = new Tweet("Dit is een #TestTweet @Yale96", new Date());
        Tweet tTwo = new Tweet("Lekker weer #Zomer @Admin", new Date());
        Tweet tThree = new Tweet("Zullen we een #Biertje doen @Yale96", new Date());
        Tweet tFour = new Tweet("Dit is een tweede #TestTweet voor @Admin", new Date());
        Tweet tFive = new Tweet("Klaar voor de #Wedstrijd @Yale96", new Date());
        Tweet tSix = new Tweet("We hebben gewonnen, goed gespeeld! #DenHaagVerslagen @Yale96", new Date());
        Tweet tSeven = new Tweet("Reageer eens #DuurtLang @Admin", new Date());
        Tweet tEight = new Tweet("Gaan we vandaag weer Angular doen? #DatIsTof @Admin", new Date());
        
        uTwo.addTweet(tOne);
        tOne.setOwnerForStartup(uTwo);
        
        uOne.addTweet(tTwo);
        tTwo.setOwnerForStartup(uOne);
        
        uTwo.addTweet(tThree);
        tThree.setOwnerForStartup(uTwo);
        
        uOne.addTweet(tFour);
        tFour.setOwnerForStartup(uOne);
        
        uTwo.addTweet(tFive);
        tFive.setOwnerForStartup(uTwo);
        
        uTwo.addTweet(tSix);
        tSix.setOwnerForStartup(uTwo);
        
        uOne.addTweet(tSeven);
        tSeven.setOwnerForStartup(uOne);
        
        uOne.addTweet(tEight);
        tEight.setOwnerForStartup(uOne);
        
        HashTag hOne = new HashTag("#TestTweet");
        HashTag hTwo = new HashTag("#Zomer");
        HashTag hThree = new HashTag("#Biertje");
        HashTag hFour = new HashTag("#TestTweet");
        HashTag hFive = new HashTag("#Wedstrijd");
        HashTag hSix = new HashTag("#DenHaagVerslagen");
        HashTag hSeven = new HashTag("#DuurtLang");
        HashTag hEight = new HashTag("#DatIsTof");
        
        rService.addRole(roleOne);
        rService.addRole(roleTwo);
        
        uService.addUser(uOne);
        uService.addUser(uTwo);
        
        pService.addProfile(pOne);
        pService.addProfile(pTwo);
        
        tService.addTweet(tOne);
        tService.addTweet(tTwo);
        tService.addTweet(tThree);
        tService.addTweet(tFour);
        tService.addTweet(tFive);
        tService.addTweet(tSix);
        tService.addTweet(tSeven);
        tService.addTweet(tEight);
        
        hService.addHashTag(hOne);
        hService.addHashTag(hTwo);
        hService.addHashTag(hThree);
        hService.addHashTag(hFour);
        hService.addHashTag(hFive);
        hService.addHashTag(hSix);
        hService.addHashTag(hSeven);
        hService.addHashTag(hEight);
    }
}
