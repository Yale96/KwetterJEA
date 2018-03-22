/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Rol;
import java.util.ArrayList;

/**
 *
 * @author yanni
 */
public interface RolDao {
    void create(Rol role);
    
    void remove(Rol role);
    
    void edit(Rol role);

    Rol findById(Long id);

    ArrayList<Rol> getRoles();
}
