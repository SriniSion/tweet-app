package com.tweetapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.models.TweetDto;
import com.tweetapp.service.TweetService;

@RestController
@RequestMapping("/tweets")
@CrossOrigin(origins = "http://localhost:4200")
public class TweetController {

	@Autowired
	TweetService tweetService;

	@RequestMapping(value = "/{userName}/add", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> postNewTweet(@RequestBody TweetDto tweetDto,@PathVariable String userName) {
		String  status = tweetService.postNewTweet(tweetDto,userName);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{userName}/update/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<String> updateTweet(@RequestBody TweetDto tweetDto,@PathVariable String userName,@PathVariable int id) {
		String  status = tweetService.updateTweet(tweetDto,userName,id);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{userName}/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<String> deleteTweet(@RequestBody TweetDto tweetDto,@PathVariable String userName,@PathVariable int id) {
		String  status = tweetService.deleteTweet(tweetDto,userName,id);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{userName}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<TweetDto>> getUserTweets(@PathVariable String userName) {
		List<TweetDto> tweetList = tweetService.getUserTweets(userName);
		return new ResponseEntity<List<TweetDto>>(tweetList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<TweetDto>> getAllTweets() {
		List<TweetDto> tweetList = tweetService.getAllTweets();
		return new ResponseEntity<List<TweetDto>>(tweetList, HttpStatus.OK);
	}
	
	

	
}
