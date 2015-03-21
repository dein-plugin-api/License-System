package de.deinplugin.LizenzSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Dieser Lizenz-Typ ist nur für dein-plugin.de gültig.
 * @author jonasfranz
 *
 */
public class DeinPluginLicense extends  License{
	 private final String USER_AGENT = "Mozilla/5.0";
	 
	public DeinPluginLicense(String license_key, String product_key) {
		super(license_key, product_key);
		
	}
	private  LicenseResult fromException(Exception e){
		LicenseResult result = new LicenseResult();
		result.success = false;
		try {
			result.error = e.getMessage()+readUrl("https://dein-plugin.de/license.php?id="+getProductID()+"&key="+getKey()+"&mac="+getMacAdress());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(e.getMessage().toLowerCase().contains("5")){
			result.error = "Website ist offline"; 
			result.success = false;
		}
		return result;
	}
	public LicenseResult onValidate() {
		Gson gson = new Gson();
		try {
			return (LicenseResult) gson.fromJson(readUrl("https://dein-plugin.de/license.php?id="+getProductID()+"&key="+getKey()+"&mac="+getMacAdress()), LicenseResult.class);
		} catch (JsonSyntaxException e) {
			return fromException(e);
		} catch (Exception e) {
			return fromException(e);
		}
	}
	private  String readUrl(String urlString) throws Exception {
		StringBuilder content = new StringBuilder();
		 
	      URL url = new URL(urlString);
	 
	      URLConnection urlConnection = url.openConnection();
	      urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; de-DE; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

	      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	 
	      String line;
	 
	      while ((line = bufferedReader.readLine()) != null)
	      {
	        content.append(line + "\n");
	      }
	      bufferedReader.close();
	    
	   
	    return content.toString();
		
	   
	}
	 private String getMacAdress()
	  { 
	    try
	    {
	      InetAddress ip = InetAddress.getLocalHost();
	      NetworkInterface network = NetworkInterface.getByInetAddress(ip);
	      byte[] mac = network.getHardwareAddress();
	      StringBuilder sb = new StringBuilder();
	      for (int i = 0; i < mac.length; i++) {
	    	  
	        sb.append(String.format("%02X%s", new Object[] { Byte.valueOf(mac[i]), i < mac.length - 1 ? "" : "" }));
	      }
	      
	      return sb.toString();
	    }
	    catch (Exception localException) {}
	    return "";
	  }
	public LicenseResult onActivate() {
		Gson gson = new Gson();
		try {
			return (LicenseResult) gson.fromJson(readUrl("https://dein-plugin.de/activate.php?id="+getProductID()+"&key="+getKey()+"&mac="+getMacAdress()), LicenseResult.class);
		} catch (JsonSyntaxException e) {
			return fromException(e);
		} catch (Exception e) {
			return fromException(e);
		}
	}
	public void onDeactivate() {
		Gson gson = new Gson();
		try {
			gson.fromJson(readUrl("https://dein-plugin.de/deactivate.php?id="+getProductID()+"&key="+getKey()+"&mac="+getMacAdress()), LicenseResult.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
