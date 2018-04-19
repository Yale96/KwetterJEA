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
import Models.Rol;
import Models.Tweet;
import Models.User;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Comparator.comparing;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import DAO.RolDao;

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
    
    @Inject @JPA
    private RolDao roleDao;
    
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
    
    public void addFollower(long id, String leaderName) {
        User u = userDao.findById(id);
        User ut = userDao.findByName(leaderName);
        if(u.getId() != ut.getId())
            userDao.addFollower(id, leaderName);
    }
    public void removeFollower(long id, String leaderName) {
        User u = userDao.findById(id);
        User ut = userDao.findByName(leaderName);
        if(u.getId() != ut.getId())
            userDao.removeFollower(id, leaderName);
    }
    
    public long getIdByTheName(String name)
    {
        return userDao.findIdByName(name);
    }
    
    public void edit(User user)
    {
        userDao.edit(user);
    }
    
    public void deleteTweet(long id, long tweetId) {
        getTweets(id).remove(getTweets(id).stream().filter(k->k.getId() == tweetId).findAny().orElse(null));
        
    }
    
    public void editRole(long id, long roleId) {
        User user = userDao.findById(id);
        Rol toSet = roleDao.findById(roleId);
        user.setRol(toSet);
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
        Profile p = new Profile();
        profileDao.create(p);
        User user = userDao.findById(id);
        user.setProfile(p);
        userDao.edit(user);
    }
    
    public Profile getProfileyName(String name)
    {
        return profileDao.findByName(name);
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
    
    public void removeLike(long id, long tweetId)
    {
        tweetDao.findById(tweetId).removeLike(userDao.findById(id));
    }
    
    public void addFlag(long id, long tweetId) {
        tweetDao.findById(tweetId).addFlag(userDao.findById(id));
    }
    
    public void removeFlag(long id, long tweetId)
    {
        tweetDao.findById(tweetId).removeFlag(userDao.findById(id));
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
        User u = userDao.getByName(name);
        String s = "Debug";
        return u;
    }
    
    public User registerUser(String userName, String password)
    {
        User u = userDao.register(userName, password);
        String s = "Debug";
        return u;
    }
    
    public boolean login(String userName, String password)
    {
        return userDao.login(userName, password);
    }
    
    public UserService(){

    }

    
}
