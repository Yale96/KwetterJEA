/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author Yannick van Leeuwen
 */
public class UserDTO {
    private long id;
    
    private ProfileDTO profileDTO;
    
    private String email;
    
    private String username;
    
    private String password;
    
    private long supersCount;
    
    private long followersCount;
    
    private long likesCount;
    
    private long flagsCount;
    
    private long mentionsCount;
    
    private long tweetsCount;
    
    private RoleDTO roleDTO;
    
    public UserDTO()
    {
        
    }
    
    public UserDTO(long id, ProfileDTO profileDTO, String email, String username, String password, long supersCount, long followersCount, long likesCount, long flagsCount, long mentionsCount, long tweetsCount, RoleDTO roleDTO)
    {
        this.id = id;
        this.profileDTO = profileDTO;
        this.email = email;
        this.username = username;
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

    public ProfileDTO getProfileDTO() {
        return profileDTO;
    }

    public void setProfileDTO(ProfileDTO profileDTO) {
        this.profileDTO = profileDTO;
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

    public RoleDTO getRoleDTO() {
        return roleDTO;
    }

    public void setRoleDTO(RoleDTO roleDTO) {
        this.roleDTO = roleDTO;
    }
    
    
}