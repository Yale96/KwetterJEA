/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;
import Models.User;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Yannick van Leeuwen
 */
public class UserDTO {
    private long id;
    
    private long profileId;
    
    private String email;
    
    private String username;
    
    private String password;
    
    private long supersCount;
    
    private long followersCount;
    
    private long likesCount;
    
    private long flagsCount;
    
    private List<String> followers;
    
    private long mentionsCount;
    
    private long tweetsCount;
    
    private String roleDTO;
    
    private String uri;
    
    public UserDTO()
    {
        
    }
    
    public UserDTO(User user)
    {
        followers = new ArrayList<String>();
        this.id = user.getId();
        this.uri = user.getUri();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.profileId = user.getProfile().getId();
        this.supersCount = user.getSupers().size();
        this.followersCount = user.getFollowers().size();
        this.likesCount = user.getLikes().size();
        this.flagsCount = user.getFlags().size();
        this.mentionsCount = user.getMentions().size();
        this.tweetsCount = user.getTweets().size();
        this.roleDTO = user.getRol().getType();
        for(User u: user.getFollowers())
        {
            followers.add(u.getUsername());
        }
    }
    
    public UserDTO(long id, int profileDTO, String email, String username, String password, long supersCount, long followersCount, String uri, long likesCount, long flagsCount, long mentionsCount, long tweetsCount, String roleDTO)
    {
        this.id = id;
        this.profileId = profileDTO;
        this.email = email;
        this.username = username;
        this.uri = uri;
        this.password = password;
        this.supersCount = supersCount;
        this.followersCount = followersCount;
        this.likesCount = likesCount;
        this.flagsCount = flagsCount;
        this.mentionsCount = mentionsCount;
        this.tweetsCount = tweetsCount;
        this.roleDTO = roleDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProfileDTO() {
        return profileId;
    }

    public void setProfileDTO(long profileDTO) {
        this.profileId = profileDTO;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getSupersCount() {
        return supersCount;
    }

    public void setSupersCount(long supersCount) {
        this.supersCount = supersCount;
    }

    public long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(long followersCount) {
        this.followersCount = followersCount;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public long getFlagsCount() {
        return flagsCount;
    }

    public void setFlagsCount(long flagsCount) {
        this.flagsCount = flagsCount;
    }

    public long getMentionsCount() {
        return mentionsCount;
    }

    public void setMentionsCount(long mentionsCount) {
        this.mentionsCount = mentionsCount;
    }

    public long getTweetsCount() {
        return tweetsCount;
    }

    public void setTweetsCount(long tweetsCount) {
        this.tweetsCount = tweetsCount;
    }

    public String getRoleDTO() {
        return roleDTO;
    }

    public void setRoleDTO(String roleDTO) {
        this.roleDTO = roleDTO;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
