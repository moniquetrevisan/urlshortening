package com.moniquetrevisan.urlshortening.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.ManagedBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

	/**
	 * Redirect URL
	 * EndPoint [/urls/{id}]
	 * @param urlId
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/urls/{id}")
	public Response redirectUrl(@PathParam("id") int urlId,  @Context HttpServletRequest request, @Context HttpServletResponse response) throws ServletException, URISyntaxException {
		// recupera a url original para fazer o redirect
		Stat stat = urlService.getOriginalUrl(urlId);

		// se nao encontrou a url retornamos NotFound 404
		if (null == stat) {
			return Response.status(HttpServletResponse.SC_NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
		}

		// incrementa o contador de audiencia
		boolean isIncrementSuccessful = urlService.incrementHits(stat);
		if (isIncrementSuccessful) {
			// faz o redirect
			URI uri = new URI(stat.getUrl());
			return Response.status(HttpServletResponse.SC_MOVED_PERMANENTLY).location(uri).type(MediaType.APPLICATION_JSON).build();
		}

		return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Cadastra uma nova url no sistema
	 * EndPoint [/users/{userid}/urls]
	 * @param json
	 * @param userId
	 * @return
	 */
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
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String jsonResponse = gson.toJson(stat);
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
		JSONObject json = statisticsService.getGlobalStatistics();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonResponse = gson.toJson(json);
		
		return Response.status(HttpServletResponse.SC_OK).entity(jsonResponse).type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Retorna estatisticas das urls de um usuario
	 * EndPoint [/users/{userId}/stats]
	 * @param userid
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{userId}/stats")
	public Response getUserStatistics(@PathParam("userId") String userId) {
		JSONObject json = statisticsService.getUserStatistics(userId);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonResponse = gson.toJson(json);
		
		return Response.status(HttpServletResponse.SC_OK).entity(jsonResponse).type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Retorna estatisticas de uma URL especifica
	 * EndPoint [/stats/{id}]
	 * @param urlId
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/stats/{id}")
	public Response getUrlStatistics(@PathParam("id") int urlId) {
		Stat stat = urlService.getOriginalUrl(urlId);
		if(null != stat){
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String jsonResponse = gson.toJson(stat);
			return Response.ok(jsonResponse).type(MediaType.APPLICATION_JSON).build();
		}
		return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Apaga uma URL do sistema (duh! =P )
	 * EndPoint [/urls/{id}]
	 * @param urlId
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/urls/{id}")
	public void deleteUrl(@PathParam("id") int urlId) {
		urlService.deleteUrl(urlId);
	}

	/**
	 * Cria um usuario
	 * EndPoint [/users]
	 * @param json
	 * @return
	 */
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
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String jsonResponse = gson.toJson(user);
				return Response.status(HttpServletResponse.SC_CREATED).entity(jsonResponse).type(MediaType.APPLICATION_JSON).build();
			} else {
				return Response.status(HttpServletResponse.SC_CONFLICT).type(MediaType.APPLICATION_JSON).build();
			}
		} catch (JSONException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Apaga um usuario
	 * EndPoint [/user/{userId}]
	 * @param userId
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/{userId}")
	public void deleteUser(@PathParam("userId") String userId) {
		userService.deleteUser(userId);
	}

}