/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.JPA;
import DAO.ProfileDao;
import DAO.UserDao;
import Models.Profile;
import Models.User;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Yannick van Leeuwen
 */
@Stateless
public class ProfileService {
    
    @Inject @JPA
    private ProfileDao profileDao;

    public void addProfile(Profile profile) {
        profileDao.create(profile);
    }

    public List<Profile> getProfiles() {
        return profileDao.getProfiles();
    }

    public Profile getById(Long id) {
        return profileDao.findById(id);
    }
    
    
    public void remove(long id)
    {
        Profile toRemove = profileDao.findById(id);
        profileDao.remove(toRemove);
    }
    
    public void editProfilePicture(long id, String picture) {
        Profile p = profileDao.findById(id);
        p.setPicture(picture);
        profileDao.edit(p);
    }

    public void editProfileName(long id, String profileName) {
        Profile p = profileDao.findById(id);
        p.setName(profileName);
        profileDao.edit(p);
    }

    
    public void editBio(long id, String bio) {
        Profile p = profileDao.findById(id);
        p.setBio(bio);
        profileDao.edit(p);
    }
    
    public void editWeb(long id, String web) {
        Profile p = profileDao.findById(id);
        p.setWeb(web);
        profileDao.edit(p);
    }

    public void editLocation(long id, String location) {
        Profile p = profileDao.findById(id);
        p.setLocation(location);
        profileDao.edit(p);
    }
    
    public ProfileService(){

    }
}
