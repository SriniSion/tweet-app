package com.tweetapp.service;

import java.util.List;

import com.tweetapp.models.TweetDto;

public interface TweetService {



	String postNewTweet(TweetDto tweetDto, String userName);

	String updateTweet(TweetDto tweetDto, String userName, int id);

	String deleteTweet(TweetDto tweetDto, String userName, int id);

	List<TweetDto> getUserTweets(String userName);

	List<TweetDto> getAllTweets();

}
