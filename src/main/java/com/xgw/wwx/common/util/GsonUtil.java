package com.xgw.wwx.common.util;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {

	public static class GsonHolder {
		private static final Gson INSTANCE = new GsonBuilder().create();
	}

	public static Gson getGsonInstance() {
		return GsonHolder.INSTANCE;
	}

	/**
	 * 转成json
	 *
	 * @param object
	 * @return
	 */
	public static String GsonString(Object object) {
		if (null == object) {
			return null;
		}
		return getGsonInstance().toJson(object);
	}

	/**
	 * 转成bean
	 *
	 * @param gsonString
	 * @param cls
	 * @return
	 */
	public static <T> T GsonToBean(String gsonString, Class<T> cls) {
		return getGsonInstance().fromJson(gsonString, cls);
	}

	/**
	 * 转成list
	 *
	 * @param gsonString
	 * @param cls
	 * @return
	 */
	public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
		return getGsonInstance().fromJson(gsonString, new TypeToken<List<T>>() {
		}.getType());
	}

	/**
	 * 转成list中有map的
	 *
	 * @param gsonString
	 * @return
	 */
	public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
		return getGsonInstance().fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
		}.getType());
	}

	/**
	 * 转成map的
	 *
	 * @param gsonString
	 * @return
	 */
	public static <T> Map<String, T> GsonToMaps(String gsonString) {
		return getGsonInstance().fromJson(gsonString, new TypeToken<Map<String, T>>() {
		}.getType());
	}

}
