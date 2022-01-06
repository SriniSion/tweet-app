package com.tweetapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tweetapp.entity.Tweets;

public interface TweetRepository extends JpaRepository<Tweets, Integer> {

	Tweets findByUserName(String userName);
	

}
