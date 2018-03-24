/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import Services.*;
import javax.inject.Inject;

/**
 *
 * @author Yannick van Leeuwen
 */
public class DTOBase {
    @Inject
    private HashTagService hashTagService;
    
    @Inject
    private ProfileService profileService;
    
    @Inject 
    private RoleService roleService;
    
    @Inject 
    private TweetService tweetService;
    
    @Inject
    private UserService userService;
    
    public HashTagService getHashTagService()
    {
        return hashTagService;
    }
    
    public ProfileService getProfileTagService()
    {
        return profileService;
    }
    
    public RoleService getRoleService()
    {
        return roleService;
    }
    
    public TweetService getTweetService()
    {
        return tweetService;
    }
    
    public UserService getUserService()
    {
        return userService;
    }
}
