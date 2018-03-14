/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest;

import Models.HashTag;
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
import javax.ws.rs.DELETE;
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
    @Path("/bycontent/{content}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTweetsByContent(@PathParam("content") String content, @Context HttpServletResponse response) {

        tweetService.getMatchesByContent(content);
        return Response.ok(tweetService.getMatchesByContent(content)).build();
    }
    
    @POST
    @Path("/getMentionedUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMentionedUsers(@FormParam("content") String content, @Context HttpServletResponse response) {

        tweetService.findHashtagsByPureContent(content);
        return Response.ok(tweetService.findMentions(content)).build();
    }
    
    @POST
    @Path("/getPureContent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPureContent(@FormParam("content") String content, @Context HttpServletResponse response) {

        tweetService.findHashtagsByPureContent(content);
        return Response.ok(tweetService.findHashtagsByPureContent(content)).build();
    }

    @GET
    @Path("/hashtagid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTweetsByHashtagId(@PathParam("id") long id, @Context HttpServletResponse response) {

        tweetService.getTweetByHashtagId(id);
        return Response.ok(tweetService.getTweetByHashtagId(id)).build();
    }

    @GET
    @Path("/mentionid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTweetsByMentionId(@PathParam("id") long id, @Context HttpServletResponse response) {

        tweetService.getTweetsByMentionId(id);
        return Response.ok(tweetService.getTweetsByMentionId(id)).build();
    }

    @GET
    @Path("/userid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTweetsByUserId(@PathParam("id") long id, @Context HttpServletResponse response) {
        tweetService.getTweetsByUserId(id);
        return Response.ok(tweetService.getTweetsByUserId(id)).build();
    }

    @GET
    @Path("/userid/recent/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecentTweetsByUserId(@PathParam("id") long id, @Context HttpServletResponse response) {
        tweetService.getRecentTweetsByUserId(id);
        return Response.ok(tweetService.getRecentTweetsByUserId(id)).build();
    }

    @GET
    @Path("/leadertweets/userid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecentLeaderTweetsByUserId(@PathParam("id") long id, @Context HttpServletResponse response) {

        userService.getOwnAndOthersTweets(id);
        return Response.ok(userService.getOwnAndOthersTweets(id)).build();
    }

    @GET
    @Path("/hashtagcontent/{content}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrendingTopics(@PathParam("content") String content, @Context HttpServletResponse response) {
        tweetService.getTrendingToppics(content);
        return Response.ok(tweetService.getTweets()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tweet getTweet(@PathParam("id") Long id) {
        Tweet tweet = tweetService.getById(id);
        return tweet;
    }

    @POST
    @Path("/like")
    @Produces(MediaType.APPLICATION_JSON)
    public Response likeTweet(@FormParam("name") String name, @FormParam("tweetId") long tweetId, @Context HttpServletResponse response) {
        User poster = userService.getByName(name);
        Tweet tweet = tweetService.getById(tweetId);
        poster.addLike(tweet);
        userService.edit(poster);
        tweetService.editTweet(tweet);
        return Response.ok(tweetService.getTweets()).build();
    }

    @POST
    @Path("/dislike")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dislikeTweet(@FormParam("name") String name, @FormParam("tweetId") long tweetId, @Context HttpServletResponse response) {
        User poster = userService.getByName(name);
        Tweet tweet = tweetService.getById(tweetId);
        poster.removeLike(tweet);
        userService.edit(poster);
        tweetService.editTweet(tweet);
        return Response.ok(tweetService.getTweets()).build();
    }

    @POST
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    public void addTweet(@FormParam("name") String name, @FormParam("content") String content, @Context HttpServletResponse response) {
        User poster = userService.getByName(name);
        tweetService.sendNewTweet(poster.getId(), content);
        //return Response.ok(tweetService.getTweets()).build();
    }

    @DELETE
    @Path("/remove")
    @Produces(MediaType.APPLICATION_JSON)
    public void removeTweet(@FormParam("id") long id, @Context HttpServletResponse response) {
        User poster = tweetService.getById(id).getOwner();
        Tweet toRemove = tweetService.getById(id);
        //poster.removeTweet(toRemove);
        tweetService.removeTweet(toRemove.getId());
        userService.edit(poster);
        tweetService.editTweet(toRemove);
        //return Response.ok(tweetService.getTweets()).build();
    }
}
