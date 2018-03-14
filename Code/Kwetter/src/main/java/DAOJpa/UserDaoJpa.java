/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOJpa;

import DAO.DaoFacade;
import DAO.JPA;
import DAO.UserDao;
import Models.Tweet;
import Models.User;
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
@Stateless @JPA
public class UserDaoJpa extends DaoFacade<User> implements UserDao {
    @PersistenceContext
    private EntityManager em;
    
    public UserDaoJpa() {
        super(User.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public User getByName(String name) {
        if (name == null || name.isEmpty())
            return null;

        try {
            return (User) em.createQuery("select u from User u where u.username = '" + name + "'").getSingleResult();
        }
        catch (Exception x) {
            return null;
        }
    }
    
    @Override
    public void deleteTweet(long id, long tweetId) {
        findById(id).removeTweet(getTweets(id).stream().filter(k->k.getId() == tweetId).findAny().orElse(null));
        String s = "Debug";
    }
    
    @Override
    public void addFollower(long id, long idLeider) {
        if (id >= 0 && idLeider >= 0) {
            try {
                User volger = find(id);
                User leider = find(idLeider);
                leider.addFollower(volger);
                update(volger);
                update(leider);
            }
            catch (Exception x) {
                System.out.println(x);
            }
        }
    }

    @Override
    public void removeFollower(long id, long idLeider) {
        if (id >= 0 && idLeider >= 0) {
            try {
                User volger = find(id);
                User leider = find(idLeider);
                leider.removeFollower(volger);
                update(volger);
                update(leider);
            }
            catch (Exception x) {
                System.out.println(x);
            }
        }
    }
    
    @Override
    public List<Tweet> getTweets(long id)
    {
        return findById(id).getTweets();
    }
    
    @Override
    public User findById(Long id) {
        TypedQuery<User> query = em.createNamedQuery("User.findById", User.class);
        query.setParameter("id", id);
        List<User> result = query.getResultList();
        System.out.println("count: " + result.size());
        return result.get(0);
    }

    @Override
    public ArrayList<User> getUsers() {
         Query query = em.createQuery("SELECT u FROM User u");
         return  new ArrayList<>(query.getResultList());
    }
}
