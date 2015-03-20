package de.deinplugin.LizenzSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Dieser Lizenz-Typ ist nur für dein-plugin.de gültig.
 * @author jonasfranz
 *
 */
public class DeinPluginLicense extends  License{

	public DeinPluginLicense(String license_key, String product_key) {
		super(license_key, product_key);
		
	}
	private static LicenseResult fromException(Exception e){
		LicenseResult result = new LicenseResult();
		result.success = false;
		result.error = e.getMessage();
		return result;
	}
	public LicenseResult onValidate() {
		Gson gson = new Gson();
		try {
			return (LicenseResult) gson.fromJson(readUrl("https://www.dein-plugin.de/license.php?id="+getProductID()+"&key="+getKey()+"&mac="+getMacAdress()), LicenseResult.class);
		} catch (JsonSyntaxException e) {
			return fromException(e);
		} catch (Exception e) {
			return fromException(e);
		}
	}
	private static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
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
			return (LicenseResult) gson.fromJson(readUrl("https://www.dein-plugin.de/activate.php?id="+getProductID()+"&key="+getKey()+"&mac="+getMacAdress()), LicenseResult.class);
		} catch (JsonSyntaxException e) {
			return fromException(e);
		} catch (Exception e) {
			return fromException(e);
		}
	}
	public void onDeactivate() {
		Gson gson = new Gson();
		try {
			gson.fromJson(readUrl("https://www.dein-plugin.de/deactivate.php?id="+getProductID()+"&key="+getKey()+"&mac="+getMacAdress()), LicenseResult.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
