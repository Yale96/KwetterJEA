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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Yannick van Leeuwen
 */
@Named(value = "tweetView")
@Dependent
public class TweetView {
    
    @Inject
    private TweetService tweetService;
    
    private Tweet selectedTweet;
    private List<Tweet> selectedTweets;
    private List<Tweet> tweets;
    /**
     * Creates a new instance of TweetView
     */
    public TweetView() {
    }
    
    public List<Tweet> getAllTweets()
    {
        tweets = tweetService.getTweets();
        return tweets;
    }
    
    public void deleteTweet(Long id)
    {
        Tweet tweet = tweetService.getById(id);
        tweetService.removeTweet(tweet.getId());
    }
    

    public Tweet getSelectedTweet() {
        return selectedTweet;
    }

    public void setSelectedTweet(Tweet selectedTweet) {
        this.selectedTweet = selectedTweet;
    }

    public List<Tweet> getSelectedTweets() {
        return selectedTweets;
    }

    public void setSelectedTweets(List<Tweet> selectedTweets) {
        this.selectedTweets = selectedTweets;
    }
    
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Tweet Selected", ((Tweet) event.getObject()).getContent());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
 
    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Tweet Unselected", ((Tweet) event.getObject()).getContent());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
