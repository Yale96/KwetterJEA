/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Tweet;
import Models.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yannick van Leeuwen
 */
public interface UserDao {
    void create(User user);
    
    User getByName(String name);
    
    void addFollower(long id, long idLeider);
    
    void removeFollower(long id, long idLeider);
    
    List<Tweet> getTweets(long id);
    
    public void deleteTweet(long id, long tweetId);
    
    void edit(User user);
    
    void remove(User user);

    User findById(Long id);

    List<User> getUsers();
    
    void register(String userName, String password);
    
    boolean login(String userName, String password);
}
