package space.hookszhang.duiker.predicate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.reflect.FieldUtils;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.AvailabilityPredicate;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidancePredicate;

@SuppressWarnings({"unchecked"})
public class CompositePredicate extends com.netflix.loadbalancer.CompositePredicate {

    public CompositePredicate(ZoneAvoidancePredicate zonePredicate, AvailabilityPredicate availabilityPredicate, BlueGreenPredicate blueGreenPredicate) {
        super();

        try {
            delegate.set(this, AbstractServerPredicate.ofKeyPredicate(Predicates.and(new Predicate[]{zonePredicate, availabilityPredicate, blueGreenPredicate})));
            fallbacks.set(this, Lists.newArrayList(availabilityPredicate, AbstractServerPredicate.alwaysTrue()));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Server> chooseRoundRobinAfterFiltering(List<Server> servers, Object loadBalancerKey) {
        List<Server> eligible = getEligibleServers(servers, loadBalancerKey);
        if (eligible.size() == 0) {
            return Optional.absent();
        }
        return Optional.of(eligible.get(incrementAndGetModulo(eligible.size())));
    }

    private final AtomicInteger nextIndex = new AtomicInteger();

    private int incrementAndGetModulo(int modulo) {
        for (; ; ) {
            int current = nextIndex.get();
            int next = (current + 1) % modulo;
            if (nextIndex.compareAndSet(current, next))
                return next;
        }
    }

    private static Field delegate = FieldUtils.getDeclaredField(com.netflix.loadbalancer.CompositePredicate.class, "delegate", true);
    private static Field fallbacks = FieldUtils.getDeclaredField(com.netflix.loadbalancer.CompositePredicate.class, "fallbacks", true);
}

