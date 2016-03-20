package com.moniquetrevisan.urlshortening.service;

import org.codehaus.jettison.json.JSONObject;

import com.moniquetrevisan.urlshortening.persistence.StatisticsPersistence;

public class StatisticsService {
	
	private StatisticsPersistence statisticsPersistence;
	
	public StatisticsService() {
		this.statisticsPersistence = new StatisticsPersistence();
	}
	
	public JSONObject getGlobalStatistics(){
		JSONObject jsonResponse = new JSONObject(); 
		jsonResponse = statisticsPersistence.getTotalOfHitsAndUrlCount(jsonResponse, null);
		jsonResponse = statisticsPersistence.getTopUrls(jsonResponse, null);
		return jsonResponse;
	}

	public JSONObject getUserStatistics(String userId){
		JSONObject jsonResponse = new JSONObject(); 
		jsonResponse = statisticsPersistence.getTotalOfHitsAndUrlCount(jsonResponse, userId);
		jsonResponse = statisticsPersistence.getTopUrls(jsonResponse, userId);
		return jsonResponse;
	}
	
}
