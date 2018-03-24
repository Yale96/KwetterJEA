/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import Models.HashTag;
import java.util.stream.Collectors;

/**
 *
 * @author Yannick van Leeuwen
 */
public class HashTagDTO implements ConvertToEntity<HashTag>{
    private long id;
    
    private String content;
    
    private long tweetsCount;
    
    public HashTagDTO()
    {
        
    }
    
    public HashTagDTO(long id, String content, long tweetsCount)
    {
        this.id = id;
        this.content = content;
        this.tweetsCount = tweetsCount;
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

    public long getTweetsCount() {
        return tweetsCount;
    }

    public void setTweetsCount(long tweetsCount) {
        this.tweetsCount = tweetsCount;
    }
    
    @Override
    public HashTag convert(DTOBase dtoBase)
    {
        HashTag hashTag = new HashTag();
        
        hashTag.setId(getId());
        hashTag.setContent(getContent());
        return hashTag;
    }
}
