/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Profile;
import java.util.ArrayList;

/**
 *
 * @author Yannick van Leeuwen
 */
public interface ProfileDao {
    void create(Profile profile);
    
    void remove(Profile profile);
    
    void edit(Profile profile);

    Profile findById(Long id);

    ArrayList<Profile> getProfiles();
}
