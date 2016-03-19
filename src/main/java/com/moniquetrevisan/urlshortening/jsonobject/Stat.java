package com.moniquetrevisan.urlshortening.jsonobject;

public class Stat {

	private int id;
	private int hits;
	private String url;
	private String shortUrl;

	public Stat() {
	}

	public Stat(int id, int hits, String url, String shortUrl) {
		this.id = id;
		this.hits = hits;
		this.url = url;
		this.shortUrl = shortUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	
	@Override
	public String toString() {
		return new StringBuffer("[Stat: ")
				.append("id: ").append(this.id)
				.append("hits: ").append(this.hits)
				.append("url: ").append(this.url)
				.append("shortUrl: ").append(this.shortUrl)
				.append(" ]")
				.toString();
	}

}
