/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import javax.inject.Inject;

/**
 *
 * @author Yannick van Leeuwen
 */
public class ManageService {
    
    private HashTagService hashTagService;
    private ProfileService profileService;
    private TweetService tweetService;
    private UserService userService;
    
    @Inject
    public ManageService(HashTagService hs,  TweetService ts, UserService us, ProfileService ps) {
        hashTagService = hs;
        profileService = ps;
        tweetService = ts;
        userService = us;
    }
    
    public HashTagService getHashTagService(){
        return hashTagService;
    }
}
