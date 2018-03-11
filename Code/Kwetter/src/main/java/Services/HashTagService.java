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
@Stateless
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
    
    public HashTag pushHashtag(String content) {
        if (hashTagDao.getByContent(content) != null)
            return hashTagDao.getByContent(content);

        HashTag hashtag = new HashTag();
        hashtag.setContent(content);
        hashTagDao.create(hashtag);
        return hashtag;
    }
    
    public List<HashTag> findHashtagsByPureContent(String content) {
        List<HashTag> hashtags = new ArrayList<>();
        int count = content.length() - content.replace("#", "").length();
        for (int i = 0; i < count; i++) {
            if (content.contains("#")) {
                int startPos = content.indexOf('#');
                content = content.substring(startPos);
                int endPos = content.substring(1).indexOf(' ');
                String hashtagContent;
                if (endPos > -1) {
                    hashtagContent = content.substring(0, endPos + 1);
                    content = content.substring(endPos + 1);
                } else {
                    hashtagContent = content;
                    content = "";
                }
                if (hashTagDao.getMatchesByContent(hashtagContent.substring(1)) == null)
                    pushHashtag(hashtagContent.substring(1));

                if (hashtags.stream().filter(h->h.getId() == hashTagDao.getByContent(hashtagContent.substring(1)).getId()).findAny().orElse(null) == null)
                    hashtags.add(hashTagDao.getByContent(hashtagContent.substring(1)));
            }
        }
        return hashtags;
    }
    
    public HashTagService(){

    }
}
