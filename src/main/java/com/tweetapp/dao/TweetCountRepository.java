package com.tweetapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tweetapp.entity.Tweets;
import com.tweetapp.entity.TweetsCount;

public interface TweetCountRepository extends JpaRepository<TweetsCount, Integer> {

	TweetsCount findByName(String name);

}
