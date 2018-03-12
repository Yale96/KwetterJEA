/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOJpa;

import DAO.DaoFacade;
import DAO.JPA;
import DAO.ProfileDao;
import DAO.UserDao;
import Models.Profile;
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
public class ProfileDaoJpa extends DaoFacade<Profile> implements ProfileDao {
    @PersistenceContext
    private EntityManager em;
    
    public ProfileDaoJpa() {
        super(Profile.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public Profile findById(Long id) {
        TypedQuery<Profile> query = em.createNamedQuery("Profile.findById", Profile.class);
        query.setParameter("id", id);
        List<Profile> result = query.getResultList();
        System.out.println("count: " + result.size());
        return result.get(0);
    }

    @Override
    public ArrayList<Profile> getProfiles() {
         Query query = em.createQuery("SELECT p FROM Profile p");
         return  new ArrayList<>(query.getResultList());
    }
}
