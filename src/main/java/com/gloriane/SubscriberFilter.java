package com.gloriane;

@FunctionalInterface
public interface SubscriberFilter {
    boolean matches(Subscriber subscriber);
}
