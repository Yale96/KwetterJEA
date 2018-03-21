/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeansForJsf;

import Models.Tweet;
import Services.TweetService;
import Services.UserService;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author Yannick van Leeuwen
 */
@Named(value = "tweetView")
@Dependent
public class TweetView {
    
    @Inject
    private TweetService tweetService;
    
    /**
     * Creates a new instance of TweetView
     */
    public TweetView() {
    }
    
    public List<Tweet> getAllTweets()
    {
        return tweetService.getTweets();
    }
}
