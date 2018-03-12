/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest;

import Models.Profile;
import Models.Tweet;
import Models.User;
import Services.ProfileService;
import Services.TweetService;
import Services.UserService;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@Path("tweets")
@Stateless
public class TweetResource {
    @Inject
    private TweetService tweetService;
    
    @Inject
    private UserService userService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tweet> getAll() {
        return tweetService.getTweets();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tweet getTweet(@PathParam("id") Long id) {
        Tweet tweet = tweetService.getById(id);
        return tweet;
    }
    
    @POST
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTweet(@FormParam("name") String name, @FormParam("content") String content, @Context HttpServletResponse response)
    {
        User poster = userService.getByName(name);
        tweetService.sendNewTweet(poster.getId(), content);
        return Response.ok(tweetService.getTweets()).build();
    }
}
