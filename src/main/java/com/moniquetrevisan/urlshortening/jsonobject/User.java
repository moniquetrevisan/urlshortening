package com.moniquetrevisan.urlshortening.jsonobject;

public class User {

	private String id;

	public User() {
	}

	public User(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return new StringBuffer("[User: ").append("id: ").append(this.id).append(" ]").toString();
	}
}