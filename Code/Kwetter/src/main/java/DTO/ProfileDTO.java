/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import Models.Profile;

/**
 *
 * @author Yannick van Leeuwen
 */
public class ProfileDTO {
    private long id;
    
    private String name;
    
    private String location;
    
    private String web;
    
    private String bio;
    
    private String picture;
    
    public ProfileDTO() {
        
    }

    public ProfileDTO(long id, String name, String location, String web, String bio, String picture){
        this.id = id;
        this.name = name;
        this.location = location;
        this.web = web;
        this.bio = bio;
        this.picture = picture;
    }  

    public ProfileDTO(Profile p) {
        this.id = p.getId();
        this.picture = p.getPicture();
        this.name = p.getName();
        this.web = p.getWeb();
        this.location = p.getLocation();
        this.bio = p.getBio();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    
}
