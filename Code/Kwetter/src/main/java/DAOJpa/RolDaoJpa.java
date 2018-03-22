/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOJpa;

import DAO.DaoFacade;
import DAO.JPA;
import Models.Profile;
import Models.Rol;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import DAO.RolDao;
import Interceptors.LoggingCheck;
import javax.ejb.Stateless;

/**
 *
 * @author yanni
 */
@Stateless @JPA @LoggingCheck
public class RolDaoJpa extends DaoFacade<Rol> implements RolDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public RolDaoJpa() {
        super(Rol.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public Rol findById(Long id) {
        TypedQuery<Rol> query = em.createNamedQuery("Role.findById", Rol.class);
        query.setParameter("id", id);
        List<Rol> result = query.getResultList();
        System.out.println("count: " + result.size());
        return result.get(0);
    }
    
    @Override
    public ArrayList<Rol> getRoles() {
         Query query = em.createQuery("SELECT r FROM Role r");
         return  new ArrayList<>(query.getResultList());
    }
}
