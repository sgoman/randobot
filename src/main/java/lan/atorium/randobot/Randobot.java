package lan.atorium.randobot;

import com.github.alttpo.sni.Sni;
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
        
        for (Sni.DevicesResponse.Device device : sniClient.getBlockingDevicesList(sniClient.getDevicesRequest())) {
            System.out.println("Connected device: " + device.getDisplayName());
        }
        
        // 0x18614e is the address of the chest in Link's house in Archipelago

        // DiscordBot discoBot = new DiscordBot();
    }
}
