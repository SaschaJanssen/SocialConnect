package org.social.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilProperties {

	private static Logger logger = LoggerFactory.getLogger(UtilProperties.class);

	private static Map<String, Properties> propertyCache = new HashMap<String, Properties>();

	/**
	 * Returns the value of the specified property name from the specified
	 * resource/properties file
	 *
	 * @param resource
	 *            The name of the resource - can be a file, class, or URL
	 * @param name
	 *            The name of the property in the properties file
	 * @return The value of the property in the properties file
	 */
	public static String getPropertyValue(String resource, String name) {
		if (resource == null || resource.length() <= 0)
			return "";
		if (name == null || name.length() <= 0)
			return "";

		Properties properties = getProperties(resource);
		if (properties == null) {
			return "";
		}

		String value = null;

		try {
			value = properties.getProperty(name);
		} catch (Exception e) {
			logger.error("", e);
		}
		return value == null ? "" : value.trim();
	}

	/**
	 * Returns the specified resource/properties file
	 *
	 * @param resource
	 *            The name of the resource
	 * @return The properties file
	 */
	public static Properties getProperties(String resource) {
		if (resource == null || resource.length() <= 0) {
			return null;
		}

		if (propertyCache.containsKey("resource")) {
			return propertyCache.get(resource);
		}

		return getProperty(resource);
	}

	private static Properties getProperty(String resource) {
		Properties properties = null;
		try {
			properties = new Properties();
			properties.load(new FileInputStream(new File(resource)));
			propertyCache.put(resource, properties);
		} catch (IOException e) {
			logger.error("Could not find property resource: " + resource);
		}

		return properties;

	}

	public static long getPropertyValueAsLong(String resource, String name, long defaultValue) {
		String value = getPropertyValue(resource, name);
		if (UtilValidate.isEmpty(value)) {
			logger.info("The property " + name + " is empty, retruning default value: " + defaultValue);
			return defaultValue;
		}

		return Long.parseLong(value);
	}
}
