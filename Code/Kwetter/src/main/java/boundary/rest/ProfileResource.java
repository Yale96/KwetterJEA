/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest;

import Models.Profile;
import Services.ProfileService;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yannick van Leeuwen
 */
@Path("profiles")
@Stateless
public class ProfileResource {
    @Inject
    private ProfileService profileService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Profile> getAll() {
        return profileService.getProfiles();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Profile getProfile(@PathParam("id") Long id) {
        Profile profile = profileService.getById(id);
        return profile;
    }
    
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProfile(@Context HttpHeaders httPheaders, Profile p)
    {
        profileService.addProfile(p);
        return Response.ok(profileService.getProfiles()).build();
    }
    
   @PUT
   @Path("/edit/picture")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response updatePicture(@Context HttpHeaders httPheaders, Profile p)
   {
      profileService.editProfilePicture(p.getId(), p.getPicture());
      return Response.ok(profileService.getProfiles()).build();
   }
   
   @PUT
   @Path("/edit/name")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response updateName(@Context HttpHeaders httPheaders, Profile p)
   {
      profileService.editProfileName(p.getId(), p.getName());
      return Response.ok(profileService.getProfiles()).build();
   }
   
   @PUT
   @Path("/edit/web")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response updateWeb(@Context HttpHeaders httPheaders, Profile p)
   {
      profileService.editWeb(p.getId(), p.getWeb());
      return Response.ok(profileService.getProfiles()).build();
   }
   
   @PUT
   @Path("/edit/bio")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response updateBio(@Context HttpHeaders httPheaders, Profile p)
   {
      profileService.editBio(p.getId(), p.getBio());
      return Response.ok(profileService.getProfiles()).build();
   }
   
   @PUT
   @Path("/edit/location")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response updateLocation(@Context HttpHeaders httPheaders, Profile p)
   {
      profileService.editLocation(p.getId(), p.getLocation());
      return Response.ok(profileService.getProfiles()).build();
   }
}
