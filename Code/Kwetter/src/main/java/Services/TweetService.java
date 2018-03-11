/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.JPA;
import DAO.TweetDao;
import DAO.UserDao;
import Models.Tweet;
import Models.User;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Yannick van Leeuwen
 */
@Stateless
public class TweetService {
    
    @Inject @JPA
    private TweetDao tweetDao;
    
    @Inject @JPA
    private UserDao userDao;

    public void addTweet(Tweet tweet) {
        tweetDao.create(tweet);
    }

    public List<Tweet> getTweets() {
        return tweetDao.getTweets();
    }

    public Tweet getById(Long id) {
        return tweetDao.findById(id);
    }
    
    public List<Tweet> getMatchesByContent(String content) {
        return tweetDao.getMatchesByContent(content);
    }

    public List<Tweet> getTweetByHashtagId(long id) {
        return tweetDao.getTweetsByHashtagId(id);
    }

    public List<Tweet> getKweetsByMentionId(long id) {
        List<Tweet> kweets = tweetDao.getTweetsByMentionId(id);
        return kweets;
    }

    public List<Tweet> getRecentTweetsByUserId(long id) {
        List<Tweet> tweets = tweetDao.getRecentTweetsByUserId(id);
        Collections.reverse(tweets);
        return tweets;
    }
    
    public void removeTweet(long tweetId)
    {
        userDao.deleteTweet(tweetDao.findById(tweetId).getOwner().getId(), tweetId);
        
    }
    
    public TweetService(){

    }
}
