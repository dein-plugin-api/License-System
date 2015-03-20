package example;

import de.deinplugin.LizenzSystem.DeinPluginLicense;
import de.deinplugin.LizenzSystem.License;
import de.deinplugin.LizenzSystem.LicensePlugin;
import de.deinplugin.LizenzSystem.LicenseSystem;

/**
 * Implementieren Sie LicensePlugin, damit Ihr Plugin die Methode onLicenseInvalid implementiert.
 * 
 * Um dieses Beispiel Bukkit/BungeeCord-Neutral zu halten wurden Methoden erstellt die auf Bukkit hinweisen. Beispiele für Bukkit und BungeeCord
 * finden Sie auf https://dein-plugin.de/dev/
 * 
 * @author jonasfranz
 *
 */
public class Example implements LicensePlugin{

	public void onEnable(){
		//Registrieren Sie Ihr Plugin als LizenzPlugin:
		//Fügen Sie bei [Ihr Produktkey] den Schlüssel ein, den Sie im Developer-Interface (https://dein-plugin.de/dev/) erhalten.
		//Lesen Sie z.B. den Lizenz-Key aus der Config.yml Datei aus.
		LicenseSystem.start(new DeinPluginLicense(getConfig().getString("license"), "[Ihr Produkt-Key]"), this);
	}
	public void onLicenseInvalid(License lizenz) {
		//Stoppen Sie das Plugin. Diese Methode wird aufgerufen, sobald die Lizenz-Prüfung fehlschlägt.
		//..
	}
	
	public void onDisable(){
		//Der Lizenz-Key wird wieder abgemeldet.
		LicenseSystem.stop();
		
	}
	
	/**
	 * Ignorieren Sie diese Methode. Sie dient nur als Platzhalter für später Bukkit/BungeeCord Methoden.
	 * @return
	 */
	private Config getConfig(){
		return new Config();
	}

}
