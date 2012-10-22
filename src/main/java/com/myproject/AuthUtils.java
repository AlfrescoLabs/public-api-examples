package com.myproject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Simple Utilities class
 *
 * @author A Person
 */
public class AuthUtils
{

	//A singleton
	private static final AuthUtils INSTANCE = new AuthUtils();
	public static AuthUtils getInstance() { return INSTANCE; }
	
	/**
	 * Private constructor, use getInstance() instead.
	 */
    private AuthUtils() 
    {
		super();
	}


	/**
     * Gets the configuration properties
     * @return Properties config props
     */
    public Properties getConfig()
    {
        Properties prop = new Properties();
        
        try {
               //load a properties file
        	InputStream inputStream = this.getClass().getResourceAsStream("/config.properties");
        	if (inputStream!=null)
        	{
	            prop.load(inputStream);
        	}

        } catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        
        return prop;
    }
    
}
