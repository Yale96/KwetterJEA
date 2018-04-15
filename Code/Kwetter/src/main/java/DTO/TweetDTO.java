/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import Models.Tweet;
import java.util.Date;

/**
 *
 * @author Yannick van Leeuwen
 */
public class TweetDTO {
    private long id;
    
    private String owner;
    
    private String content;
    
    private Date timeStamp;
    
    private long tagsCount;
    
    private long mentionsCount;
    
    private long likesCount;
    
    private long flagsCount;
    
    public TweetDTO()
    {
        
    }
    
    public TweetDTO(Tweet tweet)
    {
        this.id = tweet.getId();
        this.content = tweet.getContent();
        this.timeStamp = tweet.getTimeStamp();
        this.tagsCount = tweet.getHashtags().size();
        this.flagsCount = tweet.getFlags().size();
        this.likesCount = tweet.getLikes().size();
        this.mentionsCount = tweet.getMentionedUsers().size();
        this.owner = tweet.getOwner().getUsername();
    }
   
    public TweetDTO(long id, String owner, String content, Date timeStamp, long tagsCount, long mentionsCount, long likesCount, long flagsCount)
    {
        this.id = id;
        this.owner = owner;
        this.content = content;
        this.timeStamp = timeStamp;
        this.tagsCount = tagsCount;
        this.mentionsCount = mentionsCount;
        this.likesCount = likesCount;
        this.flagsCount = flagsCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public long getTagsCount() {
        return tagsCount;
    }

    public void setTagsCount(long tagsCount) {
        this.tagsCount = tagsCount;
    }

    public long getMentionsCount() {
        return mentionsCount;
    }

    public void setMentionsCount(long mentionsCount) {
        this.mentionsCount = mentionsCount;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public long getFlagsCount() {
        return flagsCount;
    }

    public void setFlagsCount(long flagsCount) {
        this.flagsCount = flagsCount;
    }
}
