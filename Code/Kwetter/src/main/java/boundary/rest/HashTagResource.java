/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest;

import DTO.DTOBase;
import Models.HashTag;
import Services.HashTagService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.PUT;
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
@Path("hashtags")
@Stateless
public class HashTagResource {
    @Inject
    private HashTagService hashTagService;
    
    @Inject
    private DTOBase dtoBase;
    
    @Context
    private ServletContext servletContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HashTag> getAll() {
        return hashTagService.getHashTags();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HashTag getHashTag(@PathParam("id") Long id) {
        HashTag hashTag = hashTagService.getById(id);
        return hashTag;
    }
    
    @GET
    @Path("/trends")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrends() 
    {
        LinkedHashMap<String, Long> toStringify = (LinkedHashMap<String, Long>) hashTagService.getTrendss(getAll());
        List<Entry> entryList = new ArrayList<Entry>(toStringify.entrySet());
		System.out.println("\n==> Size of Entry list: " + entryList.size());
		for (Entry temp : entryList) {
			System.out.println(temp);
		}
        List<String> returnList = new ArrayList<>();
        for(Entry e: entryList)
        {
            returnList.add(e.toString());
        }
        
        return Response.ok(returnList).build();
    }
    
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addHashTag(@Context HttpHeaders httPheaders, HashTag tag)
    {
        hashTagService.addHashTag(tag);
        return Response.ok(hashTagService.getHashTags()).build();
    }
    
   @PUT
   @Path("/edit")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response updateHashTag(@Context HttpHeaders httPheaders, HashTag tag)
   {
      hashTagService.edit(tag.getId(), tag.getContent());
      return Response.ok(hashTagService.getHashTags()).build();
   }
   
   @DELETE
   @Path("/delete/{id}")
   public Response deleteHashTag(@PathParam("id") long id)
   {
       //HashTag h = hashTagService.getById(hashTag.getId());
       //Long test = new Long(id);
       hashTagService.remove(id);
       return Response.ok(hashTagService.getHashTags()).build();
   }
}
