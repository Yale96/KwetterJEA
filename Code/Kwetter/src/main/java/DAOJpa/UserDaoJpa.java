/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOJpa;

import DAO.DaoFacade;
import DAO.JPA;
import DAO.ProfileDao;
import DAO.RolDao;
import DAO.UserDao;
import Models.Profile;
import Models.Rol;
import Models.Tweet;
import Models.User;
import Services.TweetService;
import io.sentry.Sentry;
import io.sentry.event.Event;
import io.sentry.event.EventBuilder;
import io.sentry.event.interfaces.ExceptionInterface;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
    
    @Inject @JPA
    private ProfileDao profileDao;
    
    @Inject @JPA
    private RolDao rolDao;
    
    public UserDaoJpa() {
        super(User.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public long findIdByName(String name)
    {
        long l = (long) em.createQuery("SELECT u.id FROM User u WHERE u.username = '" + name + "'").getSingleResult();
        String s = "Debug";
        return l; 
    }
    
    @Override
    public User getByName(String name) {
        if (name == null || name.isEmpty())
            return null;

        try {
            return (User) em.createQuery("SELECT u FROM User u WHERE u.username = '" + name + "'").getSingleResult();
            
        }
        catch (Exception x) {
            EventBuilder eventBuilder = new EventBuilder()
                               .withMessage("Exception caught")
                               .withLevel(Event.Level.ERROR)
                               .withLogger(UserDaoJpa.class.getName())
                               .withSentryInterface(new ExceptionInterface(x));

               // Note that the *unbuilt* EventBuilder instance is passed in so that
               // EventBuilderHelpers are run to add extra information to your event.
               Sentry.capture(eventBuilder);
            return null;
        }
    }
    
    @Override
    public void deleteTweet(long id, long tweetId) {
        findById(id).removeTweet(getTweets(id).stream().filter(k->k.getId() == tweetId).findAny().orElse(null));
        String s = "Debug";
    }
    
    @Override
    public void addFollower(long id, String idLeider) {
        if (id >= 0 && idLeider != "") {
            try {
                User volger = find(id);
                User leider = getByName(idLeider);
                leider.addFollower(volger);
                update(volger);
                update(leider);
            }
            catch (Exception x) {
                EventBuilder eventBuilder = new EventBuilder()
                               .withMessage("Exception caught")
                               .withLevel(Event.Level.ERROR)
                               .withLogger(UserDaoJpa.class.getName())
                               .withSentryInterface(new ExceptionInterface(x));

               // Note that the *unbuilt* EventBuilder instance is passed in so that
               // EventBuilderHelpers are run to add extra information to your event.
               Sentry.capture(eventBuilder);
            }
        }
    }
    
    @Override
    public User findByName(String name)
    {
        return (User) em.createQuery("SELECT u FROM User u WHERE u.username = '" + name + "'").getSingleResult();
    }
    
    @Override
    public void removeFollower(long id, String name) {
        if (id >= 0 && name != null) {
            try {
                User volger = find(id);
                User leider = findByName(name);
                leider.removeFollower(volger);
                update(volger);
                update(leider);
            }
            catch (Exception x) {
                EventBuilder eventBuilder = new EventBuilder()
                               .withMessage("Exception caught")
                               .withLevel(Event.Level.ERROR)
                               .withLogger(UserDaoJpa.class.getName())
                               .withSentryInterface(new ExceptionInterface(x));

               // Note that the *unbuilt* EventBuilder instance is passed in so that
               // EventBuilderHelpers are run to add extra information to your event.
               Sentry.capture(eventBuilder);
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
    public List<User> getUsers() {
         Query query = em.createQuery("SELECT u FROM User u");
         return  new ArrayList<>(query.getResultList());
    }

    @Override
    public User register(String userName, String password) {
        if(userName != null && !userName.isEmpty() && password != null && !password.isEmpty())
        {
            User user = new User();
            Profile p = new Profile("<vul in>", "<vul in>", "<vul in>", "<vul in>", "//placehold.it/100");
            Rol role = rolDao.findById(2l);
            user.setUsername(userName);
            user.setPassword(password);
            user.setEmail(userName + "@student.fontys.nl");
            user.setRol(role);
            profileDao.create(p);
            rolDao.create(role);
            user.setProfile(p);
            create(user);
            
            String s = "Debug";
            return user;
        }
        return null;
    }

    @Override
    public boolean login(String userName, String password) {
        if(userName != null && !userName.isEmpty() && password != null && !password.isEmpty())
            return false;

        try {
            User user = (User) em.createQuery("select u from User u where u.userName = '" + userName + "' and u.password = '" + password + "'").getSingleResult();
            if (user != null)
                return true;
            return false;
        }
        catch (Exception x) {
            return false;
        }
    }
}
