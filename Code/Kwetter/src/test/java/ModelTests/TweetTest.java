/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelTests;

import Models.HashTag;
import Models.Profile;
import Models.Rol;
import Models.Tweet;
import Models.User;
import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yanni
 */
public class TweetTest {

    public TweetTest() {
    }

    @Test
    public void testGettersAndSetters() {
        //Dummy tweeter as owner of the tweet.
        Rol r = new Rol("Test","test");
        User owner = new User();
        owner.setId(1);
        owner.setPassword("Yannick");
        owner.setEmail("Test");
        owner.setUsername("Yale96");
        owner.setRol(r);
        
        HashTag testHashTag = new HashTag();
        testHashTag.setId(1);
        testHashTag.setContent("#LMAO");
        
        long id = 1;
        String content = "This is a test tweet";
        Date testDate = new Date();
        
        ArrayList<Tweet> responses = new ArrayList<Tweet>();
        ArrayList<HashTag> hashtags = new ArrayList<HashTag>();
        ArrayList<User> mentionedUsers = new ArrayList<User>();
        ArrayList<User> likes = new ArrayList<User>(); 
        
        Tweet t = new Tweet();
        t.setId(id);
        t.setContent(content);
        t.setTimeStamp(testDate);
        t.setOwner(owner);
                
        responses.add(t);
        hashtags.add(testHashTag);
        mentionedUsers.add(owner);
        likes.add(owner);
        
        t.setResponses(responses);
        t.setHashtags(hashtags);
        t.setMentionedUsers(mentionedUsers);
        t.setLikes(likes);
        
        assertEquals(responses, t.getResponses());
        assertEquals(hashtags, t.getHashtags());
        assertEquals(mentionedUsers, t.getMentionedUsers());
        assertEquals(likes, t.getLikes());
        
        assertEquals(id, t.getId());
        assertEquals(content, t.getContent());
        assertEquals(testDate, t.getTimeStamp());
        assertEquals(owner, t.getOwner());
    }

    @Test
    public void testAddMethods() {
        User uOne = new User();
        Tweet tOne = new Tweet();
        HashTag hOne = new HashTag();
        
        uOne.addTweet(tOne);
        tOne.addLike(uOne);
        tOne.addMention(uOne);
        tOne.addHashTag(hOne);
        
        tOne.setOwner(uOne);
        
        assertEquals(uOne, tOne.getOwner());
        assertEquals(tOne, uOne.getTweets().get(0));
        assertEquals(uOne, tOne.getLikes().get(0));
        assertEquals(uOne, tOne.getMentionedUsers().get(0));
        assertEquals(hOne, tOne.getHashtags().get(0));
    }

    @Test
    public void testRemoveMethods() {

    }
}
