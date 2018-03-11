/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest;

import Models.Profile;
import Models.Tweet;
import Services.ProfileService;
import Services.TweetService;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Yannick van Leeuwen
 */
@Path("tweets")
@Stateless
public class TweetResource {
    @Inject
    private TweetService tweetService;

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
}
