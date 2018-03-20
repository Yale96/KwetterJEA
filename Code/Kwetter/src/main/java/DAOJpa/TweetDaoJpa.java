/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOJpa;

import DAO.DaoFacade;
import DAO.JPA;
import DAO.TweetDao;
import Models.HashTag;
import Models.Tweet;
import io.sentry.Sentry;
import io.sentry.event.Event;
import io.sentry.event.EventBuilder;
import io.sentry.event.interfaces.ExceptionInterface;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 *
 * @author Yannick van Leeuwen
 */
@Stateless @JPA 
public class TweetDaoJpa extends DaoFacade<Tweet> implements TweetDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public TweetDaoJpa() {
        super(Tweet.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public List<Tweet> getMatchesByContent(String content) {
        if (content == null)
            return new ArrayList<>();
        return spareUnnecessaryWork("select t from Tweet t where t.content LIKE '%" + content + "%'");
    }
    
    @Override
    public List<Tweet> getTweetsByUserId(long id)
    {
        if (id >= 0)
            return spareUnnecessaryWork("select t from Tweet t where t.owner_id = " + id + "");
        return new ArrayList<>();
    }
    
    @Override
    public List<Tweet> getTweetsByHashtagId(long id) {
        if (id >= 0)
            return spareUnnecessaryWork("select t from Tweet t where t.id = (select x.tweet_hashtag_id from tweet_hashtag x where x.hashtag_hashtag_id = " + id + ")");
        return new ArrayList<>();
    }

    @Override
    public List<Tweet> getTweetsByMentionId(long id) {
        if (id >= 0) 
            return spareUnnecessaryWork("select t from Tweet t where t.content LIKE '%@ (select user.name from user u where u.id = " + id + ") %'");
        return new ArrayList<>();
    }
    
    @Override
    public List<Tweet> getRecentTweetsByUserId(long id) {
        if (id >= 0)
            return spareUnnecessaryWork("select t from Tweet t where t.owner_id = " + id + " order by t.timestamp desc");
        return new ArrayList<>();
    }
    
    //@Transactional
    @Override
    public Tweet findById(Long id) {
        TypedQuery<Tweet> query = em.createNamedQuery("Tweet.findById", Tweet.class);
        query.setParameter("id", id);
        List<Tweet> result = query.getResultList();
        System.out.println("count: " + result.size());
        return result.get(0);
    }

    @Override
    public ArrayList<Tweet> getTweets() {
         Query query = em.createQuery("SELECT t FROM Tweet t");
         return  new ArrayList<>(query.getResultList());
    }    
    
    public List<Tweet> spareUnnecessaryWork(String query) {
        if (query == null || query.isEmpty())
            return new ArrayList<>();

        try {
            return (List<Tweet>) em.createQuery(query).getResultList();
        }
        catch (Exception x) {
            return new ArrayList<>();
        }
    }
}
