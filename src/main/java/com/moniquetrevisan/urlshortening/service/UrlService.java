package com.moniquetrevisan.urlshortening.service;

import java.net.URLEncoder;
import java.util.Random;

import com.moniquetrevisan.urlshortening.jsonobject.Stat;
import com.moniquetrevisan.urlshortening.persistence.UrlPersistence;

public class UrlService {

	private static String HOST = "http://localhost:8080/urlshortening/resources/";

	private UrlPersistence urlPersistence;

	public UrlService() {
		this.urlPersistence = new UrlPersistence();
	}

	public Stat getOriginalUrl(int urlId) {
		return urlPersistence.getOriginalUrl(urlId);
	}

	public boolean incrementHits(Stat stat) {
		return urlPersistence.incrementHits(stat);
	}

	public Stat createUrl(String userId, String url) {
		Stat stat = new Stat();
		stat.setHits(0);
		stat.setUrl(url);
		stat.setShortUrl(createShortUrl(url));

		int generatedKey = urlPersistence.createUrl(stat, userId);
		stat.setId(generatedKey);
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
	
	public boolean deleteUrl(int urlId) {
		if (urlPersistence.deleteUrl(urlId)) {
			return true;
		}
		return false;
	}

}