package de.deinplugin.LizenzSystem;



public abstract class License {
	private String key, product_id;
	public License(String license_key, String product_key){
		key = license_key;
		this.product_id = product_key;
	}
	public String getKey(){ return key; }
	public String getProductID(){ return product_id;}
	
	/** s
	 * Wird ausgeführt, wenn die Lizenz überprüft werden soll.
	 * @return Gibt das Ergebnis des Lizenz-Checks zurück.
	 */
	public abstract LicenseResult onValidate();
	/**
	 * Wird bei dem Serverstart ausgeführt.
	 * @return Gibt das Ergebnis des Lizenz-Checks zurück.
	 */
	public abstract LicenseResult onActivate();
	/**
	 * Wird bei dem Serverstop ausgeführt.
	 */
	public abstract void onDeactivate();
	
	
	
	public static class LicenseResult{
		/**
		 * Gibt an, ob die Lizenz gültig ist.
		 */
		public boolean success;
		/**
		 * Gibt an welcher Fehler passiert ist. 
		 */
		public String error;
	}

}
