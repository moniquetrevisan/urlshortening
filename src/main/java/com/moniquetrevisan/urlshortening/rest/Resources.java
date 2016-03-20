package com.moniquetrevisan.urlshortening.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.ManagedBean;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.moniquetrevisan.urlshortening.jsonobject.Stat;
import com.moniquetrevisan.urlshortening.jsonobject.User;
import com.moniquetrevisan.urlshortening.service.StatisticsService;
import com.moniquetrevisan.urlshortening.service.UrlService;
import com.moniquetrevisan.urlshortening.service.UserService;

@Path("/resources")
@ManagedBean
public class Resources {
	
	private static Logger LOGGER = Logger.getLogger(Resources.class.getName());
	
	private UserService userService;
	
	private UrlService urlService;
	
	private StatisticsService statisticsService;
	
	public Resources() throws Exception {
		this.userService = new UserService();
		this.urlService = new UrlService(); 
		this.statisticsService = new StatisticsService();
	}
	
	@GET
	@Path("/urls/{id}")
	public Response redirectUrl(@PathParam("id") String urlId) {
		//TODO 
		// deve ir ao banco pegar a url
		// atualiza hits
		// faz o redirect
		return Response.ok().build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/users/{userid}/urls")
	public Response createShortUrl(String json, @PathParam("userid") String userId) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			String url = jsonObject.getString("url");
			Stat stat = urlService.createUrl(userId, url);
			if (stat.getId() != -1) {
				String jsonResponse = new Gson().toJson(stat);
				return Response.status(HttpServletResponse.SC_CREATED).entity(jsonResponse).type(MediaType.APPLICATION_JSON).build();
			} else {
				return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).build();
			}
		} catch (JSONException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return null; 
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/stats")
	public Response getGlobalStatistics() {
		Stat stat = new Stat();
		stat.setId(1);
		stat.setHits(0);
		stat.setUrl("longurl");
		stat.setShortUrl("shorturl");
		
		String jsonResponse = new Gson().toJson(stat);
		return Response.ok(jsonResponse).type(MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{userId}/stats")
	public Response getUserStatistics(@PathParam("userId") String userid) {
		// TODO getUserStatistics
		return Response.ok().build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/stats/{id}")
	public Response getUrlStatistics(@PathParam("id") String urlId) {
		// TODO getUrlStatistics
		return Response.ok().build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/urls/{id}")
	public void deleteUrl(@PathParam("id") String urlId) {
		// TODO delete url
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/users")
	public Response createUser(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			String userId = jsonObject.getString("id");
			User user = userService.createUser(userId);
			if (!Strings.isNullOrEmpty(user.getId())) {
				String jsonResponse = new Gson().toJson(user);
				return Response.status(HttpServletResponse.SC_CREATED).entity(jsonResponse).type(MediaType.APPLICATION_JSON).build();
			} else {
				return Response.status(HttpServletResponse.SC_CONFLICT).type(MediaType.APPLICATION_JSON).build();
			}
		} catch (JSONException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return null; 
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/{userId}")
	public void deleteUser(@PathParam("userId") String userId) {
		// TODO delete user
	}

}