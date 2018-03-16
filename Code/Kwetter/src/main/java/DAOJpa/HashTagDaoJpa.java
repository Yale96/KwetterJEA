/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOJpa;

import DAO.DaoFacade;
import DAO.HashTagDao;
import DAO.JPA;
import Interceptors.LoggingCheck;
import Models.HashTag;
import Models.Profile;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Yannick van Leeuwen
 */
@Stateless @JPA @LoggingCheck
public class HashTagDaoJpa extends DaoFacade<HashTag> implements HashTagDao {
    @PersistenceContext
    private EntityManager em;
    
    public HashTagDaoJpa() {
        super(HashTag.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    //Will override
    @Override
    public HashTag findById(Long id) {
        TypedQuery<HashTag> query = em.createNamedQuery("HashTag.findById", HashTag.class);
        query.setParameter("id", id);
        List<HashTag> result = query.getResultList();
        System.out.println("count: " + result.size());
        return result.get(0);
    }
    
    @Override
    public HashTag getByContent(String content) {
        if (content == null || content.isEmpty())
            return null;

        try {
            return (HashTag) em.createQuery("select h from HashTag h where h.content like '" + content + "'").getSingleResult();
        }
        catch (Exception x) {
            return null;
        }
    }

    @Override
    public List<HashTag> getMatchesByContent(String content) {
        if (content == null || content.isEmpty())
            return new ArrayList<>();

        try {
            return (List<HashTag>) em.createQuery("select h from HashTag h where h.content like '" + content + "'").getResultList();
        }
        catch (Exception x) {
            return new ArrayList<>();
        }
    }
    
    @Override
    public ArrayList<HashTag> getHashTags() {
         Query query = em.createQuery("SELECT h FROM HashTag h");
         return  new ArrayList<>(query.getResultList());
    }
    
     public void setEm(EntityManager em) {
        this.em = em;
    }
}
