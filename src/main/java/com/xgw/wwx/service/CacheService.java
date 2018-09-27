package com.xgw.wwx.service;

public interface CacheService {

	public void put(String key, String value);

    public String get(String key);

    public void remove(String key);
    
    public boolean isHit(String key);
		
}
