package com.moniquetrevisan.urlshortening.rest;

import javax.annotation.ManagedBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.moniquetrevisan.urlshortening.jsonobject.Stat;

@Path("/users")
@ManagedBean
public class UserRs {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{userid}/urls")
	public void saveUrl(@PathParam ("userid") String userid,
					 @Context HttpServletRequest request, 
					 @Context HttpServletResponse response) {
		String url = request.getParameter("url");
		System.out.println(url);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/itworks")
	public Stat justHi() {
		return new Stat(1, 0, "http://localhost:8080/urlshortening/longurl", "http://localhost:8080/urlshortening/itworks");
	}
}