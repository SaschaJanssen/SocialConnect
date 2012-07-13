package org.social.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Test;
import org.social.core.util.UtilProperties;

public class UtilPropertiesTest {

	@Test
	public void testGetProperties() {
		Properties prop = UtilProperties.getProperties("conf/social.properties");
		assertNotNull(prop);
		assertFalse(prop.isEmpty());
	}

	@Test
	public void testGetPropValue() throws Exception {
		String value = UtilProperties.getPropertyValue("conf/social.properties", "application.name");
		assertEquals("SocialConnect", value);
	}

	@Test
	public void testGetLongValue() throws Exception {
		long value = UtilProperties.getPropertyValueAsLong("conf/social.properties", "schedule.period", 6);
		assertEquals(3600000, value);

		value = UtilProperties.getPropertyValueAsLong("conf/social.properties", "schedule.period2", 6);
		assertEquals(6, value);
	}

}
