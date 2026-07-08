package fr.fitzche.lgmore.Util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonUtil {

	
	
	public static Integer getInt(JsonObject obj, String key, int defaultValue) {
		if (obj.get(key) != null) {
			return obj.get(key).getAsInt();
		} else {
			return defaultValue;
		}
	}
	
	public static String getString(JsonObject obj, String key, String defaultValue) {
		if (obj.get(key) != null) {
			return obj.get(key).getAsString();
		} else {
			return defaultValue;
		}
	}
	
	public static Double getDouble(JsonObject obj, String key, Double defaultValue) {
		if (obj.get(key) != null) {
			return obj.get(key).getAsDouble();
		} else {
			return defaultValue;
		}
	}
	
	public static Boolean getBool(JsonObject obj, String key, Boolean defaultValue) {
		if (obj.get(key) != null) {
			return obj.get(key).getAsBoolean();
		} else {
			return defaultValue;
		}
	}
	public static JsonArray getJsonArray(JsonObject obj, String key) {
		if (obj.get(key) != null) {
			return obj.get(key).getAsJsonArray();
		} else {
			return new JsonArray();
		}
	}
	public static JsonObject getJsonObject(JsonObject obj, String key) {
		if (obj.get(key) != null) {
			return obj.get(key).getAsJsonObject();
		} else {
			return new JsonObject();
		}
	}
}
