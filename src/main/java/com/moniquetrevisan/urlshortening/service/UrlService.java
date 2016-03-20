package com.moniquetrevisan.urlshortening.service;

import java.net.URLEncoder;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.moniquetrevisan.urlshortening.jsonobject.Stat;
import com.moniquetrevisan.urlshortening.persistence.UrlPersistence;

public class UrlService {

	private static Logger LOGGER = Logger.getLogger(UrlService.class.getName());

	private static String HOST = "http://localhost:8080/urlshortening/resources/";

	private UrlPersistence urlPersistence;

	public UrlService() throws Exception {
		this.urlPersistence = new UrlPersistence();
	}

	public Stat createUrl(String userId, String url) {
		Stat stat = new Stat();
		try {
			stat.setHits(0);
			stat.setUrl(url);
			stat.setShortUrl(createShortUrl(url));
			
			int generatedKey = urlPersistence.createUrl(stat, userId);
			stat.setId(generatedKey);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
		}
		return stat;
	}

	@SuppressWarnings("deprecation")
	private String createShortUrl(String url) {
		url = URLEncoder.encode(url);
		Random rng = new Random();
		char[] text = new char[10];
		for (int i = 0; i < 10; i++) {
			text[i] = url.charAt(rng.nextInt(url.length()));
		}
		String shortSufix = new String(text); 
				
		return HOST + shortSufix;
	}

}