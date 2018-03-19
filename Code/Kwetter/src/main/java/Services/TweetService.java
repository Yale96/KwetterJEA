/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.HashTagDao;
import DAO.JPA;
import DAO.TweetDao;
import DAO.UserDao;
import Interceptors.LoggingCheck;
import Interceptors.ReplaceCheck;
import Models.HashTag;
import Models.Tweet;
import Models.User;
import java.util.ArrayList;
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
    
    @Inject @JPA
    private HashTagDao tagDao;
    
    public void editTweet(Tweet tweet)
    {
        tweetDao.edit(tweet);
    }
    
    public void addTweet(Tweet tweet) {
        tweetDao.create(tweet);
    }

    public List<Tweet> getTweets() {
        return tweetDao.getTweets();
    }

    public Tweet getById(Long id) {
        return tweetDao.findById(id);
    }
    
    public HashTag pushHashtag(String content) {
        if (tagDao.getByContent(content) != null)
            return tagDao.getByContent(content);

        HashTag hashtag = new HashTag();
        hashtag.setContent(content);
        tagDao.create(hashtag);
        return hashtag;
    }
    
    public List<Tweet> getMatchesByContent(String content) {
        return tweetDao.getMatchesByContent(content);
    }
    
    public List<Tweet> getTrendingToppics(String hashTagContent) {
        HashTag hashtag = tagDao.getByContent(hashTagContent);
        if (hashtag != null)
            return tweetDao.getTweetsByHashtagId(hashtag.getId());
        return new ArrayList<>();
    }
    
    public List<HashTag> findHashtagsByPureContent(String content) {
        List<HashTag> hashtags = new ArrayList<>();
        int count = content.length() - content.replace("#", "").length();
        for (int i = 0; i < count; i++) {
            if (content.contains("#")) {
                int startPos = content.indexOf('#');
                content = content.substring(startPos);
                int endPos = content.substring(1).indexOf(' ');
                String hashtagContent;
                if (endPos > -1) {
                    hashtagContent = content.substring(0, endPos + 1);
                    content = content.substring(endPos + 1);
                } else {
                    hashtagContent = content;
                    content = "";
                }
                if (tagDao.getByContent(hashtagContent) == null)
                    pushHashtag(hashtagContent);

                if (hashtags.stream().filter(h->h.getId() == tagDao.getByContent(hashtagContent).getId()).findAny().orElse(null) == null)
                    hashtags.add(tagDao.getByContent(hashtagContent));
            }
        }
        return hashtags;
    }

    public List<Tweet> getTweetByHashtagId(long id) {
        return tweetDao.getTweetsByHashtagId(id);
    }
    
    @ReplaceCheck
    public void sendNewTweet(long id, String inhoud) {
        Tweet tweet = new Tweet();
        tweet.setContent(inhoud);
        tweet.setOwner(userDao.findById(id));
        tweet.setTimeStamp(new Date());
        tweetDao.create(tweet);
        List<HashTag> tags = findHashtagsByPureContent(inhoud);
        tags.forEach(h -> {
            if (h != null)
                tweet.addHashTag(h);
                
        });
        List<User> mentions = findMentions(inhoud);
        mentions.forEach(m -> {
            if (m != null) {
                tweet.addMention(m);
            }
        });
        tweetDao.edit(tweet);
    }
    
    public List<Tweet> getTweetsByMentionId(long id) {
        List<Tweet> kweets = tweetDao.getTweetsByMentionId(id);
        return kweets;
    }
    
    public List<Tweet> getTweetsByUserId(long id) {
        List<Tweet> kweets = tweetDao.getTweetsByUserId(id);
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
        long toRemove = tweetDao.findById(tweetId).getOwner().getId();
        Tweet t = getById(toRemove);
        tweetDao.remove(t);
        String s = "Debug";
    }
    
    public List<User> findMentions(String content) {
        List<User> mentions = new ArrayList<>();
        int count = content.length() - content.replace("@", "").length();
        for (int i = 0; i < count; i++) {
            if (content.contains("@")) {
                int startPos = content.indexOf('@');
                content = content.substring(startPos);
                int endPos = content.substring(1).indexOf(' ');
                String mentioned;
                if (endPos > -1) {
                    mentioned = content.substring(0, endPos + 1);
                    content = content.substring(endPos + 1);
                } else {
                    mentioned = content;
                    content = "";
                }
                mentions.add(userDao.getByName(mentioned.substring(1)));
            }
        }
        return mentions;
    }

    @LoggingCheck
    public void throwsSome()
    {
        throw new UnsupportedOperationException("Roep dit niet aan in je endpoints!");
    }
    
    /**
     *
     */
    @LoggingCheck
    public void logException() {
        try
        {
            throwsSome();
        }
        catch(Exception x)
        {
            
        } 
       }
    
    public TweetService(){
        
        
    }
}
