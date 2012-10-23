package com.myproject;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Properties;

import org.junit.Test;

/**
 * Some tests
 *
 * @author A Person
 */
public class AuthUtilsTests
{
    private boolean sysOut = true;

    @Test
    public void testGetConfig()
    {
    	
        Properties config = AuthUtils.getInstance().getConfig();
        assertNotNull(config);
        if (sysOut) 
        {
            System.out.println("######### Configuration is ########");
            for (Map.Entry<Object, Object> configEntry : config.entrySet())
            {
                System.out.println(configEntry.getKey()+" "+configEntry.getValue());
            }
            System.out.println("###### End of Configuration #######");
        }
        assertTrue(config.size()>0);
        assertNotNull(config.get("client_id"));
    }

}
