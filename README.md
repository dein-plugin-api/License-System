# License-System
Das exklusive Lizenz System ermöglicht es Entwicklern Ihre Plugins vor Weiterverkauf oder Mehrfachbenutzung zu schützen.
# Installation / Anleitung
Sie finden weiter Beispiele im Ordner [example](https://github.com/dein-plugin-api/License-System/tree/master/src/example). In diesem Teil werde ich erklären, wie Sie das License-System in Ihr Bukkit-Plugin einbauen. 
Sie haben die wahl, ob Sie die Libary per Maven importieren oder den Sourcecode manuel in Ihr IDE einfügen. Beachten Sie, dass gson benötigt wird. Hier sehen Sie nun eine "Main"-Klasse eines Plugins. Um das License-System nutzen zu können müssen Sie LicensePlugin implementieren (implements LicensePlugin). Erstellen Sie die benötigte Methode "onLicenseInvalid", diese wird ausgeführt, sobald das License-System festgestellt hat, dass die Lizenz ungültig ist. In diesem Beispiel wird das Plugin disabled. Zu aller erst erstellen Sie in dem "onEnable"-Teil eine neue Lizenz. (DeinPlugin license = ...). Als erstes Argument übergeben Sie den Lizenz-Key, welcher der Nutzer bei dem Kauf erhält. Als zweites Argument übergeben Sie die Produkt-ID, welche Sie im [Developer-Interface](https://dein-plugin.de/dev/) unter Marktplatz erhalten. Anschließend aktivieren Sie die Lizenz, indem Sie LicenseSystem.start(license, this) ausführen. Diese Methode übergibt einen boolean, der aussagt, ob der Lizenz-Key gültig ist. Sollter der Lizenz-Key ungültig sein müssen Sie das Plugin zum Beispiel disablen. Durch LicenseSystem.start wird außerdem alle 5 Minuten die Gültigkeit der Lizenz geprüft. Fügen Sie bei onDisable LicenseSystem.stop() hinzu, um die Lizenz abzumelden. Nun ist Ihr Plugin einsatzbereit.

```java
package de.deinplugin.LicenseExample;

import de.deinplugin.LizenzSystem.DeinPluginLicense;
import de.deinplugin.LizenzSystem.License;
import de.deinplugin.LizenzSystem.LicensePlugin;
import de.deinplugin.LizenzSystem.LicenseSystem;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements LicensePlugin{
    @Override
    public void onEnable() {
        saveDefaultConfig();
        DeinPluginLicense license = new DeinPluginLicense(getConfig().getString("license"), "2");
        if(!LicenseSystem.start(license, this)){
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onLicenseInvalid(License license) {
        getServer().getPluginManager().disablePlugin(this);
    }

    @Override
    public void onDisable() {
        LicenseSystem.stop();
    }
}

```
# Maven
```xml
<dependency>
  <groupId>de.deinplugin</groupId>
  <artifactId>LicenseLib</artifactId>
  <version>LATEST</version>
</dependency>
```
```xml
<repository>
    <id>deinplugin-repo</id>
    <url>http://maven.dein-plugin.de/nexus/content/repositories/releases/</url>
</repository>
```
