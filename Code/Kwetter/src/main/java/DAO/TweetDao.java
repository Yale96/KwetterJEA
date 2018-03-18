/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Profile;
import Models.Tweet;
import Models.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yannick van Leeuwen
 */
public interface TweetDao {
    void create(Tweet tweet);
    
    List<Tweet> getMatchesByContent(String inhoud);
    
    List<Tweet> getTweetsByUserId(long id);
    
    List<Tweet> getTweetsByHashtagId(long id);
    
    List<Tweet> getTweetsByMentionId(long id);
    
    List<Tweet> getRecentTweetsByUserId(long id);
    
    void edit(Tweet tweet);

    Tweet findById(Long id);
    
    void remove(Tweet tweet);

    ArrayList<Tweet> getTweets();
}
