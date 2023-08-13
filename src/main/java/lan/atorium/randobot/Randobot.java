package lan.atorium.randobot;

import lan.atorium.randobot.sni.SniClient;
import lan.atorium.randobot.discord.DiscordBot;

/**
 *
 * @author gobo
 */
public class Randobot {
    
    public static void main(String[] args) {
        System.out.println("RandoBot boot process started...");
        
        SniClient sniClient = new SniClient("127.0.0.1", 8191);
        /*
        for (Sni.DevicesResponse.Device device : sniClient.getDevicesList(sniClient.getRequest())) {
            System.out.println(device.getDisplayName());
        }
        */

        DiscordBot discoBot = new DiscordBot();
    }
}
