/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest;

import Models.User;
import Services.UserService;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yannick van Leeuwen
 */
@Path("users")
@Stateless
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAll() {
        return userService.getUsers();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") Long id) {
        User user = userService.getById(id);
        return user;
    }
    
   @PUT
   @Path("/edit/role")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response updateRole(@Context HttpHeaders httPheaders, User u)
   {
      userService.editMail(u.getId(), u.getEmail());
      return Response.ok(userService.getUsers()).build();
   }
   
//   @PUT
//   @Path("/edit/mail")
//   @Produces(MediaType.APPLICATION_JSON)
//   @Consumes(MediaType.APPLICATION_JSON)
//   public Response updateMail(@Context HttpHeaders httPheaders, User u)
//   {
//      profileService.editProfilePicture(p.getId(), p.getPicture());
//      return Response.ok(profileService.getProfiles()).build();
//   }
//   
//   @PUT
//   @Path("/edit/username")
//   @Produces(MediaType.APPLICATION_JSON)
//   @Consumes(MediaType.APPLICATION_JSON)
//   public Response updateUserName(@Context HttpHeaders httPheaders, User u)
//   {
//      profileService.editProfilePicture(p.getId(), p.getPicture());
//      return Response.ok(profileService.getProfiles()).build();
//   }
}
