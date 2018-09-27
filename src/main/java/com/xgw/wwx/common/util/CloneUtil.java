package com.xgw.wwx.common.util;


import org.apache.commons.beanutils.BeanUtils;

public class CloneUtil {

	@SuppressWarnings("unchecked")
	public static <T> T cloneBean(T t) {
		try {
			return (T) BeanUtils.cloneBean(t);
		}  catch (Exception e) {
			throw new RuntimeException("-- cloneBean error --");
		}
	}
	
}
