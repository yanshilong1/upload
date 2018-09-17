package cn.cloud.core.common;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonBinder {

	private static Logger logger = LoggerFactory.getLogger(JsonBinder.class);

	private ObjectMapper mapper;

	public JsonBinder(Inclusion inclusion) {
		mapper = new ObjectMapper();
		mapper.getSerializationConfig().setSerializationInclusion(inclusion);
		mapper.getDeserializationConfig().set(
				org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}


	public static JsonBinder buildNormalBinder() {
		return new JsonBinder(Inclusion.ALWAYS);
	}


	public static JsonBinder buildNonNullBinder() {
		return new JsonBinder(Inclusion.NON_NULL);
	}


	public static JsonBinder buildNonDefaultBinder() {
		return new JsonBinder(Inclusion.NON_DEFAULT);
	}

	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (jsonString==null || jsonString.trim().length() == 0) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}


	public String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}

	public ObjectMapper getMapper() {
		return mapper;
	}
	
}

