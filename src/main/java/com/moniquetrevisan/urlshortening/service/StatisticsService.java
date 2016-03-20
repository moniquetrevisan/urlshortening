package com.moniquetrevisan.urlshortening.service;

import java.util.logging.Logger;

import com.moniquetrevisan.urlshortening.jsonobject.Stat;
import com.moniquetrevisan.urlshortening.persistence.StatisticsPersistence;

public class StatisticsService {
	
	private static Logger LOGGER = Logger.getLogger(StatisticsService.class.getName());
	
	private StatisticsPersistence statisticsPersistence;
	
	public StatisticsService() throws Exception{
		this.statisticsPersistence = new StatisticsPersistence();
	}
	
	public Stat getGlobalStatistics(){
		return null;
	}

}
