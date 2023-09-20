package lan.atorium.randobot.sni;

import com.github.alttpo.sni.DevicesGrpc;
import com.github.alttpo.sni.DeviceMemoryGrpc;
import com.github.alttpo.sni.Sni;
import com.github.alttpo.sni.Sni.DevicesRequest;
import com.github.alttpo.sni.Sni.DevicesResponse;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author gobo
 */
public class SniClient {
    private SniConfig config;
    
    private Channel channel;
    
    private DevicesGrpc.DevicesBlockingStub blockingDevices;
    private DevicesGrpc.DevicesStub asyncDevices;
    
    private DeviceMemoryGrpc.DeviceMemoryBlockingStub blockingReadMemory;
    private DeviceMemoryGrpc.DeviceMemoryStub asyncReadMemory;
    
    public SniClient(String host, int port) {
        config = new SniConfig(host, port);
        
        init();
    }
    
    public SniClient(SniConfig sniConfig) {
        config = sniConfig;
        
        init();
    }
    
    private void init() {
        blockingDevices = getDevicesBlockingStub();
        asyncDevices = getDevicesAsyncStub();
        
        blockingReadMemory = getDeviceMemoryBlockingStub();
        asyncReadMemory = getDeviceMemoryAsyncStub();
    }
    
    public Channel getChannel() {
        ManagedChannelBuilder channelBuilder = ManagedChannelBuilder.forAddress(config.host, config.port);
        channelBuilder.usePlaintext();
        return channelBuilder.build();
    }
    
    public DevicesGrpc.DevicesBlockingStub getDevicesBlockingStub() {
        if (channel == null) {
            channel = getChannel();
        }
        return DevicesGrpc.newBlockingStub(channel);
    }
    
    public DevicesGrpc.DevicesStub getDevicesAsyncStub() {
        if (channel == null) {
            channel = getChannel();
        }
        return DevicesGrpc.newStub(channel);
    }
    
    public Sni.DevicesRequest getDevicesRequest() {
        Sni.DevicesRequest dr = Sni.DevicesRequest.newBuilder().build();
        return dr;
    }
    
    public List<DevicesResponse.Device> getBlockingDevicesList(DevicesRequest request) {
        return blockingDevices.listDevices(request).getDevicesList();
    }
    
    public List<DevicesResponse.Device> getAsyncDevicesList(DevicesRequest request) {
        
        final CountDownLatch finishLatch = new CountDownLatch(1);
        
        List<DevicesResponse.Device> devices = new ArrayList();
        
        StreamObserver<DevicesResponse> responseObserver = new StreamObserver<DevicesResponse>(){
            @Override
            public void onNext(DevicesResponse deviceResponse) {
                for (DevicesResponse.Device device : deviceResponse.getDevicesList()) {
                    devices.add(device);
                }
            }
            
            @Override
            public void onError(Throwable t) {
                System.out.println("Error receiving the devices list: " + t.getMessage());
                System.out.println("Trace: " + t.getStackTrace().toString());
                finishLatch.countDown();
            }
            
            @Override
            public void onCompleted() {
                finishLatch.countDown();
            }
        };
        
        asyncDevices.listDevices(request, responseObserver);
        responseObserver.onCompleted();
        
        return devices;
    }
    
    public DeviceMemoryGrpc.DeviceMemoryBlockingStub getDeviceMemoryBlockingStub() {
        if (channel == null) {
            channel = getChannel();
        }
        return DeviceMemoryGrpc.newBlockingStub(channel);
    }
    
    public DeviceMemoryGrpc.DeviceMemoryStub getDeviceMemoryAsyncStub() {
        if (channel == null) {
            channel = getChannel();
        }
        return DeviceMemoryGrpc.newStub(channel);
    }
    
    public Sni.SingleReadMemoryRequest getSingleReadMemoryRequest() {
        Sni.SingleReadMemoryRequest rmr = Sni.SingleReadMemoryRequest.newBuilder().build();
        return rmr;
    }
    
    public Sni.SingleReadMemoryRequest getSingleReadMemoryRequest(String address) {
        int addressValue = Integer.decode(address);
        // Sni.SingleReadMemoryRequest rmr = Sni.SingleReadMemoryRequest.newBuilder().setRequest(addressValue).build();
        Sni.SingleReadMemoryRequest rmr = Sni.SingleReadMemoryRequest.newBuilder().build();
        return rmr;
    }
    
    public Sni.SingleReadMemoryResponse getBlockingSingleReadMemory(Sni.SingleReadMemoryRequest request) {
        return blockingReadMemory.singleRead(request);
    }
}
