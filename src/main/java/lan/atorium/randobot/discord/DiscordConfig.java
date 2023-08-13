package lan.atorium.randobot.discord;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import lan.atorium.randobot.Randobot;

/**
 *
 * @author gobo
 */
public class DiscordConfig {
    private Properties secrets = new Properties();
    
    public DiscordConfig() throws Exception {
        init();
    }
    
    private void init() throws Exception {
        secrets = readSecretsFromFile();
        if (null == secrets.getProperty("bottoken")) {
            throw new Exception("No bottoken set in secrets.properties!");
        }
    }
    
    public Properties readSecretsFromFile() {
        Properties appSecrets = new Properties();
        BufferedInputStream stream;
        try {
            stream = new BufferedInputStream(new FileInputStream("secrets.properties"));
            appSecrets.load(stream);
            stream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Randobot.class.getName()).log(Level.SEVERE, "Please make sure you have a secrets.properties file with the secret tokens for your bot!", ex);
        } catch (IOException ex) {
            Logger.getLogger(Randobot.class.getName()).log(Level.SEVERE, "Could not access the secrets.properties file!", ex);
        }
        return appSecrets;
    }
    
    public Properties getSecrets() {
        return secrets;
    }
}
