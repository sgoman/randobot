package lan.atorium.randobot.sni;

import com.github.alttpo.sni.DevicesGrpc;
import com.github.alttpo.sni.Sni;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import java.util.List;

/**
 *
 * @author gobo
 */
public class SniClient {
    private SniConfig config;
    
    private Channel channel;
    private DevicesGrpc.DevicesBlockingStub blockingDevices;
    
    public SniClient(String host, int port) {
        config = new SniConfig(host, port);
        
        blockingDevices = getDevicesBlockingStub();
    }
    
    public SniClient(SniConfig sniConfig) {
        config = sniConfig;
        
        blockingDevices = getDevicesBlockingStub();
    }
    
    public DevicesGrpc.DevicesBlockingStub getDevicesBlockingStub() {
        if (channel == null) {
            channel = getChannel();
        }
        return DevicesGrpc.newBlockingStub(channel);
    }
    
    public Channel getChannel() {
        ManagedChannelBuilder channelBuilder = ManagedChannelBuilder.forAddress(config.host, config.port);
        return channelBuilder.build();
    }
    
    public Sni.DevicesRequest getRequest() {
        return Sni.DevicesRequest.newBuilder().build();
    }
    
    public List<Sni.DevicesResponse.Device> getDevicesList(Sni.DevicesRequest request) {
        return blockingDevices.listDevices(request).getDevicesList();
    }
}
