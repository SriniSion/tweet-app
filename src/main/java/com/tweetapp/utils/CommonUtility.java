package com.tweetapp.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtility {


	public static <T> String objectToStringConverter(T obj) {
		String output = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			output = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return output;
	}

	public static <T> T stringToObjectConverter(String input, Class<T> obj) {
		ObjectMapper mapper = new ObjectMapper();
		T result = null;
		try {
			result = mapper.readValue(input, obj);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Timestamp stringToTimeStamp(String str) {
		Timestamp timestamp = null;
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		    Date parsedDate = dateFormat.parse(str);
		     timestamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch(Exception e) { //this generic but you can control another types of exception
		    // look the origin of excption 
		}
		
		return timestamp;
	}

	

}