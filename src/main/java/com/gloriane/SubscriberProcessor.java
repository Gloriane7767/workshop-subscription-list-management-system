package com.gloriane;

import java.util.List;
import java.util.stream.Collectors;

public class SubscriberProcessor {
    public List<Subscriber> findSubscribers(List<Subscriber> subscribers, SubscriberFilter filter) {
        return subscribers.stream()
                .filter(filter::matches)
                .collect(Collectors.toList());
    }

    public List<Subscriber> applyToMatching(List<Subscriber> subscribers,
                                           SubscriberFilter filter,
                                           SubscriberAction action) {
        subscribers.stream()
                .filter(filter::matches)
                .forEach(action::run);
        return subscribers;
    }
}
