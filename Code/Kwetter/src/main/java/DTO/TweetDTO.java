/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Date;

/**
 *
 * @author Yannick van Leeuwen
 */
public class TweetDTO {
    private long id;
    
    private UserDTO owner;
    
    private String content;
    
    private Date timeStamp;
    
    private long tagsCount;
    
    private long mentionsCount;
    
    private long likesCount;
    
    private long flagsCount;
    
    private long responsesCount;
    
    public TweetDTO()
    {
        
    }
   
    public TweetDTO(long id, UserDTO owner, String content, Date timeStamp, long tagsCount, long mentionsCount, long likesCount, long flagsCount, long responsesCount)
    {
        this.id = id;
        this.owner = owner;
        this.content = content;
        this.timeStamp = timeStamp;
        this.tagsCount = tagsCount;
        this.mentionsCount = mentionsCount;
        this.likesCount = likesCount;
        this.flagsCount = flagsCount;
        this.responsesCount = responsesCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
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

    public long getResponsesCount() {
        return responsesCount;
    }

    public void setResponsesCount(long responsesCount) {
        this.responsesCount = responsesCount;
    }
    
    
}
