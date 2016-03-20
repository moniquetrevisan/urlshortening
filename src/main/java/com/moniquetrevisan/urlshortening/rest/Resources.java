package com.moniquetrevisan.urlshortening.rest;

import java.io.IOException;

import javax.annotation.ManagedBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.moniquetrevisan.urlshortening.jsonobject.Stat;
import com.moniquetrevisan.urlshortening.jsonobject.User;

@Path("/resources")
@ManagedBean
public class Resources {
	
	@GET
	@Path("/urls/{id}")
	public HttpServletResponse redirectUrl(@PathParam("id") String urlId, @Context HttpServletResponse response) {
		return response;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{userid}/urls")
	public void createShortUrl(@PathParam("userid") String userid, @Context HttpServletRequest request, @Context HttpServletResponse response) {
		String url = request.getParameter("url");
		//TODO createShortUrl
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/stats")
	public HttpServletResponse getGlobalStatistics(@Context HttpServletResponse response) {
		Stat stat = new Stat();
		stat.setId(1);
		stat.setHits(0);
		stat.setUrl("longurl");
		stat.setShortUrl("shorturl");
		
		String jsonResponse = new Gson().toJson(stat);
		try {
			response.getWriter().write(jsonResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setStatus(HttpServletResponse.SC_CREATED);
		return response;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{userId}/stats")
	public HttpServletResponse getUserStatistics(@PathParam("userId") String userid, @Context HttpServletResponse response) {
		// TODO getUserStatistics
		return response;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/stats/{id}")
	public HttpServletResponse getUrlStatistics(@PathParam("id") String urlId, @Context HttpServletResponse response) {
		// TODO getUrlStatistics
		return response;
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/urls/{id}")
	public void deleteUrl(@PathParam("id") String urlId) {
		// TODO delete url
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users")
	public HttpServletResponse createUser(@Context HttpServletRequest request, @Context HttpServletResponse response) {

		try {
			String userId = request.getParameter("id");
			// TODO chamar o servico que cria o usuário
			// TODO ele vai retornar um usuário
			User user = new User(userId);

			if (!Strings.isNullOrEmpty(user.getId())) {
				String jsonResponse = new Gson().toJson(user);
				response.getWriter().write(jsonResponse);
				response.setStatus(HttpServletResponse.SC_CREATED);
			} else {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
			}
		} catch (IOException e) {
			// TODO colocar um log bonitinho
			e.printStackTrace();
		}
		return response;
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/{userId}")
	public void deleteUser(@PathParam("userId") String userId) {
		// TODO delete user
	}

}