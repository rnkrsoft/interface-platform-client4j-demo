package com.rnkrsoft.platform;

import com.rnkrsoft.platform.client.Location;
import com.rnkrsoft.platform.client.LocationProvider;
import com.rnkrsoft.platform.client.LocationStore;
import com.rnkrsoft.platform.client.ServiceFactory;
import com.rnkrsoft.platform.client.demo.domain.HelloRequest;
import com.rnkrsoft.platform.client.demo.domain.HelloResponse;
import com.rnkrsoft.platform.client.demo.service.HelloService;
import org.junit.Test;

/**
 * Created by woate on 2019/1/23.
 */
public class ServiceFactoryTest {
    @Test
    public void testSuccess() throws Exception {
        ServiceFactory serviceFactory = new ServiceFactory();
        serviceFactory.settingFallback("public", false, "localhost", 80, "api");
        serviceFactory.settingFallback("test-channel", false, "localhost", 80, "api");
        serviceFactory.setPassword("1234567890");
        serviceFactory.setKeyVector("1234567890654321");
        serviceFactory.addServiceClasses(HelloService.class);
        serviceFactory.registerLocationProvider(new LocationProvider() {
            @Override
            public void locate(LocationStore locationStore) {
                locationStore.refreshLocation(new Location(1, 2));
            }
        });
//        serviceFactory.setFetchMetadataIntervalSecond(5);
//        serviceFactory.setFetchConfigureIntervalSecond(8);
        serviceFactory.init();
        HelloService helloService = serviceFactory.get(HelloService.class);
        HelloRequest request = new HelloRequest();
        request.setName("test");
        HelloResponse response = helloService.hello(request);
        System.out.println(response);
        Thread.sleep(60000);
    }
}
