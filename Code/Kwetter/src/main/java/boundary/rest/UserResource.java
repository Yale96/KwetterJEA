/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest;

import DTO.UserDTO;
import Models.User;
import Services.UserService;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
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
    public List<UserDTO> getAll() {
        List<UserDTO> returnList = new ArrayList<>();
        for(User u: userService.getUsers())
        {
            returnList.add(new UserDTO(u));
        }
        return returnList;
    }

    @GET
    @Path("/single")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@QueryParam("name") String name) {
        User user = userService.getByName(name);
        return Response.ok(new UserDTO(user)).build();
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
   
   @PUT
   @Path("/edit/mail")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response updateMail(@Context HttpHeaders httPheaders, User u)
   {
      userService.editMail(u.getId(), u.getEmail());
      return Response.ok(userService.getUsers()).build();
   }
   
   @PUT
   @Path("/edit/username")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response updateUserName(@Context HttpHeaders httPheaders, User u)
   {
      userService.editName(u.getId(), u.getUsername());
      return Response.ok(userService.getUsers()).build();
   }
   
   @GET
   @Path("/OwnAndOthers/{id}")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response getOwnAndOthers(@PathParam("id") Long id)
   {
       userService.getOwnAndOthersTweets(id);
       return Response.ok(userService.getOwnAndOthersTweets(id)).build();
   }
   
   @GET
   @Path("/getByName/{name}")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response getByName(@PathParam("name") String name)
   {
       userService.getByName(name);
       return Response.ok(userService.getByName(name)).build();
   }
   
   @POST
   @Path("/addProfile")
   @Produces(MediaType.APPLICATION_JSON)
   public Response addProfile(@FormParam("id") long id, @FormParam("profileid") long profileId, @Context HttpServletResponse response)
   {
       userService.addProfile(id, profileId);
       return Response.ok(userService.getUsers()).build();
   }
   
   @POST
   @Path("/addLike")
   @Produces(MediaType.APPLICATION_JSON)
   public Response addLike(@FormParam("id") long id, @FormParam("tweetid") long tweetId, @Context HttpServletResponse response)
   {
       userService.addLike(id, tweetId);
       return Response.ok(userService.getTweets(id)).build();
   }
   
   @POST
   @Path("/removeLike")
   @Produces(MediaType.APPLICATION_JSON)
   public Response removeLike(@FormParam("id") long id, @FormParam("tweetid") long tweetId, @Context HttpServletResponse response)
   {
       userService.removeLike(id, tweetId);
       return Response.ok(userService.getTweets(id)).build();
   }
   
   @POST
   @Path("/addFollower")
   @Produces(MediaType.APPLICATION_JSON)
   public void addFollower(@FormParam("id") long id, @FormParam("superid") long superId, @Context HttpServletResponse response)
   {
       userService.addFollower(id, superId);
       //return Response.ok(userService.getFollowers(id)).build();
   }
   
   @POST
   @Path("/removeFollower")
   @Produces(MediaType.APPLICATION_JSON)
   public Response removeFollower(@FormParam("id") long id, @FormParam("superid") long superId, @Context HttpServletResponse response)
   {
       userService.removeFollower(id, superId);
       return Response.ok(userService.getFollowers(id)).build();
   }
   
   @DELETE
   @Path("/removeTweet")
   @Produces(MediaType.APPLICATION_JSON)
   public Response removeTweet(@FormParam("id") long id, @FormParam("tweetid") long tweetId, @Context HttpServletResponse response)
   {
       userService.deleteTweet(id, tweetId);
       return Response.ok(userService.getTweets(id)).build();
   }
}
