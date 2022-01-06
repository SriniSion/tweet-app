package com.tweetapp.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.dao.TweetCountRepository;
import com.tweetapp.dao.TweetRepository;
import com.tweetapp.entity.Tweets;
import com.tweetapp.entity.TweetsCount;
import com.tweetapp.exception.BusinessException;
import com.tweetapp.exception.ExceptionConstants;
import com.tweetapp.models.TweetDto;
import com.tweetapp.models.TweetListDto;
import com.tweetapp.service.TweetService;
import com.tweetapp.utils.CommonUtility;

@Service
public class TweetServiceImpl implements TweetService {

	private static final Logger logger = LoggerFactory.getLogger(TweetServiceImpl.class);
	

	@Autowired
	TweetRepository tweetRepository;
	
	@Autowired
	TweetCountRepository tweetCountRepository;



	@Override
	public String postNewTweet(TweetDto tweetDto, String userName) {
		String status = null;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Tweets tweet = tweetRepository.findByUserName(userName);

		if(Objects.nonNull(tweet)) {
			TweetListDto tweetListDto =CommonUtility.stringToObjectConverter(tweet.getTweets(),TweetListDto.class);
			List<TweetDto> tweetList = tweetListDto.getTweets();
			TweetsCount tweetCounts = tweetCountRepository.findByName("tweetcount");
			tweetDto.setTweetId(tweetCounts.getTweetCount()+1);
			tweetCounts.setTweetCount(tweetCounts.getTweetCount()+1);
			tweetCountRepository.save(tweetCounts);
			tweetDto.setUserName(userName);
			tweetList.add(tweetDto);
			tweetListDto.setTweets(tweetList);
			String tweetsListString = CommonUtility.objectToStringConverter(tweetListDto);
			tweet.setUserName(userName);
			tweet.setTweets(tweetsListString);
			
			
		}else {
			TweetListDto tweetListDto = new TweetListDto();
			List<TweetDto> tweetList = new ArrayList<TweetDto>();
			TweetsCount tweetCounts = tweetCountRepository.findByName("tweetcount");
			tweetDto.setTweetId(tweetCounts.getTweetCount()+1);
			tweetCounts.setTweetCount(tweetCounts.getTweetCount()+1);
			tweetCountRepository.save(tweetCounts);
			tweetDto.setUserName(userName);
			tweetList.add(tweetDto);
			tweetListDto.setTweets(tweetList);
			String tweetsListString = CommonUtility.objectToStringConverter(tweetListDto);
			tweet = new Tweets();
			tweet.setUserName(userName);
			tweet.setTweets(tweetsListString);
			
			
		}
		Tweets tweetResponse = tweetRepository.save(tweet);
		if(Objects.nonNull(tweetResponse)) {
			
			status= "Posted Successful";
		}else{
			status= "Failed to Post";
		}
		return status;
	}



	@Override
	public String updateTweet(TweetDto tweetDto, String userName, int id) {
		String status = null;
		Tweets tweet = tweetRepository.findByUserName(userName);

		if(Objects.nonNull(tweet)) {
			TweetListDto tweetListDto =CommonUtility.stringToObjectConverter(tweet.getTweets(),TweetListDto.class);
			List<TweetDto> tweetList = tweetListDto.getTweets();
			TweetDto userTweetDto=tweetList.stream().filter(userTweet -> id==userTweet.getTweetId())
			  .findAny()
			  .orElse(null);
			if(Objects.nonNull(userTweetDto)) {
			userTweetDto.setTweet(tweetDto.getTweet());
			userTweetDto.setPostedDate(tweetDto.getPostedDate());
			}else {
				throw new BusinessException(ExceptionConstants.TWEET_NOT_FOUND, ExceptionConstants.GENERAL_MODULE,
						"Tweet Not Available");
			}
			tweetListDto.setTweets(tweetList);
			String tweetsListString = CommonUtility.objectToStringConverter(tweetListDto);
			tweet.setUserName(userName);
			tweet.setTweets(tweetsListString);
		}
		
		Tweets tweetResponse = tweetRepository.save(tweet);
		if(Objects.nonNull(tweetResponse)) {
			
			status= "Tweet Updated";
		}else{
			status= "Failed to Post";
		}
		return status;
	}



	@Override
	public String deleteTweet(TweetDto tweetDto, String userName, int id) {
		String status = null;
		Tweets tweet = tweetRepository.findByUserName(userName);

		if(Objects.nonNull(tweet)) {
			TweetListDto tweetListDto =CommonUtility.stringToObjectConverter(tweet.getTweets(),TweetListDto.class);
			List<TweetDto> tweetList = tweetListDto.getTweets();
			
			tweetList.removeIf(userTweet -> id==userTweet.getTweetId());
			tweetListDto.setTweets(tweetList);
			String tweetsListString = CommonUtility.objectToStringConverter(tweetListDto);
			tweet.setUserName(userName);
			tweet.setTweets(tweetsListString);
		}
		
		Tweets tweetResponse = tweetRepository.save(tweet);
		if(Objects.nonNull(tweetResponse)) {
			
			status= "Tweet Deleted";
		}else{
			status= "Failed to Delete";
		}
		return status;
	}



	@Override
	public List<TweetDto> getUserTweets(String userName) {
		Tweets tweet = tweetRepository.findByUserName(userName);
		List<TweetDto> tweetList = new ArrayList<TweetDto>();
		if(Objects.nonNull(tweet)) {
			TweetListDto tweetListDto =CommonUtility.stringToObjectConverter(tweet.getTweets(),TweetListDto.class);
			tweetList = tweetListDto.getTweets();
		}else {
			throw new BusinessException(ExceptionConstants.NO_TWEETS_BY_USER, ExceptionConstants.GENERAL_MODULE,
					"Tweets Not Available");
		}
		
		return tweetList;
	}



	@Override
	public List<TweetDto> getAllTweets() {
		List<Tweets> tweets = tweetRepository.findAll();
		
		
		List<TweetDto> tweetList =tweets.stream().flatMap(m->CommonUtility.stringToObjectConverter(m.getTweets(),TweetListDto.class)
				.getTweets().stream()).collect(Collectors.toList());

		
	
		if(Objects.isNull(tweetList)) {
			throw new BusinessException(ExceptionConstants.NO_TWEETS_BY_USER, ExceptionConstants.GENERAL_MODULE,
					"Tweets Not Available");
		}
		
		return tweetList;
	
	}

}
