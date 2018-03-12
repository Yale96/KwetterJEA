/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yannick van Leeuwen
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.count", query = "SELECT COUNT(u) FROM User u")})

@XmlRootElement
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_leaders",
             joinColumns = @JoinColumn(name = "super_id", referencedColumnName = "id", nullable = false),
             inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id", nullable = false))
    private List<User> supers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_followers",
             joinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id", nullable = false),
             inverseJoinColumns = @JoinColumn(name = "super_id", referencedColumnName = "id", nullable = false))
    private List<User> followers;

    @ManyToMany(mappedBy = "likes", cascade = CascadeType.MERGE)
    private List<Tweet> likes;

    @ManyToMany(mappedBy = "mentionedUsers", cascade = CascadeType.MERGE)
    private List<Tweet> mentions;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.MERGE)
    private List<Tweet> tweets;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @Column(nullable = false)
    private String rol;

    public User() {
        supers = new ArrayList<>();
        followers = new ArrayList<>();
        likes = new ArrayList<>();
        mentions = new ArrayList<>();
        tweets = new ArrayList<>();
    }

    public User(String email, String password, String username, String rol) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.rol = rol;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String hashstring = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            hashstring = hexString.toString();
        } catch (Exception x) {
            System.out.println(x);
        }
        this.password = (hashstring == null || hashstring.isEmpty()) ? password : hashstring;
    }

    public List<User> getLeaders() {
        return supers;
    }

    public void setLeaders(ArrayList<User> leaders) {
        this.supers = leaders;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    public List<Tweet> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Tweet> likes) {
        this.likes = likes;
    }

    public List<Tweet> getMentions() {
        return mentions;
    }

    public void setMentions(ArrayList<Tweet> mentions) {
        this.mentions = mentions;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(ArrayList<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<User> getSupers() {
        return supers;
    }

    public void setSupers(ArrayList<User> supers) {
        this.supers = supers;
    }
    
    public void addTweet(Tweet tweet) {
        if (tweet != null && tweets != null && !tweets.contains(tweet)) 
        {
            tweets.add(tweet);
            if (tweet.getOwner() != this) {
                tweet.setOwner(this);
            }
        }
    }
    
    public void removeTweet(Tweet tweet)
    {
        Tweet t = tweets.stream().filter(k -> k.getId() == tweet.getId()).findAny().orElse(null);
        if (tweet != null && tweets != null && tweets.contains(t)) {
            tweets.remove(t);
            if (t.getOwner() != this)
                t.setOwner(null);
        }
    }
    
    public void addLike(Tweet like) {
        if(like != null && likes != null && !likes.contains(like))
        {
            likes.add(like);
            if(!like.getLikes().contains(this))
                like.addLike(this);
        }
    }
    
    public void removeLike(Tweet tweet){
        Tweet t = likes.stream().filter(k -> k.getId() == tweet.getId()).findAny().orElse(null);
        if (tweet != null && likes != null && likes.contains(t)) {
            likes.remove(t);
            if (t.getLikes().contains(this))
                t.removeLike(this);
        }
    }
    
    public void addFollower(User follower){
        if(follower != null && followers != null && !followers.contains(follower))
        {
            followers.add(follower);
            if(!follower.getLeaders().contains(this))
                follower.addSuper(this);
        }
    }
    
    public void removeFollower(User follower){
        if (follower != null && followers != null && followers.contains(follower)) {
            followers.remove(follower);
            if (follower.getLeaders().contains(this))
                follower.removeSuper(this);
        }
    }
    
    public void addSuper(User superUser){
        if (superUser != null && supers != null && !supers.contains(superUser)) {
            supers.add(superUser);
            if (!superUser.getFollowers().contains(this))
                superUser.addFollower(this);
        }
    }
    
    public void removeSuper(User superUser){
        if (superUser != null && supers != null && supers.contains(superUser)) {
            supers.remove(superUser);
            if (superUser.getFollowers().contains(this))
                superUser.removeFollower(this);
        }
    }
    
    public void addMention(Tweet mention){
        if(mention != null && mentions != null && !mentions.contains(mention))
        {
            mentions.add(mention);
            if(!mention.getMentionedUsers().contains(this))
                mention.addMention(this);
        }
    }
}
