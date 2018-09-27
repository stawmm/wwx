package com.xgw.wwx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xgw.wwx.service.CacheService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
public class CacheServiceImpl implements CacheService {

	@Autowired
	private CacheManager cacheManager;

	@Override
	public void put(String key, String value) {
		Cache userCache = cacheManager.getCache("userCache");
		Element element = new Element(key, value);
		userCache.put(element);

	}

	@Override
	public String get(String key) {
		Cache userCache = cacheManager.getCache("userCache");
		Element element = userCache.get(key);
		if (null == element) {
			return null;
		}
		return (String) element.getObjectValue();
	}

	@Override
	public void remove(String key) {
		Cache userCache = cacheManager.getCache("userCache");
		userCache.remove(key);

	}

	@Override
	public boolean isHit(String key) {
		Cache userCache = cacheManager.getCache("userCache");
		Element element = userCache.get(key);
		return element != null;
	}

}
