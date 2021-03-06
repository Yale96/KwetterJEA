/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest;

import DTO.ProfileDTO;
import DTO.UserDTO;
import Models.Profile;
import Models.User;
import Services.UserService;
import Utils.KeyGenerator;
import Utils.PasswordUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Yannick van Leeuwen
 */
@Path("users")
@Stateless
public class UserResource {

    @Inject
    private UserService userService;
    
    @Context
    private UriInfo uriInfo;

    @Inject
    private Logger logger;

    @Inject
    private KeyGenerator keyGenerator;

    @PersistenceContext
    private EntityManager em;

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
        Link getFollowing = Link.fromUri("http://localhost:8080/Kwetter/resources/users/getfollowing?id=" + userService.getIdByTheName(name))
                .rel("get following")
                .type("GET text/json")
                .build("localhost", "8080");
        Link getOwnAndOthers = Link.fromUri("http://localhost:8080/Kwetter/resources/users/OwnAndOthers?id=" + userService.getIdByTheName(name))
                .rel("get own and others tweets")
                .type("GET text/json")
                .build("localhost", "8080");
        Link getProfileByName = Link.fromUri("http://localhost:8080/Kwetter/resources/users/getProfileByName?name=" + name)
                .rel("get profile")
                .type("GET text/json")
                .build("localhost", "8080");
        return Response.ok(new UserDTO(user)).links(getFollowing, getOwnAndOthers, getProfileByName).build();
    }
    
    @GET
    @Path("/getProfileByName")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfileByName(@QueryParam("name") String name) {
        Profile p = userService.getProfileyName(name);
        return Response.ok(new ProfileDTO(p)).build();
    }
    
    @GET
    @Path("/getfollowing")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFollowing(@QueryParam("id") Long id) {
        List<String> followings = new ArrayList<>();
        for(User u: userService.getLeaders(id))
        {
            followings.add(u.getUsername());
        }
        return Response.ok(followings).build();
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
   @Path("/OwnAndOthers")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response getOwnAndOthers(@QueryParam("id") Long id)
   {
       userService.getOwnAndOthersTweets(id);
       return Response.ok(userService.getOwnAndOthersTweets(id)).build();
   }
   
   @GET
   @Path("/getByName")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response getByName(@QueryParam("name") String name)
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
   public Response addFollower(@QueryParam("id") long id, @QueryParam("superName") String superName, @Context HttpServletResponse response)
   {
        User poster = userService.getById(id);
        User supert = userService.getByName(superName);
        userService.addFollower(poster.getId(), supert.getUsername());
        return Response.ok(new UserDTO(userService.getById(id))).build();
   }
   
   @POST
   @Path("/removeFollower")
   @Produces(MediaType.APPLICATION_JSON)
   public Response removeFollower(@QueryParam("id") long id, @QueryParam("superName") String superName, @Context HttpServletResponse response)
   {
        User poster = userService.getById(id);
        User supert = userService.getByName(superName);
        userService.removeFollower(poster.getId(), supert.getUsername());
        return Response.ok(new UserDTO(userService.getById(id))).build();
   }
   
   @GET
   @Path("/byname")
   @Produces(MediaType.APPLICATION_JSON)
   public Response removeFollower(@QueryParam("name") String name, @Context HttpServletResponse response)
   {
       long l = userService.getIdByTheName(name);
       return Response.ok(l).build();
   }
   
   @DELETE
   @Path("/removeTweet")
   @Produces(MediaType.APPLICATION_JSON)
   public Response removeTweet(@QueryParam("id") long id, @QueryParam("tweetid") long tweetId, @Context HttpServletResponse response)
   {
       userService.deleteTweet(id, tweetId);
       return Response.ok(getAll()).build();
   }
   
   @POST
   @Path("/register")
   @Produces(MediaType.APPLICATION_JSON)
   public Response registerUser(@QueryParam("username") String username, @QueryParam("password") String password, @Context HttpServletResponse response)
   {
       User u = userService.registerUser(username, password);
       return Response.ok(new UserDTO(u)).build();
   }
   
   @POST
   @Path("/logins")
   @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUsers(@QueryParam("login") String login, @QueryParam("password") String password, @Context HttpServletResponse response) {
        try {
 
            // Authenticate the user using the credentials provided
            User u = authenticate(login, password);
 
            // Issue a token for the user
            String token = issueToken(login);
 
            // Return the token on the response
            String returnString = token;
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
 
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }
   
   @POST
   @Path("/login")
   @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(@QueryParam("login") String login, @QueryParam("password") String password, @Context HttpServletResponse response) {
        try {
 
            // Authenticate the user using the credentials provided
            User u = authenticate(login, password);
 
            // Issue a token for the user
            String token = issueToken(login);
 
            // Return the token on the response
            String returnString = token;
            return Response.ok(returnString).build();
 
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }
 
    private User authenticate(String login, String password) throws Exception {
        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_LOGIN_PASSWORD, User.class);
        query.setParameter("username", login);
        query.setParameter("password", PasswordUtils.digestPassword(password));
        User user = query.getSingleResult();

        if (user == null)
            throw new SecurityException("Invalid user/password");
        
        return user;
    }

    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        logger.info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;
    }
    
    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    @GET
    @Path("/checkfollow")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean checkFlag(@QueryParam("id") long id, @QueryParam("superName") String superName, @Context HttpServletResponse response) {
        User poster = userService.getById(id);
        User supert = userService.getByName(superName);
        for(User u: poster.getSupers())
        {
            if(poster.getSupers().contains(supert) || poster.getId() == supert.getId())
            {
                return false;
            }
        }
        return true;
    }
}
