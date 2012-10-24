package com.myproject;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import org.junit.Test;

/**
 * Some tests
 *
 * @author A Person
 */
public class AuthUtilsTest
{
    private boolean sysOut = true;
	private final static Logger LOGGER = Logger.getLogger(AuthUtilsTest.class.getName());

    @Test
    public void testGetConfig()
    {
    	
        Properties config = AuthUtils.getInstance().getConfig();
        assertNotNull(config);
        if (sysOut) 
        {
        	LOGGER.info("######### Configuration is ########");
            for (Map.Entry<Object, Object> configEntry : config.entrySet())
            {
            	LOGGER.info(configEntry.getKey()+" "+configEntry.getValue());
            }
            LOGGER.info("###### End of Configuration #######");
        }
        assertTrue(config.size()>0);
        assertNotNull(config.get("client_id"));
    }

}
