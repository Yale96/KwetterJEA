/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Yannick van Leeuwen
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "HashTag.findById", query = "SELECT h FROM HashTag h WHERE h.id = :id"),
    @NamedQuery(name = "HashTag.count", query = "SELECT COUNT(h) FROM HashTag h")})
public class HashTag implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String content;

    @ManyToMany(mappedBy="hashtags", cascade = { CascadeType.MERGE, CascadeType.PERSIST})
    private List<Tweet> tweets;
    
    public HashTag()
    {
        tweets = new ArrayList<Tweet>();
    }
    
    public HashTag(String content)
    {
        this.content = content;
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

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(ArrayList<Tweet> tweets) {
        this.tweets = tweets;
    }
    
    public void addTweet(Tweet tweet){
        if (tweet != null && tweets != null && !tweets.contains(tweet)) {
            tweets.add(tweet);
            if (!tweet.getHashtags().contains(this))
                tweet.addHashTag(this);
        }
    }
}
