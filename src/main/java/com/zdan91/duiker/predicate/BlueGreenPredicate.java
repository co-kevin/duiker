package com.zdan91.duiker.predicate;

import java.util.Map;

import com.zdan91.duiker.config.Constants;
import com.zdan91.duiker.utils.SleuthUtils;
import org.apache.commons.lang.StringUtils;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PredicateKey;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

public class BlueGreenPredicate extends AbstractServerPredicate {

    public BlueGreenPredicate(IRule rule, IClientConfig clientConfig) {
        super(rule, clientConfig);
    }

    @Override
    public boolean apply(PredicateKey input) {
        String loadBalancerKey = SleuthUtils.getBaggage(Constants.COLOR);

        if (input.getServer() instanceof DiscoveryEnabledServer) {
            DiscoveryEnabledServer server = (DiscoveryEnabledServer) input.getServer();
            Map<String, String> meta = server.getInstanceInfo().getMetadata();

            if (StringUtils.isNotBlank(loadBalancerKey) && !loadBalancerKey.equals(meta.get(Constants.COLOR))) {
                return false;
            } else if (StringUtils.isBlank(loadBalancerKey)) {
                if (meta.containsKey(Constants.COLOR) && StringUtils.isNotBlank(meta.get(Constants.COLOR))) {
                    return false;
                }
            }
            return server.isAlive();
        }

        return true;
    }
}

