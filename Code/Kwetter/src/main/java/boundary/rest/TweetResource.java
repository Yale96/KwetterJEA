/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest;

import DTO.TweetDTO;
import Interceptors.JWTTokenNeeded;
import Interceptors.LoggingCheck;
import Models.HashTag;
import Models.Profile;
import Models.Tweet;
import Models.User;
import Services.ProfileService;
import Services.TweetService;
import Services.UserService;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Yannick van Leeuwen
 */
@LoggingCheck
@Path("tweets")
@Stateless
public class TweetResource {

    @Inject
    private TweetService tweetService;

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TweetDTO> getAll() {
        List<TweetDTO> returnList = new ArrayList<>();
        for(Tweet t: tweetService.getTweets())
        {
            returnList.add(new TweetDTO(t));
        }
        return returnList;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/gettweetss")
    @JWTTokenNeeded
    public List<TweetDTO> getAllToken() {
        List<TweetDTO> returnList = new ArrayList<>();
        for(Tweet t: tweetService.getTweets())
        {
            returnList.add(new TweetDTO(t));
        }
        return returnList;
    }

    @GET
    @Path("/bycontent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTweetsByContent(@QueryParam("content") String content, @Context HttpServletResponse response) {
        List<TweetDTO> returnList = new ArrayList<TweetDTO>();
        for(Tweet t: tweetService.getMatchesByContent(content))
        {
            returnList.add(new TweetDTO(t));
        }
        return Response.ok(returnList).build();
    }
    
    @GET
    @Path("/highest")
    @Produces(MediaType.APPLICATION_JSON)
    public TweetDTO getLatestTweet() {
        List<TweetDTO> tweetList = this.getAll();
        Collections.reverse(tweetList);
        TweetDTO t = tweetList.get(0);
        return t;
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
    @Path("/hashtagid")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTweetsByHashtagId(@QueryParam("id") long id, @Context HttpServletResponse response) {

        List<TweetDTO> returnList = new ArrayList<TweetDTO>();
        for(Tweet t: tweetService.getTweetByHashtagId(id))
        {
            returnList.add(new TweetDTO(t));
        }
        return Response.ok(returnList).build();
    }

    @GET
    @Path("/mentionname")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTweetsByMentionId(@QueryParam("name") String name, @Context HttpServletResponse response) {

        tweetService.getTweetsByMentionId(name);
        List<TweetDTO> returnList = new ArrayList<>();
        for(Tweet t: tweetService.getTweetsByMentionId(name))
        {
            returnList.add(new TweetDTO(t));
        }
        return Response.ok(returnList).build();
    }

    @GET
    @Path("/userid")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTweetsByUserId(@QueryParam("id") long id, @Context HttpServletResponse response) {
        List<TweetDTO> returnList = new ArrayList<TweetDTO>();
        for(Tweet t : tweetService.getTweetsByUserId(id))
        {
            returnList.add(new TweetDTO(t));
        }
        return Response.ok(returnList).build();
    }

    @GET
    @Path("/userid/recent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecentTweetsByUserId(@QueryParam("id") long id, @Context HttpServletResponse response) {
        List<TweetDTO> returnList = new ArrayList<TweetDTO>();
        for(Tweet t : tweetService.getRecentTweetsByUserId(id))
        {
            returnList.add(new TweetDTO(t));
        }
        
        return Response.ok(returnList.get(0)).build();
    }

    @GET
    @Path("/leadertweets/userid/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecentLeaderTweetsByUserId(@PathParam("id") long id, @Context HttpServletResponse response) {

        userService.getOwnAndOthersTweets(id);
        return Response.ok(userService.getOwnAndOthersTweets(id)).build();
    }

    @GET
    @Path("/hashtagcontent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrendingTopics(@QueryParam("content") String content, @Context HttpServletResponse response) {
        List<TweetDTO> returnList = new ArrayList<TweetDTO>();
        for(Tweet t : tweetService.getTrendingToppics(content))
        {
            returnList.add(new TweetDTO(t));
        }
        return Response.ok(returnList).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Tweet getTweet(@PathParam("id") Long id) {
        Tweet tweet = tweetService.getById(id);
        return tweet;
    }
    
    @GET
    @Path("/checkflag")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean checkFlag(@QueryParam("name") String name, @QueryParam("tweetId") long tweetId, @Context HttpServletResponse response) {
        User poster = userService.getByName(name);
        Tweet tweet = tweetService.getById(tweetId);
        for(User u: tweet.getLikes())
        {
            if(tweet.getFlags().contains(poster))
            {
                return false;
            }
        }
        return true;
    }
    
    @GET
    @Path("/checklike")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean checkLike(@QueryParam("name") String name, @QueryParam("tweetId") long tweetId, @Context HttpServletResponse response) {
        User poster = userService.getByName(name);
        Tweet tweet = tweetService.getById(tweetId);
        for(User u: tweet.getLikes())
        {
            if(tweet.getLikes().contains(poster))
            {
                return false;
            }
        }
        return true;
    }
    
    @POST
    @Path("/like")
    @Produces(MediaType.APPLICATION_JSON)
    public Response likeTweet(@QueryParam("name") String name, @QueryParam("tweetId") long tweetId, @Context HttpServletResponse response) {
        User poster = userService.getByName(name);
        Tweet tweet = tweetService.getById(tweetId);
        poster.addLike(tweet);
        userService.edit(poster);
        tweetService.editTweet(tweet);
        return Response.ok(getAll()).build();
    }

    @POST
    @Path("/dislike")
    @Produces(MediaType.APPLICATION_JSON)
    public void dislikeTweet(@FormParam("name") String name, @FormParam("tweetId") long tweetId, @Context HttpServletResponse response) {
        User poster = userService.getByName(name);
        Tweet tweet = tweetService.getById(tweetId);
        poster.removeLike(tweet);
        userService.edit(poster);
        tweetService.editTweet(tweet);
        //return Response.ok(tweetService.getTweets()).build();
    }
    
    @POST
    @Path("/flag")
    @Produces(MediaType.APPLICATION_JSON)
    public Response flagTweet(@QueryParam("name") String name, @QueryParam("tweetId") long tweetId, @Context HttpServletResponse response) {
        User poster = userService.getByName(name);
        Tweet tweet = tweetService.getById(tweetId);
        poster.addFlag(tweet);
        userService.edit(poster);
        tweetService.editTweet(tweet);
        return Response.ok(getAll()).build();
    }

    @POST
    @Path("/unflag")
    @Produces(MediaType.APPLICATION_JSON)
    public Response disflagTweet(@FormParam("name") String name, @FormParam("tweetId") long tweetId, @Context HttpServletResponse response) {
        User poster = userService.getByName(name);
        Tweet tweet = tweetService.getById(tweetId);
        poster.removeFlag(tweet);
        userService.edit(poster);
        tweetService.editTweet(tweet);
        return Response.ok().build();
    }

    @POST
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTweet(@QueryParam("name") String name, @QueryParam("content") String content) {
        User poster = userService.getByName(name);
        tweetService.sendNewTweet(poster.getId(), content);
        return Response.ok(getAll()).build();
    }

    @POST
    @Path("/remove")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTweet(@QueryParam("id") long id, @Context HttpServletResponse response) {
        tweetService.removeTweet(id);
        return Response.ok(getAll()).build();
    }

    @GET
    @Path("/exceptions")
    public void genereteExceptions() {
        tweetService.logException();
    }
}
