package com.moniquetrevisan.urlshortening.service;

import java.util.logging.Logger;

import com.moniquetrevisan.urlshortening.jsonobject.Stat;

public class StatisticsService {
	
	private static Logger LOGGER = Logger.getLogger(StatisticsService.class.getName());
	
	private StatisticsService statisticsService;
	
	public StatisticsService(){
		this.statisticsService = new StatisticsService();
	}
	
	public Stat getGlobalStatistics(){
		return null;
	}

}
