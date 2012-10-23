package com.myproject;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
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
	
	//extra config.properties constants
	public static final String aTestUrl = "aTestUrl";
	public static final String myBrowser = "myBrowser";

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
    
    /**
     * Launches in a browser
     * 
     * @param browser
     * @param url
     * @throws IOException
     */
	public void launchInBrowser(String browser, String url) throws IOException {

		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
		    if (desktop.isSupported(Action.BROWSE)) {
		    	desktop.browse(URI.create(url));
		        return;
		    }
		}
		
		if (browser != null) {
			Runtime.getRuntime().exec(new String[] {browser, url});
		} else {
			System.out.println("Open the following address in your favorite browser:");
			System.out.println("  " + url);
		}
	}
    
}
