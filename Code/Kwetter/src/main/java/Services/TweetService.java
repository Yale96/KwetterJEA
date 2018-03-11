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
    
    public List<Tweet> getTrendingToppics(String hashTagContent) {
        HashTag hashtag = tagDao.getByContent(hashTagContent);
        if (hashtag != null)
            return tweetDao.getTweetsByHashtagId(hashtag.getId());
        return new ArrayList<>();
    }

    public List<Tweet> getTweetByHashtagId(long id) {
        return tweetDao.getTweetsByHashtagId(id);
    }
    
    
//    public Kweet sendNewTweet(long id, String inhoud) {
//        Kweet kweet = new Kweet();
//        kweet.setInhoud(inhoud);
//        kweet.setEigenaar(kwetteraarBaseService.getKwetteraar(id));
//        kweet.setDatum(new Date());
//        kweetBaseService.saveKweet(kweet);
//        List<Hashtag> hashtags = findHashtags(inhoud);
//        hashtags.forEach(h -> {
//            if (h != null)
//                kweet.addHashtag(h);
//        });
//        List<Kwetteraar> mentions = findMentions(inhoud);
//        mentions.forEach(m -> {
//            if (m != null) {
//                kweet.addMention(m);
//            }
//        });
//        return tweetDao.updateKweet(tweet);
//    }
//
//    //Tweets van user and leaders
//    public List<Kweet> getOwnAndOthersTweets(long id) {
//        List<Kweet> kweets = new ArrayList<>();
//        List<Kwetteraar> leiders = kwetteraarBaseService.getLeiders(id);
//        for (int i = 0; i < leiders.size(); i++) {
//            Kwetteraar kwetteraar = leiders.get(i);
//            List<Kweet> kwetKweets = kwetteraar.getKweets();
//            kweets.addAll(kwetKweets);
//        }
//        kweets.addAll(kwetteraarBaseService.getKweets(id));
//        int count = kweets.size();
//        if (count > 50)
//            count = 50;
//        kweets.sort(comparing(Kweet::getDatum));
//        Collections.reverse(kweets);
//        return kweets.subList(0, count);
//    }
    
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
    
    public TweetService(){

    }
}
