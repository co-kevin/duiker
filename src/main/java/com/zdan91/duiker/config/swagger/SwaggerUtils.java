package com.zdan91.duiker.config.swagger;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

public class SwaggerUtils {

    public static String basePackage;

    public static String findUrl(DiscoveryClient discoveryClient, String serviceId, String relativePath) {
        ServiceInstance instance = discoveryClient.getInstances(serviceId).stream().findAny().orElse(null);
        if (instance != null) {
            if (instance.getMetadata().containsKey("groupName")) {
                String groupName = instance.getMetadata().get("groupName");
                ServiceInstance gateway = discoveryClient.getInstances(groupName).stream().findAny().orElse(null);
                if (gateway != null) {
                    return "http://" + gateway.getHost() + ":" + gateway.getPort() + "/" + serviceId.toLowerCase() + "/" + relativePath;
                }
            }
            return "http://" + instance.getHost() + ":" + instance.getPort() + "/" + relativePath;
        }
        return "";
    }
}
