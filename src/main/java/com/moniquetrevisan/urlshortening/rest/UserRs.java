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
	@Path("/itworks")
	public String justHi() {
		return "it works!";
	}
}