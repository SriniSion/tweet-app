package com.tweetapp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TWEETS")
public class Tweets {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	
	private String userName;
	@Column(columnDefinition = "TEXT")
	private String tweets;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getTweets() {
		return tweets;
	}

	public void setTweets(String tweets) {
		this.tweets = tweets;
	}

	
	
	
	
}