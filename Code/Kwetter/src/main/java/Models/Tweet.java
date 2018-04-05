/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Yannick van Leeuwen
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Tweet.findById", query = "SELECT t FROM Tweet t WHERE t.id = :id"),
    @NamedQuery(name = "Tweet.count", query = "SELECT COUNT(t) FROM Tweet t")})
public class Tweet implements Serializable {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false)
    private String content;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    
    @ManyToMany
    @JoinTable(name = "tweet_hashtag"
            , joinColumns = @JoinColumn(name = "tweet_hashtag_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "hashtag_hashtag_id", referencedColumnName = "id"))
    private List<HashTag> hashtags;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;
    
    @ManyToMany
    @JoinTable(name = "tweet_tweeter_mentions"
            , joinColumns = @JoinColumn(name = "tweet_mention_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "user_mention_id", referencedColumnName = "id"))
    private List<User> mentionedUsers;
    
    @ManyToMany
    @JoinTable(name = "tweet_user_likes"
            , joinColumns = @JoinColumn(name = "tweet_like_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "user_like_id", referencedColumnName = "id"))
    private List<User> likes;
    
    @ManyToMany
    @JoinTable(name = "tweet_user_flags"
            , joinColumns = @JoinColumn(name = "tweet_flag_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "user_flag_id", referencedColumnName = "id"))
    private List<User> flags;
    
    
    public Tweet()
    {
       hashtags = new ArrayList<HashTag>();
       mentionedUsers = new ArrayList<User>();
       likes = new ArrayList<User>(); 
       flags = new ArrayList<User>();
    }
    
    public Tweet(String content, Date timeStamp)
    {
        this.content = content;
        this.timeStamp = timeStamp;
        
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
        if(!owner.getTweets().contains(this))
        {
            owner.addTweet(this);
        }
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<HashTag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<HashTag> hashtags) {
        this.hashtags = hashtags;
    }

    public List<User> getMentionedUsers() {
        return mentionedUsers;
    }

    public void setMentionedUsers(ArrayList<User> mentionedUsers) {
        this.mentionedUsers = mentionedUsers;
    }

    public List<User> getLikes() {
        return likes;
    }

    public List<User> getFlags() {
        return flags;
    }

    public void setFlags(List<User> flags) {
        this.flags = flags;
    }
    
    public void setLikes(ArrayList<User> likes) {
        this.likes = likes;
    }
    
    public void addHashTag(HashTag hashTag){
        if(hashTag != null && hashtags != null && !hashtags.contains(hashTag))
        {
            hashtags.add(hashTag);
            if(!hashTag.getTweets().contains(this))
                hashTag.addTweet(this);
        }
    }
    
    public void addMention(User mention){
        if(mention != null && mentionedUsers != null && !mentionedUsers.contains(mention))
        {
            mentionedUsers.add(mention);
            if(!mention.getMentions().contains(this))
                mention.addMention(this);
        }
    }
    
    public void addLike(User like){
        if(like != null && likes != null && !likes.contains(like))
        {
            likes.add(like);
            if(!like.getTweets().contains(this))
                like.addLike(this);
        }
    }
    
    public void removeLike(User like){
        User user = likes.stream().filter(k -> k.getId() == like.getId()).findAny().orElse(null);
        if (like != null && likes != null && likes.contains(user)) {
            likes.remove(user);
            if (user.getTweets().contains(this))
                user.removeLike(this);
        }
    }
    
    public void addFlag(User flag){
        if(flag != null && flags != null && !flags.contains(flag))
        {
            flags.add(flag);
            if(!flag.getTweets().contains(this))
                flag.addFlag(this);
        }
    }
    
    public void removeFlag(User flag){
        User user = flags.stream().filter(k -> k.getId() == flag.getId()).findAny().orElse(null);
        if (flag != null && flags != null && flags.contains(user)) {
            flags.remove(user);
            if (user.getTweets().contains(this))
                user.removeFlag(this);
        }
    }
}
