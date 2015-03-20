package de.deinplugin.LizenzSystem;

import java.util.Timer;
import java.util.TimerTask;

import de.deinplugin.LizenzSystem.License.LicenseResult;


public class LicenseSystem
{
	private static Thread thread;
	private static License license;
	private static boolean failed = false;
	public  static boolean start(final License lic, final LicensePlugin plugin){
		LicenseResult result = lic.onActivate();
		if(!result.success) {
			failed = true;
			System.err.println("Fehler bei der Aktivierung des Lizenz-Keys: "+result.error);
			return false;
		}
		license = lic;
		thread = new Thread(new Runnable() {
			
			public void run() {
				Timer t = new Timer();
				t.schedule(new TimerTask() {
					
					public void run() {
						LicenseResult result = license.onValidate();
						if(!result.success){
							failed =true;
							System.err.println("Fehler bei der Lizenz-Prüfung: "+ result.error);
							plugin.onLicenseInvalid(lic);
						}
						
					}
				}, 1*60*1000, 5*60*1000);
			}
		});
		thread.start();
		return true;
	}
	public static void stop(){
		if(!failed) license.onDeactivate();
		if(!failed) thread.stop();
	}
}
