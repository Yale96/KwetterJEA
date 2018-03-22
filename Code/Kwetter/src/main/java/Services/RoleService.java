/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.JPA;
import DAO.RolDao;
import Models.Rol;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author yanni
 */
@Stateless
public class RoleService {
    @Inject @JPA
    private RolDao roleDao;

    public void addRole(Rol role) {
        roleDao.create(role);
    }
    
    public List<Rol> getRoles() {
        return roleDao.getRoles();
    }
}
