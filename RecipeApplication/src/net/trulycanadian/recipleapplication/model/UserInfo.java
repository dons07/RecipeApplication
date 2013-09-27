package net.trulycanadian.recipleapplication.model;

import java.io.Serializable;

public class UserInfo implements Serializable {

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	private long id;
	private int rating;
	private String userName;
	
}
