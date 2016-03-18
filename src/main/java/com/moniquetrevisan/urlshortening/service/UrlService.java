package com.moniquetrevisan.urlshortening.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/urls")
public class UrlService {

    public UrlService() {
    	// do nothing...
    }

    @GET
	public String redirectUrl(String urlId) {
		// nothing yet
		return null;
	}

	public void deleteUrl(String urlId) {
		// nothing yet
	}

}
