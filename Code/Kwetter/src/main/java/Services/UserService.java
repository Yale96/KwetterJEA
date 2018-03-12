/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.HashTagDao;
import DAO.UserDao;
import DAO.JPA;
import DAO.ProfileDao;
import DAO.TweetDao;
import Models.HashTag;
import Models.Profile;
import Models.Tweet;
import Models.User;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Comparator.comparing;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Yannick van Leeuwen
 */
@Stateless
public class UserService {
    
    @Inject @JPA
    private UserDao userDao;
    
    @Inject @JPA
    private ProfileDao profileDao;
    
    @Inject @JPA
    private TweetDao tweetDao;
    
    @Inject @JPA
    private HashTagDao tagDao;
    
    public void addUser(User user) {
        userDao.create(user);
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }
    
    public List<Tweet> getTweets(long id) {
        return userDao.findById(id).getTweets();
    }

    public User getById(Long id) {
        return userDao.findById(id);
    }
    
    public void addFollower(long id, long idLeader) {
        if (id != idLeader)
            userDao.addFollower(id, idLeader);
    }
    public void removeVolger(long id, long idLeader) {
        if (id != idLeader)
            userDao.removeFollower(id, idLeader);
    }
    
    public void edit(User user)
    {
        userDao.edit(user);
    }
    
    public void deleteTweet(long id, long tweetId) {
        getTweets(id).remove(getTweets(id).stream().filter(k->k.getId() == tweetId).findAny().orElse(null));
    }
    
    public void editRole(long id, String newRol) {
        User user = userDao.findById(id);
        user.setRol(newRol);
        userDao.edit(user);
    }
    
    public void editMail(long id, String mail) {
        User user = userDao.findById(id);
        user.setEmail(mail);
        userDao.edit(user);
    }
    
    public void editName(long id, String name) {
        User user = userDao.findById(id);
        user.setUsername(name);
        userDao.edit(user);
    }
    
    public void addProfile(long id, long profileId)
    {
        Profile toAdd = profileDao.findById(profileId);
        User user = userDao.findById(id);
        user.setProfile(toAdd);
        userDao.edit(user);
    }
    
    public List<Tweet> getOwnAndOthersTweets(long id) {
        List<Tweet> tweets = new ArrayList<>();
        List<User> leaders = getLeaders(id);
        for (int i = 0; i < leaders.size(); i++) {
            User user = leaders.get(i);
            List<Tweet> userTweets = user.getTweets();
            tweets.addAll(userTweets);
        }
        tweets.addAll(userDao.getTweets(id));
        int count = tweets.size();
        if (count > 50)
            count = 50;
        tweets.sort(comparing(Tweet::getTimeStamp));
        Collections.reverse(tweets);
        return tweets.subList(0, count);
    }
    
    public void addLike(long id, long tweetId) {
        tweetDao.findById(tweetId).addLike(userDao.findById(id));
    }
    
    public List<User> getLeaders(long id)
    {
        return userDao.findById(id).getLeaders();
    }
    
    public List<User> getFollowers(long id)
    {
        return userDao.findById(id).getFollowers();
    }
    
    public User getByName(String name) {
        return userDao.getByName(name);
    }
    
    public UserService(){

    }

    
}
