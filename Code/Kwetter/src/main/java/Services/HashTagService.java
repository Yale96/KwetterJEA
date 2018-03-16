/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.HashTagDao;
import DAO.JPA;
import DAO.ProfileDao;
import DAO.TweetDao;
import Interceptors.LoggingCheck;
import Models.HashTag;
import Models.Profile;
import Models.Tweet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Yannick van Leeuwen
 */
@Stateless @LoggingCheck
public class HashTagService {
    
    @Inject @JPA
    private HashTagDao hashTagDao;
    
    @Inject @JPA
    private TweetDao tweetDao;

    public void addHashTag(HashTag hashTag) {
        hashTagDao.create(hashTag);
    }

    public List<HashTag> getHashTags() {
        return hashTagDao.getHashTags();
    }

    public HashTag getById(Long id) {
        return hashTagDao.findById(id);
    }
    
    public void edit(long id, String content)
    {
        HashTag toEdit = hashTagDao.findById(id);
        toEdit.setContent(content);
        hashTagDao.edit(toEdit);
    }
    
    public void remove(long id)
    {
        HashTag toRemove = hashTagDao.findById(id);
        hashTagDao.remove(toRemove);
    }
    
    public List<Tweet> getTrends(String content) {
        HashTag hashtag = hashTagDao.getByContent(content);
        if (hashtag != null)
            return tweetDao.getTweetsByHashtagId(hashtag.getId());
        return new ArrayList<>();
    }
    
    
    
    public HashTagService(){

    }
}
