package lan.atorium.randobot.sni;

/**
 *
 * @author gobo
 */
public class SniConfig {
    public String host = "127.0.0.1";
    public int port = 8191;
    
    public SniConfig() {
        String envHost = System.getenv("SNI_HOST");
        if (envHost != null) {
            host = envHost;
        }
        String envPort = System.getenv("SNI_LISTEN_PORT");
        if (envPort != null) {
            port = Integer.parseInt(envPort);
        }
    }
    
    public SniConfig(String sniHost, int sniPort) {
        host = sniHost;
        port = sniPort;
    }
}
