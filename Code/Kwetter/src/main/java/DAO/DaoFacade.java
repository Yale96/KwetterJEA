/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import javax.persistence.EntityManager;

/**
 *
 * @author Yannick van Leeuwen
 */
public abstract class DaoFacade<T> {
    private final Class<T> entityClass;

    public DaoFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
    public T update(final T t) {
        try {
            return this.getEntityManager().merge(t);
        } catch (Exception x){
            return t;
        }
    }
}
